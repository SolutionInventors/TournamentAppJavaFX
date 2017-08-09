/**
 *@Author: Oguejiofor Chidiebere
 *RoundRobinTournament.java
 *Aug 3, 2017
 *10:10:42 PM
 */
package com.solutioninventors.tournament.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.NoOutstandingException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

public class RoundRobinTournament extends GroupTournament
{

	/**
	 * This class is used to create a ROund Robin tournament and it extends GroupTournament
	 * When created the constructor stores competitors, Sporttype and other info
	 * It then calls utility method createRounds() which uses the Carousel-Berger system
	 * to create the rounds in the tournament and enters the first round.
	 * 
	 * Note: The first roundNumber = 0. Also this class can have byes
	 * 
	 * After the object is created the program stores the first round results via calls to
	 * method setResult
	 * 
	 * After the results are inputed, class increments rounds via a call to moveToRound() from
	 * its object. 
	 * When moveToROund() is called it checks that the results were inputed and if there are
	 * pending fixtures it prompts the user to choose if he wants these fixtures to be outstnading
	 * if so it adds those fixtures to List outstandingMatches
	 * 
	 * if all current round fixtures are inputed moveToNextRound() increments roundNuber
	 * 
	 */
	
	private final boolean HOME_AND_AWAY_FIXTURES;
	private final boolean BYE; 
	
	private List<Fixture> outstandingMatches ;
	
	public RoundRobinTournament(Competitor[] comps, SportType type , double winPoint ,
								double drawPoint, double lossPoint , boolean away )
	{
		super(comps, type, winPoint , drawPoint, lossPoint );
		HOME_AND_AWAY_FIXTURES =  away ;
		BYE =  getCompetitors().length % 2 == 0 ? false : true ;
		setRoundsArray( createRounds( getCompetitors() ) ) ;
		outstandingMatches =  new ArrayList<>();
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
		
		if ( getCurrentRoundNum() < rnds.length  )
		{
			getTable().updateTables();
			if ( !getCurrentRound().isComplete() ) //contains outstanding
			{
				int ans = JOptionPane.showConfirmDialog( null , "Has pending fixtures.\nContinue?" );
				
				if ( ans == JOptionPane.YES_OPTION )
				{
					Arrays.stream( getCurrentRound().getPendingFixtures())
						  .forEach( f -> outstandingMatches.add( f ) );
					JOptionPane.showMessageDialog( null , "Incomplete fixtures added to outstanding" );
					setCurrentRoundNum( getCurrentRoundNum() + 1 );
					
				}
			}
			else
			{
				setCurrentRoundNum( getCurrentRoundNum() + 1 );
				if ( getCurrentRoundNum() < getRoundsArray().length && 
						outstandingMatches.size() > 0 )
				{
					String message = "Only outsanding matches are left";
					JOptionPane.showMessageDialog( null , message ) ;
				}
			}
		}
		else if ( outstandingMatches.size() > 0 )
		{
			String message = "Only outsanding matches are left";
			JOptionPane.showMessageDialog( null , message ) ;
		}
		else
		{
			String message = "Tournament has ended";
			JOptionPane.showMessageDialog( null , message ) ;
		}
		
	}

	public boolean hasEnded()
	{
		return getCurrentRoundNum() >= getRoundsArray().length &&
			outstandingMatches.size() <= 0 ? true : false ;	
	}
	
	
	
	@Override
	public void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 )
	{		
		if ( Arrays.stream( getCurrentRound().getFixtures() )
				.anyMatch( f -> f.hasFixture( com1 , com2  )) )
		{
			Arrays.stream( getCurrentRound().getFixtures() )
			.filter( f -> f.hasFixture( com1 , com2  ) )
			.forEach( f -> f.setResult(score1, score2) );
			
		}
	}
	
	
	public void setOutstandingResult( Competitor com1 , int score1 , int score2 , Competitor com2 )
	{		
		if ( outstandingMatches.stream()
				.anyMatch( f -> f.hasFixture( com1 , com2  )) )
		{
			
			for( int i = 0 ; i < outstandingMatches.size() ; i ++ )
			{
				if ( outstandingMatches.get( i ).hasFixture( com1, com2) )
				{
					outstandingMatches.get( i ).setResult( score1, score2);
					outstandingMatches.remove( i );
					break ;
				}
			}
		}
	}
	
	public Fixture[] getOutstanding() throws NoOutstandingException
	{
		if ( outstandingMatches.size() > 0 )
		{
			Fixture[] fixes = new Fixture [ outstandingMatches.size() ];
			outstandingMatches.toArray( fixes ) ;
			return fixes ;
		}
		throw new NoOutstandingException( ) ; 
		
	}

	public Competitor getWinner()
	{
		getTable().updateTables(); 
		if ( hasEnded())
			return getTable().getCompetitors()[ 0 ];
		return null;
	}
	
	
	
}
