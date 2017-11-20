/**
 * 
 */
package com.solutioninventors.tournament.GUI.utility;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author ChineduKnight
 */

public class GUITest extends Application {
	private static Stage window;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			Parent root = loader.load(getClass().getResource(Paths.viewpath + "WelcomeScreen.fxml").openStream());
			Scene scene = new Scene(root);
			window = primaryStage;
			InputStream url1 = getClass().getResourceAsStream(Paths.images + "logo.png"); 
			window.getIcons().add(new Image(url1));
			window.centerOnScreen();
			window.setScene(scene);
			window.sizeToScene();
			window.setResizable(false);
			window.setOnCloseRequest(e -> {
				e.consume();
				closeprogram();
			});
			scene.getStylesheets().add(getClass().getResource(Paths.css + "welcomeScreen.css").toExternalForm());
			primaryStage.setTitle("Tournament APP");
			
			url1.close();
			primaryStage.show();
		} catch (IOException e) {
		}
	}

	private void closeprogram() {
		int answer = ConfirmBox.display("Close App", "Are you sure you want to exit");
		if (answer == 1) {
			window.close();

		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
