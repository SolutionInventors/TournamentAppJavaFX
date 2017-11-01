/**
 *@Author: Oguejiofor Chidiebere
 *TournamentEndedException.java
 *Aug 7, 2017
 *3:03:25 PM
 */
package com.solutioninventors.tournament.exceptions;

public class TournamentEndedException extends Exception
{

	private static final long serialVersionUID = 1L;

	/** 
	 * Should be thrown when moveToNextRound is called when an attempt is made to modify
	 * a tournament that has already ended
	 */
	public TournamentEndedException()
	{
		this( "Tournament has Ended" );
	}

	public TournamentEndedException(String arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
}
