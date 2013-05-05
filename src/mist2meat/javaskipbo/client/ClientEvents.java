package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.Card;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.client.game.Game;
import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.client.game.Player;
import mist2meat.javaskipbo.client.game.PlayerManager;
import mist2meat.javaskipbo.client.popups.EnterNamePopup;
import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.CardSlotType;
import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.ReceivedPacket;

import org.newdawn.slick.SlickException;

public class ClientEvents {

	public static void serverMessage(String msg) {
		Client.chatwindow.addLine(msg);
		if(!msg.startsWith(LocalPlayer.getName())){
			GameWindow.chatsound.play((float) Math.max(0.4f,Math.random()*2f),1f);
		}
	}

	public static void serverLoginResponse(byte response, byte id) {
		if (response == ServerLoginResponse.LOGIN_SUCCESS) {
			Client.log("Login was successfull");
			LocalPlayer.id = id;
			Main.client.beginGame();
		} else if (response == ServerLoginResponse.LOGIN_NAME_TAKEN) {
			Client.log("Login failed: name is in use");
			new EnterNamePopup(LocalPlayer.getName());
		} else if (response == ServerLoginResponse.LOGIN_SERVER_FULL) {
			Client.log("Login failed: server is full");
			new EnterNamePopup(LocalPlayer.getName());
		} else if (response == ServerLoginResponse.LOGIN_INVALID_NAME_LENGTH) {
			Client.log("Login failed: invalid name length (2 >< 15)");
			new EnterNamePopup(LocalPlayer.getName());
		}
	}

	public static void serverPong(InetAddress ip) {
		try {
			Main.client.setServerAddress(ip);
			Main.client.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void beginGame(byte gamemode) {
		Client.log("Game should begin!");
		Main.gamemode = gamemode;
		Main.client.prepareGame();
	}

	public static void cardOperation(ReceivedPacket pack) throws IOException {
		byte operation = pack.readByte();

		CardSlot from = null,to;
		byte playerid,deckid,cardid,fromdeckid,todeckid,slot,fromslottype;
		boolean hascardunder;
		
		switch(operation){
			case CardOperation.DRAW_FROM_DECK:
				playerid = pack.readByte();
				deckid = pack.readByte();
				cardid = pack.readByte();
				
				CardSlot toslot;
				if(playerid == LocalPlayer.id){
					toslot = LocalPlayer.deckslots.get(deckid);
					toslot.setHasCardUnder(true);
				}else{
					toslot = PlayerManager.getPlayerByID(playerid).deckslots.get(deckid);
				}
				
				new AnimatedCard(Game.deck,toslot,cardid);
				break;
			case CardOperation.DRAW_TO_HAND:
				slot = pack.readByte();
				cardid = pack.readByte();
				
				LocalPlayer.addToHand(slot,cardid);
				break;
			case CardOperation.PUT_TO_MIDDLE:
				playerid = pack.readByte();
				fromdeckid = pack.readByte();
				fromslottype = pack.readByte();
				
				todeckid = pack.readByte();
				cardid = pack.readByte();
				
				byte wildcard = 0;
				
				if(cardid == 13){
					wildcard = pack.readByte();
				}
				
				to = Game.middleDecks.get(todeckid);
				
				if(playerid == LocalPlayer.id){
					if(fromslottype == CardSlotType.HAND_SLOT){
						from = LocalPlayer.handslots.get(fromdeckid);
					}else if(fromslottype == CardSlotType.PLAYER_SLOT){
						from = LocalPlayer.deckslots.get(fromdeckid);
					}
					from.setCard(null);
				}else{
					Player ply = PlayerManager.getPlayerByID(playerid);
					
					if(fromslottype == CardSlotType.HAND_SLOT){
						from = ply.deckslots.get(0); //TODO: find a better way to display other players hand cards
					}else if(fromslottype == CardSlotType.PLAYER_SLOT){
						from = ply.deckslots.get(fromdeckid);
					}
				}
				
				new AnimatedCard(from,to,cardid,wildcard);
				
				break;
			case CardOperation.PUT_TO_FREEDECK:
				playerid = pack.readByte();
				
				fromdeckid = pack.readByte();
				todeckid = pack.readByte();
				
				cardid = pack.readByte();
				
				hascardunder = pack.readByte() == 1;
				
				if(playerid == LocalPlayer.id){
					LocalPlayer.handslots.get(fromdeckid).setCard(null);
					
					from = LocalPlayer.handslots.get(fromdeckid);
					to = LocalPlayer.deckslots.get(todeckid);
					
					to.setHasCardUnder(hascardunder);
				}else{
					Player ply = PlayerManager.getPlayerByID(playerid);
					
					from = ply.deckslots.get(0); //TODO: find a better way to display other players hand cards
					to = ply.deckslots.get(todeckid);
				}
				
				new AnimatedCard(from,to,cardid);
				
				break;
			case CardOperation.SET_CARD:
				playerid = pack.readByte();
				deckid = pack.readByte();
				cardid = pack.readByte();
				
				hascardunder = pack.readByte() == 1;
				
				Card card = null;

				if(cardid > 0){
					card = new Card(cardid);
				}else{
					hascardunder = false;
				}
				
				if(playerid == LocalPlayer.id){
					LocalPlayer.deckslots.get(deckid).setCard(card);
					LocalPlayer.deckslots.get(deckid).setHasCardUnder(hascardunder);
				}else{
					Player ply = PlayerManager.getPlayerByID(playerid);

					ply.deckslots.get(deckid).setCard(card);
				}
				
				break;
			case CardOperation.EMPTY_DECK:
				deckid = pack.readByte();
				
				Game.middleDecks.get(deckid).setCard(null);
				break;
			default:
				Client.log("Unknown card operation: "+operation);
				break;
		}
	}

	public static void playersTurn(byte playerid) {
		PlayerManager.resetTurn();
		LocalPlayer.myTurn = false;
		if(playerid == LocalPlayer.id){
			LocalPlayer.myTurn = true;
			GameWindow.turnsound.play((float) (1.25-Math.random()*0.5),1f);
			Client.log("Your turn");
		}else{
			Player pl = PlayerManager.getPlayerByID(playerid);
			pl.setTurn(true);
			Client.log(pl.getName()+"'s turn");
		}
	}

	public static void endGame(byte winnerid) {
		PlayerManager.resetTurn();
		LocalPlayer.myTurn = false;
		
		if(winnerid == LocalPlayer.id){
			Client.log("You won the game!");
			Client.chatwindow.addLine("You won the game!");
		}else{
			Player winner = PlayerManager.getPlayerByID(winnerid);
			Client.log(winner.getName()+" won the game!");
			Client.chatwindow.addLine(winner.getName()+" won the game!");
		}
		Client.log("Game over");
		
		for(CardSlot slot : LocalPlayer.handslots) {
			slot.setCard(null);
		}
	}
}
