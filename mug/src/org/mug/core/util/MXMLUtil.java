package org.mug.core.util;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.jdom.*;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;

public class MXMLUtil
{
  private static final Logger log = Logger.getLogger( MXMLUtil.class );

  public static final String    XML_NAMESPACE_URL = "http://org.mug.net";
  public static final Namespace XML_NAMESPACE = Namespace.getNamespace( XML_NAMESPACE_URL );

  public static final String    GLOBAL_XML_NAMESPACE_URL = "http://www.w3.org/2001/XMLSchema-instance";
  public static final Namespace GLOBAL_XML_NAMESPACE = Namespace.getNamespace( "xsi", GLOBAL_XML_NAMESPACE_URL );

  public static final String    XSD_URL = "http://blah...blah...blah/Schemas/schema.xsd";
  public static final Attribute XML_SCHEMALOCATION_ATTRIBUTE =
      new Attribute( "schemaLocation", XML_NAMESPACE_URL +" "+ XSD_URL, GLOBAL_XML_NAMESPACE );

  public static String xmlToStr(Element element) throws IOException
  {
    XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
    StringWriter writer = new StringWriter();
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    xout.output( element, writer );
    return ( writer.toString() );
  }

  public static boolean xmlToFile( File file, Element element )
  {
    try
    {
      XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
      FileWriter writer = new FileWriter( file );
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      xout.output( element, writer );
      writer.close();
      return ( true );
    }
    catch( Exception ex )
    {}
    return ( false );
  }

  public static boolean xmlToOutputStream( OutputStream stream, Element element )
  {
    try
    {
      XMLOutputter xout = new XMLOutputter( Format.getPrettyFormat() );
      Writer writer = new BufferedWriter( new OutputStreamWriter( stream ) );
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      xout.output( element, writer );
      return ( true );
    }
    catch( Exception ex )
    {}
    return ( false );
  }

  public static String xmlToString(Element element)
  {
    try
    {
      return ( xmlToStr(element) );
    }
    catch ( IOException ex)
    {
      return ( "<EXCEPTION_THROWN>\n" + ex.getMessage()+ "\n</EXCEPTION_THROWN>" );
    }
  }

//------------------------------------------------------------------------------

  private static final String PATH_DELIMITER = "/";

  private static final String EMPTY = "";

  public static String getValue( Element root, String path )
  {
    if( null == root )
      return ( EMPTY );
    StringTokenizer tokenizer = new StringTokenizer(path,PATH_DELIMITER);
    Element current = root;

    while(tokenizer.hasMoreTokens())
    {
      String token = (String)tokenizer.nextToken().trim();
      if( token.startsWith("@") )
      {
        if( tokenizer.hasMoreTokens() )
          throw new RuntimeException("Error: bad path :"+path);
        String attribute = token.substring( 1 );
        String value = current.getAttributeValue( attribute );
        return ( null == value ? EMPTY : value.trim() );
      }
      current = current.getChild( token );
      if( null == current )
        return ( EMPTY );
    }
    String value = current.getText();
    return ( null == value ? EMPTY : value.trim() );
  }

  public static Element getElement( Element root, String path )
  {
    if( null == root )
      return ( null );
    StringTokenizer tokenizer = new StringTokenizer( path, PATH_DELIMITER );
    Element current = root;

    while(tokenizer.hasMoreTokens())
    {
      String token = (String)tokenizer.nextToken().trim();
      if( token.startsWith( "@" ) )
        throw new RuntimeException( "Error: bad path :" + path );
      current = current.getChild( token );
      if( null == current )
        return ( null );
    }
    return ( current );
  }

//  public static void setValue(Element root, String path, String value)
//  {
//    if( null == root )
//      return;
//    StringTokenizer tokenizer = new StringTokenizer(path,PATH_DELIMITER);
//    Element current = root;
//
//    while(tokenizer.hasMoreTokens())
//    {
//      String token = (String)tokenizer.nextToken().trim();
//      if( token.startsWith("@") )
//      {
//        if( tokenizer.hasMoreTokens() )
//          throw new RuntimeException("Error: bad path :"+path);
//        String attribute = token.substring(1);
//        current.setAttribute( attribute, value );
//        return;
//      }
//      Element next = current.getChild( token, XML_NAMESPACE );
//      if( null == next )
//      {
//        next = new Element( token, XML_NAMESPACE );
//        current.addContent( next );
//      }
//      current = next;
//    }
//    current.setText( value );
//  }

/*
  <SunrisePalette xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://www.ptc.com/Sunrise http://rdweb.ptc.com/home/ttt/Schemas/sunrise.xsd"
                  xmlns="http://www.ptc.com/Sunrise">
*/
}
