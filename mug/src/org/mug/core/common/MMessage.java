/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.common;

import org.apache.log4j.*;

import java.util.*;
import org.mug.core.interfaces.MiMessageListener;

public class MMessage
{
  private static final Logger log = Logger.getLogger( MMessage.class );

  private static ArrayList messageListeners = new ArrayList();

//  private MMessage()
//  {
//  }

  public static boolean addMessageListener( MiMessageListener listener )
  {
    synchronized( messageListeners )
    {
      return ( messageListeners.add( listener ) );
    }
  }

  public static boolean removeMessageListener( MiMessageListener listener)
  {
    synchronized( messageListeners )
    {
      return ( messageListeners.remove( listener ) );
    }
  }

  public static void post( int priority, String message )
  {
    synchronized( messageListeners )
    {
      Iterator iterator = messageListeners.iterator();
      while( iterator.hasNext() )
      {
        MiMessageListener listener = (MiMessageListener)iterator.next();
        if( null != listener )
          listener.messagePosted( priority, message );
      }
    }
  }

  public static void post( String msg )
  {
    post( MiMessageListener.MESSAGE, msg );
  }

  public static void postWarning( String msg )
  {
    post( MiMessageListener.WARNING, msg );
  }

  public static void postError( String msg )
  {
    post( MiMessageListener.ERROR, msg );
  }

  public static void postStatus( String msg )
  {
    post( MiMessageListener.STATUS, msg );
  }

//  public static void initSrfcLogAppender()
//  {
//    Logger.getLogger("com.ptc.sr.srfc").addAppender(new SrfcLog4jAppender());
//  }
//
//  /**
//   * Redirect log4j messages to the SrfcMessage
//   */
//  private static class SrfcLog4jAppender extends AppenderSkeleton
//  {
//    public SrfcLog4jAppender()
//    {
//      setThreshold(Priority.ERROR);
//      //
//    }
//
//    protected void append(LoggingEvent ev)
//    {
//      SrfcMessage.put(SriMsgListener.ERROR, ev.getRenderedMessage());
//    }
//
//    public boolean requiresLayout()
//    {
//      return false;
//    }
//
//    public void close()
//    {
//    }
//  }
}
