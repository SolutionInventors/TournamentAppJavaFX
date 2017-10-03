/**
 *@Author: Oguejiofor Chidiebere
 *ConvertBreakerTest.java
 *Sep 30, 2017
 *9:54:55 AM
 */
package com.solutioninventors.tournament.test;

import java.util.Arrays;

import com.solutioninventors.tournament.utils.Breaker;

public class ConvertBreakerTest
{

	public static void main(String[] args)
	{
		Test.displayMessage("This tests the conversion of Breaker object to String and\n vice versa" );
		
		Breaker[] breakers  =  Breaker.values();
		

		String[] names = Breaker.convertToString( breakers );
		Test.displayMessage( Arrays.toString( names  ) );
		System.out.println(Arrays.toString( Breaker.convertToBreaker( names ) )  );
		
	}

}
