package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;

public class LocalPlayer {

	public static ArrayList<CardSlot> deckslots = new ArrayList<CardSlot>();
	public static ArrayList<CardSlot> handslots = new ArrayList<CardSlot>();
	public static byte id;
	public static String name;
	
	public static void addHandSlot(CardSlot slot) {
		handslots.add(slot);
	}
	
	public static void addToHand(byte card) {
		for(CardSlot slot : handslots){
			if(!slot.hasCard()){
				Card placeholdercard = new Card(0);
				placeholdercard.setHidden(true);
				slot.setCard(placeholdercard);
				new AnimatedCard(Game.deck, slot, card);
				break;
			}
		}
	}
	
	public static void addDeckSlot(CardSlot slot) {
		deckslots.add(slot);
	}
	
	public static void setDeckCard(int deckid, int card) {
		deckslots.get(deckid).setCard(new Card(card));
	}
}
