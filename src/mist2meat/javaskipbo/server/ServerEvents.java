package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.server.CardOperationPacket;
import mist2meat.javaskipbo.network.server.PongClientPacket;
import mist2meat.javaskipbo.network.server.ServerLoginResponsePacket;
import mist2meat.javaskipbo.server.game.Card;
import mist2meat.javaskipbo.server.game.Game;
import mist2meat.javaskipbo.server.game.Player;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class ServerEvents {
	
	public static int numresponses = 0;
	public static void playerLogin(String name, InetAddress ip, int port) throws IOException {
		Server.log("Logging in player: "+name+" from "+ip+":"+port);
		
		byte response = PlayerManager.newPlayer(name, ip, port);
		
		ServerLoginResponsePacket resppack = new ServerLoginResponsePacket(ServerListener.socket,ip,port);
		resppack.setResponse(response);
		if(response == ServerLoginResponse.LOGIN_SUCCESS){
			resppack.setID(PlayerManager.getPlayerByName(name).getID());
		}
		resppack.send();
	}
	
	public static void playerReady(byte id) {
		Player ply = PlayerManager.players.get(id);
		Server.log(ply.getName()+" is ready!");
		numresponses++;
		if(numresponses >= PlayerManager.maxplayers){
			Server.log("Game can start now!");
			ServerEvents.beginGame();
		}
	}
	
	public static void ping(InetAddress addr, int port) throws IOException {
		new PongClientPacket(ServerListener.socket,addr,port).send();
	}
	
	public static void beginGame() {
		Server.currentGame = new Game();
		Server.currentGame.startGame();
	}

	public static void playerMoveCard(byte fromwho, byte fromdeckid, byte cardnum, byte towho, byte todeckid) throws IOException {
		// TODO: Clean this up ALOT!
		// PLYID 50 = hand
		// PLYID 100 = middle decks
		
		Player fromplayer = PlayerManager.getPlayerByID(fromwho);
		Server.log(fromplayer.getName()+" wants to move card "+cardnum+" from "+fromdeckid+" to "+towho+","+todeckid);
		
		if(towho >= 50 && towho <= 55){ // can't move to a hand slot
			return;
		}
		
		
		Game game = Server.currentGame;
		
		if(fromwho != game.playersTurn){
			return;
		}
			
		if(towho == 100){ // to middle
			if(todeckid > 4){
				return;
			}		
			ArrayList<Card> deck = game.middleDecks.get((int)todeckid);
			if(deck.size() > 0){
				Card topcard = deck.get(deck.size()-1);
				if(topcard.getNum() == cardnum-1 || cardnum == 13){
					if(cardnum == 13){
						deck.add(new Card(cardnum,(byte)(topcard.getNum()+1)));
					}else{
						deck.add(new Card(cardnum));
					}
					
					CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
					pack.setOperation(CardOperation.PUT_TO_MIDDLE);
					pack.writeByte(fromwho);
					pack.writeByte(fromdeckid);
					pack.writeByte(todeckid);
					pack.writeByte(cardnum);
					
					if(cardnum == 13){
						pack.writeByte((byte)(topcard.getNum()+1));
					}
					
					PlayerManager.broadcastPacket(pack);
					
					if(fromdeckid >= 50 && fromdeckid <= 55){
						fromplayer.getHand().put(fromdeckid-50, null);
						if(fromplayer.getHandCardNum() == 0){
							fromplayer.fillHand();
						}
					}
					
					if(fromdeckid == 0){ //players deck
						if(fromplayer.getDeck().size() > 0){
							ArrayList<Card> plydeck = fromplayer.getDeck();
							if(plydeck.size() > 0){
								plydeck.remove(plydeck.size()-1);
								
								CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
								pack2.setOperation(CardOperation.SET_CARD);
								pack2.writeByte(fromwho);
								pack2.writeByte(fromdeckid);
								if(plydeck.size() > 0){
									pack2.writeByte(plydeck.get(plydeck.size()-1).getNum());
									PlayerManager.broadcastPacket(pack2);
								}else{
									Server.currentGame.endGame(fromplayer);
									return;
								}
							}
						}
					}else if(fromdeckid >= 1 && fromdeckid <= 4) {
						ArrayList<Card> plydeck = fromplayer.getFreeDeck(fromdeckid);
						if(plydeck.size() > 0){
							plydeck.remove(plydeck.size()-1);
							if(plydeck.size() > 0){
								CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
								pack2.setOperation(CardOperation.SET_CARD);
								pack2.writeByte(fromwho);
								pack2.writeByte(fromdeckid);
								pack2.writeByte(plydeck.get(plydeck.size()-1).getNum());
								
								PlayerManager.broadcastPacket(pack2);
							}
						}
					}
					
					if(Server.currentGame.middleDecks.get(todeckid).size() == 12) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						Server.currentGame.deck.addAll(deck);
						Collections.shuffle(Server.currentGame.deck);
						
						CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
						pack2.setOperation(CardOperation.EMPTY_DECK);
						pack2.writeByte(todeckid);
						
						PlayerManager.broadcastPacket(pack2);
						
						deck.clear();
					}
				}
			}else{ // new middle deck
				if(cardnum == 1 || cardnum == 13){
					if(cardnum == 13){
						deck.add(new Card(cardnum,(byte)1));
					}else{
						deck.add(new Card(cardnum));
					}
					
					CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
					pack.setOperation(CardOperation.PUT_TO_MIDDLE);
					pack.writeByte(fromwho);
					pack.writeByte(fromdeckid);
					pack.writeByte(todeckid);
					pack.writeByte(cardnum);
					
					if(cardnum == 13){
						pack.writeByte((byte)1);
					}
					
					PlayerManager.broadcastPacket(pack);
					
					if(fromdeckid >= 50 && fromdeckid <= 55){
						fromplayer.getHand().put(fromdeckid-50, null);
						if(fromplayer.getHandCardNum() == 0){
							fromplayer.fillHand();
						}
					}
					
					if(fromdeckid == 0){ // players deck
						if(fromplayer.getDeck().size() > 0){
							ArrayList<Card> plydeck = fromplayer.getDeck();
							if(plydeck.size() > 0){
								plydeck.remove(plydeck.size()-1);
								
								CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
								pack2.setOperation(CardOperation.SET_CARD);
								pack2.writeByte(fromwho);
								pack2.writeByte(fromdeckid);
								if(plydeck.size() > 0){
									pack2.writeByte(plydeck.get(plydeck.size()-1).getNum());
									PlayerManager.broadcastPacket(pack2);
								}else{
									Server.currentGame.endGame(fromplayer);
									return;
								}
							}
						}
					}else if(fromdeckid >= 1 && fromdeckid <= 4) {
						ArrayList<Card> plydeck = fromplayer.getFreeDeck(fromdeckid);
						if(plydeck.size() > 0){
							plydeck.remove(plydeck.size()-1);
							if(plydeck.size() > 0){
								CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
								pack2.setOperation(CardOperation.SET_CARD);
								pack2.writeByte(fromwho);
								pack2.writeByte(fromdeckid);
								pack2.writeByte(plydeck.get(plydeck.size()-1).getNum());
								
								PlayerManager.broadcastPacket(pack2);
							}
						}
					}
				}
			}
			return;
		}
		
		if(towho == fromwho){ // player moves to free deck
			if(fromdeckid >= 50 && todeckid < 50){
				if(todeckid > 0){
					fromplayer.getFreeDeck(todeckid).add(new Card(cardnum));
					
					CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
					pack.setOperation(CardOperation.PUT_TO_FREEDECK);
					pack.writeByte(fromwho);
					pack.writeByte(fromdeckid);
					pack.writeByte(todeckid);
					pack.writeByte(cardnum);
					
					PlayerManager.broadcastPacket(pack);
					
					Server.currentGame.endTurn();
					
					fromplayer.getHand().put(fromdeckid-50, null);
				}
			}
		}
	}

	public static void playerSay(byte from, String msg) {
		Player pl = PlayerManager.getPlayerByID(from);
		
		Server.log(pl.getName()+" says \""+msg+"\"");
		
		try {
			PlayerManager.broadcastMessage(pl.getName()+": "+msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
