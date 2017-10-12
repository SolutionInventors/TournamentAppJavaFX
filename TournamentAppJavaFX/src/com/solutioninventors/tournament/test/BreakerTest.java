/**
 *@Author: Oguejiofor Chidiebere
 *BreakerTest.java
 *Sep 15, 2017
 *2:16:03 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.utils.Breaker;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class BreakerTest
{

	
	public static void main(String[] args) throws InvalidBreakerException, TournamentException 
	{
//		Tests the TieBreaker and Breaker objects
		
//		You can initialize TieBreker in two ways
//		Method 1:
		
		Breaker[] breakers = 
			{Breaker.HOME_WINS, Breaker.GOALS_DIFFERENCE, Breaker.SHOOT_OUT} ;
		
		TieBreaker tieBreaker = null;
		try
		{
			tieBreaker = new TieBreaker( breakers );
		}
		catch (InvalidBreakerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
/*		Method 2 : Simply list all the tieBreakers as much as you want 
		Note that the order is important*/
		
		try
		{
			tieBreaker = new TieBreaker( Breaker.HOME_WINS, Breaker.AWAY_GOALS_DIFFERENCE,
									Breaker.AWAY_GOALS_CONCEDED	);
		}
		catch (InvalidBreakerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//In case you want to display the breaker names
		for( int i = 0 ;  i<  tieBreaker.getBreakers().length ; i++ )
			System.out.println( tieBreaker.getBreakers()[i].getName() );
		
		System.out.println("\nTo get breakers based on specifications:" );
		System.out.println("Breaker.ALL that is GOAL_DEPENDENT");
		
		for( Breaker breaker : Breaker.getBreakers(Breaker.ALL, Breaker.GOAL_DEPENDENT)  )
			System.out.println( breaker.getName() );
		
		System.out.println("\nBreaker.GROUP that is GOAL DEPENDENT");
		for( Breaker breaker : Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.GOAL_DEPENDENT)  )
			System.out.println( breaker.getName() );
		
		Test.displayMessage( "Please read this code carefully.\nThis is all you'll see\n"
				+ "You can comment this message it was actually \nmeant to annoy you (error line 74)"  );
		
		System.out.println("\nAll Group tournament only breaker that is not GOAL DEPENDENT");
		for( Breaker breaker : Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.NOT_GOAL_DEPENDENT)  )
			System.out.println( breaker.getName() );
		
		System.out.println("\nAll Group tournament only breakers irrespective of type");
		for( Breaker breaker : Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.ALL)  )
			System.out.println( breaker.getName() );
		
		System.out.println("\nBreakers that can be used for all tournaments irrespective of type");
		for( Breaker breaker : Breaker.getBreakers(Breaker.ALL, Breaker.ALL)  )
			System.out.println( breaker.getName() );
		
		
		System.out.println("\nAll Knockout tournament breakers irrespective of type");
		for( Breaker breaker : Breaker.getBreakers(Breaker.KNOCKOUT_BREAKER, Breaker.ALL)  )
			System.out.println( breaker.getName() );
		
		
		
		
		
		/* Once created you can use the TieBreaker to create Tournaments*/
		File file = new File( "Arsenal.jpg" );
		Competitor c1 = new Competitor("Chidiebere", file);
		Competitor c2 = new Competitor("Fred", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);
		Competitor[] comps = { c1, c2, c3, c4 };
		GroupTournament roubin = 
				new RoundRobinTournament(comps,
						SportType.GOALS_ARE_SCORED, 3, 1, 0, tieBreaker, false 	);
		
		/*
		 * For other info see other tests
		 */
		
		
	}

}
