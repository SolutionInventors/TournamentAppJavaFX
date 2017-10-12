/**
 *@Author: Oguejiofor Chidiebere
 *SingleEliminationTournament.java
 *Aug 7, 2017
 *2:38:15 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

/**
 * This class simulates a single elimination tournament in which the losers of each
 * {@code Round} is eliminated and only the winners advance to the next {@code Round}.
 * {@code Competitor}s are eliminated after each {@code Round } until only one {@code Competitor} is
 * left. This is he winner of the tournament.<p>
 * A {@code SingleEliminationTournament} may also have a home and away {@code Fixture}s.
 * In that case {@code Competitor}s would be eliiminated after every two games<p>
 *H
 */
public class SingleEliminationTournament extends EliminationTournament {

	/**
	 * Stores all the rounds in this tournament
	 */
	private List<Round> roundList;
	
	private boolean tieRound;
	
	/**
	 * Stores the active ties in this {@code Tournament}
	 */
	private List<Fixture> activeTies;
	
	/**
	 * {@code true} when this {@code SingleEliminationTournament} has away fixtures
	 */
	private final boolean AWAY;

	/**
	 * Stores the {@code Round}s that simulate ties in this {@code Tournament}
	 * @see setTieResult
	 */
	private final Map<Integer, Round> tieRounds ;
	
	/**
	 * Stores the topThree {@code Competitor}s at the end of this {@code Tournament}
	 */
	private final Competitor[] topThree;
	
	/**
	 * Initializes this {@code SingleEliminationTournament} with the {@code Competitor[]}
	 * Always shuffles the array
	 *@param type
	 *@param comps
	 *@param away
	 *@throws TournamentException
	 */
	public SingleEliminationTournament(SportType type, Competitor[] comps, boolean away) throws TournamentException {
		this( type, comps, true , true );
	}
	
	/**
	 * Initializes this {@code SingleEliminationTournament} with the {@code Competitor[]}
	 * Shuffles the array only if shuffle is true 
	 *@param type
	 *@param comps
	 *@param away
	 *@throws TournamentException
	 */
	public SingleEliminationTournament(SportType type, Competitor[] comps, boolean away, boolean shuffle) throws TournamentException {
		super( type, comps, shuffle );

		roundList = new ArrayList<>();
		AWAY = away;
		createTounament();
		activeTies = new ArrayList<>();
		topThree = new Competitor[3];
		tieRounds =  new HashMap<>();
	}
	
	
	/**
	 *Initializes this {@code SingleEliminationTournament} with the {@code Competitor[]}.
	 *This constructor creates a {@code SingleEliminationTournament} with only home {@code Fixture}
	 *@author Oguejiofor Chidiebere
	 *@since v1.0
	 *@see Fixture
	 *@param type
	 *@param comps
	 *@throws TournamentException
	 */
	public SingleEliminationTournament(SportType type, Competitor[] comps) throws TournamentException
	{
		this( type,  comps , false );
	}


	/**
	 * Creates the initial rounds of this object 
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	private void createTounament() {
		Competitor[] tempComps = getCompetitors();

		Fixture[] fixtures = new Fixture[tempComps.length / 2];

		for (int i = 0; i < tempComps.length; i += 2) {
			fixtures[i / 2] = new Fixture( getSportType(), tempComps[i], tempComps[i + 1]);

		}

		roundList.add(new Round(fixtures));

		if (hasAway())
			roundList.add(new Round(fixtures).invertHomeAndAway( getSportType() ));
	}

	/**
	 * Checks if this {@code Tournament} contains away {@code Fixture}s
	 *@return {@code true} when this object contains away fixtures
	 */ 
	public boolean hasAway() {
		return AWAY;
	}

	@Override
	public Round getCurrentRound() {
		if ( isTieRound() )
			return tieRounds.get( getCurrentRoundNum() );
		return roundList.get(getCurrentRoundNum());
	}
	
		@Override
	public Round[] getRoundArray() {
		return roundList.toArray(new Round[roundList.size()]);
	}

	/**
	 * Stores the result of the current {@code Round} and eliminates the losers
	 * if necessary.
	 * <p>
	 * If there are draws in the current {@code Round} and there are no away {@code Fixture}s 
	 * then this method would throw a {@link MoveToNextRoundException}.<br>
	 * However if there are away fixtures then this method uses the away goal rule
	 * to eliminate a {@code Competitor}. If there are still ties it eliminates the
	 * away team
	 * <p>
	 * A call to this method would always eliminate the losers if this {@code SingleEliminationTournament}
	 * does not have away {@code Fixture}s.
	 * <p>
	 * It would eliminate the losers only after the home and away {@code Fixture}s have
	 * been play if this {@code Tournament} contains away matches
	 * 
	 * throws TournamentEndedException if this {@code Tournament} has ended
	 * throws MoveToNextRoundException if there is a tie in the current round
	 * 
	 */
	@Override
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException 
	{
		if ( hasEnded() )
			throw new TournamentEndedException();
		else if ( !getCurrentRound().isComplete() )
			throw new MoveToNextRoundException("This Round is incomplete" );
		else if ( hasTie() && isTieRound() )
			throw new MoveToNextRoundException("The ties have not been broken");
		
		
		if ( isFinal()  && !hasTie() )
		{
			eliminateLosers();
			createNextRound();
		}
		else if (hasTie() )
		{
			if ( getCurrentRound().getLosers() != null )
			{
				eliminateLosers();
			}
			Round tieRound = new Round( activeTies );
			createTieRound( tieRound );
			setTieRound( true );
		}
		else if ( hasAway() && !isSecondLeg() )//firstLeg fixture
			incrementRoundNum();
		else if ( isSecondLeg()   || !hasAway()   )
		{
			eliminateLosers();
			createNextRound();
		}
		
	}

	public void createNextRound()
	{
		if ( !isTieRound() && hasTie() )
		{
			Round tieRound = new Round( activeTies );
			tieRounds.put( getCurrentRoundNum(), tieRound ) ;
			createTieRound( tieRound );
			setTieRound( true );
		}
		else
		{
			createNextRound( getActiveCompetitors());
			setTieRound( false );
		}
	}

	
	private boolean isFinal()
	{
		return getActiveCompetitors().length == 2 ;
	}

	private void createNextRound(Competitor[] activeCompetitors)
	{
		
		if ( activeCompetitors.length > 1 )
		{
			Fixture[ ] nextRoundFixtures =  new Fixture[ activeCompetitors.length /2 ];
			for ( int i = 0 ; i < activeCompetitors.length ; i+= 2 )
				nextRoundFixtures[ i/2 ] = new Fixture( getSportType(), activeCompetitors[ i ], activeCompetitors[ i + 1 ] );
			
			Round round = new Round( nextRoundFixtures );
			roundList.add( round );
			if ( hasAway() && getActiveCompetitors().length > 2 )
				roundList.add( round.invertHomeAndAway( getSportType() ) );

			incrementRoundNum();
		}
		
	}

	private void createTieRound(Round round)
	{
		tieRounds.put( getCurrentRoundNum() , round );
		
	}

	private void eliminateLosers()
	{
		if (!hasAway() || getActiveCompetitors().length == 2 )
		{
			
			if ( getActiveCompetitors().length ==  2 && 
					!getCurrentRound().hasDraw() )
			{
				topThree[0] = getCurrentRound().getWinners()[0];
				topThree[1] = getCurrentRound().getLosers()[0];
			
			}
			Arrays.stream( getCurrentRound().getLosers()  )
			  .forEach( loser -> loser.setEliminated( true ) );
			
		
		}
		else
		{
			Arrays.stream( getCurrentRound().getFixtures() )
			.forEach( secondLeg ->
			{
				try
				{
					Fixture firstLeg  = getRound( getCurrentRoundNum() -1 )
							.getFixture(secondLeg.getCompetitorTwo() ,
									secondLeg.getCompetitorOne() );
					double com1AwayScore  = firstLeg.getCompetitorTwoScore() ;
					double com2AwayScore = secondLeg.getCompetitorTwoScore();
					double totalCom1Score = com1AwayScore + secondLeg.getCompetitorOneScore();
					double totalCOm2Score = com2AwayScore + firstLeg.getCompetitorOneScore();
					
					if ( totalCom1Score < totalCOm2Score )
						secondLeg.getCompetitorOne().setEliminated( true );
					else if ( totalCom1Score > totalCOm2Score )
						secondLeg.getCompetitorTwo().setEliminated(true );
					else if ( com1AwayScore  < com2AwayScore )
						secondLeg.getCompetitorOne().setEliminated( true );
					else
						secondLeg.getCompetitorTwo().setEliminated(true );
					
				}
				catch (NoFixtureException | IncompleteFixtureException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
				
			});
	
		}
		
	}

	public boolean isSemiFinal()
	{
		return getCurrentRound().getNumberOfFixtures() == 2  &&
				getActiveCompetitors().length == 4;
	}

	

	@Override
	public Competitor getWinner() {
		if (hasEnded())
			return getActiveCompetitors()[0];
		return null;
	}


	@Override
	public void setResult(Competitor com1, double score1, double score2, Competitor com2)
			throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException 
	{
		if ( hasEnded() )
			throw new TournamentEndedException();
		if ( isTieRound() )
		{
			setTieResult(com1, score1, score2, com2);
			return ;
		}
			
		boolean fixtureExists = 
				getCurrentRound().hasFixture( com1 , com2 );
					
		boolean fixtureHasResult  = 
				Arrays.stream( getCurrentRound().getFixtures())
					.filter( f -> Competitor.isEqual( com1,  f.getCompetitorOne() ) && 
								  Competitor.isEqual( com2, f.getCompetitorTwo() ) 	)
					.anyMatch( f -> f.isComplete() );
		
		
		if ( !fixtureExists ) 
			throw new NoFixtureException("Fixture was not found" );
		else if ( fixtureHasResult )
			throw new ResultCannotBeSetException();
		

		Fixture theFixture = getCurrentRound()
							 .getFixture(com1, com2 );
		
			
		if ( !hasAway() || isFinal() )
		{
			if( score1 == score2 )
				addToTieList(com1, score1, score2, com2);
			
		}
		else // there are away fixtures
		{
			if ( isSecondLeg() )
			{
				
				Fixture firstLeg  = getRound( getCurrentRoundNum() -1 )
										.getFixture(com2, com1);
				
				double com1AwayGoal = 0;
				double totalCom2Goal = 0;
				double com2AwayGoal = score2 ;
				double totalCom1Goal = 0;
				
				try
				{
					com1AwayGoal = firstLeg.getCompetitorTwoScore();
					totalCom1Goal = com1AwayGoal + score1;
					totalCom2Goal = com2AwayGoal + firstLeg.getCompetitorOneScore();
				}
				catch (IncompleteFixtureException e)
				{
					e.printStackTrace();
				}
			
				
				
				if ( totalCom1Goal ==  totalCom2Goal && 
						com1AwayGoal == com2AwayGoal ) //there is a draw after away fixture
				{
					addToTieList(com1, score1, score2, com2);
				}	
			}
		}
		new Round( theFixture ).setResult(theFixture, score1, score2);	// stores the result
		
		
	}

	private boolean isSecondLeg()
	{
		if ( hasAway() && getCurrentRoundNum() % 2 ==  1 && 
				 getActiveCompetitors().length > 2 )
			return true;
		return false;
			
	}

	/**
	 *Sets the result a tie in this current{@code Round}. Should be used only in tie breaking {@code Round}s
	 *
	 * @throws ResultCannotBeSetException when the two scores are equal
	 * @throws NoFixtureException  when the {@code Fixture} doesn't exist
	 */
	public void setTieResult( Competitor com1, double score1, double score2, Competitor com2)
			throws ResultCannotBeSetException, NoFixtureException
	{
		if ( hasTie() )
		{
			Predicate<Fixture> predicate = 
					f-> Competitor.isEqual( f.getCompetitorOne() , com1 ) && 
			   			Competitor.isEqual( f.getCompetitorTwo() , com2 );
					
			if ( score1 == score2 )
				throw new ResultCannotBeSetException( "The score inputed is invalid " +"Score 1 = "+score1+"Score 2 = "+score2);
			else if ( activeTies.stream().anyMatch( predicate  ))
			{
				for ( int i = 0 ; i < activeTies.size() ; i++ )
				{
					
					Fixture tieFixture = activeTies.get( i );
					if ( Competitor.isEqual( tieFixture.getCompetitorOne() , com1 ) && 
						 Competitor.isEqual( tieFixture.getCompetitorTwo() ,  com2 ) )
					{
						tieFixture.eliminateLoser();
						tieRounds.get( getCurrentRoundNum() ).setResult(tieFixture, score1, score2);;
						activeTies.remove( i );
					}
				}
			}
			else
				throw new NoFixtureException( "The fixture not in active ties" );
		}
			
	}
	
	/**
	 * Adds a {@code Fixture} to a tie list
	 *@author Oguejiofor Chidiebere
	 *@see v1.0
	*@param com1 the home {@code Competitor}
	 *@param score1 the home team score
	 *@param score2 the away team score
	 *@param com2 the away {@code Competitor}
	 *@throws ResultCannotBeSetException
	 */
	private void addToTieList(Competitor com1, double score1, double score2, Competitor com2) throws ResultCannotBeSetException  {
		Fixture fixture = new Fixture( getSportType(), com1, com2);

		
		activeTies.add(fixture);
	}

	/**
	 * Checks if there is a tie in the current {@code Round}
	 *@return
	 */
	public boolean hasTie() {
		return activeTies.size() == 0 ? false : true;
	}

	/**
	 * Gets the active ties in the current {@code Round}
	 *@return  a {@code Fixture[]} object containing all the {@code Fixture}s
	 *@since v1.0
	 *@author Oguejiofor Chidiebere
	 *@see Fixture
	 */
	public Fixture[] getActiveTies() {
		if (activeTies.size() <= 0)
			return null;

		Fixture[] fixes = new Fixture[activeTies.size()];
		activeTies.toArray(fixes);
		return fixes;
	}

	public boolean isTieRound( int roundNum ) 
	{
		return tieRounds.containsKey( roundNum );

	}

	public String toString() {
		
		if ( hasEnded() )
			return "Tournament Has Ended";
		if ( isTieRound() )
			return "Round Ties";
		String message = null;
		String leg = null;
		if ( hasAway() && isSecondLeg() )
			leg = "Second-Leg";
		else if( hasAway() && !isFinal() )
			leg = "First-Leg";
		else
			leg = "";
		
		
		switch (getActiveCompetitors().length) {

		case 1:
			message = "Tournament Ended: \nWinner is " + getWinner();
			break;
		case 2:
			message = "Final";
			break;
		case 4:
			if ( isSemiFinal() )
				message = "Semi final";
			else
				message = "Third-place fixture";
			break;
		case 8:
			message = "Quarter-Final";
			break;
		
		default:
			message = "Round of " + (getCurrentRoundNum() + 1);
		}
		return  message + " " + leg ;
	}

	@Override
	public boolean hasEnded() {
		return getActiveCompetitors().length == 1 ? true : false;
	}

	@Override
	public Competitor[] getTopThree()
	{
		if ( hasEnded() )
			return topThree;
		return null;
	}

	public boolean isTieRound()
	{
		return tieRound;
	}

	public void setTieRound(boolean tieRound)
	{
		this.tieRound = tieRound;
	}

}
