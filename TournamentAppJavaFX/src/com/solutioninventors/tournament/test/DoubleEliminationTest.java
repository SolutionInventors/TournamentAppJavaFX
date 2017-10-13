/**
 *@Author: Oguejiofor Chidiebere
 *DoubleEliminationTest.java
 *Aug 9, 2017
 *7:30:18 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.IncompleteFixtureException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.DoubleElimination.BracketType;
import com.solutioninventors.tournament.types.knockout.EliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;
import com.sun.javafx.image.impl.ByteIndexed.Getter;

public class DoubleEliminationTest
{

	public static void main(String[] args) throws TournamentEndedException
	{
		File file = new File("Arsenal.jpg");
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		
		Competitor c5 = new Competitor( "Ada" , file );
		Competitor c6 = new Competitor( "Oguejiofor", file );
		Competitor c7 = new Competitor( "Pio" , file );
		Competitor c8 = new Competitor( "Oloche" ,  file ) ;
		
		
		
		Competitor[] comps = { 
				c1 , c2  , c3 , c4 , c5, c6, c7, c8
				}; 

		Tournament tournament = null ;
		try
		{
			tournament = new DoubleElimination( SportType.GOALS_ARE_SCORED, comps);
		}
		catch (TournamentException e)
		{
			Test.displayMessage( e.getMessage() );
			System.exit( 0 );
			
		}
		Test.displayMessage("Double Elimination begins");
		
		
		while( ! tournament.hasEnded() 	)
		{
			simulateRound(tournament);
			Test.printRounds( tournament.getRoundArray() );
		}

		Test.displayMessage("The winner is " + tournament.getWinner() );
		
		StringBuilder builder = new StringBuilder();
		builder.append("The top three players are:\n");
		
		EliminationTournament elimSpecific = 
				(EliminationTournament) tournament;
		
		Competitor[] topThree = elimSpecific.getTopThree() ;
		for ( int i =  0 ;i < topThree.length ; i++ )
			builder.append( (i + 1) + ". " + topThree[i] );
		
		Test.displayMessage( builder.toString() );
	}



	public static void simulateRound(Tournament tournament) throws TournamentEndedException
	{
		Test.displayMessage("Welcome to the " + tournament.toString() );
		
		DoubleElimination doubleSpecific = (DoubleElimination) tournament ; 
		try
		{
			if ( doubleSpecific.isTieRound() )
			{
				Test.displayMessage("Round Ties Fixtures" 	);
				Test.displayFixtures( doubleSpecific.getCurrentRound().getFixtures()  );
				
			}
			else if ( !doubleSpecific.isInitialComplete() )
			{
				Test.displayMessage("Initial Round Fixtures" 	);
				Test.displayFixtures( doubleSpecific.getCurrentRound( BracketType.INITIAL_BRACKET ).getFixtures() );
				
			}
			else if ( !doubleSpecific.isMinorFixtureComplete() && ! doubleSpecific.isFinal() )
			{
				Test.displayMessage("Winner Bracket Fixtures" 	);
				Test.displayFixtures( doubleSpecific.getCurrentRound( BracketType.WINNERS_BRACKET ).getFixtures() );
				
				Test.displayMessage("Minor Bracket Fixtures" 	);
				Test.displayFixtures( doubleSpecific.getCurrentRound( BracketType.MINOR_BRACKET ).getFixtures() );
			}
			else if( ! doubleSpecific.isFinal() )
			{
				Test.displayMessage("Major Bracket Fixtures" 	);
				Test.displayFixtures( doubleSpecific.getCurrentRound( BracketType.MAJOR_BRACKET ).getFixtures() );
				
			}
		}
		catch (RoundIndexOutOfBoundsException e1)
		{
			e1.printStackTrace();
		} 	
		
		
		inputRoundResults(tournament);
		
		Test.displayMessage("Results");
		
		Test.displayRoundResults( tournament.getCurrentRound() );
		
		try
		{
			tournament.moveToNextRound();
		}
		catch (MoveToNextRoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	public static void inputRoundResults(Tournament tournament) throws TournamentEndedException
	{
		StringBuilder builder = new StringBuilder( 400 );
		Fixture[] currentFixtures = tournament.getCurrentRound().getFixtures() ;
		
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
				if ( score1 == score2 )
					continue;
				builder.append(String.format("%s %.0f VS %.0f %s\n",
						com1 , currentFixtures[ i ].getCompetitorOneScore() ,
						currentFixtures[  i ].getCompetitorTwoScore() , com2 ));
			}
			catch (NoFixtureException e)
			{
				e.printStackTrace();
			}
			catch (ResultCannotBeSetException e)
			{
				e.printStackTrace();
			}
			catch (IncompleteFixtureException e)
			{
				e.printStackTrace();
			}
			
			
			
		}
		
	}
	
	
}
