/**
 *@Author: Oguejiofor Chidiebere
 *MultistageTest.java
 *Aug 12, 2017
 *9:11:33 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.GroupIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class MultistageTest {

	public static void main(String[] args) throws TournamentEndedException {
		File file = new File("Arsenal.jpg");
		File golfFile =  new File("golf.jpg" );
		Competitor c1 = new Competitor("Chidiebere", golfFile);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", golfFile);

		Competitor c5 = new Competitor("Ada", file);
		Competitor c6 = new Competitor("Oguejiofor", golfFile);
		Competitor c7 = new Competitor("Pio", file);
		Competitor c8 = new Competitor("Oloche", file);
		//
		Competitor c9 = new Competitor("Manchester", "United", file);
		Competitor c10 = new Competitor("Chealsea", file);
		Competitor c11 = new Competitor("Arsenal", file);
		Competitor c12 = new Competitor("Real", file);
		//
		// Competitor c13 = new Competitor( "Barca" , file );
		// Competitor c14 = new Competitor( "Atletico", file );
		// Competitor c15 = new Competitor( "Lagos" , file );
		// Competitor c16 = new Competitor( "NIgeria" , file ) ;

		//Competitor[] comps = { c1, c2, c3, c4 , c5, c6, c7, c8, c9, c10, c11, c12 };
		Competitor[] comps = { c1, c2, c3, c4};

		Breaker[] breakers = { Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, Breaker.GOALS_CONCEDED,
				Breaker.NUMBER_OF_WINS, Breaker.AWAY_GOAL};

		

		
		Tournament tournament = null ;
		TieBreaker tieBreakers;
		try {
			tieBreakers = new TieBreaker(breakers);
			int type = Integer.parseInt(JOptionPane.showInputDialog("Type 1 for swiss group stage or \n"
					+ "Type 2 for a double round robin( home and away group stage\n"
					+ "Type any other number for round robin group stage"));
			if (type == 1) {
				int numOfRounds = Integer.parseInt(JOptionPane.showInputDialog("Input number of rounds: "));
				tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, 3, 1, 0, tieBreakers, numOfRounds,
						false);
			} else if (type == 2) {
				tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, 3, 1, 0, tieBreakers, true, false);
			} else {

				tournament = new Multistage(comps, SportType.GOALS_ARE_SCORED, 3, 1, 0, tieBreakers, false, false);
			}

		} catch (InvalidBreakerException | TournamentException e) {
			e.printStackTrace();
			System.exit(0);
		}

		Test.displayMessage("MultiStage begins");

		
		StringBuilder builder = new StringBuilder( 300 );
		
		builder.append( "The competitors are: \n" );
		Competitor[] tournamentComps = tournament.getCompetitors() ;
		
		for ( int i =  0 ; i < tournamentComps.length ; i ++ )
		{
			builder.append( (i+1) + ". " + tournamentComps[ i ] + " \n" ); 
		}
		
		
		Test.displayMessage( builder.toString() );
		displayGroupStanding( ( Multistage ) tournament);
		
//		
		while( !tournament.hasEnded() )//tournament is ongoing
		{
			simulateRound(tournament );
			
		}
		Test.displayMessage("The winner is " + tournament.getWinner() + " and his total goals scored is "
				+ (int) tournament.getWinner().getGoalsScored());
	}

	public static void simulateRound(Tournament tournament ) throws TournamentEndedException
	{
		StringBuilder builder = new StringBuilder( 300 );
		Test.displayMessage( "Welcome to " + tournament );
		Fixture[] currentFixtures = 
				tournament.getCurrentRound().getPendingFixtures() ;
		Test.displayFixtures( currentFixtures );
		
		builder.delete(0 , builder.length() );
		builder.append("Round results are: \n" );
		
		for( int i = 0 ; i< currentFixtures.length ;i++ )
		{
			Competitor com1 = currentFixtures[i].getCompetitorOne() ;
			Competitor com2 = currentFixtures[i].getCompetitorTwo() ;

			double score1 = Double.parseDouble(
					JOptionPane.showInputDialog( "Input score for " + com1 ));
			double score2 = Double.parseDouble(
					JOptionPane.showInputDialog( "Input score for " + com2 ));
			
			try
			{
				tournament.setResult( com1, score1, score2, com2);
			}
			catch (NoFixtureException e)
			{
				Test.displayMessage(e.getMessage() );
			}
			builder.append(String.format("%s %.0f VS %.0f %s\n",
					com1 , currentFixtures[ i ].getCompetitorOneScore() ,
					currentFixtures[  i ].getCompetitorTwoScore() , com2 ));	
		}
		
		Test.displayMessage( builder.toString()  );
		try
		{
			tournament.moveToNextRound();
			
			
		}
		catch (MoveToNextRoundException e)
		{
			e.printStackTrace();
		} 
		
		
		Multistage multiStageSpecific = ( Multistage )  tournament;
				
		if (  tournament.getCurrentRoundNum() <=
				multiStageSpecific.getNumberOfGroupRounds()
				) 
		{
			displayGroupStanding( multiStageSpecific );
			if (multiStageSpecific.getNumberOfExtraQualifiers() != 0) 
			{
			String position = 
					multiStageSpecific.getNumberOfGroups() == 3 ? "3rd" : "4th";
		
			Test.displayMessage(
					String.format("The %s place ranking able is shown ", position) );
			Test.displayStandingTable( multiStageSpecific.getPossibleQualifierTable()
										.getStringTable());
			}
		}
	}

	public static void displayGroupStanding(Multistage tournament) {
		Test.displayMessage("Tournament Groups are ");
		for (int i = 0; i < tournament.getNumberOfGroups(); i++) {
			try {
				Test.displayStandingTable(tournament.getGroup(1).getTable().getStringTable());
			} catch (GroupIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}

}
