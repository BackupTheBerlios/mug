/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import javax.swing.*;
import org.apache.log4j.Logger;
import java.awt.Component;
import org.jdom.Element;
import java.awt.Container;
import org.reflector.Reflector;

public class MMenuItem extends JMenuItem
{
  private final static Logger log = Logger.getLogger( MMenuItem.class );

  private MMenuItem( Action action )
  {
    super( action );
  }

  public void reassignHotkeys()
  {
    Container parent = this.getParent();
    if( parent instanceof MMenu )
      ((MMenu)parent).reassignHotkeys();
  }

  public static Component create( MAbstractAction action )
  {
    return ( null != action ? new MMenuItem( action ) : null );
  }

  public static Component create( Element menuItemElement )
  {
    String menuItemClass = menuItemElement.getAttributeValue( "componentClass" );
    if( !MMenuItem.class.getName().equals( menuItemClass ) )
      return ( null );
    String actionClass = menuItemElement.getAttributeValue( "actionClass" );
    MAbstractAction action = (MAbstractAction)Reflector.invokeStaticGetInstanceMethod( actionClass );
    return ( create( action ) );
  }
}
