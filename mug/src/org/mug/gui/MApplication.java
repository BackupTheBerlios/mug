/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui;

import org.mug.core.common.MConfig;
import org.apache.log4j.Logger;
import java.net.URL;
import javax.swing.UIManager;
import java.util.Locale;
import java.net.URLClassLoader;
import javax.swing.JFrame;
import org.mug.core.interfaces.MiServer;
import org.mug.core.common.*;
import user.model.ModelOne;

public class MApplication
{
  private final static Logger log = Logger.getLogger( MApplication.class );

  private static void init()
  {
    if( MConfig.getProperty( MConstant.M_PROP_APPLICATION_CLASSPATH_PRINT, true ) )//false
    {
      ClassLoader loader = MApplication.class.getClassLoader();
      if( loader instanceof URLClassLoader )
      {
        URL urls[] = ( (URLClassLoader)loader ).getURLs();
        if( urls.length > 0 )
        {
          log.info( "ClassPath = " );
          for (int i = 0; i < urls.length; i++)
          {
            URL url = urls[i];
            log.info("\t" + url.toExternalForm() );
//            InputStream content = null;
//            try {
//              URLConnection connection = url.openConnection();
//              sun.net.www.protocol.file.FileURLConnection j;
//              content = (InputStream)connection.getContent();
//            } catch (IOException ex) {
//            }
//            try{
//              log.info("\t" + url.toExternalForm() + "[" + getReport(content) +
//                       "]");
//            }
//            catch( Exception ex)
//            {
//            }
          }
        }
      }
    }

    if( MConfig.getProperty( MConstant.M_PROP_APPLICATION_PROPERTIES_PRINT, false ) )
      MConfig.debugPrintAllProps();
  }

  private static void runGUI()
  {

    Locale.setDefault(
      new Locale( MConfig.getProperty(
         MConstant.M_PROP_APPLICATION_LOCALE,
         MConstant.M_DEFAULT_APPLICATION_LOCALE ) ) );
    try
    {
      UIManager.setLookAndFeel(
        MConfig.getProperty(
          MConstant.M_PROP_APPICATION_LOOK_AND_FEEL,
          MConstant.M_DEFAULT_LOOK_AND_FEEL ) );
    }
    catch( Exception ex )
    {
      log.error( " Unable to setup look and feel", ex );
    }

    JFrame frame = MApplicationFrame.getInstance();
    frame.setVisible( true );
    frame.validate();
//-------------------
    MGUISession.getInstance().add( new ModelOne() );
  }

  public static void main( String[] args )
  {
    MApplication.init();

    boolean standalone = MConfig.getProperty(  MConstant.M_PROP_APPICATION_STANDALONE, false );

    MiServer server = ( standalone ? null : MServer.lookup() );

    if( null == server )              //DP: standalone or no servers located
      MApplication.runGUI();
    if( standalone )
    {
      log.info(" Running standalone mode");
      MCommandExecutor.execute( args ); //DP: local exec
    }
    else
    {
      if (null == server)
        server = MServer.create(); //DP:launch server if needed
      if (null == server)
        System.exit( 0 );

      log.info( " Executing commands remotely on the Server " + MServer.M_SERVER_INFO );
      try { server.execute( args ); } //DP: remote exec
      catch( Exception ex ) { log.error(" Unable to execute.\n Reason: " + ex.getMessage() ); }
      log.info( " Done " );
    }
  }
}
