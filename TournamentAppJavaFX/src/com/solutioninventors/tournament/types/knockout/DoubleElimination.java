/**
 *@Author: Oguejiofor Chidiebere
 *DoubleElimination.java
 *Aug 6, 2017
 *10:38:53 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import com.solutioninventors.tournament.GUI.FixturesController;
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

	private boolean winnerRoundComplete;
	
	private Map< BracketType , List<Round> > rounds;
	
	public DoubleElimination(Competitor[] comps) throws TournamentException
	{
		super(comps);
		rounds = new HashMap<>();
		List<Round> minor = new ArrayList<>(); //used to increase index to 1
		List<Round> major = new ArrayList<>(); //used to increase index to 1
		
		minor.add(null);
		major.add(null);
		rounds.put( BracketType.WINNERS_BRACKET , new ArrayList<Round>() );
		rounds.put( BracketType.MAJOR_BRACKET ,major );
		rounds.put( BracketType.MINOR_BRACKET ,   minor);
		
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
		Round winnerRound = new Round(  
				roundFixtures.toArray(new Fixture[ roundFixtures.size() ] ));
		addRound( BracketType.WINNERS_BRACKET , winnerRound );
		
	}

	public Round getCurrentRound( BracketType type ) 
			throws RoundIndexOutOfBoundsException
	{
		return getBracketRound(getCurrentRoundNum(), type );
	}
	
	public Round getBracketRound(int roundNum ,  BracketType type)
			throws RoundIndexOutOfBoundsException
	{
		
		List<Round> list = rounds.get( type );
		
		if ( list.size() <= 0 	|| roundNum >= list.size())
			throw new RoundIndexOutOfBoundsException();
		
		Round round = list.get(roundNum);
		if ( round == null )
			throw new RoundIndexOutOfBoundsException();
		return round ; 
	}
	
	private void addRound(BracketType bracketType, Round round )
	{
		List<Round> bracketRounds  = rounds.get( bracketType );
		bracketRounds.add( round );
		rounds.put( bracketType	, bracketRounds );
	}

	
	@Override
	public void setResult(Competitor com1, double score1, 
			double score2, Competitor com2) throws NoFixtureException
	{
		if ( score1 != score2 )
			getCurrentRound().setResult(com1, score1, score2, com2);
		
	}

	
//	private void setResult(BracketType majorBracket, Competitor com1,
//			double score1, double score2, Competitor com2) throws NoFixtureException
//	{
//		if ( score1 != score2 )
//			getRound(getCurrentRoundNum()).setResult(com1, score1, score2, com2);
//	}

	@Override
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException
	{
		if ( !hasEnded() )
		{
			if ( getCurrentRound().isComplete() 	)
			{
				eliminateLosers();
				createNextRound();
				if ( !isWinnerRoundComplete() || getCurrentRoundNum() == 0  )
					incrementRoundNum();
				else if ( isWinnerRoundComplete())
					toggleWinnerRoundComplete();
			}
			else
				throw new MoveToNextRoundException("The round is incomplete");
		}
		else
		{
			throw new TournamentEndedException();
		}
		
	}

	private void toggleWinnerRoundComplete()
	{
		winnerRoundComplete = !winnerRoundComplete;
		
	}

	private void createNextRound()
	{
//		precondition: is that tournament has not ended
		List<Competitor> loserBracket = new ArrayList<>();
		
		if ( !isWinnerRoundComplete() || getCurrentRoundNum() == 0 )
		{
			List<Competitor> winnerBracket = new ArrayList<>();
			List<Fixture> loserFixtures = new ArrayList<>();
			List<Fixture> winnerFixtures = new ArrayList<>();
			
			
			Arrays.stream( getCompetitors() )
				  .filter( c-> c.getNumberOfLoss() == 1 )
				  .forEach( c-> loserBracket.add( c ) );
			
			Arrays.stream( getCompetitors() )
			  .filter( c-> c.getNumberOfLoss() == 0 )
			  .forEach( c-> winnerBracket.add( c ) );
			
			
			for ( int i = 0 ; i< loserBracket.size() ; i+= 2 )
			{
				loserFixtures.add(new Fixture( loserBracket.get(i) ,
									 loserBracket.get( i+1 ) ) );
			}
			
			for ( int i = 0 ; i< winnerBracket.size() ; i+= 2 )
			{
				winnerFixtures.add(new Fixture( winnerBracket.get(i) ,
						winnerBracket.get( i+1 ) ) );
			}
			
			Round loserRound = new Round( 
					loserFixtures.toArray( new Fixture[ loserFixtures.size() ] ) );
			
			Round winnerRound = new Round( 
					winnerFixtures.toArray( new Fixture[ winnerFixtures.size() ] ) );
			
			addRound(BracketType.MINOR_BRACKET, loserRound );
			addRound(BracketType.WINNERS_BRACKET, winnerRound );
			
		}
		else
		{
			List<Fixture> loserFixtures = new ArrayList<>();
			for ( int i = 0 ; i< loserBracket.size() ; i+= 2 )
			{
				loserFixtures.add(new Fixture( loserBracket.get(i) ,
									 loserBracket.get( i+1 ) ) );	
			}
			Round loserRound = new Round( 
					loserFixtures.toArray( new Fixture[ loserFixtures.size() ] ) );
			addRound( BracketType.MAJOR_BRACKET , loserRound );
		}
		
		
		
		
	}

	private void eliminateLosers()
	{
		Arrays.stream( getActiveCompetitors() )
			   .forEach( c -> {
				   if ( c.getNumberOfLoss() >= 2 )
					   c.setEliminated( true );
			   });
		
	}

	@Override
	public boolean hasEnded()
	{
		if ( getActiveCompetitors().length == 1 )
			return true;
		return false ;
	}

	@Override
	public Round getCurrentRound() throws TournamentEndedException
	{

		List<Fixture > roundFixtures = new ArrayList<>();
		
		try
		{
			if (isWinnerRoundComplete() && currentRoundHasMajorFixture() )
				roundFixtures.addAll(Arrays.asList(getBracketRound(getCurrentRoundNum(),
						BracketType.MAJOR_BRACKET).getFixtures()) );
			else
			{
				roundFixtures.addAll(Arrays.asList(getBracketRound(getCurrentRoundNum(),
						BracketType.WINNERS_BRACKET).getFixtures()) );
				
				try
				{
					roundFixtures.addAll(Arrays.asList(getBracketRound(getCurrentRoundNum(),
							BracketType.MINOR_BRACKET).getFixtures()) );
				}
				catch (RoundIndexOutOfBoundsException e ){}
			}
		}
		catch (RoundIndexOutOfBoundsException e )
		{
			throw new TournamentEndedException( "The tournament has ended" 	);
		}
		
		return new Round( roundFixtures.toArray( new Fixture[ roundFixtures.size() ] ) );
		
		
	}

	private boolean currentRoundHasMajorFixture()
	{
		try
		{
			getBracketRound(getCurrentRoundNum(),
					BracketType.MAJOR_BRACKET).getFixtures() ;
		}
		catch (RoundIndexOutOfBoundsException e)
		{
			return false ;
		} 
		return true ;
	}

	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() 	)
			return getActiveCompetitors()[ 0 ];
		return null ;
	}

	@Override
	public Round[] getRoundArray()
	{
		List<Round> theRounds = new ArrayList<>( getCurrentRoundNum() + 1 );
		
		try
		{
			theRounds.add( getBracketRound(0 , BracketType.WINNERS_BRACKET ) );
			int roundNum = getCurrentRoundNum() ;
			while( true )
			{
				theRounds.add( getBracketRound(roundNum , BracketType.WINNERS_BRACKET ) );
				theRounds.add( getBracketRound(roundNum , BracketType.MINOR_BRACKET ) );
				theRounds.add( getBracketRound(roundNum , BracketType.MAJOR_BRACKET ) );
				roundNum++ ;
				
			}
		}
		catch (RoundIndexOutOfBoundsException e){}
		
		if ( theRounds.size() > 0 )
			return theRounds.toArray( new Round[ theRounds.size() ] );
		else
			return null ;
	}

	public enum BracketType
	{
		MAJOR_BRACKET, 
		MINOR_BRACKET,
		WINNERS_BRACKET;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		if ( isWinnerRoundComplete() )
			builder.append( "Loser Major\n" );
		else
			builder.append( "Winners and Loser Minor Brackets\n" );
		
		String message = null ;
		switch( getActiveCompetitors().length )
		{
		
		case 1: 
			message = "Tournament Ended: \nWinner is " + getWinner() ;
			break ;
		case 2 :
			message = "Final" ;
			break ;
		case  4:
			message = "Semifinal";
			break;
		case 8:
			message = "Quarter-Final";
			break;
		case 16 :
			message = "Round of 16";
			break;
		default:
			message = "Round " + ( getCurrentRoundNum() +  1) ;
		}
		builder.append( message );
		
		return builder.toString();
	}

	public boolean isWinnerRoundComplete()
	{
		return winnerRoundComplete;
	}

	public void setWinnerRoundComplete(boolean winnerRoundComplete)
	{
		this.winnerRoundComplete = winnerRoundComplete;
	}

	
	
}
