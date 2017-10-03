/**
 * author: Oguejiofor Chidiebere
 * Jul 31, 2017
 * ImageFormatException.java
 * 10:41:56 PM
 *
 */
package com.solutioninventors.tournament.exceptions;

public class InvalidFileFormatException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public InvalidFileFormatException ( String message )
	{
		super( message );
	}
}
