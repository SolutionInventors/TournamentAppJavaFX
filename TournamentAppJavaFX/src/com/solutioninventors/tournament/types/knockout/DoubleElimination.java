/**
 *@Author: Oguejiofor Chidiebere
 *DoubleElimination.java
 *Aug 6, 2017
 *10:38:53 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.io.IOException;
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

public class DoubleElimination extends EliminationTournament
{
	

	private static final long serialVersionUID = 3157150642503299616L;
	/** 
	 * 
	 * This is used to create a double elimination tournament
	 * The class rounds is stored in the Map&lt BracketType, List&ltRound&gt &gt object 
	 * The BracketType objects stores the info about the bracket its values include 
	 * BracketType.WINNERS_BRACKET , BracketType.MAJOR_BRACKET , BracketType.MINOR_BRACKET ,
	 * BracketType.INITIAL_BRACKET , BracketType.TOURNAMENT_FINAL  
	 * 
	 * The first round is stored in the Map<BracketType, List<Round>> object as BracketType.INITIAL_BRACKET
	 * After the first round, the winners and losers brackets are created and
	 * Rounds are now  stored in BracketType.WINNERS_BRACKET , BracketType.MAJOR_BRACKET and BracketType.MINOR_BRACKET 
	 * This is repeated for subsequent rounds with players being eliminated after each round
	 * until only two competitors are left( the Tournament final )
	 * At this point the round is stored in the map with key value BracketType.TOURNAMENT_FINAL
	 * 
	 * Note that a player is eliminated only when he has lost two games in the tournament 
	 * 
	 * Note also that rounds between the initial round( the first round ) and the tournament final
	 * each round has a loserBracket minor Round and loserBracket major round
	 * Thus boolean minorComplete is used to check the currentFixtures 
	 * 
	 * Also method moveToNextRound() does not always incrementROundNum. It determines whether the
	 * major bracketRound has been played before incrementing
	 * 

	 * This class contains some operations that aid tournament simulation 
	 * 
	 * Currently this class does not allow away matches however it may be added inn later versions
	 * 
	 */
	private boolean minorComplete;
	private boolean initialComplete; 
	
	
	private String roundName ;
	private Map< BracketType , List<Round> > rounds;
	
	/**
	 * 
	 *@param comps
	 *@throws TournamentException
	 *
	 */
	public DoubleElimination( SportType type, Competitor[] comps) throws TournamentException
	{
		super(type, comps);
		rounds = new HashMap<>();
		List<Round> minor = new ArrayList<>(); //used to increase index to 1
		List<Round> major = new ArrayList<>(); //used to increase index to 1
		
		rounds.put( BracketType.WINNERS_BRACKET , new ArrayList<Round>() );
		rounds.put( BracketType.MAJOR_BRACKET ,major );
		rounds.put( BracketType.MINOR_BRACKET ,   minor);
		rounds.put( BracketType.INITIAL_BRACKET , new ArrayList<>() );
		rounds.put( BracketType.TOURNAMENT_FINAL, new ArrayList<>() );
//		setLoserMajorRund( true );
		createTournament();
	}
	
	
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
	 *This overloaded method 
	 *It returns a Round object that encapsulates the fixtures of the current Round
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
			else if ( !initialComplete )
			{
				Round round = getCurrentRound( BracketType.INITIAL_BRACKET );
				round.setResult( competitorOne , score1, score2, competitorTwo);
				addRound(BracketType.INITIAL_BRACKET , round );
			}
			else if ( !minorComplete )
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

	

	public boolean isInitialComplete()
	{
		return initialComplete;
	}
	
	
	@Override
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException
	{
		
		if ( !hasEnded() )
			if ( !getCurrentRound().isComplete() )//contains pending fixtures or ties 
				return ;
			else if ( getActiveCompetitors().length  == 2 )
			{
				createNextRound( CurrentFixture.FINAL);
				incrementRoundNum() ;
			}
			else if( !initialComplete )
			{
				createNextRound( CurrentFixture.INITIAL);
				initialComplete = true ;
			}
			else if ( !minorComplete )
			{
				minorComplete = true ;
				createNextRound( CurrentFixture.WINNER_BRACKET );
				
			}
			else
			{
				minorComplete = false;
				createNextRound( CurrentFixture.LOSER_MAJOR_BRACKET );
				incrementRoundNum();
			}
		else
			throw new TournamentEndedException( "This tournament has ended." );
		
	}

	private void createNextRound(CurrentFixture current)
	{
		List<Competitor > winnerBracket = new ArrayList<>() ;
		List<Competitor > loserBracket = new ArrayList<>() ;
		try
		{
			switch ( current )
			{
			
			case INITIAL:
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
				break;
				
			case LOSER_MAJOR_BRACKET:
				Arrays.stream( getCurrentRound( BracketType.MAJOR_BRACKET).getFixtures() )
				  .forEach( f -> f.getLoser().setEliminated( true ) );
				
				Arrays.stream( getCurrentRound( BracketType.MAJOR_BRACKET).getFixtures() )
				  .forEach( f -> loserBracket.add(f.getWinner() ) );
				
				if ( loserBracket.size()  > 1 )
					addRound(BracketType.	MINOR_BRACKET , new Round( 
							fixesCreator( loserBracket)
							.toArray(new Fixture[ fixesCreator( loserBracket).size() ] ) ) );
				else
				{
					Competitor wFinalist = Arrays.stream( getActiveCompetitors() )
							.filter( c ->! Competitor.isEqual(c, loserBracket.get(0) ))
							.findFirst()
							.get();
					
					Fixture finale = new Fixture( getSportType(), wFinalist, loserBracket.get( 0 ) );
					addRound(BracketType.TOURNAMENT_FINAL , new Round( finale) );
					
					
					
					
				}
				break;
			case WINNER_BRACKET:
				
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
				break;
			
			case FINAL:
				
				Round round = getCurrentRound( BracketType.TOURNAMENT_FINAL );
				Fixture  f =  round.getFixtures()[0];
				
				int roundNum = getNumberOfRounds( BracketType.TOURNAMENT_FINAL );
				if ( roundNum == 2  )
				{
					if ( Competitor.isEqual( f.getCompetitorTwo() , f.getLoser() ))
						Arrays.stream( round.getLosers()  )
							.forEach( c -> c.setEliminated( true ) );
					else
						addRound(BracketType.TOURNAMENT_FINAL   , 
								new Round( new Fixture( getSportType(),  f.getCompetitorTwo(), f.getCompetitorOne()) ) );
					
				}
				else
				{
					Arrays.stream( round.getLosers()  )
					.forEach( c -> c.setEliminated( true ) );
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
	
	private int getNumberOfRounds( BracketType type )
	{
		return rounds.get(type).size();
	}
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
			else if ( !initialComplete )
				return getCurrentRound( BracketType.INITIAL_BRACKET )  ;
			else if ( minorComplete )
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
		StringBuilder builder = new StringBuilder();
		
		int totalCompetitors = getActiveCompetitors().length;
	
		
		if ( !minorComplete )
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
			
			roundName = builder.toString() ;
			return roundName ;
		}
		
		return "Major " + roundName; 
	}
	

	
	public enum BracketType
	{
		MAJOR_BRACKET, 
		MINOR_BRACKET,
		WINNERS_BRACKET,
		INITIAL_BRACKET,
		TOURNAMENT_FINAL ;
	}

	public boolean isMinorFixtureComplete()
	{
		return minorComplete;
	}
	
	public boolean isFinal()
	{
		return getActiveCompetitors().length  <= 2 ? true : false ;
	}

	private enum CurrentFixture
	{
		LOSER_MAJOR_BRACKET,
		FINAL,
		INITIAL,
		WINNER_BRACKET;
	}
	
}
