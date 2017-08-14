/**
 *@Author: Oguejiofor Chidiebere
 *SingleEliminationTest.java
 *Aug 9, 2017
 *3:30:11 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

public class SingleEliminationTest {

	public static void main(String[] args) throws  MoveToNextRoundException {
		File file = new File("golf.jpg");
		//File file = new File(InputStream = new InputStream(getClass().getResourceAsStream("/img/icon2.png")))
	//	getClass().getResourceAsStream("/img/icon2.png");
		Competitor c1 = new Competitor("Chidiebere", file);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

		Competitor[] comps = { c1, c2, c3, c4 };

		SingleEliminationTournament tournament = null;
		try 
		{
			tournament = new SingleEliminationTournament(comps , false );
		} catch (TournamentException e) {
			Test.displayMessage(e.getMessage());
			System.exit(1);

		}
		Test.displayMessage("Single Elimination begins");

		while (!tournament.hasEnded()) {
			Test.displayMessage("Welcome to the " + tournament.toString());

			inputRoundResults(tournament);

			while (tournament.hasTie())
				breakTies(tournament);

			Test.displayRoundResults(tournament.getCurrentRound());
			tournament.moveToNextRound();
		}

		Test.displayMessage("The winner is " + tournament.getWinner());

	}

	private static void breakTies(SingleEliminationTournament tournament) {
		StringBuilder builder = new StringBuilder(400);
		Fixture[] currentFixtures = tournament.getActiveTies();
		Test.displayMessage("Current Ties: \n");
		Test.displayFixtures(currentFixtures);

		builder.delete(0, builder.length());
		builder.append("Tie Result: \n");
		for (int i = 0; i < currentFixtures.length; i++) {
			Competitor com1 = currentFixtures[i].getCompetitorOne();
			Competitor com2 = currentFixtures[i].getCompetitorTwo();

			double score1 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com1));
			double score2 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com2));

			try
			{
				tournament.setResult(com1, score1, score2, com2);
			}
			catch (NoFixtureException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			builder.append(String.format("%s %.0f VS %.0f %s\n", com1, score1, score2, com2));

		}

		Test.displayMessage(builder.toString());

	}

	public static void inputRoundResults(SingleEliminationTournament tournament) {
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

			try
			{
				tournament.setResult(com1, score1, score2, com2);
			}
			catch (NoFixtureException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			builder.append(String.format("%s %.0f VS %.0f %s\n", com1, currentFixtures[i].getCompetitorOneScore(),
					currentFixtures[i].getCompetitorTwoScore(), com2));

		}

	}

}
