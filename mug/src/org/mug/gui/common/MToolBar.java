/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import java.util.List;
import java.awt.*;

import javax.swing.*;
import org.apache.log4j.Logger;
import org.jdom.Element;
import java.util.Iterator;
import org.mug.core.util.MXMLUtil;
import org.mug.core.common.MMessage;

public class MToolBar extends JToolBar
{
  private final static Logger log = Logger.getLogger( MToolBar.class );

  private Dimension separatorSize = new Dimension( 3, 28 );

  protected MToolBar()
  {
    this( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
  }

  protected MToolBar( LayoutManager layout )
  {
    super.setLayout( layout );
    super.setBorder( null );
    super.setMargin( new Insets( 0, 0, 0, 0 ) );
  }

  public static Component create( Element toolbarElement )
  {
    String toolbarClass = toolbarElement.getAttributeValue( "componentClass" );
    if( !MToolBar.class.getName().equals( toolbarClass ) )
      return ( null );
    MToolBar toolBar = new MToolBar();
    List buttonList = toolbarElement.getChildren();
    for( Iterator it = buttonList.iterator(); it.hasNext(); )
    {
      Element element = (Element)it.next();
      String elementName = element.getName();
      if( "ToolBarSeparator".equalsIgnoreCase( elementName ) )
      {
        toolBar.addSeparator();
        continue;
      }
      Component component = MHelper.createComponent( element );
      if( null != component )
          toolBar.add( component );
      else
        MMessage.postError( "Unable to create menu entry for " +
                             MXMLUtil.xmlToString( toolbarElement ) );
    }
    return ( toolBar );
  }

  public void addSeparator()
  {
    super.addSeparator( separatorSize );
  }
}
