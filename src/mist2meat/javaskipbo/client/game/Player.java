package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;

public class Player {
	
	public ArrayList<CardSlot> deckslots = new ArrayList<CardSlot>();
	public byte id;
	public String name;
	
	public Player(byte id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void setDeckCard(int deckid, int card) {
		deckslots.get(deckid).setCard(new Card(card));
	}
	
	public void setDeckSlot(int id, CardSlot slot) {
		deckslots.set(id, slot);
	}
}
