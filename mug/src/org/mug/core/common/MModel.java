/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.common;

import org.mug.core.interfaces.MiModel;
import java.util.ArrayList;
import java.util.Iterator;
import org.mug.core.interfaces.MiModel;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.mug.gui.MGUISession;
import org.reflector.Reflector;

public abstract class MModel implements MiModel
{
  private final static Logger log = Logger.getLogger( MModel.class );

  private ArrayList listenerList = new ArrayList();

  protected MModel()
  {
  }

  public void destroy()
  {
    fireDestroyed();
    listenerList.clear();
    listenerList = null;
  }

  public void invoked( Object[] args )
  {
    Class cls = MGUISession.getInstance().getActiveModel().getClass();
    String methodName = Reflector.getInvokedMethodName( cls );
    if( null == methodName )
      throw new IllegalArgumentException( "wrong value for first argument, should be called: invoked( this.getClass(), parameters )" );
    Class[] signature = new Class[ args.length ];
    for( int argIndex = 0; argIndex < args.length; argIndex++ )
      signature[ argIndex ] = args[ argIndex ].getClass();
    try
    {
      Method method = cls.getMethod( methodName, signature );
    }
    catch( Exception ex )
    {
      throw new IllegalArgumentException( "unable to locate a method " + methodName + "\nfor class " + cls.getName() );
    }
    String report = "invoked: " + cls.getName() + ":" + methodName + "( ";
    for( int argIndex = 0; argIndex < args.length; argIndex++ )
      report += signature[ argIndex ].getName() + " ";
    log.info( report + ")" );
  }

  protected void fireChanged()
  {
    for( Iterator it = listenerList.iterator(); it.hasNext(); )
    {
      Object listener = it.next();
      if( listener instanceof MiModel.ModelListener )
        ((MiModel.ModelListener)listener).changed();
    }
  }

  protected void fireDestroyed()
  {
    for( int index = 0; index < listenerList.size(); )
    {
      MiModel.ModelListener currentListener = (MiModel.ModelListener)listenerList.get( index );
      currentListener.destroyed();
      if( index < listenerList.size() && currentListener == listenerList.get( index ) )
        index++; //DP:user forgot to remove the ModelListener upon destroy
    }
  }

  public void addModelListener( MiModel.ModelListener listener )
  {
    listenerList.add( listener );
  }

  public void removeModelListener( MiModel.ModelListener listener )
  {
    listenerList.remove( listener );
  }
}
