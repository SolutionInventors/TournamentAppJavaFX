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

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

/** 
 * This class is the super class of all the classes used to create a
 * knockout tournament. Information about knockout tournament can be found online<br>
 * This class contains methods that are common to all types of knockout tournaments
 * 
 */
public abstract class EliminationTournament extends Tournament implements Serializable
{
	/**
	 * Stores the active ties in this {@code Tournament}
	 */
	private List<Fixture> activeTies;
	
	/**
	 * True when current fixtures need to be broken
	 */
	private boolean tieRound;
	
	
	/**
	 * Creates an {@code EliminationTournament}. This constructor ensures that the 
	 * number of competitors is a power of 2. That is<br>
	 * {@code number of competitors = 2}<sup>x</sup> <br>
	 * where x is greater than 1<br>
	 * 
	 *@param type the {@code SportType } of this {@code Tournament}
	 *@param comps the {@link Competitor}s 
	 *@throws TournamentException when the total competitors is invalid
	 */
	public EliminationTournament ( SportType type, Competitor[] comps, boolean shuffle ) throws TournamentException
	{
		super( comps, type,  shuffle );
		double validator = ( Math.log( comps.length ) )/
				( Math.log( 2 ) );
		if ( ! ( validator % 1 == 0.0f)  ) //the number is invalid 
			throw new TournamentException( "The number of Competitors must be a power of 2 " );
		activeTies = new ArrayList<>();

	}

	public abstract Competitor[] getTopThree();
	
	public  boolean hasTie(){
		return getActiveTieList().size() == 0 ? false : true ;	
	}
	
	/** 
	 * Gets the active {@link Competitor}s in this {@code EliminationTournament}.
	 * It scans through a collection of {@code Competitor}s and returns only the ones
	 * that have not been eliminated 
	 * 
	 *@return an array of {@code Competitor}s 
	 */
	public Competitor[] getActiveCompetitors()
	{
		List< Competitor > list = new ArrayList<>();
		
		list =  Arrays.stream( getCompetitors() ) 
						.filter( c -> ! c.isEliminated() )
						.collect( Collectors.toList() ) ;
		
		
		return list.toArray( new Competitor[ list.size() ] ) ;
		
	}
	
	/**
	 * Gets the competitors that have been eliminated in this {@code Tournaments}
	 *@return a {@code Competitor[]) object
	 *@see Competitor
	 *@since v1.0
	 *@author Oguejiofor Chidiebere 
	 */
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

	
	public List<Fixture> getActiveTieList()
	{
		return activeTies;
	}
	
	/**
	 * Gets a round specified by a round number
	 *Round
	 *@param i
	 *@return
	 */
	public Round getRound(int i)
	{
		return getRoundArray()[ i ];
	}
	
	public boolean isTieRound()
	{
		return tieRound;
	}

	protected void setTieRound(boolean tieRound)
	{
		this.tieRound = tieRound;
	}
	
	
	
}
