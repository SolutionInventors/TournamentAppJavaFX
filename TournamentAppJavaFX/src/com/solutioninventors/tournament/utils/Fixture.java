package com.solutioninventors.tournament.utils;

import java.io.Serializable;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;

/**
 * This class encapsulates all the operations of a Fixuture in a tournament.<br/> 
 * It contains a boolean which is toggled true when fixture results have been set
 * The competitor scores are initially set to -1
 * <table border = "1.0" width = " 730.0" >
 * 		<tr>
 * 			<td>{@code private final Competitor COMPETITOR_ONE}</td> 
 * 			<td>Stores the home {@code  Competitor } in this {@code Fixture} <br/>
 * 				Can be retrieved via call to method {@code getCompetitorOne()}</td> 
 * 		</tr>
		<tr>
		 	<td>{@code private final Competitor COMPETITOR_TWO}</td> 
 * 			<td>Stores the away {@code  Competitor } in this {@code Fixture} <br/>
 * 				Can be retrieved via call to method {@code getCompetitorTwo()}</td>  
		</tr>
		<tr>
		 	<td>{@code private int competitorOneScore}</td> 
			<td>Stores the home {@code Competitor}'s score <br/>
				It is initialised to -1 when this object is created<br/>
				Can be retrieved via method call {@code getCompetitorOneScore }</td> 
		</tr>
		
		<tr>
		 	<td>{@code private int competitorTwoScore}</td> 
			<td>Stores the away {@code Competitor}'s score <br/>
				It is initialized to -1 when this object is created<br/>
				Can be retrieved via method call {@code getCompetitorTwoScore }</td> 
		</tr>
		
		<tr>
		 	<td>{@code private boolean complete} </td> 
			<td>true when this {@code Fixture } contains a result  
				That is when method {@code setResult( double score1 , double score2 ) } or method
				{@code setResult(double score1, double score2, boolean b)} is called with true has been called</td> 
		</tr>
 * <table>
 
 */

public class Fixture implements Serializable{

	
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	private double competitorOneScore;
	private double competitorTwoScore;
	private boolean complete;

	/**
	 * 
	* This constructor creates initializes this {@code Fixture } object with the two {@code Competitor} 
	* objects and initializes {@code competitorScoreOne } and {@code competitorScoreTwo } to -1
	 *
	 *@author Chidiebere Oguejiofor Chidiebere
	 *@since v1.0
	 *@see Competitor
	 *@param homeCompetitor
	 *@param awayCompetitor
	 */
	public Fixture(Competitor homeCompetitor, Competitor awayCompetitor) {
		COMPETITOR_ONE = homeCompetitor;
		COMPETITOR_TWO = awayCompetitor;
		competitorOneScore = -1 ;
		competitorTwoScore = -1;
	}

	/**
	 This method sets the CompetitorOne and COmpetitorTwoScore and also increments 
	 the {@code Competitor } objects' data such as the number of wins, goals scored, <br/>
	 goals conceded etc. as appropriate and sets this Fixture's complete to {@code true}
	 
	 @since v1.0
	 @see Competitor
	 *@param 
	 */
	public void setResult(final double homeComScore, final double awayComScore)
	{
		if (!isComplete()) {
			if (homeComScore > awayComScore) {
				getCompetitorOne().incrementNumberOfHomeWin();
				getCompetitorOne().incrementWins();
				getCompetitorTwo().incrementLoss();
			} 
			else if (homeComScore == awayComScore) // match is a draw
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

			
			getCompetitorOne().incrementGoalsScoredBy( homeComScore );
			getCompetitorOne().incrementGoalsConcededBy( awayComScore );
			getCompetitorTwo().incrementGoalsScoredBy( awayComScore );
			getCompetitorTwo().incrementGoalsConcededBy( homeComScore );
			getCompetitorOne().addToHeadToHead( getCompetitorTwo() , homeComScore ); 
			getCompetitorTwo().addToHeadToHead(getCompetitorOne(), awayComScore );
			
			getCompetitorTwo().addAwayGoal( getCompetitorOne(), awayComScore);//adds away goal
			getCompetitorOne().addHomeGoal( getCompetitorTwo(), homeComScore);//adds home goal
			competitorOneScore = homeComScore ;
			competitorTwoScore = awayComScore ;
			
			complete = true ;
		}
		else
		{
			String message = String.format( "%s\n%s %.1f VS %.1f %s", "This fixture has a result" ,
										getCompetitorOne().getName() , getCompetitorOneScore() , 
										getCompetitorTwoScore() , getCompetitorTwo().getName() );
			JOptionPane.showMessageDialog( null , message );
		} 
	}

	/**
	 *
	 *@return true when this {@code Fixture } contains a result
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * 
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have thesame score
	 *
	 */
	public boolean isDraw() {
		if (isComplete() && (getCompetitorOneScore() == getCompetitorTwoScore()))
			return true;
		else
			return false;
	}

	/**
	 * 
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have different scores
	 *
	 */
	
	public boolean hasWinner() {
		if (isComplete() && (getCompetitorOneScore() > getCompetitorTwoScore() && isDraw()))
			return true;
		else
			return false;
	}

	/**
	 * 
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have different scores
	 *
	 */
	
	public boolean hasLoser() {
		return hasWinner();
	}

	/**
	 * 
	 *@return the {@code Competitor } object of the winner if this {@code Fixture } has a winner else
	 *returns {@code null }
	 */
	public Competitor getWinner() {
		if (hasWinner())
			return getCompetitorOneScore() > getCompetitorTwoScore() ? getCompetitorOne() : getCompetitorTwo();

		return null;

	}

	/**
	 * 
	 *@return the {@code Competitor } object of the loser if this {@code Fixture } has a winner else
	 *returns {@code null }
	 */
	public Competitor getLoser() 
	{
		
		if (hasLoser())
			return getCompetitorOneScore() < getCompetitorTwoScore() ? getCompetitorOne() : getCompetitorTwo();

		return null;

	}
	
	/**
	 * 
	 *@param com1
	 *@param com2
	 *@return {@code true } when the com1 and com2 are equal to this {@code FIxture}'s {@code CompetitorOne} and
	 * {@code CompetitorTwo } data. If not returns {@code false }
	 * @since v1.0
	 */
	public boolean hasFixture(Competitor com1, Competitor com2) {
		// returns true if com1 equals COMPETITOR_ONE AND con2 = COMPETITOR_TWO
		if (getCompetitorOne().getName().equals(com1.getName()) && getCompetitorTwo().getName().equals(com2.getName()))
			return true;
		else
			return false;
	}

	/**
	 * @since v1.0
	 *@return The home {@code Competitor } object
	 */
	public Competitor getCompetitorOne() {
		return COMPETITOR_ONE;
	}

	/**
	 *@return The away {@code Competitor}object 
	 *@since v1.0
	 */
	public Competitor getCompetitorTwo() {
		return COMPETITOR_TWO;
	}

	/**
	 *@return The home {@code Competitor}'s score as {@code double }
	 */
	
	public double getCompetitorOneScore()  {
		return competitorOneScore;
		
	}

	/**
	 *@param intValue
	 *@return The home {@code Competitor}'s score as an {@code int } if {@code intValue} = {@code true}
	 *Else returns the score as {@code double}
	 */
	public double getCompetitorOneScore( boolean intValue )  {
		return !intValue ? competitorOneScore : (int) competitorOneScore ;
		
	}
	
	/**
	 *@param intValue
	 *@return The away {@code Competitor}'s score as an {@code int } if {@code intValue} = {@code true}
	 *Else returns the score as {@code double}
	 */
	
	public double getCompetitorOnTwoScore( boolean intValue )  {
		return !intValue ? competitorTwoScore : (int) competitorTwoScore ;
		
	}
	
	/**
	 *@return The away {@code Competitor}'s score as {@code double }
	 */
	public double getCompetitorTwoScore() {
		return competitorTwoScore;
	}

	/**
	 * This method does nothing if this {@code Fixture } is complete but 
	 * calls method {@code setResult( double score1, double score2 ) } if b = true
	 * <br/>
	 * This method sets the competitorOneScore and COmpetitorTwoScore if b = false 
	 *@param score1
	 *@param score2
	 *@param b
	 */
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
