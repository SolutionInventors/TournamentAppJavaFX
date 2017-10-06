/**
 *@Author: Oguejiofor Chidiebere
 *SingleEliminationTest.java
 *Aug 9, 2017
 *3:30:11 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.knockout.EliminationTournament;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;

public class SingleEliminationTest {

	public static void main(String[] args)  {
		File file = new File("First .jpg");
		
		Competitor c1 = new Competitor("Chidiebere", file);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

		Competitor[] comps = { c1, c2, c3, c4 };

		int ans = Integer.parseInt( JOptionPane.showInputDialog(
				"Type 1 for home and away else only home fixtures" ));
		
		boolean homeAndAway = ans == 1 ? true : false ;
		Tournament tournament = null;
		try 
		{
			tournament = new SingleEliminationTournament(SportType.GOALS_ARE_SCORED , comps , homeAndAway );
		} catch (TournamentException e) {
			Test.displayMessage(e.getMessage());
			System.exit(1);

		}
		
		
		Test.displayMessage("Single Elimination begins");

		while (!tournament.hasEnded()) 
		{
			try
			{
				simulateRound(tournament);
			}
			catch (TournamentEndedException | MoveToNextRoundException e)
			{
				
			}
		}
		Test.displayMessage("The winner is " + tournament.getWinner());
		StringBuilder builder = new StringBuilder(300);
		
		EliminationTournament elimSpecific = 
				(EliminationTournament) tournament;
		
		Competitor[] topThree = elimSpecific.getTopThree() ;
		for ( int i =  0 ;i < topThree.length ; i++ )
			builder.append( (i + 1) + ". " + topThree[i] + "\n");
		
		Test.displayMessage( builder.toString() );
	}

	public static void simulateRound(Tournament tournament) throws MoveToNextRoundException, TournamentEndedException
	{
		Test.displayMessage("Welcome to the " + tournament.toString());
		inputRoundResults((SingleEliminationTournament) tournament);

		Test.displayRoundResults(tournament.getCurrentRound());
		tournament.moveToNextRound();
		System.out.println(tournament.hasEnded());
	}

	

	public static void inputRoundResults(SingleEliminationTournament tournament) throws TournamentEndedException {
		StringBuilder builder = new StringBuilder(400);
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures();
		Test.displayFixtures(currentFixtures);

		builder.delete(0, builder.length());
		builder.append("Roound results are: \n");
		for (int i = 0; i < currentFixtures.length; i++) {
			Competitor com1 = currentFixtures[i].getCompetitorOne();
			Competitor com2 = currentFixtures[i].getCompetitorTwo();

			double score1 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com1));
			double score2 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com2));

			try {
				if ( tournament.isTieRound() )
					tournament.setTieResult(com1, score1, score2, com2);
				else
					tournament.setResult(com1, score1, score2, com2);
				
				builder.append(
						String.format("%s %.0f VS %.0f %s\n", com1, currentFixtures[i].getCompetitorOneScore(),
						currentFixtures[i].getCompetitorTwoScore(), com2));
			} catch (NoFixtureException | ResultCannotBeSetException | IncompleteFixtureException e) {
				e.printStackTrace();
			}
			

		}

	}

}
