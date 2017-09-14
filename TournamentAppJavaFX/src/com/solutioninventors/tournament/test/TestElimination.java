/**
 * @author Chinedu Oguejiofor
 *9 Aug. 2017
 * 1:45:11 pm
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import com.solutioninventors.tournament.exceptions.MoveToNextRoundException;
import com.solutioninventors.tournament.exceptions.NoFixtureException;
import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.exceptions.TournamentEndedException;
import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;

public class TestElimination {

	/**
	 * 
	 */
	public static void main(String[] args) {
		File file = new File("Arsenal.jpg");
		Competitor c1 = new Competitor( "Chidiebere" , file );
		Competitor c2 = new Competitor( "Fred", file );
		Competitor c3 = new Competitor( "Joshua" , file );
		Competitor c4 = new Competitor( "Chinedu" ,  file ) ;
		Competitor[] comps = { c1 , c2  , c3 , c4 }; 
		
		try 
		{
			SingleEliminationTournament cp = new SingleEliminationTournament( SportType.GOALS_ARE_SCORED, comps);
			cp.setResult(c1, 2, 5, c2);
			cp.setResult(c3, 2, 5, c4);
			cp.moveToNextRound();
		}
		catch ( MoveToNextRoundException| NoFixtureException |
				TournamentEndedException | TournamentException | ResultCannotBeSetException e)
		{
			e.printStackTrace();
		}
		
		
		
	}

}
