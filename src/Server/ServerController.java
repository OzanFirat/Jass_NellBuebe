package Server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ServerController {

    private ServerModel  serverModel;
    private ServerView serverView;



    public ServerController(ServerModel serverModel,ServerView serverView ){
        this.serverModel = serverModel;
        this.serverView = serverView;

        serverView.startBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //ServerMain sMain = new ServerMain();
                //sMain.acceptConnections();
                serverModel.startServer();
                changeStartServerLabel();
                serverView.startBtn.setDisable(true);
                //serverView.startBtn.setText(t.getString("server is running"));
            }
        });
        serverView.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    // change lblName to Server is running
    public void changeStartServerLabel(){
        serverView.startBtn.setText("Server is running!");
    }
}
