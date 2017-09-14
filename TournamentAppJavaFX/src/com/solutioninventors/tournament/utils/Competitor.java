/**
 * author: Oguejiofor Chidiebere
 * Jul 31, 2017
 * Competitor.java
 * 9:32:17 PM
 *
 */
package com.solutioninventors.tournament.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.solutioninventors.tournament.exceptions.ImageFormatException;
import com.solutioninventors.tournament.exceptions.NoCompetitorNameException;


/**
 * A {@code Competitor } object encapsulates informations about each Competitor in a Tournament<br>
 * {@code Competitor} objects contains the following properties:
 * <table border = "1.0" width = " 730.0">
 * 		<caption>Com</caption>
 * 		<tr>
 * 			<td>{@code private final String FIRST_NAME}</td> 
 * 			<td>Contains the first name of the competitor <br>
 * 				Can be retrieved via call to method {@code getFirstName }</td> 
 * 		</tr>
		<tr>
		 	<td>{@code private final String LAST_NAME}</td> 
			<td>Contains the last name of the competitor <br>
		 		Can be retrieved via call to method {@code getLastName }</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfWin}</td> 
			<td>Stores the total number of {@code Fixture}s this Competitor has won <br>
		 		Can be retrieved via call to method {@code getNumberOfWin }</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfLoss}</td> 
			<td>Stores the total number of {@code Fixture}s this Competitor has lost <br>
		 		Can be retrieved via call to method {@code getNumberOfLoss}</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfDraw} </td> 
			<td>Stores the total number of {@code Fixture}s this Competitor has drawn <br>
		 		Can be retrieved via call to method {@code getNumberOfDraw }</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfAwayWin} </td> 
			<td>Stores the total number of away {@code Fixture}s this Competitor has won <br>
		 		Can be retrieved via call to method {@code getNumberOfWin }</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfAwayWin} </td> 
			<td>Stores the total number of away {@code Fixture}s this Competitor has won <br>
		 		Can be retrieved via call to method {@code getNumberOfWin }<br>
		 		It is used by {@code Breaker.AWAY_WIN} object when breaking ties</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfHomeWin} </td> 
			<td>Stores the total number of away {@code Fixture}s this Competitor has won <br>
		 		Can be retrieved via call to method {@code getNumberOfWin }<br>
		 		It is used by {@code Breaker.NUMBER_OF_HOME_WIN} object when breaking ties</td> 
		</tr>
		<tr>
		 	<td>{@code private int numberOfAwayWin} </td> 
			<td>Stores the total number of away {@code Fixture}s this Competitor has won <br>
		 		Can be retrieved via call to method {@code getNumberOfAwayWin }<br>
		 		</td> 
		</tr>
		<tr>
		 	<td>{@code private double goalsScored} </td> 
			<td>Stores the total number of goals scored by this  Competitor<br>
		 		Can be retrieved via call to method {@code getGoalsConceeded }<br>
		 		</td> 
		</tr>
		<tr>
		 	<td>{@code private double goalsConceeded} </td> 
			<td>Stores the total number of goals scored by this  Competitor<br>
		 		Can be retrieved via call to method {@code getNumberOfGoalsConceeded }<br>
		 		It is used by {@code Breaker.GOALS_CONEEDED} object when breaking ties</td> 
		
		</tr>
		<tr>
		 	<td>{@code private Map<Competitor, Double> awayGoals} </td> 
			<td>Stores the away goals scored by this Competitor against an opponent<br>
				This property is incremented via {@code addAwayGoal(Competitor) }
				Can retrieved via call to {@code getAwayGoal(Competitor) } </td> 
		
		</tr>
		<tr>
		 	<td>{@code private Map<Competitor, Double> homeGoals} </td> 
			<td>Stores the home goals scored by this Competitor against an opponent<br>
				 </td> 
		
		</tr>
		<tr>
		 	<td>{@code private File image} </td> 
			<td>Stores this Competitor's logo/picture<br>
				It <b>MUST</b> inputed in this object's constructor
				Can retrieved via call to {@code getAwayGoal(Competitor) } </td> 
		</tr>
		<tr>
		 	<td>{@code private boolean eliminated} </td> 
			<td>Stores {@code true} when Competitor has been eliminated from the {@code Tornament} else {@code false}<br>
				Can retrieved via call to {@code isEliminated()} </td> 
		</tr>
		
		
	</table>	
		
 *
 * 
 * <p>
 * A Competitor contains some protected methods because  a {@code Competitor } can only be mutated by a {@code Fixture } object<br>
 * Some common manipulations are: <br>
 * 
 * 		{@code Competitor John = new Competitor( "John", "Oloche" , new File( "john.jpg") ) ; } <br>
 * 		
 * 		{@code Competitor Fred = new Competitor( "Fred", "Williams", new File( "fred.jpg") ) ; } <br> 
 * 		
 * 		{@code  Fixture fixture = new Fixture( John, Fred ) );}  <br>
 * 		{@code fixture.setResult( 2 ,3 ) }; <br>
 * 		{@code System.out.println( "John's goals = " + John.getGoalsScored() } <br>
 * 
 * Prints:<br> {@code John's goals = 2 }
 * 
 * @author Oguejiofor Chidiebere 
 * 
 * @see Fixture  
   @see Round  
   @see Breaker
 * @since  v1.0
 * 
 */

public class Competitor implements Serializable
{

	private final String FIRST_NAME;
	private final String LAST_NAME;

	private int homeWin;
	private int awayWin;
	
	private int homeDraw;
	private int awayDraw;
	
	private int homeLoss;
	private int awayLoss;
	
	
	private double goalsScored;
	private double goalsConceded;

	private Map< Competitor , Double > headToHead ;
	
	private Map< Competitor , Double > awayGoals; 
	private Map< Competitor , Double > homeGoals; 
	
	private File image;
	private boolean eliminated;

	/**
	 * 
	 * This constructor is used to create a {@code Competitor } object with the name and imageFile
	 * <br> The name is returned via call to {@code getName() } 
	 *@param name The name of this {@code Competitor}
	 *@param imageFile {@code File } that stores the image. This file must be in .jpg, .png or .jpeg formats
	 */
	public Competitor(String name, File imageFile)
	{
		this(name, null, imageFile);
	}

	/**
		This constructor is used to create a {@code Competitor } object with the first Name, 
		last name and imageFile.
	 * <br> The lastName is returned via call to {@code getLastName() } 
	 * <br> The firstName is returned via call to {@code getFirstName() } 
	 *@param first a {@code String } containing the first name of this {@code Competitor}
	 *@param last a String containing the last name of the {@code Competitor}
	 *@param imageFile The {@code File } object that contains a file in either .jpg, .png or .jpeg
	 */
	public Competitor(String first, String last, File imageFile)
	{

		if (first != null) {
			FIRST_NAME = first;
			LAST_NAME = last;
		} else
			throw new NoCompetitorNameException("You tried to input null as a name");
		setImage(imageFile);
		setEliminated(false);

		headToHead = new HashMap<Competitor , Double >();
		awayGoals = new HashMap<Competitor , Double >();
		homeGoals = new HashMap<Competitor , Double >();
		
	}

	/**
	 * Gets this {@code COmpetitor}'s first name  or his entire name
	 * if this was created with {@code Competitor(String name, File imageFile)  } constructor 
	 *@return String representation of this Competitor's firs tName
	 */
	public String getFirstName()
	{
		return FIRST_NAME;
	}

	/**
	 * Gets the last name of the Competitor
	 * 
	 *@return String representation of this {@code Competitor}'s last name
	 */
	public String getLastName() 
	{
		return LAST_NAME != null ? LAST_NAME : "";
	}

	/**
	 * Gets the name of this {@code Competitor}
	 *@return name as {@code String}
	 */
	public String getName() 
	{
		return getFirstName() + " " + getLastName();
	}

	/**
	 * Gets this {@code Competitor}'s number of wins
	 *@return an int of this Competitor's number of win
	 */
	public int getNumberOfWin() 
	{
		return getNumberOfAwayWin() + getNumberOfHomeWin();
	}

	
	/**
	 *Gets the number of draws this {@code Competitor } has
	 *@return number of draw as int
	 */
	public int getNumberOfDraw() 
	{
		return getNumberOfAwayDraw() + getNumberOfHomeDraw();
	}

	public int getNumberOfHomeDraw()
	{
		return homeDraw;
	}

	/**
	 * Increases this {@code Competitor}'s home draw by 1
	 *void
	 */
	protected void incrementHomeDraw()
	{
		homeDraw++;
	}
	
	/**
	 *Gets this {@code Competitor}'s number of away draw
	 *@return away draw as int
	 */
	public int getNumberOfAwayDraw()
	{
		return awayDraw;
	}
	
	/**
	 * Increases this {@code Competitor}'s away draw by one
	 */
	protected void incrementAwayDraw()
	{
		awayDraw++;
	}

	

	/**
	 * Gets this {@code Comepetitor } number of loss
	 * @return the number of loss as {@code int}
	 */
	public int getNumberOfLoss() 
	{
		return getNumberOfHomeLoss() +  getNumberOfAwayLoss();
	}

	/**
	 * Gets the total away games this {@code Competitor } lost
	 *@return
	 */
	public int getNumberOfAwayLoss()
	{
		return awayLoss;
	}

	/**
	 * Increase this {@code Competitor}'s number of away loss by one
	 *void
	 */
	protected void incrementAwayLoss()
	{
		awayLoss++;
	}

	/**
	 *Gets the number of {@code Fixture}s this {@code Competitor} lost at home
	 *@return an int
	 */
	public int getNumberOfHomeLoss()
	{
		return homeLoss;
	}

	/**
	 * Increases this {@code Comepetitor}'s home loss by 1
	 */
	protected void incrementHomeLoss() 
	{
		homeLoss++;
	}

	/**
	 * Gets a file containing this {@code Competitor} image
	 * @return a {@code File }object 
	 */
	public File getImage() 
	{
		return image;
	}

	/**
	 *Checks if this {@code Competitor } is eliminated from a {@code Tournament}
	 *Typically used by {@code Multistage} and {@code EliminationTournament}
	 *@see com.solutioninventors.tournament.types.Multistage
	 *@see com.solutioninventors.tournament.types.knockout.EliminationTournament
	 @return true when this {@code COmpetitor } is eliminated
	 */
	public boolean isEliminated() 
	{
		return eliminated;
	}

	
	public void setEliminated(boolean eliminated)
	{
		this.eliminated = eliminated;
	}

	/**
	 * Calculates the point this {@code Competitor} has in this tournament
	 * using data provided as its argument
	 *@param pWin The point for a win
	 *@param pDraw  Thw point for a draw
	 *@param pLoss The point for a loss
	 *@return the total point as {@code double}
	 */
	public double getPoint(double pWin, double pDraw, double pLoss) 
	{
		return (getNumberOfWin() * pWin) + (getNumberOfDraw() * pDraw) + (getNumberOfLoss() * pLoss);
	}

	/**
	 * 
	 *Stores the imageFile argument to this Competitor's {@code image } property 
	 *
	 *@param imageFile
	 *@throws ImageFormatException
	 */
	private void setImage(File imageFile) throws ImageFormatException  
	{
//		 This method copies the image file and stores it in the class dir
//		 It first validates the file and its format
		String imageName = imageFile.getName();

		if (imageFile.exists() || imageName.indexOf(".") < 0) {
			String format = imageName.substring(imageName.indexOf("."), imageName.length()); // get the format

			format = format.toLowerCase();
			if (format.matches(".png|.jpg|.jpeg")) {
				image = new File(getName() + format);
				copyFile(imageFile, image); // copies the file

			} else
				throw new ImageFormatException("The file is not a valid image file");

		} else
			throw new ImageFormatException("The URL is not a file");
	}

	/**
	 * Copies the file in its first argument and pastes it to its 
	 * second argument
	 *@param fileToCopy The file to be copied
	 *@param fileToPaste The destination file
	 */
	private void copyFile(File fileToCopy, File fileToPaste)
	{
		try 
		{
			FileChannel input = new FileInputStream(fileToCopy).getChannel();
			FileChannel output = new FileOutputStream(fileToPaste).getChannel();
			output.transferFrom(input, 0, input.size());

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			
			e.printStackTrace();
		}

	}

	/**
	 * Gets the total goals scored by this {@code Competitor} 
	 *
	 *@return The goalsScored by this Competitor
	 */
	public double getGoalsScored() 
	{
		return goalsScored;
	}

	/**
	 *Increases this {@code Competitor}'s goals scored by its argument
	 *@param score The value to increment by
	 */
	protected void incrementGoalsScoredBy(double score) 
	{
		goalsScored += score;
	}

	/**
	 * Gets the goals conceeded by this {@code Competitor}
	 *@return a double 
	 */
	public double getGoalsConceded() 
	{
		return goalsConceded;
	}

	/**
	 * Increments the goals conceeded by this {@code Competitor  } its argument
	 * 
	 *@param goals a 
	 */
	public void incrementGoalsConcededBy(double goals)
	{
		goalsConceded += goals;
	}

	/**
	 * Gets this {@code Competitor}'s goal difference
	 *@return difference between {@code getGoalsScored()} and {@code getGoalsConceded()} 
	 */
	public double getGoalDifference() 
	{
		return getGoalsScored() - getGoalsConceded();
	}

	@Override
	public String toString() 
	{
		return getName();
	}
	
	/**
	 *Gets the total {@code Fixture}s this {@code Competitor} has played
	 *
	 *@return an int
	 */
	public int getPlayedFixtures()
	{
		return getNumberOfDraw() + 
				getNumberOfWin() + 
				getNumberOfLoss() ;
	}
	
	/**
	 * Adds this {@code Competitor}the head to head score against of this 
	 * {@code Competitor} against an opponent
	 *@param com the opponent
	 *@param score this {@code Competitor }score
	 */
	protected void addToHeadToHead( Competitor com , double score )
	{
		if ( headToHead.containsKey( com ) )
			score += headToHead.get( com );
		
		headToHead.put( com , score );
	}
	
	/**
	 *Gets this {@code Competitor}'s score against a specified opponent
	 *Returns 0 if this {@code Competitor } has not met the specified
	 *opponent
	 *@param com the opponent
	 *@return a double
	 */
	public double getHeadToHeadScore( Competitor com  )
	{
		if( headToHead.containsKey( com ) )
			return headToHead.get( com ) ;
		return 0 ;
	}
	
	/**
	 *Increases this {@code Competitor}'s total away goals by against a particular oppponent by
	 *the specified score
	 *@param com the opponent
	 *@param score this {@code Competitor}'s score
	 */
	protected void addAwayGoal( Competitor com , double score )
	{
		if ( awayGoals.containsKey( com ) )
			score += awayGoals.get( com );
		
		awayGoals.put( com , score );
	}
	
	/**
	 * Increases this {@code Competitor}'s home goals against an opponet by score
	 *@param com the opponent
	 *@param score this {@code Competitor}'s score
	 */
	protected void addHomeGoal( Competitor com , double score )
	{
		if ( homeGoals.containsKey( com ) )
			score += homeGoals.get( com );
		
		homeGoals.put( com , score );
	}
	
	
	/**
	 * Gets the away goals that this competitor has scored against the
	 * specified opponent  
	 *@param  opponent This {@code Competitor}'s opponent
	 *@return double containing the away goal.Returns 0 if the two {@code Competitor}s have not met
	 */ 
	public double getAwayGoal( Competitor opponent  )
	{
		if( awayGoals.containsKey( opponent ) )
			return awayGoals.get( opponent ) ;
		return 0 ;
	}
	
	/**
	 * 
	 *
	 *@param com1 the first {@code Competitor }
	 *@param com2 the second {@code Competitor } to compare
	 *@return Returns true if {@code com1}'s method {@code getName()} returns the same value as 
	 *{@code com2}'s method {@code getName()}
	 */
	public static boolean isEqual( Competitor com1 , Competitor com2 )
	{
		if ( com1.getName().equals( com2.getName() ) )
			return true;
		return false;
	}

	/**
	 * Gets the number of games won away by this {@code Competitor}
	 *@return this {@code Competitor}'s number of away win
	 */
	public int getNumberOfAwayWin()
	{
		return awayWin;
	}

	/**
	 * Increases this {@code Competitor}'s number of away win
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	protected void incrementAwayWin()
	{
		awayWin++;
	}
	
	/**
	 * Gets the number of home wins by this {@code Competitor}
	 *@return int
	 */
	public int getNumberOfHomeWin()
	{
		return homeWin;
	}

	/**
	 * Increase  this {@code Competitor}'s number of home win by 1 
	 */
	protected void incrementHomeWin()
	{
		homeWin++ ;
	}

	/**
	 * Gets the number of away goals scored by this {@code Competitor}
	 *double
	 *@return number of away goals as {@code double }
	 */
	public double getNumberOfAwayGoals()
	{
		
		double total = 0 ;
		Set<Competitor> keys = awayGoals.keySet();
		
		for ( Competitor  k : keys)
			total += awayGoals.get( k );
		
		return total;
	}

	
	
	
}
