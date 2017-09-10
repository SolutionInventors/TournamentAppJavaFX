package com.solutioninventors.tournament.utils;

import java.io.Serializable;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;

public class Fixture implements Serializable{

	/**
	 * This class encapsulates all the operations of a Fixuture in a tournament
	 * It contains a boolean which is toggled true when fixture results have been set
	 * The competitor scores are initially set to -1
	 * This class is IMMUTABLE
	 */
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	private double competitorOneScore;
	private double competitorTwoScore;
	private boolean complete;

	public Fixture(Competitor competitorOne, Competitor competitorTwo) {
		COMPETITOR_ONE = competitorOne;
		COMPETITOR_TWO = competitorTwo;
		competitorOneScore = -1 ;
		competitorTwoScore = -1;
	}

	public void setResult(final double score1, final double score2)
	{
		if (!isComplete()) {
			if (score1 > score2) {
				getCompetitorOne().incrementNumberOfHomeWin();
				getCompetitorOne().incrementWins();
				getCompetitorTwo().incrementLoss();
			} 
			else if (score1 == score2) // match is a draw
			{
				getCompetitorOne().incrementDraw();
				getCompetitorTwo().incrementDraw();
			}
			else 
			{
				getCompetitorOne().incrementLoss();
				getCompetitorTwo().incrementNumberOfAwayWin();
				getCompetitorTwo().incrementWins();
				
			}

			
			getCompetitorOne().incrementGoalsScoredBy( score1 );
			getCompetitorOne().incrementGoalsConcededBy( score2 );
			getCompetitorTwo().incrementGoalsScoredBy( score2 );
			getCompetitorTwo().incrementGoalsConcededBy( score1 );
			getCompetitorTwo().incrementNumberOfAwayGoalsBy( score2); 
			getCompetitorOne().addToHeadToHead( getCompetitorTwo() , score1); 
			getCompetitorTwo().addToHeadToHead(getCompetitorOne(), score2 );
			
			getCompetitorTwo().addAwayGoal( getCompetitorOne(), score2);//adds away goal
			competitorOneScore = score1 ;
			competitorTwoScore = score2 ;
			
			complete = true ;
		}
		else
		{
			String message = String.format( "%s\n%s %.1f VS %.1f %s", "This fixture has a result" ,
										getCompetitorOne().getName() , getCompetitorOneScore() , 
										getCompetitorTwoScore() , getCompetitorTwo().getName() );
			JOptionPane.showMessageDialog( null , message );
			
			complete = true;
		} 
	}

	public boolean isComplete() {
		return complete;
	}

	public boolean isDraw() {
		if (isComplete() && (getCompetitorOneScore() == getCompetitorTwoScore()))
			return true;
		else
			return false;
	}

	public boolean hasWinner() {
		if (isComplete() && (getCompetitorOneScore() > getCompetitorTwoScore() && isDraw()))
			return true;
		else
			return false;
	}

	public boolean hasLoser() {
		return hasWinner();
	}

	public Competitor getWinner() {
		if (hasWinner())
			return getCompetitorOneScore() > getCompetitorTwoScore() ? getCompetitorOne() : getCompetitorTwo();

		return null;

	}

	public Competitor getLoser() 
	{
		
		if (hasLoser())
			return getCompetitorOneScore() < getCompetitorTwoScore() ? getCompetitorOne() : getCompetitorTwo();

		return null;

	}

	public boolean hasFixture(Competitor com1, Competitor com2) {
		// returns true if com1 equals COMPETITOR_ONE AND con2 = COMPETITOR_TWO
		if (getCompetitorOne().getName().equals(com1.getName()) && getCompetitorTwo().getName().equals(com2.getName()))
			return true;
		else
			return false;
	}

	public Competitor getCompetitorOne() {
		return COMPETITOR_ONE;
	}

	public Competitor getCompetitorTwo() {
		return COMPETITOR_TWO;
	}

	public double getCompetitorOneScore()  {
		return competitorOneScore;
		
	}

	public double getCompetitorOneScore( boolean intValue )  {
		return !intValue ? competitorOneScore : (int) competitorOneScore ;
		
	}
	
	public double getCompetitorOnTwoScore( boolean intValue )  {
		return !intValue ? competitorTwoScore : (int) competitorTwoScore ;
		
	}
	
	public double getCompetitorTwoScore() {
		return competitorTwoScore;
	}

	public void setResult(double score1, double score2, boolean b) {
		if ( !isComplete() )
			return;
		else if (b)
			setResult(score1, score2);
		else {
			competitorOneScore = score1;
			competitorTwoScore = score2;
		}

	}

}
