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

public class MFileSaveAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileSaveAction.class );

  private static final String ACTION_TEXT = "saveFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.Save24_ICON;
  protected static MAbstractAction instance;

  public MFileSaveAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileSaveAction() : instance );
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
  //  MTrailWriter.write( this.getClass() );
//--[ business logic here ]-----------------------------------------------------
//    MiModel model = MApplication.getGUISession().getSelectedModel();
//    model.save(...)
  }
}
