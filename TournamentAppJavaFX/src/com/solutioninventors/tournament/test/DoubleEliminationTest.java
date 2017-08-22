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
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

public class DoubleEliminationTest
{

	public static void main(String[] args)
	{
		File file = new File("Arsenal.jpg");
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		
		Competitor c5 = new Competitor( "Ada" , file );
		Competitor c6 = new Competitor( "Oguejiofor", file );
		Competitor c7 = new Competitor( "Pio" , file );
		Competitor c8 = new Competitor( "Oloche" ,  file ) ;
		

		Competitor[] comps = { c1 , c2  , c3 , c4 , 
				c5 , c6  , c7 , c8 }; 

		DoubleElimination tournament = null ;
		try
		{
			tournament = new DoubleElimination(comps);
		}
		catch (TournamentException e)
		{
			Test.displayMessage( e.getMessage() );
			System.exit( 0 );
			
		}
		Test.displayMessage("Double Elimination begins");
		
		
		while( ! tournament.hasEnded() 	)
		{
			Test.displayMessage("Welcome to the " + tournament.toString() );
			
			inputRoundResults(tournament);
			
			try
			{
				Test.displayMessage("Winner Bracket Results");
				
				Test.displayRoundResults( tournament.getCurrentRound(
						DoubleElimination.BracketType.WINNERS_BRACKET) );
				
				
				
				if ( tournament.getCurrentRoundNum() > 0)
				{
					Test.displayMessage("Loser Minor Bracket Results");
					Test.displayRoundResults( tournament.getCurrentRound(
							DoubleElimination.BracketType.MINOR_BRACKET) );
				}
					
				
				if ( tournament.isWinnerRoundComplete())
				{
					Test.displayMessage("Loser Major Bracket Results");
					
					Test.displayRoundResults( tournament.getCurrentRound(
							DoubleElimination.BracketType.MAJOR_BRACKET) );
				}
				tournament.moveToNextRound();
			}
			catch (MoveToNextRoundException e)
			{
				e.printStackTrace();
			}
			catch (RoundIndexOutOfBoundsException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Test.displayMessage("The winner is " + tournament.getWinner() );
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
			
			try
			{
				tournament.setResult( com1, score1, score2, com2);
			}
			catch (NoFixtureException e)
			{
				e.printStackTrace();
			}
			builder.append(String.format("%s %.0f VS %.0f %s\n",
					com1 , currentFixtures[ i ].getCompetitorOneScore() ,
					currentFixtures[  i ].getCompetitorTwoScore() , com2 ));
			
			
		}
		
	}
	
	
}
