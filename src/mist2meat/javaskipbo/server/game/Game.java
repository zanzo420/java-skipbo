package mist2meat.javaskipbo.server.game;

import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.server.Server;

public class Game {
	
	private ArrayList<Card> deck;
	private byte playersTurn;
	
	public Game(){
		Server.log("Starting game");
		
		setUpGame();
		
		dealCards();
		
		playersTurn = (byte) (Math.random()*PlayerManager.maxplayers);
		
		startTurn();
	}

	public void setUpGame() {
		Server.log("Setting up game");
		
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
	}
	
	private void dealCards() {
		Server.log("Dealing cards");
		
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
	
	private void startTurn(){
		Player ply = PlayerManager.getPlayerByID(playersTurn);
		
		Server.log(ply.getName()+"("+playersTurn+")'s turn");
		
		ArrayList<Card> hand = ply.getHand();
		
		if(hand.size() < 5){
			int missing = (5-hand.size());
			for(int i = 1; i <= missing; i++){
				ply.addCardToHand(deck.get(deck.size()-1));
				deck.remove(deck.size()-1);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Server.log("Gave "+ply.getName()+" "+missing+" hand cards");
		}
	}
	
}
