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
import java.util.Set;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class DoubleElimination extends EliminationTournament
{
	/** 
	 * 
	 * This is used to create a double elimination tournament
	 * The class rounds is stored in the Map< BracketType, List<Round> > object 
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
	
	private boolean AWAY_MATCH;
	private String roundName ;
	private Map< BracketType , List<Round> > rounds;
	
	/**
	 * 
	 * Sep 11, 2017
	 *@param comps
	 *@throws TournamentException
	 *
	 */
	public DoubleElimination(Competitor[] comps) throws TournamentException
	{
		super(comps);
		AWAY_MATCH = false ;
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
			roundFixtures.add( new Fixture( competitors[ i ] , competitors[ i+ 1] ) );
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
	 * @see {@link BracketType, Round }
	 */
	@Override
	public void setResult(Competitor competitorOne, double score1, 
			double score2, Competitor competitorTwo) throws NoFixtureException, TournamentEndedException 
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

	


	
	
	@Override
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException
	{
		
		if ( !hasEnded() )
			if ( !getCurrentRound().isComplete() )//contains pending fixtures or ties 
				return ;
			else if ( getActiveCompetitors().length  == 2 )
			{
				createNextRound();
				incrementRoundNum() ;
			}
			else if( !initialComplete )
			{
				createNextRound();
				initialComplete = true ;
			}
			else if ( !minorComplete )
			{
				minorComplete = true ;
				createNextRound();
				
			}
			else
			{
				minorComplete = false;
				createNextRound();
				incrementRoundNum();
			}
		else
			throw new TournamentEndedException( "This tournament has ended." );
		
	}


	private void createNextRound()
	{
		List<Competitor > winnerBracket = new ArrayList<>() ;
		List<Competitor > loserBracket = new ArrayList<>() ;
		List<Fixture> winnerFixes = new ArrayList<>();
		List<Fixture> loserFixes = new ArrayList<>();
		
		//eliminate competitors and create brackets
		Arrays.stream( getCompetitors() )
			.filter( c -> !c.isEliminated() )
			.forEach( c -> {
				if( c.getNumberOfLoss() >= 2 )
					c.setEliminated( true );
				else if ( c.getNumberOfLoss() == 1 )
					loserBracket.add( c );
				else if ( c.getNumberOfLoss() == 0  )
					winnerBracket.add( c );
					
					
			});
		
		
		
		if ( getActiveCompetitors().length <= 1 )//tournament has ended 
			return ; 
		else if ( getActiveCompetitors().length  == 2 )
		{
			Fixture tourFinal = new Fixture( getActiveCompetitors()[ 0 ] , getActiveCompetitors()[ 1 ] ) ;
			Fixture[] array = { tourFinal };
			
			addRound( BracketType.TOURNAMENT_FINAL , new Round( array ) );
		}
		else
		{
			if( winnerBracket.size() > 1 )
				for ( int i = 0 ; i < winnerBracket.size() ; i+= 2 )
					winnerFixes.add( new Fixture( winnerBracket.get( i ), winnerBracket.get( i + 1 )));
		
		
			for ( int i = 0 ; i < loserBracket.size() ; i+= 2 )
				loserFixes.add( new Fixture( loserBracket.get( i ), loserBracket.get( i + 1 )));
			
			if ( minorComplete )
			{
				addRound(BracketType.MAJOR_BRACKET , new Round( 
						loserFixes.toArray(new Fixture[ loserFixes.size() ] ) ) );
			}
			else
			{
				addRound(BracketType.	MINOR_BRACKET , new Round( 
						loserFixes.toArray(new Fixture[ loserFixes.size() ] ) ) );
				addRound(BracketType.WINNERS_BRACKET , new Round( 
						winnerFixes.toArray(new Fixture[ winnerFixes.size() ] ) ) );
			}
		}
		
		
		
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

	
	
}
