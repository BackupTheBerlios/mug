/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import java.awt.Component;
import javax.swing.*;
import org.mug.gui.MGUISession;
import org.mug.core.interfaces.MiModel;
import org.mug.gui.MIconData;
import org.apache.log4j.Logger;
import org.jdom.Element;

public class MModelChooser extends JComboBox
{
  private final static Logger log = Logger.getLogger( MModelChooser.class );

  private ThisComboBoxRenderer renderer = new ThisComboBoxRenderer();

  private MModelChooser( MGUISession guiSession )
  {
    super( guiSession );
    jbInit();
  }

  private void jbInit()
  {
    this.setMaximumRowCount( 10 );
    this.setBorder( BorderFactory.createLoweredBevelBorder() );
    this.setAlignmentX( 0.0f );
    this.setEditable( false );
    this.setLightWeightPopupEnabled( true );
    this.setRenderer( renderer );
  }

  public static Component create()
  {
    MGUISession guiSession = MGUISession.getInstance();
    if( null == guiSession )
    {
      log.error(" unable to create model chooser, reason: no guisession available ");
      return (null);
    }
    MModelChooser modelChooser = new MModelChooser( guiSession );
    return ( modelChooser );
  }

  public static Component create( Element modelChooserElement )
  {
    String modelChooserClass = modelChooserElement.getAttributeValue( "componentClass" );
    if( !MModelChooser.class.getName().equals( modelChooserClass ) )
      return ( null );
    return ( create() );
  }

  private class ThisComboBoxRenderer extends DefaultListCellRenderer
  {
    public Component getListCellRendererComponent( JList list, Object value,
                        int index,  boolean isSelected,  boolean cellHasFocus)
    {
      super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );

      if( value instanceof MiModel )
      {
        this.setText( ((MiModel)value).toString() );//DP:redo:.getName() );
        this.setIcon( MIconData.COMPONENT_DOCUMENT_18X16_ICON );
      }
      return ( this ) ;
    }
  }
}
