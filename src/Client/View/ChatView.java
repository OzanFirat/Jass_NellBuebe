package Client.View;

import Client.Model.ClientModel;
import javafx.stage.Stage;

public class ChatView {
    private Stage chatStage;
    private ClientModel model;
    public ChatView(Stage chatStage, ClientModel model) {
        this.chatStage = chatStage;
        this.model = model;
    }
}
