/* -------------------------------------------------------------------------- */
/* Date      Version  Name  Description of modification                       */
/* ========================================================================== */

package org.mug.gui.common;

import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import org.apache.log4j.Logger;

public abstract class MAbstractButton extends JButton
{
  private final static Logger log = Logger.getLogger( MAbstractButton.class );

  private static final Dimension BUTTON_DIMENSIONS = new Dimension(29, 29);
  private static final Border    BUTTON_BORDER =
      BorderFactory.createBevelBorder( BevelBorder.RAISED, Color.lightGray,
          Color.white, Color.lightGray, new Color(104, 99, 91) );
  private static final Insets    BUTTON_MARGIN_INSETS = new Insets(1, 1, 1, 1);

  public MAbstractButton( MAbstractAction action )
  {
    super( action );
    jbInit( (String)action.getValue( action.SHORT_DESCRIPTION ) );
  }

  private void jbInit( String toolTipText )
  {
    this.setBorder( BUTTON_BORDER );
    this.setMaximumSize( BUTTON_DIMENSIONS );
    this.setMinimumSize( BUTTON_DIMENSIONS );
    this.setPreferredSize( BUTTON_DIMENSIONS );
    this.setBorderPainted( false );
    this.setFocusPainted( false );
    if( null != toolTipText )
      this.setToolTipText( toolTipText );
    this.setMargin( BUTTON_MARGIN_INSETS );
    this.setText("");
    this.addMouseListener( new java.awt.event.MouseAdapter()
    {
      public void mouseEntered( MouseEvent event )
      {
        setBorderPainted( true );
      }
      public void mouseExited( MouseEvent event  )
      {
        setBorderPainted( false );
      }
    });
  }
}
