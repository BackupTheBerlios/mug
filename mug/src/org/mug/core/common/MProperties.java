/* -------------------------------------------------------------------------- */
/* Date         Version	     Name            Description of modification     */
/* ========================================================================== */

package org.mug.core.common;

import java.util.Properties;

public class MProperties extends Properties
{
  public MProperties()
  {
  }

  public MProperties( Properties defaults )
  {
    super( defaults );
  }

  public synchronized Object put( Object key, Object value )
  {
    String sKey = ( ( String ) key ).trim();
    String sValue = ( ( String ) value ).trim();
    String newValue = processValue( sKey, sValue );
    return( super.put( key, newValue ) );
  }

  public synchronized Object setProperty( String key, String value )
  {
    return( put( key, value ) );
  }

  private String processValue( String key, String value )
  {
    int lastIndex = value.length();
    int index;

    for( ; ( index = value.lastIndexOf( '$', lastIndex ) ) >= 0;
         lastIndex = index - 1 )
    {
      int start = value.indexOf( '(', index ) + 1;
      int end = value.indexOf( ')', index );
      if( start < index || end < start )
      {
        continue;
      }

      String name = value.substring( start, end ).trim();
      String oldValue = super.getProperty( name );
      if( null == oldValue )
        oldValue = System.getProperty( name );
      if( null == oldValue )
        continue;
      value = value.substring( 0, index ) + oldValue.trim() + value.substring( end + 1 );
    }
    return( value.trim() );
  }
}
