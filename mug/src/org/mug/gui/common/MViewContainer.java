/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import javax.swing.JPanel;
import java.awt.Component;
import org.mug.core.interfaces.MiModel;

import org.apache.log4j.Logger;
import org.mug.core.interfaces.MiSession;
import java.util.Hashtable;
import org.mug.gui.MViewRegistry;

public class MViewContainer extends JPanel implements MiSession.LifeCycleListener
{
  private final static Logger log = Logger.getLogger( MViewContainer.class );

  private MCardLayout layout;
  private String viewContainerID;

  private MViewContainer( String viewContainerID )
  {
    super( new MCardLayout() );
    this.viewContainerID = viewContainerID;
    this.layout = (MCardLayout)this.getLayout();
  }

  private static Hashtable collection = new Hashtable();

  public static MViewContainer getInstance( String viewContainerID )
  {
    MViewContainer instance = (MViewContainer)collection.get( viewContainerID );
    if( null == instance )
    {
      instance = new MViewContainer( viewContainerID );
      collection.put( viewContainerID, instance );
    }
    return ( instance );
  }

//----------------------------------

  public void opened( MiModel model )
  {
    Component view = MViewRegistry.createView( this.viewContainerID, model );
    super.add( view, model );
    this.validate();
  }

  public void selected( MiModel model )
  {
    layout.show( this, model );
    this.validate();
  }

  public void closed( MiModel model )
  {
    Component component = layout.getComponent( model );
    if( null == component )
      return;
    super.remove( component );
    this.repaint();
  }
}
