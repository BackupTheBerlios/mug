/* -------------------------------------------------------------------------- */
/* Date         Version         Name            Description of modification   */
/* ========================================================================== */

package org.mug.gui.common;

import java.awt.*;
import java.util.Vector;
import org.apache.log4j.Logger;

public class MCardLayout implements LayoutManager2
{
  private final static Logger log = Logger.getLogger( MAbstractButton.class );

  private MCard currentCard;
  private Vector holder = new Vector();
  private int hgap = 0;
  private int vgap = 0;

  public static class MCard
  {
    public final Component component;
    public final Object    key;
    public MCard( Component component, Object key )
    {
      this.component = component;
      this.key = key;
    }
  }

  public MCardLayout()
  {
  }

  public Dimension maximumLayoutSize( Container target  )
  {
    return ( new Dimension( Integer.MAX_VALUE, Integer.MAX_VALUE ) );
  }

  public float getLayoutAlignmentX( Container parent )
  {
    return ( 0.5f );
  }

  public float getLayoutAlignmentY( Container parent )
  {
    return ( 0.5f );
  }

  public void invalidateLayout( Container target )
  {}

  public Dimension preferredLayoutSize( Container parent )
  {
    synchronized( parent.getTreeLock() )
    {
      Insets insets = parent.getInsets();
      int ncomponents = holder.size();
      int w = 0;
      int h = 0;

      for( int i = 0; i < ncomponents; i++ )
      {
        Component comp = ((MCard) holder.get(i)).component;
        Dimension d = comp.getPreferredSize();
        if (d.width > w)
          w = d.width;
        if (d.height > h)
          h = d.height;
      }
      return ( new Dimension( insets.left + insets.right + w + hgap * 2,
                              insets.top + insets.bottom + h + vgap * 2 ) );
    }
  }

  public Dimension minimumLayoutSize( Container parent )
  {
    synchronized( parent.getTreeLock() )
    {
      Insets insets = parent.getInsets();
      int ncomponents = holder.size();
      int w = 0;
      int h = 0;

      for( int i = 0 ; i < ncomponents ; i++ )
      {
        Component comp = ((MCard) holder.get(i)).component;
        Dimension d = comp.getMinimumSize();
        if( d.width > w )
          w = d.width;
        if( d.height > h )
          h = d.height;
      }
      return ( new Dimension( insets.left + insets.right + w + hgap*2,
                              insets.top + insets.bottom + h + vgap*2 ) );
    }
  }

  public void layoutContainer( Container parent )
  {
    synchronized( parent.getTreeLock() )
    {
      Insets insets = parent.getInsets();

      if( !holder.isEmpty() && null != currentCard)
      {
        Component comp = currentCard.component;
        comp.setBounds( hgap + insets.left, vgap + insets.top,
                        parent.getWidth() - (hgap*2 + insets.left + insets.right),
                        parent.getHeight() - (vgap*2 + insets.top + insets.bottom) );
        if( !comp.isVisible() )
          comp.setVisible(true);
      }
    }
  }

  private int getCardIndex( Component component )
  {
    for( int index = 0; index < holder.size(); index++ )
    {
      MCard card = (MCard)holder.get( index );
      if( component == card.component )
        return ( index );
    }
    return ( -1 );
  }

  private MCard getCard( Object key )
  {
    for( int index = 0; index < holder.size(); index++ )
    {
      MCard card = (MCard)holder.get( index );
      if( key == card.key )
        return ( card );
    }
    return ( null );
  }

  public Component getCurrentComponent( )
  {
    return( null != currentCard ? currentCard.component : null );
  }

  public Component getComponent( Object key )
  {
    MCard card = getCard( key );
    return( null != card ? card.component : null );
  }

  public void addLayoutComponent( String key, Component component )
  {
    this.addLayoutComponent( component, key );
  }

  public void addLayoutComponent( Component component, Object key )
  {
    synchronized( component.getTreeLock() )
    {
      component.setVisible( holder.isEmpty() );
      MCard card = new MCard( component, key );
      if( holder.isEmpty() )
        currentCard = card;
      holder.add( card );
    }
  }

  public void removeLayoutComponent( Component component )
  {
    synchronized( component.getTreeLock() )
    {
      int index = getCardIndex( component );
      if( index < 0 )
        return;
      holder.remove( index) ;
      if( holder.isEmpty() )
      {
        currentCard = null;
        return;
      }
      currentCard = (MCard) holder.get(Math.min(index, holder.size() - 1));
      currentCard.component.setVisible( true );
    }
  }

  public void show( Container parent, Object key )
  {
    synchronized( parent.getTreeLock() )
    {
      if( parent.getLayout() != this )
      {
        throw new IllegalArgumentException("wrong parent for MCardLayout");
      }
      MCard card = getCard( key );
      if( null == card || card == currentCard )
        return;
      if( null != currentCard )
        currentCard.component.setVisible( false );
      card.component.setVisible( true );
      currentCard = card;
    }
  }

  public void show( Container parent, int index )
  {
    synchronized( parent.getTreeLock() )
    {
      if( parent.getLayout() != this )
      {
        throw new IllegalArgumentException("wrong parent for MCardLayout");
      }
      MCard card = ( index < holder.size() ? (MCard)holder.get( index ) : null );
      if( null == card || card == currentCard )
        return;
      if( null != currentCard )
        currentCard.component.setVisible( false );
      card.component.setVisible( true );
      currentCard = card;
    }
  }
}

