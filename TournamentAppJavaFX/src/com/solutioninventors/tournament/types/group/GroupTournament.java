/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.types.group;

import java.util.Arrays;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.OnlyOutstandingAreLeftException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/**
 * This is the su[er class of all the group tournament types
 * Its sub classes include RoundRobin and SwissTournamnet types 
 * The class stores the StandingTable object to be  used by all its sub class
 * It also stores a Round array and the sportType
 * The SportType object here is used to create the StandingTable object
 * This is a relatively simple class that contains some abstract methods and a 
 * bunch of getters and setters 
 * 
 */

public abstract class GroupTournament extends Tournament 
{
	
	private StandingTable table;
	
	private Round[] rounds;
	
	public GroupTournament(Competitor[] comps, SportType type, double pWin, double pDraw, double pLoss,
			TieBreaker breaker) throws InvalidBreakerException, TournamentException {
		super(type, comps);
		
		if ( comps.length < 3 )
			throw new TournamentException("A group tournament must have total comopettitors > 2" );
		
		if ( breaker == null || !Arrays.stream( breaker.getBreakers() )
				.allMatch( b -> b.getType() == Breaker.GROUP_BREAKER ||
							b.getType() ==  Breaker.BOTH ))
			throw new InvalidBreakerException("The breaker is invalid");
		table = new StandingTable( getSportType(), comps, pWin, pDraw, pLoss, breaker);
		setName("");
	}


	public StandingTable getTable() {
		return table;
	}

	
	

	protected void setRoundsArray(Round[] rnds) {
		rounds = rnds;
	}

	public Round[] getRoundArray() {
		return rounds;
	}

	public Round getRound(int roundNum) throws RoundIndexOutOfBoundsException {
		if (roundNum < getRoundArray().length)
			return getRoundArray()[roundNum];
		throw new RoundIndexOutOfBoundsException();
	}

	protected void setCurrentRound(Fixture[] fixes) {
		rounds[getCurrentRoundNum()] = new Round(fixes);
	}


	public Round getCurrentRound() 
			throws TournamentEndedException, OnlyOutstandingAreLeftException 

	{
		if (getCurrentRoundNum() < getRoundArray().length)
			return getRoundArray()[getCurrentRoundNum()];
		else if ( hasEnded() )
			throw new TournamentEndedException( "This tournament is over");
		else
			return null ; 
	}

	public abstract void setResult(Competitor com1, double score1, double score2, Competitor com2)
			throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException ;

	public abstract void moveToNextRound() throws MoveToNextRoundException;

	public abstract boolean hasEnded();

	public abstract Competitor getWinner();

	public int getTotalNumberOfRounds() {
		return getRoundArray().length;
	}

	

	public String toString() {
		return "Round " + getCurrentRoundNum();
	}

	public boolean isCurrentRoundComplete() throws TournamentEndedException
	{
		
		try
		{
			if ( !hasEnded() && getCurrentRound().isComplete() )
				return true;
		}
		catch (OnlyOutstandingAreLeftException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
