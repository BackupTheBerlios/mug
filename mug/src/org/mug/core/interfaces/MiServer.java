/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.core.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MiServer extends Remote
{
  public void execute( String[] args ) throws RemoteException;
}
