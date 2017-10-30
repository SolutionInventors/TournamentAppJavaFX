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
 * This class is used in creating a Multistage Tournament <p>

 * The total competitors in a multistage must be a multiple of 4 and must be
 * greater than 4 <br>
 * This class contains a collection of {@link GroupTournament}s which simulates the group rounds
 * and a {@code EliminationTournament} that simulates the knock out stage. Each group in the groupstage
 * would always have four competitors
 * <p>
 * On initialization the constructor determines if there would be a fourth-place; third place; or no; ranking table
 * The constructor validates that the total competitors is a multiple of 4 
 * This class provides constructors for creating a {@code Multistage } with any combination
 * @see Tournament
 * @author Oguejiofor Chidiebere
 * @since v1.0
 */


public class Multistage extends Tournament
{

	private static final long serialVersionUID = 1L;
	/**
	 * Contains the different groups in the group stage of the tournament.
	 */
	private GroupTournament[] groupStage;
	/**
	 * The knockoutstage of this {@code Multistage} tornament
	 */
	private EliminationTournament knockoutStage;
	
	/**
	 * {@code true } when the group stage has home and away fixtures
	 */
	private final boolean groupAwayMatches;
	
	private  final double WIN_POINT;
	private final double DRAW_POINT;
	private final double LOSS_POINT;
	
	/**
	 * {@code true } when the knockout stage has home and away fixtures 
	 * Can only be true if knockout stage is a {@code SingleElimination}
	 * @see SingleEliminationTournament
	 */
	private final boolean knockoutAwayMatches;
	
	/**
	 * The point for win in the groupStage
	 */

	/**
	 * Stores the third or fourth place ranking table
	 * It is set to null if such a table is not required
	 */
	private  StandingTable extraQualifierTable;
	/**
	 * Stores the number of extra qualifiers that would be gotten from
	 * third place or fourth place ranking table
	 */
	private final int NUMBER_OF_EXTRA_QUALIFIERS ;
	
	/**
	 * Stores the total number of winners in a group
	 */
	private final GroupWinners numberOfGroupWinners;
	/**
	 * Stores {@code GroupStageType} of this {@code Multistage} group stage
	 * @see GroupStageType
	 */
	private final GroupStageType groupType;
	/**
	 * Stores {@code KnockoutType} of this {@code Multistage} group stage
	 * @see KnockoutType
	 */
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
	 *@throws TournamentException when any argument is inputed
	 *@throws InvalidBreakerException when the TieBreaketr object contains invalid {@code Breaker}s
	 */
	
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , int numOfGroupRound) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type, pWin , pDraw ,pLoss , breaker, numOfGroupRound , false , false,
				GroupStageType.SWISS, KnockoutType.DOUBLE );
		
	}
	
	/**
	 * Creates a {@code Multistage} with the group stage played as a {@link com.solutioninventors.tournament.types.group.RoundRobinTournament} and 
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
	 *@param groupHomeAndAway {@code true} indicates that there would be home and away in this {@code Tournament} groupStage
	 *@throws TournamentException - when any parameter is invalid
	 *@throws InvalidBreakerException when the {@link TieBreaker} is invalid
	 *
	 */
	public Multistage(Competitor[] coms, SportType type , double pWin , double pDraw , 
			double pLoss , TieBreaker breaker , boolean groupHomeAndAway , boolean knockoutHomeAndAway) 
					throws TournamentException, InvalidBreakerException
	{
		this( coms , type , pWin , pDraw ,pLoss, breaker , 
				0 , groupHomeAndAway ,knockoutHomeAndAway , GroupStageType.ROUND_ROBIN, KnockoutType.SINGLE 	 );
		
	}

	/**
	 * Creates a {@code Multistage} with the groupstage played as a 
	 * {@link com.solutioninventors.tournament.types.group.RoundRobinTournament} and
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
	 *@throws TournamentException - when invalid parameter is passed as an argument
	 *@throws InvalidBreakerException when the {@link TieBreaker} is invalid
	 *@throws TournamentException when invalid arguments wore passed
	 
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
		
		knockoutType =  knockout;
		
		if( coms.length % 4 != 0 || 
				coms.length <= 4 )
			throw new TournamentException("The total competitors must be a multiple of 4 "
					+ "and must be greater than 4" );
		
		NUMBER_OF_EXTRA_QUALIFIERS = calculateExtraQualifiers(coms.length);;
		WIN_POINT = pWin;
		DRAW_POINT = pDraw;
		LOSS_POINT = pLoss;
		
		if ( NUMBER_OF_EXTRA_QUALIFIERS > coms.length / 2 )
			numberOfGroupWinners = GroupWinners.FIRST_THREE;
		else
			numberOfGroupWinners = GroupWinners.FIRST_TWO;
		groupType = groupStageType ;
		
		createTournament( type , breaker , numOfGroupRound );
	}
	
	
	/**
	 * This utility method calculates the number of extra qualifiers in this {@code Multistage}
	 * If the total number of groups is a power of 2 ( that is 4, 8, 16 etc) then this method
	 * returns zero<br>
	 * 
	 *@param totalCompetitors
	 *@return
	 */
	private int calculateExtraQualifiers(int totalCompetitors)
	{
		int totalQualifiers = 1 ;
		int totalFirstTwo = totalCompetitors/2 ;
		while( totalQualifiers <  totalFirstTwo)
			totalQualifiers *= 2 ;
		
		int factor = totalQualifiers - totalFirstTwo;
		return factor;
	}

	
	/** 
	 * Creates the group stage of this {@code Multistage} tournament based on 
	 * specifications. 
	 * @author Oguejiofor Chidiebere
	 *@param type the {@code SportType} of this {@code Multistage}
	 *@param breaker the {@code TieBreaker} to be used in the group stage
	 *@param numOfGroupRound the number of group rounds to be used when creating a {@link SwissTournament}
	 *@throws InvalidBreakerException when the {@code TieBreaker} is invalid for {@code GroupTournament}
	 *@throws TournamentException when an error occured while creating the {@code GroupTournament}
	 */
	private void createTournament( SportType type , TieBreaker breaker , int numOfGroupRound ) 
			throws InvalidBreakerException, TournamentException
	{
		if ( groupType == GroupStageType.ROUND_ROBIN )
			groupStage = new RoundRobinTournament[ getCompetitors().length / 4 ] ;
		else
			groupStage = new SwissTournament[getCompetitors().length / 4 ];
		
		Competitor[] theCompetitors = getCompetitors();
		
		for ( int i = 0 ; i < theCompetitors.length ; i+= 4 )
		{
			Competitor[] compPerGroup = new Competitor[ 4 ];
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
		
		int totalRounds = numberOfGroupStageRounds();
		
		if( isGroupStageOver() ) 
			totalRounds += knockoutStage.getRoundArray().length ;
		
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

	/**
	 * Gets the a {@code Round} object based on the round number.
	 * Note that when roundNum is 0 this method returns the first round
	 *@param roundNum the round number indexed 0 
	 *@return a {@code Round} object containing the specified {@code Round}}
	 *@throws RoundIndexOutOfBoundsException when the roundNum is invalid
	 */
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
			
			round = new Round( list.toArray( new Fixture[ list.size() ] ) , "Round " + (roundNum +1 ));
		}
		else if ( roundNum >= 0 && roundNum - numberOfGroupStageRounds() < 
				knockoutStage.getRoundArray().length )
		{
			round = knockoutStage.getRound( roundNum - numberOfGroupStageRounds() );
		}
		else if ( knockoutStage != null  && !knockoutStage.hasEnded() )
			try
			{
				round = knockoutStage.getCurrentRound();
			}
			catch (TournamentEndedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
		{
			throw new RoundIndexOutOfBoundsException( "The index number : " + roundNum + " is invalid" );
		}
		
		return round ; 
		
	}
	
	/**
	 * Returns true when the group stage has ended
	 *@return {@code true} when this {@code Multistage }group stage has ended
	 */
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
	
	/**
	 * Checks if the knockoutstage has ended
	 *@return {@code true } when the group stage has ended
	 */
	public boolean hasKnockoutStageEnded()
	{
		if ( knockoutStage != null )
			return knockoutStage.hasEnded();
		return false;
	}
	
	/**
	 * Determines if this {@code Multistage} is in its group stage or knockout stage
	 * and then coalates the results of the current round 
	 */
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

	/**
	 * Gets an {@code ElimiantionTournament} that encapsulaets the knockoutstage
	 * of this {@code Multistage} {@code Tournament}
	 * @see EliminationTournament
	   @author Oguejifor Chidiebere
	 *@return EliminationTournament
	 */
	public EliminationTournament getKnockoutStage()
	{
		return knockoutStage;
	}
	
	/**
	 * Gets the number of rounds in the group stage
	 * @author Oguejiofor Chidiebere
	 *@return the number pf rounds in each group as  {@code int } 
	 */
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
	
	/**
	 * Creates the knockout stage of this {@code Multistage} {@link Tournament}
	 * @see DoubleElimination
	 *@throws MoveToNextRoundException
	 */
	private void createKnockoutStage() throws MoveToNextRoundException
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
		
//		This loop ensures that two competitors from the same group don't meet in the knock-out stage
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
								hasKnockoutAwayMatches() , false );
			else
				knockoutStage = new DoubleElimination(getSportType(), allQualifiers.toArray( 
								new Competitor[ allQualifiers.size() ] ), false  ) ;
			
		}
		catch (TournamentException e)
		{
			throw new 
				MoveToNextRoundException("Error in entering knockout stage" );
		}
		
//		This section of the code eliminates the Competitors that did not make it past
//		the group stage
		Competitor[] allComps = getCompetitors();

		for( int i = 0 ; i < allComps.length ; i++ )
		{
			if 
			( !isAvailable( 
					allComps[i ] , 
					allQualifiers.toArray( new Competitor[ allQualifiers.size() ]) ) )
				
				allComps[ i].setEliminated( true );
		}
		
	}

	/** Checks if com is in an array of comps
	 * Used only once in
	 *@param com
	 *@param comps
	 *@return
	 */
	private boolean isAvailable( Competitor com , Competitor[] comps)
	{
		return Arrays.stream( comps ).anyMatch( c-> Competitor.isEqual( c , com) );
		
	}
	
	public StandingTable getGroupTable( int groupNum ) throws GroupIndexOutOfBoundsException
	{
		return getGroup( groupNum ).getTable();
	}
	/**
	 * Gets all the {@code Competitor}s that won in their group
	 * The number of winnners per group is dependent on the total number of competitors
	 *@return a{@code Competitor[]} that contains all the group stage winners
	 */ 
	public Competitor[] getGroupWinners()
	{
		List<Competitor> list = new ArrayList<>( 
				numberOfGroupWinners.getNumberOfWinners() * groupStage.length );
		for( int i = 0 ; i< groupStage.length ; i++ )
		{
			StandingTable groupTable = null ;
			try
			{
				groupTable = getGroupTable( i );
			}
			catch (GroupIndexOutOfBoundsException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Competitor[] competitors = groupTable.getCompetitors();
			
			for( int position = 0 ; position < numberOfGroupWinners.getNumberOfWinners() ;
					position++ )
			{
				list.add( competitors[ position ] 	);
			}
			
		}
		return list.toArray( new Competitor[ list.size() ] );
	}
	

	/**
	 * Returns "Group Stage" or "Knockout Stage" depending on the current stage
	 *@return a{@code String} representation of the current stage
	 */
	public String getCurrentStageName()
	{
		if ( getCurrentRoundNum() < numberOfGroupStageRounds()  )
			return "Group Stage";
		return "Knockout Stage";
	}
	
	/**
	 * Gets the number of groups in the group stage
	 *@return an {@code int } 
	 */
	public int numberOfGroupStageRounds()
	{
		return groupStage[ 0 ].getRoundArray().length;
	}

	/**
	 * Updates the third or forth place ranking table
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 * @see StandingTable
	 */
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

	/**
	 * Gets a {@link Round} array containing all the {@code Round}s in a particular group.<br>
	 * Note that the {@code int} argument is indexed 0.THerefore, 0 represents group 1 etc.
	 *@param groupNum the group number
	 *@return a {@code Round } array
	 */
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

	/**
	 * @return the current {@code Round} of this {@code Multistage} or {@code null} if this
	 * {@code Tournament } is over
	 */
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
	
	/**
	 * Gets the active competitors in this {@code Multistage}
	 * {@code Tournament}
	 * @return all the {@code Competitor}s that have not been eliminated
	 */
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

	/**
	 * Checks if the group stage has away fixtures.
	 * Can be true only if the group stage is a {@link RoundRobinTournament}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	*@return {@code true} if the group stage has away fixtures
	 */
	public boolean hasGroupAwayMatches()
	{
		return groupAwayMatches;
	}

	/**
	 * Checks if the knockout stage has away fixtures.
	 * Can be   true only if the knockout stage is a 
	 * {@link  com.solutioninventors.tournament.types.knockout.SingleEliminationTournament}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return {@code boolean}
	 */
	public boolean hasKnockoutAwayMatches()
	{
		return knockoutAwayMatches;
	}

	/**
	 * Gets the point for win the group stage of this {@code Multistage}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return a double
	 */
	public double getWinPoint()
	{
		
		return WIN_POINT ;
	}

	/**
	 * Gets the point for draw the group stage of this {@code Multistage}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return a double
	 */
	public double getDrawPoint()
	{
		return DRAW_POINT;
	}

	/**
	 * Gets the point for loss the group stage of this {@code Multistage}
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return a double
	 */
	public double getLossPoint()
	{
		return LOSS_POINT;
	}

	/**
	 * Checks if this {@code Multistage}'s group srage is over
	 *  @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@return a {@code true} when the group stage is over
	 */
	public boolean isGroupStageOver()
	{
		return knockoutStage == null ? false : true ;
	}

	/**
	 * Gets the number of players that would be chosen from fourth or third place
	 * ranking table
	 *@return an int indicating the number of extra qualifiers
	 */
	public int getNumberOfExtraQualifiers()
	{
		return NUMBER_OF_EXTRA_QUALIFIERS;
	}
	
	/**
	 * 
	 *  Gets the a {@link GroupTournament} representing a group in the group stage
	 *  @author Oguejiofor Chidiebere
	 * @since v1.0
	 *@param groupNum - 0 represents group  one 
	 *@return a GroupTournament
	 *@throws GroupIndexOutOfBoundsException when the group num is invalid
	 */
	public GroupTournament getGroup( int groupNum ) throws GroupIndexOutOfBoundsException
	{
		if ( groupNum < groupStage.length )
			return groupStage[ groupNum ];
		throw new GroupIndexOutOfBoundsException( "The group num is invalid") ;
	}
	
	/**
	 * Gets the number of groups in the group stage
	 *@return an {@code int}
	  *  author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	public int getNumberOfGroups()
	{
		return groupStage.length ;
	}
	
	/**
	 * Gets the third-place or fourth-place {@link StandingTable} 
	 * Returns null if this {@code Multistage} does not contain extra table
	 *@return a {@code StandingTable} object that encapsulates the all 3rd or 4th
	 *place ranking table

	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	public StandingTable getPossibleQualifierTable()
	{
		return extraQualifierTable;
	}
	
	@Override
	public String toString()
	{
		if ( hasEnded() )
			return "Tournament Has Ended";
		
		else if ( isGroupStageOver() )
			return knockoutStage.toString();
		return groupStage[0 ].toString();
		
	}
	
	/** 
	 * This enumeration encapsulates info about how the group winners would be chosen
	 * If the extra qualifier is taken from position 4 then the winners are
	 * FIRST_THREE 
	 * If the extra qualifier is taken from position 3 the the group winners are
	 * the FIRST_TWO
	 */
	private enum GroupWinners
	{
		
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
	
	/**
	 * This enum encapsulates the type of group stage that this {@code Multistage} tournament has
	 * @see GroupTournament
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	private enum GroupStageType
	{
		/**
		 * Indicates that the {@code Multistage} has a {@link SwissTournament} as its 
		 * group stage
		 */
		SWISS , 

		/**
		 * Indicates that the {@code Multistage} has a {@link RoundRobinTournament} as its 
		 * group stage
		 */
		ROUND_ROBIN;
	}
	
	
	 /** This enum encapsulates the type of knockoout stage that a {@code Multistage} tournament has
	 * @see ElimiantionTournament
	 * @author Oguejiofor Chidiebere
	 * @since v1.0
	 */
	
	private enum KnockoutType
	{
		/**
		 * Indicates that the knockout stage is a single elimination
		 */
		SINGLE , 
		/**
		 * Indicates that the knockout stage is a double elimination
		 */
		DOUBLE;
	}
}
