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

public class MFileExitAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileExitAction.class );

  private static final String ACTION_TEXT = "exitFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.transp24_ICON;
  protected static MAbstractAction instance;

  public MFileExitAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileExitAction() : instance );
  }

//------------------------------------------------------------------------------

  private void run()
  {
//    MTrailWriter.write( this.getClass() );
    System.out.println( ">> EXIT <<" );
    System.exit( 0 );
  }

//------------------------------------------------------------------------------

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    run();
  }
}
