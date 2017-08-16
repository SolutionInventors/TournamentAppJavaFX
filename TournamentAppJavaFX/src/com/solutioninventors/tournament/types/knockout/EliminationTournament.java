/**
 *@Author: Oguejiofor Chidiebere
 *EliminationTournament.java
 *Aug 6, 2017
  s10:46:29 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

public abstract class EliminationTournament extends Tournament implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	
	public EliminationTournament ( Competitor[] comps ) throws TournamentException
	{
		super( comps );
		double validator = ( Math.log( comps.length ) )/
				( Math.log( 2 ) );
		if ( ! ( validator % 1 == 0.0f)  ) //the number is invalid 
			throw new TournamentException( "The number of Competitors must be a power of 2 " );


	}

	

	public Competitor[] getActiveCompetitors()
	{
		List< Competitor > list = new ArrayList<>();
		
		list =  Arrays.stream( getCompetitors() ) 
						.filter( c -> ! c.isEliminated() )
						.collect( Collectors.toList() ) ;
		
		
		return list.toArray( new Competitor[ list.size() ] ) ;
		
	}
	
	public Competitor[] getEliminatedCompetitors()
	{
		List< Competitor > list = new ArrayList<>();
		
		list =  Arrays.stream( getCompetitors() ) 
						.filter( c -> c.isEliminated() )
						.collect( Collectors.toList() ) ;
		
		Competitor[] temp = new Competitor[ list.size() ];
		list.toArray( temp );
		
		return temp ;
	
	}

	

	public Round getRound(int i)
	{
		return getRoundArray()[ i ];
	}
	
	
}
