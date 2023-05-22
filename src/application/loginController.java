package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Arc;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	PasswordField password;
	@FXML
	Text incorrect;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void login(ActionEvent e) throws IOException {
		System.out.println("test");
		String saveName = password.getText();
		String pass = "test";
		if(saveName.equals(pass)) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
		root = loader.load();
			
		//SecondController second = loader.getController();
		//second.displayName(saveName);
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		} else {
			incorrect.setText("Incorrect");
		}
		
	}
}
