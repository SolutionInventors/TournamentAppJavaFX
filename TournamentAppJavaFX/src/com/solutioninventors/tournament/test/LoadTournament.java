/**
 *@Author: Oguejiofor Chidiebere
 *LoadTournament.java
 *Aug 23, 2017
 *11:22:31 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;

public class LoadTournament
{

	public static void main(String[] args)
	{
		
		Tournament tournament = null ;
		
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.showOpenDialog( null );
		File file = fileChooser.getSelectedFile();
		
		if ( !file.exists() )
		{
			Test.displayMessage( "The file is invalid" );
			System.exit( 0 );
		}
		
		
		
		try
		{
			tournament = Tournament.loadTournament( file );
		}
		
		catch (IOException e )
		{
			Test.displayMessage("Error in loading file..." );
			System.exit( 0 );
			e.printStackTrace();
		}
		
		
		StringBuilder builder = new StringBuilder( 300 );
		Test.displayStandingTable(  ((GroupTournament)tournament)
				.getTable() //GroupTournament specific
				.getStringTable() );
		
		
		while ( ! tournament.hasEnded() )
		{
			Fixture[] currentFixtures;
			try
			{
				currentFixtures = tournament.getCurrentRound().getFixtures();
			}
			catch (TournamentEndedException e1)
			{
				break;
			}
			Test.displayFixtures( currentFixtures );
			
			builder.delete(0 , builder.length() );
			builder.append("Round results are: \n" );
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
				catch (NoFixtureException | TournamentEndedException | ResultCannotBeSetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					builder.append(String.format("%s %.0f VS %.0f %s\n",
							com1 , currentFixtures[ i ].getCompetitorOneScore() ,
							currentFixtures[  i ].getCompetitorTwoScore() , com2 ));
				}
				catch (IncompleteFixtureException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
				
				
			Test.displayMessage( builder.toString()  );
			
			
			try
			{
				tournament.moveToNextRound();
			}
			catch (TournamentEndedException | MoveToNextRoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Test.displayStandingTable(  ((GroupTournament)tournament)
					.getTable() //GroupTournament specific
					.getStringTable() );
			
			
		}
		Test.displayMessage( "The winner is " + tournament.getWinner() );

	}

}
