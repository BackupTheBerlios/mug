/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import org.mug.core.interfaces.MiMessageListener;
import org.mug.core.common.MMessage;
import java.awt.EventQueue;
import org.apache.log4j.Logger;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Component;

public class MStatusBar extends JLabel
{
  private final static Logger log = Logger.getLogger( MStatusBar.class );

  private final Dimension M_STATUS_BAR_SIZE = new Dimension( 0, 15 );

  private static MStatusBar instance;

  private MStatusBar()
  {
    super( "" );
    jbInit();
  }

  private void jbInit()
  {
    this.setMinimumSize( M_STATUS_BAR_SIZE );
    this.setPreferredSize( M_STATUS_BAR_SIZE );
    this.setMaximumSize( M_STATUS_BAR_SIZE );
    MMessage.addMessageListener( new MiMessageListener()
    {
      public void messagePosted( final int priority, final String message )
      {
        if( MiMessageListener.STATUS != priority )
          return;
        EventQueue.invokeLater( new Runnable()
        {
          public void run()
          {
            MStatusBar.this.setText( message );
          }
        });
      }
    });
  }

  public static Component getInstance()
  {
    return ( null == instance ? instance = new MStatusBar() : instance );
  }
}
