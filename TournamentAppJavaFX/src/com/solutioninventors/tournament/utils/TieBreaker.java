package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
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
	private final Breaker[] BREAKERS;
	
	
	/**
	 * 
	 *Creates this {@code TieBreaker} with a {@code Breaker[]} object
	 *@author Oguejiofor Chidiebere 
	 *@since v1.0
	 *@param breakers The {@code Breaker[]} object
	 *@throws InvalidBreakerException when its argument is {@code null}
	 */
	public TieBreaker( Breaker[] breakers ) throws InvalidBreakerException
	{
		if ( breakers !=null )
			BREAKERS = breakers;
		else
			throw new InvalidBreakerException( "The breaker array is null  " );
	}
	
	/**
	 * 
	 *Creates this {@code TieBreaker} with a {@code Breaker[]} object
	 *@author Oguejiofor Chidiebere 
	 *@since v1.0
	 *@param comps An array of {@code Competitor}s before ties are broken
	 *@param pWin The point for win
	 *@param pDraw The point for a draw
	 *@param pLoss The point for a loss
	 *@return a {@code Competitor[] } This array contains the {@code Competitor}s in the right
	 *order after breaking the ties
	 */
	public Competitor[] breakTies( Competitor[] comps, double pWin, double pDraw, double pLoss)
	{
		Comparator< Competitor > comparator = 
				Comparator.comparing(c-> c.getPoint( pWin , pDraw , pLoss) );
		
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
