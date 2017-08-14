/**
 *@Author: Oguejiofor Chidiebere
 *RoundIndexOutOfBoundsException.java
 *Aug 11, 2017
 *6:51:35 PM
 */
package com.solutioninventors.tournament.exceptions;

public class RoundIndexOutOfBoundsException extends Exception 
{

	private static final long serialVersionUID = 1L;


	public RoundIndexOutOfBoundsException()
	{
		this( "Round index is out of bound") ;
	}
	
	
	public RoundIndexOutOfBoundsException(String message )
	{
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
