/**
 *@Author: Oguejiofor Chidiebere
 *Challenge.java
 *Aug 9, 2017
 *11:42:42 PM
 */
package com.solutioninventors.tournament.types;

import java.awt.HeadlessException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class Challenge extends Tournament 
{

	private final Round[] ROUNDS;
	
	public Challenge( Competitor[] competitors , int numOfRounds 	)
	{
		super( competitors );
		
		ROUNDS = new Round[ numOfRounds ];
		
		createTournament();
		
	}

	private void createTournament()
	{
		Fixture fixture ;
		for ( int i = 0 ; i < ROUNDS.length ; i++ )
		{
			fixture = new Fixture(getCompetitors()[ i % 2 == 0 ? 0 : 1], 
					getCompetitors()[ i % 2 == 0 ? 1 : 0 ] );
			getRoundArray()[ i ] = new Round( fixture );
		}
	}

	@Override
	public Round[] getRoundArray()
	{
		return ROUNDS ;
	}
	@Override
	public void moveToNextRound() throws  TournamentEndedException
	{
		if ( !hasEnded() && getCurrentRound().isComplete() 	)
			incrementRoundNum();
		else if ( !getCurrentRound().isComplete() )
			JOptionPane.showMessageDialog(null , "Results not yet inputed" );//replace with Javafx
		else
			JOptionPane.showMessageDialog(null, "Tournamnet has ended");//replace with Javafx
		
	}
	
	public void setResult(Competitor com1 , double score1 , double score2 , Competitor com2) 
			throws TournamentEndedException
	{
		if ( !hasEnded() )
		{
			if ( Arrays.stream( getCurrentRound().getFixtures() )
					.anyMatch( f-> f.hasFixture(com1,  com2)))
			{
				Arrays.stream( getCurrentRound().getFixtures() )
				.filter( f-> f.hasFixture(com1,  com2))
				.forEach( f->f.setResult(score1,  score2));
			}
		}
	}
	
	@Override
	public Round getCurrentRound() throws TournamentEndedException
	{
		if ( getCurrentRoundNum() < getRoundArray().length )
			return getRoundArray()[ getCurrentRoundNum() ];
		throw new TournamentEndedException();
	}

	@Override
	public boolean hasEnded()	
	{
		return  getCurrentRoundNum()   <  ROUNDS.length  ? false : true ;
		
	}

	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
		{
			Function<Competitor, Integer> function = c-> c.getNumberOfWin()  ; 
			
			
			
			Comparator<Competitor> comparator = 
					Comparator.comparing( function ).reversed()
							.thenComparing( c-> c.getGoalDifference() ).reversed() ;
			
			Competitor[] coms = new Competitor[ 2 ]; 
			
			return  Arrays.stream( getCompetitors() )
					.sorted( comparator )
					.collect(Collectors.toList())
					.toArray( coms )[ 0 ] ;
		}
		return null ;
	}
	
	@Override
	public String toString()
	{
		return "Round " + ( getCurrentRoundNum() + 1 ); 
	}

	
}
