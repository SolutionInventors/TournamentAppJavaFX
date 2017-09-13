/**
 *@Author: Oguejiofor Chidiebere
 *ChallengeTest.java
 *Aug 10, 2017
 *3:37:33 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.OnlyOutstandingAreLeftException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Challenge;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

public class ChallengeTest {

	public static void main(String[] args) throws OnlyOutstandingAreLeftException {
		File file = new File("Arsenal.jpg");
		
		Competitor[] coms = { new Competitor("Chinedu", file), new Competitor("Chidi", file) };

		Tournament tournament = new Challenge(coms, 2);

		Test.displayMessage("Challenge between \n" + coms[0] + " and " + coms[1] + "begins");

		while (!tournament.hasEnded()) 
		{
			try
			{
				simulateRound(tournament);
			}
			catch (TournamentEndedException e)
			{
				break;
			}
			
		}

		Test.displayMessage("The winner is " + tournament.getWinner());

	}

	public static void simulateRound(Tournament tournament) throws TournamentEndedException, OnlyOutstandingAreLeftException
	{
		Test.displayMessage("Welcome to the " + tournament.toString());

		inputRoundResults(tournament);

		Test.displayRoundResults(tournament.getCurrentRound());
		try {
			tournament.moveToNextRound();
		} catch (MoveToNextRoundException e) {
			e.printStackTrace();
		}
	}

	private static void inputRoundResults(Tournament tournament) throws TournamentEndedException, OnlyOutstandingAreLeftException {
		Test.displayFixtures(tournament.getCurrentRound().getFixtures());
		Fixture fixture = tournament.getCurrentRound().getFixtures()[0];
		Competitor com1 = fixture.getCompetitorOne();
		Competitor com2 = fixture.getCompetitorTwo();

		double score1 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com1));
		double score2 = Double.parseDouble(JOptionPane.showInputDialog("Input score for " + com2));

		try {
			tournament.setResult(com1, score1, score2, com2);
		} catch (NoFixtureException | ResultCannotBeSetException e) {
			e.printStackTrace();
		}

		Test.displayMessage("Round results:\n" + String.format("%s %.0f VS %.0f %s\n", com1,
				fixture.getCompetitorOneScore(), fixture.getCompetitorTwoScore(), com2));

	}

}
