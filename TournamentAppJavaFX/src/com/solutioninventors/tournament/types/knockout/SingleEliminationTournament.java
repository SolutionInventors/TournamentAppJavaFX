/**
 *@Author: Oguejiofor Chidiebere
 *SingleEliminationTournament.java
 *Aug 7, 2017
 *2:38:15 PM
 */
package com.solutioninventors.tournament.types.knockout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.JOptionPane;

import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;

public class SingleEliminationTournament extends EliminationTournament
{
	/**
	 * this class simulates a single elimination tournament
	 * The class constructor throwa a TournamentException if the number of competitors
	 * inputs is not a power of 2 
	 * Utility method createTournament creates the tournament's first fixture
	 * 
	 * The class then gets the result via calls to method setResult
	 * 
	 * 
	 * Then method moveToNextRound eliminates the losers in the currentRound and
	 * increment the roundNum. If there are ties the roundNumber remains unchanged
	 * 
	 * The method contains other services required in an elimination tournament
	 * 
	 *  
	 * Note: that the first round's number = 0 
	 */
	private final Round[] ROUNDS;
	
	private final List < Fixture> tieList;
	private  List < Fixture > activeTies;
	
	public SingleEliminationTournament(Competitor[] comps) throws TournamentException
	{
		super(comps);
		double validator = ( Math.log( getCompetitors().length ) )/
							( Math.log( 2 ) );
		if ( ! ( validator % 1 == 0.0f)  ) //the number is valid 
			throw new TournamentException( "The number of Competitors must be a power of 2 " );
		
		ROUNDS = new Round[ (int) Math.sqrt(  getCompetitors().length )  ];
		createTounament();
		tieList = new ArrayList<>() ;
		activeTies = new ArrayList<>();
	}
	
	private void setCurrentRound(Round round)
	{
		ROUNDS[ getCurrentRoundNum() ] = round ;
		
	}

	@Override
	public Round getCurrentRound()
	{
		return ROUNDS[  getCurrentRoundNum() ];
	}
	
	
	@Override
	public Round[] getRoundArray()
	{
		return ROUNDS;
	}
	

	private void createTounament()
	{
		Competitor[] tempComps = getCompetitors();
		
		Fixture[] fixtures = new Fixture [ tempComps.length / 2 ];
		
		for( int i = 0  ; i < tempComps.length ; i+=2 )
		{
			fixtures[ i/2 ] = new Fixture( tempComps[ i ] , tempComps[ i +1 ] ) ;
			
		}
		
		setCurrentRound( new Round( fixtures ) 	) ;
		
	}

	
	

	@Override
	public void moveToNextRound() throws TournamentEndedException 
	{
		if( !hasEnded() )
		{	
			if ( getCurrentRound().isComplete() )
			{
				if ( !getCurrentRound().hasDraw() )
				{
					eliminateLosers();
					setCurrentRoundNum( getCurrentRoundNum() + 1 );
					Competitor[] comps = getActiveCompetitors();
					if( !hasEnded() )
					{
						Fixture[] fixtures = new Fixture[ comps.length / 2];
						
						for( int i = 0 ; i < comps.length ; i+= 2  )
						{
							fixtures[ i/2 ] = new Fixture( comps[ i ] , comps[ i+1 ] );
							
						}
						
						setCurrentRound( new Round( fixtures ));
					}
				}
			}
			else
				JOptionPane.showMessageDialog(null , "Some fixtures are incomplete");
		}
		else
			throw new TournamentEndedException();
		
	}

	private void eliminateLosers()
	{
		Arrays.stream( getCompetitors() )
			.filter( c -> c.getNumberOfLoss() >= 1 && !c.isEliminated() )
			.forEach( c -> c.setEliminated( true ) );
		
	}

	
	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
			return getActiveCompetitors()[ 0 ];
		return null ;
	}
	
	
	@Override
	public void setResult(Competitor com1, double score1, double score2, Competitor com2)
	{
		Fixture[] fixes = getCurrentRound().getFixtures() ;
		
		Predicate<Fixture> tester = f -> f.getCompetitorOne().getName().equals( com1.getName() ) &&
				f.getCompetitorTwo().getName().equals( com2.getName() ) ;
		
		if ( Arrays.stream( fixes)
				.anyMatch( tester) ) //fixture is present
		{
			Fixture theFixture = Arrays.stream( fixes )
								  .filter( tester ).findFirst().get();
			if ( score1 == score2)
			{
				Fixture temp = new Fixture( theFixture.getCompetitorOne() , 
						theFixture.getCompetitorTwo());
				temp.setResult(score1, score2, false );
				
				if( !activeTies.stream().anyMatch(tester ))
				{
					activeTies.add( temp );
				}
				
				
				tieList.add(temp);
			}
			else
			{
				Arrays.stream( fixes )
				.filter( tester )
				.forEach( f ->  f.setResult(score1, score2));
				if ( hasTie() && activeTies.stream().anyMatch( tester) )
					for ( int i = 0 ; i < activeTies.size() ; i ++ )
					{
						if ( activeTies.get( i ).getCompetitorOne().getName().equals( com1.getName()) &&
							 activeTies.get( i ).getCompetitorTwo().getName().equals(com2.getName() ) )
						{
							activeTies.remove( i );
							break;
						}
					}
			}
		}
		else
			JOptionPane.showMessageDialog(null , "(Replace with Javafx modal dialog)\nFixture doesn't exist" );
		
	}

	public  boolean hasTie()
	{
		return activeTies.size() == 0 ? false : true ;
	}

	public Fixture[] getActiveTies()
	{
		if ( activeTies.size() <= 0 )
			return null;
		
		Fixture[] fixes = new Fixture[ activeTies.size() ];
		activeTies.toArray( fixes );
		return fixes ;
	}
	
	public Fixture[] getTournamentTies()
	{
		if ( tieList.size() <= 0 )
			return null;
		
		Fixture[] fixes = new Fixture[ tieList.size() ];
		return tieList.toArray( fixes ) ;
		
	}
	

	public String toString()
	{
		String message = null ;
		
		switch( getActiveCompetitors().length )
		{
		
		case 1: 
			message = "Tournament Ended: \nWinner is " + getWinner() ;
			break ;
		case 2 :
			message = "Final" ;
			break ;
		case  4:
			message = "Semifinal";
			break;
		case 8:
			message = "Quarter-Final";
			break;
		case 16 :
			message = "Round of 16";
			break;
		default:
			message = "Round " + ( getCurrentRoundNum() +  1) ;
		}
		return message ;
	}

	@Override
	public boolean hasEnded()
	{
		return getActiveCompetitors().length == 1 ? true : false ;
	}

}
