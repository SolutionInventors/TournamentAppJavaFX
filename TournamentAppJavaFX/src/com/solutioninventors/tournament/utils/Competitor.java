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
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import com.solutioninventors.tournament.exceptions.ImageFormatException;
import com.solutioninventors.tournament.exceptions.NoCompetitorNameException;

public class Competitor {

	/**
	 * This class encapsulates all the information about a competitor The class
	 * stores the name, numberOFWins, numberOfDraws, numberOfLosses points , image
	 * file , and a boolean which is true when the Competitor is eliminated
	 */
	private final String FIRST_NAME;
	private final String LAST_NAME;

	private int numberOfWin;
	private int numberOfDraw;
	private int numberOfLoss;

	private int numberOfAwayWin;
	private int numberOfHomeWin;
	private double goalsScored;
	private double goalsConceded;

	private Map< Competitor , Double > headToHead ; 
	private Map< Competitor , Double > awayGoals; 
	private File image;
	private boolean eliminated;

	public Competitor(String name, File imageFile)
	{
		this(name, null, imageFile);
	}

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
		
	}

	public String getFirstName()
	{
		return FIRST_NAME;
	}

	public String getLastName() 
	{
		return LAST_NAME != null ? LAST_NAME : "";
	}

	public String getName() 
	{
		return getFirstName() + " " + getLastName();
	}

	public int getNumberOfWin() 
	{
		return numberOfWin;
	}

	public void incrementWins() 
	{
		numberOfWin++;
	}

	public int getNumberOfDraw() 
	{
		return numberOfDraw;
	}

	public void incrementDraw() 
	{
		numberOfDraw++;
	}

	public int getNumberOfLoss() 
	{
		return numberOfLoss;
	}

	public void incrementLoss() 
	{
		numberOfLoss++;
	}

	public File getImage() 
	{
		return image;
	}

	public boolean isEliminated() 
	{
		return eliminated;
	}

	public void setEliminated(boolean eliminated)
	{
		this.eliminated = eliminated;
	}

	public double getPoint(double pWin, double pDraw, double pLoss) 
	{
		return (getNumberOfWin() * pWin) + (getNumberOfDraw() * pDraw) + (getNumberOfLoss() * pLoss);
	}

	private void setImage(File imageFile) 
	{
//		 This method copies the image file and stores it in the class dir
//		 It first validates the file and its format
		String imageName = imageFile.getName();

		if (imageFile.exists() || imageName.indexOf(".") < 0) {
			String format = imageName.substring(imageName.indexOf("."), imageName.length()); // get the format
//			System.out.println("still comp");
//			System.out.println(format);
			
//			System.out.println(format);
//			NOTE: after abt an hour of troubleshooting my code
//			I realized that the problem was from here
//			so I had to set this to lowercase to match all checks
			format = format.toLowerCase();
			if (format.matches(".png|.jpg|.jpeg")) {
				image = new File(getName() + format);
				copyFile(imageFile, image); // copies the file

			} else
				throw new ImageFormatException("The file is not a valid image file");

		} else
			throw new ImageFormatException("The URL is not a file");
	}

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

	public double getGoalsScored() 
	{
		return goalsScored;
	}

	public void incrementGoalsScoredBy(double score1) 
	{
		goalsScored += score1;
	}

	public double getGoalsConceded() 
	{
		return goalsConceded;
	}

	public void incrementGoalsConcededBy(double goals)
	{
		goalsConceded += goals;
	}

	public double getGoalDifference() 
	{
		return getGoalsScored() - getGoalsConceded();
	}

	@Override
	public String toString() 
	{
		return getName();
	}
	
	public int getPlayedFixtures()
	{
		return getNumberOfDraw() + 
				getNumberOfWin() + 
				getNumberOfLoss() ;
	}
	
	public void addToHeadToHead( Competitor com , double score )
	{
		if ( headToHead.containsKey( com ) )
			score += headToHead.get( com );
		
		headToHead.put( com , score );
	}
	
	public double getHeadToHeadScore( Competitor com  )
	{
		if( headToHead.containsKey( com ) )
			return headToHead.get( com ) ;
		return 0 ;
	}
	
	
	public void addAwayGoal( Competitor com , double score )
	{
		if ( awayGoals.containsKey( com ) )
			score += awayGoals.get( com );
		
		awayGoals.put( com , score );
	}
	
	public double getAwayGoal( Competitor com  )
	{
		if( awayGoals.containsKey( com ) )
			return awayGoals.get( com ) ;
		return 0 ;
	}
	public static boolean isEqual( Competitor com1 , Competitor com2 )
	{
		if ( com1.getName().equals( com2.getName() ) )
			return true;
		return false;
	}

	public int getNumberOfAwayWin()
	{
		return numberOfAwayWin;
	}

	public void incrementNumberOfAwayWin()
	{
		numberOfAwayWin++ ;
	}
	
	public int getNumberOfHomeWin()
	{
		return numberOfHomeWin;
	}

	public void incrementNumberOfHomeWin()
	{
		numberOfHomeWin++ ;
	}
	
}
