/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.actions;

import javax.swing.Icon;
import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;

import org.mug.gui.MIconData;
import org.mug.gui.common.MAbstractAction;
import org.mug.core.common.MConfig;
import org.mug.core.interfaces.MiModel;
import org.mug.gui.MGUISession;
import org.mug.gui.MConstant;

public class MFileCloseAllAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileCloseAllAction.class );

  private static final String ACTION_TEXT = "closeAllFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.CloseComponent24_ICON;
  protected static MAbstractAction instance;

  public MFileCloseAllAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileCloseAllAction() : instance );
  }

//------------------------------------------------------------------------------

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    run();
  }

//------------------------------------------------------------------------------

  private void run( )
  {
//    MTrailWriter.write( this.getClass() );
    MGUISession session = MGUISession.getInstance();
    MiModel model = session.getActiveModel();
    while( null != model )
    {
      session.remove( model );
      model = session.getActiveModel();
    }
  }
}
