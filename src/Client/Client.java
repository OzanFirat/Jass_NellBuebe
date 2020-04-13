/**
 * 
 * Client for Jassen group NellBuebe
 * 
 * @author ozanf
 * 
 */

package Client;



import Client.ClientController.ClientController;
import Client.ClientModel.ClientModel;
import Client.ClientView.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;



public class Client extends Application{
	public ClientView view;
	public ClientController controller;
	public ClientModel model;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Initialize the GUI
		model = new ClientModel();
		view = new ClientView(primaryStage, model);
		controller = new ClientController(model, view);

		// Display the GUI after all initialization is complete
		view.start();
	}

}
