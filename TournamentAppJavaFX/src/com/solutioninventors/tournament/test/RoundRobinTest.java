/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTest.java
 *Aug 5, 2017
 *8:07:58 AM
 *//*
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
		//File file = new File("/test/golf.jpg");
		File file = new File("C:\\Users\\Chinedu\\git\\TournamentAppJavaFX\\TournamentAppJavaFX\\bin\\com\\solutioninventors\\tournament\\test\\golf.jpg");
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
<<<<<<< HEAD
		//Competitor[] comps = { c1 , c2  , c3 , c4 ,c5 , c6  , c7  , c8, 
		//		c9 , c10  , c11 , c12 ,c13 , c14  , c15  , c16} ;
		Competitor[] comps = { c1 , c2  , c3 , c4 }; 
				//		c9 , c10  , c11 , c12 ,c13 , c14  , c15  , c16} ;
		System.out.println("Robin begins");
=======
		Competitor[] comps = { c1 , c2  , c3 , c4 ,c5 , c6  , c7  , c8} ;
		
		JOptionPane.showMessageDialog(null ,"Robin begins");
>>>>>>> refs/remotes/origin/master
		RoundRobinTournament tournament = 
				new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 3 , 1 , 0  , false );
		
		Round[] rounds = tournament.getRoundsArray() ;
		Fixture[] temp = null ;
		StringBuilder m = new StringBuilder( 200 );
		JOptionPane.showMessageDialog(null, "The competitors in the tournament are"); 
		for ( int i  = 0  ; i < comps.length ; i ++ )
		{
			m.append( ( i+1 ) + ". " + comps[ i ].getName() + "\n");
			System.out.print( ( i+1 ) + ". " + comps[ i ].getName() + "\n" );
		}
		JOptionPane.showMessageDialog( null , m );
		m.delete( 0 , m.length() -1 );
		
		while( !tournament.hasEnded() )
		{
			for ( int i = 0 ; i < rounds.length ; i++ )
			{
				m.append( "Round " + ( i + 1) +"\n");
				System.out.print( "Round " + ( i + 1) +"\n");
				temp = rounds[ i ].getFixtures() ;
				
				for ( Fixture f : temp )
				{
					m.append(String.format( "%30s VS %s\n" , f.getCompetitorOne().getName() , 
							f.getCompetitorTwo().getName()) );
					System.out.print( String.format( "%30s VS %s\n" , f.getCompetitorOne().getName() , 
							f.getCompetitorTwo().getName()) );
				}
				System.out.println();
				m.append( "\n" );
			}
			JOptionPane.showMessageDialog( null , m );
			Fixture[] fixtures = tournament.getCurrentRound().getFixtures() ;
			
			JOptionPane.showMessageDialog( null  , "Welcome to Round " + tournament.getCurrentRoundNum() );
			StringBuilder resultsMessage = new StringBuilder( 200 );
			resultsMessage.append( "Results: \n" );
			for ( int i = 0  ; i < fixtures.length -1 ; i ++ )
			{
				String message = String.format( "%s\n%s  VS %s", "Input Score for fixture:" ,
						fixtures[ i ].getCompetitorOne() ,  fixtures[ i ].getCompetitorTwo() );
				
				JOptionPane.showMessageDialog( null , message );
				double score1 = Double.parseDouble( JOptionPane.showInputDialog( fixtures[ i ].getCompetitorOne() ) );
				double score2 = Double.parseDouble( JOptionPane.showInputDialog( fixtures[ i ].getCompetitorTwo() ) );
				
				fixtures[ i ].setResult(score1, score2);
				fixtures = tournament.getCurrentRound().getFixtures() ;
				resultsMessage.append( 
										String.format( "%s %.0f VS %.0f %s\n",
										fixtures[ i ].getCompetitorOne() , fixtures[ i ]. getCompetitorOneScore() , 
										fixtures[ i ].getCompetitorTwoScore() , fixtures[ i ].getCompetitorTwo()) 
									);
			}
			
			
			JOptionPane.showMessageDialog( null , resultsMessage );
			
			tournament.moveToNextRound();
			JOptionPane.showMessageDialog(null , "Welcome to  Round: " + tournament.getCurrentRoundNum());
			
		}
		
//		System.out.println("Current Round Number " + tournament.getCurrentRoundNum());
//		
//		System.out.println("Round Num after call to moveToNextRound() = " + 
//		
//		tournament.getCurrentRoundNum() );
	}

}
*/