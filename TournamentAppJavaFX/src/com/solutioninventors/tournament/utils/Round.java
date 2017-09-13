/**
 * author: Oguejiofor Chidiebere
 * Aug 1, 2017
 * Round.java
 * 2:22:30 AM
 *
 */
package com.solutioninventors.tournament.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;


/**
 * A {@code Round } object is made up of several {@link Fixture}s. This object provides several
 * methods for retrieving informatiions about the collection of {@code Fixture}s 
 * <br>
 * The constructor simply takes a {@code Fixture[] } or {@code Collection<Fixture>} 
* object 
*   
*   Some common method includes {@code getLosers() }, {@code getPendingFixtures, hasDraw, isComplete} 
*   <br>
*   This object is IMMUTABLE
*   @author Oguejiofor Chidiebere 
*   @since v1.0
*   @see Fixture
*   
 */

public class Round implements Serializable
{
	private Fixture[] fixtures;
	
	/**
	 * Creates a Round object with a {@code Fixture[] } object
	 * Removes any duplicate in the arrays
	 * 
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 * @see Fixture
	 *@param fixes the {@code Fixture[] } object
	 */
	public Round( Fixture[]  fixes )
	{
		HashSet<Fixture > set = new HashSet<Fixture>( Arrays.asList( fixes )  );
		this.fixtures = set.toArray( new Fixture[ set.size() ] )  ;
		
	}
	
	/**
	 * Creates a Round object with a {@code Collection<Fixture> } object
	 * Removes any duplicate  in the {@code Collection<Fixture }
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 * @see Fixture
	 *@param fixes the {@code Collection<Fixture>} object
	 */
	
	public Round( Collection<Fixture>  fixes )
	{
		this( fixes.toArray( new Fixture[ fixes.size() ] ) );
		
	}
	
	/**
	 *Creates this object with one {@link Fixture } object
	 *@author Oguejiofor Chidiebere
	 *@param f the {@code Fixture } object
	 *@since v1.0
	 *
	 */
	public Round( Fixture f )
	{
		Fixture[] temp = {f};
		fixtures = temp; ;
	
	}
	
	/**
	 *@return All the fixtures in this object as {@code Fixture[] }
	 *@author Oguejiofor Chidiebere
	 */
	public Fixture[] getFixtures()
	{
		return fixtures;
	}

	
	
	/**
	 * This method scans through all the {@code Fixture}s in this {@code Round } and 
	 * sets the score of the {@code Fixture } that matches the home and away {@code Competitor}s <br>
	 * specified in this method argument
	 * <p>
	 * throws a NoFixtureException when such a {@code Fixture} is not found 
	 *@author Oguejiofor Chidiebere
	 *@param com1 the home {@code Competitor} object
	 *@param score1 the score of the home {@code Competitor } as {@code double}
	 *@param score2 the score of the away {@code Competitor } as {@code double}
	 *@param com2 the away {@code Competitor} object
	 *@throws NoFixtureException when the {@code Fixture } is not in this Round
	 * @throws ResultCannotBeSetException 
	 */
	
	public void  setResult( Competitor com1 , double score1 , 
					double score2 , Competitor com2 ) throws NoFixtureException, ResultCannotBeSetException
	{
		Predicate<Fixture> tester =
				f -> f.getCompetitorOne().getName().equals( com1.getName() ) &&
				f.getCompetitorTwo().getName().equals( com2.getName() ) ;
		
		if ( Arrays.stream( getFixtures() ).anyMatch( tester ) )
		{
			Arrays.stream( getFixtures() )
			.filter( tester )
			.findFirst()
			.get().setResult( score1 , score2 );
			
		}
		else
			throw new NoFixtureException( "Fixture does not exist" );
	}
	
	/**
	 This method scans through all the {@code Fixture}s in this {@code Round } and 
	 * sets the score of the {@code Fixture } that matches the home and away {@code Competitor}s <br>
	 * encapsulated in the {@code Fixture } agrgument
	 * <p>
	 * throws a NoFixtureException when such a {@code Fixture} is not found 

	* @author Oguejiofor Chidiebere 
	* @see Fixture
	* @since v1.0
	 *@param fixture the {@code Fixture } object that encapsulates the home and away {@code Competitor}'s
	 *@param score1 the home {@code Competitor}'s score
	 *@param score2 the away {@code Competitor}'s score
	 *@throws NoFixtureException when a match is not found
	 * @throws ResultCannotBeSetException 
	 */
	public void  setResult( Fixture fixture , double score1 , double score2 ) throws NoFixtureException, ResultCannotBeSetException 
	{
		setResult( fixture.getCompetitorOne() , score1, score2, fixture.getCompetitorTwo() );
	}
	
	/**
	 *This method returns a a {@code Round} with all the {@link Fixture }s in this object inverted
	 *<br> It does not change this object but reeturns a new object
	 *@author Oguejiofor Chidiebere 
	 *@see Fixture
	 *@since v1.0
	 *@return a {@code Round} with all the {@link Fixture }s in this object inverted
	 */
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
	
	/**
	 * 
	 * Gets all the pending {@code Fixture}s in this object. A  {@code Round } may contain
	 * complete and incomplete {@code Fixture}s
	 *@author Oguejiofor Chidiebere
	 *@since v1.0
	 *@return all the incomplete fixtures as a Fixture[] object
	 */
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
	
	
	/**
	 * @author Oguejiofor Chidiebere
	 *@since v1.0
	 *@return {@code true } when all the {@code Fixture}s are complete
	 */
	public  boolean isComplete()
	{
		if (Arrays.stream( getFixtures() )
				.anyMatch( f -> !f.isComplete() ) )
			return false ;
		else
			return true ;
	}
	
	
	/**
	 * Returns a {@code boolean } that is true when a {@code Fixture } whose home and
	 *away team are com1 and com2 respectively 
	 * @author Oguejiofor Chidiebere
	 * @see Fixture
	 * @see Competitor
	 * @since v1.0
	 *@param com1 The home {@code Competitor} object
	 *@param com2 The away {@code Competitor} object
	 *@return {@code true} when the this {@code Round } contains a {@code Fixture } whose home and
	 *away team is specified by the arguments com1  and com2  respectively
	 */
	public boolean hasFixture( Competitor com1 , Competitor com2 )
	{
		return Arrays.stream( getFixtures() )
				.anyMatch( f -> f.hasFixture(com1, com2 ) );

	}


	/**
	 * Gets the winnners in this {@code Round } <br>
	 * Return an empty array if there are no winners
	 *@return a collection of all the winners in this Round as a {@code Competitor[]} object
	 */
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
	
	/**
	 * Gets the Losers in this {@code Round}
	 * <br>Returns an empty array if there are no losers
	 *@return All the losers in this {@code Round }as a {@code Competitor[] } object
	 */
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
	
	/**
	 * Checks if any {@code Fixture } in this {@code Round } was drawn
	 *@return {@code true } if there is a draw else {@code false }
	 */
	public boolean hasDraw()
	{
		
			return Arrays.stream( getFixtures() )
					.anyMatch(Fixture :: isDraw);
			
	}

	
	
}	
