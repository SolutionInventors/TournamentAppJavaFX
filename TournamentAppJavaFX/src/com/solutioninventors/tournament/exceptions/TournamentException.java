/**
 *@Author: Oguejiofor Chidiebere
 *TournamentException.java
 *Aug 3, 2017
 *9:47:38 PM
 */
package com.solutioninventors.tournament.exceptions;

public class TournamentException extends RuntimeException
{
	/**
	 * This exception should be thrown  only by subclasses of Tournament 
	 */
	
	public TournamentException( String message )
	{
		super( message );
	}
}
