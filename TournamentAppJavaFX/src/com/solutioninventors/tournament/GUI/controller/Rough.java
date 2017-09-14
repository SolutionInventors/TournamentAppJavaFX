/**
 * @author Chinedu Oguejiofor
 *14 Sep. 2017
 * 9:36:07 am
 */
package com.solutioninventors.tournament.GUI.controller;

import java.util.HashMap;
import java.util.Map;

public class Rough {
		// create and output two-dimensional arrays
		public static void main(String[] args) {
//			int[][] array1 = { { 1, 2, 3 }, { 4, 5, 6 } };
//			int[][] array2 = { { 1, 2 }, { 3 }, { 4, 5, 6 } };
			String a[][] = {{"first","second","third"},{"fourth","fifth","sixth"}};
			String b[][] = {{"first","second","third"},{"fourth","fifth","sixth"}};
			
			
			Map<Integer, String[][]> map = new HashMap<Integer, String[][]>();
			map.put(0, a);
			map.put(1, b);
			
		outputString(map.get(0));
		outputString(map.get(1));
			
			
			
//			System.out.println("Values in array1 by row are");
//			outputArray(array1); // displays array1 by row
//
//			System.out.println("\nValues in array2 by row are");
//			outputArray(array2); // displays array2 by row
		} // end main

		// output rows and columns of a two-dimensional array
		public static void outputArray(int[][] array) {
			// loop through array's rows
			for (int row = 0; row < array.length; row++) {
				// loop through columns of current row
				for (int column = 0; column < array[row].length; column++)
					System.out.printf("%d  ", array[row][column]);

				System.out.println(); // start new line of output
			} // end outer for
		} // end method outputArray
		
		public static void outputString(String[][] array) {
			// loop through array's rows
			for (int row = 0; row < array.length; row++) {
				// loop through columns of current row
				for (int column = 0; column < array[row].length; column++)
					System.out.printf("%s  ", array[row][column]);

				System.out.println(); // start new line of output
			} // end outer for
		} // end method outputArray
	} // end class TwoDimArray
