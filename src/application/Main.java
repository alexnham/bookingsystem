package application;

	

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application{
	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
		Scene scene = new Scene(root);
		String css = this.getClass().getResource("application.css").toExternalForm();
		scene.getStylesheets().add(css);
		stage.setScene(scene);
		stage.setTitle("Booking System");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
