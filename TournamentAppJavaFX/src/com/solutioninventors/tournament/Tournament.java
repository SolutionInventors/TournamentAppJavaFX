/**
 * July 27, 2017
 * @author Chidiebere
 * Tournament.java
 * 8:07:23 AM
 */

package com.solutioninventors.tournament;

import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

public abstract class Tournament
{
	
	private final Competitor[] competitors;
	
	private int currentRoundNum;
	
	
	
	public Tournament ( Competitor[] coms )
	{
		competitors = coms ;
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


	public abstract Round[] getRoundsArray();


	public abstract void moveToNextRound();
	public abstract void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 );
	public abstract boolean hasEnded() ;
	public abstract Round getCurrentRound();
	public abstract Competitor getWinner();
	

	
	
}
