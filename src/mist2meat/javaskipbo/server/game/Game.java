package mist2meat.javaskipbo.server.game;

import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.game.content.Card;

public class Game {
	
	private ArrayList<Card> deck;
	
	public Game(){
		Server.log("Starting game");
		
		setUpGame();
		
	}

	public void setUpGame() {
		deck = new ArrayList<Card>();
		
		for(int i = 1; i <= 12; i++){
			for(int j = 1; j <= 12; j++){
				deck.add(new Card((byte)j));
			}
		}
		for(int i = 0; i < 18; i++){
			deck.add(new Card((byte)13));
			
		}
		Server.log(""+deck.size());
		
		Collections.shuffle(deck);
		for(Card c : deck){
			System.out.println(c.getNum());
		}
		
		
	}
	
	


}
