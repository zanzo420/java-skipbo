package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;

public class LocalPlayer {

	public static ArrayList<CardSlot> deckslots = new ArrayList<CardSlot>();
	public static ArrayList<CardSlot> handslots = new ArrayList<CardSlot>();
	public static byte id;

	private static String name;
	
	public static boolean myTurn = false;
	
	public static String getName() {
		return name;
	}
	
	public static void setName(String nam) {
		name = nam;
	}
	
	public static void addHandSlot(CardSlot slot) {
		slot.setDeckID((byte)(50+handslots.size()));
		handslots.add(slot);
		
		slot.setOwner(id);
	}
	
	public static void addToHand(int slotnum, byte card) {
		for(CardSlot slot : handslots){
			if(!slot.hasCard() && slot.getDeckID() == 50+slotnum){
				new AnimatedCard(Game.deck, slot, card);
				break;
			}
		}
	}
	
	public static void addDeckSlot(CardSlot slot) {
		slot.setDeckID((byte)deckslots.size());
		deckslots.add(slot);
		
		slot.setOwner(id);
	}
	
	public static void setDeckCard(int deckid, int card) {
		deckslots.get(deckid).setCard(new Card(card));
	}
}
