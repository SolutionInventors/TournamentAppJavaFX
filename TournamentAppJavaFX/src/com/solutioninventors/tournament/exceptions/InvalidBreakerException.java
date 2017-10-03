/**
 *@Author: Oguejiofor Chidiebere
 *InvalidBreakerException.java
 *Aug 10, 2017
 *9:44:01 PM
 */
package com.solutioninventors.tournament.exceptions;

public class InvalidBreakerException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidBreakerException()
	{
		super("Tie Breaker is invalid" );
	}
	
	public InvalidBreakerException( String message )
	{
		super( message );
	}
}
