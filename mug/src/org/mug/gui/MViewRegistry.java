/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui;

import org.mug.core.interfaces.MiModel;
import java.awt.Component;
import org.apache.log4j.Logger;
import javax.swing.JTextArea;
import org.mug.core.util.MDOMBuilder;
import java.io.InputStream;
import org.jdom.Element;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.net.URL;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;
import org.reflector.Reflector;

public class MViewRegistry
{
  private final static Logger log = Logger.getLogger( MViewRegistry.class );

  private static Hashtable viewRegistry = new Hashtable();

static
{
  rescan();
}

  static public Component createView( String viewContainerID, MiModel model )
  {
    if( null == viewContainerID )
      viewContainerID = "";
    String key = viewContainerID + "(" + model.getClass().getName() + ")";
    String viewClassName = (String)viewRegistry.get( key );
    Object view = Reflector.invokeStaticMethod( viewClassName, "getInstance", new Class[]{ model.getClass() }, new Object[]{ model } );
    if( view instanceof Component )
      return ( (Component)view );

    JTextArea defaultView = new JTextArea("No view available for model\n <" + model.getClass() +
                            ">\n in container\n <" + viewContainerID + ">");
    defaultView.setEnabled( false );
    return ( defaultView );
  }

  public static boolean addURL( URL url )
  {
    try
    {
      InputStream inputStream = ((URL)url).openStream();
      Element rootElement = MDOMBuilder.build( inputStream ).getRootElement();
      List modelElementList = rootElement.getChildren( "Model" );
      for( Iterator it = modelElementList.iterator(); it.hasNext(); )
        processModelElement( (Element)it.next() );
      return ( true );
    }
    catch( Exception ex )
    {
      ex.printStackTrace();
    }
    return ( false );
  }

  public static void rescan()
  {
    try
    {
      Enumeration enumeration = ((URLClassLoader)MViewRegistry.class.getClassLoader()).findResources( "custom.xml" );
      while( enumeration.hasMoreElements() )
        addURL( (URL)enumeration.nextElement() );
    }
    catch( Exception ex )
    {
      ex.printStackTrace();
    }
  }

  private static void processModelElement( Element modelElement )
  {
    String modelClassName = modelElement.getAttributeValue( "class", "" );

    List viewElementList = modelElement.getChildren( "View" );
    for( Iterator it = viewElementList.iterator(); it.hasNext(); )
    {
      Element viewElement    = (Element)it.next();
      String viewContainerID = viewElement.getAttributeValue( "container", "" );
      String viewClassName   = viewElement.getAttributeValue( "viewClass", "" );
      String key = viewContainerID + "(" + modelClassName + ")";
      viewRegistry.put( key, viewClassName );
    }
  }
}
