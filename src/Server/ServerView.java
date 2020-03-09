package Server;

/**
 * 
 *@author ozanf
 */

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ServerView {
	public Stage serverStage;
	public ServerModel model;
	public BorderPane serverRoot;
	public TextField port;
	public Button connect;
	

	public ServerView(Stage serverStage, ServerModel model) {
	this.serverStage = serverStage;
	this.model = model;
	
	serverRoot = new BorderPane();
	port = new TextField("1111");
	connect = new Button("Connect");
	
	
	serverRoot.setCenter(port);
	serverRoot.setBottom(connect);
	
	
	Scene serverScene = new Scene(serverRoot);
	serverStage.setScene(serverScene);
	
	
	
	
	
	}

	public void start() {
		serverStage.show();
		
	}
	

}
