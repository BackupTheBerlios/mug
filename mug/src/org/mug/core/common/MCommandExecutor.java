/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.common;

import java.util.*;
import org.apache.log4j.Logger;
import org.mug.gui.actions.MFileOpenAction;
import java.awt.EventQueue;

//
//  Commandline options:
//  [no dash]   XXX YYY              -- load models
//   -X[jarfilename.jar!]classname.class:method(type:param1,type:param2) -- execute method
//   -Stop                           -- terminate
//
// -Xfile:\c:\abc.jar!classname.class:method(type1:param1,type2:param2)
// -Xhttp:\\www.aaa.com\abc.jar!classname.class:method(type1:param1,type2:param2)
// -Xfile:\c:\archive\classname.class:method(type1:param1,type2:param2)
// -Xclassname.class:method(type1:param1,type2:param2)
// -Xclassname.class:method()
// -Xclassname.class

public class MCommandExecutor
{
  private final static Logger log = Logger.getLogger( MCommandExecutor.class );

  private static ArrayList init( String[] args )
  {
    ArrayList commandList = new ArrayList();
    if (null == args)
      return ( commandList );
    for( int index = 0; index < args.length; index++ )
    {
      String token = args[index];
      if( null == token || "".equals(token) )
        continue;
      token = token.trim();
      if( !(token.startsWith("-")) )
      {
        commandList.add( new MLoadCommand( token ) );
        continue;
      }
      String mode = token.substring(1,2);
      String value = token.substring(2);
      if( "F".equalsIgnoreCase( mode ) )
      {
        commandList.add( new MForkCommand( value ) );
        continue;
      }
      if( "T".equalsIgnoreCase( mode ) )
      {
        commandList.add( new MTrailCommand( value ) );
        continue;
      }
      if( "-stop".equalsIgnoreCase( token ) )
      {
        commandList.add( new MStopCommand( "Stop" ) );
        continue;
      }
      commandList.add( new MUnrecognizedCommand( token ) );
    }
    return ( commandList );
  }

  public static synchronized void execute( String[] args )
  {
    final ArrayList commandList = init( args );
    if( 0 == commandList.size() )
      log.info( "nothing to do" );
    EventQueue.invokeLater( new Runnable()
    {
      public void run()
      {
        for( Iterator it = commandList.iterator(); it.hasNext(); )
          ((MCommand)it.next()).execute();
      }
    });
  }

//------------------------------------------------------------------------------

  private static abstract class MCommand
  {
    protected final String command;
    public MCommand( String command )
    {
      this.command = command;
    }
    public void execute()
    {
      log.info( "Executing command : " + command );
    }
  }

  private static class MLoadCommand extends MCommand
  {
    public MLoadCommand( String command )
    {
      super( command );
    }
    public void execute()
    {
      super.execute();
      ((MFileOpenAction)MFileOpenAction.getInstance()).run( command );
    }
  }

  private static class MTrailCommand extends MCommand
  {
    public MTrailCommand( String command )
    {
      super( command );
    }
    public void execute()
    {
      super.execute();
//      MTrailRunner.execute( command );
    }
  }

  private static class MForkCommand extends MCommand
  {
    public MForkCommand( String command )
    {
      super( command );
    }

    public void execute()
    {
      super.execute();
//      executeTrail( command );
    }

//     //   -X[jarfilename.jar#]classname.class:method(param1,param2) -- execute main method from classfile specified
//    private void executeTrail( String value )
//    {
//      ClassLoader loader = null;
//      int jarIndex = value.toLowerCase().indexOf(".jar");
//      if( jarIndex < 0 )
//        loader = CommandExecutor.class.getClassLoader();
//      else
//      {
//        jarIndex += 4;
//        String jarURLString = value.substring( 0, jarIndex );
//        URL url = SrfcURLClassLoader.getURL( jarURLString );
//        loader = URLClassLoader.newInstance( new URL[]{ url }, CommandExecutor.class.getClassLoader() );
//        value = value.substring( jarIndex+1 );
//      }
//
//      Class clazz = null;
//      int classIndex = value.toLowerCase().indexOf(".class");
//      if( classIndex >=0 )
//      {
//        String className = value.substring( 0, classIndex );
//        try{ clazz = loader.loadClass( className ); }
//        catch( Exception ex ){ clazz = null; ex.printStackTrace(); }
//        value = value.substring( classIndex + 6 );
//      }
//      if( null == clazz )
//        return;
//
//      String[] parameters = null;
//      if( value.endsWith(")") )
//      {
//        int openIndex = value.indexOf('(');
//        String param = value.substring( openIndex+1, value.length()-1 );
//        StringTokenizer tokenizer = new StringTokenizer( param, "," );
//        parameters = new String[ tokenizer.countTokens() ];
//        for( int index = 0; tokenizer.hasMoreTokens(); index++ )
//          parameters[index] = tokenizer.nextToken();
//        value = value.substring( 0, openIndex );
//      }
//      else
//        parameters = new String[]{};
//
//      String methodName = ( "".equals(value) ? "main" : value.substring(1) );
//      try
//      {
//        Method method = clazz.getMethod( methodName, new Class[]{ String[].class } );
//        method.invoke( null, new Object[]{ parameters } );
//      }
//      catch( Exception ex ) { ex.printStackTrace(); }
//    }
  }

  private static class MStopCommand extends MCommand
  {
    public MStopCommand( String command )
    {
      super( command );
    }
    public void execute()
    {
      System.exit(0);
    }
  }

  private static class MUnrecognizedCommand extends MCommand
  {
    public MUnrecognizedCommand( String command )
    {
      super( command );
    }
    public void execute()
    {
      log.error( "Unrecognized command : " + command +"\nUnable to execute. SKIPPING." );
    }
  }
}
