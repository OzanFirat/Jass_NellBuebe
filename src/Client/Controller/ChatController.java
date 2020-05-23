package Client.Controller;

import Client.JassClient;
import Client.Model.ClientModel;
import Client.View.ChatView;
import Common.Messages.Message;

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
        });

        chatView.exitChatButton.setOnAction(e->{
            JassClient.mainProgram.getGameController().setChatPopup(false);
            JassClient.mainProgram.stopChat();
        });


        // key events for the chatView in gameScene
        chatView.messageEntry.setOnKeyPressed(e->{
            switch (e.getCode()){
                case ESCAPE:
                    JassClient.mainProgram.getGameController().setChatPopup(false);
                    JassClient.mainProgram.stopChat();
                    break;
                case ENTER:
                    sendMessage();
                case BACK_SPACE:
                    chatView.messageEntry.deletePreviousChar();
            }
            e.consume();
        });

        chatView.getScene().setOnKeyPressed(e-> {
            switch (e.getCode()){
                case ESCAPE:
                    JassClient.mainProgram.getGameController().setChatPopup(false);
                    JassClient.mainProgram.stopChat();
                    break;
                case ENTER:
                    sendMessage();
            }
            e.consume();
        });

        chatView.getChatStage().setOnCloseRequest(e->{
            chatView.stop();
            JassClient.mainProgram.getGameController().setChatPopup(false);
        });
    }
    public void sendMessage(){
        ClientCommunication cc = ClientCommunication.getInstance();
        Message msg = new Message(Message.Type.CHATMESSAGE, chatView.messageEntry.getText());
        cc.sendMessage(msg);
        log.info("The following message has been successfully sent" + chatView.messageEntry.getText());
        chatView.messageEntry.setText("");
    }

    public void updateChatView(String s ){
        chatView.chatHistory.appendText(s + "\n");
    }

    public void updateChatEntry(){
        chatView.chatHistory.setText("");
        for (String s : model.getPlayerNames()) {
            chatView.chatHistory.appendText(s + " has entered the chat \n");
        }
    }
}
