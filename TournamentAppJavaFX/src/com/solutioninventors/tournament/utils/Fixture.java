package com.solutioninventors.tournament.utils;

import java.io.Serializable;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;

/**
 * This class encapsulates all the operations of a Fixuture in a tournament.<br> 
 * It contains a boolean which is toggled true when fixture results have been set
 * The competitor scores are initially set to -1
 * <table border = "1.0" width = " 730.0" >
 * 		<caption>{@code Fixture} properties </caption>
 * 		<tr>
 * 			<td>{@code private final Competitor COMPETITOR_ONE}</td> 
 * 			<td>Stores the home {@code  Competitor } in this {@code Fixture} <br>
 * 				Can be retrieved via call to method {@code getCompetitorOne()}</td> 
 * 		</tr>
		<tr>
		 	<td>{@code private final Competitor COMPETITOR_TWO}</td> 
 * 			<td>Stores the away {@code  Competitor } in this {@code Fixture} <br>
 * 				Can be retrieved via call to method {@code getCompetitorTwo()}</td>  
		</tr>
		<tr>
		 	<td>{@code private int competitorOneScore}</td> 
			<td>Stores the home {@code Competitor}'s score <br>
				It is initialised to -1 when this object is created<br>
				Can be retrieved via method call {@code getCompetitorOneScore }</td> 
		</tr>
		
		<tr>
		 	<td>{@code private int competitorTwoScore}</td> 
			<td>Stores the away {@code Competitor}'s score <br>
				It is initialized to -1 when this object is created<br>
				Can be retrieved via method call {@code getCompetitorTwoScore }</td> 
		</tr>
		
		<tr>
		 	<td>{@code private boolean complete} </td> 
			<td>true when this {@code Fixture } contains a result  
				That is when method {@code setResult( double score1 , double score2 ) } or method
				{@code setResult(double score1, double score2, boolean b)} is called with true has been called</td> 
		</tr>
</table>
 
 */

public class Fixture implements Serializable{

	
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	private double competitorOneScore;
	private double competitorTwoScore;
	private boolean complete;
	private final SportType TYPE;
	
	/**
	 * 
	* This constructor creates initializes this {@code Fixture } object with the two {@code Competitor} 
	* objects and initializes {@code competitorScoreOne } and {@code competitorScoreTwo } to -1
	 *
	 *@author Chidiebere Oguejiofor Chidiebere
	 *@since v1.0
	 *@see Competitor 
	 *@param homeCompetitor the {@code Competitor } that is home( or white in chess) 
	 *@param awayCompetitor the {@code COmpetitor } that is away( or black in chess)
	 */
	public Fixture( SportType type , Competitor homeCompetitor, Competitor awayCompetitor) {
		COMPETITOR_ONE = homeCompetitor;
		COMPETITOR_TWO = awayCompetitor;
		competitorOneScore = -1 ;
		competitorTwoScore = -1;
		TYPE = type;
	}

	/**
	 This method sets the CompetitorOne and COmpetitorTwoScore and also increments 
	 the {@code Competitor } objects' data such as the number of wins, goals scored, <br>
	 goals conceded etc. as appropriate and sets this Fixture's complete to {@code true}
	 <p>
	 If this {@code Fixture}'s {@code SportType} is set to {@code SportType.GOALS_ARE_NOT_SCORED}
	 this method sets the winner score to 1, draw to 0 and loss to -1 
	 <br>It stores the argument of the homeComSccore and awayComScore if it's {@code SportType} is
	 	{@code SportType.GOALS_ARE_SCORED}
	 @since v1.0
	 @see SportType
	 @see Competitor 
	 @param homeComScore the home {@code Competitor}'s score
	 @param awayComScore the away {@code Competitor}'s score
	 @throws ResultCannotBeSetException when this {@code Fixture } is already contains a result
	 */
	public void setResult(final double homeComScore, final double awayComScore) 
			throws ResultCannotBeSetException
	{
		if (!isComplete()) {
			if (homeComScore > awayComScore) {
				getCompetitorOne().incrementHomeWin();
				getCompetitorTwo().incrementAwayLoss();
				competitorOneScore = 1;
				competitorTwoScore = -1;
			} 
			else if (homeComScore == awayComScore) // match is a draw
			{
				getCompetitorOne().incrementHomeDraw();
				getCompetitorTwo().incrementAwayDraw();
				competitorOneScore = 0;
				competitorTwoScore = 0;
			}
			else 
			{
				getCompetitorOne().incrementAwayLoss();
				getCompetitorTwo().incrementAwayWin();
				competitorOneScore = -1;
				competitorTwoScore = 1;
				
			}

			
			if ( TYPE == SportType.GOALS_ARE_SCORED )
			{
				
				getCompetitorOne().addToHeadToHead( getCompetitorTwo() , homeComScore ); 
				getCompetitorTwo().addToHeadToHead(getCompetitorOne(), awayComScore );
				
				getCompetitorTwo().addAwayGoal( getCompetitorOne(), awayComScore);//adds away goal
				getCompetitorOne().addHomeGoal( getCompetitorTwo(), homeComScore);//adds home goal
				competitorOneScore = homeComScore ;
				competitorTwoScore = awayComScore ;
			}
			complete = true ;
		}
		else
		{
			throw new ResultCannotBeSetException();
		} 
	}

	/**
	 *Checks if this {@code Fixture } is complete. Once this is {@code true} this {@code Fixture} 
	 *results cannot be modified
	 *@return true when this {@code Fixture } contains a result
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Checks if this {@code Fixture } contains result AND  is a draw( that is the twi 
	 * competitors have thesame score ) 
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have thesame score
	 *
	 */
	public boolean isDraw() {
		
		if (isComplete() && ( competitorOneScore == competitorTwoScore))
			return true;
		else
			return false;
	}

	/**
	 * Checks if this {@code Fixture } has a winner( that is no draw and is complete )
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have different scores
	 *
	 */
	
	public boolean hasWinner() {
		return !isDraw();
	}

	/**
	 * Checks if this {@code Fixture } has a loser
	 *@return true when this {@code Fixture } is complete and the  home and away {@code Competitor}s
	 *have different scores
	 *
	 */
	
	public boolean hasLoser() {
		return hasWinner();
	}

	/**
	 * Gets the {@code Competitor } that won this {@code Fixture }
	 * Returns {@code null  } when this {@code Fixture } is incomplete or contains draw
	 *@return the {@code Competitor } object of the winner if this {@code Fixture } has a winner else
	 *returns {@code null }
	 */
	public Competitor getWinner() {
		if (hasWinner())
			return competitorOneScore > competitorTwoScore ?
					getCompetitorOne() : getCompetitorTwo();

		return null;

	}

	/**
	 * Gets the loser of this {@code Fixture }. Returns {@code null } when this {@code Fixture}
	 * does not have a loser
	 *@return the {@code Competitor } object of the loser if this {@code Fixture } has a winner else
	 *returns {@code null }
	 */
	public Competitor getLoser() 
	{
		
		if (hasLoser())
			return competitorOneScore < competitorTwoScore ?
					getCompetitorOne() : getCompetitorTwo();

		return null;

	}
	
	/**
	 * Checks if this {@code Fixture } contains the specified {@code Competitor}s
	 * Returns {@code false } if either the home or away {@code Competitor} is misplaced
	 * @author Oguejiofor Chidiebere
	 *@param com1 the home {@code Competitor}
	 *@param com2 the away {@code Competitor}
	 *@return {@code true } when the com1 and com2 are equal to this {@code FIxture}'s {@code CompetitorOne} and
	 * {@code CompetitorTwo } data. If not returns {@code false }
	 * @since v1.0
	 * @see Competitor
	 */
	public boolean hasFixture(Competitor com1, Competitor com2) {
		// returns true if com1 equals COMPETITOR_ONE AND con2 = COMPETITOR_TWO
		if (getCompetitorOne().getName().equals(com1.getName()) && getCompetitorTwo().getName().equals(com2.getName()))
			return true;
		else
			return false;
	}

	/**
	 * Gets the home {@code Competitor} 
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return The home {@code Competitor } object
	 */
	public Competitor getCompetitorOne() {
		return COMPETITOR_ONE;
	}

	/**
	 * Gets the away {@code Competitor}
	 *@return The away {@code Competitor}object 
	 *@since v1.0
	 */
	public Competitor getCompetitorTwo() {
		return COMPETITOR_TWO;
	}

	/**
	 * Gets the away {@code Competitor}'s score.
	 * When this {@code Fixture}'s {@code SportType} is {@code SpoortType.GOALS_ARE_NOT_SCORED}
	 * this method returns 1, 0 or -1 to indicate a win, draw or loss respectively
	 *@return The home {@code Competitor}'s score as {@code double }
	 * @throws IncompleteFixtureException when this {@code Fixture} is does not contain a result
	 * 
	 */
	
	public double getCompetitorOneScore() 
			throws IncompleteFixtureException {
		
		if ( isComplete() )
			return competitorOneScore;
		
		throw new IncompleteFixtureException("This fixture does not contain a restult" );
		
	}

	/**
	 * Gets an {@code Integer} representation of the home {@code Competitor}'s score when 
	 * the boolean is {@code true}. Else gets the {@code Double } representation
	 * When this {@code Fixture}'s {@code SportType} is {@code SpoortType.GOALS_ARE_NOT_SCORED}
	 * this method returns 1, 0 or -1 to indicate a win, draw or loss respectively
	 * @author Oguwjiofor Chidiebere
	 *@param intValue  when {@code true} gets an {@code Integer }
	 *@return The home {@code Competitor}'s score as an {@code Number}
		@throws IncompleteFixtureException when this {@code Fixture} is does not contain a result
	 
	 */
	public Number getCompetitorOneScore( boolean intValue ) 
			throws IncompleteFixtureException {
		return !intValue ? getCompetitorOneScore() : 
			(int) getCompetitorOneScore() ;
		
	}
	
	/**
	 * Gets the away teams score as a {@code Number} object
	 * When this {@code Fixture}'s {@code SportType} is {@code SpoortType.GOALS_ARE_NOT_SCORED}
	 * this method returns 1, 0 or -1 to indicate a win, draw or loss respectively
	 * @author Oguejiofor Chidiebere 
	 *@param intValue returns an {@code int	} if {@code true }
	 *@return The away {@code Competitor}'s score as an {@code int } if {@code intValue} = {@code true}
	 *Else returns the score as {@code double}
	 *@throws IncompleteFixtureException when this {@code Fixture} is does not contain a result
	 */
	
	public  Number getCompetitorTwoScore( boolean intValue ) 
			throws IncompleteFixtureException  {
		
		return !intValue ? getCompetitorTwoScore() : 
			(int) getCompetitorTwoScore() ;
		
		
	}
	
	/**
	 * Gets the the away {@code Competitor}'s score
	 * When this {@code Fixture}'s {@code SportType} is {@code SpoortType.GOALS_ARE_NOT_SCORED}
	 * this method returns 1, 0 or -1 to indicate a win, draw or loss respectively
	 * @author Oguejiofor Chidiebere
	 *@return The away {@code Competitor}'s score as {@code double }
	 @throws IncompleteFixtureException when this {@code Fixture} is does not contain a result
	
	 */
	public double getCompetitorTwoScore() 
			throws IncompleteFixtureException {
		if ( isComplete() )
			return competitorTwoScore;
		
		throw new IncompleteFixtureException("This fixture does not contain a restult" );
		
	}

	/**
	 * Sets the result of this {@code Fixture } when store = {@code true } but
	 * only changes the {@code Competitor } score when store = {@code false } <br>
	 * without changing the {@code Competitor } object's data
	 * <br>
	 * {@code store} should be set to false when storing ties 
	 * <br>
	 * This method sets the competitorOneScore and CompetitorTwoScore if b = false 
	 *@param score1 home {@code Competitor} score
	 *@param score2 away {@code COmpetitor} score
	 *@param store indicates whether to save the scores in the {@code Competitor} objects and toggle this {@code Fixture } to complete
	 * @throws ResultCannotBeSetException when this object is complete 
	 */
	
	public void setResult(double score1, double score2, boolean store) throws ResultCannotBeSetException
	{
		if ( isComplete() )
			throw new ResultCannotBeSetException();
		else if ( store  )
			setResult(score1, score2);
		else {
			competitorOneScore = score1;
			competitorTwoScore = score2;
		}

	}

	public void eliminateLoser()
	{
		if ( isComplete() )
			getLoser().setEliminated( true );
		
	}
}
