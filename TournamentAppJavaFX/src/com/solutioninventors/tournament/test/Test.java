/**
 *@Author: Oguejiofor Chidiebere
 *Test.java
 *Aug 9, 2017
 *3:30:45 PM
 */
package com.solutioninventors.tournament.test;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class Test {
	public static void displayStandingTable(String[][] stringTable) {
		StringBuilder builder = new StringBuilder(1000);

		
		
		if (stringTable[0].length == 8)
			builder.append(String.format( "%-20s %s", "Name" , 
					"W D L F A GD  Pts\n"));
		else
			builder.append(String.format( "%-20s %s", "Name" , 
					"W D L Pts\n"));
			
		for (int row = 0; row < stringTable.length; row++) {
			for (int col = 0; col < stringTable[row].length; col++)
			{
				if ( col == 0 )
					builder.append(String.format("%-20s ", stringTable[row][col]));
				
				else
					builder.append(String.format("%s ", stringTable[row][col]));
			}

			builder.append("\n");

		}
		displayMessage("The table is \n" + builder.toString());
		;

	}

	public static void displayFixtures(Fixture[] currentFixtures) {
		
		displayMessage(getFixutures(currentFixtures));

	}

	public static String getFixutures(Fixture[] currentFixtures)
	{
		StringBuilder builder = new StringBuilder(500);

		for (int i = 0; i < currentFixtures.length; i++)
			builder.append(String.format("%s VS %s\n", currentFixtures[i].getCompetitorOne(),
					currentFixtures[i].getCompetitorTwo()));
		return builder.toString();
	}

	public static void displayMessage(String message) 
	{
		System.out.println(message);
		JOptionPane.showMessageDialog(null, message);
	}

	public static void displayRoundResults(Round currentRound)
	{
		
		try {
			displayMessage(getResultString(currentRound));
		} catch (IncompleteFixtureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getResultString(Round currentRound) throws IncompleteFixtureException
	{
		Fixture[] fixtures = currentRound.getFixtures();

		StringBuilder builder = new StringBuilder(500);
		builder.append("Round Results: \n");
		for (Fixture fixture : fixtures) {
			builder.append(String.format("%s %.1f VS %.1f %s\n", fixture.getCompetitorOne(),
					fixture.getCompetitorOneScore(), fixture.getCompetitorTwoScore(), fixture.getCompetitorTwo()));
		}
		return builder.toString();
	}

}
