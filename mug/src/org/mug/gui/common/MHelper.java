/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import org.apache.log4j.Logger;
import org.jdom.Element;
import java.awt.Component;
import org.reflector.Reflector;

public class MHelper
{
  private final static Logger log = Logger.getLogger( MHelper.class );
  private static final String    CREATE_METHOD_NAME = "create";
  private static final Class[]   SIGNATURE          = new Class[]{ Element.class };

  public static Component createComponent( Element componentElement )
  {
    String componentClass = componentElement.getAttributeValue("componentClass");
    if( null == componentClass )
      return ( null );
    Object[] parameters = new Object[] { componentElement };
    return ( (Component)Reflector.invokeStaticMethod( componentClass, CREATE_METHOD_NAME, SIGNATURE, parameters ) );
  }
}
