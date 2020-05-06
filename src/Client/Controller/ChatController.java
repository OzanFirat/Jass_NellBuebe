package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.ChatView;
import Common.Message;
import javafx.scene.input.KeyEvent;

import java.util.logging.Logger;

public class ChatController {
    private ClientModel model;
    private ChatView chatView;
    private Logger log ;

    public ChatController(ClientModel model, ChatView chatView) {
        this.model = model;
        this.chatView = chatView;
        log = JassClient.mainProgram.getLogger();


    chatView.sendButton.setOnAction(e-> {
        sendMessage();
        chatView.messageEntry.setText("");
    });

        chatView.exitChatButton.setOnAction(e->{
        JassClient.mainProgram.stopChat();
    });


    // key events for the chatView in gameScene
        chatView.messageEntry.setOnKeyReleased(e->{
        switch (e.getCode()){
            case ESCAPE:
                JassClient.mainProgram.stopChat();
                break;
            case ENTER:
                sendMessage();
                chatView.messageEntry.setText("");
        }
        e.consume();
    });

        chatView.getScene().setOnKeyReleased(e-> {
        switch (e.getCode()){
            case ESCAPE:
                JassClient.mainProgram.stopChat();
                break;
            case ENTER:
                sendMessage();
                chatView.messageEntry.setText("");
        }
        e.consume();
    });
}
    public void sendMessage(){
        ClientCommunication cc = ClientCommunication.getInstance();
        Message msg = new Message(Message.Type.CHATMESSAGE, chatView.messageEntry.getText());
        cc.sendMessage(msg);
        log.info("The following message has been successfully sent" + chatView.messageEntry.getText());
    }

    public void updateChatView(String s ){
        chatView.chatHistory.appendText(s + "\n");
    }

    public void updateChatEntry(){
        chatView.chatHistory.setText("");
        for (String s : model.getPlayerNames()) {
            chatView.chatHistory.appendText(s + " has entered the chat \n");
            System.out.println(s);
        }
    }

    public void handle(KeyEvent kEvt) {
        // TODO all keyEvent comes here by Levin
    }

}
