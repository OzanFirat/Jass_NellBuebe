package Client.Controller;

import Client.Model.ClientModel;
import Client.View.ChatView;

public class ChatController {
    private ClientModel model;
    private ChatView chatView;

    public ChatController(ClientModel model, ChatView chatView) {
        this.model = model;
        this.chatView = chatView;
    }

}
