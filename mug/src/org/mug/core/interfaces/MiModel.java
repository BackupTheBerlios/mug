/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.interfaces;

public interface MiModel
{
  public void destroy();

//---

  public interface ModelListener
  {
    public void changed();
    public void destroyed();
  }

//---

  public void addModelListener( MiModel.ModelListener listener );
  public void removeModelListener( MiModel.ModelListener listener );
}
