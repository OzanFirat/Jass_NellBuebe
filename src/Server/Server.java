package Server;

import Server.ServerController.ServerController;
import Server.ServerModel.ServerModel;
import Server.ServerView.ServerView;

/**
 * 
 * @author ozanf
 * 
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class Server extends Application {
	private ServerView view;
	private ServerController controller;
	private ServerModel model;
	
	
	private static void  main(String[] args) {
		launch(args);
	}

	
		

	public void start(Stage primaryStage) throws Exception {
		model = new ServerModel();
		view = new ServerView(primaryStage, model);
		controller = new ServerController(model, view);
		
		view.start();
		
		
	}


}
