/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.common;

import org.apache.log4j.Logger;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.mug.core.interfaces.MiServer;

import org.mug.gui.*;


public class MServer extends UnicastRemoteObject implements MiServer
{
  private final static Logger log = Logger.getLogger( MServer.class );

  private static final String M_PROP_SERVER_NAME = "application.rmi.server.name";
  private static final String M_DEFAULT_SERVER_NAME = "M_SERVER";

  public static final String M_SERVER_NAME =
    MConfig.getProperty( MConstant.M_PROP_SERVER_NAME, MConstant.M_DEFAULT_SERVER_NAME );

  public static final String M_SERVER_HOST =
    MConfig.getProperty( MConstant.M_PROP_SERVER_HOST, "localhost" );

  public static final int M_SERVER_PORT =
    MConfig.getProperty( MConstant.M_PROP_SERVER_PORT, MConstant.M_DEFAULT_SERVER_PORT );

  public static final String M_SERVER_INFO = "[" + M_SERVER_NAME + "@" + M_SERVER_HOST + ":" + M_SERVER_PORT + "]";

  private MServer() throws RemoteException
  {
  }

  public static MiServer lookup()
  {
    try
    {
      log.info( " Locating a Server " + M_SERVER_INFO );
      Registry registry = LocateRegistry.getRegistry( M_SERVER_HOST, M_SERVER_PORT );
      MiServer server = (MiServer)registry.lookup( M_SERVER_NAME );
      log.info( " Server located" );
      return ( server );
    }
    catch( Exception rex ) // expected proper behaviour if no servers
    {
      log.info( " Unable to locate a Server " + M_SERVER_INFO + "\n Reason: " + rex.getMessage() );
    }
    return ( null );
  }

  public static MiServer create()
  {
    try
    {
      MServer server = new MServer();
      log.info( " Creating a Server " + M_SERVER_INFO );
      LocateRegistry.createRegistry( M_SERVER_PORT ).rebind( M_SERVER_NAME, server );
      log.info( " Server " + M_SERVER_INFO + " created" );
      return ( server );
    }
    catch( Exception rex )
    {
      log.info( " Unable to locate a Server " + M_SERVER_INFO + "\n Reason: " + rex.getMessage() );
    }
    return ( null );
  }

  public void execute( String args[] )
  {
//    log.info( " Executing commands on the Server " + M_SERVER_INFO );
    MCommandExecutor.execute( args );  //DP: invoking static server-side method
//    log.info( " Done " );
  }
}
