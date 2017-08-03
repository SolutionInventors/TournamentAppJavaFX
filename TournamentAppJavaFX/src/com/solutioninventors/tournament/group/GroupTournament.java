/**
 *@Author: Oguejiofor Chidiebere
 *GroupTournament.java
 *Aug 3, 2017
 *9:53:16 PM
 */
package com.solutioninventors.tournament.group;

import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;

public class GroupTournament
{
	private StandingTable table;
	private final Competitor[] COMPETITORS;
	private final SportType SPORT_TYPE;
	
	
	public GroupTournament( Competitor[] comps  , SportType type )
	{
		SPORT_TYPE = type ;
		COMPETITORS = comps ; 
		table = new StandingTable( SPORT_TYPE, COMPETITORS );
	}
	
	public Competitor[] getCompetitor()
	{
		return COMPETITORS;
	}

	public SportType getSportType()
	{
		return SPORT_TYPE;
	}
	
	
	public StandingTable getTable()
	{
		return table;
	}
	

}
