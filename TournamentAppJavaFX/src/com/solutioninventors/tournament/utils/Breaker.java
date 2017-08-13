/**
 *@Author: Oguejiofor Chidiebere
 *Breaker.java
 *Aug 10, 2017
 *9:32:11 PM
 */
package com.solutioninventors.tournament.utils;

import java.security.SecureRandom;
import java.util.Comparator;
import java.util.function.Function;

public enum Breaker
{
	/**
	 * This golden enum encapsulates all the types of breakers
	 * Each type contains a Comparator object which is used to break ties
	 * Private classes HeadToHead and CoinToss are used to create the headTOhead and
	 * CoinToss types
	 */
	
	
	GROUUP_BREAKER ,
	KNOCKOUT_BREAKER , 
	
	GOALS_SCORED( GROUUP_BREAKER , getGoalsScored() ) ,
	GOALS_CONCEDED( GROUUP_BREAKER , getGoalsConceded() ) ,
	GOALS_DIFFERENCE( GROUUP_BREAKER , getGoalsDifference() ) ,
	NUMBER_OF_WINS( GROUUP_BREAKER , getGoalsScored() ) ,
	NUMBER_OF_DRAWS( GROUUP_BREAKER, getGoalsScored() ) ,
	NUMBER_OF_LOSS( GROUUP_BREAKER , getGoalsScored() ) ,
	COIN_TOSS( GROUUP_BREAKER , getCoinToss() )  ,
	HEAD_TO_HEAD( GROUUP_BREAKER , getHeadToHead() )  ;
	
	
	private final  Comparator< Competitor> breaker;
	private final Breaker type ;
	
	private Breaker()
	{
		this( null , null );
	}
	
	private Breaker( Breaker typeName, Comparator<Competitor> comparator )
	{
		type = typeName;
		breaker = comparator;
	}
	
	
	private static Comparator< Competitor> getCoinToss()
	{
		return new CoinTossBreaker();
	}

	private static Comparator< Competitor> getHeadToHead()
	{
		return new Head_To_Head();
	}

	

	public Breaker getType()
	{
		return type;
	}

	public Comparator< Competitor> getBreaker()
	{
		return breaker;
	}
	
	
	private static Comparator<Competitor> getGoalsScored()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalsScored;

		return Comparator.comparing( function ).reversed();
	}
	
	private static Comparator<Competitor> getGoalsDifference()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalDifference;

		return Comparator.comparing( function ).reversed();
	}
	
	
	private static Comparator<Competitor> getGoalsConceded()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalsConceded;

		return Comparator.comparing( function );
	}
	
	private static Comparator<Competitor> getNumberOfWins()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfWin;

		return Comparator.comparing( function ).reversed();
	}
	
	private static Comparator<Competitor> getNumberOfDraw()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfDraw;

		return Comparator.comparing( function ).reversed();
	}
	
	
	private static Comparator<Competitor> getNumberOfLoss()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfLoss;

		return Comparator.comparing( function );
	}

	
	private static class Head_To_Head implements Comparator< Competitor >
	{
		@Override
		public int compare(Competitor com1, Competitor com2)
		{
			
			double score1 = com1.getHeadToHeadScore( com2 );
			double score2 = com2.getHeadToHeadScore( com1 ); ;
			
			if ( score1 < score2 )
				return 1;
			else if ( score1 == score2 )
				return 0 ;
			else
				return -1 ;
			
			
		}
	}
	
	private static class CoinTossBreaker implements  Comparator<Competitor>
	{

		@Override
		public int compare(Competitor com1, Competitor com2)
		{
			SecureRandom random = new SecureRandom();
			
			int score1 = random.nextInt(1000) ;
			int score2 = random.nextInt(1000) ;
			
			if ( score1 > score2 )
				return 1;
			return -1;
			
			
		}
	}
	
}
