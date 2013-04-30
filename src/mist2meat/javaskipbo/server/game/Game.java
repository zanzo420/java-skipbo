package mist2meat.javaskipbo.server.game;

import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.server.Server;

public class Game {
	
	private ArrayList<Card> deck;
	
	public Game(){
		Server.log("Starting game");
		
		setUpGame();
		
	}

	public void setUpGame() {
		Server.log("Dealing cards");
		
		deck = new ArrayList<Card>();
		
		for(int i = 1; i <= 12; i++){
			for(int j = 1; j <= 12; j++){
				deck.add(new Card((byte)j));
			}
		}
		for(int i = 0; i < 18; i++){
			deck.add(new Card((byte)13));
			
		}
		
		Collections.shuffle(deck);
		
		int cards = (Main.getGamemode() == GameMode.GAME_2VS2 ? 15 : 20);
		
		for(int i = 1; i <= cards; i++){
			for(Player pl : PlayerManager.players){
				pl.addCardtoDeck(deck.get(deck.size()-1),i != cards);
				deck.remove(deck.size()-1);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
		Server.log("Cards dealt");
	}
	
}
