/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.util;

import java.util.Enumeration;
import java.io.File;
import java.util.Properties;
import java.util.StringTokenizer;

public class MRuntime
{
  private static final int UNKNOWN_OS_TYPE = 0;
  private static final int WINDOWS_OS_TYPE = 1;
  private static final int UNIX_OS_TYPE    = 2;

  private static final String[] GET_ENV_COMMAND = new String[]{ null, "set", "setenv" };

  private static final int CURRENT_OS_TYPE;

static
{
  String osName = System.getProperty( "os.name" );
  if( null == osName )
    CURRENT_OS_TYPE = 0;
  else
    CURRENT_OS_TYPE = osName.trim().toUpperCase().startsWith( "WINDOWS" ) ?
                      WINDOWS_OS_TYPE : UNIX_OS_TYPE;
}

  private static Properties osEnvironment = null;

  public static Properties getOSEnvironment() throws Exception
  {
    if( null != osEnvironment )
      return ( new Properties( osEnvironment ) );
    if( UNKNOWN_OS_TYPE == CURRENT_OS_TYPE )
      throw new Exception( "Not supported" );

    osEnvironment = new Properties();
    String envCommand = GET_ENV_COMMAND[ CURRENT_OS_TYPE ];

    MRuntimeExecStatistics execStatistics = exec( envCommand );
    if( null != execStatistics.getException() )
      throw execStatistics.getException();
    if( 0 != execStatistics.getExitCode() )
      throw new Exception( " Abnormal program termination," +
                           "\n   program   :" + envCommand +
                           "\n    exitcode :" + execStatistics.getExitCode() );

    String report = execStatistics.getReport();
    StringTokenizer tokenizer = new StringTokenizer( report, "=\n" );
    for(;;)
    {
      String key = ( tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null );
      String value = ( tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null );
      if( null == key || null == value )
        break;
      osEnvironment.setProperty( key, value );
    }
    return ( osEnvironment );
  }

  private static String[] getApplication( String command )
  {
    if( WINDOWS_OS_TYPE == CURRENT_OS_TYPE )
      return ( new String[] { "cmd.exe", "/c", command } );
    if( UNIX_OS_TYPE == CURRENT_OS_TYPE )
      return ( new String[] { "csh", "-c", command } );
    return ( new String[] { command } );
  }

  public static class MRuntimeExecStatistics
  {
    private int       exitCode;
    private String    report;
    private Exception exception;

    public MRuntimeExecStatistics( int exitCode, String report, Exception exception )
    {
      this.exitCode = exitCode;
      this.report = report;
      this.exception = exception;
    }

    public int getExitCode()
    {
      return ( exitCode );
    }

    public String getReport()
    {
      return ( report );
    }

    public Exception getException()
    {
      return ( exception );
    }
  }

  public static MRuntimeExecStatistics exec( String command )
  {
    return ( exec( command, null, null ) );
  }

  public static MRuntimeExecStatistics exec( String command, Properties environment )
  {
    return ( exec( command, environment, null ) );
  }

  public static MRuntimeExecStatistics exec( String command, Properties environment, File runDir )
  {
    String env[] = null;
    if( null != environment )
    {
      env = new String[ environment.size() ];
      Enumeration en = environment.propertyNames();
      for( int index = 0; en.hasMoreElements(); index++ )
      {
        String name = (String)en.nextElement();
        String value = environment.getProperty( name );
        env[index] = name + "=" + value;
      }
    }
    String report = null;
    int exitCode = -1;
    Exception exception = null;
    try
    {
      Process process = Runtime.getRuntime().exec( getApplication( command ), env, runDir );
      report = MStreamUtil.convertToString( process.getInputStream() );
      exitCode = process.waitFor();
      return ( new MRuntimeExecStatistics( exitCode, report, null ) );
    }
    catch( Exception ex )
    {
      exception = ex;
    }
    return ( new MRuntimeExecStatistics( exitCode, report, exception ) );
  }
}
