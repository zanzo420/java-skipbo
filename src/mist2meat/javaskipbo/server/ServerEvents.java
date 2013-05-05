package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.CardSlotType;
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
		
		try {
			PlayerManager.broadcastMessage(ply.getName()+" joined the game!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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

	public static void playerMoveCard(byte fromwho, byte fromdeckid, byte cardnum, byte towho, byte todeckid, byte fromslottype, byte toslottype) throws IOException {
		// TODO: Clean this up ALOT!
		
		Player fromplayer = PlayerManager.getPlayerByID(fromwho);
		Server.log(fromplayer.getName()+" wants to move card "+cardnum+" from "+fromdeckid+" to "+towho+","+todeckid+" type: "+toslottype);
		
		if(toslottype == CardSlotType.HAND_SLOT || toslottype == CardSlotType.CARD_DECK){ // drop the obvious invalid moves
			return;
		}
		
		Game game = Server.currentGame;
		
		if(fromwho != game.playersTurn){
			return;
		}
			
		if(toslottype == CardSlotType.MIDDLE_SLOT){
			boolean validMove = false;
			
			ArrayList<Card> deck = game.middleDecks.get((int)todeckid);
			
			if(deck.size() > 0){
				Card topcard = deck.get(deck.size()-1);
				if(topcard.getNum() == cardnum-1 || cardnum == 13){
					if(cardnum == 13){
						deck.add(new Card(cardnum,(byte)(topcard.getNum()+1)));
					}else{
						deck.add(new Card(cardnum));
					}
					
					validMove = true;
				}
			}else{ // new middle deck
				if(cardnum == 1 || cardnum == 13){
					if(cardnum == 13){
						deck.add(new Card(cardnum,(byte)1));
					}else{
						deck.add(new Card(cardnum));
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
					pack.writeByte(deck.get(deck.size()-1).getNum());
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
						Server.currentGame.endGame(fromplayer);
						return;
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
					
					Server.currentGame.endTurn();
					
					fromplayer.getHand().put((int) fromdeckid, null);
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
