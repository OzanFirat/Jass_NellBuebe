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
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientView {
	private ClientModel model;
	private Stage stage;

	private Scene sceneCreateGame;
	private Scene sceneJassGame;
	private final double sceneWidth = 1400;
	private final double sceneHeight = 800;

	public JassGameRoot rootGame;
	public CreateGameRoot rootCreateGame;





	//Define the image for the background
	private Image background = new Image(getClass().getClassLoader().getResourceAsStream("images/background_1400x800.png"));




	public ClientView(Stage stage, ClientModel model) {
		this.stage = stage;
		this.model = model;

		// Import the image for the background - used by many stages so it's in the constructor
		rootCreateGame = new CreateGameRoot(model);
		rootCreateGame.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
		sceneCreateGame = new Scene(rootCreateGame, sceneWidth, sceneHeight);
		sceneCreateGame.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());


		stage.setScene(sceneCreateGame);
		stage.setResizable(false);
	}


	public void changeToGameScene() {
		rootGame = new JassGameRoot(model);
		rootGame.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));
		sceneJassGame = new Scene(rootGame, sceneWidth, sceneHeight);
		sceneJassGame.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
		stage.setScene(sceneJassGame);
	}

	public void start() {
		stage.show();
	}


}