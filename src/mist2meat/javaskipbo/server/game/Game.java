package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.network.server.PlayersTurnPacket;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerListener;

public class Game {
	
	public ArrayList<Card> deck;
	public ArrayList<ArrayList<Card>> middleDecks = new ArrayList<ArrayList<Card>>();
	public byte playersTurn;
	
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
		
		for(int i = 1; i <= 4; i++){
			middleDecks.add(new ArrayList<Card>());
		}
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
		
		try {
			PlayersTurnPacket pack = new PlayersTurnPacket(ServerListener.socket);
			pack.setPlayer(ply);
			PlayerManager.broadcastPacket(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Server.log(ply.getName()+"("+playersTurn+")'s turn");
		
		int handcards = ply.getHandCardNum();
		if(handcards < 5){
			int missing = (5-handcards);
			for(int i = 1; i <= missing; i++){
				Card card = deck.get(deck.size()-1);
				ply.addCardToHand(card);
				deck.remove(deck.size()-1);
				Server.log("Gave card "+card.getNum());
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Server.log("Gave "+ply.getName()+" "+missing+" hand cards");
		}
	}
	
	public void endTurn() {
		playersTurn = (byte) ((playersTurn + 1) % PlayerManager.maxplayers);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		startTurn();
	}
}
