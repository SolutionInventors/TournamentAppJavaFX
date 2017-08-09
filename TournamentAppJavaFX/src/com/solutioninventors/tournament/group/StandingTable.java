/**
 * author: Oguejiofor Chidiebere
 * Jul 31, 2017
 * StandingTable.java
 * 11:32:36 PM
 *
 */
package com.solutioninventors.tournament.group;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;

public class StandingTable
{
	
	/***
	 * This class encapsulates the operation of a standingTable in a group Tournament
	 * The input of the class is a competitor array while the output is a String[][] table
	 * This class also uses the SportType enum in order to know when to add GF , GA and GD
	 * However the info W , D , L , Pts are not sportType dependent
	 * Operations of this class have been tested and debugged
	 */
	private Competitor[] competitors;
	private String[][] table;
	
	private final SportType sportType;
	private final double POINT_FOR_WIN;
	private final double POINT_FOR_DRAW;
	private final double POINT_FOR_LOSS;
	
	public StandingTable( SportType type, Competitor[] comptitors , 
			double win , double draw , double loss )
	{
		sportType = type ;
		setCompetitors(comptitors);
		table = new 
			String[ competitors.length ]
				  [ sportType == SportType.GOALS_ARE_SCORED ? 8 : 5 ];
		
		POINT_FOR_WIN = win ;
		POINT_FOR_DRAW = draw ;
		POINT_FOR_LOSS = loss ;
		updateTables(); //updates the String[][] table and Competitors[] array
		
	}

	private double getPointForWin()
	{
		return POINT_FOR_WIN ;
	}
	private double getPointForDraw()
	{
		return POINT_FOR_DRAW ;
	}
	private double getPointForLoss()
	{
		return POINT_FOR_LOSS ;
	}
	
	public void updateTables( )
	{
//		The tableComparator should be improved with more tieBreakers
		
		Function< Competitor, Double >  pointFunction = c->
				c.getPoint( getPointForWin() , getPointForDraw() , getPointForLoss() );
		
		Comparator<Competitor> tableComparator = 
				Comparator.comparing( pointFunction )
									.thenComparing(Competitor:: getGoalDifference )
									.thenComparing( Competitor :: getName ).reversed();
		
		Arrays.stream( competitors )
				.sorted( tableComparator )
				.collect( Collectors.toList()  )
				.toArray( competitors );
		

		updateStringTable(); // updates StringTable
		
	}

	private void updateStringTable()
	{
		int numOfCompetitors = competitors.length ;
		String[] nameColumn = new String[ numOfCompetitors ] ;
		String[] winsColumn =  new String[ numOfCompetitors ] ;
		String[] drawColumn = new String[ numOfCompetitors ]  ;
		String[] lossColumn =  new String[ numOfCompetitors ]  ;
		String[] pointColumn =  new String[ numOfCompetitors ]  ;
		
		Arrays.stream( competitors )
				   .map( Competitor :: getName )
				   .collect( Collectors.toList() )
				   .toArray( nameColumn );
		
		 Arrays.stream( competitors )
				   .map( com -> String.valueOf( com.getNumberOfWin() ) )
				   .collect( Collectors.toList() )
				   .toArray( winsColumn );
		 
		Arrays.stream( competitors )
				   .map( com -> String.valueOf( com.getNumberOfDraw() )  )
				   .collect( Collectors.toList() )
				   .toArray( drawColumn );
		
		Arrays.stream( competitors )
				   .map( com -> String.valueOf( com.getNumberOfLoss() )  )
				   .collect( Collectors.toList() )
				   .toArray( lossColumn) ;
		
		
		Arrays.stream( competitors )
		   .map( com -> String.valueOf( 
				   			com.getPoint( getPointForWin() , getPointForDraw() ,getPointForLoss()) ) )
		   .collect( Collectors.toList() )
		   .toArray ( pointColumn ) ;
		
		for ( int row = 0 ; row < competitors.length ; row++ )
		{
			table[ row ][ 0 ] = nameColumn[ row ];
			table[ row ][ 1 ] = winsColumn[ row ];
			table[ row ][ 2 ] = drawColumn[ row ];
			table[ row ][ 3 ] = lossColumn[ row ];
			table[ row ][ 4 ] = pointColumn[ row ];
			
		}
		
		if (sportType == SportType.GOALS_ARE_SCORED )
		{
			String[] goalsScoredColumn = new String[ numOfCompetitors ]  ;
			String[] goalsConcededColumn =  new String[ numOfCompetitors ] ; 
			String[] goalDifferenceColumn = new String[ numOfCompetitors ] ;
			
			Arrays.stream( competitors )
					   .map( com -> String.valueOf( com.getGoalsScored() ) )
					   .collect( Collectors.toList() )
					   .toArray( goalsScoredColumn );
			
			
				Arrays.stream( competitors )
				   .map( com -> String.valueOf( com.getGoalsConceded() ) )
				   .collect( Collectors.toList() )
				   .toArray(goalsConcededColumn );
				
				
			
			Arrays.stream( competitors )
				   .map( com -> String.valueOf( com.getGoalDifference() ) )
				   .collect( Collectors.toList() )
				   .toArray( goalDifferenceColumn );
			
			for ( int row = 0 ; row < competitors.length ; row++ )
			{
				table[ row ][ 4 ] = goalsScoredColumn[ row ];
				table[ row ][ 5 ] = goalsConcededColumn[ row ];
				table[ row ][ 6 ] = goalDifferenceColumn[ row ];
				table[ row ][ 7 ] = pointColumn[ row ];
			}
			
		}
		
	}

	public Competitor[] getCompetitors()
	{
		return competitors;
	}

	public void setCompetitors(Competitor[] comps)
	{
		if ( comps != null 	)
			competitors = comps;
	
	}
	
	public String[][] getStringTable ()
	{
		return table;
	}
	
	
}


