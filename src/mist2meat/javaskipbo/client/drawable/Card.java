package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;

public class Card {
	
	byte wildnum;
	byte num;
	
	Image img;
	Image wildimg;
	
	boolean rotated;
	boolean hidden = false;
	
	float x,y,w,h;
	
	public Card(int num) {
		this.num = (byte)num;
		this.img = GameWindow.cards.get(num);
	}
	
	public void draw() {
		if(!hidden){
			if(rotated){
				img.setCenterOfRotation(0, 0);
				img.setRotation(90);
				img.draw(x-2 ,y+2, w-4, h-4);
			}else{
				img.draw(x+2 ,y+2, w-4, h-4);
			}
			img.setRotation(0);
			if(wildimg != null){
				drawWildCard();
			}
		}
	}
	
	public void drawWildCard() {
		if(!hidden){
			wildimg.setAlpha(0.2f);
			if(rotated){
				wildimg.setCenterOfRotation(0, 0);
				wildimg.setRotation(90);
				wildimg.draw(x-2 ,y+2, w-4, h-4);
			}else{
				wildimg.draw(x+2 ,y+2, w-4, h-4);
			}
			wildimg.setAlpha(1f);
			wildimg.setRotation(0);
		}
	}
	
	public void setWildNum(int wild) {
		wildnum = (byte)wild;
		wildimg = GameWindow.cards.get(wild);
	}
	
	public byte getWildNum() {
		return wildnum;
	}

	public void setNum(int card) {
		num = (byte)card;
	}
	
	public byte getNum() {
		return num;
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
	}

	public void setRotated(boolean rot) {
		rotated = rot;
	}

	public void setHidden(boolean hide) {
		hidden = hide;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
