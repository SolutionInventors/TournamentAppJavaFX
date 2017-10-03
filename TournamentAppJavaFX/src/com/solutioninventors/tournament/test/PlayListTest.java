/**
 *@Author: Oguejiofor Chidiebere
 *PlayListTest.java
 *Sep 30, 2017
 *11:27:44 AM
 */
package com.solutioninventors.tournament.test;

import java.io.File;

import javax.swing.JFileChooser;

import com.solutioninventors.tournament.GUI.utility.Playlist;

public class PlayListTest
{

	public static void main(String[] args)
	{
		JFileChooser chooser = new  JFileChooser();
		chooser.showOpenDialog( null );
		File songFile = chooser.getSelectedFile() ;
		
		System.out.println( "Adding File..." );
		Playlist.add( songFile );
		
		

	}

}
