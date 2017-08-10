package com.solutioninventors.tournament.utils;

import java.util.Comparator;

public class TieBreaker
{
	private final Breaker[] BREAKERS;
	
	
	public TieBreaker( Breaker[] breaks )
	{

		BREAKERS = breaks;
	}
	
	public void breakTies( Competitor[] comps, double pWin, double pDraw, double pLoss)
	{
		Comparator< Competitor > comparator = 
				Comparator.comparing(c-> c.getPoint( pWin , pDraw , pLoss) );
		
		
	}
	public Breaker[] getBREAKERS()
	{
		return BREAKERS;
	}
}
