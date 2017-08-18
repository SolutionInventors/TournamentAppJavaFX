/**
 * July 27, 2017
 * @author Chidiebere
 * Tournament.java
 * 8:07:23 AM
 */

package com.solutioninventors.tournament.types;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

public abstract class Tournament
{
	
	private final Competitor[] competitors;
	
	private int currentRoundNum;
	private String name;	
	
	public Tournament ( Competitor[] coms )
	{
		competitors = coms ;
		currentRoundNum = 0 ; 
		
		setName( "Tournament App" );
	}


	public Competitor[] getCompetitors()
	{
		return competitors;
	}


	public int getCurrentRoundNum()
	{
		return currentRoundNum;
	}


	public void incrementRoundNum()
	{
		currentRoundNum++ ;
	}
	
	public abstract void moveToNextRound()
			throws TournamentEndedException, MoveToNextRoundException ;
	public abstract void setResult( Competitor com1 , 
			double score1 , double score2 , Competitor com2 ) throws NoFixtureException;
	public abstract boolean hasEnded() ;
	public abstract Round getCurrentRound() throws TournamentEndedException;
	public abstract Competitor getWinner();

	public abstract Round[] getRoundArray();
	@Override
	public abstract String toString();


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name != null ? name : "Tournament App" 	;
	}
	

	
	
}
