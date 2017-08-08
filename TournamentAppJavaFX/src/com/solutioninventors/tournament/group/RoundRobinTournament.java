/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTournament.java
 *Aug 3, 2017
 *10:10:42 PM
 */
package com.solutioninventors.tournament.group;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

public class RoundRobinTournament extends GroupTournament
{

	private final boolean HOME_AND_AWAY_FIXTURES;
	private final boolean BYE; 
	
	public RoundRobinTournament(Competitor[] comps, SportType type , double winPoint ,
								double drawPoint, double lossPoint , boolean away )
	{
		super(comps, type, winPoint , drawPoint, lossPoint );
		HOME_AND_AWAY_FIXTURES =  away ;
		BYE =  getCompetitors().length % 2 == 0 ? false : true ;
		setRoundsArray( createRounds( getCompetitors() ) ) ;
		
	}
	
	private Round[] createRounds(Competitor[] competitor )
	{
		/**
		 * This method creates rounds by assigning numbers to each competitor 
		 * The method achieves this by implementing the the  Carousel-Berger system
		 * You can read up Carousel-Berger system online for more info
		 * This method also allows byes 
		 */
		
		int tempCurrentRound = 0 ;
		int totalCompetitors = getCompetitors().length ;
		int[][] fixureCreator = new int [ hasBye() ? 
											totalCompetitors + 1 : totalCompetitors/2 ][ 2 ];
		
		Round[] tempRound = new Round[ hasBye() ? totalCompetitors : totalCompetitors  - 1 ];
		fixureCreator[ 0 ][ 0 ] = hasBye() ? 0 : 1 ; //this value does not change in the loop
		
		int initialValue = fixureCreator[ 0 ][ 0 ] + 1 ; // loop begins writing numbers with this value
	
		
		for( int beginValue = initialValue ; beginValue <= totalCompetitors  ; beginValue++ ) //loop creates each round
		{

			int num = beginValue;
			
			for( int row = 1 ; row < fixureCreator.length ; row++ )
			{
				fixureCreator[ row ][ 0 ] = num ; //set home team number
				num++ ;
				num = num > totalCompetitors? initialValue : num ;
			}
			
			for ( int row  = fixureCreator.length - 1 ; row >= 0 ;  row -- )
			{
				fixureCreator[ row ][ 1 ] = num ;//set away team number
				num++ ;
				num = num > totalCompetitors? initialValue : num ;
			}
			
			tempRound[ tempCurrentRound] = 
					convertToRound( fixureCreator  ); //convert int[][] to Round 
			tempCurrentRound++ ;
			
		}
		
		return scheduleRounds( tempRound ); 
	}

	private Round convertToRound(int[][] fixes ) 
	{
		/**
		 * This methos covverts an int[][] to a Round object
		 * precondition: fixes = int[ numberOfCompetitors /2][ 2 ] if no bye or
		 * 				 fixes = int[ numberOfCompetitors/2 + 1][ 2 ]  if bye
		 * This method skips the first row if bye
		 */
		Competitor[] competitors = getCompetitors();
		Fixture[] fixtures = new Fixture[ competitors.length /2 ];
		
		int skip = hasBye() ? 1 : 0 ;
		
		for ( int i = 0 ; i < fixtures.length ; i ++ )
		{
			fixtures[ i ] = new Fixture ( competitors[ fixes[ i + skip ][ 0 ]  - 1 ],
			                              competitors[ fixes[ i + skip ][ 1 ] - 1 ] );
		}
		
		return new Round ( fixtures );
		
	}

	
	private Round[] scheduleRounds(Round[]  rounds )
	{
		/**
		 * This method schedules the tournament such that a Competitor would not have too
		 * many consective home games or too many consecutive away games
		 * precondition: variable rounds contains numberofCompetitors -1 elements
		 * 
		 */
		Round[] inverseRounds = new Round[ rounds.length ];
		
		for ( int i = 0 ; i < rounds.length ; i++ )
		{
			inverseRounds[ i ] = rounds[ i ].invertHomeAndAway() ;
			Collections.shuffle( Arrays.asList( inverseRounds[ i ].getFixtures() )); 
		}
		
		
		int numOfRounds = hasHomeaAndAwayFixtures() ? rounds.length * 2 : rounds.length  ; 
		Round[] finalRounds =  new Round[ numOfRounds  ];
		
		
		for( int i = 0 ; i < rounds.length ; i++ )
		{
			if( i % 2  == 0 )
				finalRounds[ i ] = rounds[ i ];
			else
				finalRounds[ i ] = inverseRounds[ i ];
			
		}
		
		if ( hasHomeaAndAwayFixtures() )
		{
			
			for( int i = 0 ; i < rounds.length ; i++ )
			{
				if( i % 2  == 0 )
					finalRounds[ i + rounds.length ] = inverseRounds [ i ];
				else
					finalRounds[ i + rounds.length ] = rounds[ i ];
				
			}
			
			//shuffle rounds after mid tournament
			for ( int i = 0 ; i < rounds.length ; i ++ ) 
			{
				rounds[ i ] = finalRounds[ i ];
				inverseRounds[ i ] = finalRounds[ i + rounds.length ];
			}
			
			Collections.shuffle( Arrays.asList( rounds ) );
			Collections.shuffle( Arrays.asList( rounds ) );
			
			for( int i = 0 ; i < rounds.length ; i  ++ )
			{
				finalRounds[ i ] = rounds[ i ];
				finalRounds[ i + rounds.length ] = inverseRounds[ i ] ;
			}
			
		}
		
		return finalRounds ;
	}

	public boolean hasHomeaAndAwayFixtures()
	{
		return HOME_AND_AWAY_FIXTURES;
	}

	public boolean hasBye()
	{
		return BYE;
	}

	@Override
	public void moveToNextRound()
	{
		Round[] rnds = getRoundsArray();
		
		for ( int i = 0 ; i < rnds.length ; i ++  )
		{
			if ( rnds[ i ].isComplete() )
			{
				System.out.println("Round is incomplete\nDo you want to continue?");
				System.out.print("Y or N? "); // replace with JOptionPane version of JavaFx
				String ans = new Scanner( System.in ).nextLine();
				if ( ans.toLowerCase().equals( "y" ) )
				{
					System.out.println("continue" );
					setCurrentRoundNum( getCurrentRoundNum() + 1 );
				}
				else
					System.out.println( "did not change round" );
				break;
			}
		}
	}
	
	
}
