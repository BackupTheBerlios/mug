/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.actions;

import javax.swing.Icon;
import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;

import org.mug.gui.MIconData;
import org.mug.gui.common.MAbstractAction;
import org.mug.gui.MGUISession;

public class MFileCloseAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileCloseAction.class );

  private static final String ACTION_TEXT = "closeFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.CloseComponent24_ICON;
  protected static MAbstractAction instance;

  public MFileCloseAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileCloseAction() : instance );
  }

//------------------------------------------------------------------------------

  private void run( )
  {
//    MTrailWriter.write( this.getClass() );
    System.out.println( ">> + close" );
    MGUISession session = MGUISession.getInstance();
    session.remove( session.getActiveModel() );
  }

//------------------------------------------------------------------------------

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    run();
  }
}
