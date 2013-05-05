package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.network.server.BeginGamePacket;
import mist2meat.javaskipbo.network.server.CardOperationPacket;
import mist2meat.javaskipbo.network.server.EndGamePacket;
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
		
		try {
			for(Player p : PlayerManager.players){
				BeginGamePacket pack = new BeginGamePacket(ServerListener.socket);
				pack.setTargetPlayer(p);
				p.sendPacket(pack);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dealCards();
		
		playersTurn = (byte) (Math.random()*PlayerManager.maxplayers);
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
		
		for(int i = 0; i < 10; i++){
			Collections.shuffle(deck);
		}
		
		for(int i = 1; i <= 4; i++){
			middleDecks.add(new ArrayList<Card>());
		}
	}
	
	private void dealCards() {
		Server.log("Dealing cards");
		
		int cards = 20;
		
		for(int i = 1; i <= cards; i++){
			for(Player pl : PlayerManager.players){
				pl.addCardtoDeck(0, deck.get(deck.size()-1),i != cards);
				deck.remove(deck.size()-1);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
		Server.log("Cards dealt");
	}
	
	public void startGame(){
		startTurn();
	}
	
	public void endGame(Player winner) throws IOException{
		EndGamePacket pack = new EndGamePacket(ServerListener.socket);
		pack.setWinner(winner);
		PlayerManager.broadcastPacket(pack);
		
		for(Player p : PlayerManager.players){
			PlayerManager.broadcastMessage(p.getName()+" had "+p.getDeck(0).size()+" cards left");
		}
		
		Server.log(winner.getName()+" won the game!");
		Server.log("Game over!");
		
		PlayerManager.broadcastMessage("Game will restart in 10 seconds");
		Server.log("Game will restart in 10 seconds");
		
		Server.currentGame = null;
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				try {
					for(Player p : PlayerManager.players){
						p.getHand().clear();
						
						for(int i = 0; i <= 4; i++){
							p.getDeck(i).clear();
							
							CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
							pack.setOperation(CardOperation.SET_CARD);
							pack.writeByte(p.getID());
							pack.writeByte((byte) i);
							pack.writeByte((byte) 0);
							pack.writeByte((byte) 0);
							PlayerManager.broadcastPacket(pack);
						}
						
						p.initDecks();
					}
					
					for(int i2 = 0; i2 < 4; i2++){
						CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
						pack2.setOperation(CardOperation.EMPTY_DECK);
						pack2.writeByte((byte) i2);
						
						PlayerManager.broadcastPacket(pack2);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				Server.currentGame = new Game();
				Server.currentGame.startGame();
			}
		});
		t.start();
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
		
		ply.fillHand();
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
