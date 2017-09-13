/**
 *@Author: Oguejiofor Chidiebere
 *Breaker.java
 *Aug 10, 2017
 *9:32:11 PM
 */
package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A Breaker object encapsulates the logic of how a {@link Tournament} would resolve ties
 * It is used when creating a {@link TieBreaker } object <br>
 * It achieves this by creating a Comparator&lt;Competitor&gt; object that is created via calls to some {@code Competitor } 
 * methods
 * <p>
 * Constants {@code Breaker.GROUP_BREAKER, Breaker.KNOCKOUT_BREAKER, Breaker.BOTH } are the
 * different types of breakers and are used to group other breaker objects.
 * Note that these types cannot be used when creating a {@code TieBreaker } object. For example
 * 
 * <p>
 *  {@code Breaker[] breakers = { Breaker.BOTH, Breaker.GROUP_BREAKERS }; } <br>
 *   {@code TieBreaker = new TieBreaker( breakers) // throws an InvalidBreakerException  } ;<br>
 *   throws an {@link InvalidBreakerException }
 * 
 * <p>
 *  Constants {@code Breaker.AWAY_GOAL , Breaker.SHOOTOUT, Breaker.COIN_TOSS } are of type {@code Breaker.BOTH}
 * 	While {@code Breaker.AWAY_WIN,
 *  Breaker.GOALS_SCORED, Breaker.GOALS_CONCEDED,Breaker.GOALS_DIFFERENCE,
 *  Breaker.NUMBER_OF_HOME_WIN ,Breaker.NUMBER_OF_AWAY_GOALS, Breaker.NUMBER_OF_WINS,
 *  Breaker.NUMBER_OF_DRAWS ,Breaker.NUMBER_OF_LOSS ,Breaker.HEAD_TO_HEAD }
 *  are all of type Breaker.GROUP_BREAKER
 *  
 *  <p>
 *  The {@code Breaker.GROUP}  can <b>ONLY</b> be used for creating {@link GroupTournament}s. {@code Breaker.KOCKOUT_BREAKER}
 *   can <b>ONLY</b> be used in creating {@link EliminationTournament } objects 
 *   {@code Breaker.BOTH } may be used for any {@code Tournament } object
 *   
 *   <p>
 *   
 *   The class contains public methods {@code getType, getName and getBreaker}<br>
 *   Method {@code getBreaker } returns a {@code Comparator } object 
 *    <p>
 *    
 *    @author  Oguejiofor Chidiebere
 *    @see Tournament
 *    @see TieBreaker
 *    @see InvalidBreakerException
 *    @see GroupTournament
 *    @see EliminationTournament
 *   
 *    @since v1.0
 *   
 *    
 */
public enum Breaker implements Serializable
{
	
	
	GROUP_BREAKER("GROUP BREAKER") ,
	KNOCKOUT_BREAKER( "KNOCKOUT BREAKER") , 
	BOTH( null ) , 
	
	AWAY_GOAL( BOTH , getAwayGoalBreaker(), "AWAY GOAL" ),
	SHOOT_OUT( KNOCKOUT_BREAKER , null , "SHOOT OUT" ),
	
	
	COIN_TOSS( BOTH  , getCoinToss() , "COIN TOSS")  ,

	NUMBER_OF_AWAY_WIN( GROUP_BREAKER , getNumberOfAwayWin() ,"AWAY_WIN" ) ,
	GOALS_SCORED( GROUP_BREAKER , getGoalsScored() , "GOALS SCORED") ,
	GOALS_CONCEDED( GROUP_BREAKER , getGoalsConceded() , "GOALS CONCEDDED") ,
	GOALS_DIFFERENCE( GROUP_BREAKER , getGoalsDifference(), "GOAL DIFFERENCE" ) ,
	NUMBER_OF_HOME_WIN( GROUP_BREAKER , getNumberOfHomeWin() ,"NUMBER OF HOME WIN" ) ,
	NUMBER_OF_AWAY_GOALS( GROUP_BREAKER , getNumberOfAwayGoals() , "NUMBER OF AWAY GOAL" ), 
	NUMBER_OF_WINS( GROUP_BREAKER , getNumberOfWins(), "NUMBER OF WINS" ) ,
	NUMBER_OF_DRAWS( GROUP_BREAKER, getNumberOfDraw(), "NUMBER OF DRAWS" ) ,
	NUMBER_OF_LOSS( GROUP_BREAKER , getNumberOfLoss(), "NUMBER OF LOSS" ) ,
	HEAD_TO_HEAD( GROUP_BREAKER , getHeadToHead() , "HEAD TO HEAD")  ;
	
	private final  Comparator< Competitor> breaker;
	private final Breaker type ;
	private final String name ;
	
	/**
	 * 
	 * Creates a Breaker constant with only the name 
	 * Used to create types
	 *@param name
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 */
	private Breaker(String name )
	{
		this( null , null, name  );
	}
	
	

	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *total number of away goals scored by the {@code Competitor}s. This method is used to create Breaker.NUMBER_OF_AWAY_GOALS object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 *
	 */
	private static Comparator<Competitor> getNumberOfAwayGoals()
	{
		Function< Competitor, Double >  function = Competitor:: getNumberOfAwayGoals;

		return Comparator.comparing( function ).reversed();
	}


	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of away goal against a particular competitor. This method is used to create Breaker.AWAY_WIN object.<br/>
	 *
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 *
	 */
	
	private static Comparator< Competitor>  getAwayGoalBreaker()
	{
		return new AwayGoal();
	}

	/**
	 * 
	 *Creates a {@code Breaker } object 
	 *@param comparator Is a Comparator&ltCompetitor&gt object which encapsulates the logic used to break the tie
	 *@param typeName Can either be Breaker.GROUP_BRREAKERS, Breaker.kNOCKOUT_BREAKERS, Breaker.BOTH
	 *@param theName The name of the Breaker object stored as String
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 */
	
	private Breaker( Breaker typeName, Comparator<Competitor> comparator, String theName )
	{
		type = typeName;
		breaker = comparator;
		name = theName;
	}
	
	
	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of away {@code Fixture }s won by the {@code Competitor}s. 
	 *This method is used to create Breaker.NUMBER_OF_AWAY_WINS object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 
 *    
	 *
	 */
	private static Comparator< Competitor> getNumberOfAwayWin()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfAwayWin;

		return Comparator.comparing( function ).reversed();
	}
	
	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of home {@code Fixture }s won by the {@code Competitor}s. 
	 *This method is used to create Breaker.NUMBER_OF_HOME_WIN object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getNumberOfHomeWin()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfHomeWin;

		return Comparator.comparing( function ).reversed();
	}
	
	
	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via a coin toss. 
	 *This method is used to create Breaker.COIN_TOSS object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator< Competitor> getCoinToss()
	{
		return new CoinTossBreaker();
	}

	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their head to head record. 
	 *This method is used to create Breaker.HEAD_TO_HEAD object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator< Competitor> getHeadToHead()
	{
		return new Head_To_Head();
	}

	

	/**
	 * 
	 *Gets the type of @c{@code Breaker } as  {@code Breaker.GROUP_BREAKERS }, {@code Breaker.BOTH } or {@code Breaker.KNOCKOUT_BREAKERS}
	 *@return the type of this {@code Breaker } as Breaker
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 */
	public Breaker getType()
	{
		return type;
	}

	/**
	 * Gets the  {@code Comparator<Competitor> } that encapsulates the logic used to break ties
	 *
	 *@author Oguejiofor Chidiebere
	 *@return {@code Comparator<Competitor> }
	 **@since v1.0
	 *@see Competitor
	 */
	public Comparator< Competitor> getComparator()
	{
		return breaker;
	}
	
	/**
	 * Gets a Comparator&ltCompetitor&gt that encapsulates the logic for breaking ties ageainst 
	 * two {@code COmpetitor}s with their total goals scored
	 *@author Oguejiofor Chidiebere
	 **@since v1.0
	 *@see Competitor 
	 */
	private static Comparator<Competitor> getGoalsScored()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalsScored;

		return Comparator.comparing( function ).reversed();
	}
	
	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their goalDifference. 
	 *This method is used to create Breaker.GOAL_DIFFERENCE object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getGoalsDifference()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalDifference;

		return Comparator.comparing( function ).reversed();
	}
	
	/**
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their goal conceeded. 
	 *This method is used to create Breaker.GOAL_CONCEEDED object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getGoalsConceded()
	{
		Function< Competitor, Double >  function = Competitor:: getGoalsConceded;

		return Comparator.comparing( function );
	}
	

	/**
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their number of wins. 
	 *This method is used to create Breaker.NUMBER_OF_WIN object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
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

	

	/**
	 *This class is used to create a Comparator&ltCompetitor&gt object that 
	 *breaks ties between two Competitors via their head to head record
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
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
	

	/**
	 *This class is used to create a Comparator&ltCompetitor&gt object that 
	 *breaks ties between two Competitors via their away goal against each other
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
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
	

	/**
	 *This class is used to create a Comparator&ltCompetitor&gt object that 
	 *breaks ties between two Competitors by simulating a coin toss
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
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
	
	/**
	 * 
	 *Gets a  all {@code Breaker}s of type {@code Breaker.GROUP_BREAKERS}
	 *@return Breaker[]
	 **@author Oguejiofor Chidiebere
	 **@since v1.0
	 */
	public static Breaker[] getGroupBreakers()
	{
		
		Breaker[] vals = Breaker.values();
		
		List< Breaker > list = new ArrayList<>();
		
		for ( int i = 0 ;i < vals.length;i++ )
			if ( vals[i].getType() != Breaker.KNOCKOUT_BREAKER && vals[i].getType() != null )
				list.add(vals[ i ] );
		
		
		return list.toArray( new Breaker[ list.size() ] );
			  
	}
	
	/**
	 * 
	 *Gets all {@code Breaker}s of type {@code Breaker.KNOCKOUT_BREAKERS}
	 *@author Oguejiofor Chidiebere
	 *@since v1.0
	 *@return Breaker[]
	 *
	 *
	 */
	public static Breaker[] getKnockoutBreakers()
	{
		List<Breaker> knockoutBreakers  = 
				Arrays.stream( Breaker.values() )
				  .filter(b -> b.getType() != Breaker.GROUP_BREAKER  &&
						  	b.getType()!= null )
				  .collect(Collectors.toList() );
		
		return knockoutBreakers.toArray( new Breaker[ knockoutBreakers.size() ] );
			  
	}
	
	@Override
	public String toString()
	{
		return getName();
	}


	/**
	 * 
	 *Gets the name of this {@code Breaker} as {@code String}
	 *@return A {@code String } containing the Breaker name
	 **@author Oguejiofor Chidiebere
	 **@since v1.0
	 */
	public String getName()
	{
		return name;
	}
}
