/**
 * 
 * Client for Jassen group NellBuebe
 * 
 * @author ozanf
 * 
 */

package Client;



import javafx.application.Application;
import javafx.stage.Stage;



public class Client extends Application{
	private ClientView view;
	private ClientController controller;
	private ClientModel model;
	
	public static void main(String[] args) {
		launch(args);
		System.out.println("Git 1st try");

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
