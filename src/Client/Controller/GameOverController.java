package Client.Controller;

import Client.Model.ClientModel;
import Client.View.GameOverView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameOverController {
    private ClientModel model;
    private GameOverView gameOverView;



    public GameOverController(ClientModel model, GameOverView gameOverView) {
        this.model = model;
        this.gameOverView = gameOverView;
    }




}
