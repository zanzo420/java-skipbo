package mist2meat.javaskipbo.client.drawable.cardlayout;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.client.game.Game;

public class Layout {

	ArrayList<CardSlot> cardslots = new ArrayList<CardSlot>();
	
	float w = 0;
	float h = 0;
	
	public float xscale = 1;
	public float yscale = 1;
	
	float cardWidth = 90;
	float cardHeight = 150;
	
	public void drawCardSlots() {
		for(CardSlot slot : cardslots) {
			slot.draw();
		}
	}
	
	public void update(float width, float height) {
		w = width;
		h = height;
		
		xscale = width / Main.scrw;
		yscale = height / Main.scrh;
		
		for(CardSlot slot : cardslots) {
			slot.setPos(slot.getXPos(), slot.getYPos());
			slot.setSize(getCardWidth(), getCardHeight());
		}
	}
	
	public float getCardWidth() {
		return cardWidth;
	}
	
	public float getCardHeight() {
		return cardHeight;
	}
	
	public void addSlot(CardSlot slot) {
		cardslots.add(slot);
	}
	
	public void build() {
		float xpos = (Main.scrw/2)-15-getCardWidth()*2;
		float ypos = (Main.scrh/2)-getCardHeight()/2;
		
		CardSlot slot;
		
		//middle decks
		for(int i = 0; i < 4; i++){
			slot = new CardSlot(xpos,ypos);
			Game.middleDecks.add(slot);
			addSlot(slot);
			slot.setCanTouch(false);
			
			slot.setOwner((byte)100);
			slot.setDeckID((byte)i);
			
			xpos += getCardWidth();
			xpos += 10;
		}
		
		//deck
		xpos += getCardHeight();
		xpos += 10;
		
		ypos += (getCardHeight()/2)-(getCardWidth()/2);
		
		slot = new CardSlot(xpos,ypos,true);
		slot.setCard(new Card(0));
		
		Game.deck = slot;
		addSlot(slot);
		slot.setCanTouch(false);
		
		slot.setOwner((byte)100);
		slot.setDeckID((byte)5);
	}

	public boolean changed(int width, int height) {
		if(w == width && h == height){
			return false;
		}
		return true;
	}

	public ArrayList<CardSlot> getSlotsAt(int x, int y) {
		ArrayList<CardSlot> list = new ArrayList<CardSlot>();
		
		for(CardSlot slot : cardslots) {
			if(slot.getRotated()){
				if(x <= slot.getX()*xscale && y >= slot.getY()*yscale){
					if(x >= (slot.getX()*xscale)-(getCardHeight()*yscale) && y <= (slot.getY()*yscale)+(getCardWidth()*xscale)){
						list.add(slot);
					}
				}
			}else{
				if(x >= slot.getX()*xscale && y >= slot.getY()*yscale){
					if(x <= (slot.getX()*xscale)+(getCardWidth()*xscale) && y <= (slot.getY()*yscale)+(getCardHeight()*yscale)){
						list.add(slot);
					}
				}
			}
		}
		
		return list;
	}

	public void drawPlayerNames(Graphics g) {
		// override this in the layout
	}
}
