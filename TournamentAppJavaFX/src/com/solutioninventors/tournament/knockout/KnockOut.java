/** 31 Jul. 2017 KnockOuwt.java 8:45:22 pm */
package com.solutioninventors.tournament.knockout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/** @author ChineduKnight */
public class KnockOut {

	// variable declaration
	private int noofPlayers;
	private ArrayList<Competitor> competitors;
	int count = 1;

	private int[] scoreofPlayers;
	Scanner input = new Scanner(System.in);

	// constructor
	public KnockOut(int howmanyplayers) {
		setNoofPlayers(howmanyplayers);
		inputCompetitorname(howmanyplayers);
		Collections.shuffle(competitors);
		Collections.shuffle(competitors);
		printFixtures();
		inputScore();
		eliminateLosers();
		System.out.println("All set");

	}// end of constructor

	public void eliminateLosers() {
		int first = 0, index1 = 0;
		for (int j = 0; j < competitors.size(); j++) {
			if (!competitors.get(j).isEliminate()) {
				if (count % 2 != 0) {
					first = competitors.get(j).getCurrentScore();
					index1 = j;
					count++;
				} else {
					int second = competitors.get(j).getCurrentScore();
					if (first > second) {
						competitors.get(j).setEliminate(true);
					} else
						competitors.get(index1).setEliminate(true);
					count++;
				} // end count if
			} // end not eliminated
		} // end for loop
	}// end losers elimination

	public void inputCompetitorname(int noofcomp) {
		// competitors = new Competitor[noofcomp];
		competitors = new ArrayList<Competitor>(noofcomp);
		for (int i = 0; i < noofcomp; i++) {
			System.out.printf("Please input the name of player %d: ", i + 1);
			competitors.add(new Competitor(input.next()));
		} // end for loop

	}// end inputCometitorname

	/* public void generateFixture(ArrayList<Competitor> toShuffle) {
	 * Collections.shuffle(toShuffle); Collections.shuffle(toShuffle); } */
	// setters and getters
	public int getNoofPlayers() {
		return noofPlayers;
	}

	public void inputScore() {
		System.out.println("Input the Scores for the following games");
		for (Competitor competitor : competitors) {
			if (!competitor.isEliminate()) {
				System.out.printf("%s:\t", competitor.getName());
				competitor.setCurrentScore(input.nextInt());
			}
		} // end score input
			// display the result
		for (Competitor competitor2 : competitors) {
			if (!competitor2.isEliminate()) {
				if (count % 2 != 0) {
					System.out.printf("%s\t%d\tVs ", competitor2.getName(), competitor2.getCurrentScore());
					count++;
				} else {
					System.out.printf("%d\t%s\n", competitor2.getCurrentScore(), competitor2.getName());
					count++;
				} // end count if
			} // end not eliminated
		}
	}// end input score

	public void setNoofPlayers(int noofPlayers) {
		// code to check if no is even
		if (noofPlayers % 4 == 0) {
			this.noofPlayers = noofPlayers;
		} else {
			// this should be an error
			System.out.println("number Must be in power of 2^x and x>1");
			System.exit(1);
		}

	}


	public int[] getScoreofPlayers() {
		return scoreofPlayers;
	}

	public void setScoreofPlayers(int[] scoreofPlayers) {
		this.scoreofPlayers = scoreofPlayers;
	}

	public void printFixtures() {
		for (Competitor competitor : competitors) {

			if (!competitor.isEliminate()) {
				if (count % 2 != 0) {
					System.out.printf("%s\t Vs ", competitor.getName());
					count++;
				} else {
					System.out.printf("%s\n", competitor.getName());
					count++;
				} // end count if

			} // end not eliminated
		} // end for loop
	}// end print fixtures

	public static void main(String[] args) {
		KnockOut test = new KnockOut(8);
		test.printFixtures();
		test.inputScore();
		test.eliminateLosers();
		
		test.printFixtures();
		test.inputScore();
		test.eliminateLosers();
		
		test.printFixtures();//for the winner
	}// end of main

}// end of class
