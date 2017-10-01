package com.solutioninventors.tournament.test;

import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Breaker;

public class CustomTest {
	public static void main(String[] args) throws InvalidBreakerException, TournamentException 
	{
		/*for( Breaker breaker : Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.GOAL_DEPENDENT)  )
			System.out.println( breaker.getName() );
	
		System.out.println("\nAll Group tournament only breaker that is not GOAL DEPENDENT");
		for( Breaker breaker : Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.NOT_GOAL_DEPENDENT)  ) {
			System.out.println( breaker.getName() );
			
		}*/
		Breaker[] breaker = Breaker.getBreakers(Breaker.GROUP_BREAKER, Breaker.NOT_GOAL_DEPENDENT);
		String abc[] = new String[breaker.length];
		for (int i = 0; i < breaker.length; i++) {
			abc[i] = breaker[i].toString();
		}
		
		for (int i = 0; i < abc.length; i++) {
			System.out.println(abc[i]);
		}
		
		
		
		}
	
}
