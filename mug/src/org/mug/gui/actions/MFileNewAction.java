/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.actions;

import javax.swing.Icon;
import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;

import org.mug.gui.MIconData;
import org.mug.gui.common.MAbstractAction;
import org.mug.core.interfaces.MiModel;
import org.mug.core.interfaces.MiSession;
import org.mug.gui.MGUISession;

public class MFileNewAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileNewAction.class );

  private static final String ACTION_TEXT = "newFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.New24_ICON;
  protected static MAbstractAction instance;

  private MFileNewAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileNewAction() : instance );
  }


//------------------------------------------------------------------------------

  private static String MODEL_NAME_TAG = "ModelName";

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    //data =getUIbdata
    String data = "some name";
    run( data );
  }

//------------------------------------------------------------------------------

  private void run( String data )
  {
//    MTrailWriter.write( this.getClass(), MODEL_NAME_TAG, data );
    System.out.println( "Creating new file with name>>" + data + "<<");
    MiModel model =  new MiModel()
    {
      public void createModel( MiSession miSession ){}

      public void destroy(){}

      public String getName(){ return "wwedwedwed"; }

      public void addModelListener( MiModel.ModelListener listener ){}
      public void removeModelListener( MiModel.ModelListener listener ){}
    };
    MGUISession.getInstance().add( model );
  }
}
