/**
 *@Author: Oguejiofor Chidiebere
 *Breaker.java
 *Aug 10, 2017
 *9:32:11 PM
 */
package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A Breaker object encapsulates the logic of how a {@link com.solutioninventors.tournament.types.Tournament} would resolve ties
 * It is used when creating a {@link TieBreaker } object <br>
 * It achieves this by creating a Comparator&lt;Competitor&gt; object that is created via calls to some {@code Competitor } 
 * methods
 * <p>
 * Constants {@code Breaker.GROUP_BREAKER, Breaker.KNOCKOUT_BREAKER, Breaker.BOTH } are the
 * different types of breakers and are used to group other breaker objects.
 * Thus these types cannot be used when creating a {@code TieBreaker } object. For example
 * 
 * <p>
 *  {@code Breaker[] breakers = { Breaker.BOTH, Breaker.GROUP_BREAKERS }; } <br>
 *   {@code TieBreaker = new TieBreaker( breakers) // throws an InvalidBreakerException  } ;<br>
 *   throws an {@link com.solutioninventors.tournament.exceptions.InvalidBreakerException }
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
 *  The {@code Breaker.GROUP}  can <b>ONLY</b> be used for creating {@link com.solutioninventors.tournament.types.group.GroupTournament}s. {@code Breaker.KOCKOUT_BREAKER}
 *   can <b>ONLY</b> be used in creating {@link com.solutioninventors.tournament.types.knockout.EliminationTournament } objects 
 *   {@code Breaker.BOTH } may be used for any {@code Tournament } object
 *   
 *   <p>
 *   The class contains public methods {@code getType, getName and getBreaker}<br>
 *   Method {@code getBreaker } returns a {@code Comparator } object 
 *    <p>
 *    
 *    @author  Oguejiofor Chidiebere
 *    @see com.solutioninventors.tournament.types.Tournament
 *    @see TieBreaker
 *    @see com.solutioninventors.tournament.exceptions.InvalidBreakerException
 *    @see com.solutioninventors.tournament.types.group.GroupTournament
 *    @see com.solutioninventors.tournament.types.knockout.EliminationTournament
 *   
 *    @since v1.0
 *   
 *    
 */
public enum Breaker implements Serializable
{
	/**
	 * Specifies the type of a {@code Breaker}. It specifies that a breaker can be used to in {@code GroupTournament}s
	 * Must not be used when creating {@code TieBreaker}s
	 */
	GROUP_BREAKER ,
	/**
	 * A type of {@code Breaker}. It specifies that a breaker can be used to in {@code EliminationTournament}s
	 * Should not be used when creating {@code TieBreaker}s
	 */
	KNOCKOUT_BREAKER ,
	/**
	 * A type of {@code Breaker}. It specifies that a breaker is goal dependent ( e.g goals scored , goals against etc ).
	 * Should not be used when creating {@code TieBreaker}s
	 */
	GOAL_DEPENDENT ,
	/**
	 * Specifies that a breaker is not goal dependent.
	 * Should not be used when creating {@code TieBreaker}s
	 */
	NOT_GOAL_DEPENDENT ,
	/**
	 * A type of {@code Breaker}. It specifies that a breaker can be used for all {@code Tournament}s .
	 * Should not be used when creating {@code TieBreaker}s
	 */
	ALL , 
	
	AWAY_GOAL_AGAINST_AN_OPPONENT( ALL , getAwayGoalBreaker(), GOAL_DEPENDENT ),
	SHOOT_OUT( KNOCKOUT_BREAKER , null , NOT_GOAL_DEPENDENT),
	
	
	COIN_TOSS( ALL  , getCoinToss(), NOT_GOAL_DEPENDENT)  ,

	
	GOALS_SCORED( GROUP_BREAKER , getGoalsScored() , GOAL_DEPENDENT ) ,
	GOALS_CONCEDED( GROUP_BREAKER , getGoalsConceded()  , GOAL_DEPENDENT ) ,
	GOALS_DIFFERENCE( GROUP_BREAKER , getGoalsDifference(), GOAL_DEPENDENT  ) ,
	

	AWAY_GOALS_SCORED( GROUP_BREAKER , getAwayGoalsScored() , GOAL_DEPENDENT  ), 
	AWAY_GOALS_CONCEEDED( GROUP_BREAKER , getAwayGoalsConceded()  , GOAL_DEPENDENT), 
	AWAY_GOALS_DIFFERENCE( GROUP_BREAKER , getAwayGoalDifference() , GOAL_DEPENDENT ), 
	
	HOME_GOALS_SCORED( GROUP_BREAKER , getHomeGoalsScored()  , GOAL_DEPENDENT ), 
	HOME_GOALS_CONCEEDED( GROUP_BREAKER , getHomeGoalConceeded() , GOAL_DEPENDENT  ), 
	HOME_GOALS_DIFFERENCE( GROUP_BREAKER , getHomeGoalDifference()  , GOAL_DEPENDENT ), 
	
	
	HOME_WINS( GROUP_BREAKER , getNumberOfHomeWin() ,NOT_GOAL_DEPENDENT ) ,
	HOME_DRAWS( GROUP_BREAKER , getNumberOfHomeWin()  , NOT_GOAL_DEPENDENT) ,
	HOME_LOSS( GROUP_BREAKER , getNumberOfHomeWin() , NOT_GOAL_DEPENDENT ) ,
	
	AWAY_WINS(GROUP_BREAKER , getNumberOfAwayWin() , NOT_GOAL_DEPENDENT ) ,
	AWAY_DRAWS( GROUP_BREAKER , getNumberOfHomeWin() , NOT_GOAL_DEPENDENT ) ,
	AWAY_LOSS( GROUP_BREAKER , getNumberOfHomeWin() , NOT_GOAL_DEPENDENT ) ,
	
	
	
	TOTAL_WINS( GROUP_BREAKER , getNumberOfWins(),  NOT_GOAL_DEPENDENT) ,
	TOTAL_DRAWS( GROUP_BREAKER, getNumberOfDraw(), NOT_GOAL_DEPENDENT) ,
	TOTAL_LOSS( GROUP_BREAKER , getNumberOfLoss(), NOT_GOAL_DEPENDENT ) ,
	
	HEAD_TO_HEAD( GROUP_BREAKER , getHeadToHead() , NOT_GOAL_DEPENDENT)  ;
	
	private final  Comparator< Competitor> breaker;
	private final Breaker type ;
	private final Breaker goalsType;
	
	
	
	/**
	 * 
	 * Creates a Breaker constant with only the name 
	 * Used to create types
	 *@param name
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
	 */
	private Breaker()
	{
		this( null , null, null  );
	}
	
	
	/**
	 * Initializes  this {@code Breaker } object by storing the arguments
	 * 
<<<<<<< HEAD
	 *@param typeName - specifies the {@code Tournament } type that this {@code Breaker} would be used. <br>
	 *	Must be set to {@code Breaker.GROUP}, {@code Breaker.KNOCKOUT or Breker.BOTH}.
=======
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *total number of away goals scored by the {@code Competitor}s. This method is used to create Breaker.NUMBER_OF_AWAY_GOALS object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejiofor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *@author  Oguejiofor Chidiebere
 *    @since 1.0
>>>>>>> refs/remotes/origin/master
	 *
	 *@param comparator - a {@code Comparator&lt;Competitor}&gt; that specifies the logic to be used to break the tie
	 *@param goalDependent - a {@code Breaker}  that specifies if this {@code Breaker} uses goals to 
	 *break ties. <br> Must be set to {@code Breaker.GOAL_DEPENDENT } or {@code Breaker.NOT_GOAL_DEPENDENT} 
	 * @author Oguejiofor Chidiebere
	 */
	private Breaker( Breaker typeName, Comparator<Competitor> comparator, 
			 Breaker goalDependent )
	{
		type = typeName;
		breaker = comparator;
		
		goalsType = goalDependent ;
	}
	
	
	
	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of away {@code Fixture }s won by the {@code Competitor}s. 
	 *This method is used to create Breaker.NUMBER_OF_AWAY_WINS object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejiofor Chidiebere 
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
	 *@author Oguejiofor Chidiebere 
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
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of goals scored at home. 
	 *This method is used to create Breaker.HOME_GOAL_DIFFERENCE object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getHomeGoalDifference()
	{
		Function< Competitor, Double >  function = Competitor:: getHomeGoalDifference;

		return Comparator.comparing( function ).reversed();
	}





	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *number of goals conceeded at home. 
	 *This method is used to create Breaker.HOME_GOAL_CONCEEDED object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getHomeGoalConceeded()
	{
		Function< Competitor, Double >  function = Competitor:: getHomeGoalsConceeded;

		return Comparator.comparing( function );
	}


	/**
	 * 
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties with the {@code Competitor}'s<br/>
	 *goal difference in all away {@code Fixture}'s . 
	 *This method is used to create Breaker.HOME_GOAL_CONCEEDED object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getAwayGoalDifference()
	{
		Function< Competitor, Double >  function = Competitor:: getAwayGoalDifference;

		return Comparator.comparing( function ).reversed();
	}

	/**
	 * 
	 Gets the a {@code Comparator&lt;Competitor&gt; } that breaks ties between two {@code Competitor}'s 
	 based on the away goal that each has scored against his opponent.<p>
	 This can be used to implement away goal rule in knockout tournaments and may also be used in
	 group tournament 
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 * @since v1.0
	 *
	 */
	
	private static Comparator< Competitor>  getAwayGoalBreaker()
	{
		return new AwayGoal();
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
	 * Gets a Comparator&ltCompetitor&gt that encapsulates the logic for breaking ties ageainst 
	 * two {@code COmpetitor}s with their home goals scored
	 *@author Oguejiofor Chidiebere
	 **@since v1.0
	 *@see Competitor 
	 */
	private static Comparator<Competitor> getHomeGoalsScored()
	{
		Function< Competitor, Double >  function = Competitor:: getHomeGoalsScored;

		return Comparator.comparing( function ).reversed();
	}
	
	/**
	 * Gets a Comparator&ltCompetitor&gt that encapsulates the logic for breaking ties ageainst 
	 * two {@code COmpetitor}s with their home goals scored
	 *@author Oguejiofor Chidiebere
	 **@since v1.0
	 *@see Competitor 
	 */
	private static Comparator<Competitor> getAwayGoalsScored()
	{
		Function< Competitor, Double >  function = Competitor:: getAwayGoalsScored;

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
		Function< Competitor, Double >  function = Competitor:: getGoalsConceeded;

		return Comparator.comparing( function );
	}
	
	 /**Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their away goal conceeded. 
	 *This method is used to create Breaker.AWAY_GOAL_CONCEEDED object
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getAwayGoalsConceded()
	{
		Function< Competitor, Double >  function = Competitor:: getAwayGoalsConceeded;

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
	
	/**
	 *Gets a Comparator<Competitor> object that encapsulates the logic used for creating breaking ties
	 *between two {@code Competitor}'s via their total number of draw. 
	 *@return a Comparator<Competitor> object used to break tie
	 *@author Oguejifor Chidiebere 
	 *@since v1.0
	 *@see Competitor
	 *
	 */
	
	private static Comparator<Competitor> getNumberOfDraw()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfDraw;

		return Comparator.comparing( function ).reversed();
	}
	
	/**
	 * 
	 *Gets a Comparator&lt;Competitor&gt; that encapsulates the logic for breaking ties with the
	 *{@code Competitor}'s total number of win
	 *@return {@code Comparator&lt;Competitor&gt;}
	 */
	 private static Comparator<Competitor> getNumberOfLoss()
	{
		Function< Competitor, Integer >  function = Competitor:: getNumberOfLoss;

		return Comparator.comparing( function );
	}

	

	/**
	 *This class is used to create a Comparator&ltCompetitor&gt object that 
	 *breaks ties between two Competitors via their head to head record
	 *@author Oguejiofor Chidiebere 
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
	 *@author Oguejiofor Chidiebere 
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
	 *This class is used to create a Comparator&lt;Competitor&gt; object that 
	 *breaks ties between two Competitors by simulating a coin toss
	 *@author Oguejiofor Chidiebere 
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
	 *Gets the name of this {@code Breaker} as {@code String}
	 *@return A {@code String } containing the Breaker name
	 **@author Oguejiofor Chidiebere
	 **@since v1.0
	 */
	public String getName()
	{
		String name =  toString().replace( '_', ' ' ).trim();
		
//		String[] tokens = name.split(" ");
//		   
//		for ( int i = 0 ; i< tokens.length ; i ++ 	)
//		{
//			name = name.replaceFirst( tokens[i], capitaliseFirstLetter(tokens[i]));
//		}
		return name ;
	}

	
	private String capitaliseFirstLetter( String word )
	{
		char[] charac =  { word.charAt(0)}; 
		String first  = new String(charac ) ;
		word = word.replaceFirst(first, first.toUpperCase() );
		word = word.replaceFirst(first, first.toUpperCase() );
		return word;
	}
	/**
	 * Gets an array of {@code Breaker}'s based on the type and goal dependence specifiedby the
	 * arguments
	 * @author Oguejiofor Chidiebere
	 *@param breakerType - Specifies the type of tournament that the {@code Breaker}s would bew used<br>
	 *Must be set to {@code Breaker.GROUP}, {@code Breaker.KNOCKOUT} or {@code Breaker.ALL}
	 *
	 *@param dependence - Specifies if the returned {@code Breaker}s would be goal dependent or not<br>
	 *When set to {@code Breaker.GOAL_DEPENDENT} this method gets all the goal dependent {@code Breaker}s
	 *<br> When set to {@code Breaker.NOT_GOAL_DEPENDENT} this method gets all the breakers that are not goal dependent
	 * <br>If this argument is set to any other value( preferably {@code Breaker.ALL} )  then goal dependence is ignored.
	 *
	 *@since v1.0
	 *@return an array of {@code Breaker}s
	 */
	public static Breaker[] getBreakers( Breaker breakerType, Breaker dependence )
	{
		final Breaker  val ;
		Predicate<Breaker> predicate = null;
		if ( dependence == Breaker.GOAL_DEPENDENT || 
				dependence == Breaker.NOT_GOAL_DEPENDENT)
			predicate = b-> (b.getType() == breakerType || 
									b.getType() == Breaker.ALL ) &&
							b.getGoalDependence() == dependence;
		else //gets all the breaker type
			predicate = b-> b.getType() == breakerType || b.getType() == Breaker.ALL;	
			
		val = dependence ;
		
		if(  breakerType == Breaker.KNOCKOUT_BREAKER || breakerType == Breaker.GROUP_BREAKER ||
						breakerType == Breaker.ALL)
		{
			List<Breaker> list = Arrays.stream( values() )
					.filter( predicate )
					.collect( Collectors.toList() );
				
				return list.toArray( new Breaker[ list.size() ] );
					
		}
		
		return null;
	}

	
	/**
	 * Gets the goal dependence of this {@code Breaker} 
	 *@author Oguejiofor Chidiebere 
	 *@since v1.0
	 *@return Either {@code Breaker.GOAL_DEPENDENT} or  {@code Breaker.NOT_GOAL_DEPENDENT}
	 */
	public Breaker getGoalDependence()
	{
		return goalsType;
	}
	
	/**
	 * Converts an array of Breakers to an array of String by calling each  {@code Breaker}'s
	 * toString() method
	 *String[]
	 *@author Oguejiofor Chidiebere
	 *@sincev1.0
	 *@param breakers the Breaker array to be converted
	 *@return an array of String
	 */
	public static String[] convertToString( Breaker ... breakers)
	{
		List<String> breakerName = 
				Arrays.stream( breakers )
				  .map( b-> b.getName())
				  .collect(Collectors.toList() );
		return breakerName.toArray( new String[ breakerName.size() ] );
			  
	}
	
	public static Breaker[] getStandardBreaker(SportType type	)
	{
		if( type == SportType.GOALS_ARE_SCORED )
		{ 
			Breaker[] breakers = 		 {Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, 
			Breaker.HEAD_TO_HEAD, Breaker.GOALS_SCORED, Breaker.COIN_TOSS};
			return breakers;
		}
		
		Breaker[] breakers = 		 
			{Breaker.HEAD_TO_HEAD, Breaker.TOTAL_WINS, Breaker.COIN_TOSS };
		return breakers;
	}
	
	/**
	 * Converts an array of Breakers to an array of String by calling each  {@code Breaker}'s
	 * toString() method
	 *String[]
	 *@param breakers
	 *@return a BreakerArray
	 */
	public static Breaker[] convertToBreaker( String ... breakerNames)
	{
		List<Breaker> breakerList = 
				Arrays.stream( breakerNames)
				  .map( name-> convertToBreaker(name) )
				  .filter( b-> b!=null )
				  .collect(Collectors.toList() );
		
		if ( breakerList.size() > 0 )
			return breakerList.toArray(new  Breaker[ breakerList.size() ]) ;
		return null;
			  
	}
	
	public static Breaker convertToBreaker( String breakerName)
	{
		boolean hasName = 	Arrays.stream(values() )
						   	.anyMatch( b-> b.getName().equals( breakerName));
				
		if ( hasName )	
			return Arrays.stream( values() )
				   .filter( b-> b.getName().equals( breakerName ) )
				   .findFirst()
				   .get();
		
		return null;
	}
}
