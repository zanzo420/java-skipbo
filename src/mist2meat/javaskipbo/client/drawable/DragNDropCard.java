package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;

public class DragNDropCard {
	byte num;
	Image img;
	
	boolean rotated;
	boolean hidden = false;
	
	float x,y,w,h;
	
	public DragNDropCard(int num) {
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
		}
	}
	
	public void update() {
		
	}
}
