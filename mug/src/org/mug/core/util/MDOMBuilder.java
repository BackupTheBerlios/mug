/* -------------------------------------------------------------------------- */
/* Date      Version  Name    Description of modification                     */
/* ========================================================================== */
package org.mug.core.util;

import org.apache.log4j.Logger;

import org.jdom.*;
import org.jdom.input.*;
import org.xml.sax.*;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MDOMBuilder
{
  private static final Logger log = Logger.getLogger( MDOMBuilder.class );

  private static final SAXBuilder builder = new SAXBuilder();

static
{
  builder.setErrorHandler( new ErrorHandler()
  {
    public void warning( SAXParseException spex )
    {
      log.warn( "Warning", spex );
    }
    public void error( SAXParseException spex )
    {
      log.error( "Error", spex );
    }
    public void fatalError( SAXParseException spex ) throws SAXException
    {
      log.fatal( "Fatal error", spex );
    }
  });
}

  public static Document build( String xs )
  {
    try { return ( builder.build( xs ) ); }
    catch( Exception ex ) { log.error( ex ); }
    return ( null );
  }

  public static Document build( InputStream is )
  {
    try { return ( builder.build( is ) ); }
    catch( Exception ex ) { log.error( ex ); }
    return ( null );
  }

  public static Document build( InputStreamReader reader )
  {
    try { return ( builder.build( reader ) ); }
    catch( Exception ex ) { log.error( ex ); }
    return ( null );
  }
}
