package mist2meat.javaskipbo.client;

import java.net.InetAddress;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.client.popups.EnterNamePopup;
import mist2meat.javaskipbo.enums.ServerLoginResponse;

import org.newdawn.slick.SlickException;

public class ClientEvents {

	public static void serverMessage(String msg) {
		Client.log("Server says: "+msg);
	}
	
	public static void serverLoginResponse(byte response, byte id) {
		if(response == ServerLoginResponse.LOGIN_SUCCESS){
			Client.log("Login was successfull");
			LocalPlayer.id = id;
			Client.log("Got ID: "+id);
		}else if(response == ServerLoginResponse.LOGIN_NAME_TAKEN){
			Client.log("Login failed: name is in use");
			new EnterNamePopup(Main.client.name);
		}else if(response == ServerLoginResponse.LOGIN_SERVER_FULL){
			Client.log("Login failed: server is full");
			new EnterNamePopup(Main.client.name);
		}
	}
	
	public static void serverPong(InetAddress ip) {
		try {
			Main.client.setServerAddress(ip);
			Main.client.start();
			Main.client.beginGame();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void beginGame(byte gamemode) {
		Client.log("Game should begin!");
		Main.gamemode = gamemode;
		Main.client.prepareGame();
	}
}
