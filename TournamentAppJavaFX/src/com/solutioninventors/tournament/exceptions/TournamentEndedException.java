/**
 *@Author: Oguejiofor Chidiebere
 *TournamentEndedException.java
 *Aug 7, 2017
 *3:03:25 PM
 */
package com.solutioninventors.tournament.exceptions;

public class TournamentEndedException extends RuntimeException
{

	/** 
	 * Should be thrown when moveToNextRound is called when an attempt is made to modify
	 * a tournament that has already ended
	 */
	public TournamentEndedException()
	{
		this( "Tournament hasended" );
	}

	public TournamentEndedException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
}