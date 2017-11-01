package com.solutioninventors.tournament.test;

import java.util.ArrayList;

public class SChoo {

	public static void main(String[] args) {
	ArrayList<Integer> abc = new ArrayList<>();
	abc.add(0, 12);
	abc.add(1, 33);
	abc.add(2, 55);
	for (int i = 0; i < abc.size(); i++) {
		System.out.println(abc.get(i));
	}
	
	}

}
