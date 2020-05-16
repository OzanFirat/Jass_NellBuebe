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

import java.util.logging.Logger;

public class LobbyController {
    private ClientModel model;
    private LobbyView lobbyView;
    private Logger log = JassClient.mainProgram.getLogger();
    private ClientCommunication cc = ClientCommunication.getInstance();

    private ServiceLocator sl = ServiceLocator.getServiceLocator();
    private Translator t = sl.getTranslator();

    public LobbyController(ClientModel model, LobbyView lobbyView) {
        this.model = model;
        this.lobbyView = lobbyView;

        lobbyView.getBtnStart().setOnAction(e -> {

            cc.sendMessage(new Message(Message.Type.STARTGAME, cc.getUserName() + "has started the game"));
            log.info(model.getUserName() + " sent start game request");
        });

        lobbyView.getBtnChat().setOnAction(e -> {
            JassClient.mainProgram.startChat();
        });

    }

    // TODO Call after message, not button click - Do smth to avoid NullPointerException if no value
    public void setCardStyle() {
        if (lobbyView.getCbCardStyle().getValue() != null) {
            switch (lobbyView.getCbCardStyle().getValue().toString()) {
                case "FR":
                    JassClient.mainProgram.getGameView().setStyle(CardLabel.Style.FR);
                    break;
                case "DE":
                    JassClient.mainProgram.getGameView().setStyle(CardLabel.Style.DE);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + lobbyView.getCbCardStyle().getValue());
            }
        }
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
}
