/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package user.ui;

import user.model.ModelOne;
import java.awt.Component;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import org.mug.core.interfaces.MiModel;

public class ModellerOne extends JPanel implements MiModel.ModelListener
{
  private JTextField sumText = new JTextField();

  private ModelOne model;

  private ModellerOne( ModelOne model )
  {
    super( new BorderLayout() );
    this.model = model;
    jbInit();
  }

  public static Component getInstance( ModelOne model )
  {
    return ( new ModellerOne( model ) );
  }

  private void jbInit()
  {
    sumText.setEditable( false );
    sumText.setText( String.valueOf( model.getSum() ) );

    this.add( sumText, BorderLayout.CENTER );

    model.addModelListener( this );
  }

  public void changed()
  {
    sumText.setText( String.valueOf( model.getSum() ) );
  }

  public void destroyed()
  {
    this.destroy();
  }

  public void destroy()
  {
    model.removeModelListener( this );
  }
}
