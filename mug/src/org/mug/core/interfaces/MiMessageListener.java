/* -------------------------------------------------------------------------- */
/* Date         Version         Name      Description of modification         */
/* ========================================================================== */

package org.mug.core.interfaces;

public interface MiMessageListener
{
  // message priorities
  public static int MESSAGE = 1;
  public static int WARNING = 2;
  public static int ERROR   = 3;
  public static int STATUS  = 4;

  public void messagePosted( int priority, String message ); //DP: --> messagePosted
}
