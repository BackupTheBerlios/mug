/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.interfaces;

public interface MiSession
{
  public interface LifeCycleListener
  {
    public void opened( MiModel model );
    public void selected( MiModel model );
    public void closed( MiModel model );
  }

  public void addLifeCycleListener( LifeCycleListener listener );
  public void removeLifeCycleListener( LifeCycleListener listener );
}
