package Server;

import Common.ServiceLocator;
import Common.Translator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class ServerController {

    private ServerModel  serverModel;
    private ServerView serverView;

    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator t = sl.getTranslator();

    public ServerController(ServerModel serverModel,ServerView serverView ){
        this.serverModel = serverModel;
        this.serverView = serverView;

        serverView.btnStartServer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //ServerMain sMain = new ServerMain();
                //sMain.acceptConnections();
                serverModel.startServer();
                serverView.btnStartServer.setDisable(true);
                changeStartServerLabel();
                //serverView.startBtn.setText(t.getString("server is running"));
            }
        });
        serverView.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.exit(0);
            }
        });
    }


    // change lblName to "Server is running" or "Server läuft"
    public void changeStartServerLabel(){
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();
        serverView.btnStartServer.setText(t.getString("server.button.runningServer"));
    }
}
