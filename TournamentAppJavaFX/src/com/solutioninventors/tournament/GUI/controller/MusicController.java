package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MusicController {
	@FXML
	private Button btnplay;
	@FXML
	private Button btnstop;
	@FXML
	private Button btnchangesong;
	@FXML
	private Slider seekslider;
	@FXML
	private Slider volumeslider;
	private boolean playing;
	MediaPlayer mediaPlayer;
	public void initialize(){
	playsong("C:\\Users\\Chinedu\\Music\\Don Williams\\don william (16).mp3");
	System.out.println("I am called");
	}

	   @FXML
	   private void playPauseButtonPressed(ActionEvent e) {
	      playing = !playing;

	      if (playing) {
	    	  btnplay.setText("Pause");
	         mediaPlayer.play();
	      }
	      else {
	    	  btnplay.setText("Play");
	         mediaPlayer.pause();
	      }
	   } 
	   
	   @FXML
	   private void stop(ActionEvent e) {
		   mediaPlayer.stop();
		   playing = false;
		   btnplay.setText("Pause");
	      
	   } 
	   
	   
	private void playsong(String path) {
		Media media= new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		playing=true;
	}
	
	public void changeSong() throws MalformedURLException {
		FileChooser fc = new FileChooser();
		Stage primaryStage = new Stage();
		File seletedfile = fc.showOpenDialog(primaryStage);
		//for the song
		String path = seletedfile.getAbsolutePath();
		stop(null);
		playsong(path);
		
	}
}