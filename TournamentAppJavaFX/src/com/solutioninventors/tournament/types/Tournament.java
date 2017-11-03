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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.FileIsOpenException;
import com.solutioninventors.tournament.exceptions.ImageFormatException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.exceptions.TournamentHasNotBeenSavedException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

/** 
 * This abstract class contains static and object level methods that make simulation of a tournament possible
 * Concrete subclasses include RounRobinTournament, SwissTournament, 
 *  Challenge, Multistage, SingeElimination and DoubleElimination. <br>
 * Some abstract subclasses including GroupTournmant and Elimination. 
 * 
 * @see com.solutioninventors.tournament.types.group.GroupTournament
 * @see com.solutioninventors.tournament.types.knockout.EliminationTournament
 * @see com.solutioninventors.tournament.types.Challenge
 * @see com.solutioninventors.tournament.types.Multistage
 * @see com.solutioninventors.tournament.types.group.SwissTournament
 * @see com.solutioninventors.tournament.types.group.RoundRobinTournament
 * @see com.solutioninventors.tournament.types.knockout.SingleEliminationTournament
 * @see com.solutioninventors.tournament.types.knockout.DoubleElimination

 */

public abstract class Tournament implements Serializable
{
	/**
	 * Stores the currently open tournaments
	 */
	private static final Set<File> openFiles = new 	HashSet<>();
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * A {@code File} that stores the absolute path of the all saved {@code Tournament}s 
	 */
	private static final File savedTournaments //stores all saved tournaments 
		= new File("saved-tournaments.dll");
	
	/**
	 * This array stores all the {@code Competitor}s in this {@code Tournament} 
	 */
	private final Competitor[] competitors;
	
	/**
	 * A {@code File} that stores this {@code Tournament}.It is updated when method {@code save} is called and 
	 * created when method {@code saveAs} is called
	 */
	private File tournamentFile; 
	
	/**
	 * Stores a {@code File} containing this {@code Tournament}'s image
	 */
	private File tournamentIcon;
	
	/**
	 * This {@code Tournament}'s {@code SportType} 
	 * @see com.solutioninventors.tournament.utils.SportType
	 */
	private final SportType SPORT_TYPE;
	
	/**
	 * Stores this {@code Tournament}'s current round number
	 * Updated by method {@code moveToNextRound}
	 */
	private int currentRoundNum;
	
	/**
	 * Stores the name of this {@code Tournament}
	 */
	private String name;	
	
	/**
	 * Creates a {@code Tournament} object with the specified {@code Sporttype} and 
	 * {@code Competitor} array
	 * This constructor always shuffles the array only if shuffle is true
	 * @author Ogujiofor Chidiebere
	 * @since v1.0
	 *@param type - the {@code SportType} with which the {@code Tournament} would be created
	 *@param coms - the {@code Competitor } array
	 * @throws TournamentException 
	 */
	public Tournament ( SportType type , Competitor ... coms ) throws TournamentException
	{
		
		this( coms,  type, false  );
	}

	
	/**
	 * Creates a {@code Tournament} object with the specified {@code SportType} and 
	 * {@code Competitor} array. This constructor shuffles the array only 
	 * if shuffle is true
	 * @author Ogujiofor Chidiebere
	 * @since v1.0
	 * @param shoffle specifies whether the {@code Competitor}s should be shuffled
	 *@param type - the {@code SportType} with which the {@code Tournament} would be created
	 *@param coms - the {@code Competitor } array
	 * @throws TournamentException 
	 */
	public Tournament ( Competitor [] coms  , SportType type , boolean shuffle) throws TournamentException
	{
		int size = coms.length;
		int duplicateTrim = 
				Arrays.stream( coms )
				.map( com -> com.getName() )
				.distinct().toArray().length;
		
		if( size  != duplicateTrim ){
			throw new TournamentException("Two competitors have the same name" );
		}
		if (  shuffle )
		{
			Collections.shuffle( Arrays.asList(coms) );
		}
		competitors = coms ;
		currentRoundNum = 0 ; 
		SPORT_TYPE = type;
		setName( "Tournament App" );
	}

	
	/**
	 * Gets all the {@code Competitor}s that participated in this {@code Tournament}
	 *@return an array of {@code Competitor}s
	 */
	public Competitor[] getCompetitors()
	{
		return competitors;
	}


	/**
	 * Gets this {@code Tournament}'s {@code SportType} 
	 * @see com.solutioninventors.tournament.utils.SportType
	 * @author Oguejiofor Chidiebere
	 *@return - a {@code SportType} object
	 */
	public SportType getSportType()
	{
		return SPORT_TYPE;
	}
	
	/**
	 * Gets this {@code Tournament}'s current round number
	 *@return - the current round number as {@code int}
	 */
	public int getCurrentRoundNum()
	{
		return currentRoundNum;
	}


	/**
	 * Increases this {@code Tournament}'s current round number by one
	 * @since v1.0
	 * @author Oguejiofor Chidiebere
	 */
	protected void incrementRoundNum()
	{
		currentRoundNum++ ;
	}
	
	/**
	 * Gets the name of this {@code Tournament}
	 *@author Oguejiofor Chidiebere
	 *@return - the name of this {@code Tournament } as {@code String}
	 */
	public String getName()
	{
		return name;
	}

	public Round[] getResults () {
		List<Round> list = Arrays.stream( getRoundArray())
							.filter( round -> round.isComplete() )
							.collect( Collectors.toList());
		return list.toArray( new Round[ list.size() ]  );	
	}
	
	public Round[] getPendingRounds(){
		List<Round> list = Arrays.stream( getRoundArray())
				.filter( round -> !round.isComplete() )
				.collect( Collectors.toList());
		return list.toArray( new Round[ list.size() ]  );	
	}
	/**
	 * 
	 *Saves the current state of this {@code Tournament} to an existing file.<p>
	 *An exception is thrown when this {@code Tournament} has not been saved with method
	 *{@code saveAs}
	 *@throws FileNotFoundException - Signals that an attempt to open the file denoted by a specified pathname has failed. 
	 *@throws IOException - Signals that an I/O exception of some sort has occurred
	 *@throws TournamentHasNotBeenSavedException - Signals that this {@code Tournament has not been saved}
	 */
	public void save() 
			throws FileNotFoundException, IOException,  TournamentHasNotBeenSavedException
	{
		String errorMessage = String.format( "%s\n%s", 
				"The tournament file might have been moved or deleted",
				"Please recheck for file or use saveAs to save the tournament");
		if ( getTournamentFile().exists() )
			try
			{
				saveAs( Tournament.this , getTournamentFile() );
			}catch (TournamentException e){}
		else
			throw new 
				TournamentHasNotBeenSavedException( errorMessage  );
	
		
	}
	
	/**
	 * Sets the {@code File } in which this {@code Tournament } is saved
	 *@param 	file - the new {@code File } object
	 */
	private  void setTournamentFile( File file )
	{
		tournamentFile = file; 
	}
	
	
	/**
	 * Gets this a {@code File } in which this {@code Tournament } is saved
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return - a {@code File} object 
	 */
	public File getTournamentFile()
	{
		return tournamentFile;
	}
	
	/**
	 * @author Oguejiofor Chidiebere 
	 * @since v1.0
	 * Sets the name of this {@code Tournament}
	 *@param name - the new name as {@code String}
	 */
	public void setName(String name)
	{
		this.name = name != null ? name : "Tournament App" 	;
	}
	
	/**
	 * Gets this an {@code File} containing an image  of this {@code Tournament}'s icon
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return - a {@code File}
	 */
	public File getTournamentIcon()
	{
		return tournamentIcon;
	}

	/**
	 * Sets this tournament icon {@code File}
	 *@param logo - a file containing this {@code Tournament}'s icon
	 *@throws ImageFormatException when the {@code File } format is invalid
	 */
	public void setTournamentIcon(File logo)
	{
		if (logo.exists() || logo.getName().indexOf(".") < 0) 
			this.tournamentIcon = logo;
		else
			throw new ImageFormatException("The URL is not a file");
			
	}

//	All static methods are declared here:
	
	/**
	 * Sets the tournament file  of the specified {@code Tournament }  object<br>
	 * Used by method {@code saveAs
	 * @author Oguejiofor Chidiebere
	 *@param tournament - the {@code Tournament} object
	 *@param file - the new {@code File} object
	 */
	private  static void setTournamentFile( Tournament tournament, File file )
	{
		tournament.setTournamentFile( file );
	}
	
	/**
	 * Saves a {@code Tournament} in a specified {@code File}
	 * @param <T> a {@code Tournament} object type. Could be any of its subclasses 
	 * @param tournament a subclass of {@code Tournament}
	 *@param file - the {@code File} where the {@code Tournament} would be saved
	 *@throws FileNotFoundException - Signals that an error occured
	 *@throws IOException - signals that an I/O  exception occured
	 *@throws com.solutioninventors.tournament.exceptions.TournamentException - when  the {@code Tournament} passed as argument is {@code null}
	 */
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
		
		setTournamentFile(tournament, file);
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

	/**
	 *Gets a {@code List} containing absolute path of all thefiles that have been saved in this tournament
	 *@return a {@code File}
	 *@throws IOException - an IO exception occurs
	 */
	@SuppressWarnings("unchecked")
	public static List<File> retrieveSavedFiles() throws IOException
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
		finally
		{
			input.close();
		}
		return tourList.stream()
				.filter( f-> f.exists() )
				.sorted(sortByDateCreated.reversed() )
				.collect(Collectors.toList() );
	}
	
	/**
	 * Adds a particular file to the app's list of saved files 
	 * 
	 *@param file - the new {@code File}
	 *@param tourList - the list where it'll be added 
	 *@throws IOException
	 */
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
			openFiles.add( file );
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IOException( "Error in writing to saved files" );
		}
		
	}

	/**
	 * 
	 * Loads a {@code Tournament} from a {@code File}
	 * @param <E> is a subclass of {@code Tournament}
	 *@param file the {@code File} that contains the {@code Tournament}
	 *@return - a {@code Tournament} object
	 *@throws IOException when an I/O  error occurs
	 * @throws FileIsOpenException when the specified file is already open
	 */
	@SuppressWarnings("unchecked")
	public static <E extends Tournament> E loadTournament( File file  ) 
			throws  IOException, FileIsOpenException	
	{
		if( openFiles.contains( file ) )
			throw new FileIsOpenException("File is already open" );

		E tournament ;
		 if ( file.exists() )
		{
			try ( ObjectInputStream  input = 
					new ObjectInputStream( new FileInputStream(file ));)
			{
				
				tournament = (E) input.readObject( );
				openFiles.add( file );
				
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

	/**
	 * Removes the file from the set of open files 
	 *@param file the {@code File} to add
	 */
	public static void closeFile( File file ){
		if( openFiles.contains( file) )
			openFiles.remove( file );
	}

	/**
	 * Gets the absolute path of all the saved {@code Tournament}s by this app
	 *@return - a {@code String} array
	 */
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

	
	/**
	 * This method moves this {@code Tournament} to its next {@code Round} by performing
	 *  some type specific operations
	 *  @author Oguejiofor Chidiebere
	 *  @since v1.0
	 *  @see com.solutioninventors.tournament.utils.Round
	 *  @see com.solutioninventors.tournament.utils.Fixture
	 *@throws com.solutioninventors.tournament.exceptions.TournamentEndedException -Signals this  {@code Tournament} is complete and 
	 *			thus cannot move to next round
	 *@throws com.solutioninventors.tournament.exceptions.MoveToNextRoundException - Signals that another type specific exception occured
	 */
	public abstract void moveToNextRound()
			throws TournamentEndedException, MoveToNextRoundException ;
	
	/**
	 * Sets the result of this {@code Tournament}'s the current {@code Round}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 * @see Round
	 * @see Fixture
	 *@param com1 - the home {@code Competitor}	
	 *@param score1 - the home {@code Competitor}'s score
	 *@param score2 - the away {@code Competitor}'s score
	 *@param com2 - the away {@code Competitor}	
	 *@throws com.solutioninventors.tournament.exceptions.NoFixtureException -  Signals that the {@code Fixture}  is not in 
	 *this {@code Tournament}'s current {@code Round}
	 *@throws TournamentEndedException -Signals that this {@code Tournament } is over
	 *@throws ResultCannotBeSetException - Signals that {@code Fixture} is in the current {@code Round} but 
	 *it already contains a result
	 */
	public abstract void setResult( Competitor com1 , 
			double score1 , double score2 , Competitor com2 ) throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException;
	
	/**
	 * Checks if this {@code Toornament } has ended 
	 *@return {@code true} when this {@code Tournament } is over
	 */
	public abstract boolean hasEnded() ;
	
	/**
	 * Gets the current {@link Round} of this {@code Tournament}
	 *@return - the current {@code Round } as {@code Round }  
	 *@throws TournamentEndedException - when this {@code Tournament } has ended
	 */
	public abstract Round getCurrentRound() throws TournamentEndedException ;
	
	/**
	 * Gets the winner of this {@code Tournament} 
	 * Returns {@code null } if this {@code Tournament } is not over
	 *Competitor
	 *@return - a {@code om.solutioninventors.tournament.utils.Competitor}
	 *@see Competitor
	 */
	public abstract Competitor getWinner();

	/**
	 * Gets an array of {@code Round} that contains all the {@code Round}s that have been 
	 * played in this {@code Tournament}
	 *@return - a {@code Round} array
	 */
	public abstract Round[] getRoundArray();
	
	
	
}
