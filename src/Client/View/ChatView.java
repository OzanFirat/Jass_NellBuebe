package Client.View;

import Client.Model.ClientModel;
import Common.ServiceLocator;
import Common.Translator;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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


    //Elements to display the languageSetting
    public ChoiceBox<String> choiceBoxLanguageChatView;


    public ChatView(Stage chatStage, ClientModel model) {
        this.chatStage = chatStage;
        this.model = model;

        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        // defined languages
        choiceBoxLanguageChatView = new ChoiceBox<>();
        choiceBoxLanguageChatView.setValue("DE");
        choiceBoxLanguageChatView.getItems().add("EN");
        choiceBoxLanguageChatView.getItems().add("DE");
        choiceBoxLanguageChatView.setTranslateX(300);


        messageEntry = new TextArea();
        messageEntry.setMinHeight(40);
        messageEntry.setMaxHeight(50);
        messageEntry.setEditable(true);


        chatHistory  = new TextArea("");
        chatHistory.setMinHeight(400);
        chatHistory.setMaxHeight(450);
        chatHistory.setEditable(false);
        enterMessageLbl = new Label(t.getString("chat.lbl.enterMessage"));

        exitChatButton = new Button(t.getString("chat.lbl.exitButton"));
        sendButton = new Button(t.getString("chat.lbl.sendButton"));
        sendButton.setId("sendButton");

        HBox hB = new HBox();
        hB.getChildren().addAll(exitChatButton,sendButton, choiceBoxLanguageChatView);

        VBox vb = new VBox();
        vb.getChildren().addAll(chatHistory,enterMessageLbl,messageEntry,hB);

        BorderPane bp = new BorderPane();
        bp.setCenter(vb);

        choiceBoxLanguageChatView.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == "DE" || newValue == "GER") {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[1].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[1].getLanguage()));
                updatChatViewViewTexts();
            } else {
                sl.getConfiguration().setLocalOption("Language", sl.getLocales()[0].getLanguage());
                sl.setTranslator(new Translator(sl.getLocales()[0].getLanguage()));
                updatChatViewViewTexts();
            }
        });

        scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getResource("jass.css").toExternalForm());
        chatStage.setScene(scene);

        chatStage.setHeight(530);
        chatStage.setWidth(500);
        chatStage.setResizable(false);
        chatStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("images/logo.png")));

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

    protected void updatChatViewViewTexts() {
        ServiceLocator sl = ServiceLocator.getServiceLocator();
        Translator t = sl.getTranslator();

        // Menus
        enterMessageLbl.setText(t.getString("chat.lbl.enterMessage"));
        exitChatButton.setText(t.getString("chat.lbl.exitButton"));
        sendButton.setText(t.getString("chat.lbl.sendButton"));

    }
}
