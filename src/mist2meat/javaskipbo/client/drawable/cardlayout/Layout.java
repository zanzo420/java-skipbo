package mist2meat.javaskipbo.client.drawable.cardlayout;

import java.util.ArrayList;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.client.game.Game;

public class Layout {

	ArrayList<CardSlot> cardslots = new ArrayList<CardSlot>();
	
	float w = 0;
	float h = 0;
	
	float xscale = 1;
	float yscale = 1;
	
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
			if(slot.getRotated()){
				slot.setPos(slot.getXPos() * xscale, slot.getYPos() * yscale);
				slot.setSize(getRotatedCardWidth(), getRotatedCardHeight());
			}else{
				slot.setPos(slot.getXPos() * xscale, slot.getYPos() * yscale);
				slot.setSize(getCardWidth(), getCardHeight());		
			}
		}
	}
	
	public float getCardWidth() {
		return cardWidth * xscale;
	}
	
	public float getCardHeight() {
		return cardHeight * yscale;
	}
	
	public float getRotatedCardWidth() {
		return cardWidth * yscale;
	}
	
	public float getRotatedCardHeight() {
		return cardHeight * xscale;
	}
	
	public void addSlot(CardSlot slot) {
		cardslots.add(slot);
	}
	
	public void build() {
		float xpos = 140;
		float ypos = 230;
		
		CardSlot slot;
		
		for(int i = 1; i <= 4; i++){
			slot = new CardSlot(xpos,ypos);
			Game.middleDecks.add(slot);
			addSlot(slot);
			
			xpos += getCardWidth();
			xpos += 10;
		}
		
		xpos += getCardHeight();
		xpos += 10;
		
		ypos += (getCardHeight()/2)-(getCardWidth()/2);
		
		slot = new CardSlot(xpos,ypos,true);
		Game.deck = slot;
		addSlot(slot);
	}

	public boolean changed(int width, int height) {
		if(w == width && h == height){
			return false;
		}
		return true;
	}

}
