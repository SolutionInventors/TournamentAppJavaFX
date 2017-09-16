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

import com.solutioninventors.tournament.GUI.controller.KnockoutScreenController;
import com.solutioninventors.tournament.exceptions.GroupIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.InvalidBreakerException;
import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.RoundIndexOutOfBoundsException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.group.GroupTournament;
import com.solutioninventors.tournament.types.group.RoundRobinTournament;
import com.solutioninventors.tournament.types.group.StandingTable;
import com.solutioninventors.tournament.types.group.SwissTournament;
import com.solutioninventors.tournament.types.knockout.DoubleElimination;
import com.solutioninventors.tournament.types.knockout.EliminationTournament;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;
import com.solutioninventors.tournament.utils.TieBreaker;

/**
 * This class is used in creating a Multistage Tournament 
 * This class contains a collection of {@link GroupTournament}s which simulates the group rounds
 * and a {@code EliminationTournament} that simulates the knock out stage.
 * 
 * On initialization the constructor determines if there would be a fourth or third place ranking table
 * The constructor validates that the total competitors is a multiple of 4 
 * The constructor also creates the Tournament via a call to createTournament
 * The public constructors either creates a Robin groupStage or Swiss groupStage
 * 
 * This table is updated after every group round 
 * Thus this class contains a StandingTable object that is used to create this feature
 * 
 * This class provides some services required to manage the operations of this class
 */


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
	private final KnockoutType knockoutType;
	
	/**
	 * Creates this {@code Multistage} {@link Tournament} whose group stage would be a {@link SwissTournament} style and 
	 * its knokoutStage would be a {@link SingleElimination}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@param coms - the {@code Competitor}s that would be in this {@code Multistage} tournament
	 *@param type - the {@code SportType} of the  {@code Multistage} 
	 *@param pWin - the point for win in the group stage
	 *@param pDraw - the point for a draw in the group stage
	 *@param pLoss- the point for a loss in the group stage
	 *@param breaker - a list of {@link TieBreaker}s that would be used when breaking ties in the group stage
	 *@param numOfGroupRound - the number of rounds that the {@link SwissTournament } would have
	 *@param knockoutHomeAndAway - true indicates that the {@link SingleElimination} would be home and away.
	 *@throws TournamentException - when invalid parameters are passed as argument
	 *@throws InvalidBreakerException- when the {@link  TieBreaker} is invalid 
	 */
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound,  boolean knockoutHomeAndAway) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type, pWin , pDraw ,pLoss , breaker, numOfGroupRound , false , knockoutHomeAndAway,
				GroupStageType.SWISS, KnockoutType.SINGLE );
		
	}
	
	/**
	 * Creates a {@code Multistage} with the groupStage played as a {@link SwissTournament}  and 
	 * the knock-out stage played as a {@link DoubleElimination}
	  * @author Oguejiofor Chidiebere
	 * @since v1.0
	  *@param coms - the {@code Competitor}s that would be in this {@code Multistage} tournament
	 *@param type - the {@link SportType} of the  {@code Multistage} 
	 *@param pWin - the point for win in the group stage
	 *@param pDraw - the point for a draw in the group stage
	 *@param pLoss- the point for a loss in the group stage
	 *@param breaker - a {@link TieBreaker}s that would be used when breaking ties in the group stage
	 *@param numOfGroupRound - the number of rounds that the {@link SwissTournament } would have
	 *@throws TournamentException
	 *@throws InvalidBreakerException
	 */
	
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type, pWin , pDraw ,pLoss , breaker, numOfGroupRound , false , false,
				GroupStageType.SWISS, KnockoutType.DOUBLE );
		
	}
	
	/**
	 * Creates a {@code Multistage} with the group stage played as a {@link RoundRobin} and 
	 * the knock-out stage played as {@link SingleElimination}
	 *  @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@param coms - the {@code Competitor}s in the {@code Multistage}
	 *@param type - the {@link SportType} of this {@code Multistage}
	 *@param pWin - the point for win in the group stage
	 *@param pDraw - the point for a draw in the group stage
	 *@param pLoss- the point for a loss in the group stage
	 *@param breaker - a  {@link TieBreaker}s that would be used when breaking ties in the group stage
	 *@param knockoutHomeAndAway - indicates whether the knockout stage would be home and away
	 *@param knockoutHomeAndAway indicates if the group stage would be home and away when set to {@code true}
	 *@throws TournamentException - when any parameter is invalid
	 *@throws InvalidBreakerException when the {@link TieBreaker} is invalid
	 */
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , boolean groupHomeAndAway , boolean knockoutHomeAndAway) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type , pWin , pDraw ,pLoss, breaker , 
				0 , groupHomeAndAway ,knockoutHomeAndAway , GroupStageType.ROUND_ROBIN, KnockoutType.SINGLE 	 );
		
	}

	/**
	 * Creates a {@code Multistage} with the groupstage played as a {@link RoundRobin} and
	 * the lnockout stage played as a {@link DoubleElimination}. Note that 
	 * {@link DoubleElimination} knockout stage cannot be home and away and 
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@param coms - the {@code Competitor}s in the {@code Multistage}
	 *@param type - the {@link SportType} of this {@code Multistage}
	 *@param pWin - the point for win in the group stage
	 *@param pDraw - the point for a draw in the group stage
	 *@param pLoss- the point for a loss in the group stage
	 *@param breaker - a  {@link TieBreaker}s that would be used when breaking ties in the group stage
	 *@param groupHomeAndAway - indicates if the round robin would be home and away
	 *@throws TournamentException- when invalid parameter is passed as an argument
	 *@throws InvalidBreakerException when the {@link TieBreaker} is invalid
	 */
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , boolean groupHomeAndAway ) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type , pWin , pDraw ,pLoss, breaker , 
				0 , groupHomeAndAway ,false , GroupStageType.ROUND_ROBIN , KnockoutType.DOUBLE	 );
		
	}
	
	/**
	 * Used to create a {@code Multistage} with any specification. 
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 **@param coms - the {@code Competitor}s in the {@code Multistage}
	 *@param type - the {@link SportType} of this {@code Multistage}
	 *@param pWin - the point for win in the group stage
	 *@param pDraw - the point for a draw in the group stage
	 *@param pLoss- the point for a loss in the group stage
	 *@param breaker - a  {@link TieBreaker}s that would be used when breaking ties in the group stage
	 *@param groupHomeAndAway - indicates if the round robin would be home and away
	 *@param numOfGroupRound - indicates if the group stage would be home and away
	 *@param knockoutHomeAndAway- indicates if the group stage would be home and away
	 *@param groupStageType - indicates if the {@link GroupStageType} that is ROUND_ROUBIN or SWISS
	 *@param knockout - indicates if the {@link KnockoutType}  that is DOUBLE or SINGLE that
	 *@throws TournamentException
	 *@throws InvalidBreakerException
	 */
	private Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound, boolean groupHomeAndAway,
			boolean knockoutHomeAndAway , GroupStageType groupStageType, KnockoutType knockout ) 
					throws TournamentException, InvalidBreakerException
	{
		super(type, coms);
		groupAwayMatches = groupHomeAndAway;
		knockoutAwayMatches = knockoutHomeAndAway ;
		WIN_POINT = pWin ;
		DRAW_POINT = pDraw;
		LOSS_POINT = pLoss ;
		knockoutType =  knockout;
		
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
	
	
	private int calculateExtraQualifiers(int totalCompetitors)
	{
		int totalQualifiers = 1 ;
		int totalFirstTwo = totalCompetitors/2 ;
		while( totalQualifiers <  totalFirstTwo)
			totalQualifiers *= 2 ;
		
		int factor = totalQualifiers - totalFirstTwo;
		return factor;
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
	
	public boolean hasGroupStageEnded()
	{
		try
		{
			return getGroup( 0 ).hasEnded();
		}
		catch (GroupIndexOutOfBoundsException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean hasKnockoutStageEnded()
	{
		if ( knockoutStage != null )
			return knockoutStage.hasEnded();
		return false;
	}
	
	@Override
	public void moveToNextRound() throws MoveToNextRoundException, TournamentEndedException
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

	public EliminationTournament getKnockoutStage()
	{
		return knockoutStage;
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
			
			if ( knockoutType == KnockoutType.SINGLE )
				knockoutStage = new SingleEliminationTournament( getSportType(), 
						allQualifiers.toArray( 
								new Competitor[ allQualifiers.size() ]) , 
								hasKnockoutAwayMatches() );
			else
				knockoutStage = new DoubleElimination(getSportType(), allQualifiers.toArray( 
								new Competitor[ allQualifiers.size() ] ) ) ;
			
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
			double score2, Competitor com2) throws NoFixtureException, TournamentEndedException, ResultCannotBeSetException
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
//			return getCurrentRoundNum() < numberOfGroupStageRounds() +
//							knockoutStage.getRoundArray().length ? false : true ;
			return knockoutStage.hasEnded();
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
		 * This enumeration encapsulates info about how the group winners would be chosen
		 * If the extra qualifier is taken from position 4 then the winners are
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
	
	
	private enum KnockoutType
	{
		SINGLE , DOUBLE;
	}
}
