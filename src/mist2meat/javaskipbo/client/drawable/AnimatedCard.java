package mist2meat.javaskipbo.client.drawable;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import mist2meat.javaskipbo.client.GameWindow;
import mist2meat.javaskipbo.client.game.Game;
import mist2meat.javaskipbo.enums.CardSlotType;

import org.newdawn.slick.Image;

public class AnimatedCard {

	private static ArrayList<AnimatedCard> animations = new ArrayList<AnimatedCard>();

	Image img;

	CardSlot toslot;

	float x, y;
	float tox, toy;

	float rot, torot;

	byte id;
	byte wildcard = 0;

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

	public AnimatedCard(CardSlot fromslot, CardSlot toslot, byte id, byte wildcard) {
		x = fromslot.getX();
		y = fromslot.getY();

		rot = fromslot.rotated ? 90 : 0;

		tox = toslot.getX();
		toy = toslot.getY();

		torot = toslot.rotated ? 90 : 0;

		this.toslot = toslot;
		this.id = id;
		this.wildcard = wildcard;

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
		if (Math.abs(x - tox) < 1f && Math.abs(y - toy) < 1f) {
			Card c = new Card(id);
			if (wildcard > 0) {
				c.setWildNum(wildcard);
			}
			toslot.setCard(c);
			finished = true;
			
			if(toslot.getType() == CardSlotType.MIDDLE_SLOT){
				if(c.getNum() == 12 || wildcard == 12){
					toslot.setCard(null);
					new AnimatedCard(toslot, Game.deck, (byte)0);
				}
			}
		} else {
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
		try {
			for (AnimatedCard anim : animations) {
				if (!anim.finished) {
					anim.draw();
				}
			}
		} catch (ConcurrentModificationException e) {
			// Duck you java >:/
		}
	}

	public static void updateAnimations() {
		ArrayList<AnimatedCard> done = new ArrayList<AnimatedCard>();

		try {
			for (AnimatedCard anim : animations) {
				if (!anim.finished) {
					anim.update();
				} else {
					done.add(anim);
				}
			}
		} catch (ConcurrentModificationException e) {
			// Duck you java >:/
		}

		for (AnimatedCard anim : done) {
			animations.remove(anim);
		}

		done.clear();
	}
}
