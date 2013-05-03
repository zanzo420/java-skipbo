package mist2meat.javaskipbo.client.game;

import java.io.IOException;
import java.util.ArrayList;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.ClientListener;
import mist2meat.javaskipbo.client.GameWindow;
import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.network.client.MoveCardPacket;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class DragNDropManager {
	
	private static CardSlot fromSlot;
	private static boolean dragging = false;
	private static Card dragged;
	
	private static float xoff = 0;
	private static float yoff = 0;

	public static void draw() {
		if(dragging){
			dragged.draw();
		}
	}
	
	public static void init(Input input) {
		input.addMouseListener(new MouseListener() {
			@Override
			public void inputEnded() {}

			@Override
			public void inputStarted() {}

			@Override
			public boolean isAcceptingInput() {
				return LocalPlayer.myTurn;
			}

			@Override
			public void setInput(Input arg0) {}

			@Override
			public void mouseClicked(int button, int x, int y, int clicks) {
				
			}

			@Override
			public void mouseDragged(int x1, int y1, int x2, int y2) {
				if(dragging){
					x2 /= GameWindow.curLayout.xscale;
					y2 /= GameWindow.curLayout.yscale;
					dragged.setPos(x2+xoff, y2+yoff);
				}
			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {}

			@Override
			public void mousePressed(int button, int x, int y) {
				if(button == 0){
					CardSlot slot = getTopmostNonEmptyCardSlotAt(x,y);
					
					if(slot != null){
						Card card = slot.getCard();
						
						xoff = (card.getX()*GameWindow.curLayout.xscale) - x;
						yoff = (card.getY()*GameWindow.curLayout.yscale) - y;
						
						dragging = true;
						dragged = card;
						fromSlot = slot;
					}
				}
			}

			@Override
			public void mouseReleased(int button, int x, int y) {
				if(button == 0){
					if(dragging){
						dragging = false;
						
						fromSlot.setCard(dragged);
						
						CardSlot slot = getTopmostCardSlotAt(x,y);
						
						if(slot != null){
							try {
								MoveCardPacket pack = new MoveCardPacket(ClientListener.socket,Main.client.getServerAddress(),3625);
								pack.setFromSlot(fromSlot);
								pack.setToSlot(slot);
								pack.send();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						
						dragged = null;
						fromSlot = null;
					}
				}
			}

			@Override
			public void mouseWheelMoved(int arg0) {}
		});
	}
	
	private static CardSlot getTopmostNonEmptyCardSlotAt(int x, int y) {
		ArrayList<CardSlot> slots = GameWindow.curLayout.getSlotsAt(x, y);
		
		while(true){
			if(slots.size() == 0){
				break;
			}
			
			CardSlot slo = slots.get(slots.size()-1);
			
			if(slo.hasCard() && slo.canTouch()){
				break;
			}else{
				slots.remove(slots.size()-1);
			}
		}
		
		if(slots.size() > 0){
			return slots.get(slots.size()-1);
		}else{
			return null;
		}
	}
	
	private static CardSlot getTopmostCardSlotAt(int x, int y) {
		ArrayList<CardSlot> slots = GameWindow.curLayout.getSlotsAt(x, y);
		
		if(slots.size() > 0){
			return slots.get(slots.size()-1);
		}else{
			return null;
		}
	}
}
