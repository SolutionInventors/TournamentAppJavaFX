/**
 * July 27, 2017
 * @author Chidiebere
 * Tournament.java
 * 8:07:23 AM
 */

package com.solutioninventors.tournament.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

public abstract class Tournament implements Serializable
{
	
	private final Competitor[] competitors;
	
	private int currentRoundNum;
	private String name;	
	
	public Tournament ( Competitor[] coms )
	{
		competitors = coms ;
		currentRoundNum = 0 ; 
		
		setName( "Tournament App" );
	}


	public Competitor[] getCompetitors()
	{
		return competitors;
	}


	public int getCurrentRoundNum()
	{
		return currentRoundNum;
	}


	public void incrementRoundNum()
	{
		currentRoundNum++ ;
	}
	
	public abstract void moveToNextRound()
			throws TournamentEndedException, MoveToNextRoundException ;
	public abstract void setResult( Competitor com1 , 
			double score1 , double score2 , Competitor com2 ) throws NoFixtureException;
	public abstract boolean hasEnded() ;
	public abstract Round getCurrentRound() throws TournamentEndedException;
	public abstract Competitor getWinner();

	public abstract Round[] getRoundArray();
	@Override
	public abstract String toString();


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name != null ? name : "Tournament App" 	;
	}
	
	public static void saveTournament( Tournament tournament, File file ) 
			throws FileNotFoundException , IOException, TournamentException
			
	{
		
		if ( tournament == null )
			throw new TournamentException( "The tournament object is null" );
		
		ObjectOutputStream output  = null ;
		file = new File( file.getName() + ".sit" );
		try
		{
			output  = 
					new ObjectOutputStream( new FileOutputStream(file ));
		}
		catch (IOException e)
		{
			throw new IOException( "Error in writing to file" );
		}
		
		output.writeObject( tournament );
		output.close();
	
		
	}
	
	
	public static Tournament loadTournament( File file  ) 
			throws FileNotFoundException , IOException, TournamentException
			
	{
		Tournament tournament ;
		 if ( file.exists() )
		{
			ObjectInputStream input  = null ;
			try
			{
				input  = 
						new ObjectInputStream( new FileInputStream(file ));
				tournament = (Tournament) input.readObject( );
				input.close();
				
			}
			catch (IOException | ClassNotFoundException e)
			{
				throw new IOException( "Error in reading the file" );
			}
			
		}
		else  
			throw new FileNotFoundException("The file was not found" ) ;
		 
		 return tournament;
	}
	
	
}
