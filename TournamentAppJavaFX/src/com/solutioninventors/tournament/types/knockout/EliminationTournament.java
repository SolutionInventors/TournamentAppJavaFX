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
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.types.Tournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

public abstract class EliminationTournament extends Tournament implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private int roundNum; 
	
	public EliminationTournament ( Competitor[] comps )
	{
		super( comps );
		setCurrentRoundNum(0) ;
	}

	

	public int getCurrentRoundNum()
	{
		return roundNum;
	}

	protected void setCurrentRoundNum(int roundNumber)
	{
		this.roundNum = roundNumber;
	}
	
	
	public Competitor[] getActiveCompetitors()
	{
		List< Competitor > list = new ArrayList<>();
		
		list =  Arrays.stream( getCompetitors() ) 
						.filter( c -> ! c.isEliminated() )
						.collect( Collectors.toList() ) ;
		
		Competitor[] temp = new Competitor[ list.size() ];
		list.toArray( temp );
		
		return temp ;
	
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

	
	public abstract void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 );
	public abstract Round getCurrentRound();



	public Round getRound(int i)
	{
		return getRoundArray()[ i ];
	}
	
	
}