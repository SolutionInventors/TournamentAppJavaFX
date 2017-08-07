package com.solutioninventors.tournament.utils;

import javax.swing.JOptionPane;

public class Fixture
{
	
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	private  double competitorOneScore;
	private  double competitorTwoScore ;
	private boolean complete ;
	
	public Fixture(Competitor competitorOne, Competitor competitorTwo)
	{
		COMPETITOR_ONE = competitorOne ;
		COMPETITOR_TWO = competitorTwo ;
		
	}

	
	public void setResult( final double score1 , final double score2 ) 
	{
		if ( !isComplete() )
		{
			if ( score1 > score2 )
			{
				getCompetitorOne().incrementWins();
				getCompetitorTwo().incrementLoss();
			}
			else if ( score1 == score2  ) //match is a draw
			{
				getCompetitorOne().incrementDraw();
				getCompetitorTwo().incrementDraw();
			}
			else
			{
				getCompetitorOne().incrementLoss();
				getCompetitorTwo(). incrementWins();
			}
			competitorOneScore = score1 ;
			competitorTwoScore = score2 ;
			
			complete = true ;
		}
		else
		{
			String message = String.format( "%s\n%s %.1f VS %.1f %s", "This fixture has a result" ,
										getCompetitorOne().getName() , getCompetitorOneScore() , 
										getCompetitorTwoScore() , getCompetitorTwo().getName() );
			JOptionPane.showMessageDialog( null , message );
			
		}
	}

	public boolean isComplete()
	{
		return complete;
	}


	public boolean isDraw()
	{
		if ( isComplete() && ( getCompetitorOneScore() == getCompetitorTwoScore() ) )
			return true ;
		else
			return false ;
	}
	public boolean hasWinner()
	{
		if ( isComplete() && ( getCompetitorOneScore() > getCompetitorTwoScore() && isDraw() )  )
			return true ;
		else
			return false ;
	}
	
	public boolean hasLoser()
	{
		return hasWinner() ;
	}
	
	public Competitor getWinner()
	{
		if ( hasWinner() )
			return getCompetitorOneScore() > getCompetitorTwoScore() ? 
					getCompetitorOne() : getCompetitorTwo() ;
					
		return null ;
		
	}
	
	public Competitor getLoser()
	{
		if ( hasLoser() )
			return getCompetitorOneScore() < getCompetitorTwoScore() ? 
					getCompetitorOne() : getCompetitorTwo() ;
					
		return null ;
		
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


	public double getCompetitorOneScore()
	{
		return competitorOneScore;
	}


	public double getCompetitorTwoScore()
	{
		return competitorTwoScore;
	}


	
	
}
