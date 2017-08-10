/**
 * author: Oguejiofor Chidiebere
 * Aug 3, 2017
 * SwissTournament.java
 * 9:15:00 PM
 *
 */
package com.solutioninventors.tournament.group;

import java.util.Arrays;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class SwissTournament extends GroupTournament
{
	/**
	 * This class is used to simulate a swiss tournament. 
	 * Its constructor takes a competitor array , SportType , pointing system and totalROunds
	 * Utility method createTournament creates the tournament rounds
	 * 
	 * Then it takes results via calls to setResult
	 * Method moveToNextRound moves to the next round 
	 * 
	 * The class also contains some utility methods that aid its use
	 * 
	 * @throws InvalidBreakerException 
	 
	 */
	public SwissTournament( Competitor[] comps, SportType type,
							double winPoint , double drawPoint , double lossPoint,
							 TieBreaker breaker , int totalRounds  
						  ) 
							throws TournamentException, InvalidBreakerException
	{
		super( comps , type , winPoint , drawPoint , lossPoint, breaker );
		if ( comps.length % 2 != 0 )
			throw new TournamentException("Total competitors must be a multiple of 2" ) ;
	
		setRoundsArray( new Round[ totalRounds ] );
		createTournament();
	}

	private void createTournament()
	{
		createCurrentRound();
	}
	

	@Override
	public void moveToNextRound() 
	{
		getTable().updateTables(); 
		
		setCurrentRoundNum( getCurrentRoundNum() + 1 );
		
		if ( getCurrentRoundNum() <  getTotalNumberOfRounds() )
		{
			createCurrentRound();
		}
		else
			JOptionPane.showMessageDialog( null , "Tournament has ended" );
		
	}

	private void createCurrentRound()
	{
		Competitor[] temp = getTable().getCompetitors() ;
		Fixture[] fixtures = new 
				Fixture[ temp.length / 2 ];
		
		
		for ( int i = 0 ; i < temp.length ; i +=2 )
		{
			fixtures[ i/2 ] = new Fixture( temp[i], temp[ i + 1] );
			
		}
		
		 setCurrentRound( fixtures );
	}
	
	
	
	@Override
	public void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 )
	{
		Predicate<Fixture> tester = f-> f.getCompetitorOne().getName().equals(com1.getName() )
										&& f.getCompetitorTwo().getName().equals(com2.getName() );
		
		if ( Arrays.stream( getCurrentRound().getFixtures() )
				.anyMatch( tester ) )
			Arrays.stream( getCurrentRound().getFixtures() )
			.filter( f -> f.hasFixture( com1 , com2  ))
			.forEach( f-> f.setResult(score1, score2) );
		
	}

	@Override
	public boolean hasEnded()
	{
		return getCurrentRoundNum() < getRoundsArray().length ? false : true ;
		
	}

	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
		{
			getTable().updateTables();
			return getTable().getCompetitors()[0 ];
		}
			
		return null ;
		
	}
	
}
