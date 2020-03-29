package Server.ServerController;

import Server.ServerModel.ServerModel;
import Server.ServerView.ServerView;

/**
 * 
 * 
 * @author ozanf
 *
 */

public class ServerController {
	
	ServerView view;
	ServerModel model;

	public ServerController(ServerModel model, ServerView view) {
		this.model = model;
		this.view = view;
	
		
	}


}
