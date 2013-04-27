package mist2meat.javaskipbo.client;

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
}
