/**
 *@Author: Oguejiofor Chidiebere
 *CompetitorTest.java
 *Sep 14, 2017
 *8:37:26 PM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import com.solutioninventors.tournament.exceptions.ResultCannotBeSetException;
import com.solutioninventors.tournament.utils.Competitor;
import com.solutioninventors.tournament.utils.Fixture;
import com.solutioninventors.tournament.utils.SportType;

public class CompetitorTest
{
	public static void main( String ... args )
	{
		File file = new File("Arsenal.jpg");
		
		Competitor[] coms = { new Competitor("Chinedu", file), new Competitor("Chidi", file) };

		Fixture fixture = new Fixture( SportType.GOALS_ARE_SCORED , coms[0],coms[1]	);
		
		try
		{
			fixture.setResult( 4 , 3 );
		}
		catch (ResultCannotBeSetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		System.out.println( coms[0] + " home goal scored against" +coms[1] + " = " +
				coms[0].getHomeGoal( coms[1] ) +"\n" ) ;
		
		System.out.println( coms[0] + " goalConceeded =" + coms[0].getGoalsConceeded(true));
		System.out.println( coms[1] + " goalConceeded =" + coms[1].getGoalsConceeded(true));
		
		System.out.println( coms[0] + " away goalConceeded =" + coms[0].getAwayGoalsConceeded(true ));
		System.out.println( coms[1] + " away goalConceeded =" + coms[1].getAwayGoalsConceeded(true));
		
		System.out.println( coms[0] + " home goalConceeded =" + coms[0].getHomeGoalsConceeded(true ));
		System.out.println( coms[1] + " home goalConceeded =" + coms[1].getHomeGoalsConceeded(true));
		
		
	}
}
