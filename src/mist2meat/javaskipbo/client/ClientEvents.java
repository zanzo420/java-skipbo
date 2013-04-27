package mist2meat.javaskipbo.client;

import java.net.InetAddress;

import org.newdawn.slick.SlickException;

import mist2meat.javaskipbo.Main;

public class ClientEvents {

	public static void serverMessage(String msg) {
		Client.log("Server says: "+msg);
	}
	
	public static void serverLoginResponse(boolean success) {
		if(success){
			Client.log("Login was successfull");
			Main.client.beginGame();
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
}
