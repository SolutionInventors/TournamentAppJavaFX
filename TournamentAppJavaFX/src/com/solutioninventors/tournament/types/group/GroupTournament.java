/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.types.group;

import java.util.Arrays;
import java.util.List;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.OnlyOutstandingAreLeftException;
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
 * A {@code GroupTournament} is used to simulate a {@code Tournament} in which
 * {@code Competitor}s are arranged in a group and a ranking table is used
 * to determine the winner after all the {@code Round}s have been played
 * <p>
 * This class is abstract and delays methods common to all it types.
 * <p>
 * Some concrete implementation include {@link SwissTournament}, 
 * {@link RoundRobinTournament}
 * 
 * @author Oguejiofor Chidiebere
 * @see Tournament
 * 
 * 
 */

public abstract class GroupTournament extends Tournament 
{
	
	/**
	 * Stores the ranking table of this {@code GroupTournament}. Would be used to
	 * determine the winner at the end of the {@code Tournament}
	 */
	private StandingTable table;
	
	/**
	 * Stores the {@link Round}s in this {@code Tournament}
	 */
	private Round[] rounds;
	
	public GroupTournament(Competitor[] comps, SportType type, double pWin, double pDraw, double pLoss,
			TieBreaker breaker) throws InvalidBreakerException, TournamentException {
		
		super(type,   comps);
		
		if ( comps.length < 3 )
			throw new TournamentException("A group tournament must have total comopettitors > 2" );
		
		if ( breaker == null || !Arrays.stream( breaker.getBreakers() )
				.allMatch( b -> b.getType() == Breaker.GROUP_BREAKER ||
							b.getType() ==  Breaker.ALL ))
			throw new InvalidBreakerException("The breaker is invalid");
		
		
		table = new StandingTable( getSportType(), comps, pWin, pDraw, pLoss, breaker);

	}


	

	
	/**
	 * Gets the ranking table of this {@code GroupTournament} 
	 * @author Oguejiofor Chidiebere 
	 * @since v1.0
	 *
	 *@return a {@code StandingTable} that encapsulates the ranking of this {@code GroupTournament}
	 *
	 */
	public StandingTable getTable()
	{
		if ( !hasEnded() )
			table.updateTables();
		return table;
	}

	
	
	/**
	 * Sets the rounds of this {@code GroupTournament} 
	 * @author Oguejiofor Chidiebere 
	 * @see v1.0
	 *@param rnds the {@code Round[]} object
	 */
	protected void setRoundsArray(Round[] rnds) {
		rounds = rnds;
	}

	/**
	 * Gets an array of all the {@code Round}s in this {@code Tournament}
	 * @see Round
	 * @author Oguejiofor Chidiebere
	 */
	public Round[] getRoundArray() {
		return rounds;
	}

	/**
	 * Gets a specified {@code Round}. Note that if roundNum eqauls 0 then
	 * the first {@code Round} is returned
	 * @param roundIndex specifies the round number to be returned 
	 * Note that the first round has index 0 
	 * @see Round 
	 * @throws RoundIndexOutOfBoundException when the specified roundIndex is invalid
	 * @author Oguejiofor Chidiebere
	 */
	
	public Round getRound(int roundIndex) throws RoundIndexOutOfBoundsException {
		if (roundIndex < getRoundArray().length)
			return getRoundArray()[roundIndex];
		throw new RoundIndexOutOfBoundsException();
	}

	/**
	 * Sets the current {@code Round} array of fixtures
	 *@param fixes
	 */
	protected void setCurrentRound(Fixture[] fixes) {
		rounds[getCurrentRoundNum()] = new Round(fixes , toString()  );
	}

	/**
	 * Gets the top three {@code Competitor}s in this {@code GroupTournament} from
	 * the {@code StandingTable}
	 *@return a {@code Competitor} array
	 *@author Chidiebere
	 *@since v1.0
	 *@see Competitor
	 */
	public Competitor[] getTopThree()
	{
		Competitor[] comps =
			{ getTable().getCompetitor(0), 
			  getTable().getCompetitor(1),
			  getTable().getCompetitor(2)
			};
		return comps;
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

	/**
	 * Gets the total number of {@code Round}s that have beeen played in this 
	 * {@code GroupTournament}
	*@return an {@code int }
	 */
	public int getTotalNumberOfRounds() {
		return getRoundArray().length;
	}

	/**
	 * Gets the point for a win in the tournament
	*@return a {@code double} specifying the point for a win
	 */
	public double getWinPoint()
	{
		return getTable().getWinPoint();
	}
	
	/**
	 * Gets the point for a draw in the {@code GroupTournament}
	 *@return a {@code double}
	 */
	public double getDrawPoint()
	{
		return getTable().getDrawPoint();
	}
	
	/**
	 * Gets the point for a loss in the {@code GroupTournament}
	 *@return a {@code double}
	 */
	public double getLossPoint()
	{
		return getTable().getLossPoint();
	}
	
	public void updateTables()
	{
		table.updateTables();
	}
	public String toString() {
		if ( hasEnded() )
			return "Tournament Has Ended";
		
		return "Round " + ( getCurrentRoundNum()+1) ;
	}

	/**
	 * Checks if the current {@code Round } is complete
	 * 
	 *@return a {@code true	} when the current round does not contain pending {@code Fixture}s
	 *@throws TournamentEndedException when this {@code Tournament } has ended
	 *@author Oguejiofor Chidiebere
	 */
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
