/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import javax.swing.*;
import java.awt.Component;
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.mug.core.common.MConfig;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Iterator;
import org.jdom.Element;
import org.mug.gui.MIconData;
import org.mug.core.common.MMessage;
import org.mug.core.util.MXMLUtil;
import org.reflector.Reflector;

public class MMenu extends JMenu
{
  private final static Logger log = Logger.getLogger( MMenu.class );

  static String resourseBundle = MConfig.getProperty( "application.localization.bundle" );
  static ResourceBundle res = ResourceBundle.getBundle( resourseBundle );

  private HashSet m_HotkeysInUse = null;

  private MMenu( MAbstractAction action )
  {
    super( action );
  }

  private MMenu( String menuText )
  {
    super( menuText );
  }

  public void reassignHotkeys()
  {
    // Enumerate all menu items and assign the
    // first character as hotkey
    //
    m_HotkeysInUse = new HashSet();
    Component[] array = this.getMenuComponents();

    for(int i = 0; i < array.length; i++)
    {
      if( array[i] instanceof MMenuItem )
      {
        String text = ((MMenuItem)array[i]).getText();
        char hotkey = getValidHotkey(text);
        ((MMenuItem)array[i]).setMnemonic(hotkey);
      }
    }
  }

  private char getValidHotkey(String menu_text)
  {
    for(int i = 0; i < menu_text.length(); i++)
    {
      char ch = menu_text.charAt(i);

      if( Character.isLetter(ch) && !m_HotkeysInUse.contains(Integer.toString(ch)))
      {
        m_HotkeysInUse.add(Integer.toString(ch));
        return ch;
      }
    }

    // All letters of this menu text are already in use as hotkeys:
    // Return the first character anyway (Note that this will cause
    // duplication)
    //
    return menu_text.charAt(0);
  }

  private static MMenu createMMenuImpl( Element menuElement )
  {
    String actionClass = menuElement.getAttributeValue( "actionClass" );
    if( null != actionClass )
    {
      MAbstractAction action = (MAbstractAction)Reflector.invokeStaticGetInstanceMethod( actionClass );
      return ( new MMenu( action ) );
    }
    String resourceText = menuElement.getAttributeValue( "resourceText" );
    if( null != resourceText )
    {
      String menuText = res.getString( resourceText );
      return ( new MMenu( menuText ) );
    }
    return ( null );
  }

  public static Component create( Element menuElement )
  {
    String toolbarClass = menuElement.getAttributeValue( "componentClass" );
    if( !MMenu.class.getName().equals( toolbarClass ) )
      return ( null );
    MMenu menu = createMMenuImpl( menuElement );
    if( null == menu )
      return ( null );
    List buttonList = menuElement.getChildren();
    for( Iterator it = buttonList.iterator(); it.hasNext(); )
    {
      Element element = (Element)it.next();
      String elementName = element.getName();
      if( "MenuSeparator".equalsIgnoreCase( elementName ) )
      {
        menu.addSeparator();
        continue;
      }
      Component component = MHelper.createComponent( element );
      if( component instanceof MMenu && null == ((MMenu)component).getIcon() )
        ((MMenu)component).setIcon( MIconData.transp24_ICON );
      if( null != component )
        menu.add( component );
      else
        MMessage.postError( "Unable to create menu entry for " +
                             MXMLUtil.xmlToString( menuElement ) );
    }
    menu.reassignHotkeys();
    return ( menu );
  }
}
