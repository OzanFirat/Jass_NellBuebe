package Client.View;

import Client.Model.ClientModel;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatView {

    public ClientModel model;

    public Stage chatStage;
    public Scene scene;

    public Button sendButton;
    public Button exitChatButton;

    public Label enterMessageLbl;
    public TextArea chatHistory;
    public TextArea messageEntry;

    public MenuBar mBar;

    public Menu settingMenu;
    public MenuItem languageEngItem;
    public MenuItem languageGerItem;

    public Menu helpMenu;
    public MenuItem chatInfoItem;



    public ChatView(Stage chatStage, ClientModel model) {
        this.chatStage = chatStage;
        this.model = model;

        //chatStage.setTitle("Jass ChatRoom");

        messageEntry = new TextArea();
        messageEntry.setMinHeight(40);
        messageEntry.setMaxHeight(50);
        messageEntry.setEditable(true);


        chatHistory  = new TextArea("");
        chatHistory.setMinHeight(400);
        chatHistory.setMaxHeight(450);
        chatHistory.setEditable(false);
        enterMessageLbl = new Label("Input Message: ");

        exitChatButton = new Button("Exit");
        sendButton = new Button("Send");
        sendButton.setId("sendButton");

        HBox hB = new HBox();
        hB.getChildren().addAll(exitChatButton,sendButton);

        VBox vb = new VBox();
        vb.getChildren().addAll(chatHistory,enterMessageLbl,messageEntry,hB);

        mBar = new MenuBar();

        settingMenu = new Menu("Settings");
        languageEngItem = new MenuItem("ENG");
        languageGerItem = new MenuItem("GER");
        settingMenu.getItems().addAll(languageEngItem,languageGerItem);

        helpMenu = new Menu("Help");
        chatInfoItem = new MenuItem("Info");
        helpMenu.getItems().add(chatInfoItem);

        BorderPane bp = new BorderPane();
        bp.setTop(mBar);
        bp.setCenter(vb);

        scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        chatStage.setScene(scene);

        chatStage.setHeight(530);
        chatStage.setWidth(500);
        chatStage.setResizable(false);

    }

    // Getters and Setters

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void start() {
        chatStage.setTitle(model.getUserName());
        chatStage.show();
    }

    public void stop() {
        chatStage.hide();
    }
}
