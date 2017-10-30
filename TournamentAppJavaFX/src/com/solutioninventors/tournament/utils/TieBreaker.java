package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;

/**
 * This {@code TieBreaker } uses an array of {@code Breaker}s to sort a an array of 
 * {@code Competitor}s. A {@code TieBreaker } is <b>IMMUTABLE</b>.
 * This {@code TieBreaker} contains method {@code breakTies}
 * 
 * @author Oguejiofor Chidiebere 
 * @since v1.0
 * @see Competitor
 * @see com.solutioninventors.tournament.types.group.GroupTournament
 * @see Breaker
 *  
 */
public class TieBreaker implements Serializable
{
	private static final long serialVersionUID = 7353343296579961184L;
	/**
	 * The {@code Breaker}s  array to be used to break ties
	 */
	private final Breaker[] BREAKERS;
	/**
	 *A constant to be used when breaking ties for home fixtures by a {@code StandingTabel}
	 *when creating a home fixuture table
	 */
	public static final int HOME_FIXTURES = 1;
	/**
	 *A constant to be used when breaking ties for away fixtures by a {@code StandingTabel}
	 *when creating a away fixuture table
	 */
	public static final int AWAY_FIXTURES = 2 ;
	
	/**
	 * 
	 *Creates this {@code TieBreaker} with a {@code Breaker}s 
	 *The order in which the {@link Breaker}s appear determines the order in 
	 *which the ties would be broken.
	 *
	 *@author Oguejiofor Chidiebere 
	 *@since v1.0
	 *@param breakers The {@code Breaker[]} object
	 *
	 *@throws com.solutioninventors.tournament.exceptions.InvalidBreakerException - 
	 * when its argument is {@code null} or when
	 *any of the {@code Breaker}s is set to
	 *{@code Breaker.KNOCKOUT_BREAKER , Breaker.GROUP_BREAKER , Breaker.ALL, Breaker.GOALS_SCORED or
	 *Breker.NOT_GOALS_SCORED}
	 *It appends Breaker.COIN_TOSS to the Breaker list
	 *@see Breaker
	 */
	public TieBreaker( Breaker ... breakers ) throws InvalidBreakerException
	{
		
		List<Breaker> list =  new ArrayList<>() ; 
		list.addAll( Arrays.asList( breakers ) );
		list.add(Breaker.COIN_TOSS  );
		
		
		list.add( Breaker.COIN_TOSS );
		breakers = list.toArray( new Breaker[ list.size() ] );
		
		boolean invalid = 
			Arrays.stream( breakers)
				.anyMatch( b-> b.getType() == null );
		
		if ( breakers ==null || invalid )
			throw new InvalidBreakerException( "The breaker array is null  " );
		else
			BREAKERS = breakers;
			
	}
	
	/**
	 * 
	 * Sorts an array of {@code Competitor}s, breaks ties between the {@code Competitor}s if any
	 *  and returns a {@code Competitor[] } object with the {@code Competitor}s sroted 
	 *  <p>
	 * The {@code scope} argument specifies which point this method would use for the initial comparison
	 * . For example, if scope = {@code TieBreaker.HOME } then this method<br>
	 * would initially sort the competitors with their number of home points.
	 * If it is set to {@code TieBreaker.AWAY} then this method initially sort the competitors
	 * with <br>their number of away points.
	 * If scope is {@code TieBreaker.HOME + TieBreaker.AWAY} then it initially considers the 
 	 * the total points. 
 	 * This scope variable is important when retrieving a home table, an away table and
 	 * a table from a {@link com.solutioninventors.tournament.types.group.StandingTable} 
 	 * <p>
 	 * 
 	 * Typically, this method takes all the {@code COmpetitor}s in a {@code GroupTournmant}
 	 * and returns then sorted with this {@code TieBreaker}'s {@code Breaker}s 
 	 * 
	 *@param scope specifies the point to be used for the comparison. It can be set to
	 *TieBreaker.HOME( or 1) , TieBreaker.AWAY ( or 2 )  or TieBreker.HOME + TieBreaker.AWAY( or 3 )
	 *<br> if scope is not less than 1 and less than or equal to 3 then this method would assume
	 *{@code Breaker. HOME} + {@code TieBreaker.AWAY } 
	 *@param comps the {@code Competitor[]} to be sorted
	 *@param pWin the point for win
	 *@param pDraw the point for draw
	 *@param pLoss the point for loss
	 *@return a sorted {@code Competitor[]} object with all ties broken
	 */
	public Competitor[] breakTies( int scope , Competitor[] comps, double pWin, double pDraw, double pLoss)
	{
		Comparator< Competitor > comparator = null;
		
		
		if ( scope == AWAY_FIXTURES )
			comparator = Comparator.comparing(c-> c.getAwayPoint( pWin , pDraw , pLoss) );
		else if ( scope == HOME_FIXTURES )
			comparator = Comparator.comparing(c-> c.getHomePoint( pWin , pDraw , pLoss) );
		else
			comparator  = Comparator.comparing(c-> c.getPoint( pWin , pDraw , pLoss) );
		
		comparator = comparator.reversed();
		for ( int i = 0 ; i < getBreakers().length ;i ++ )
		{
			comparator = comparator.thenComparing( getBreakers()[i].getComparator() );
		}
		
		return Arrays.stream( comps )
				.sorted( comparator )
				.collect( Collectors.toList() )
				.toArray( new Competitor[ comps.length ] ) ;
	}
	
	/**
	 * Gets the {@code Breaker[] } object in this {@code TieBreaker}
	 *@return the {@code Breaker}s as {@code Breaker[] } 
	 */
	public Breaker[] getBreakers()
	{
		return BREAKERS;
	}
}
