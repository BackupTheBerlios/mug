/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package user.ui;

import java.awt.Component;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.mug.core.interfaces.MiModel;
import user.model.ModelOne;

public class DesignerOne extends JPanel implements MiModel.ModelListener
{
  private JTextField valueOneText = new JTextField();
  private JTextField valueTwoText = new JTextField();
  private JTextField sumText = new JTextField();

  private ModelOne model;

  private DesignerOne(ModelOne model) {
    super(new BorderLayout());
    this.model = model;
    jbInit();
  }

  public static Component getInstance( ModelOne model )
  {
    return ( new DesignerOne( model ) );
  }

  private void jbInit()
  {
    valueOneText.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent event )
      {
        try
        {
          String data = valueOneText.getText();
//          MTrailWriter.write(DesignerOne.class, "ONE_TAG", data);
          DesignerOne.this.model.setValueONE( Integer.parseInt( data ) );
        }
        catch( Exception numberFormatException )
        {
          //DP: use old value in case of error
          valueOneText.setText( String.valueOf( DesignerOne.this.model.getValueONE() ) );
        }
      }
    });

    valueTwoText.addActionListener( new ActionListener()
    {
      public void actionPerformed( ActionEvent event )
      {
        try
        {
          String data = valueTwoText.getText();
//          MTrailWriter.write( DesignerOne.class, "TWO_TAG", data );
          model.setValueTWO( Integer.parseInt( data ) );
        }
        catch( Exception numberFormatException )
        {
          //DP: use old value in case of error
          valueTwoText.setText( String.valueOf( model.getValueTWO() ) );
        }
      }
    });

    sumText.setEditable( false );

    this.add( valueOneText, BorderLayout.NORTH );
    this.add( sumText, BorderLayout.CENTER );
    this.add( valueTwoText, BorderLayout.SOUTH );
    model.addModelListener( this );
    this.changed();//update UI
  }

  public void setONEValue( String oneValueString ) // trail entry point
  {
    if( null != oneValueString )
    {
      valueOneText.setText( oneValueString );
      valueOneText.postActionEvent();// will invoke actionPerformed( ActionEvent ), see above
    }
  }

  public void setTWOValue( String twoValueString ) // trail entry point
  {
    if( null != twoValueString )
    {
      valueTwoText.setText( twoValueString );
      valueTwoText.postActionEvent();// will invoke actionPerformed( ActionEvent ), see above
    }
  }

  public void changed() //DP:update ui if model was chenged
  {
    valueOneText.setText( String.valueOf( model.getValueONE() ) );
    valueTwoText.setText( String.valueOf( model.getValueTWO() ) );
    sumText.setText( String.valueOf( model.getSum() ) );
  }

  public void destroyed()
  {
    this.destroy();
  }

  public void destroy()
  {
    Container container = this.getParent();
    if( null != container ) //if we need to destroy just this view, not a model itself
      container.remove( this );
    model.removeModelListener( this );
  }
}
