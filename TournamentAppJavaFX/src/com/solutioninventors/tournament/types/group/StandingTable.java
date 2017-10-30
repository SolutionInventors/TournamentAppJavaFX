/**
 * author: Oguejiofor Chidiebere
 * Jul 31, 2017
 * StandingTable.java
 * 11:32:36 PM
 *
 */
package com.solutioninventors.tournament.types.group;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;


/***
 * This class encapsulates the operation of a standingTable in a group
 * Tournament The input of the class is a competitor array while the output is a
 * String[][] table This class also uses the SportType enum in order to know
 * when to add GF , GA and GD However the info W , D , L , Pts are not sportType
 * dependent Operations of this class have been tested and debugged
 * 
 * This class also uses a TieBreaker object to create the tabke
 * This tie breaker must contains Breakers of type Breaeker.GROUP Or Breaker.BOTH
 * 
 * The class contains a bunch of getters but no setter 
 * Thus it is IMMUTABLE!
 */

public class StandingTable implements Serializable {

	private static final long serialVersionUID = 2063358009821795868L;
	
	private Competitor[] competitors;
	private String[][] table;

	private final SportType sportType;
	private final double POINT_FOR_WIN;
	private final double POINT_FOR_DRAW;
	private final double POINT_FOR_LOSS;

	private final TieBreaker TIE_BREAKER;

	public StandingTable(SportType type, Competitor[] comptitors, double win, double draw, double loss,
			TieBreaker breakers) {
		sportType = type;
		competitors = comptitors;
		table = new String[competitors.length][sportType == SportType.GOALS_ARE_SCORED ? 9 : 6];

		POINT_FOR_WIN = win;
		POINT_FOR_DRAW = draw;
		POINT_FOR_LOSS = loss;
		TIE_BREAKER = breakers;
		updateTables(); // updates the String[][] table and Competitors[] array

	}

	public double getWinPoint() {
		return POINT_FOR_WIN;
	}

	public double getDrawPoint() {
		return POINT_FOR_DRAW;
	}

	public double getLossPoint() {
		return POINT_FOR_LOSS;
	}

	protected void updateTables() {
		
		competitors = getTable();
		updateStringTable( getTable(), table, TieBreaker.AWAY_FIXTURES + TieBreaker.HOME_FIXTURES  ); // updates StringTable

	}

	public Competitor[] getTable()
	{
		return getTieBreaker().breakTies( 
				TieBreaker.HOME_FIXTURES + TieBreaker.AWAY_FIXTURES,
				competitors, getWinPoint(), 
				getDrawPoint(), getLossPoint());
	}

	public Competitor getCompetitor(int position) {
		updateTables();
		
		if ( position < getCompetitors().length )
			return getTable()[ position ];
		
		return null ;
	}
	
	
	private String[][] updateStringTable( Competitor[] competitors,  String[][] table,
											int tieBreakerType )
	{
		int numOfCompetitors = competitors.length ;
		String[] nameColumn = new String[ numOfCompetitors ] ;
		String[] playedColumn = new String[ numOfCompetitors ];
		String[] winsColumn =  new String[ numOfCompetitors ] ;
		String[] drawColumn = new String[ numOfCompetitors ]  ;
		String[] lossColumn =  new String[ numOfCompetitors ]  ;
		String[] pointColumn =  new String[ numOfCompetitors ]  ;
		
		createNoGoalColumns(competitors, nameColumn, playedColumn, winsColumn, 
				drawColumn, lossColumn, pointColumn, tieBreakerType);
		
		for ( int row = 0 ; row < competitors.length ; row++ )
		{
			table[ row ][ 0 ] = nameColumn[ row ];
			table[ row ][ 1 ] = playedColumn[ row ];
			table[ row ][ 2 ] = winsColumn[ row ];
			table[ row ][ 3 ] = drawColumn[ row ];
			table[ row ][ 4 ] = lossColumn[ row ];
			table[ row ][ 5 ] = pointColumn[ row ];
			
		}
		
		if (sportType == SportType.GOALS_ARE_SCORED )
		{
			String[] goalsScoredColumn = new String[ numOfCompetitors ]  ;
			String[] goalsConcededColumn =  new String[ numOfCompetitors ] ; 
			String[] goalDifferenceColumn = new String[ numOfCompetitors ] ;
			
			createGoalScoredColumns(competitors, goalsScoredColumn, goalsConcededColumn, 
					goalDifferenceColumn, tieBreakerType);
			
			for ( int row = 0 ; row < competitors.length ; row++ )
			{
				table[ row ][ 5 ] = goalsScoredColumn[ row ];
				table[ row ][ 6 ] = goalsConcededColumn[ row ];
				table[ row ][ 7 ] = goalDifferenceColumn[ row ];
				table[ row ][ 8 ] = pointColumn[ row ];
			}
		}
		return table;
			
	}

	private void createGoalScoredColumns(Competitor[] competitors , String[] goalsScoredColumn, String[] goalsConcededColumn,
			String[] goalDifferenceColumn, int tieBreakerType)
	{
		Function<Competitor, String> goalsScoredFunction = null;
		Function<Competitor, String> goalsDiffFunction = null;
		Function<Competitor, String> goalsConceededFunction = null;
		
		if ( tieBreakerType ==  TieBreaker.AWAY_FIXTURES)
		{

			goalsScoredFunction = com -> String.valueOf( com.getAwayGoalsScored( true ) );
			goalsConceededFunction = com -> String.valueOf( com.getAwayGoalsConceeded( true ) );
			goalsDiffFunction = com -> String.valueOf( com.getAwayGoalsDifference( true ) );
			
		}
		else if( tieBreakerType == TieBreaker.HOME_FIXTURES )
		{
			goalsScoredFunction = com -> String.valueOf(  com.getHomeGoalsScored( true ) );
			goalsConceededFunction = com -> String.valueOf(  com.getHomeGoalsConceeded( true ) );
			goalsDiffFunction = com -> String.valueOf( com.getHomeGoalsDifference( true ) );
			
		}
		else
		{
			goalsScoredFunction = com -> String.valueOf( com.getGoalsScored(true ) );
			goalsConceededFunction = com -> String.valueOf( com.getGoalsConceeded(true ) );
			goalsDiffFunction = com -> String.valueOf( com.getGoalDifference( true ) );
			
			
		}
		
		Arrays.stream( competitors )
				   .map( goalsScoredFunction )
				   .collect( Collectors.toList() )
				   .toArray( goalsScoredColumn );
		
		
			Arrays.stream( competitors )
			   .map( goalsConceededFunction )
			   .collect( Collectors.toList() )
			   .toArray(goalsConcededColumn );
			
			
		
		Arrays.stream( competitors )
			   .map( goalsDiffFunction )
			   .collect( Collectors.toList() )
			   .toArray( goalDifferenceColumn );
	}

	public void createNoGoalColumns(Competitor[] competitors, String[] nameColumn, String[] playedColumn, String[] winsColumn, String[] drawColumn,
			String[] lossColumn, String[] pointColumn, int type)
	{
		Function<Competitor, String> playedFunction = null;
		Function<Competitor, String> winFunction = null;
		Function<Competitor, String> lossFunction = null;
		Function<Competitor, String> drawFunction = null;
		Function<Competitor, String> pointFunction = null ;
		if ( type == TieBreaker.AWAY_FIXTURES )
		{
			playedFunction = com -> String.valueOf( com.getFixturesPlayedAway() );
			winFunction = com -> String.valueOf( com.getNumberOfAwayWin() );
			lossFunction = com -> String.valueOf( com.getNumberOfAwayLoss() );
			drawFunction = com -> String.valueOf( com.getNumberOfAwayDraw() );
			pointFunction = com -> 
			String.valueOf( com.getAwayPoint(
					getWinPoint(), getDrawPoint(), getLossPoint())  );
			
		}
		else if( type == TieBreaker.HOME_FIXTURES )
		{
			playedFunction = com -> String.valueOf( com.getFixturesPlayedHome() );
			winFunction = com -> String.valueOf( com.getNumberOfHomeWin() );
			lossFunction = com -> String.valueOf( com.getNumberOfHomeLoss() );
			drawFunction = com -> String.valueOf( com.getNumberOfHomeDraw() );
			pointFunction = com -> 
			String.valueOf( com.getHomePoint(
					getWinPoint(), getDrawPoint(), getLossPoint())  );
		}
		else
		{
			playedFunction = com -> String.valueOf( com.getPlayedFixtures() );
			winFunction = com -> String.valueOf( com.getNumberOfWin() );
			lossFunction = com -> String.valueOf( com.getNumberOfLoss() );
			drawFunction = com -> String.valueOf( com.getNumberOfDraw() );
			pointFunction = com -> 
				String.valueOf( com.getPoint(
						getWinPoint(), getDrawPoint(), getLossPoint())  );
			
		}
		
		Arrays.stream( competitors )
				   .map( Competitor :: getName )
				   .collect( Collectors.toList() )
				   .toArray( nameColumn );
		
		Arrays.stream( competitors )
		   .map(playedFunction )
		   .collect( Collectors.toList() )
		   .toArray( playedColumn );
		
		 Arrays.stream( competitors )
				   .map( winFunction )
				   .collect( Collectors.toList() )
				   .toArray( winsColumn );
		 
		Arrays.stream( competitors )
				   .map( drawFunction  )
				   .collect( Collectors.toList() )
				   .toArray( drawColumn );
		
		Arrays.stream( competitors )
				   .map( lossFunction  )
				   .collect( Collectors.toList() )
				   .toArray( lossColumn) ;
		
		
		Arrays.stream( competitors )
		   .map( pointFunction )
		   .collect( Collectors.toList() )
		   .toArray ( pointColumn ) ;
	}
		

	public Competitor[] getCompetitors() {
		return getTable();
	}

	

	public String[][] getStringTable() {
		return table;
	}

	public TieBreaker getTieBreaker() {
		return TIE_BREAKER;
	}
	
	
	public Competitor[] getHomeTable()
	{
		return getTieBreaker().breakTies( 
				TieBreaker.HOME_FIXTURES ,
				competitors, getWinPoint(), 
				getDrawPoint(), getLossPoint());

	}
	
	public String[][] getHomeStringTable()
	{
		String[][] homeTable =  new String[ table.length ][ table[0].length ];
		
		return updateStringTable( getHomeTable() , homeTable, TieBreaker.HOME_FIXTURES  );
	}
	
	public Competitor[] getAwayTable()
	{
		return getTieBreaker().breakTies( 
				TieBreaker.AWAY_FIXTURES ,
				competitors, getWinPoint(), 
				getDrawPoint(), getLossPoint());

	}
	
	public String[][] getAwayStringTable()
	{
		String[][] awayTable =  new String[ table.length ][ table[0].length ];
		
		return updateStringTable( getAwayTable() , awayTable, TieBreaker.AWAY_FIXTURES );
	}
	
	
}
