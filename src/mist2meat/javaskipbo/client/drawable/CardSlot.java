package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;


public class CardSlot {

	Card card;
	Image img;
	
	float x,y,w,h; // Draw pos
	float xpos,ypos; // actual pos
	
	boolean rotated;
	
	public CardSlot(float x, float y) {
		img = GameWindow.cardSlotImage;
		xpos = x;
		ypos = y;
		setPos(x,y);
		setRotated(false);
	}
	
	public CardSlot(float x, float y, boolean rotated) {
		img = GameWindow.cardSlotImage;
		xpos = x;
		ypos = y;
		setPos(x,y);
		setRotated(rotated);
	}
	
	public void setCard(Card card) {
		this.card = card;
		
		setPos(x,y); // update
		setSize(w,h);
		setRotated(rotated);
	}
	
	public void draw() {
		if(rotated){
			img.setCenterOfRotation(0, 0);
			img.setRotation(90);
		}
		img.draw(x ,y, w, h);
		img.setRotation(0);
		
		if(card != null){
			card.draw();
		}
	}
	
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		if(card != null) {
			card.setPos(x,y);
		}
	}
	
	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
		if(card != null) {
			card.setSize(w,h);
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getXPos() {
		return xpos;
	}
	
	public float getYPos() {
		return ypos;
	}
	
	public boolean getRotated() {
		return rotated;
	}
	
	public void setRotated(boolean rot) {
		this.rotated = rot;
		if(card != null){
			card.setRotated(rot);
		}
	}
}
