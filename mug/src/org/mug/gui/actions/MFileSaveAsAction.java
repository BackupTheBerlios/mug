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

public class MFileSaveAsAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileSaveAsAction.class );

  private static final String ACTION_TEXT = "saveAsFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.transp24_ICON;
  protected static MAbstractAction instance;

  public MFileSaveAsAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileSaveAsAction() : instance );
  }

//------------------------------------------------------------------------------

  private static String FILE_PATH_TAG = "FilePath";

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    //data =getUIbdata
    run();
  }

//------------------------------------------------------------------------------

  private void run()
  {
//    MTrailWriter.write( this.getClass() );
//    MiModel model = MApplication.getGUISession().getSelectedModel();
//    model.save(...)
  }
}
