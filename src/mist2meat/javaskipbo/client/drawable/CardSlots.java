package mist2meat.javaskipbo.client.drawable;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;

import org.newdawn.slick.Image;

public class CardSlots {

	private static int imgWidth;
	private static int imgHeight;
	
	public static void draw(Image cardslot) {
		switch(Main.gamemode){
			case GameMode.GAME_1VS1:
				draw1v1(cardslot);
				break;
		}
	}
	
	private static void draw1v1(Image cardslot) {
		drawBottomPlayerSlots(cardslot);
		drawTopPlayerSlots(cardslot);
		drawCenterSlots(cardslot);
	}
	
	private static void drawBottomPlayerSlots(Image cardslot) {
		int xpos = 350;
		int ypos = 570;
		
		cardslot.setRotation(90);
		cardslot.draw(xpos, ypos);
		cardslot.setRotation(0);
		for(int i = 0; i < 4; i++){
			cardslot.draw(xpos+175+(i*135), ypos);
		}
	}
	
	private static void drawTopPlayerSlots(Image cardslot) {
		int xpos = 320;
		int ypos = 10;
		
		for(int i = 0; i < 4; i++){
			cardslot.draw(xpos+(i*135), ypos);
		}
		
		cardslot.setRotation(90);
		cardslot.draw(xpos+580, ypos);
		cardslot.setRotation(0);
	}
	
	private static void drawCenterSlots(Image cardslot) {
		int xpos = 320;
		int ypos = 290;
		
		for(int i = 0; i < 4; i++){
			cardslot.draw(xpos+(i*135), ypos);
		}
		
		cardslot.setRotation(-45);
		cardslot.draw(xpos+600, ypos);
		cardslot.setRotation(0);
	}
}
