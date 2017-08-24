/**
 * @author Chinedu Oguejiofor
 *23 Aug. 2017
 * 10:35:33 am
 */
package com.solutioninventors.tournament.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveTourJavaFX extends Application{
	@Override
	public void start(Stage primaryStage) throws TournamentEndedException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("FileChooser.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("FileChooser");
			
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

		File file = new File("Arsenal.jpg");
		Competitor c1 = new Competitor("Chidiebere", file);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

	
		Competitor[] comps = {c1, c2 ,c3,c4};	
		Breaker[] breakers ={Breaker.getGroupBreakers()[ 0 ], Breaker.getGroupBreakers()[1]};
		Tournament tournament = null ;
		
		int numOfRounds = Integer.parseInt(JOptionPane.showInputDialog("input the number of Round"));
		try
		{
			TieBreaker tieBreakers = new TieBreaker( breakers );
			tournament = new SwissTournament( comps, SportType.GOALS_ARE_SCORED , 
					3 , 1 , 0 , tieBreakers , numOfRounds );
		}
		catch (TournamentException | InvalidBreakerException e )
		{
			JOptionPane.showMessageDialog(null,  e.getMessage() );
			e.printStackTrace();
		}

	//	JFileChooser fileChoos = new JFileChooser();
		FileChooser fileChooser = new FileChooser();
		//Stage primaryStage = new Stage();
	//	fileChoos.showSaveDialog( null );
		File tournamentFile = fileChooser.showSaveDialog(primaryStage);
		//File tournamentFile = fileChooser.getSelectedFile();
		//	File tournamentFile = fileChooser.getSelectedExtensionFilter();
		
		tournament = saveAndRetrieveToournament(tournament, tournamentFile);
		
		
//		Tournament begins
		Test.displayMessage("Swiss begins");
		StringBuilder builder = new StringBuilder( 300 );
		
		builder.append( "The competitors are: \n" );
		Competitor[] tournamentComps = tournament.getCompetitors() ;
		
		for ( int i =  0 ; i < tournamentComps.length ; i ++ )
		{
			builder.append( (i+1) + ". " + tournamentComps[ i ] + " \n" ); 
		}
		
		
		Test.displayMessage( builder.toString() );
		Test.displayStandingTable(   ( (GroupTournament)tournament )
									  .getTable() // groupTournament specific
									  .getStringTable() );
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
				
				try
				{
					tournament.setResult( com1, score1, score2, com2);
				}
				catch (NoFixtureException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				builder.append(String.format("%s %.0f VS %.0f %s\n",
						com1 , currentFixtures[ i ].getCompetitorOneScore() ,
						currentFixtures[  i ].getCompetitorTwoScore() , com2 ));
				
				
				}
				try
				{
					tournament.moveToNextRound();
				}
				catch (MoveToNextRoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				Test.displayMessage( builder.toString()  );
				Test.displayStandingTable(  ((GroupTournament)tournament)
											.getTable() //GroupTournament specific
											.getStringTable() );
				
				tournament = saveAndRetrieveToournament(tournament, tournamentFile);
				
			}
			
			Test.displayMessage( "The winner is " + tournament.getWinner()) ;

		
	}

	
	public static Tournament saveAndRetrieveToournament(Tournament tournament, File tournamentFile)
	{
		try
		{
			Test.displayMessage("Swiss Tournament is saving...");
			Tournament.saveAs(tournament, tournamentFile);
			
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (TournamentException e)
		{
			e.printStackTrace();
		}
		
		tournament = null ; // deletes the tournament reference 
		
		tournamentFile = new File( tournamentFile.getName() + ".sit" );
		try
		{
			Test.displayMessage("Retrieving from file...... ");
			tournament = Tournament.loadTournament( tournamentFile);
		}
		catch (IOException | TournamentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tournament;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
