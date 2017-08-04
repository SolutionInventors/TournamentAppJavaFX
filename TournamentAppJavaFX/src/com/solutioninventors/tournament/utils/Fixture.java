package com.solutioninventors.tournament.utils;

public class Fixture
{
	
	private final Competitor COMPETITOR_ONE;
	private final Competitor COMPETITOR_TWO;
	
	
	public Fixture(Competitor competitorOne, Competitor competitorTwo)
	{
		COMPETITOR_ONE = competitorOne ;
		COMPETITOR_TWO = competitorTwo ;
	}

	
	public void setResult( int CompetitoOneScore , int CompetitirTwoScore ) 
	{
		
	}

	public Competitor getCompetitorOne()
	{
		return COMPETITOR_ONE ;
	}
	
	public Competitor getCompetitorTwo()
	{
		return COMPETITOR_TWO ;
	}
}
