/**
 *@Author: Oguejiofor Chidiebere
 *TournamentException.java
 *Aug 3, 2017
 *9:47:38 PM
 */
package com.solutioninventors.tournament.exceptions;

public class TournamentException extends Exception
{
	private static final long serialVersionUID = 708518744386092574L;

	/**
	 * This exception should be thrown  only by subclasses of Tournament 
	 * Thrown when an error occurs when creating  tournament
	 * @param message the error message that would be shown
	 */
	
	public TournamentException( String message )
	{
		super( message );
	}
}
