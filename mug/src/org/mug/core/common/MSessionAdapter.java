/* -------------------------------------------------------------------------- */
/* Date     Version  Name    Description of modification                      */
/* ========================================================================== */

package org.mug.core.common;

import org.apache.log4j.Logger;
import java.util.*;
import org.mug.core.interfaces.MiSession;
import org.mug.core.interfaces.MiModel;

public class MSessionAdapter implements MiSession
{
  private static final Logger log = Logger.getLogger( MSessionAdapter.class );

  private ArrayList modelListenerList = new ArrayList();

  public void addLifeCycleListener( MiSession.LifeCycleListener listener )
  {
    modelListenerList.add( listener );
  }

  public void removeLifeCycleListener( MiSession.LifeCycleListener listener )
  {
    modelListenerList.remove( listener );
  }

//--[Session event handling]-----------------

  protected void fireOpened( MiModel model )
  {
    log.debug("[+]opened()");

    for( Iterator it = modelListenerList.iterator(); it.hasNext(); )
      ((MiSession.LifeCycleListener) it.next()).opened( model );

    log.debug("[-]opened()");
  }

  protected void fireSelected( MiModel model )
  {
    for( Iterator it = modelListenerList.iterator(); it.hasNext(); )
      ((MiSession.LifeCycleListener) it.next()).selected( model );
  }

  protected void fireClosed( MiModel model )
  {
    log.debug("[+]closed()");

    for(Iterator it = modelListenerList.iterator(); it.hasNext();)
      ( (MiSession.LifeCycleListener)it.next() ).closed( model );

    model.destroy();

    log.debug("[-]closed()");
  }
}
