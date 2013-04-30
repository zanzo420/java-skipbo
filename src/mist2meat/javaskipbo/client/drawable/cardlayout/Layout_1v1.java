package mist2meat.javaskipbo.client.drawable.cardlayout;

import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.client.game.Player;
import mist2meat.javaskipbo.client.game.PlayerManager;

public class Layout_1v1 extends Layout {
	
	@Override
	public void build() {
		super.build(); // builds the middle decks
		
		float cw = getCardWidth();
		float ch = getCardHeight();
		
		float xpos;
		float ypos;
		int gap = 10;

		CardSlot slot;
		
		//LocalPlayer slots
		xpos = 300;
		ypos = 600-ch-gap;
		
		slot = new CardSlot(xpos,ypos+(ch/2)-(cw/2),true);
		LocalPlayer.addDeckSlot(slot);
		addSlot(slot);
		
		xpos += gap;
		
		slot = new CardSlot(xpos,ypos);
		LocalPlayer.addDeckSlot(slot);
		addSlot(slot);
		
		for(int i = 1; i < 4; i++){
			xpos += cw+gap;
			
			slot = new CardSlot(xpos,ypos);
			LocalPlayer.addDeckSlot(slot);
			addSlot(slot);
		}
		
		//LocalPlayer hand slots
		xpos = gap;
		ypos = 350;
		for(int i = 0; i < 5; i++){
			slot = new CardSlot(xpos,ypos+(i*30));
			slot.setHidden(true);
			LocalPlayer.addHandSlot(slot);
			addSlot(slot);
		}
		
		//Player 2 slots
		Player player = PlayerManager.players.get(0);
		
		xpos = 300+(gap*4)+(cw*4);
		ypos = gap;
		
		slot = new CardSlot(xpos,ypos+(ch/2)-(cw/2),true);
		player.addDeckSlot(slot);
		addSlot(slot);
		
		xpos -= gap+ch+cw;
		
		slot = new CardSlot(xpos,ypos);
		player.addDeckSlot(slot);
		addSlot(slot);
		
		for(int i = 1; i < 4; i++){
			xpos -= cw+gap;
			
			slot = new CardSlot(xpos,ypos);
			player.addDeckSlot(slot);
			addSlot(slot);
		}
	}
}
