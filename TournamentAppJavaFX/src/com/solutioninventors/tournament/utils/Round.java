/**
 * author: Oguejiofor Chidiebere
 * Aug 1, 2017
 * Round.java
 * 2:22:30 AM
 *
 */
package com.solutioninventors.tournament.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Round
{
	/**
	 * This class encapsulates all the action that occur in a Round
	 * Variable complete becomes true when the round has no pending fixtures
	 */
	private Fixture[] fixtures;
	private List<Fixture> drawList;
	
	public Round( Fixture[]  fixes )
	{
		fixtures = fixes ;
		
	}
	
	public Round( Fixture f )
	{
		Fixture[] temp = {f};
		fixtures = temp; ;
	
	}
	
	
	public Fixture[] getFixtures()
	{
		return fixtures;
	}

//	public void setScores( Competitor comp1 , double compOneScore ,
//								double comTwoScore , Competitor com2 ) throws NoSuchElementException
//	{
////		throws NoSubchElementException if the fixture is not found in the round
//		Fixture temp = null ;
//		
//		
//		temp = Arrays.stream(  getFixtures() )
//				.filter( f -> f.getCompetitorOne().getName().equals( f.getCompetitorTwo().getName() ) )
//				.findFirst().get();
//		temp.setResult( compOneScore, comTwoScore );
//		
//	}

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
	
	public Fixture[] getPendingFixtures()
	{
		if ( isComplete() )
			return null ;
		List< Fixture > fixtures =  new ArrayList<> ();
		
		Arrays.stream( getFixtures() )
			.filter( f -> !f.isComplete() )
			.forEach( f -> fixtures.add( f ) );
		
		Fixture[] fix = new Fixture[ fixtures.size() ];
		
		return  fixtures.toArray( fix ) ;
	}
	
	
	public  boolean isComplete()
	{
		if (Arrays.stream( getFixtures() )
				.anyMatch( f -> !f.isComplete() ) )
			return false ;
		else
			return true ;
	}
	
	
	public boolean hasFixture( Competitor com1 , Competitor com2 )
	{
		return Arrays.stream( getFixtures() )
				.anyMatch( f -> f.hasFixture(com1, com2 ) );

	}


	public Competitor[] getWinners()
	{
		ArrayList< Competitor > winners = new ArrayList<>() ;
		Fixture[] temp = getFixtures() ;
		
		for (Fixture fixture : temp)
		{
			if ( fixture.hasWinner() )
				winners.add( fixture.getWinner() );
				
		}
		if ( winners.size() == 0  ) 
			return null ;
		return winners.toArray( new Competitor[ winners.size() ]) ;
			
	}
	
	public Competitor[] getLosers()
	{
		ArrayList< Competitor > losers = new ArrayList<>() ;
		Fixture[] temp = getFixtures() ;
		
		for (Fixture fixture : temp)
		{
			if ( fixture.hasLoser() )
				losers.add( fixture.getLoser() );
				
		}
		if ( losers.size() == 0  ) 
			return null ;
		return losers.toArray( new Competitor[ losers.size() ]) ;
			
	}
	
	public boolean hasDraw()
	{
		
			return Arrays.stream( getFixtures() )
					.anyMatch(Fixture :: isDraw);
			
	}

	
	
}	
