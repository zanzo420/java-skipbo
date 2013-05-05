package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;


public class CardSlot {

	Card card;
	Image img;
	
	float x,y,w,h; // Draw pos
	float xpos,ypos; // actual pos
	
	boolean hidden = false;
	boolean touchable = true;
	boolean rotated;
	boolean hasCardUnder = false;
	
	byte owner;
	byte id;
	byte type;
	
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
	
	public void setOwner(byte own){
		owner = own;
	}
	
	public void setDeckID(byte i){
		id = i;
	}
	
	public byte getOwner() {
		return owner;
	}
	
	public byte getDeckID() {
		return id;
	}
	
	public void setCard(Card card) {		
		this.card = card;
		
		setPos(x,y); // update
		setSize(w,h);
		setRotated(rotated);
	}
	
	public void setHidden(boolean hide) {
		hidden = hide;
	}
	
	public void draw() {
		if(!hidden){
			if(rotated){
				img.setCenterOfRotation(0, 0);
				img.setRotation(90);
			}
			img.draw(x ,y, w, h);
			img.setRotation(0);
			
			if(hasCardUnder){
				Image i = GameWindow.cards.get(0);
				if(rotated){
					i.setCenterOfRotation(0, 0);
					i.setRotation(90);
					i.draw(x-2 ,y+2, w-4, h-4);
				}else{
					i.draw(x+2 ,y+2, w-4, h-4);
				}
				i.setRotation(0);
			}
		}
		
		if(card != null){
			card.draw();
		}
	}
	
	public void setHasCardUnder(boolean has) {
		hasCardUnder = has;
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
	
	public Card getCard() {
		return card;
	}
	
	public boolean hasCard() {
		return !(this.card == null);
	}
	
	public void setCanTouch(boolean b) {
		touchable = b;
	}
	
	public boolean canTouch() {
		return touchable;
	}

	public void setType(byte typ) {
		type = typ;
	}
	
	public byte getType() {
		return type;
	}
}
