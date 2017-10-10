/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTournament.java
 *Aug 3, 2017
 *10:10:42 PM
 */
package com.solutioninventors.tournament.types.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.NoOutstandingException;
import com.solutioninventors.tournament.exceptions.OnlyOutstandingAreLeftException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/**
 * A {@code RoundRobinTournament} is a {@link GroupTournament} in which all the {@link Competitor}s
 * must face each other at least once before this tournament ends
 * 
 * <p>
 * The {@link Round}s of a {@code RoundRobinTournament} are created once its
 * constructor is called. 
 * These {@code Round}s are created in such a way that one {@code Competitor} would never play
 * three consecutive home/away games.
 * The winner of a {@code RoundRobin } is determined by a {@code StadingTable}
 * which contains a {@link TieBreaker} that breaks the ties between {@code Competitor}s
 * The number of {@code Competitor}s in a {@code RoundRoubin} can be any number greater than 2
 * <p>
 * A {@code RoundRobinTournament} can have outstanding in a fixture
 * 
 * 
 */
public class RoundRobinTournament extends GroupTournament
{
	/**
	 * Stores {@code true } if there are home and away fixtures. That is if
	 * a {@code Competitor} would meet all opponent twice
	 */
	private final boolean HOME_AND_AWAY_FIXTURES;
	
	/**
	 * Stores all the outstanding games in this {@code Tournament}
	 */
	private List<Fixture> outstandingMatches ;
	
	public RoundRobinTournament(Competitor[] comps, SportType type , double winPoint ,
								double drawPoint, double lossPoint , TieBreaker breaker , boolean away ) throws InvalidBreakerException, TournamentException
	{
		super(comps, type, winPoint , drawPoint, lossPoint , breaker  );
		HOME_AND_AWAY_FIXTURES =  away ;
		setRoundsArray( createRounds( getCompetitors() ) ) ;
		outstandingMatches =  new ArrayList<>();
	}
	
	
	/**
	 * Creates rounds by assigning numbers to each competitor. 
	 * The method achieves this by implementing the the  Carousel-Berger system
	 * You can read up Carousel-Berger system online for more info
	 * This method also allows byes 
	 */
	private Round[] createRounds(Competitor[] competitor )
	{
		
		
		int tempCurrentRound = 0 ;
		int totalCompetitors = getCompetitors().length ;
		
		int[][] fixureCreator = new int [ hasBye() ? 
											totalCompetitors + 1 : totalCompetitors/2 ][ 2 ];
		
		Round[] tempRound = new Round[ hasBye() ? totalCompetitors : totalCompetitors  - 1 ];
		fixureCreator[ 0 ][ 0 ] = hasBye() ? 0 : 1 ; //this value does not change in the loop
		
		int initialValue = fixureCreator[ 0 ][ 0 ] + 1 ; // loop begins writing numbers with this value
	
		
		for( int beginValue = initialValue ; beginValue <= totalCompetitors  ; beginValue++ ) //loop creates each round
		{

			int num = beginValue;
			
			for( int row = 1 ; row < fixureCreator.length ; row++ )
			{
				fixureCreator[ row ][ 0 ] = num ; //set home team number
				num++ ;
				num = num > totalCompetitors? initialValue : num ;
			}
			
			for ( int row  = fixureCreator.length - 1 ; row >= 0 ;  row -- )
			{
				fixureCreator[ row ][ 1 ] = num ;//set away team number
				num++ ;
				num = num > totalCompetitors? initialValue : num ;
			}
			
			tempRound[ tempCurrentRound] = 
					convertToRound( fixureCreator  ); //convert int[][] to Round 
			tempCurrentRound++ ;
			
		}
		
		return scheduleRounds( tempRound ); 
	}

	/**
	 * This methos covverts an int[][] to a Round object
	 * precondition: fixes = int[ numberOfCompetitors /2][ 2 ] if no bye or
	 * 				 fixes = int[ numberOfCompetitors/2 + 1][ 2 ]  if bye
	 * This method skips the first row if bye
	 */
	private Round convertToRound(int[][] fixes ) 
	{
		
		Competitor[] competitors = getCompetitors();
		Fixture[] fixtures = new Fixture[ competitors.length /2 ];
		
		int skip = hasBye() ? 1 : 0 ;
		
		for ( int i = 0 ; i < fixtures.length ; i ++ )
		{
			fixtures[ i ] = new Fixture ( getSportType(), competitors[ fixes[ i + skip ][ 0 ]  - 1 ],
			                              competitors[ fixes[ i + skip ][ 1 ] - 1 ] );
		}
		
		return new Round ( fixtures );
		
	}

	/**
	 * Schedules the tournament such that a Competitor would not have too
	 * many consective home games or too many consecutive away games
	
	 */
	private Round[] scheduleRounds(Round[]  rounds )
	{
		
		Round[] inverseRounds = new Round[ rounds.length ];
		
		for ( int i = 0 ; i < rounds.length ; i++ )
		{
			inverseRounds[ i ] = rounds[ i ].invertHomeAndAway( getSportType() ) ;
			Collections.shuffle( Arrays.asList( inverseRounds[ i ].getFixtures() )); 
		}
		
		
		int numOfRounds = hasHomeAndAwayFixtures() ? rounds.length * 2 : rounds.length  ; 
		Round[] finalRounds =  new Round[ numOfRounds  ];
		
		
		for( int i = 0 ; i < rounds.length ; i++ )
		{
			if( i % 2  == 0 )
				finalRounds[ i ] = rounds[ i ];
			else
				finalRounds[ i ] = inverseRounds[ i ];
			
		}
		
		if ( hasHomeAndAwayFixtures() )
		{
			
			for( int i = 0 ; i < rounds.length ; i++ )
			{
				if( i % 2  == 0 )
					finalRounds[ i + rounds.length ] = inverseRounds [ i ];
				else
					finalRounds[ i + rounds.length ] = rounds[ i ];
				
			}
			
			//shuffle rounds after mid tournament
			for ( int i = 0 ; i < rounds.length ; i ++ ) 
			{
				rounds[ i ] = finalRounds[ i ];
				inverseRounds[ i ] = finalRounds[ i + rounds.length ];
			}
			
			Collections.shuffle( Arrays.asList( rounds ) );
			Collections.shuffle( Arrays.asList( rounds ) );
			
			for( int i = 0 ; i < rounds.length ; i  ++ )
			{
				finalRounds[ i ] = rounds[ i ];
				finalRounds[ i + rounds.length ] = inverseRounds[ i ] ;
			}
			
		}
		
		return finalRounds ;
	}

	public boolean hasHomeAndAwayFixtures()
	{
		return HOME_AND_AWAY_FIXTURES;
	}

	public boolean hasBye()
	{
		return getCompetitors().length % 2 != 0;
	}

	
	
	
	@Override
	public void moveToNextRound() throws  TournamentEndedException, MoveToNextRoundException
	{
		Round[] rnds = getRoundArray();
		
		if ( getCurrentRoundNum() < rnds.length  )
		{
			
			updateTables();
			
			if ( !getCurrentRound().isComplete() ) //contains outstanding
			{
				
				Arrays.stream( getCurrentRound().getPendingFixtures())
				  .forEach( f -> outstandingMatches.add( f ) );
				incrementRoundNum();
				
			}
			else
			{
				incrementRoundNum();
				if ( getCurrentRoundNum() >= getRoundArray().length && 
						outstandingMatches.size() > 0 )//tournament is in last round
				{
					throw new MoveToNextRoundException( "Only outstanding is left");
				}
			}
		
			
			
		}
		else if ( outstandingMatches.size() > 0 )
		{
			String message = "Only outsanding matches are left";
			JOptionPane.showMessageDialog( null , message ) ;
		}
		else
		{
			throw new TournamentEndedException("Tournament is over thus cannot move to next round" );
		}
		
	}

	public boolean hasEnded()
	{
		return getCurrentRoundNum() >= getRoundArray().length &&
			outstandingMatches.size() <= 0 ? true : false ;	
	}
	
	
	
	@Override
	public void setResult( Competitor com1 , double score1 , 
			double score2 , Competitor com2 ) throws NoFixtureException, ResultCannotBeSetException
	{	
		try 
		{
			try
			{
				getCurrentRound().setResult(com1, score1, score2, com2);
				
			}
			catch (OnlyOutstandingAreLeftException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch(  TournamentEndedException e )
		{
			throw new NoFixtureException( e.getMessage() );
			
		}
		
	}
	
	/**
	 * Sets the result of the outstaning {@code Fixture} in this {@code RoundRobin}
	 
	 @param com1 the home {@code Competitor}
	 *@param score1 the home {@code Competitor}'s score
	 *@param score2 the away {@code Competitor}'s score
	 *@param com2 the away {@code Competitor}
	 *@throws NoFixtureException when the {@code Fixture } is not found
	
	 */
	public void setOutstandingResult( Competitor com1 , double score1 , double score2 , Competitor com2 ) 
			throws NoFixtureException
	{		
		if ( outstandingMatches.stream()
				.anyMatch( f -> f.hasFixture( com1 , com2  )) )
		{
			
			for( int i = 0 ; i < outstandingMatches.size() ; i ++ )
			{
				if ( outstandingMatches.get( i ).hasFixture( com1, com2) )
				{
					Fixture f =  outstandingMatches.get( i );
					Round temp = new Round( f );
					
					
					try
					{
						temp.setResult( f.getCompetitorOne(), score1, score2, f.getCompetitorOne());
					}
					catch (ResultCannotBeSetException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outstandingMatches.remove( i );
					updateTables();
					break ;
				}
			}
		}
		else
		{
			throw new NoFixtureException( "Fixture not found in outstanding" );
		}
	}
	
	public Fixture[] getOutstanding() throws NoOutstandingException
	{
		if ( outstandingMatches.size() > 0 )
		{
			Fixture[] fixes = new Fixture [ outstandingMatches.size() ];
			outstandingMatches.toArray( fixes ) ;
			return fixes ;
		}
		throw new NoOutstandingException( ) ; 
		
	}

	@Override
	public Round getCurrentRound() 
			throws TournamentEndedException, OnlyOutstandingAreLeftException 
	{
		if (getCurrentRoundNum() < getRoundArray().length)
			return getRoundArray()[getCurrentRoundNum()];
		else if ( hasEnded() )
			throw new TournamentEndedException( "This tournament is over");
		else
			throw new OnlyOutstandingAreLeftException("Only otustanding fixtures are left" );
	}
	public Competitor getWinner()
	{ 
		if ( hasEnded())
			return getTable().getCompetitors()[ 0 ];
		return null;
	}

	
	
	
	
}
