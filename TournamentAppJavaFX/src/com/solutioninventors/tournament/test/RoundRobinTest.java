/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTest.java
 *Aug 5, 2017
 *8:07:58 AM
 */
package com.solutioninventors.tournament.test;

import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.group.InvalidBreakerException;
import com.solutioninventors.tournament.group.RoundRobinTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class RoundRobinTest
{

	public static void main( String[]  args )
	{
		//File file = new File("/test/golf.jpg");
		File file = new File("golf.jpg");
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		
//		Competitor c5 = new Competitor( "Ada" , file );
//		Competitor c6 = new Competitor( "Oguejiofor", file );
//		Competitor c7 = new Competitor( "Pio" , file );
//		Competitor c8 = new Competitor( "Oloche" ,  file ) ;
//		
//		Competitor c9 = new Competitor( "Manchester" , "United", file );
//		Competitor c10 = new Competitor( "Chealsea", file );
//		Competitor c11 = new Competitor( "Arsenal" , file );
//		Competitor c12 = new Competitor( "Real" ,  file ) ;
//		
//		Competitor c13 = new Competitor( "Barca" , file );
//		Competitor c14 = new Competitor( "Atletico", file );
//		Competitor c15 = new Competitor( "Lagos" , file );
//		Competitor c16 = new Competitor( "NIgeria" ,  file ) ;

		Competitor[] comps = { c1 , c2  , c3 , c4 }; 
				
		Test.displayMessage("Robin begins");

		Breaker[] breakers = {
				Breaker.GOALS_DIFFERENCE , Breaker.GOALS_SCORED, Breaker.HEAD_TO_HEAD
		};
		
		
		RoundRobinTournament tournament = null ;
		try
		{
			TieBreaker tieBreakers = new TieBreaker( breakers );
			tournament = new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 3 , 1 , 0 , tieBreakers  , false );
		}
		catch (InvalidBreakerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuilder builder = new StringBuilder( 300 );
		
		builder.append( "The competitors are: \n" );
		Competitor[] tournamentComps = tournament.getCompetitors() ;
		
		for ( int i =  0 ; i < tournamentComps.length ; i ++ )
		{
			builder.append( (i+1) + ". " + tournamentComps[ i ] + " \n" ); 
		}
		
		
		Test.displayMessage( builder.toString() );
		Test.displayStandingTable(   tournament.getTable().getStringTable() );
		while( !tournament.hasEnded() )//tournament is ongoing
		{
			
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
			tournament.moveToNextRound(); 
			
			Test.displayMessage( builder.toString()  );
			Test.displayStandingTable(   tournament.getTable().getStringTable() );
		}
		
		Test.displayMessage( "The winner is " + tournament.getWinner()) ;
	}

	
}
