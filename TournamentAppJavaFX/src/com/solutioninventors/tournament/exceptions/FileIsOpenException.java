/**
 *@Author: Oguejiofor Chidiebere
 *InvalidBreakerException.java
 *Aug 10, 2017
 *9:44:01 PM
 */
package com.solutioninventors.tournament.exceptions;

public class FileIsOpenException extends Exception
{
	private static final long serialVersionUID = 1L;

	public FileIsOpenException()
	{
		super("The file is already open" );
	}
	
	public FileIsOpenException( String message )
	{
		super( message );
	}
}
