/**
 *@Author: Oguejiofor Chidiebere
 *GroupNumberOutOfBounds.java
 *Aug 12, 2017
 *9:22:58 PM
 */
package com.solutioninventors.tournament.exceptions;

public class OnlyOutstandingAreLeftException extends TournamentEndedException
{

	private static final long serialVersionUID = 1L;

	public OnlyOutstandingAreLeftException(String string)
	{
		super ( string );
	}

}
