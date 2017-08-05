/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTest.java
 *Aug 5, 2017
 *8:07:58 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.group.RoundRobinTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

public class RoundRobinTest
{

	public static void main( String[]  args )
	{
		File file = new File ( "golf.jpg");
		
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		
		Competitor c5 = new Competitor( "Ada" , file );
		Competitor c6 = new Competitor( "Oguejiofor", file );
		Competitor c7 = new Competitor( "Pio" , file );
		Competitor c8 = new Competitor( "Oloche" ,  file ) ;
		
		Competitor c9 = new Competitor( "Manchester" , "United", file );
		Competitor c10 = new Competitor( "Chealsea", file );
		Competitor c11 = new Competitor( "Arsenal" , file );
		Competitor c12 = new Competitor( "Real" ,  file ) ;
		
		Competitor c13 = new Competitor( "Barca" , file );
		Competitor c14 = new Competitor( "Atletico", file );
		Competitor c15 = new Competitor( "Lagos" , file );
		Competitor c16 = new Competitor( "NIgeria" ,  file ) ;
		Competitor[] comps = { c1 , c2  , c3 , c4 ,c5 , c6  , c7  , c8, 
				c9 , c10  , c11 , c12 ,c13 , c14  , c15  , c16} ;
		
		System.out.println("Robin begins");
		RoundRobinTournament tournament = 
				new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , false  );
		
		Round[] rounds = tournament.getRoundsArray() ;
		Fixture[] temp = null ;
		System.out.println("The competitors in the tournament are"); 
		for ( int i  = 0  ; i < comps.length ; i ++ )
			System.out.println( ( i+1 ) + ". " + comps[ i ].getName() );
		for ( int i = 0 ; i < rounds.length ; i++ )
		{
			System.out.println("Round " + ( i + 1) );
			temp = rounds[ i ].getFixtures() ;
			for ( Fixture f : temp )
				System.out.printf( "%30s VS %s\n" , f.getCompetitorOne().getName() , 
						f.getCompetitorTwo().getName());
			System.out.println( "\n");
		}
		
		Fixture[] fixtures = tournament.getCurrentRound().getFixtures() ;
		
		JOptionPane.showMessageDialog( null  , "Welcome to Round " + tournament.getCurrentRoundNum() );
		StringBuilder resultsMessage = new StringBuilder( 200 );
		resultsMessage.append( "Results: \n" );
		for ( int i = 0  ; i < fixtures.length ; i ++ )
		{
			String message = String.format( "%s\n%s  VS %s", "Input Score for fixture:" ,
					fixtures[ i ].getCompetitorOne() ,  fixtures[ i ].getCompetitorTwo() );
			
			JOptionPane.showMessageDialog( null , message );
			int score1 = Integer.parseInt( JOptionPane.showInputDialog( fixtures[ i ].getCompetitorOne() ) );
			int score2 = Integer.parseInt( JOptionPane.showInputDialog( fixtures[ i ].getCompetitorTwo() ) );
			
			fixtures[ i ].setResult(score1, score2);
			resultsMessage.append( 
									String.format( "%s %d VS %d %s\n",
									fixtures[ i ].getCompetitorOne() , fixtures[ i ]. getCompetitorOneScore() , 
									fixtures[ i ].getCompetitorTwoScore() , fixtures[ i ].getCompetitorTwo()) 
								);
		}
		
		
		JOptionPane.showMessageDialog( null , resultsMessage );
		
		tournament.moveToNextRound();
		System.out.println("Welcome to  Round: " + tournament.getCurrentRoundNum());
		
//		System.out.println("Current Round Number " + tournament.getCurrentRoundNum());
//		
//		System.out.println("Round Num after call to moveToNextRound() = " + 
//		
//		tournament.getCurrentRoundNum() );
	}

}
