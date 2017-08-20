/**
 * 
 */
package com.solutioninventors.tournament.GUI.controller;

import java.io.IOException;

import com.solutioninventors.tournament.GUI.utility.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author ChineduKnight
 *
 */
public class Btn {

	
	public void previous(ActionEvent event, String fxml, String Cssfile, String title) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(Paths.viewpath+fxml));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource(Paths.viewpath+Cssfile).toExternalForm());
		primaryStage.show();
		primaryStage.setTitle(title);}
	
	public static void next(Pane root, ActionEvent event, String title) throws IOException {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle(title);
		}
	public void cancel(ActionEvent event) {
	}
}
