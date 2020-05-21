package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.CardLabel;
import Client.View.LobbyView;
import Common.Messages.Message;
import Common.ServiceLocator;
import Common.Translator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.logging.Logger;

public class LobbyController {
    private ClientModel model;
    private LobbyView lobbyView;
    private Logger log = JassClient.mainProgram.getLogger();
    private ClientCommunication cc = ClientCommunication.getInstance();

    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator t = sl.getTranslator();

    private boolean settingsPopup = false;
    private int maxPoints;

    public LobbyController(ClientModel model, LobbyView lobbyView) {
        this.model = model;
        this.lobbyView = lobbyView;

        lobbyView.getStage().setOnCloseRequest(e -> {
            e.consume();
            showAlertLeavingLobby();
        });

        lobbyView.getBtnStart().setOnAction(e -> {
            cc.sendMessage(new Message(Message.Type.STARTGAME, cc.getUserName(), maxPoints));
            log.info(model.getUserName() + " sent start game request");
        });

        lobbyView.getBtnChat().setOnAction(e -> {
            if(!JassClient.mainProgram.getGameController().isChatPopup()){
                JassClient.mainProgram.getGameController().setChatPopup(true);
                JassClient.mainProgram.startChat();
            }else{
                JassClient.mainProgram.getGameController().setChatPopup(false);
                JassClient.mainProgram.stopChat();
            }
        });

        lobbyView.getBtnSettings().setOnAction( e-> {
            JassClient.mainProgram.getSettingsView().setSettingStageTitle();
            JassClient.mainProgram.startSettings();
        });

    }


    public void updateLobby() {
        lobbyView.getPlayersInLobby().setText("");
        for (String s : model.getPlayerNames()) {
            lobbyView.getPlayersInLobby().appendText(s + "\n");
            System.out.println(s);
        }
    }

    public void showAlertLessPlayer(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(t.getString("lobby.alert.lessPlayer.title"));
                alert.setHeaderText(t.getString("lobby.alert.lessPlayer.header"));
                alert.setContentText(t.getString("lobby.alert.lessPlayer.content"));
                alert.showAndWait();
            }
        });
    }

    public void showAlertLeavingLobby(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lobby");
        alert.setHeaderText("Exit Lobby");
        alert.setContentText("Are you sure u want to exit the Lobby?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            cc.sendMessage(new Message(Message.Type.LOGOUT,model.getUserName()));
            JassClient.mainProgram.stopLobby();
            Platform.exit();
            System.exit(0);
        }
    }
    // Getters and Setters

    public boolean isSettingsPopup() {
        return settingsPopup;
    }

    public void setSettingsPopup(boolean settingsPopup) {
        this.settingsPopup = settingsPopup;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
