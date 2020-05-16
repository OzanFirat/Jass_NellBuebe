package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.LoginView;
import Common.Messages.Message;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.scene.control.Alert;


import java.util.logging.Logger;

public class LoginController {
    private ClientModel model;
    private LoginView loginView;
    private Logger log = JassClient.mainProgram.getLogger();
    private ClientCommunication cc = ClientCommunication.getInstance();
    private boolean loginSuccessful;
    int requestConnectionCounter = 0;

    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator t = sl.getTranslator();


    public LoginController(ClientModel model, LoginView loginView) {
        this.model = model;
        this.loginView = loginView;

        loginView.btnLogIn.setOnAction( e -> {

            if(loginView.inputUserName.getText().isEmpty()){
                showAlertUserNameIsEmpty();
            }else {
                connectToServer();
                log.info("Player "+loginView.inputUserName.getText() +" has started login request");
                requestConnectionCounter++;

            }
        });
    }


    private void connectToServer(){
        cc.setServer(loginView.inputIpAdress.getText());
        cc.setUserName(loginView.inputUserName.getText());
        if(cc.start()){
            startLogInRequest();
        }
    }


    private void startLogInRequest(){
        String userName = loginView.inputUserName.getText();
        Message msg = new Message(Message.Type.LOGIN, userName);
        cc.sendMessage(msg);
    }


    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public void setLoginSuccessful(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }



    public void showAlertConnectionFailed(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(t.getString("login.alert.connectionFailed.title"));
                alert.setHeaderText(t.getString("login.alert.connectionFailed.header"));
                alert.setContentText(t.getString("login.alert.connectionFailed.content"));
                alert.showAndWait();
            }
        });
    }

    public void showAlertUserNameIsEmpty(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(t.getString("login.alert.UserNameIsEmpty.title"));
                alert.setHeaderText(t.getString("login.alert.UserNameIsEmpty.header"));
                alert.setContentText(t.getString("login.alert.UserNameIsEmpty.content"));
                alert.showAndWait();
            }
        });
    }

    public void showAlertLoginRejectedSameNameUsed() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Rejected\n");
                alert.setHeaderText("This Username is already taken. \n");
                alert.setContentText("Try again with a different username");
                alert.showAndWait();
            }
        });
    }

    public void showAlertLoginRejectedTooManyPlayers() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Rejected\n");
                alert.setHeaderText("LobbyRoom has already 4 players\n");
                alert.setContentText("Wait until game is over !");
                alert.showAndWait();
            }
        });
    }

    public void loginAccepted() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                JassClient.mainProgram.stopLogin();
                JassClient.mainProgram.startLobby();
            }
        });
    }

    public void showAlertTooManyPlayers(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(t.getString("login.alert.gameIsFull.title"));
                alert.setHeaderText(t.getString("login.alert.gameIsFull.header"));
                alert.setContentText(t.getString("login.alert.gameIsFull.content"));
                alert.showAndWait();
            }
        });
    }
}