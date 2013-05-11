package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.CardSlotType;
import mist2meat.javaskipbo.network.server.BeginGamePacket;
import mist2meat.javaskipbo.network.server.CardOperationPacket;
import mist2meat.javaskipbo.network.server.PlayersTurnPacket;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerEvents;
import mist2meat.javaskipbo.server.ServerListener;

public class Game {

	public ArrayList<Card> deck;
	public ArrayList<ArrayList<Card>> middleDecks = new ArrayList<ArrayList<Card>>();
	public byte playersTurn;
	
	private boolean turnFinished,gameRunning;

	public Game() {
		Server.log("Starting game");

		try {
			for (Player p : PlayerManager.players) {
				BeginGamePacket pack = new BeginGamePacket(ServerListener.socket);
				pack.setTargetPlayer(p);
				p.sendPacket(pack);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		setUpGame();
		dealCards();
		
		playersTurn = (byte) (Math.random() * PlayerManager.minplayers);
		
		gameRunning = true;
	}
	
	public void start() {
		while(gameRunning){
			startTurn();
			while(!turnFinished){
				sleep(100);
			}
			sleep(1000);
		}
	}

	public void setUpGame() {
		deck = new ArrayList<Card>();

		for (int i = 1; i <= 12; i++) {
			for (int j = 1; j <= 12; j++) {
				deck.add(new Card((byte) j));
			}
		}
		for (int i = 0; i < 18; i++) {
			deck.add(new Card((byte) 13));

		}

		for (int i = 0; i < 10; i++) {
			Collections.shuffle(deck);
		}

		for (int i = 1; i <= 4; i++) {
			middleDecks.add(new ArrayList<Card>());
		}
	}

	private void dealCards() {
		Server.log("Dealing cards");

		int cards = 20;

		for (int i = 1; i <= cards; i++) {
			for (Player pl : PlayerManager.players) {
				pl.addCardtoDeck(0, deck.get(deck.size() - 1), i != cards);
				deck.remove(deck.size() - 1);
					sleep(250);
			}
		}
		Server.log("Cards dealt");
	}

	private void startTurn() {
		turnFinished = false;
		Player ply = PlayerManager.getPlayerByID(playersTurn);

		try {
			PlayersTurnPacket pack = new PlayersTurnPacket(ServerListener.socket);
			pack.setPlayer(ply);
			PlayerManager.broadcastPacket(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Server.log(ply.getName() + "(" + playersTurn + ")'s turn");

		ply.fillHand();
	}

	public void endTurn() {
		turnFinished = true;
		playersTurn = (byte) ((playersTurn + 1) % PlayerManager.minplayers);
	}
	
	public void processCardMove(byte fromwho, byte fromdeckid, byte cardnum, byte towho, final byte todeckid, byte fromslottype, byte toslottype) throws IOException {
		// TODO: Clean this up ALOT!
		
		Player fromplayer = PlayerManager.getPlayerByID(fromwho);
		Server.log(fromplayer.getName()+" wants to move card "+cardnum+" from "+fromdeckid+" to "+towho+","+todeckid+" type: "+toslottype);
		
		if(toslottype == CardSlotType.HAND_SLOT || toslottype == CardSlotType.CARD_DECK){ // drop the obvious illegal moves
			return;
		}
		
		if(fromwho != playersTurn){
			return;
		}
			
		if(toslottype == CardSlotType.MIDDLE_SLOT){
			boolean validMove = false;
			
			final ArrayList<Card> middledeck = middleDecks.get((int)todeckid);
			
			if(middledeck.size() > 0){
				Card topcard = middledeck.get(middledeck.size()-1);
				if(topcard.getNum() == cardnum-1 || cardnum == 13){
					if(cardnum == 13){
						middledeck.add(new Card(cardnum,(byte)(topcard.getNum()+1)));
					}else{
						middledeck.add(new Card(cardnum));
					}
					
					validMove = true;
				}
			}else{ // new middle deck
				if(cardnum == 1 || cardnum == 13){
					if(cardnum == 13){
						middledeck.add(new Card(cardnum,(byte)1));
					}else{
						middledeck.add(new Card(cardnum));
					}
					
					validMove = true;
				}
			}
			
			if(validMove){
				CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
				pack.setOperation(CardOperation.PUT_TO_MIDDLE);
				pack.writeByte(fromwho);
				pack.writeByte(fromdeckid);
				pack.writeByte(fromslottype);
				
				pack.writeByte(todeckid);
				pack.writeByte(cardnum);
				
				if(cardnum == 13){
					pack.writeByte(middledeck.get(middledeck.size()-1).getNum());
				}
				
				PlayerManager.broadcastPacket(pack);
				
				if(fromslottype == CardSlotType.HAND_SLOT){
					fromplayer.getHand().put((int) fromdeckid, null);
					if(fromplayer.getHandCardNum() == 0){
						fromplayer.fillHand();
					}
				}
				
				if(fromslottype == CardSlotType.PLAYER_SLOT){
					if(fromplayer.getDeck(fromdeckid).size() > 0){
						ArrayList<Card> plydeck = fromplayer.getDeck(fromdeckid);
						if(plydeck.size() > 0){
							plydeck.remove(plydeck.size()-1);
							
							CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
							pack2.setOperation(CardOperation.SET_CARD);
							pack2.writeByte(fromwho);
							pack2.writeByte(fromdeckid);
							if(plydeck.size() > 0){
								pack2.writeByte(plydeck.get(plydeck.size()-1).getNum());
								pack2.writeByte((byte)(plydeck.size() > 1 ? 1 : 0));
							}
							PlayerManager.broadcastPacket(pack2);
						}
					}
					
					if(fromplayer.getDeck(0).size() == 0){
						endGame(fromplayer);
						return;
					}
				}
			}
			
			if(middleDecks.get(todeckid).size() == 12) {
				deck.addAll(middledeck);
				Collections.shuffle(deck);
				
				middledeck.clear();
			}
		}else if(toslottype == CardSlotType.PLAYER_SLOT){
			if(fromslottype == CardSlotType.HAND_SLOT){
				if(todeckid > 0){
					fromplayer.getDeck(todeckid).add(new Card(cardnum));
					
					CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
					pack.setOperation(CardOperation.PUT_TO_FREEDECK);
					pack.writeByte(fromwho);
					pack.writeByte(fromdeckid);
					
					pack.writeByte(todeckid);
					pack.writeByte(cardnum);
					pack.writeByte((byte) (fromplayer.getDeck(todeckid).size() > 1 ? 1 : 0));
					
					PlayerManager.broadcastPacket(pack);
					
					endTurn();
					
					fromplayer.getHand().put((int) fromdeckid, null);
				}
			}
		}
	}
	
	private void endGame(Player winner) throws IOException {
		gameRunning = false;
		ServerEvents.endGame(winner);
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
