/**
 *@Author: Oguejiofor Chidiebere
 *DoubleElimination.java
 *Aug 6, 2017
 *10:38:53 PM
 */
package com.solutioninventors.tournament.knockout;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class DoubleElimination extends EliminationTournament
{

	private Round[] winnerBracketRounds;
	private Round[] losersMinorRounds;
	private Round[] losersMajorRounds;
	private Round[] tournamentFinals;
	
	private Competitor tournamentWinner ; 
	private boolean tournamentOver;
	
	public DoubleElimination( Competitor[] comps ) throws TournamentException
	{
		super( comps );
		if ( Math.sqrt( getCompetitors().length )%2 != 0 	)
		{
			
			throw  new TournamentException( "The Double-Elimination must have a total"
					+ "competor equal to a power of 2 (i.e competitors = 2^x" );
			
		}
		
		createTournament();
		
	}

	private void createTournament()
	{
		Competitor[] tempComps= getCompetitors();
		
		winnerBracketRounds = new Round[ tempComps.length / 2 - 1];
		losersMajorRounds = new Round[ winnerBracketRounds.length  ] ;
		losersMinorRounds = new Round[ winnerBracketRounds.length  ] ;
		tournamentFinals = new Round[ 2 ];

		Fixture[] fixtures = new Fixture[ tempComps.length / 2 ];
		for( int i = 0 ; i < fixtures.length ; i += 2 )
		{
			fixtures[ i/2 ] = new Fixture( tempComps[ i ] ,tempComps[ i + 1 ] );
		}
		winnerBracketRounds[ 0  ] = new Round( fixtures );
	}
	
	
	public void setResult( Competitor com1 , double score1 , double score2 , Competitor com2 )
	{
		if ( tournamentFinals[ 1 ] != null )
			tournamentFinals[ 0 ].setScores( com1, score1, score2, com2);
		else if ( getCurrentRoundNum() ==  0 )
			winnerBracketRounds[ 0 ].setScores(com1, score1, score2, com2);
		else if ( ! isMajorBracket() )
		{
			if ( getCurrentMinorBracketRound().hasFixture( com1 , com2  ))
				getCurrentMinorBracketRound().setScores( com1 , score1, score2, com2);
			else if ( getCurrentWinnersBracketRound().hasFixture(com1, com2))
				getCurrentWinnersBracketRound().hasFixture( com1 , com2);
			
		}
		else if ( isMajorBracket() && getCurrentMajorBracketRound().hasFixture( com1, com2))
			getCurrentMajorBracketRound().setScores( com1, score1, score2, com2);
		
	}

	private boolean isMajorBracket()
	{
		// returns true when major current fixtures is major bracket
		if ( getCurrentRoundNum() < losersMajorRounds.length ||
				getCurrentMajorBracketRound() == null )
			return false ;
		else
			return true ;
	}
	
	public Competitor getTournamentWinner()
	{
		return tournamentWinner;
	}

	private void setTournamentWinner(Competitor tournamentWinner)
	{
		this.tournamentWinner = tournamentWinner;
	}

	public void moveToNextRound() throws MoveToNextRoundException 
	{
		if ( tournamentFinals[ 0 ] == null )
		{
			if ( getCurrentWinnersBracketRound().isComplete() && 
					!getCurrentMinorBracketRound().isComplete() &&
					!getCurrentWinnersBracketRound().hasDraw() && 
					!getCurrentMinorBracketRound().hasDraw() )
			{
				coalateRoundResults();
				
			}
		}
		
		else if ( !tournamentFinals[ 0 ].isComplete()  )
		{
			Fixture theFinal = tournamentFinals[ 0  ].getFixtures()[ 0 ];
			if ( theFinal.getWinner().getNumberOfLoss() == 0 )
			{
				setTournamentWinner( theFinal.getWinner() );
				setTournamentOver( true );
			}
			else
			{
				theFinal =  new Fixture( theFinal.getCompetitorTwo() , theFinal.getCompetitorOne());
				Fixture[] f = { theFinal} ;
				tournamentFinals[ 1 ] = new Round( f  );
			}
			
		}
		else if ( !isTournamentOver() && !tournamentFinals[ 1 ].hasDraw() )
		{
			Fixture theFinal = tournamentFinals[ 0  ].getFixtures()[ 0 ];
			
			setTournamentWinner( theFinal.getWinner() );
			setTournamentOver( true );
		}
		else
			JOptionPane.showMessageDialog(null, "Tournament is Over" );
		
		
	}

	public void coalateRoundResults() throws MoveToNextRoundException
	{
		if ( !isMajorBracket()) 	
		{
			Competitor[] wLosers = getCurrentWinnersBracketRound().getLosers();
			Competitor[] lWinners =  getCurrentMinorBracketRound().getWinners();
			Fixture[] fixtures = new Fixture[ wLosers.length / 2];
			
			for ( int i = 0 ; i < fixtures.length ; i = i+ 2 )
				fixtures[ i ] = new Fixture( wLosers[ i ] , lWinners[ i ] );
			
			setCurrentMajorBracketRound(fixtures);
			
		}
		else if ( getCurrentMajorBracketRound().isComplete() )
		{
			Competitor[] losBracketWinners = getCurrentMajorBracketRound().getWinners();
			Competitor[] wBracketWinners = getCurrentWinnersBracketRound().getWinners();
			
			if( losBracketWinners.length ==  1 )
			{
				Fixture[] temp = null ;
				temp = new Fixture[ 1 ];
				temp[ 0 ] = new Fixture( losBracketWinners[0] , wBracketWinners[0]);
				tournamentFinals[ 0 ] = new Round ( temp );
			}
			else
			{
				setCurrentRoundNum( getCurrentRoundNum() + 1 );
				Fixture[] losBracketFixtures = new Fixture[ losBracketWinners.length / 2];
				Fixture[] wBracketFixtures = new Fixture[ losBracketFixtures.length 	];
				
				for ( int i =  0 ; i < losBracketFixtures.length  ; i = i + 2 )
				{
					losBracketFixtures[ i ] = new Fixture( losBracketWinners[ i ], 
							losBracketWinners[ i + 1 ]);
					wBracketFixtures[ i ] = new Fixture( wBracketWinners[ i ], 
							wBracketWinners[ i + 1 ]);
				}
			}
			
		}
		else
			throw new MoveToNextRoundException( "There are incomplete results or ties" 	);
	}

	private void setCurrentMajorBracketRound(Fixture[] fixtures)
	{
		if ( getCurrentRoundNum() < losersMajorRounds.length )
			losersMajorRounds[ getCurrentRoundNum() ] = new Round( fixtures );
	}

	public boolean isTournamentOver()
	{
		return tournamentOver;
	}

	private void setTournamentOver(boolean tournamentOver)
	{
		this.tournamentOver = tournamentOver;
	}
	
	public Round getCurrentMinorBracketRound()
	{
		if ( getCurrentRoundNum() < losersMinorRounds.length  && 
			 getCurrentRoundNum() > 0 )
			return losersMinorRounds[ getCurrentRoundNum() ] ;
		else
			return null ;
	}
	
	public Round getCurrentMajorBracketRound()
	{
		if ( getCurrentRoundNum() < losersMajorRounds.length  && 
				 getCurrentRoundNum() > 0 )
				return losersMajorRounds[ getCurrentRoundNum() ] ;
		return null ;
	}
	
	public Round getCurrentWinnersBracketRound()
	{
		if ( getCurrentRoundNum() < winnerBracketRounds.length  && 
				 getCurrentRoundNum() > 0 )
				return getCurrentWinnersBracketRound() ;
		return null ;
	}

	
	public Round getCurrentRound()
	{
		Fixture[] allRoundFixtures = null ;
		
		if ( getCurrentRoundNum() < winnerBracketRounds.length )
		{
			Fixture[] wFixtures = getCurrentWinnersBracketRound().getFixtures();
			Fixture[] minorFixtures = getCurrentMinorBracketRound().getFixtures();
			
			int factor = isMajorBracket() ? getCurrentMajorBracketRound().getFixtures().length * 3  :
				getCurrentRoundNum() > 0 ? getCurrentMajorBracketRound().getFixtures().length * 2 : 
				getCurrentWinnersBracketRound().getFixtures().length;
				
			allRoundFixtures = new Fixture[ factor ];
			
			int num = 0 ;
			for( int i  = 0 ; i < wFixtures.length ; i++ )
			{
				allRoundFixtures[ num ] = wFixtures[ i ]; 
				num++ ;
			}
			
			if ( getCurrentRoundNum() > 0 )
			{
				for( int i  = 0 ; i < minorFixtures.length ; i++ )
				{
					allRoundFixtures[ num ] = minorFixtures[ i ]; 
					num++;
				}
				if ( isMajorBracket() )
				{
					Fixture[] majorFixtures = getCurrentMajorBracketRound().getFixtures();
					
					for( int i  = 0 ; i < majorFixtures.length ; i++ )
					{
						allRoundFixtures[ num ] = majorFixtures[ i ]; 
						num++;
					}
				}
			}
				
		}
		
		else if ( tournamentFinals[0 ] != null  )
			allRoundFixtures =  tournamentFinals[ 0 ].getFixtures();
		
		return new Round( allRoundFixtures ) ;
		
	}

	@Override
	public Competitor getWinner()
	{
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
