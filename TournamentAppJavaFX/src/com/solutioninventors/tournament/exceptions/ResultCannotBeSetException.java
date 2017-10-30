package com.solutioninventors.tournament.exceptions;

import com.solutioninventors.tournament.utils.Competitor;

/**
 * This {@code Exception} is thrown when an attempt is made to set the result of a 
 * {@code Fixture } that is complete
 * @author Oguejiofor Chidiebere
 * @see com.solutioninventors.tournament.utils.Fixture
 * @since v1.0
 * 
 */
public class ResultCannotBeSetException extends Exception
{
	/**
	 * Creates a this object with default error message
	 * @author Oguejiofor Chidiebere
	 * @param com2  the away competitor in the fixture
	 * @param score2 the away competitor score
	 * @param score1 the home competitor score
	 * @param com1 the away competitor in the fixture
	 * @see com.solutioninventors.tournament.utils.Fixture
	 * @since v1.0
	 * 
	 */
	
	public ResultCannotBeSetException(Competitor com1, double score1, double score2, Competitor com2)
	{
		this( "Cannot change the result of a complete Fixture " +
				com1 + " VS " + com2 );
	}

	/**
	 * Creates this object with specified String as the error message
	 * @author Oguejiofor Chidiebere
	 * @param message the error message to be displayed when the this object is thrown
	 * @see com.solutioninventors.tournament.utils.Fixture
	 * @since v1.0
	 * 
	 */
	
	public ResultCannotBeSetException(String message)
	{
		super( message );
	}

	public ResultCannotBeSetException(double score1, double score2)
	{
		this( "Cannot change the result of a complete Fixture: " + score1 + " VS " + score2 );
	}
}
