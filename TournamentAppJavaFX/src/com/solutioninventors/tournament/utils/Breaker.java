/**
 *@Author: Oguejiofor Chidiebere
 *Breaker.java
 *Aug 10, 2017
 *9:32:11 PM
 */
package com.solutioninventors.tournament.utils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Breaker
{
	/**
	 * This golden enum encapsulates all the types of breakers
	 * Each type contains a Comparator object which is used to break ties
	 * Private classes HeadToHead and CoinToss are used to create the headTOhead and
	 * CoinToss types
	 */
	
	
	GROUP_BREAKER("GROUP BREAKER") ,
	KNOCKOUT_BREAKER( "KNOCKOUT BREAKER") , 
	BOTH( null ) , 
	
	AWAY_GOAL( BOTH , getAwayGoalBreaker(), "AWAY GOAL" ),
	SHOOT_OUT( KNOCKOUT_BREAKER , null , "SHOOT OUT" ),
	
	
	COIN_TOSS( BOTH  , getCoinToss() , "COIN TOSS")  ,
	
	HOME_WIN( GROUP_BREAKER , getHomeWin() ,"HOME_WIN" ) ,
	AWAY_WIN( GROUP_BREAKER , getAwayWin() ,"AWAY_WIN" ) ,
	GOALS_SCORED( GROUP_BREAKER , getGoalsScored() , "GOALS SCORED") ,
	GOALS_CONCEDED( GROUP_BREAKER , getGoalsConceded() , "GOALS CONCEDDED") ,
	GOALS_DIFFERENCE( GROUP_BREAKER , getGoalsDifference(), "GOAL DIFFERENCE" ) ,
	NUMBER_OF_WINS( GROUP_BREAKER , getNumberOfWins(), "NUMBER OF WINS" ) ,
	NUMBER_OF_DRAWS( GROUP_BREAKER, getNumberOfDraw(), "NUMBER OF DRAWS" ) ,
	NUMBER_OF_LOSS( GROUP_BREAKER , getNumberOfLoss(), "NUMBER OF LOSS" ) ,
	HEAD_TO_HEAD( GROUP_BREAKER , getHeadToHead() , "HEAD TO HEAD")  ;
	
	private final  Comparator< Competitor> breaker;
	private final Breaker type ;
	private final String name ;
	
	private Breaker(String name )
	{
		this( null , null, name  );
	}
	
	

	private static Comparator< Competitor>  getAwayGoalBreaker()
	{
		return new AwayGoal();
	}

	private Breaker( Breaker typeName, Comparator<Competitor> comparator, String theName )
	{
		type = typeName;
		breaker = comparator;
		name = theName;
	}
	
	private static Comparator< Competitor> getAwayWin()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfAwayWin;

		return Comparator.comparing( function ).reversed();
	}
	
	private static Comparator<Competitor> getHomeWin()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfHomeWin;

		return Comparator.comparing( function ).reversed();
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
	
	
	private static class AwayGoal implements Comparator< Competitor >
	{
		@Override
		public int compare(Competitor com1, Competitor com2)
		{
			
			double score1 = com1.getAwayGoal( com2 );
			double score2 = com2.getAwayGoal( com1 ); ;
			
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
	
	public static Breaker[] getGroupBreakers()
	{
		Breaker[] vals = Breaker.values();
		
		List< Breaker > list = new ArrayList<>();
		
		for ( int i = 0 ;i < vals.length;i++ )
			if ( vals[i].getType() != Breaker.KNOCKOUT_BREAKER && vals[i].getType() != null )
				list.add(vals[ i ] );
		
		
		return list.toArray( new Breaker[ list.size() ] );
			  
	}
	
	public static Breaker[] getKnockoutBreakers()
	{
		List<Breaker> knockoutBreakers  = 
				Arrays.stream( Breaker.values() )
				  .filter(b -> b.getType() != Breaker.GROUP_BREAKER  &&
						  	b.getType()!= null )
				  .collect(Collectors.toList() );
		
		return knockoutBreakers.toArray( new Breaker[ knockoutBreakers.size() ] );
			  
	}
	
	public String toString()
	{
		return getName();
	}



	public String getName()
	{
		return name;
	}
}
