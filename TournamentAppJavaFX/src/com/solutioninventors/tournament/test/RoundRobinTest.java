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
		File file = new File("golf.jpg");
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

		Competitor[] comps = { c1 , c2  , c3 , c4 }; 
				
		displayMessage("Robin begins");

		
		RoundRobinTournament tournament = 
				new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 3 , 1 , 0  , false );
		
		
	}

	public static void displayMessage(String message) 
	{
		System.out.println( message );
		JOptionPane.showMessageDialog(null ,message );
	}

}
