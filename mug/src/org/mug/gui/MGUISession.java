/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui;

import org.mug.core.common.MSessionAdapter;
import org.apache.log4j.Logger;
import javax.swing.DefaultComboBoxModel;
import org.mug.core.interfaces.MiModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

public class MGUISession extends MSessionAdapter implements ComboBoxModel
{
  private final static Logger log = Logger.getLogger( MGUISession.class );

  private DefaultComboBoxModel openedModels = new DefaultComboBoxModel();

  private static MGUISession instance;

  private MGUISession()
  {
  }

  public static MGUISession getInstance()
  {
    return ( null == instance ? instance = new MGUISession() : instance  );
  }

  public void add( MiModel model )
  {
    if( null == model )
      return;
    openedModels.addElement( model );
    super.fireOpened( model );
    this.setSelectedItem( model );
  }

  public MiModel getActiveModel()
  {
    return ( (MiModel)openedModels.getSelectedItem() );
  }

  public MiModel[] getModels()
  {
    MiModel[] result = new MiModel[ openedModels.getSize() ];
    for(int index = 0; index < result.length; )
      result[ index ] = (MiModel)openedModels.getElementAt( index );
    return ( result );
  }

  public void remove( MiModel model )
  {
    if( null == model )
      return;
    openedModels.removeElement( model );
    super.fireClosed( model );
    super.fireSelected( this.getActiveModel() );
  }

//--[ Incapsulation for interface ComboBoxModel ]-------------------------------

  public void setSelectedItem( Object anItem )
  {
    openedModels.setSelectedItem( anItem );
    if( anItem instanceof MiModel )
      super.fireSelected( (MiModel)anItem );
  }

  public Object getSelectedItem()
  {
    return ( openedModels.getSelectedItem() );
  }

  public int getSize()
  {
    return ( openedModels.getSize() );
  }

  public Object getElementAt( int index )
  {
    return ( openedModels.getElementAt( index ) );
  }

  public void addListDataListener( ListDataListener listener )
  {
    openedModels.addListDataListener( listener );
  }

  public void removeListDataListener( ListDataListener listener )
  {
    openedModels.removeListDataListener( listener );
  }

  //--[ Incapsulation for interface ComboBoxModel ]-------------------------------

}
