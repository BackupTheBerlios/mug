/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.util.Iterator;

public class MStreamUtil
{
  public static String convertToString( InputStream inputStream ) throws Exception
  {
    StringBuffer report = new StringBuffer();
    char buff[] = new char[ 1024 ];
    Reader reader = new InputStreamReader( new BufferedInputStream( inputStream ) );
    for(;;)
    {
      int numRead = reader.read( buff, 0, buff.length );
      if( numRead < 0 )
        break;
      report.append( buff, 0, numRead );
    }
    return( report.toString() );
  }

  public static byte[] convertToByteArray( InputStream inputStream ) throws Exception
   {
     ArrayList holder = new ArrayList();
     int bytes = 0;
     byte buff[] = new byte[ 1024 ];
     BufferedInputStream stream = new BufferedInputStream( inputStream );
     for(;;)
     {
       int numRead = stream.read( buff, 0, buff.length );
       if( numRead < 0 )
         break;
       byte[] block = new byte[numRead];
       bytes += numRead;
       System.arraycopy( buff, 0, block, 0, numRead );
       holder.add( block );
     }
     byte[] result = new byte[bytes];
     int pos = 0;
     for( Iterator it = holder.iterator(); it.hasNext(); )
     {
       byte[] block = (byte[])it.next();
       System.arraycopy( block, 0, result, pos, block.length );
       pos += block.length;
     }
     return( result );
   }
}
