/**
 *@Author: Oguejiofor Chidiebere
 *SaveTournamentTest.java
 *Aug 18, 2017
 *2:13:52 PM
 */
package com.solutioninventors.tournament.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.exceptions.TournamentHasNotBeenSavedException;
import com.solutioninventors.tournament.types.Challenge;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;

public class SaveTournamentTest
{

	public static void main( String[] args 	) throws FileNotFoundException, IOException 
			
	{
		JFileChooser fileChooser =  new JFileChooser();
		fileChooser.setDialogTitle( "Open previously saved tournament file" );
		fileChooser.showOpenDialog( null );
		
		Tournament tournament = null ;
		try
		{
			tournament = Tournament.loadTournament( fileChooser.getSelectedFile() );
		}
		catch (IOException | TournamentException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try
		{
			if ( tournament instanceof SingleEliminationTournament )
				SingleEliminationTest.simulateRound( tournament );
			else if ( tournament instanceof SwissTournament )
				SwissTes.simulateRound( tournament );
			else if ( tournament instanceof RoundRobinTournament )
				RoundRobinTest.simulateRound( tournament );
			else if ( tournament instanceof Multistage )
				MultistageTest.simulateRound( tournament );
			else if ( tournament instanceof Challenge )
				ChallengeTest.simulateRound( tournament );	
				
		}
		catch( MoveToNextRoundException e 	)
		{
			Test.displayMessage(e.getMessage());
			e.printStackTrace(); 
			System.exit( 0 );
			
		}
		catch (TournamentEndedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( tournament.hasEnded() )
			Test.displayMessage("The winner is " + tournament.getWinner() );
		
		Test.displayMessage("Saving File....");
		
		try
		{
			tournament.save();
		}
		catch (TournamentException | TournamentHasNotBeenSavedException e)
		{
			Test.displayMessage("Save unsuccessful\nError Message: " + e.getMessage() );
			Test.displayMessage( "Save As...." );
			fileChooser.setDialogTitle("Save As Dialog is opening..." );
			fileChooser.showSaveDialog(null );
			
			try
			{
				Tournament.saveAs( tournament,  fileChooser.getSelectedFile() );
			}
			catch (TournamentException e2)
			{
				e2.printStackTrace();
				System.exit( 0 );
			}
			
			
		}
		Test.displayMessage("Save Successful");
		
		viewPreviouslySaveFileNames();
		
	}

	private static void viewPreviouslySaveFileNames()
	{
		System.out.println("Previously Saved files are..." );
		String[] names = Tournament.savedFilePaths();
		
		for( int i = 0 ; i < names.length ; i++ )
			System.out.println( names[ i ] );
				
	}

}