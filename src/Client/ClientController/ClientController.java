package Client.ClientController;

import Client.ClientModel.ClientModel;
import Client.ClientView.ClientView;
import Client.ClientModel.Card;
import Client.ClientView.CardLabel;

/**
 * 
 * 
 * @author ozanf
 *
 */

public class ClientController {

	private ClientModel model;
	private ClientView view;
	
	public ClientController(ClientModel model, ClientView view) {
		 this.model = model;
		 this.view = view;


		 view.rootCreateGame.btnChooseTrumpf.get(0).setOnMouseClicked( e -> {
		 	model.setTrumpf(Card.Suit.Clubs);
		 });

		 view.rootCreateGame.btnChooseTrumpf.get(1).setOnMouseClicked( e -> {
		 	model.setTrumpf(Card.Suit.Hearts);
		 });

		 view.rootCreateGame.btnChooseTrumpf.get(2).setOnMouseClicked( e -> {
		 	model.setTrumpf(Card.Suit.Diamonds);
		 });

		 view.rootCreateGame.btnChooseTrumpf.get(3).setOnMouseClicked( e -> {
		 	model.setTrumpf(Card.Suit.Spades);
		 });

		 view.rootCreateGame.btnStartGame.setOnAction( e ->{
		 	model.createGame();
		 	view.changeToGameScene();
		 	handleGameAction();
		 });


	}

	private void handleGameAction() {
		for (CardLabel c : view.rootGame.yourCards) {
			c.setOnMouseEntered( e -> {
				c.getStyleClass().add("card-label-hover");
			});

			c.setOnMouseExited( e -> {
				c.getStyleClass().clear();
			});

			c.setOnMouseClicked(e -> {
				view.rootGame.doAnimationPlayYourCard(c);
			});
		}

		view.rootGame.btnRemove.setOnAction( e-> {
			view.rootGame.removeCardsInMiddle();
		});

		view.rootGame.btnRemake.setOnAction( e-> {
			view.rootGame.setOpponentCards();
		});


	}


}
