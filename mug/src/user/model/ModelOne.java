/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package user.model;

import org.mug.core.common.MModelAdapter;
import org.mug.core.interfaces.MiModel;

public class ModelOne implements MiModel
{
  private int intValueONE;
  private int intValueTWO;

  public ModelOne()
  {
    intValueONE = intValueTWO = 0;
  }

  public int getValueONE()
  {
    return ( this.intValueONE );
  }

  public void setValueONE( int valueONE )
  {
    if( intValueONE == valueONE )
      return;
    this.intValueONE = valueONE;
    modelAdapter.fireChanged();
  }

  public int getValueTWO()
  {
    return ( this.intValueTWO );
  }

  public void setValueTWO( int valueTWO )
  {
    if( intValueTWO == valueTWO )
      return;
    this.intValueTWO = valueTWO;
    modelAdapter.fireChanged();
  }

  public int getSum()
  {
    return ( this.intValueONE + this.intValueTWO );
  }

  public String getName()
  {
    return "";
  }
//------------------------------------------------------------------------------
  private MModelAdapter modelAdapter = new MModelAdapter();

  public void destroy()
  {
    modelAdapter.destroy();
  }

  public void addModelListener( MiModel.ModelListener listener )
  {
    modelAdapter.addModelListener( listener );
  }

  public void removeModelListener( MiModel.ModelListener listener )
  {
    modelAdapter.removeModelListener( listener );
  }
}
