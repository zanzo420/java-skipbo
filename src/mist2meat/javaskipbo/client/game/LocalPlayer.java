package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;

public class LocalPlayer {

	public static ArrayList<CardSlot> deckslots = new ArrayList<CardSlot>();
	public static byte id;
	public static String name;
	
	public static void setDeckSlot(int id, CardSlot slot) {
		deckslots.set(id, slot);
	}
	
	public static void setDeckCard(int deckid, int card) {
		deckslots.get(deckid).setCard(new Card(card));
	}
}
