/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui;

import java.awt.Toolkit;
import java.awt.Dimension;
import org.apache.log4j.Logger;
import javax.swing.UIManager;

public class MConstant
{
  private final static Logger log = Logger.getLogger( MConstant.class );

  public static final String M_PROP_APPLICATION_CLASSPATH_PRINT = "application.classpath.print";
  public static final String M_PROP_APPLICATION_PROPERTIES_PRINT = "application.properties.print";
  public static final String M_PROP_APPICATION_TRAIL_WRITE = "application.trail.write";

//--[ Look and Feel ]-----------------------------------------------------------
  public static final String M_DEFAULT_LOOK_AND_FEEL = UIManager.getCrossPlatformLookAndFeelClassName();
  public static final String M_PROP_APPICATION_LOOK_AND_FEEL = "application.lookAndFeel";

//--[ ApplicationFrame size and placement ]-------------------------------------
  public static final Dimension M_SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
  private static final int M_DEFAULT_X_POS = 10;
  private static final int M_DEFAULT_Y_POS = 25;
  public static final int M_DEFAULT_APPLICATION_WINDOW_WIDTH = M_SCREEN_DIMENSION.width - M_DEFAULT_X_POS * 2;
  public static final int M_DEFAULT_APPLICATION_WINDOW_HEIGHT = M_SCREEN_DIMENSION.height - M_DEFAULT_Y_POS * 2;
  public static final String M_PROP_APPLICATION_WINDOW_WIDTH = "application.window.width";
  public static final String M_PROP_APPLICATION_WINDOW_HEIGHT = "application.window.height";
  public static final String M_PROP_APPICATION_WINDOW_HEADER = "application.window.header";
  public static final String M_DEFAULT_APPICATION_WINDOW_HEADER = "Application";
//--[ Application locale ]------------------------------------------------------

  public static final String M_DEFAULT_APPLICATION_LOCALE = "en";
  public static final String M_PROP_APPLICATION_LOCALE = "application.locale";

//--[ RMI server ]--------------------------------------------------------------
  public static final String M_PROP_APPICATION_STANDALONE = "application.standalone";
  public static final String M_PROP_SERVER_NAME = "application.rmi.server.name";
  public static final String M_DEFAULT_SERVER_NAME = "M_SERVER";

  public static final String M_PROP_SERVER_HOST  = "application.rmi.host";
  public static final String M_PROP_SERVER_PORT  = "application.rmi.port";
  public static final int    M_DEFAULT_SERVER_PORT = 2669;

static
{
}

}
