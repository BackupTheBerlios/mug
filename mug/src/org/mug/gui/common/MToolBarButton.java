/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import org.apache.log4j.Logger;
import org.jdom.Element;
import java.awt.Component;
import org.reflector.Reflector;

public class MToolBarButton extends MAbstractButton
{
  private final static Logger log = Logger.getLogger( MToolBarButton.class );

  private MToolBarButton( MAbstractAction action )
  {
    super( action );
  }

  public static Component create( MAbstractAction action )
  {
    return ( null != action ? new MToolBarButton( action ) : null );
  }

  public static Component create( Element buttonElement )
  {
    String buttonClass = buttonElement.getAttributeValue( "componentClass" );
    if( !MToolBarButton.class.getName().equals( buttonClass ) )
      return ( null );
    String actionClass = buttonElement.getAttributeValue( "actionClass" );
    MAbstractAction action = (MAbstractAction)Reflector.invokeStaticGetInstanceMethod( actionClass );
    return ( create( action ) );
  }
}
