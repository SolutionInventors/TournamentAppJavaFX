/**
 * author: Oguejiofor Chidiebere
 * Aug 3, 2017
 * SwissTournament.java
 * 9:15:00 PM
 *
 */
package com.solutioninventors.tournament.types.group;

import java.util.Arrays;
import java.util.function.Predicate;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/**
 * This class is used to simulate a swiss tournament. Its constructor takes a
 * competitor array , SportType , pointing system and totalROunds Utility method
 * createTournament creates the tournament rounds
 * 
 * Then it takes results via calls to setResult Method moveToNextRound moves to
 * the next round
 * 
 * The class also contains some utility methods that aid its use
 * 

 * 
 */
public class SwissTournament extends GroupTournament {
	
	public SwissTournament(Competitor[] comps, SportType type, double winPoint, double drawPoint, double lossPoint,
			TieBreaker breaker, int totalRounds) throws TournamentException, InvalidBreakerException {
		super(comps, type, winPoint, drawPoint, lossPoint, breaker);
		if (comps.length % 2 != 0)
			throw new TournamentException("Total competitors must be a multiple of 2");

		setRoundsArray(new Round[totalRounds]);
		createTournament();
	}

	private void createTournament() {
		createCurrentRound();
	}

	@Override
	public void moveToNextRound() throws MoveToNextRoundException 
	{
		updateTables();; // updates the table
		
		try
		{
			if( !getCurrentRound().isComplete() )
				throw new MoveToNextRoundException("The current round is not yet complete");
		}
		catch (TournamentEndedException e)
		{
			throw new MoveToNextRoundException(e.getMessage() );
		}
		
		incrementRoundNum();
		if (!hasEnded()) 
		{
			createCurrentRound();
			
		} 
		else if ( getCurrentRoundNum() != getRoundArray().length)
			throw new MoveToNextRoundException("Tournament is over thus cannot move to next round");
		
	}

	private void createCurrentRound() {
		
		Competitor[] temp = getTable().getCompetitors();
		Fixture[] fixtures = new Fixture[temp.length / 2];

		for (int i = 0; i < temp.length; i += 2) {
			fixtures[i / 2] = new Fixture( getSportType(), temp[i], temp[i + 1]);

		}

		setCurrentRound(fixtures);
	}

	@Override
	public void setResult(Competitor com1,
			double score1, double score2, Competitor com2) throws NoFixtureException, ResultCannotBeSetException 
	{
		try
		{
			getCurrentRound().setResult(com1, score1, score2, com2 );
		}
		catch (TournamentEndedException e)
		{
			throw new NoFixtureException( e.getMessage() );
		}

	}

	@Override
	public boolean hasEnded() 
	{
		return getCurrentRoundNum() < getRoundArray().length ? false : true;

	}

	@Override
	public Competitor getWinner() {
		if (hasEnded()) 
		{
			return getTable().getCompetitor(0);
		}

		return null;

	}

}
