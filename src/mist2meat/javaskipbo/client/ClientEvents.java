package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.client.game.Game;
import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.client.game.PlayerManager;
import mist2meat.javaskipbo.client.popups.EnterNamePopup;
import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.ReceivedPacket;

import org.newdawn.slick.SlickException;

public class ClientEvents {

	public static void serverMessage(String msg) {
		Client.log("Server says: " + msg);
	}

	public static void serverLoginResponse(byte response, byte id) {
		if (response == ServerLoginResponse.LOGIN_SUCCESS) {
			Client.log("Login was successfull");
			LocalPlayer.id = id;
			Client.log("Got ID: " + id);
			Main.client.beginGame();
		} else if (response == ServerLoginResponse.LOGIN_NAME_TAKEN) {
			Client.log("Login failed: name is in use");
			new EnterNamePopup(Main.client.name);
		} else if (response == ServerLoginResponse.LOGIN_SERVER_FULL) {
			Client.log("Login failed: server is full");
			new EnterNamePopup(Main.client.name);
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
		switch(operation){
			case CardOperation.DRAW_FROM_DECK:
				byte playerid = pack.readByte();
				byte deckid = pack.readByte();
				byte cardid = pack.readByte();
				
				Client.log("PlayerID "+playerid+" got card "+cardid+" to deck "+deckid);
				
				CardSlot toslot;
				if(playerid == LocalPlayer.id){
					toslot = LocalPlayer.deckslots.get(deckid);
				}else{
					toslot = PlayerManager.getPlayerByID(playerid).deckslots.get(deckid);
				}
				
				new AnimatedCard(Game.deck,toslot,cardid);
				break;
			case CardOperation.DRAW_TO_HAND:
				byte card = pack.readByte();
				
				LocalPlayer.addToHand(card);
				break;
			default:
				Client.log("Unknown card operation: "+operation);
				break;
		}
	}
}
