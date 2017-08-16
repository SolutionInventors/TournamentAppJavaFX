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

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
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
	
	private List<Round> roundList ;
	private final List < Fixture> tieList;
	private  List < Fixture > activeTies;
	private final boolean AWAY;
	
	public SingleEliminationTournament( 
			Competitor[] comps , boolean away) throws TournamentException
	{
		super(comps);
		
		roundList = new ArrayList<>();
		AWAY = away;
		createTounament();
		tieList = new ArrayList<>() ;
		activeTies = new ArrayList<>();
		
	}
	
	public SingleEliminationTournament(Competitor[] comps) throws TournamentException
	{
		this( comps , false );
	}
	

	private void createTounament()
	{
		Competitor[] tempComps = getCompetitors();
		
		Fixture[] fixtures = new Fixture [ tempComps.length / 2 ];
		
		for( int i = 0  ; i < tempComps.length ; i+=2 )
		{
			fixtures[ i/2 ] = new Fixture( tempComps[ i ] , tempComps[ i +1 ] ) ;
			
		}
		
		roundList.add( new Round( fixtures )	) ;
		
		if ( hasAway() )
			roundList.add( new Round( fixtures ).invertHomeAndAway() );
	}

	
	public boolean hasAway()
	{
		return AWAY ; 
	}
	
	@Override
	public Round getCurrentRound()
	{
		return roundList.get( getCurrentRoundNum() );
	}
	
	
	@Override
	public Round[] getRoundArray()
	{
		return roundList.toArray( new Round[ roundList.size() ] );
	}

	@Override
	public void moveToNextRound() 
			throws TournamentEndedException, MoveToNextRoundException 
	{
		if( !hasEnded() && getCurrentRound().isComplete())
		{	
			if ( hasAway() 	)
			{
				if( getActiveCompetitors().length == 2 )//it is the final
				{
					Fixture fix = getCurrentRound().getFixtures()[ 0 ];
					
					if ( fix.getCompetitorOneScore() > fix.getCompetitorTwoScore() )
						eliminateLoser( fix.getCompetitorTwo());
					else
						eliminateLoser(fix.getCompetitorOne());
				}
				else if ( getCurrentRoundNum() % 2 == 0 ) //is first leg
					incrementRoundNum();
				else //second leg fixture
				{
					createNextRound();
				}
			}
			else
			{
				if ( !getCurrentRound().hasDraw()) // no ties
				{
					createNextRound();
				}
				else
					throw new MoveToNextRoundException(  "Some fixtures are incomplete");
			}
		}
		else
			throw new TournamentEndedException();
		
	}

	public void createNextRound()
	{
		
		eliminateLosers();
		incrementRoundNum();
		Competitor[] comps = getActiveCompetitors();
		
		if( !hasEnded() )
		{
			Fixture[] fixtures = new Fixture[ comps.length / 2];
			
			for( int i = 0 ; i < comps.length ; i+= 2  )
			{
				fixtures[ i/2 ] = new Fixture( comps[ i ] , comps[ i+1 ] );
				
			}
			
			roundList.add( new Round( fixtures ));
			if( hasAway() && getActiveCompetitors().length > 2 	)
				roundList.add( new Round( fixtures ).invertHomeAndAway() );
		}
	}

	private void eliminateLosers()
	{
		
		if ( !hasAway() )
		{
			Arrays.stream(getCurrentRound().getFixtures() )
				.forEach(f-> 
				{
					if ( f.getCompetitorOneScore() > f.getCompetitorTwoScore() )
					{
						f.getCompetitorTwo().setEliminated( true );
					}
					else
					{
						f.getCompetitorOne().setEliminated( true );
					}
					
				});
		}
		else
		{
			List<Fixture > firstLeg = new ArrayList<>();
			List<Fixture > secondLeg = new ArrayList<>();
			
			
			firstLeg.addAll( Arrays.asList(getRound( 
					getCurrentRoundNum()).getFixtures() ) );
			secondLeg.addAll( Arrays.asList(getCurrentRound().getFixtures() ) );
			
			for (int i = 0 ; i <  firstLeg.size() ; i++ )
			{
				Competitor com1 = firstLeg.get(i).getCompetitorOne();
				Competitor com2 = firstLeg.get(i).getCompetitorTwo();
				
				double totalComOneScore = firstLeg.get(i).getCompetitorOneScore() +
						secondLeg.get(i).getCompetitorTwoScore();
				
				double totalComTwoScore = firstLeg.get(i).getCompetitorTwoScore() +
						secondLeg.get(i).getCompetitorOneScore();
				
				if ( totalComOneScore > totalComTwoScore )
					eliminateLoser( com1 );
				else if ( totalComOneScore <totalComTwoScore )
					eliminateLoser( com2 );
				else
				{
					if ( com1.getAwayGoal(com2 ) >com2.getAwayGoal(com1 ) )
						eliminateLoser(com2);
					else
						eliminateLoser(com1);
				}
				
			}
		}
	}

	
	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
			return getActiveCompetitors()[ 0 ];
		return null ;
	}
	
	
	@Override
	public void setResult(Competitor com1,
			double score1, double score2, Competitor com2) throws NoFixtureException
	{
		if ( !hasEnded() )
		{
			if ( activeTies.stream().anyMatch( f->f.hasFixture(com1, com2) ))
			{
				for( int i = 0 ; i< activeTies.size() ; i++ )
					if ( activeTies.get(i ).hasFixture(com1, com2))
					{
						activeTies.remove( i ) ;
						break;
					}
			}
			if( hasAway() )
			{
				if ( getActiveCompetitors().length == 2 && score1 != score2 )
				{
					getCurrentRound().setResult(com1, score1, score2, com2);
				}
				else if ( getCurrentRoundNum() %2 == 0 )
					getCurrentRound().setResult(com1, score1, score2, com2);
				else
				{
					double totalComOneScore = score1 + com1.getHeadToHeadScore( com2 );
					double totalComTwoScore = score2 + com2.getHeadToHeadScore( com1 );
					
					if ( totalComOneScore != totalComTwoScore )
					{
						getCurrentRound().setResult( com1, score1, score2, com2);
					}
					else if ( (totalComOneScore == totalComTwoScore ) && 
							(com1.getAwayGoal(com2 ) != score2 ) )
					{
						getCurrentRound().setResult( com1, score1, score2, com2);
					}
					else
					{
						addToTieList( com1 , score1 , score2 , com2 );
					}
				}
			}
			else
			{
				if ( score1 != score2 )
					getCurrentRound().setResult(com1, score1, score2, com2);
				else
					addToTieList(com1, score1, score2, com2);
			}	
		}
		else
			throw new TournamentEndedException( "Tournament is over" );
		
	}

	private void eliminateLoser(Competitor com1)
	{
		Competitor[] comps = getActiveCompetitors();
		
		if ( Arrays.stream( comps ).anyMatch( c->Competitor.isEqual(c, com1 ) ) )
		{
			Arrays.stream( comps )
			.filter( c->Competitor.isEqual(c, com1 ) )
			.forEach( c -> c.setEliminated( true ) ) ;
		}
	}

	private void addToTieList(Competitor com1, double score1, double score2, Competitor com2)
	{
		Fixture fixture = new Fixture( com1 , com2 );
		
		fixture.setResult(score1, score2, false );
		tieList.add( fixture );
		activeTies.add( fixture );
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
