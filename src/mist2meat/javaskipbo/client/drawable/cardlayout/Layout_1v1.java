package mist2meat.javaskipbo.client.drawable.cardlayout;

import mist2meat.javaskipbo.client.drawable.CardSlot;

public class Layout_1v1 extends Layout {
	
	@Override
	public void build() {
		//TODO: assign slots to players
		CardSlot slot;
		
		slot = new CardSlot(400,50,true); // TODO: assign
		//player.setslot(id,slot) 0 = deck, 1-4 = free decks
		addSlot(slot);
	}
}
