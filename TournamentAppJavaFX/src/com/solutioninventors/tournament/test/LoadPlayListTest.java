/**
 *@Author: Oguejiofor Chidiebere
 *LoadPlayListTest.java
 *Sep 30, 2017
 *11:36:38 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.solutioninventors.tournament.GUI.utility.Playlist;

public class LoadPlayListTest
{

	public static void main(String[] args)
	{
		List<File> list = null; 
		try
		{
			list  = Playlist.get();
			System.out.println("Loading File.. ");
			viewSongs(list);
			
			System.out.println("Shuffling..." );
			list = Playlist.shuffle();
			viewSongs(list);
			
		}
		catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void viewSongs(List<File> list)
	{
		for ( File f : list )
		{
			System.out.println( f.getAbsolutePath() );
			
		}
	}

}
