/* -------------------------------------------------------------------------- */
/* Date     Version  Name    Description of modification                      */
/* ========================================================================== */

package org.mug.gui.common;

import org.apache.log4j.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import java.util.ResourceBundle;
import org.mug.core.common.MConfig;
import javax.swing.Action;
import org.mug.core.common.MMessage;

public abstract class MAbstractAction extends AbstractAction
{
  private final static Logger log = Logger.getLogger( MAbstractAction.class );

  static String resourseBundle = MConfig.getProperty( "application.localization.bundle" );
  static ResourceBundle res = ResourceBundle.getBundle( resourseBundle );

  public static final String SMALL_SELECTED_ICON = "SmallSelectedIcon";

  protected MAbstractAction( String key, Icon icon )
  {
    this( key, icon, icon );
  }

  protected MAbstractAction( String key, Icon icon, Icon selectedIcon )
  {
    super( key, icon );
    jbInit( key, icon, selectedIcon );
  }

  private void jbInit( String key, Icon icon, Icon selectedIcon )
  {
    putValue( this.SMALL_SELECTED_ICON, ( null == selectedIcon ? icon : selectedIcon ) );

    if( null == key )
      return;

    putValue( Action.ACTION_COMMAND_KEY, key );

    String description;
    try
    {
      description = res.getString( key );
    }
    catch( Exception ex )
    {
      System.out.println( "SYSTEM WARNING: no localization message available for key: " + key );
      description = key;
    }

    putValue( Action.NAME, description );
    putValue( Action.SHORT_DESCRIPTION, description );
    putValue( Action.LONG_DESCRIPTION, description );

    if( MConfig.getProperty( "application.useTrails", false ) )
    {

    }
  }

  public void reportActionPerformed()
  {
    MMessage.post( "Action performed :" + super.getValue( Action.NAME ) );
  }
}
