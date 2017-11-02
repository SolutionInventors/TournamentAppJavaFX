/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTest.java
 *Aug 5, 2017
 *8:07:58 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.NoOutstandingException;
import com.solutioninventors.tournament.exceptions.OnlyOutstandingAreLeftException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class RoundRobinTest {

	public static void main(String[] args) throws NoFixtureException, IncompleteFixtureException {
		File file = new File("Arsenal.jpg");
				
		// File file = new File("golf.jpg");
		Competitor c1 = new Competitor("Chidiebee", file);
		Competitor c2 = new Competitor("Chidiebere", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

		

		Competitor[] comps = { c1, c2, c3, c4 };
		
		Test.displayMessage("Robin begins");
		
		
		Breaker[] breakers = {
				 Breaker.HEAD_TO_HEAD_TOTAL_GOALS			 
		};
		
		
		String ans = JOptionPane.showInputDialog( "Input 1 for double-robin\nElse Single robin" );
		Tournament tournament = null ;
		try
		{
			TieBreaker tieBreakers = new TieBreaker( breakers );
			tournament = new RoundRobinTournament( comps, SportType.GOALS_ARE_SCORED , 
					3 , 1 , 0 , tieBreakers  , ans.equals("1" ) ? true : false  );
		}
		catch (InvalidBreakerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TournamentException e) {
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
		
		printRounds( tournament.getRoundArray() );
		Test.displayStandingTable(   
				( (GroupTournament ) tournament)
				.getTable().getStringTable() );// groupTournament specific
		
		while( !tournament.hasEnded() )//tournament is ongoing
		{
			try
			{
				System.out.println( "Pending Rounds: " ); 
				printRounds( tournament.getPendingRounds() ) ;
				
				simulateRound(tournament );
				System.out.println( "Tournament Results: " ); 
				printRounds( tournament.getRoundArray() ) ;
			}
			catch (OnlyOutstandingAreLeftException e)
			{
				Test.displayMessage( "Only outstandings are left: ");
				simulateOutstanding( (RoundRobinTournament) tournament );
				
			}
			catch (TournamentEndedException e)
			{
				e.printStackTrace(); 
				break;
			}
			
		} 
		Test.displayMessage( "The winner is " + tournament.getWinner() ) ;
		
	}

	public static void simulateOutstanding(RoundRobinTournament tournament) throws IncompleteFixtureException 
	{
		StringBuilder builder = new StringBuilder();
		Fixture[] outstandings = null;
		try
		{
			outstandings = tournament.getOutstanding();
		}
		catch (NoOutstandingException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		Test.displayMessage("The outstanding Fixtures are: " );
		Test.displayFixtures( outstandings );
		builder.delete(0 , builder.length() );
		builder.append("Outstanding results are: \n" );
		for( int i = 0 ; i< outstandings.length ;i++ )
		{
			Competitor com1 = outstandings[i].getCompetitorOne() ;
			Competitor com2 = outstandings[i].getCompetitorTwo() ;
			
			double score1 = Double.parseDouble(
					JOptionPane.showInputDialog( "Input score for " + com1 ));
			double score2 = Double.parseDouble(
					JOptionPane.showInputDialog( "Input score for " + com2 ));
			
			try
			{
				tournament.setOutstandingResult(com1, score1, score2, com2);
			}
			catch (NoFixtureException e)
			{
				Test.displayMessage(e.getMessage() );
			}
			builder.append(String.format("%s %.0f VS %.0f %s\n",
					com1 , outstandings[ i ].getCompetitorOneScore() ,
					outstandings[  i ].getCompetitorTwoScore() , com2 ));	
		}
		Test.displayMessage( builder.toString()  );
		
		Test.displayStandingTable(   
				( (GroupTournament ) tournament)
				.getTable().getStringTable() );
		
		
	}

	public static void simulateRound(Tournament tournament )
			throws TournamentEndedException, OnlyOutstandingAreLeftException, IncompleteFixtureException
	{
		StringBuilder builder = new StringBuilder();
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures() ;
		Test.displayFixtures( currentFixtures );
		
		builder.delete(0 , builder.length() );
		builder.append("Roound results are: \n" );
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
			} catch (ResultCannotBeSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		
		Test.displayStandingTable(   
				( (GroupTournament ) tournament)
				.getTable().getStringTable() );// groupTournament specific
	}

	private static void printRounds(Round[] rounds)
	{
		StringBuilder builder =  new StringBuilder( 200 );
		
		for ( int i = 0 ; i < rounds.length ; i ++ 	)
		{
			builder.append( (rounds[i] + ":\n" ) );
			builder.append(  ( Test.getFixutures( rounds[ i ].getFixtures() ) 	) + "\n") ;
			
		}
		
		Test.displayMessage( builder.toString() );
		
	}

}
