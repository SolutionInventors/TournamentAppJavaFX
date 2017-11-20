package com.solutioninventors.tournament.test;

import java.io.File;

import com.solutioninventors.tournament.exceptions.TournamentException;
import com.solutioninventors.tournament.types.knockout.SingleEliminationTournament;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.SportType;

public class SingleElimShiffleTest {

	public static void main(String[] args) throws TournamentException {
		File file = new File("Fred.jpg");
		
		// File file = new File("golf.jpg");
		Competitor c1 = new Competitor("Chidiebee", file);
		Competitor c2 = new Competitor("Chidiebere5", file);
		Competitor c3 = new Competitor("Joshua", file);
		Competitor c4 = new Competitor("Chinedu", file);

		Competitor c5 = new Competitor("Chd", file);
		Competitor c6 = new Competitor("chd2", file);
		Competitor c7 = new Competitor("josh1", file);
		Competitor c8 = new Competitor("Chind2", file);

		Competitor[] comps = { c1, c2, c3, c4, c5, c6, c7, c8 };
		
		SingleEliminationTournament tour1 = new
				SingleEliminationTournament(SportType.GOALS_ARE_SCORED,
						comps, false, true);
		
		
		Test.displayFixtures(tour1.getCurrentRound().getFixtures() );
	}



	
}
