/**
 *@Author: Oguejiofor Chidiebere
 *SingleEliminationTournament.java
 *Aug 7, 2017
 *2:38:15 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 *
 */
public class SingleEliminationTournament extends EliminationTournament {

	/**
	 * Stores all the rounds in this tournament
	 */
	private List<Round> roundList;
	/**
	 * Stores all the ties that  occur in this tournament
	 */
	private final List<Fixture> tieList;
	/**
	 * Stores the active ties in this {@code Tournament}
	 */
	private List<Fixture> activeTies;
	/**
	 * {@code true} when this {@code SingleEliminationTournament} has away fixtures
	 */
	private final boolean AWAY;

	
	private final Competitor[] topThree;
	/**
	 * Initializes this {@code SingleEliminationTournament} with the {@code Competitor[]}
	 * 
	 *@param type
	 *@param comps
	 *@param away
	 *@throws TournamentException
	 */
	public SingleEliminationTournament(SportType type, Competitor[] comps, boolean away) throws TournamentException {
		super( type, comps);

		roundList = new ArrayList<>();
		AWAY = away;
		createTounament();
		tieList = new ArrayList<>();
		activeTies = new ArrayList<>();
		topThree = new Competitor[3];
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
	public void moveToNextRound() throws TournamentEndedException, MoveToNextRoundException {
		if (!hasEnded() && getCurrentRound().isComplete()) {
			try
			{
				if (hasAway()) 
				{
					
				
					if (getActiveCompetitors().length == 2)// it is the final
					{
						Fixture fix = getCurrentRound().getFixtures()[0];
						topThree[0]= fix.getWinner();
						topThree[1]= fix.getLoser();
						fix.eliminateLoser();
					} 
					else if (getCurrentRoundNum() % 2 == 0) // is first leg
						incrementRoundNum();
					else // second leg fixture
					{
						createNextRound();
					}
				} 
				else 
				{
					if ( getActiveCompetitors().length > 2 &&
							getCurrentRound().getNumberOfFixtures() == 1)
					{
						if ( !getCurrentRound().hasDraw() )
						{
							Fixture f =  getCurrentRound().getFixtures()[0];
							f.getWinner().setEliminated( true );
							f.getLoser().setEliminated( true );
							topThree[ 2 ] = f.getWinner();
							createNextRound();
						}
						else
							throw new MoveToNextRoundException("Some fixtures are incomplete");
						
					}
					
					else if (!getCurrentRound().hasDraw()) // no ties
					{
						createNextRound();
					} 
					else
						throw new MoveToNextRoundException("Some fixtures are incomplete");
				}
			}
			catch (IncompleteFixtureException e)
			{
				throw new MoveToNextRoundException( e.getMessage() );
			}
			
		} else
			throw new TournamentEndedException();

	}

	/**
	 * Eliminates losers and creates the next {@code Round}
	 * @throws IncompleteFixtureException whem some {@code Fixture}s in the
	 * current round do not have results
	 */
	public void createNextRound() throws IncompleteFixtureException {
		eliminateLosers();
		
		if (!hasEnded()) {
			Competitor[] comps;
			Fixture[] fixtures;
			if ( isSemiFinal() && !hasAway() ) // create third place match
			{
				comps = getCurrentRound().getLosers();
				fixtures = new Fixture[comps.length / 2];
				
			}
			else // create next round
			{
				comps = getActiveCompetitors();

				fixtures = new Fixture[comps.length / 2];

				
			}
			for (int i = 0; i < comps.length; i += 2) {
				fixtures[i / 2] = new Fixture( getSportType(), comps[i], comps[i + 1]);

			}

			incrementRoundNum();
			
			roundList.add(new Round(fixtures));
			
			if (hasAway() && getActiveCompetitors().length > 2)
				roundList.add(new Round(fixtures).invertHomeAndAway( getSportType()));
		}
	}

	/**
	 * Eliminates the losers in the current {@code Round}
	 *@throws IncompleteFixtureException if there are incmoplete {@code Fixture}s
	 *in the current {@code Round} 
	 *@author Oguejiofor Chidiebere
	 *@since v1.0
	 *@see Fixture
	 */
	private void eliminateLosers() throws IncompleteFixtureException 
	{

		if ( getActiveCompetitors().length == 2 )
		{
			Fixture f = getCurrentRound().getFixtures()[0];
			topThree[0] = f.getWinner();
			topThree[1] = f.getLoser();
		}
		if (!hasAway()) 
		{
			if ( !isSemiFinal() )//semi-final
			{
				Arrays.stream(getCurrentRound().getFixtures())
					.forEach(f -> f.eliminateLoser() ); 
			}
			
		} 
		else if ( getSportType() ==  SportType.GOALS_ARE_SCORED ) //implement away goal rule
		{
			List<Fixture> firstLeg = new ArrayList<>();
			List<Fixture> secondLeg = new ArrayList<>();

			firstLeg.addAll(Arrays.asList(getRound(getCurrentRoundNum()).getFixtures()));
			secondLeg.addAll(Arrays.asList(getCurrentRound().getFixtures()));

			for (int i = 0; i < firstLeg.size(); i++) 
			{
				Competitor com1 = firstLeg.get(i).getCompetitorOne();
				Competitor com2 = firstLeg.get(i).getCompetitorTwo();

				
				double totalComOneScore = firstLeg.get(i).getCompetitorOneScore()
						+ secondLeg.get(i).getCompetitorTwoScore();
				double totalComTwoScore = firstLeg.get(i).getCompetitorTwoScore()
						+ secondLeg.get(i).getCompetitorOneScore();
				
				if (totalComOneScore > totalComTwoScore)
					eliminateLoser(com2);
				else if (totalComOneScore < totalComTwoScore)
					eliminateLoser(com1);
				else 
				{
					if (com1.getAwayGoal(com2) > com2.getAwayGoal(com1))
						eliminateLoser(com1);
					else
						eliminateLoser(com2);
				}
			}
		
		}
	}

	public boolean isSemiFinal()
	{
		return getCurrentRound().getNumberOfFixtures() == 2  &&
				getActiveCompetitors().length == 4;
	}

	/**
	 * Eliminates a specified {@code Competitor} 
	 *void
	 *@param com1
	 */
	private void eliminateLoser(Competitor com1)
	{
		Arrays.stream( getActiveCompetitors() )
			.filter( c -> Competitor.isEqual( c, com1 ) )
			.findFirst()
			.get().setEliminated( true );
		
	}

	@Override
	public Competitor getWinner() {
		if (hasEnded())
			return getActiveCompetitors()[0];
		return null;
	}

	/**
	 * Sets the result of a {@code Fixture} in this {@code SingleEliminationTournament}
	 * current {@code Round}. It first checks for such a {@code Fixture} in its active ties
	 * and checks other {@code Fixture}s only when it does not find a match <p>
	 * If the {@code Fixture} is a draw then this method adds the {@code Fixture} to 
	 * a tie list which can be retrieved by method getActiveTies.
	 * 
	 */
	@Override
	public void setResult(Competitor com1, double score1, double score2, Competitor com2)
			throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException 
	{
		if (!hasEnded()) {
			if (activeTies.stream().anyMatch(f -> f.hasFixture(com1, com2))) {
				for (int i = 0; i < activeTies.size(); i++)
					if (activeTies.get(i).hasFixture(com1, com2)) {
						activeTies.remove(i);
						break;
					}
			}
			if (hasAway()) {
				if (getActiveCompetitors().length == 2 && score1 != score2) {
					getCurrentRound().setResult(com1, score1, score2, com2);
				} else if (getCurrentRoundNum() % 2 == 0)
					getCurrentRound().setResult(com1, score1, score2, com2);
				else {
					double totalComOneScore = score1 + com1.getHeadToHeadScore(com2);
					double totalComTwoScore = score2 + com2.getHeadToHeadScore(com1);

					if (totalComOneScore != totalComTwoScore) {
						getCurrentRound().setResult(com1, score1, score2, com2);
					} else if ((totalComOneScore == totalComTwoScore) && (com1.getAwayGoal(com2) != score2)) {
						getCurrentRound().setResult(com1, score1, score2, com2);
					} else {
						addToTieList(com1, score1, score2, com2);
					}
				}
			} else {
				if (score1 != score2)
					getCurrentRound().setResult(com1, score1, score2, com2);
				else
					addToTieList(com1, score1, score2, com2);
			}
		} else
			throw new TournamentEndedException("Tournament is over");

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

		
		Round temp = new Round ( fixture);
		try
		{
			temp.setResult(fixture, score1, score2, false);
		}
		catch (NoFixtureException e)
		{}
		tieList.add(fixture);
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

	public Fixture[] getTournamentTies() {
		if (tieList.size() <= 0)
			return null;

		Fixture[] fixes = new Fixture[tieList.size()];
		return tieList.toArray(fixes);

	}

	public String toString() {
		String message = null;

		switch (getActiveCompetitors().length) {

		case 1:
			message = "Tournament Ended: \nWinner is " + getWinner();
			break;
		case 2:
			message = "Final";
			break;
		case 4:
			if ( isSemiFinal() )
				message = "Semifinal";
			else
				message = "Third-place fixture";
			break;
		case 8:
			message = "Quarter-Final";
			break;
		
		default:
			message = "Round of " + (getCurrentRoundNum() + 1);
		}
		return message;
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

}
