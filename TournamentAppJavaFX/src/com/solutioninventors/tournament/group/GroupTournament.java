/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.group;

import java.util.Arrays;
import java.util.NoSuchElementException;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.TournamentException;
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
	
	public GroupTournament( Competitor[] comps  , SportType type , double pWin , double pDraw , double pLoss  )
	{
		SPORT_TYPE = type ;
		COMPETITORS = comps ; 
		table = new StandingTable( SPORT_TYPE, COMPETITORS , pWin , pDraw , pLoss );
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

	public abstract void setRoundResult( Competitor com1 , int score1 , int score2 , Competitor com2 );
	public abstract void moveToNextRound() throws TournamentException;
	public abstract boolean hasEnded();
}
