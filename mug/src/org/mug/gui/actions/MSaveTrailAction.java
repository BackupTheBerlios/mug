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

public class MSaveTrailAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MSaveTrailAction.class );

  private static final String ACTION_TEXT = "save trail";
  private static final Icon   ACTION_ICON = MIconData.wtpart_ICON;
  protected static MAbstractAction instance;

  public MSaveTrailAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MSaveTrailAction() : instance );
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
//
//    MTrailEvent trailEvent = new MTrailEvent( MGUISession.class );
//    MTrailEvent trailEvent1 = new MTrailEvent( "getActiveModel" );
//    trailEvent.add( name, data );

//    MTrailWriter.flush( "trail"+System.currentTimeMillis()+".xml" );

  }
}
