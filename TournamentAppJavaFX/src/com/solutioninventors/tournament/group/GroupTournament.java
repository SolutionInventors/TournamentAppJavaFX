/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.group;

import java.util.Arrays;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public abstract class GroupTournament
{
	private StandingTable table;
	private final Competitor[] COMPETITORS;
	private final SportType SPORT_TYPE;
	private int currentRoundNum;
	private Round[] rounds ;
	
	public GroupTournament( Competitor[] comps  , SportType type , 
			double pWin , double pDraw , double pLoss , TieBreaker breaker ) throws InvalidBreakerException
	{
		SPORT_TYPE = type ;
		COMPETITORS = comps ; 
		if ( breaker == null || Arrays.stream( breaker.getBreakers() )
				.anyMatch( b -> b.getType() != Breaker.GROUUP_BREAKER ))
			throw new InvalidBreakerException("The breaker is invalid");
		table = new StandingTable( SPORT_TYPE, COMPETITORS , pWin , pDraw , pLoss, breaker );
	}
	
	public Competitor[] getCompetitors()
	{
		return COMPETITORS;
	}

	public SportType getSportType()
	{
		return SPORT_TYPE;
	}
	
	
	public StandingTable getTable()
	{
		return table;
	}

	public int getCurrentRoundNum()
	{
		return currentRoundNum;
	}

	protected void setCurrentRoundNum(int rnd)
	{
		this.currentRoundNum = rnd;
	}
	

	protected void setRoundsArray( Round[] rnds )
	{
		rounds = rnds;
	}
	
	public Round[] getRoundsArray()
	{
		return rounds ; 
	}
	
	
	protected void setCurrentRound( Fixture[] fixes )
	{
		rounds[ getCurrentRoundNum() ] = new Round( fixes );
	}
	
	public Round getCurrentRound()
	{
		if ( getCurrentRoundNum() < getRoundsArray().length )
			return getRoundsArray()[ getCurrentRoundNum() ];
		else
			return null ;
	}

	public abstract void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 );
	public abstract void moveToNextRound() throws TournamentException;
	public abstract boolean hasEnded();

	public abstract Competitor getWinner();

	public  int getTotalNumberOfRounds()
	{
		return getRoundsArray().length ;
	}
	
	
}
