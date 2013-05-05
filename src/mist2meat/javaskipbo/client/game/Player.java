package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.enums.CardSlotType;

public class Player {
	
	public ArrayList<CardSlot> deckslots = new ArrayList<CardSlot>();
	public byte id;
	
	private String name;
	
	private boolean turn = false;
	
	public Player(byte id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDeckCard(int deckid, int card) {
		deckslots.get(deckid).setCard(new Card(card));
	}
	
	public void addDeckSlot(CardSlot slot) {
		slot.setDeckID((byte)deckslots.size());
		deckslots.add(slot);
		
		slot.setOwner(id);
		slot.setType(CardSlotType.PLAYER_SLOT);
	}

	public void setTurn(boolean b) {
		turn = b;
	}
	
	public boolean isMyTurn() {
		return turn;
	}
}
