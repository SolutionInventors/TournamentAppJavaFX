/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.types.group;

import java.util.Arrays;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public abstract class GroupTournament extends Tournament {
	private StandingTable table;
	private final Competitor[] COMPETITORS;
	private final SportType SPORT_TYPE;
	private int currentRoundNum;
	private Round[] rounds;
	private String name;

	public GroupTournament(Competitor[] comps, SportType type, double pWin, double pDraw, double pLoss,
			TieBreaker breaker) throws InvalidBreakerException {
		super(comps);

		SPORT_TYPE = type ;
		COMPETITORS = comps ; 
		if ( breaker == null || !Arrays.stream( breaker.getBreakers() )
				.allMatch( b -> b.getType() == Breaker.GROUP_BREAKER ||
							b.getType() ==  Breaker.BOTH ))
			throw new InvalidBreakerException("The breaker is invalid");
		table = new StandingTable(SPORT_TYPE, COMPETITORS, pWin, pDraw, pLoss, breaker);
		setName("");
	}

	public Competitor[] getCompetitors() {
		return COMPETITORS;
	}

	public SportType getSportType() {
		return SPORT_TYPE;
	}

	public StandingTable getTable() {
		return table;
	}

	public int getCurrentRoundNum() {
		return currentRoundNum;
	}

	protected void setCurrentRoundNum(int rnd) {
		this.currentRoundNum = rnd;
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

	public Round getCurrentRound() {
		if (getCurrentRoundNum() < getRoundArray().length)
			return getRoundArray()[getCurrentRoundNum()];
		else
			return null;
	}

	public abstract void setResult(Competitor com1, double score1, double score2, Competitor com2)
			throws NoFixtureException;

	public abstract void moveToNextRound() throws MoveToNextRoundException;

	public abstract boolean hasEnded();

	public abstract Competitor getWinner();

	public int getTotalNumberOfRounds() {
		return getRoundArray().length;
	}

	public String gettName() {
		return name;
	}

	public void setName(String tournamentName) {
		name = tournamentName != null && tournamentName.matches("[A-za-z]*") ? tournamentName : "";
	}

	public String toString() {
		return "Round " + getCurrentRoundNum();
	}

}
