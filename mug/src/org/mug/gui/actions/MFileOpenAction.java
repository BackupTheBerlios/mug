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

public class MFileOpenAction extends MAbstractAction
{
  private final static Logger log = Logger.getLogger( MFileOpenAction.class );

  private static final String ACTION_TEXT = "openFileMenuItem_Text";
  private static final Icon   ACTION_ICON = MIconData.Open24_ICON;
  protected static MAbstractAction instance;

  private MFileOpenAction()
  {
    super( ACTION_TEXT, ACTION_ICON );
  }

  public static MAbstractAction getInstance()
  {
    return ( null == instance ? instance = new MFileOpenAction() : instance );
  }

//------------------------------------------------------------------------------

  private static String FILE_PATH_TAG = "FilePath";

  public void actionPerformed( ActionEvent event )
  {
    super.reportActionPerformed();
    String fileName = "some path";
    run( fileName );
  }

//------------------------------------------------------------------------------

  public void run( String fileName )
  {
//    MTrailWriter.write( this.getClass(), FILE_PATH_TAG, fileName );
    System.out.println( "Opening file with name>>" + fileName + "<<");
    MiModel model =  new MiModel()
    {
      public void createModel( MiSession miSession ){}

      public void destroy(){}

      public String getName(){ return ( "aasdasd" ); }

      public void addModelListener( MiModel.ModelListener listener ){}
      public void removeModelListener( MiModel.ModelListener listener ){}
    };
    MGUISession.getInstance().add( model );
  }
}
