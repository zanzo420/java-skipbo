package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;

public class Card {

	byte num;
	Image img;
	boolean rotated;
	
	float x,y,w,h;
	
	public Card(int num) {
		this.img = GameWindow.cards.get(num);
	}
	
	public void draw() {
		if(rotated){
			img.setCenterOfRotation(0, 0);
			img.setRotation(90);
		}
		img.draw(x ,y, w, h);
		img.setRotation(0);
	}

	public void setNum(int card) {
		num = (byte)card;
	}

	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(float w, float h) {
		this.w = w;
		this.h = h;
	}
}
