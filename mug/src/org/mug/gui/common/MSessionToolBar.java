/* ------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import org.apache.log4j.Logger;
import java.awt.Component;

public class MSessionToolBar extends MToolBar
{
  private final static Logger log = Logger.getLogger( MSessionToolBar.class );

  private JPanel holder = new JPanel( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );

  private MSessionToolBar()
  {
    super( new BorderLayout() );
    jbInit();
  }

  private void jbInit()
  {
    this.setFloatable( false );
    this.setBorder( null );
    this.add( holder, BorderLayout.WEST );

    this.add( holder, BorderLayout.WEST );
    this.add( MModelChooser.create(), BorderLayout.CENTER );
  }

  public static Component create()
  {
    MSessionToolBar sessionToolBar = new MSessionToolBar();
    Component modelChooser = MModelChooser.create();
    if( null != modelChooser )
      sessionToolBar.add( MModelChooser.create(), BorderLayout.CENTER );
//    sessionToolBar.holder.add( MToolBarButton.create( MFileCloseAction.getInstance() ) );
    return ( sessionToolBar );
  }

//  public static Component create( Element sessionToolBarElement )
//  {
//    String modelChooserClass = sessionToolBarElement.getAttributeValue( "componentClass" );
//    if( !MSessionToolBar.class.getName().equals( modelChooserClass ) )
//      return ( null );
//    return ( create() );
//  }
}
