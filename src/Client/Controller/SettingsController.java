package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.CardLabel;
import Client.View.SettingsView;
import Common.Messages.Message;

import java.util.logging.Logger;

public class SettingsController {
    private ClientModel model;
    private SettingsView settingsView;
    private Logger log;
    private ClientCommunication cc = ClientCommunication.getInstance();

    public SettingsController(ClientModel model, SettingsView settingsView) {
        this.model = model;
        this.settingsView = settingsView;
        log = JassClient.mainProgram.getLogger();

        settingsView.btnStartGame.setOnAction( e -> {
            int maxPoints = (int) settingsView.getCbMaxPoints().getValue();
            cc.sendMessage(new Message(Message.Type.STARTGAME, maxPoints));
            settingsView.stop();
        });

    }

    public void setCardStyle() {
        if (settingsView.getCbCardStyle().getValue() != null) {
            switch (settingsView.getCbCardStyle().getValue().toString()) {
                case "FR":
                    JassClient.mainProgram.getGameView().setStyle(CardLabel.Style.FR);
                    break;
                case "DE":
                    JassClient.mainProgram.getGameView().setStyle(CardLabel.Style.DE);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + settingsView.getCbCardStyle().getValue());
            }
        }
    }
}
