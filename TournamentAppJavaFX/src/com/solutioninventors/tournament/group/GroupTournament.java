/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.group;

import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

public abstract class GroupTournament
{
	private StandingTable table;
	private final Competitor[] COMPETITORS;
	private final SportType SPORT_TYPE;
	private int currentRoundNum;
	private Round[] rounds ;
	
	public GroupTournament( Competitor[] comps  , SportType type )
	{
		SPORT_TYPE = type ;
		COMPETITORS = comps ; 
		table = new StandingTable( SPORT_TYPE, COMPETITORS );
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

	public void setCurrentRoundNum(int rnd)
	{
		this.currentRoundNum = rnd;
	}
	

	public void setRoundsArray( Round[] rnds )
	{
		rounds = rnds;
	}
	
	public Round[] getRoundsArray()
	{
		return rounds ; 
	}
	
	public Round getCurrentRound()
	{
		return getRoundsArray()[ getCurrentRoundNum() ];
	}

	public abstract void moveToNextRound();
	
}
