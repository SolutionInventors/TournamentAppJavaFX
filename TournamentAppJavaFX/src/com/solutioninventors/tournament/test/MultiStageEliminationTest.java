/**
 *@Author: Oguejiofor Chidiebere
 *MultiStageEliminationTest.java
 *Sep 16, 2017
 *5:43:43 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Multistage;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/**
 * Tests the knokout stages of the {@link Multistage} {@code Tournament}.
 * Uses {@code RoundRobin} as its default
 */
public class MultiStageEliminationTest
{
	
	public static void main(String[] args)
	{
		File file = new File("Ada .jpg");
		Competitor c1 = new Competitor("Chidiebere", file);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

		Competitor c5 = new Competitor("Ada", file);
		Competitor c6 = new Competitor("Oguejiofor", file);
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

		Competitor[] comps = { c1, c2, c3, c4 , c5 };
		//Competitor[] comps = { c1, c2, c3, c4};

		Breaker[] breakers = { Breaker.GOALS_DIFFERENCE, Breaker.GOALS_SCORED, Breaker.GOALS_CONCEDED,
				Breaker.TOTAL_WINS, Breaker.AWAY_GOALS_SCORED};

		Tournament tournament = null ;
		TieBreaker tieBreakers;
		try {
			
			tieBreakers = new TieBreaker( breakers );
			tournament = new Multistage( comps , SportType.GOALS_ARE_SCORED , 
					3, 1 ,0 , tieBreakers,false   );

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
		MultistageTest.displayGroupStanding( ( Multistage ) tournament);
		
//		
		while( !tournament.hasEnded() )//tournament is ongoing
		{
			try
			{
				MultistageTest.simulateRound((Multistage) tournament );
			}
			catch (TournamentEndedException | ResultCannotBeSetException | MoveToNextRoundException e)
			{
				e.printStackTrace();
			}
			
		}
		Test.displayMessage("The winner is " + tournament.getWinner() + " and his total goals scored is "
				+ tournament.getWinner().getGoalsScored( true ));

	}

}
