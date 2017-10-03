/**
 *@Author: Oguejiofor Chidiebere
 *Playlist.java
 *Sep 30, 2017
 *10:37:58 AM
 */
package com.solutioninventors.tournament.GUI.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.solutioninventors.tournament.exceptions.InvalidFileFormatException;

public class Playlist
{

	private static File playListFile = 
			new File( "playlist.dll" );

	
	private static boolean isFileValid( File file )
	{
		if ( file.getName().endsWith(".mp3" ) )
			return true;
		return false;
	}
	
	
	public static List<File> shuffle()
	{
		List<File> playList;
		try
		{
			playList = get();
		}
		catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		Collections.shuffle( playList );   
		
		
		return playList;
		
	}
	
	public static void add( File file)
	{
		List<File> playList = null ;
		
		if ( !isFileValid( file ) )
			throw new InvalidFileFormatException("The file is not an mp3");
		
		if ( playListFile.exists() )
			try
			{
				playList = get();
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
				return ;
			}
		else
			playList = new ArrayList<File>() ; 
		
		playList.add( file );
		
		try
		{
			save( playList);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static void save( List<File> playList) throws FileNotFoundException, IOException
	{
		ObjectOutputStream output  = null;
		Collections.sort( playList );
		try
		{
			output = new ObjectOutputStream( new FileOutputStream( playListFile ) );
			output.writeObject( playList );
			
		}
		finally 
		{
			output.close();
		}
		
	}

	public  static List<File> get() throws IOException, ClassNotFoundException
	{
		ObjectInputStream input  = null;
		try
		{
			input = new ObjectInputStream( new FileInputStream( playListFile ) );
			return ( List<File>)  input.readObject() ; 
		}
		finally 
		{
			input.close();
		}
		
	}

	
	
	public List<File> switchPosition( int cutIndex, int moveIndex )
	{
		List<File> playList = null; 
		try
		{
			playList =  get() ;
		}
		catch (ClassNotFoundException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		File firstFile = playList.remove( cutIndex );
		File secondFile = playList.remove( moveIndex );
		
		playList.add(cutIndex, secondFile);
		playList.add( moveIndex, firstFile); 	
		
		try
		{
			save( playList );
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return playList;
	}
}
