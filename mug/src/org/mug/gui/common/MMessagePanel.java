/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import org.mug.core.interfaces.MiMessageListener;
import org.mug.core.common.MMessage;
import java.awt.EventQueue;
import org.apache.log4j.Logger;

public class MMessagePanel extends JScrollPane
{
  private final static Logger log = Logger.getLogger( MMessagePanel.class );

  private JTextPane messagePanel = new JTextPane();

  public MMessagePanel()
  {
    super( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    jbInit();
  }

  private void jbInit()
  {
    this.setViewportView( messagePanel );
    messagePanel.setEditable( false );
    MMessage.addMessageListener( new MiMessageListener()
    {
      public void messagePosted( final int priority, final String message )
      {
        EventQueue.invokeLater( new Runnable()
        {
          public void run()
          {
            String buffer = messagePanel.getText();
            messagePanel.setText( null == buffer ? message : buffer + "\n" + message );
          }
        });
      }
    });
  }
}
