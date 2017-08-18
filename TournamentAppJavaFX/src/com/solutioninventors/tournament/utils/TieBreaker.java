package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;

public class TieBreaker implements Serializable
{
	private final Breaker[] BREAKERS;
	
	
	public TieBreaker( Breaker[] breaks ) throws InvalidBreakerException
	{
		if ( breaks !=null )
			BREAKERS = breaks;
		else
			throw new InvalidBreakerException( "The breaker array is null  " );
	}
	
	public Competitor[] breakTies( Competitor[] comps, double pWin, double pDraw, double pLoss)
	{
		Comparator< Competitor > comparator = 
				Comparator.comparing(c-> c.getPoint( pWin , pDraw , pLoss) );
		
		comparator = comparator.reversed();
		for ( int i = 0 ; i < getBreakers().length ;i ++ )
		{
			comparator = comparator.thenComparing( getBreakers()[i].getBreaker() );
		}
		
		return Arrays.stream( comps )
				.sorted( comparator )
				.collect( Collectors.toList() )
				.toArray( new Competitor[ comps.length ] ) ;
	}
	public Breaker[] getBreakers()
	{
		return BREAKERS;
	}
}
