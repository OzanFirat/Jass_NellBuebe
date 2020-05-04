package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.SettingsView;

import java.util.logging.Logger;

public class SettingsController {
    private ClientModel model;
    private SettingsView settingsView;
    private Logger log;

    public SettingsController(ClientModel model, SettingsView settingsView) {
        this.model = model;
        this.settingsView = settingsView;
        log = JassClient.mainProgram.getLogger();

        settingsView.btnChooseTrumpf.get(0).setOnMouseClicked( e -> {
            // model.setTrumpf(Card.Suit.Clubs);
            log.info("Trumpf Selected -> Clubs");
        });

        settingsView.btnChooseTrumpf.get(1).setOnMouseClicked( e -> {
            // model.setTrumpf(Card.Suit.Hearts);
            log.info("Trumpf Selected -> Hearts");
        });

        settingsView.btnChooseTrumpf.get(2).setOnMouseClicked( e -> {
            // model.setTrumpf(Card.Suit.Diamonds);
            log.info("Trumpf Selected -> Diamonds");
        });

        settingsView.btnChooseTrumpf.get(3).setOnMouseClicked( e -> {
            // model.setTrumpf(Card.Suit.Diamonds);
            log.info("Trumpf selected -> Spades");
        });

        settingsView.btnStartGame.setOnAction( e -> {
            // model.createGame();
            JassClient.mainProgram.startGame();
            JassClient.mainProgram.stopSettings();
            log.info("Game has started");
        });

    }
}
