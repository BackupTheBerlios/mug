/* -------------------------------------------------------------------------- */
/* Date         Version	    	Name            Description of modification     */
/* ========================================================================== */

package org.mug.core.common;

import org.apache.log4j.Logger;

import java.util.*;
import java.io.*;
import org.mug.core.common.*;

public class MConfig
{
  private static final Logger log = Logger.getLogger( MConfig.class );

  private static final String RUN_DIR_PATH    = System.getProperty( "user.dir" );
  private static final String HOME_DIR_PATH   = System.getProperty( "user.home" );
  private static final String CONFIG_FILE     = "application.properties";

  private static final Properties curProps     = new MProperties();
  private static final Properties homeProps    = new MProperties();
  private static final Properties defaultProps = new MProperties();

static
{
  String userPropertyFile = System.getProperty( CONFIG_FILE );
  loadConfig( null != userPropertyFile ? userPropertyFile : CONFIG_FILE );
}

  private static void loadConfig( String configFileName )
  {
    log.info( "Loading config " + configFileName);
    try
    {
      String cfgPath = configFileName;
      InputStream configInputStream = MConfig.class.getClassLoader().getResourceAsStream( cfgPath );
      if( null != configInputStream )
      {
        log.debug("Reading classpath configuration file...");
        defaultProps.load( configInputStream );
      }
    }
    catch( IOException iex )
    {
      log.error( iex.getMessage() );
    }

    try
    {
      String cfgPath = HOME_DIR_PATH + File.separatorChar + configFileName;
      File configFile = new File( cfgPath );
      if( configFile.exists() && configFile.isFile() )
      {
        log.debug( "Reading homedir configuration file...");
        InputStream configInputStream = new FileInputStream( cfgPath );
        homeProps.load( configInputStream );
      }
    }
    catch( IOException iex )
    {
      log.error( iex.getMessage() );
    }

    try
    {
      String cfgPath = RUN_DIR_PATH + File.separatorChar + configFileName;
      File configFile = new File( cfgPath );
      if( configFile.exists() && configFile.isFile() )
      {
        log.debug( "Reading rundir configuration file..." );
        InputStream configInputStream = new FileInputStream( cfgPath );
        curProps.load( configInputStream );
      }
    }
    catch( IOException iex )
    {
      log.error( iex.getMessage() );
    }

    if( 0 == homeProps.size() + curProps.size() + defaultProps.size() )
      log.error( "No configuration files " + configFileName + " found" );
    else
      log.info( "Config " + configFileName + " loaded");

  }

  public static String getProperty( String key, String defaultValue )
  {
    String propValue = getProperty( key );
    return ( null != propValue ? propValue : defaultValue );
  }

  public static int getProperty( String key, int defaultValue )
  {
    String propValue = getProperty( key );
    try
    {
      return ( Integer.parseInt( propValue ) );
    }
    catch ( Exception ex )
    {
      log.warn( "Unable to parse integer value\nKey : " + key +
                " value : " + propValue + " ; using default : " + defaultValue );
    }
    return ( defaultValue );
  }

  public static boolean getProperty( String key, boolean defaultValue )
  {
    String propValue = getProperty( key );
    if( null == propValue )
      return ( defaultValue );
    propValue = propValue.trim();
    if( "true".equalsIgnoreCase( propValue ) || "yes".equalsIgnoreCase( propValue ) )
      return ( true );
    if( "false".equalsIgnoreCase( propValue ) || "no".equalsIgnoreCase( propValue ) )
      return ( false );
    log.warn( "Unable to parse boolean value\nKey : " + key +
              " value : " + propValue + " ; using default : " + defaultValue );
    return ( defaultValue );
  }

  public static String getProperty( String key )
  {
    String propValue;

    if( null != ( propValue = curProps.getProperty( key ) ) )
      return ( propValue );
    if( null != ( propValue = homeProps.getProperty( key ) ) )
      return ( propValue );
    if( null != ( propValue = defaultProps.getProperty( key )) )
      return ( propValue );
    try { return ( System.getProperty( key ) ); }
    catch( Exception ex ) {}
    return ( null );
  }

  public static void setProperty( String key, String value )
  {
    curProps.setProperty( key, value );
  }


//  public static void saveUserProperties()
//  {
//    Enumeration allProps = curProps.propertyNames();
//
//    while( allProps.hasMoreElements() )
//    {
//      String key = (String)allProps.nextElement();
//      homeProps.setProperty( key, curProps.getProperty( key ) );
//    }
//
//    saveProperties( HOME_PATH, homeProps );
//  }
//
//  public static void saveWorkingProperties()
//  {
//    saveProperties( CUR_PATH, curProps );
//  }
//
//  public static void saveProperties( String dirName, Properties props )
//  {
//    File dir = null;
//    try
//    {
//      dir = new File(dirName);
//      File file = new File(dir, CONFIG_FILE);
//      props.store(new FileOutputStream(file), "Configuration");
//    }
//    catch (Throwable e)
//    {
//      log.error("Error saving properties to "+dir+File.separatorChar+CONFIG_FILE+": ", e);
//    }
//  }

  public static void debugPrintAllProps()
  {
    debugPrintProps( curProps, "rundir" );
    debugPrintProps( homeProps, "homedir" );
    debugPrintProps( defaultProps,  "classpath" );
    debugPrintProps( System.getProperties(), "system" );
  }

  private static void debugPrintProps( Properties props, String location )
  {
    log.info( "--[ start " + location + " properties ]--" );
    Enumeration allProps = props.propertyNames();
    while( allProps.hasMoreElements() )
    {
      String key = (String)allProps.nextElement();
      log.info( "Property = " + key.toLowerCase() + "   Value = " + props.getProperty( key ) );
    }
    log.info( "--[ end properties ]--" );
  }
}
