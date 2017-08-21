/**
 * @author Chinedu Oguejiofor
 *10 Aug. 2017
 * 11:30:01 am
 */
package com.solutioninventors.tournament.GUI.utility;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

    public class Main extends Application {    

        @Override
        public void start(Stage primaryStage) {

            int SIZE = 10;
            int length = SIZE;
            int width = SIZE;

            GridPane root = new GridPane();    

            for(int y = 0; y < length; y++){
                for(int x = 0; x < width; x++){

                    Random rand = new Random();
                    int rand1 = rand.nextInt(2);

                    // Create a new TextField in each Iteration
                    TextField tf = new TextField();
                    tf.setPrefHeight(50);
                    tf.setPrefWidth(50);
                    tf.setAlignment(Pos.CENTER);
                    tf.setEditable(false);
                    tf.setText("(" + rand1 + ")");

                    // Iterate the Index using the loops
                    GridPane.setRowIndex(tf,y);
                    GridPane.setColumnIndex(tf,x);    
                    root.getChildren().add(tf);
                }
            }

            Scene scene = new Scene(root, 500, 500);    
            primaryStage.setTitle("Random Binary Matrix (JavaFX)");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {    
            launch(args);
        }    
    }