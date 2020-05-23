package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.GameOverView;
import Common.Messages.Message;
import javafx.application.Platform;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameOverController {
    private ClientModel model;
    private GameOverView gameOverView;

    private ClientCommunication cc = ClientCommunication.getInstance();


    public GameOverController(ClientModel model, GameOverView gameOverView) {
        this.model = model;
        this.gameOverView = gameOverView;

        gameOverView.btnBackToLobby.setOnAction( e -> {
            gameOverView.stop();
            JassClient.mainProgram.startLobby();
            cc.sendMessage(new Message(Message.Type.BACKTOLOBBY, model.getUserName()));
            JassClient.mainProgram.resetClientModel();
            JassClient.mainProgram.getGameView().removeFromRootJassGame(JassClient.mainProgram.getGameView().getvBoxTrumpf());
        });

        gameOverView.btnExit.setOnAction( e -> {
            cc.sendMessage(new Message(Message.Type.CLIENTLOST, model.getUserName()));
            Platform.exit();
            System.exit(0);
        });
    }
}
