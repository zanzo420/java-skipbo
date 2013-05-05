package mist2meat.javaskipbo.client.drawable.cardlayout;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import mist2meat.javaskipbo.Main;
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
		xpos = (Main.scrw/2)-(gap*2.5f)-(cw*2);
		ypos = Main.scrh-ch-gap;
		
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
		ypos = Main.scrh-250;
		for(int i = 0; i < 5; i++){
			slot = new CardSlot(xpos,ypos+(i*30));
			slot.setHidden(true);
			LocalPlayer.addHandSlot(slot);
			addSlot(slot);
		}
		
		//Player 2 slots
		Player player = PlayerManager.players.get(0);
		
		xpos = (Main.scrw/2)+(gap*4)+(cw*3.5f);
		ypos = gap;
		
		slot = new CardSlot(xpos,ypos+(ch/2)-(cw/2),true);
		player.addDeckSlot(slot);
		addSlot(slot);
		slot.setCanTouch(false);
		
		xpos -= gap+ch+cw;
		
		slot = new CardSlot(xpos,ypos);
		player.addDeckSlot(slot);
		addSlot(slot);
		slot.setCanTouch(false);
		
		for(int i = 1; i < 4; i++){
			xpos -= cw+gap;
			
			slot = new CardSlot(xpos,ypos);
			player.addDeckSlot(slot);
			addSlot(slot);
			slot.setCanTouch(false);
		}
	}
	
	@Override
	public void drawPlayerNames(Graphics g) {
		float cw = getCardWidth();
		float ch = getCardHeight();
		
		g.setColor(LocalPlayer.myTurn ? Color.red : Color.white);
		g.drawString(LocalPlayer.getName(), Main.scrw/2-25-cw*2-ch, Main.scrh-38);
		
		Player pl = PlayerManager.players.get(0);
		
		g.setColor(pl.isMyTurn() ? Color.red : Color.white);
		g.drawString(pl.getName(), Main.scrw/2+40+cw*3.5f-ch, 20);
		
		g.setColor(Color.white);
	}
}
