package com.solutioninventors.tournament.utils;

import javax.swing.JOptionPane;

public class Fixture
{
	
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	private  int competitorOneScore;
	private  int competitorTwoScore ;
	private boolean complete ;
	public Fixture(Competitor competitorOne, Competitor competitorTwo)
	{
		COMPETITOR_ONE = competitorOne ;
		COMPETITOR_TWO = competitorTwo ;
	}

	
	public void setResult( final int score1 , final int score2 ) 
	{
		if ( !isComplete() )
		{
			competitorOneScore = score1 ;
			competitorTwoScore = score2 ;
			complete = true ;
		}
		else
		{
			String message = String.format( "%s\n%s %d VS %d %s", "This fixture has a result" ,
										getCompetitorOne().getName() , getCompetitorOneScore() , 
										getCompetitorTwoScore() , getCompetitorTwo().getName() );
			JOptionPane.showMessageDialog( null , message );
			
		}
	}

	public boolean isComplete()
	{
		return complete;
	}


	public boolean hasFixture( Competitor com1  , Competitor com2 )
	{
//		returns true if com1 equals  COMPETITOR_ONE AND con2 = COMPETITOR_TWO
		if ( getCompetitorOne().getName().equals( com1.getName()  ) && 
			 getCompetitorTwo().getName().equals( com2.getName() ))
			return true;
		else
			return false;
	}
	
	
	public Competitor getCompetitorOne()
	{
		return COMPETITOR_ONE ;
	}
	
	public Competitor getCompetitorTwo()
	{
		return COMPETITOR_TWO ;
	}


	public int getCompetitorOneScore()
	{
		return competitorOneScore;
	}


	public int getCompetitorTwoScore()
	{
		return competitorTwoScore;
	}


	
	
}
