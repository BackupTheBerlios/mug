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

public class MMenuBar extends JMenuBar
{
  private final static Logger log = Logger.getLogger( MMenuBar.class );

  private MMenuBar( )
  {
  }

  public static Component create( Element menubarElement )
  {
//    log.info("Creating toolbar : " + menubarElement.getName() );
    String toolbarClass = menubarElement.getAttributeValue( "componentClass" );
    if( !MMenuBar.class.getName().equals( toolbarClass ) )
      return ( null );
    MMenuBar menuBar = new MMenuBar();
    List buttonList = menubarElement.getChildren();
    for( Iterator it = buttonList.iterator(); it.hasNext(); )
    {
      Element element = (Element)it.next();
      String elementName = element.getName();
      Component component = MHelper.createComponent( element );
      if( null != component )
          menuBar.add( component );
      else
        MMessage.postError( "Unable to create menu entry for " +
                             MXMLUtil.xmlToString( menubarElement ) );
    }
//    log.info("DONE: toolbar : " + menubarElement.getName() );
    return ( menuBar );
  }
}
