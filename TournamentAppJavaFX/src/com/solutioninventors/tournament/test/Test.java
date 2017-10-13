/**
 *@Author: Oguejiofor Chidiebere
 *Test.java
 *Aug 9, 2017
 *3:30:45 PM
 */
package com.solutioninventors.tournament.test;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.types.group.StandingTable;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class Test {
	public static void displayStandingTable(String[][] stringTable) {

		
		displayMessage("The table is \n" + getTableToDisplay(stringTable));
		

	}

	
	
	public static void printRounds( Round[] rounds 	)
	{
		for ( int i = 0 ; i < rounds.length ; i++ )
		{
			System.out.println( "Round " + ( i+ 1) );
			System.out.println( getFixutures( rounds[i].getFixtures()  ) );
			
		}
	}
	public static String getTableToDisplay(String[][] stringTable )
	{
		StringBuilder builder  = new StringBuilder(1000);
		if (stringTable[0].length == 9)
			builder.append(String.format( "%-20s %s", "Name" , 
					"P W D L F A GD  Pts\n"));
		else
			builder.append(String.format( "%-20s %s", "Name" , 
					"P W D L Pts\n"));
			
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
		return builder.toString() ;
	}

	public static void displayFixtures(Fixture[] currentFixtures) {
		
		displayMessage(getFixutures(currentFixtures));

	}

	public static String getAllTables(StandingTable table)
	{
		StringBuilder builder = new StringBuilder(400);
		builder.append( "The Tournament Tables are:\n\n" );
		builder.append( "Home Fixtures:\n" );
		builder.append( Test.getTableToDisplay( table.getHomeStringTable()  ) );
		
		builder.append( "\nAway Fixtures:\n" );
		builder.append( Test.getTableToDisplay( table.getAwayStringTable()  ) );
		
		builder.append( "\nAll Fixtures:\n" );
		builder.append( Test.getTableToDisplay( table.getStringTable()  ) );
		return builder.toString();
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
		
		displayMessage(getResultString(currentRound));

	}

	public static String getResultString(Round currentRound)
	{
		Fixture[] fixtures = currentRound.getFixtures();

		StringBuilder builder = new StringBuilder(500);
		builder.append("Round Results: \n");
		for (Fixture fixture : fixtures) {
			try
			{
				builder.append(String.format("%s %.1f VS %.1f %s\n", fixture.getCompetitorOne(),
						fixture.getCompetitorOneScore(true), fixture.getCompetitorTwoScore(true), fixture.getCompetitorTwo()));
			}
			catch (IncompleteFixtureException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

}
