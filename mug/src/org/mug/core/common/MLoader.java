/* -------------------------------------------------------------------------- */
/*   Date        Version         Name      Description of modification        */
/* ========================================================================== */

package org.mug.core.common;

import java.io.*;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.net.URLClassLoader;
import java.rmi.RMISecurityManager;

public class MLoader
{
  private static final String M_CUR_PATH     = System.getProperty( "user.dir" );
  private static final String M_HOME_PATH    = System.getProperty( "user.home" );
  private static final String M_CONFIG_FILE  = "loader.properties";
  private static final URL [] URL_TYPE = new URL[0];

  private static URLClassLoader loader    = null;
  private static String log4jConfigFilePath   = null;
  private static String className;

  private static final String M_PROP_USE_SECURITY_MANAGER = "loader.useSecurityManager";
  private static final String M_PROP_RUN                  = "loader.run";
  private static final String M_PROP_CLASS_PATH           = "loader.classpath";
  private static final String M_PROP_LOG4J_CONFIG_FILE    = "log4j.configfile";
  private static final String M_PROP_JVM_VERSION_CURRENT  = "java.specification.version";
  private static final String M_PROP_JVM_VERSION_REQUIRED = "loader.java.version";

static // initialize here!
{
  System.out.println( "Start Loader..." );
  MProperties config = getConfig();
  if( "true".equalsIgnoreCase( config.getProperty( M_PROP_USE_SECURITY_MANAGER, "false" ) ) )
  {
    try { System.setSecurityManager( new RMISecurityManager() ); }
    catch( Exception ex ) { ex.printStackTrace(); }
  }

  try
  {
    double jvmVersionRequired = Double.parseDouble( config.getProperty( M_PROP_JVM_VERSION_REQUIRED, "0.0" ) );
    double jvmVersionCurrent  = Double.parseDouble( System.getProperty( M_PROP_JVM_VERSION_CURRENT ) );
    System.out.println("JVM version " + jvmVersionCurrent + " detected.");
    if( jvmVersionCurrent < jvmVersionRequired )
      throw new java.lang.UnsupportedOperationException( "Required JVM version " + jvmVersionRequired + " or above" );
  }
  catch( Exception ex )
  {
    System.err.println( ex.getMessage() );
    System.exit( -1 );
  }

  String classPath = config.getProperty( M_PROP_CLASS_PATH );
  String log4jConfigFileName = config.getProperty( M_PROP_LOG4J_CONFIG_FILE );

  Enumeration props = config.propertyNames();

  while( props.hasMoreElements() )
  {
    String key = (String)props.nextElement();
    System.setProperty( key, config.getProperty( key ) );
  }

  try
  {
    StringTokenizer tokenizer = new StringTokenizer( classPath, ",;" );
    ArrayList list = new ArrayList();
    while( tokenizer.hasMoreTokens() )
    {
      URL url = getURL( tokenizer.nextToken() );
      if( null == url )
        continue;
      list.add( url );
    }
    loader = (URLClassLoader) new URLClassLoader( (URL[]) list.toArray( URL_TYPE ) );
    // Some code pieces, e.g. JAXP, count on context ClassLoader
    // to load their classes and resources.
    Thread.currentThread().setContextClassLoader( loader );
  }
  catch( Exception ex )
  {
    System.out.println( "Cannot create custom URLClassLoader" );
    ex.printStackTrace();
    System.exit( -1 );
  }

  try
  {
    Class cls = loader.loadClass( "org.apache.log4j.PropertyConfigurator" );
    if( null != log4jConfigFilePath )
    {
      String log4jConfigFile =
          ( log4jConfigFileName.indexOf( File.separatorChar ) < 0 ?
            log4jConfigFilePath + log4jConfigFileName : log4jConfigFileName );
      System.out.println( "Loading Log4J configuration as file: " + log4jConfigFile );
      Method log4jConfigureAndWatch = cls.getMethod( "configureAndWatch", new Class[]{String.class} );
      log4jConfigureAndWatch.invoke( null, new Object[]{ log4jConfigFile } );
    }
    else
    {
      System.out.println( "Loading Log4J configuration as stream: " + log4jConfigFileName );
      URL log4jConfigURL = cls.getClassLoader().getResource( log4jConfigFileName );
      if( null == log4jConfigURL )
      {
        throw new java.lang.RuntimeException( "Cannot load log4j configuration file" );
      }
      Method log4jConfigure = cls.getMethod( "configure", new Class[]{URL.class} );
      log4jConfigure.invoke( null, new Object[]{ log4jConfigURL } );
    }
  }
  catch( Exception ex )
  {
    System.out.println( "Log4J initialization failed" );
    ex.printStackTrace();
    System.exit( -1 );
  }
  className = config.getProperty( M_PROP_RUN );
}

  static private void run( String[] args ) //Application entry
  {
    try
    {
      Class cls = loader.loadClass( className );
      Method mainMethod = cls.getMethod( "main", new Class[]{ String[].class } );
      mainMethod.invoke( null, new Object[]{ args } );
    }
    catch( Exception ex )
    {
      System.out.println( "Cannot construct an application" );
      ex.printStackTrace();
      System.exit( -1 );
    }
  }

  private static URL getURL( String urlString )
  {
    if( null == urlString )
      return ( null );
    urlString = urlString.trim();
    try{ return ( new URL( urlString ) ); }
    catch( Exception ex ) {}
    try{ return ( (new File( urlString )).toURL() ); }
    catch( Exception ex ) {}
    return ( null );
  }

  private static MProperties getConfig()
  {
    try // Reading settings from working directory
    {
      String cfgPath = M_CUR_PATH + File.separatorChar + M_CONFIG_FILE;
      File configFile = new File( cfgPath );
      if( configFile.exists() && configFile.isFile() )
      {
        System.out.println( "Reading working loader configuration file..." );
        InputStream configInputStream = new FileInputStream( cfgPath );
        MProperties config = new MProperties();
        config.load( configInputStream );
        log4jConfigFilePath = M_CUR_PATH + File.separatorChar;
        return( config );
      }
    }
    catch( IOException iex )
    {
      iex.printStackTrace();
    }

    try // Reading settings from home directory
    {
      String cfgPath = M_HOME_PATH + File.separatorChar + M_CONFIG_FILE;
      File configFile = new File( cfgPath );
      if( configFile.exists() && configFile.isFile() )
      {
        System.out.println( "Reading home loader configuration file..." );
        InputStream configInputStream = new FileInputStream( cfgPath );
        MProperties config = new MProperties();
        config.load( configInputStream );
        log4jConfigFilePath = M_HOME_PATH + File.separatorChar;
        return( config );
      }
    }
    catch( IOException iex )
    {
      iex.printStackTrace();
    }

    try // Reading settings from load directory
    {
      String cfgPath = M_CONFIG_FILE;
      InputStream configInputStream = MLoader.class.getClassLoader().getResourceAsStream( cfgPath );
      if( null != configInputStream )
      {
        System.out.println( "Reading default loader configuration file..." );
        MProperties config = new MProperties();
        config.load( configInputStream );
        return( config );
      }
    }
    catch( IOException iex )
    {
      iex.printStackTrace();
    }
    System.out.println( "ERROR: No configuration files " + M_CONFIG_FILE + " found" );
    return( null );
  }

  static public void main( String[] args )
  {
    run( args );
  }
}
