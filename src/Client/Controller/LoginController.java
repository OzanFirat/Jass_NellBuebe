package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.LoginView;
import Common.Message;


import java.util.logging.Logger;

public class LoginController {
    private ClientModel model;
    private LoginView loginView;
    private Logger log = JassClient.mainProgram.getLogger();
    private ClientCommunication cc = ClientCommunication.getInstance();
    private boolean loginSuccessful;
    int requestConnectionCounter = 0;




    public LoginController(ClientModel model, LoginView loginView) {
        this.model = model;
        this.loginView = loginView;

        loginView.btnLogIn.setOnAction( e -> {

            if (requestConnectionCounter == 0) {
                connectToServer();
            }
            // Communication part Levin
            startLogInRequest();
            log.info("Player "+loginView.inputUserName.getText() +" has started login request");
            requestConnectionCounter++;
        });

    }



    private void connectToServer(){
        // optimized
        cc.setServer(loginView.inputIpAdress.getText());
        cc.setUserName(loginView.inputUserName.getText());
        cc.start();
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
}
