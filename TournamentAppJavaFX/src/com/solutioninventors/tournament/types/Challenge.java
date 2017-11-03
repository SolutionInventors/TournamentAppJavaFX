/**
 *@Author: Oguejiofor Chidiebere
 *Challenge.java
 *Aug 9, 2017
 *11:42:42 PM
 */
package com.solutioninventors.tournament.types;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.group.StandingTable;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/** 
 * 
 * This relatively simple class is used to simulate a Challenge which is an alternative to TOurnament
 * The Challenge can only have two competitors 
 * @author Oguejiofor Chidiebere
 * @since 2017
 * @since 1.0
 */

public class Challenge extends Tournament 
{

	private static final long serialVersionUID = 745303018037565536L;
	/**
	 * Contains all the rounds in this {@code Tournament}
	 */
	private final Round[] ROUNDS;
	
	
	/**
	 * The constructor creates all the rounds in the tournament via a call to utility method 
	 * {@linkplain createTournament }. 
	 * The rounds are created in such a way that one competitor does not  play too many consecutive home or aeay games 
	 * This is very important in games like chess
	 * @param type the {@code SportType } of this {@code Tournament}
	 * @param numOfRounds the number of rounds that would be played in this {@code Challenge}
	 * @author Oguejiofor Chidiebere
	 * @param competitors  the two {@code Competitor}s in the tournament
	 * @throws TournamentException 
	 */
	public Challenge( SportType type, Competitor[] competitors , int numOfRounds 	) throws TournamentException
	{
		super( type ,  competitors );
		
		ROUNDS = new Round[ numOfRounds ];
		
		createTournament();
		
	}

	
	/**
	 * This method creates all the rounds in this {@code Challenge} 
	 * It ensures that a competitor does not play too many consecutive home fixtures
	 * or too many away {@link Fixture}s 
	 */
	private void createTournament()
	{
		
		Fixture fixture ;
		for ( int i = 0 ; i < ROUNDS.length ; i++ )
		{
			fixture = new Fixture( getSportType(),  getCompetitors()[ i % 2 == 0 ? 0 : 1], 
					getCompetitors()[ i % 2 == 0 ? 1 : 0 ] );
			getRoundArray()[ i ] = new Round( fixture , "Round " + (i+1) );
		}
	}

	@Override
	public Round[] getRoundArray()
	{
		return ROUNDS ;
	}
	@Override
	public void moveToNextRound() throws  TournamentEndedException
	{
		if ( !hasEnded() && getCurrentRound().isComplete() 	)
			incrementRoundNum();
		else if ( !getCurrentRound().isComplete() )
			JOptionPane.showMessageDialog(null , "Results not yet inputed" );//replace with Javafx
		else
			JOptionPane.showMessageDialog(null, "Tournamnet has ended");//replace with Javafx
		
	}
	
	
	public void setResult(Competitor com1 , double score1 , double score2 , Competitor com2) 
			throws TournamentEndedException, ResultCannotBeSetException, NoFixtureException
	{
		
		
		if ( !hasEnded() )
		{
			getCurrentRound().setResult(com1, score1, score2, com2);
		}
	}
	
	@Override
	public Round getCurrentRound() throws TournamentEndedException
	{
		if ( getCurrentRoundNum() < getRoundArray().length )
			return getRoundArray()[ getCurrentRoundNum() ];
		throw new TournamentEndedException();
	}

	@Override
	public boolean hasEnded()	
	{
		return  getCurrentRoundNum()   <  ROUNDS.length  ? false : true ;
		
	}

	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
		{
			Function<Competitor, Integer> function = c-> c.getNumberOfWin()  ; 
			
			
			
			Comparator<Competitor> comparator = 
					Comparator.comparing( function ).reversed();
			
			
			Competitor[] coms = new Competitor[ 2 ]; 
			
			return  Arrays.stream( getCompetitors() )
					.sorted( comparator )
					.collect(Collectors.toList())
					.toArray( coms )[ 0 ] ;
		}
		return null ;
	}
	
	/**
	 * Gets a {@code StandingTable }object that shows the results of the two competitors
	 * Returns {@code null} if an error occurs.
	 *@return {@code StandingTable} containing competitor results
	 */
	public StandingTable getStandingTable(){
		
		TieBreaker breaker;
		try {
			breaker = new TieBreaker();
			return new StandingTable(getSportType(),
					getCompetitors(), 2, 1, 0, breaker);
		} catch (InvalidBreakerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String toString()
	{
		if ( hasEnded() )
			return "Challenge is Over";
		
		return "Round " + ( getCurrentRoundNum() + 1 ); 
	}

	
}
