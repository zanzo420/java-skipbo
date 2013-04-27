package mist2meat.javaskipbo.client.game;

import mist2meat.javaskipbo.game.Card;

public class LocalPlayer {

	public static Card deckCard;
	public static byte id;
	
	public static void setDeckCard(Card card) {
		deckCard = card;
	}
}
