package Client.ClientController;

import Client.ClientModel.ClientModel;
import Client.ClientView.ClientView;

/**
 * 
 * 
 * @author ozanf
 *
 */

public class ClientController {

	private ClientModel model;
	 private ClientView view;
	
	public ClientController(ClientModel model, ClientView view) {
		 this.model = model;
		 this.view = view;
	}



}
