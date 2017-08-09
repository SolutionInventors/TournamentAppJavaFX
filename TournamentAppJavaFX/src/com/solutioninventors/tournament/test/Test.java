/**
 *@Author: Oguejiofor Chidiebere
 *Test.java
 *Aug 9, 2017
 *3:30:45 PM
 */
package com.solutioninventors.tournament.test;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class Test
{
	public static void displayStandingTable(String[][] stringTable)
	{
		StringBuilder builder = new StringBuilder( 1000 );
		
		for( int row = 0 ; row < stringTable.length ; row++ )
		{
			for( int col = 0 ; col < stringTable[row].length ; col ++)
				builder.append( String.format("%s ", stringTable[row][ col ] ));
			
			builder.append( "\n");
				
		}
		displayMessage("The table is \n" +  builder.toString() );;
			
	}

	public static void displayFixtures(Fixture[] currentFixtures)
	{
		StringBuilder builder = new StringBuilder(500 );
		
		for ( int i = 0 ; i < currentFixtures.length ; i++ )
			builder.append(String.format( "%s VS %s\n" ,
					currentFixtures[ i ].getCompetitorOne() , currentFixtures[ i ].getCompetitorTwo() ));
		
		displayMessage( builder.toString() );
		
		
	}

	public static void displayMessage(String message) 
	{
		System.out.println( message );
		JOptionPane.showMessageDialog(null ,message );
	}

	public static void displayRoundResults(Round currentRound)
	{
		Fixture[] fixtures = currentRound.getFixtures();
		
		StringBuilder builder = new StringBuilder(500 );
		builder.append( "Round Results: " );
		for (Fixture fixture : fixtures)
		{
			builder.append(String.format( "%s %.1f VS %.1f %s\n" ,
					fixture.getCompetitorOne() , fixture.getCompetitorOneScore(),
					fixture.getCompetitorTwoScore() , fixture.getCompetitorTwo() ));
		}
			
		displayMessage( builder.toString() );
		
	}

}
