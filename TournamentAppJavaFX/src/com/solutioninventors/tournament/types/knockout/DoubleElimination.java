/**
 *@Author: Oguejiofor Chidiebere
 *DoubleElimination.java
 *Aug 6, 2017
 *10:38:53 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

/**
 * A {@code DoubleElimination} is used to create an {@code 	EliminationTournament}
 * in which a {@code Competitor} must lose two games before he is eliminated from the
 * {@code Tournament}. It achieves this by placing the {@code Competitor}s into a 
 * winners bracket 	and losers bracket.

 * The class rounds is stored in the Map&lt; BracketType, List&ltRound&gt; &gt; object 
 * The BracketType objects stores the info about the bracket. Its values include 
 * BracketType.WINNERS_BRACKET , BracketType.MAJOR_BRACKET , BracketType.MINOR_BRACKET ,
 * BracketType.INITIAL_BRACKET , BracketType.TOURNAMENT_FINAL  
 * <p>
 * The first round( or initial round )  in this tournament is does not contain
 * winners or losers bracket. The winners of this {@code Round} would be in the
 * next round's winners bracket and the losers would be in the next round's
 * losers bracket. <p>
 * 
 * The first round is stored in the Map&lt;BracketType, List&lt;Round&gt;&gt; object as BracketType.INITIAL_BRACKET
 * After the first round, the winners and losers brackets are created and
 * Rounds are now  stored in BracketType.WINNERS_BRACKET , BracketType.MAJOR_BRACKET and BracketType.MINOR_BRACKET 
 * 
 * This is repeated for subsequent rounds with players being eliminated after each round
 * until only two competitors are left( the Tournament final ). The final is then stored in
 * BrackeType.TOURNAMENT_FINAL
 * <p>
 * A {@code Competitor} gets eliminated when he loses in the losers bracket. The winners
 * of the loser's bracket would be paired against the losers of the winners bracket in a 
 * Major Loser Bracket fixture in each Round<p>
 * For more info on how Double Elimination tournaments work search on google
 * 
 * @see Round
 * @see Fixture
 * @see BracketType
 * @author Oguejiofor Chidiebere
 * @since v1.0
 */
public class DoubleElimination extends EliminationTournament
{
	
	private Competitor[] topThree;
	
	private static final long serialVersionUID = -7071860792899692927L;
	
	/**
	 *Encapsulates the current fixture type of the current round
	 *@see  CurrentFixture
	 */
	private BracketType currentFixture;
	
	
	private String roundName ;
	
	/**
	 * Stores all the rounds in this {@code Tournament}
	 * @author Oguejiofor Chidiebere
	 * @see Round
	 * @see BracketType
	 */
	private Map< BracketType , List<Round> > rounds;
	
	
	
	/**
	 * Creates a {@code DoubleElimination} with the specified competitors
	 * Always shuffles the competitors
	 *@param comps the {@code Competitor} array
	 *@throws TournamentException if an invalid number of competitors
	 *is inputed
	 *
	 */
	public DoubleElimination( SportType type, Competitor[] comps) throws TournamentException
	{
		this( type, comps, true );
	}
	
	/**
	 * Creates a {@code DoubleElimination} with the specified competitors
	 * Shuffles the array based on the value of shuffle
	 *@param comps the {@code Competitor} array
	 *@throws TournamentException if an invalid number of competitors
	 *is inputed
	 *
	 */
	public DoubleElimination( SportType type, Competitor[] comps, boolean shuffle) throws TournamentException
	{
		super( type , comps , shuffle );
		rounds = new HashMap<>();
		List<Round> minor = new ArrayList<>(); //used to increase index to 1
		List<Round> major = new ArrayList<>(); //used to increase index to 1
		
		rounds.put( BracketType.WINNERS_BRACKET , new ArrayList<Round>() );
		rounds.put( BracketType.MAJOR_BRACKET ,major );
		rounds.put( BracketType.MINOR_BRACKET ,   minor);
		rounds.put( BracketType.INITIAL_BRACKET , new ArrayList<>() );
		rounds.put( BracketType.TOURNAMENT_FINAL, new ArrayList<>() );
		
		topThree = new Competitor[ 3 ];
		setCurrentFixture( BracketType.INITIAL_BRACKET);
		createTournament();
	}
	
	/**
	 * Creates this tournament. Called only by the constructor
	 */
	private void createTournament()
	{
		
		Competitor[] competitors = getCompetitors();
		
		List<Fixture > roundFixtures = new ArrayList<Fixture >(competitors.length /2 );
		
		for ( int i = 0 ; i < competitors.length ; i+= 2 )
		{
			roundFixtures.add( new Fixture( getSportType(), competitors[ i ] , competitors[ i+ 1] ) );
		}
		
		
		Round initialRound = new Round(  
				roundFixtures.toArray(new Fixture[ roundFixtures.size() ] ));
		addRound( BracketType.INITIAL_BRACKET , initialRound );
		
	}

	/**
	 * Adds a {@code Round} to the rounds in this tournament according to their
	 * {@code BracketType}
	 *
	 *@param bracketType the {@code BracketType} in which the {@code Round } would be added
	 *@param round the {@link Round} to be added
	 */
	private void addRound(BracketType bracketType, Round round )
	{
		if ( bracketType != BracketType.INITIAL_BRACKET )
		{
			List<Round> bracketRounds  = rounds.get( bracketType );
			bracketRounds.add( round );//appends the round
			rounds.put( bracketType	, bracketRounds ); 
		}
		else
		{
			List<Round> bracketRounds = new ArrayList<>() ;
			bracketRounds.add( round );
			rounds.put( bracketType	, bracketRounds ); //overwrites the INITIAL_BRACKET
		}
		
	}
	
	
	/**
	 * 
	 *Gets a Round object that encapsulates the fixtures of the current Round of a specified
	 *type
	 *@param type
	 *@return {@link  Round }
	 *@throws RoundIndexOutOfBoundsException
	 */
	
	public Round getCurrentRound( BracketType type ) 
			throws RoundIndexOutOfBoundsException
	{
		if( type == BracketType.INITIAL_BRACKET && getCurrentRoundNum() > 0 )
			throw new RoundIndexOutOfBoundsException( "The round index is out of bound . " + getCurrentRoundNum());
		return getBracketRound( getCurrentRoundNum() , type );
	}
	
	public Round getBracketRound(int roundNum ,  BracketType type)
			throws RoundIndexOutOfBoundsException
	{
		List<Round> typeRounds = rounds.get( type );
		
		if( type == BracketType.TOURNAMENT_FINAL )
		{
			return typeRounds.get( typeRounds.size() - 1  );
		}
		else if ( roundNum <  typeRounds.size()  && typeRounds.size() >= 0 )
		{
			return typeRounds.get( roundNum );
		}
		else
			throw new RoundIndexOutOfBoundsException("The Round index is higher than expected" );
	}
	
	

	
	/**
	 * This method is inherited from {@link Tournament}. It is used to set the reesult of the 
	 * current {@link Round }. It sets the results by first verifying if the fixture is in the 
	 * {@link BracketType } the current {@link Round }.  
	 * 
	 * 
	 * Throws NoFixtureException if competitorOne and CompetitorTwo are not found in the current
	 * Round object
	 * @throws ResultCannotBeSetException 
	 * @see {@link BracketType, Round }
	 */
	@Override
	public void setResult(Competitor competitorOne, double score1, 
			double score2, Competitor competitorTwo) throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException 
	{
		
		if ( score1 == score2 )//there's a tie
			return ;
		
		try 
		{
			if ( getActiveCompetitors().length ==  2 )//tournament final 
			{
				Round round = getCurrentRound( BracketType.TOURNAMENT_FINAL );
				round.setResult( competitorOne , score1, score2, competitorTwo );
				addRound(BracketType.TOURNAMENT_FINAL , round );
			}
			else if ( getCurrentFixture() == BracketType.INITIAL_BRACKET )
			{
				Round round = getCurrentRound( BracketType.INITIAL_BRACKET );
				round.setResult( competitorOne , score1, score2, competitorTwo);
				addRound(BracketType.INITIAL_BRACKET , round );
			}
			else if ( getCurrentFixture() == BracketType.WINNERS_BRACKET )
			{
				Round minor  = getCurrentRound( BracketType.MINOR_BRACKET ) ;
				Round winner  = getCurrentRound( BracketType.WINNERS_BRACKET ) ;
				
				try
				{
					minor.setResult( competitorOne , score1, score2, competitorTwo);
					
				}
				catch (NoFixtureException e1)
				{
					winner.setResult(competitorOne, score1, score2, competitorTwo);
				
				}	
			}
			else
			{
				Round major  = getCurrentRound( BracketType.MAJOR_BRACKET ) ;
				major.setResult( competitorOne , score1, score2, competitorTwo);
				
			}
			
		}
		catch(  RoundIndexOutOfBoundsException e )
		{
			e.printStackTrace();
			throw new TournamentEndedException();
			
		}
	}

	
	/**
	 * Checks of the first {@code Round} of this {@code DoubleElimination} tournament
	 * has been played
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return {@code true} when the initial round has been played
	 */
	public boolean isInitialComplete()
	{
		if ( getCurrentFixture() !=  BracketType.INITIAL_BRACKET )
			return true ;
		return false;
	}
	
	/**
	 * Moves to the next {@code Round} of this tornament and eliminates{@code Competitor}s.
	 * that have lost twice <p>
	 * It achieves this by eliminating the losers in the losers bracket
	 * and placing the losers of the winners bracket in th losers bracket
	 * for the next round<p>
	 * If it is the final, this method determines if the loser of the has
	 * lost before in the tournament before eliminating the loser. If the
	 * loser has not lost, then the final would be played again
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 * @see Competitor
	 * @see Round
	 * @see BracketType
	 */
	@Override
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException
	{
		
		if ( !hasEnded() )
			if ( !getCurrentRound().isComplete() )//contains pending fixtures or ties 
				return ;
			else 
				createNextRound( getCurrentFixture() );
		else
			throw new TournamentEndedException( "This tournament has ended." );
		
	}

	/**
	 * Creates the next round in the tournament based on the current fixture
	 * 
	 * @see CurrentFixture
	 * @author Oguejiofor Chidiebere
	 *@param current
	 */
	private void createNextRound(BracketType current)
	{
		List<Competitor > winnerBracket = new ArrayList<>() ;
		List<Competitor > loserBracket = new ArrayList<>() ;
		try
		{
			switch ( current )
			{
			
			case INITIAL_BRACKET:
				Arrays.stream(getCurrentRound( BracketType.INITIAL_BRACKET ).getFixtures()  )
				  .map( f -> f.getLoser() )
				  .forEach( c-> loserBracket.add( c ) );
				Arrays.stream(getCurrentRound( BracketType.INITIAL_BRACKET ).getFixtures()  )
				  .forEach( f -> winnerBracket.add(  f.getWinner()) );
				
				
				
				addRound(BracketType.	MINOR_BRACKET , new Round( 
						fixesCreator( loserBracket )
						.toArray(new Fixture[ fixesCreator( loserBracket ).size() ] ) ) );
				addRound(BracketType.WINNERS_BRACKET , new Round( 
						fixesCreator( winnerBracket )
						.toArray(new Fixture[ fixesCreator( loserBracket ).size() ] ) ) );
				
				setCurrentFixture( BracketType.WINNERS_BRACKET);
				break;
				
			case MAJOR_BRACKET:
				Arrays.stream( getCurrentRound( BracketType.MAJOR_BRACKET).getFixtures() )
				  .forEach( f -> f.getLoser().setEliminated( true ) );
				
				Arrays.stream( getCurrentRound( BracketType.MAJOR_BRACKET).getFixtures() )
				  .forEach( f -> loserBracket.add(f.getWinner() ) );
				
				if ( loserBracket.size()  > 1 )
				{
					addRound(BracketType.	MINOR_BRACKET , new Round( 
							fixesCreator( loserBracket)
							.toArray(new Fixture[ fixesCreator( loserBracket).size() ] ) ) );
					setCurrentFixture( BracketType.WINNERS_BRACKET );
				}
				else
				{
					Competitor wFinalist = Arrays.stream( getActiveCompetitors() )
							.filter( c ->! Competitor.isEqual(c, loserBracket.get(0) ))
							.findFirst()
							.get();
					
					Fixture finale = new Fixture( getSportType(), wFinalist, loserBracket.get( 0 ) );
					addRound(BracketType.TOURNAMENT_FINAL , new Round( finale) );
					setCurrentFixture( BracketType.TOURNAMENT_FINAL );
					
					topThree[ 2 ] = getCurrentRound(BracketType.MAJOR_BRACKET )
									.getLosers()[0];
					
				}
				break;
			case WINNERS_BRACKET:
				
				Arrays.stream( getCurrentRound( BracketType.MINOR_BRACKET).getFixtures() )
				  .forEach( f -> f.getLoser().setEliminated( true ) );
				
				Arrays.stream( getCurrentRound( BracketType.WINNERS_BRACKET).getFixtures() )
				  .forEach( f -> loserBracket.add( f.getLoser())  );
				
				Arrays.stream( getCurrentRound( BracketType.WINNERS_BRACKET).getFixtures() )
				  .forEach( f -> winnerBracket.add( f.getWinner())  );
				
				
				Arrays.stream( getCurrentRound( BracketType.MINOR_BRACKET).getFixtures() )
				  .forEach( f -> loserBracket.add( f.getWinner())  );
				
				if( winnerBracket.size() > 1 )
					addRound( BracketType.WINNERS_BRACKET , 
								new Round( fixesCreator( winnerBracket )
								.toArray(new Fixture[ fixesCreator( winnerBracket ).size() ] ) ) 
						);
				
				
				
				addRound(BracketType.MAJOR_BRACKET , new Round( 
						fixesCreator( loserBracket )
						.toArray(new Fixture[ fixesCreator( loserBracket ).size() ] ) ) );
				
				setCurrentFixture( BracketType.MAJOR_BRACKET);
				break;
			
			case TOURNAMENT_FINAL:
				
				Round round = getCurrentRound( BracketType.TOURNAMENT_FINAL );
				Fixture  f =  round.getFixtures()[0];
				
				int roundNum = getNumberOfRounds( BracketType.TOURNAMENT_FINAL );
				if ( roundNum == 2  )
				{
					if ( Competitor.isEqual( f.getCompetitorTwo() , f.getLoser() ))
					{
						Arrays.stream( round.getLosers()  )
						.forEach( c -> c.setEliminated( true ) );
						topThree[ 1 ] = f.getLoser();
						topThree[ 0 ] = f.getWinner();
					}
						
					else
						addRound(BracketType.TOURNAMENT_FINAL   , 
								new Round( new Fixture( getSportType(),  f.getCompetitorTwo(), f.getCompetitorOne()) ) );
				
				}
				else
				{
					Arrays.stream( round.getLosers()  )
					.forEach( c -> c.setEliminated( true ) );
					topThree[ 1 ] = f.getLoser();
					topThree[ 0 ] = f.getWinner();
				}

				
				break;
			}
		}
		catch(  RoundIndexOutOfBoundsException e )
		{
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	/**
	 * Gets the number of rounds that have been played in a specific BracketType
	 *@param type the {@link BracketType }
	 *@return an {@code int}
	 */
	private int getNumberOfRounds( BracketType type )
	{
		return rounds.get(type).size();
	}
	
	/**
	 * Creates the fixtures with a list of competitors
	 */
	private List<Fixture> fixesCreator( List<Competitor> bracket )
	{
		List<Fixture> list =  new ArrayList<>();
		for ( int i = 0 ; i < bracket.size() ; i+= 2 )
			list.add(  new Fixture( getSportType(), 
					bracket.get( i ), bracket.get( i + 1 )));

		return list;
	}

	
	@Override
	public Round getCurrentRound() throws TournamentEndedException
	{

		List<Fixture> fixtures = 
				new ArrayList<>( getActiveCompetitors().length / 2 );
		
		try
		{ 
			if ( getActiveCompetitors().length == 2 )
				return getCurrentRound( BracketType.TOURNAMENT_FINAL );
			else if ( getCurrentFixture() == BracketType.INITIAL_BRACKET )
				return getCurrentRound( BracketType.INITIAL_BRACKET )  ;
			else if ( getCurrentFixture() == BracketType.MAJOR_BRACKET )
			{
				return getCurrentRound( BracketType.MAJOR_BRACKET ) ; 
			}
			
			fixtures.addAll( 
					Arrays.asList( getCurrentRound( BracketType.MINOR_BRACKET ).getFixtures() ));
			fixtures.addAll( 
					Arrays.asList( getCurrentRound( BracketType.WINNERS_BRACKET ).getFixtures() ));
			
			return new Round( fixtures.toArray( new Fixture[ fixtures.size() ] 	))  ;
		}
		catch ( RoundIndexOutOfBoundsException  e ) 
		{
			e.printStackTrace();
			throw new TournamentEndedException();
		}
		
		
	}

	
	@Override
	public Competitor getWinner()
	{
		if ( getActiveCompetitors().length != 1 )
			return null;
		else
			return getActiveCompetitors()[ 0 ]; 
	}

	@Override
	public boolean hasEnded()
	{
		if ( getActiveCompetitors().length == 1 )
			return true ;
		return false ; 
	}

	
	@Override
	public Round[] getRoundArray()
	{
		LinkedList<Fixture > list = new LinkedList<>();
		
		List<Round> rounds = new  LinkedList<>() ;
		try 
		{ 
			for ( int i  = 0 ; i  < getCurrentRoundNum() ; i ++ )
			{	
				list.addAll(  Arrays.asList(  
								getBracketRound( i ,  BracketType.MINOR_BRACKET ).getFixtures() 
							) ) ; 
				
				list.addAll(  Arrays.asList(  
						getBracketRound( i ,  BracketType.WINNERS_BRACKET ).getFixtures() 
					) ) ; 
				
				list.addAll(  Arrays.asList(  
						getBracketRound( i ,  BracketType.MAJOR_BRACKET ).getFixtures() 
					) ) ; 
				rounds.add( new Round ( list.toArray( new Fixture[ list.size() ] ) ) ) ;
			}
			
		}
		catch ( RoundIndexOutOfBoundsException e )
		{
			e.printStackTrace(); 
		}
		
		return rounds.toArray( new Round[ rounds.size() ] );
		
	}

	

	@Override
	public String toString()
	{
		if ( hasEnded() )
			return "Tournament Has Ended";
		
		StringBuilder builder = new StringBuilder();
		
		int totalCompetitors = getActiveCompetitors().length;
	
		
		if ( getCurrentFixture() != BracketType.MAJOR_BRACKET )
		{
			switch( totalCompetitors )
			{
			case 2 : 
				builder.append( "Final" );
				break;
			case 4 :
				builder.append( "Semi-Final" );
				break;
			case 8 :
				builder.append( "Quarter-Final" );
				break;
			
				default:
					builder.append( "Round of " + totalCompetitors );
					
			}
			
			roundName =  isInitialComplete() ? builder.toString() : 
							"Initial Round";
			return roundName ;
		}
		
		return "Major " + roundName; 
	}
	

	/**
	 * This enum is used to determine the current fixture of the current
	 * round It is also used to return some Bracket specific fixtures 
	 * @see Round
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	public enum BracketType
	{
		MAJOR_BRACKET, 
		MINOR_BRACKET,
		WINNERS_BRACKET,
		INITIAL_BRACKET,
		TOURNAMENT_FINAL ;
	}

	
	/**
	 * Checks if the current {@code Round} is the final
	 *@return {@code true} when only two {@code Competitor}s are left
	 */
	public boolean isFinal()
	{
		return getActiveCompetitors().length  <= 2 ? true : false ;
	}

	/**
	 * Gets a {@link BracketType } that indicates the current fixtures
	 * 
	 *@return
	 */
	public BracketType getCurrentFixture()
	{
		return currentFixture;
	}


	private void setCurrentFixture(BracketType currentFixture)
	{
		this.currentFixture = currentFixture;
	}



	



	public boolean isMinorFixtureComplete()
	{
		if ( getCurrentFixture() !=  BracketType.WINNERS_BRACKET )
			return true ;
		return false;
	}

	@Override
	public Competitor[] getTopThree()
	{
		if ( hasEnded() )
			return topThree;
		return null;
		
	}
	
}
