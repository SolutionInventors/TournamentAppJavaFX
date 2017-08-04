/**
 * author: Oguejiofor Chidiebere
 * Aug 1, 2017
 * Round.java
 * 2:22:30 AM
 *
 */
package com.solutioninventors.tournament.utils;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class Round
{
	/**
	 * This class encapsulates all the action that occur in a Round
	 * Variable complete becomes true when the round has no pending fixtures
	 */
	private Fixture[] fixtures;
	
	private boolean complete;
	
	public Round( Fixture[]  fixes )
	{
		fixtures = fixes ;
	
	}
	
	
	public Fixture[] getFixtures()
	{
		return fixtures;
	}

	public void setScores( Competitor comp1 , int compOneScore ,
								int comTwoScore , Competitor com2 ) throws NoSuchElementException
	{
//		throws NoSubchElementException if the fixture is not found in the round
		Fixture temp = null ;
		
		
		temp = Arrays.stream(  getFixtures() )
				.filter( f -> f.getCompetitorOne().getName().equals( f.getCompetitorTwo().getName() ) )
				.findFirst().get();
		temp.setResult( compOneScore, comTwoScore );
		
	}

	public Round invertHomeAndAway()
	{
		Fixture[] inverseFixtures = new Fixture[ getFixtures().length ];
		
		Fixture[] currentFixture= getFixtures();
		
		for ( int i = 0 ; i < inverseFixtures.length ; i ++ )
		{
			inverseFixtures[ i ] = new Fixture( currentFixture[ i ].getCompetitorTwo() ,
												currentFixture[ i ].getCompetitorOne() 	);
		}
		
		return new Round( inverseFixtures );
		
	}
	

	public  boolean isComplete()
	{
		return complete;
	}
	
}	
