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
import org.mug.gui.MConstant;
import org.mug.gui.MGUISession;
import org.mug.core.interfaces.MiModel;

public class MFileSaveAllAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileSaveAllAction.class );

  private static final String ACTION_TEXT = "saveAllFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.SaveAll24_ICON;
  protected static MAbstractAction instance;

  public MFileSaveAllAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileSaveAllAction() : instance );
  }

//------------------------------------------------------------------------------

  private static String FILE_PATH_TAG = "FilePath";

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    run();
  }

//------------------------------------------------------------------------------

  private void run()
  {
//    MTrailWriter.write( this.getClass() );
    MGUISession session = MGUISession.getInstance();
    MiModel[] models = session.getModels();
    for( int index = 0; index < models.length; index++ )
    {
//      session.save( models[index] );
//???      models[index].save();
    }
  }
}
