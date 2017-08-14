/**
 *@Author: Oguejiofor Chidiebere
 *Multistage.java
 *Aug 11, 2017
 *5:16:28 PM
 */
package com.solutioninventors.tournament.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.solutioninventors.tournament.exceptions.GroupIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.types.group.InvalidBreakerException;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.types.group.StandingTable;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.types.knockout.EliminationTournament;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

public class Multistage extends Tournament
{

	private GroupTournament[] groupStage;
	private EliminationTournament knockoutStage;
	
	private final boolean groupAwayMatches;
	private final boolean knockoutAwayMatches;
	
	private final double WIN_POINT;
	private final double DRAW_POINT;
	private final double LOSS_POINT;
	
	private  StandingTable extraQualifierTable;
	private final int NUMBER_OF_EXTRA_QUALIFIERS ;
	
	private final GroupWinners numberOfGroupWinners;
	private final GroupStageType groupType;
	
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound,  boolean knockoutHomeAndAway) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type, pWin , pDraw ,pLoss , breaker, numOfGroupRound , false , knockoutHomeAndAway,
				GroupStageType.SWISS );
		
	}
	
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , boolean groupHomeAndAway , boolean knockoutHomeAndAway) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type , pWin , pDraw ,pLoss, breaker , 
				0 , groupHomeAndAway ,knockoutHomeAndAway , GroupStageType.ROUND_ROBIN 	 );
		
	}

	public int calculateExtraQualifiers(int totalCompetitors)
	{
		int totalQualifiers = 1 ;
		int totalFirstTwo = totalCompetitors/2 ;
		while( totalQualifiers <  totalFirstTwo)
			totalQualifiers *= 2 ;
		
		int factor = totalQualifiers - totalFirstTwo;
		return factor;
	}

	private Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound, boolean groupHomeAndAway,
			boolean knockoutHomeAndAway , GroupStageType groupStageType ) 
					throws TournamentException, InvalidBreakerException
	{
		super(coms);
		groupAwayMatches = groupHomeAndAway;
		knockoutAwayMatches = knockoutHomeAndAway ;
		WIN_POINT = pWin ;
		DRAW_POINT = pDraw;
		LOSS_POINT = pLoss ;
		
		if( coms.length % 4 != 0 )
			throw new TournamentException("The total competitors must be a multiple of 4 " );
		
		NUMBER_OF_EXTRA_QUALIFIERS = calculateExtraQualifiers(coms.length);;
		
		if ( NUMBER_OF_EXTRA_QUALIFIERS > coms.length / 2 )
			numberOfGroupWinners = GroupWinners.FIRST_THREE;
		else
			numberOfGroupWinners = GroupWinners.FIRST_TWO;
		groupType = groupStageType ;
		
		createTournament( type , breaker , numOfGroupRound );
	}
	
	private void createTournament( SportType type , TieBreaker breaker , int numOfGroupRound ) 
			throws InvalidBreakerException, TournamentException
	{
		if ( groupType == GroupStageType.ROUND_ROBIN )
			groupStage = new RoundRobinTournament[ getCompetitors().length / 4 ] ;
		else
			groupStage = new SwissTournament[getCompetitors().length / 4 ];
		
		Competitor[] theCompetitors = getCompetitors();
		Competitor[] compPerGroup = new Competitor[ 4 ];
		
		for ( int i = 0 ; i < theCompetitors.length ; i+= 4 )
		{
			compPerGroup [ 0 ] = theCompetitors[ i ];
			compPerGroup [ 1 ] = theCompetitors[ i + 1 ];
			compPerGroup [ 2 ] = theCompetitors[ i + 2 ];
			compPerGroup [ 3 ] = theCompetitors[ i + 3 ];
			
			if ( groupType == GroupStageType.ROUND_ROBIN)
				groupStage[ i/4 ] = new RoundRobinTournament(compPerGroup, type ,getWinPoint(), 
						getDrawPoint(), getLossPoint(), breaker, hasGroupAwayMatches() );
			else
				groupStage[ i/4 ] = new SwissTournament( compPerGroup, type ,getWinPoint(), 
						getDrawPoint(), getLossPoint(), breaker, numOfGroupRound );
		}
	}

	@Override
	public Round[] getRoundArray()
	{
		List< Round > roundList = new ArrayList<Round>();
		
		int totalRounds = numberOfGroupStageRounds() + 
						  knockoutStage.getRoundArray().length ;
		
		int factor = getCurrentRoundNum() < totalRounds ? getCurrentRoundNum() :
						totalRounds -1 ;
		
		for( int i = 0 ; i <=factor ; i++ )
		{
			try
			{
				roundList.add( getRound( i ) );
			}
			catch (RoundIndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
		}
				
		if ( roundList.size() == 0 )
			return null;
		return roundList.toArray( new Round[ roundList.size() ] );
		
		
	}

	public Round getRound( int roundNum ) throws RoundIndexOutOfBoundsException
	{
		Round round = null ; 
		if ( roundNum >= 0 && roundNum < numberOfGroupStageRounds() )
		{
			ArrayList< Fixture > list = new ArrayList<>();
			
			for( int groupNum = 0 ; groupNum < groupStage.length ; groupNum++ )
			{
				list.addAll( Arrays.asList( groupStage[ groupNum ]
						.getRound( roundNum ).getFixtures()));
			}
			
			round = new Round( list.toArray( new Fixture[ list.size() ] ) );
		}
		else if ( roundNum >= 0 && roundNum - numberOfGroupStageRounds() < 
				knockoutStage.getRoundArray().length )
		{
			round = knockoutStage.getRound( roundNum - numberOfGroupStageRounds() );
		}
		else
		{
			throw new RoundIndexOutOfBoundsException( "The index number :" + roundNum + " is invalid" );
		}
		
		return round ; 
		
	}
	@Override
	public void moveToNextRound() throws MoveToNextRoundException
	{
		if ( hasEnded() )
			throw new MoveToNextRoundException("The tournament has ended" );
		
		if ( (getCurrentRoundNum() < numberOfGroupStageRounds())) 
		{
			updateTable() ;	
			for( int i = 0 ; i < groupStage.length ;i++ )
					groupStage[ i ].moveToNextRound();
			incrementRoundNum();
			if ( getCurrentRoundNum() == numberOfGroupStageRounds())
			{
				createKnockoutStage();		
			}
		}
		else
		{
			
			if ( knockoutStage.getCurrentRound().isComplete() )
			{
				knockoutStage.moveToNextRound(); 
				incrementRoundNum();
			}
			
		}
			
		
	}

	public int getNumberOfGroupRounds()
	{
		try
		{
			return getGroup( 0 ).getRoundArray().length ;
		}
		catch (GroupIndexOutOfBoundsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0 ;
	}
	public void createKnockoutStage() throws MoveToNextRoundException
	{
		List<Competitor> allQualifiers = new ArrayList<Competitor> ();
		
		if ( extraQualifierTable != null )
		{
			Competitor[] extraQualifiers = new Competitor[ getNumberOfExtraQualifiers() ] ;
			Competitor[] possibleQualifiers = extraQualifierTable.getCompetitors();
			
			
			for ( int i = 0 ; i < extraQualifiers.length ; i++ )
			{
				extraQualifiers[ i ] = possibleQualifiers[ i ];
			}
			allQualifiers.addAll( Arrays.asList( extraQualifiers ) );
			
		}
		
		List<Competitor> groupWinners = Arrays.asList( getGroupWinners() );
		
		List< Competitor > finalOutput 
			= new ArrayList<>( groupWinners.size() ); 
		
//				This loop ensures that two competitors from the same group don't meet in the knock-out stage
		for( int i = 0 ; i < groupWinners.size() /2 ; i++ )
		{
			finalOutput.add( groupWinners.get( i ) ) ;
			finalOutput.add( groupWinners.get( groupWinners.size() -1 - i ) );
		}

		allQualifiers.addAll( finalOutput );
		
		try
		{
			knockoutStage = new SingleEliminationTournament(
					allQualifiers.toArray( 
							new Competitor[ allQualifiers.size() ]) , 
							hasKnockoutAwayMatches() );
		}
		catch (TournamentException e)
		{
			throw new 
				MoveToNextRoundException("Error in entering knockout stage" );
		}
	}

	public Competitor[] getGroupWinners()
	{
		List<Competitor> list = new ArrayList<>( 
				numberOfGroupWinners.getNumberOfWinners() * groupStage.length );
		
		for( int i = 0 ; i< groupStage.length ; i++ )
		{
			StandingTable groupTable = groupStage[ i].getTable() ;
			
			groupTable.updateTables();
			Competitor[] competitors = groupTable.getCompetitors();
			
			for( int position = 0 ; position < numberOfGroupWinners.getNumberOfWinners() ;
					position++ )
			{
				list.add( competitors[ position ] 	);
			}
			
		}
		return list.toArray( new Competitor[ list.size() ] );
	}

	public String getCurrentStage()
	{
		if ( getCurrentRoundNum() < numberOfGroupStageRounds()  )
			return "Group Stage";
		return "Knockout Stage";
	}
	public int numberOfGroupStageRounds()
	{
		return groupStage[ 0 ].getRoundArray().length;
	}

	private void updateTable()
	{
		if ( getNumberOfExtraQualifiers() > 0 )
		{
			Competitor[] possibleQualifiers = new Competitor[ groupStage.length ];
			TieBreaker tournamentBreaker = groupStage[ 0 ].getTable().getTieBreaker();
			
			for ( int i = 0 ; i < groupStage.length ; i ++ )
			{
				possibleQualifiers[ i ] = 
						groupStage[ i ].getTable().getCompetitor(getNumberOfExtraQualifiers());
			}
			
			
			extraQualifierTable = new StandingTable(groupStage[0].getSportType(), possibleQualifiers, 
					getWinPoint(), getDrawPoint(), getLossPoint(), tournamentBreaker);
		}
	}

	
	public Round[] getGroupRoundArray( int groupNum )
	{
		return groupStage[ groupNum ].getRoundArray() ; 
	}
	
	
	@Override
	public void setResult(Competitor com1, double score1, 
			double score2, Competitor com2) throws NoFixtureException
	{
		boolean found = false ; 
		
		if ( !isGroupStageOver() )
		{
			int num = 0 ;
			while( num < groupStage.length && !found )
			{
				try
				{
					groupStage[ num ].setResult(com1, score1, score2, com2);
					found = true ;
				}
				catch (NoFixtureException e)
				{
					num++ ;
				}
			}
			
		}
		else
			knockoutStage.setResult(com1, score1, score2, com2);

	}

	@Override
	public boolean hasEnded()
	{
		if ( knockoutStage != null )
			return getCurrentRoundNum() < numberOfGroupStageRounds() +
							knockoutStage.getRoundArray().length ? false : true ;
		return false ;
	}

	@Override
	public Round getCurrentRound() 
	{
		try
		{
			return getRound( getCurrentRoundNum() );
		}
		catch (RoundIndexOutOfBoundsException e)
		{
			e.printStackTrace();
		}
	
		return null ;
	}
	
	public Competitor[] getActiveCompetitors()
	{
		if ( isGroupStageOver() )
			return knockoutStage.getActiveCompetitors();
		return getCompetitors();
	}
	
	
	@Override
	public Competitor getWinner()
	{
		if ( hasEnded() )
			return knockoutStage.getWinner();
		return null;
	
	}

	
	public boolean hasGroupAwayMatches()
	{
		return groupAwayMatches;
	}

	public boolean hasKnockoutAwayMatches()
	{
		return knockoutAwayMatches;
	}

	public double getWinPoint()
	{
		return WIN_POINT;
	}

	public double getDrawPoint()
	{
		return DRAW_POINT;
	}

	public double getLossPoint()
	{
		return LOSS_POINT;
	}

	public boolean isGroupStageOver()
	{
		return knockoutStage == null ? false : true ;
	}

	public int getNumberOfExtraQualifiers()
	{
		return NUMBER_OF_EXTRA_QUALIFIERS;
	}
	
	public GroupTournament getGroup( int groupNum ) throws GroupIndexOutOfBoundsException
	{
		if ( groupNum < groupStage.length )
			return groupStage[ groupNum ];
		throw new GroupIndexOutOfBoundsException( "The group num is invalid") ;
	}
	public int getNumberOfGroups()
	{
		return groupStage.length ;
	}
	
	public StandingTable getPossibleQualifierTable()
	{
		return extraQualifierTable;
	}
	
	@Override
	public String toString()
	{
		if ( isGroupStageOver() )
			return knockoutStage.toString();
		return groupStage[0 ].toString();
		
	}
	private enum GroupWinners
	{
		/** 
		 * This enum encapsulates info about how the group winners would be chosen
		 * If the extra qulifier is taken from position 4 then the winners are
		 * FIRST_THREE 
		 * If the extra qualifier is taken from position 3 the the group winners are
		 * the FIRST_TWO
		 */
		FIRST_THREE( 3 ) ,
		FIRST_TWO( 2 ) 	 ;
		private final int numOfWinners ; 
		
		private GroupWinners( int pos )
		{
			numOfWinners = pos ;
		}
		
		public int getNumberOfWinners( )
		{
			return numOfWinners;
		}

	}
	
	private enum GroupStageType
	{
		SWISS , ROUND_ROBIN;
	}
}
