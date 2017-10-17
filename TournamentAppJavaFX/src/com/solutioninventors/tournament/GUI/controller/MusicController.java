package com.solutioninventors.tournament.GUI.controller;

import java.io.File;
import java.net.MalformedURLException;

import com.solutioninventors.tournament.GUI.utility.AlertBox;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class MusicController {
	@FXML
	private Button btnplay;
	@FXML
	private Button btnstop;
	@FXML
	private Button btnchangesong;
	//@FXML private Slider seekslider;
	@FXML
	private Slider volumeSlider;
	@FXML private ProgressBar progress =new ProgressBar(0);
	@FXML private Label songName;
	private boolean playing;
	private MediaPlayer mediaPlayer;
	private ChangeListener<Duration> progressChangeListener;
	  
	
	
	public void initialize() {
		
		
		//playsong("C:\\Users\\Public\\Music\\Sample Music\\Kalimba.mp3");
		//mediaPlayer.setVolume(.7);// just to set the volume to about 70%
		volumeSlider.setValue(70);// this multiplication is done cos the getvol retruns 0 to
																// 1
		// while the slider uses 1 to 100
		volumeSlider.valueProperty().addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable arg0) {
				mediaPlayer.setVolume(volumeSlider.getValue() / 100);
			}

		});
	
	 
	}//end init

	@FXML
	private void playPauseButtonPressed(ActionEvent e) {
		if (mediaPlayer != null) {
		playing = !playing;

		if (playing) {
			btnplay.setText("Pause");
			mediaPlayer.play();
		} else {
			btnplay.setText("Play");
			mediaPlayer.pause();
		}
		
		
		} else {
			AlertBox.display("No Music", "You have not selected any song");
		}
	}//end 
	
	@FXML
	private void close(ActionEvent e) {
		((Node) e.getSource()).getScene().getWindow().hide();
	}
	
	@FXML
	private void stop(ActionEvent e) {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			playing = false;
			btnplay.setText("Play");
		}
		

	}

	private void playsong(String path) {
		if (mediaPlayer != null) {
			stop(null);
		}

		Media media = new Media(new File(path).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		playing = true;
		btnplay.setText("Pause");
		//mediaPlayer.currentTimeProperty().addListener(progressChangeListener);
		setCurrentlyPlaying(mediaPlayer);
		mediaPlayer.setVolume(volumeSlider.getValue());
		mediaPlayer.setOnEndOfMedia(new Runnable() {
	        @Override public void run() {
	        	btnplay.setText("Play");
	          }
	        });
		
	}

	public void changeSong() throws MalformedURLException {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(
				new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Music"));
		fc.getExtensionFilters().add(new ExtensionFilter("Mp3 Audio ", " *.mp3"));
		fc.getExtensionFilters().add(new ExtensionFilter("All Files ", "*"));
		Stage primaryStage = new Stage();
		File seletedfile = fc.showOpenDialog(primaryStage);
		//seletedfile.getName()
		// for the song
		if (seletedfile != null && seletedfile.getName().endsWith(".mp3")) {
			String path = seletedfile.getAbsolutePath();
			playsong(path);
		} 

	}
	
	  /** sets the currently playing label to the label of the new media player and updates the progress monitor. */
	  private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
	    progress.setProgress(0);
	    progressChangeListener = new ChangeListener<Duration>() {
	      @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
	        progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
	      }
	    };
	    newPlayer.currentTimeProperty().addListener(progressChangeListener);

	    String source = newPlayer.getMedia().getSource();
	    source = source.substring(0, source.length() - ".mp3".length());
	    source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
	    //currentlyPlaying.setText("Now Playing: " + source);
	    songName.setText(source);
	  }
}
