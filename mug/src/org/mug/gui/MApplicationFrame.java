/* ------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui;

import javax.swing.JFrame;
import org.apache.log4j.Logger;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import java.util.ResourceBundle;
import java.awt.BorderLayout;
import org.mug.core.common.MConfig;
import javax.swing.ImageIcon;
import org.mug.core.util.MDOMBuilder;
import java.io.InputStream;
import org.jdom.Element;
import org.mug.gui.common.MMenuBar;
import org.mug.gui.common.*;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JMenuBar;

public class MApplicationFrame extends JFrame
{
  private final static Logger log = Logger.getLogger( MApplicationFrame.class );
//------------------------------------------------------------------------------

  private static String resourseBundle = MConfig.getProperty( "application.localization.bundle" );
  private static ResourceBundle res = ResourceBundle.getBundle( resourseBundle );

  public static final String M_LEFT_VIEW_CONTAINER_NAME  = "Modeller";
  public static final String M_RIGHT_VIEW_CONTAINER_NAME = "Designer";
//------------------------------------------------------------------------------

  private MViewContainer modeller = MViewContainer.getInstance( M_LEFT_VIEW_CONTAINER_NAME );
  private MViewContainer designer = MViewContainer.getInstance( M_RIGHT_VIEW_CONTAINER_NAME );

//------------------------------------------------------------------------------
  private static MApplicationFrame instance;

  private MApplicationFrame()
  {
    jbInit();
  }

  public static JFrame getInstance()
  {
    return ( null == instance ? instance = new MApplicationFrame() : instance );
  }

  private void jbInit()
  {
    JPopupMenu.setDefaultLightWeightPopupEnabled( false );
    this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//---
//    String resourceFileName = MConfig.getProperty("descriptor.xml");
    String resourceFileName = "descriptor.xml";
    InputStream inputStream = MApplicationFrame.class.getClassLoader().getResourceAsStream( resourceFileName );
    Element rootElement = MDOMBuilder.build( inputStream ).getRootElement();
    Component toolBar = MToolBar.create( rootElement.getChild( "ApplicationToolBar" ) );
    Component menuBar = MMenuBar.create( rootElement.getChild( "ApplicationMenuBar" ) );
//---
    this.setTitle( MConfig.getProperty( MConstant.M_PROP_APPICATION_WINDOW_HEADER,
                                        MConstant.M_DEFAULT_APPICATION_WINDOW_HEADER ) );

    this.setIconImage( ((ImageIcon)MIconData.Windchill_ICON ).getImage() );
    this.setJMenuBar( (JMenuBar)menuBar );

    int xSize = MConfig.getProperty( MConstant.M_PROP_APPLICATION_WINDOW_WIDTH,
                                     MConstant.M_DEFAULT_APPLICATION_WINDOW_WIDTH );
    int ySize = MConfig.getProperty( MConstant.M_PROP_APPLICATION_WINDOW_HEIGHT,
                                     MConstant.M_DEFAULT_APPLICATION_WINDOW_HEIGHT );
    this.setSize( xSize, ySize );
    Dimension frameSize = this.getSize();
    this.setLocation( ( MConstant.M_SCREEN_DIMENSION.width - frameSize.width ) / 2,
                      ( MConstant.M_SCREEN_DIMENSION.height - frameSize.height - 30 ) / 2 );

//---
    JPanel leftComponentHolder = new JPanel( new BorderLayout() );

    leftComponentHolder.add( MSessionToolBar.create(), BorderLayout.NORTH );
    leftComponentHolder.add( modeller, BorderLayout.CENTER );

//---
    JPanel rightComponentHolder = new JPanel( new BorderLayout() );
    rightComponentHolder.add( designer, BorderLayout.CENTER );

//---
    JSplitPane splitPane = new JSplitPane();
    splitPane.setDividerSize( 10 );
    splitPane.setOneTouchExpandable( true );
    splitPane.setResizeWeight( 0.25 );
    splitPane.setDividerLocation( 0.25 );
    splitPane.setOrientation( JSplitPane.HORIZONTAL_SPLIT );
    splitPane.add( leftComponentHolder, JSplitPane.LEFT );
    splitPane.add( rightComponentHolder, JSplitPane.RIGHT );

//---
    JTabbedPane bottomPane  = new JTabbedPane( JTabbedPane.BOTTOM );
    bottomPane.add( new MMessagePanel(), "Messages" );

//---
    JSplitPane mainPane = new JSplitPane();
    mainPane.setDividerSize( 10 );
    mainPane.setOneTouchExpandable( true );
    mainPane.setResizeWeight( 0.85 );
    mainPane.setDividerLocation( 0.85 );
    mainPane.setOrientation( JSplitPane.VERTICAL_SPLIT );
    mainPane.add( splitPane, JSplitPane.TOP );
    mainPane.add( bottomPane, JSplitPane.BOTTOM );

//---
    JPanel contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout( new BorderLayout() );
    contentPane.add( toolBar, BorderLayout.NORTH );
    contentPane.add( mainPane, BorderLayout.CENTER );
    contentPane.add( MStatusBar.getInstance(), BorderLayout.SOUTH );

//---
    MGUISession.getInstance().addLifeCycleListener( modeller );
    MGUISession.getInstance().addLifeCycleListener( designer );
  }
}
