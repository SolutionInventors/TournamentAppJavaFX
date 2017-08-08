/**
 * author: Oguejiofor Chidiebere
 * Aug 3, 2017
 * SwissTournament.java
 * 9:15:00 PM
 *
 */
package com.solutioninventors.tournament.group;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.Round;
import com.solutioninventors.tournament.utils.SportType;

public class SwissTournament extends GroupTournament
{
	private final Round[] rounds ;
	
	
	public SwissTournament( Competitor[] comps, SportType type,
							double winPoint , double drawPoint , double lossPoint,
							int totalRounds  ) throws Exception
	{
		super( comps , type , winPoint , drawPoint , lossPoint );
		if ( comps.length % 2 != 0 )
			throw new Exception() ;
	
		rounds = new Round[ totalRounds ];
	
	}

	public int getTotalNumberOfRounds()
	{
		return rounds.length ;
	}

	@Override
	public void moveToNextRound() throws TournamentException
	{
		getTable().updateTables(); 
		
		setCurrentRoundNum( getCurrentRoundNum() + 1 );
		
		if ( getCurrentRoundNum() <=  getTotalNumberOfRounds() )
		{
			Competitor[] temp = getTable().getCompetitors() ;
			Fixture[] fixtures = new 
					Fixture[ temp.length %2 == 0 ? getCompetitors().length : temp.length -1 ];
			
			
			for ( int i = 0 ; i < fixtures.length ; i +=2 )
			{
				fixtures[ i ] = new Fixture( temp[i], temp[ i + 1] );
				
			}
			
			rounds[ getCurrentRoundNum() - 1 ] = new Round( fixtures );
		
		}
		else
			throw new TournamentException( "Tournament has ended" );
	}
}
