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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.exceptions.TournamentHasNotBeenSavedException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Round;

/** 
 * This class contains static and object level methods that make simulation of a tournament possible
 * This class is abstract thus you can instantiate only its concrete subclasses including
 *  RounRobinTournament, SwissTournament, Challenge, Multistage, SingeElimination and DoubleElimination
 *  It also has some abstract subclasses including GroupTournmant and Elimination
 *  
 *  This class generally contains simple methods that perform general tasks
 *  required when simulating a tournament
 */

public abstract class Tournament implements Serializable
{
	
	private static final File savedTournaments //stores all saved tournaments 
		= new File("saved-tournaments.dll");
	
	private final Competitor[] competitors;
	private File tournamentFile; //object keeps record of its owns file
	private File tournamentIcon;
	
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
	
	public String getName()
	{
		return name;
	}

	public void save() 
			throws FileNotFoundException, IOException, TournamentException, TournamentHasNotBeenSavedException
	{
		String errorMessage = String.format( "%s\n%s", 
				"The tournament file might have been moved or deleted",
				"Please recheck for file or use saveAs to save the tournament");
		if ( getTournamentFile().exists() )
			saveAs( Tournament.this , getTournamentFile() );
		else
			throw new 
				TournamentHasNotBeenSavedException( errorMessage  );
	}
	protected  void setTournamentFile( File file )
	{
		tournamentFile = file; 
	}
	
	public File getTournamentFile()
	{
		return tournamentFile;
	}
	public void setName(String name)
	{
		this.name = name != null ? name : "Tournament App" 	;
	}
	public File getTournamentIcon()
	{
		return tournamentIcon;
	}

	public void setTournamentIcon(File tournamentIcon)
	{
		this.tournamentIcon = tournamentIcon;
	}

	
//	all the abract methods are:
	public abstract void moveToNextRound()
			throws TournamentEndedException, MoveToNextRoundException ;
	public abstract void setResult( Competitor com1 , 
			double score1 , double score2 , Competitor com2 ) throws NoFixtureException, TournamentEndedException;
	public abstract boolean hasEnded() ;
	public abstract Round getCurrentRound() throws TournamentEndedException ;
	public abstract Competitor getWinner();

	public abstract Round[] getRoundArray();
	@Override
	public abstract String toString();


//	All static methods are declared here:
	
	public static <T extends Tournament> 
		void saveAs( T tournament   , File file ) 
			throws FileNotFoundException , IOException, TournamentException
			
	{
		if ( tournament == null )
			throw new TournamentException( "The tournament object is null" );
		
		ObjectOutputStream output  = null ;
		
		
		String path = file.getAbsolutePath() ;	
		if ( !path.endsWith(".sit" ))
			path = path + ".sit";
		file = new File( path );
		try
		{
			output  = 
					new ObjectOutputStream( new FileOutputStream(file ));
		}
		catch (IOException e)
		{
			throw new IOException( "Error in writing to file" );
		}
		
		tournament.setTournamentFile( file );
		output.writeObject( tournament );
		
		output.close();
		
		if ( savedTournaments.exists() )
		{
			List<File> tourList = retrieveSavedFiles()
									.stream()
									.distinct()
									.collect(Collectors.toList()); 
			
			final String temp = path;
			Predicate<File > predicate = f-> ! f.getAbsolutePath().equals( temp ) ;
				
			tourList= tourList.stream() //ensures that a file is not repeated in saved list
						.filter( (predicate) )
						.collect(Collectors.toList() );
	
				
					
			writeSavedList( file , tourList );
		}
		else
		{
			writeSavedList( file , new LinkedList<File>() ) ;
		}
		
		
	}

	public static List<File> retrieveSavedFiles() throws IOException, FileNotFoundException
	{
		ObjectInputStream input = new 
				ObjectInputStream( new FileInputStream( savedTournaments));
		List<File> tourList = null;
		
		Comparator<File> sortByDateCreated = 
				Comparator.comparing(  f -> f.lastModified() );
		
		try
		{
			tourList  = (List<File> ) input.readObject();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return tourList.stream()
				.filter( f-> f.exists() )
				.sorted(sortByDateCreated.reversed() )
				.collect(Collectors.toList() );
	}
	
	
	private static void writeSavedList(File file , List<File> tourList) throws IOException
	{
		tourList.add( file );
		ObjectOutputStream output = null ;
		try
		{
			output = new 
					ObjectOutputStream( new FileOutputStream(savedTournaments ) );
			output.writeObject( tourList );
			output.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException( "Error in writing to saved files" );
		}
		
	}

	public static <T extends Tournament> T loadTournament( File file  ) 
			throws FileNotFoundException , IOException, TournamentException
			
	{
		T tournament ;
		 if ( file.exists() )
		{
			ObjectInputStream input  = null ;
			try
			{
				input  = 
						new ObjectInputStream( new FileInputStream(file ));
				tournament = (T) input.readObject( );
				input.close();
				
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				throw new IOException( "Error in reading the file" );
			}
			
		}
		else  
			throw new FileNotFoundException("The file was not found" ) ;
		 
		 return tournament;
	}


	public static String[] savedFilePaths()
	{
		List<File> fileList = null ;
		try
		{
			fileList = retrieveSavedFiles();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		List<String> nameList = fileList.stream()
									.map( f-> f.getAbsolutePath() )
									.collect(Collectors.toList() );
		
		return nameList.toArray( new String[ nameList.size() ] );
			
	}
	
}
