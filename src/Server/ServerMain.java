package Server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerMain  extends Application {

    protected ServerModel serverModel;
    protected ServerView serverView ;
    protected ServerController serverController;

    //logger for notifications
    protected Logger log = Logger.getLogger("");
    public static ServerMain sMain;

    public static void main(String[] args){
        launch(args);
    }

    public void init(){
        if(sMain == null){
            sMain = this;
        }else{
            Platform.exit();
        }
    }

    public Logger getLogger(){
        return log;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {


        // TODO needs to be optimized by LEVIN !!!!!!!!


        serverModel = new ServerModel(55555);
        serverView = new ServerView(primaryStage, serverModel);
        serverController = new ServerController(serverModel, serverView);
        serverView.start();
    }

    public void stop() {
        if (serverView != null)
            serverView.stop();
    }

    public ServerModel getServerModel(){
        return serverModel;
    }


}