/**
 * author: Oguejiofor Chidiebere
 * Aug 1, 2017
 * Round.java
 * 2:22:30 AM
 *
 */
package com.solutioninventors.tournament.group;

public class Round {
	/**
	 * This class encapsulates all the action that occur in a Round Variable
	 * complete becomes true when the round has no pending fixtures
	 */
	private Fixture[] fixtures;
	private int[][] scores;

	private boolean complete;

	public Round(Fixture[] fixes) {
		fixtures = fixes;

	}

	public Fixture[] getFixtures() {
		return fixtures;
	}

	public void setScores(int[][] scores) {
		// precondition: the scores must be ordered in the same way as the fixtures and
		// must have same length
		if (!isComplete()) {
			for (int i = 0; i < fixtures.length; i++) {
				fixtures[i].setResult(scores[i][0], scores[i][1]);
			}

			complete = true;
		}
	}

	private boolean isComplete() {
		return complete;
	}

}
