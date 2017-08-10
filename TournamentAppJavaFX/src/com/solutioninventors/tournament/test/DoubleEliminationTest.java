/**
 *@Author: Oguejiofor Chidiebere
 *DoubleEliminationTest.java
 *Aug 9, 2017
 *7:30:18 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.knockout.DoubleElimination;
import com.solutioninventors.tournament.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

public class DoubleEliminationTest
{

	public static void main(String[] args)
	{
		File file = new File("golf.jpg");
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		
		

		Competitor[] comps = { c1 , c2  , c3 , c4 }; 

		DoubleElimination tournament = null ;
		try
		{
			tournament = new DoubleElimination(comps);
		}
		catch (TournamentException e)
		{
			Test.displayMessage( e.getMessage() );
			System.exit( 1 );
			
		}
		Test.displayMessage("Double Elimination begins");
		
		
		while( ! tournament.hasEnded() 	)
		{
			Test.displayMessage("Welcome to the " + tournament.toString() );
			
			inputRoundResults(tournament);
			
			Test.displayRoundResults( tournament.getCurrentRound() );
			try
			{
				tournament.moveToNextRound();
			}
			catch (MoveToNextRoundException e)
			{
				e.printStackTrace();
			}
		}

		Test.displayMessage("The winner is " + tournament.getWinner() );
	}

	
	private static void breakTies( SingleEliminationTournament tournament )
	{
		StringBuilder builder = new StringBuilder( 400 );
		Fixture[] currentFixtures = tournament.getActiveTies()  ;
		Test.displayMessage("Current Ties: \n" );
		Test.displayFixtures( currentFixtures );
		
		builder.delete(0 , builder.length() );
		builder.append("Tie Result: \n" );
		for( int i = 0 ; i< currentFixtures.length ;i++ )
		{
			Competitor com1 = currentFixtures[i].getCompetitorOne() ;
			Competitor com2 = currentFixtures[i].getCompetitorTwo() ;

			double score1 = Double.parseDouble(JOptionPane.showInputDialog( "Input score for " + 
								com1 ));
			double score2 = Double.parseDouble(JOptionPane.showInputDialog( "Input score for " + 
					 com2 ));

			tournament.setResult( com1, score1, score2, com2);
			builder.append(String.format("%s %.0f VS %.0f %s\n",
					com1 , score1 , score2 , com2 ));
			
			
		}
		
		Test.displayMessage( builder.toString()  );
		
	}

	public static void inputRoundResults(DoubleElimination tournament)
	{
		StringBuilder builder = new StringBuilder( 400 );
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures() ;
		Test.displayFixtures( currentFixtures );
		
		builder.delete(0 , builder.length() );
		builder.append("Roound results are: \n" );
		for( int i = 0 ; i< currentFixtures.length ;i++ )
		{
			Competitor com1 = currentFixtures[i].getCompetitorOne() ;
			Competitor com2 = currentFixtures[i].getCompetitorTwo() ;

			double score1 = Double.parseDouble(JOptionPane.showInputDialog( "Input score for " + 
								com1 ));
			double score2 = Double.parseDouble(JOptionPane.showInputDialog( "Input score for " + 
					 com2 ));
			
			tournament.setResult( com1, score1, score2, com2);
			builder.append(String.format("%s %.0f VS %.0f %s\n",
					com1 , currentFixtures[ i ].getCompetitorOneScore() ,
					currentFixtures[  i ].getCompetitorTwoScore() , com2 ));
			
			
		}
		
	}
	
	
}
