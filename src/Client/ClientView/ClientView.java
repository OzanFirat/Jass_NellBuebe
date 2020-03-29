package Client.ClientView;

/**
 * 
 * @author ozanf
 * 
 */

import java.util.ArrayList;

import Client.ClientModel.ClientModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientView {
	private ClientModel model;
	private Stage stage;

	protected BorderPane root;
	private HBox handsBox;
	private Button b;
	

	
	
	
public ClientView(Stage stage, ClientModel model) {
	this.stage = stage;
	this.model = model;
	
	stage.setTitle("Jassen2020!");
	
	root = new BorderPane(); 
	handsBox = new HBox();
	
	// puts the Buttons in hand for PlayingCards from hand.
	for(int i = 0; i <=8; i++) {
		handsBox.getChildren().add(b = new Button("b" + i));
		
	}
	
	
	root.setBottom(handsBox);
	
	
	Scene scene = new Scene(root);
	stage.setScene(scene);
	
}


public void start() {
	stage.show();
}



}