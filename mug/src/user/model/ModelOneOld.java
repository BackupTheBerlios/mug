/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package user.model;

import org.mug.core.common.MModel;

public class ModelOneOld extends MModel
{
  private int intValueONE;
  private int intValueTWO;

  public ModelOneOld()
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
    super.fireChanged();
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
    super.fireChanged();
  }

  public int getSum()
  {
    return ( this.intValueONE + this.intValueTWO );
  }

  public String getName()
  {
    return "";
  }
}
