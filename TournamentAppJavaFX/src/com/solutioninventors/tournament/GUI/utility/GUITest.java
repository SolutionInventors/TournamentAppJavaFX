/**
 * 
 */
package com.solutioninventors.tournament.GUI.utility;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

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
			Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath + "WelcomeScreen.fxml"));
			Scene scene = new Scene(root);
			window = primaryStage;
			URL url1 = getClass().getResource(Paths.images + "logo.jpg"); 
			window.getIcons().add(new Image(new FileInputStream(new File(url1.toURI()))));
			window.centerOnScreen();
			window.setScene(scene);
			window.sizeToScene();
			window.setResizable(false);
			/*window.setOnCloseRequest(e -> {
				e.consume();
				closeprogram();
			});*/
			scene.getStylesheets().add(getClass().getResource(Paths.css + "welcomeScreen.css").toExternalForm());
			primaryStage.setTitle("Tournament APP");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void closeprogram() {
		Boolean answer = ConfirmBox.display("Close App", "Are you sure you want to exit");
		if (answer) {
			window.close();

		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
