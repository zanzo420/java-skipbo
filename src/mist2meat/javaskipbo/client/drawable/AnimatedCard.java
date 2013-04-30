package mist2meat.javaskipbo.client.drawable;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.GameWindow;

import org.newdawn.slick.Image;

public class AnimatedCard {
	
	private static ArrayList<AnimatedCard> animations = new ArrayList<AnimatedCard>();
	
	Image img;
	
	CardSlot toslot;
	
	float x,y;
	float tox,toy;
	
	float rot,torot;
	
	byte id;
	
	public boolean finished = false;
	
	public AnimatedCard(CardSlot fromslot, CardSlot toslot, byte id) {
		x = fromslot.getX();
		y = fromslot.getY();
		
		rot = fromslot.rotated ? 90 : 0;
		
		tox = toslot.getX();
		toy = toslot.getY();
		
		torot = toslot.rotated ? 90 : 0;
		
		this.toslot = toslot;
		this.id = id;
		
		img = GameWindow.cards.get(id);
		
		animations.add(this);
	}
	
	public void draw() {
		float cw = GameWindow.curLayout.getCardWidth();
		float ch = GameWindow.curLayout.getCardHeight();
		
		img.setCenterOfRotation(0, 0);
		img.setRotation(rot);
		img.draw(x, y, cw, ch);
		img.setRotation(0);
	}
	
	public void update() {
		if(Math.abs(x-tox) < 1f && Math.abs(y-toy) < 1f){
			toslot.setCard(new Card(id));
			finished = true;
		}else{			
			float frames = 10;
			
			float xspeed = (tox - x) / frames;
			float yspeed = (toy - y) / frames;
			
			float rotspeed = (torot - rot) / frames;
			
			float minspeed = 0.4f;
			
			x += (xspeed > 0 ? Math.max(xspeed, minspeed) : Math.min(xspeed, -minspeed));
			y += (yspeed > 0 ? Math.max(yspeed, minspeed) : Math.min(yspeed, -minspeed));
			
			rot += rotspeed;
		}
	}
	
	public static void drawAnimations() {
		for(AnimatedCard anim : animations){
			if(!anim.finished){
				anim.draw();
			}
		}
	}
	
	public static void updateAnimations() {
		ArrayList<AnimatedCard> done = new ArrayList<AnimatedCard>();
		
		for(AnimatedCard anim : animations) {
			anim.update();
			if(anim.finished){
				done.add(anim);
			}
		}
		
		for(AnimatedCard anim : done){
			animations.remove(anim);
		}
		
		done.clear();
	}
}
