package mist2meat.javaskipbo.client.game;

import java.util.ArrayList;

import mist2meat.javaskipbo.client.Client;

public class PlayerManager {

	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void newPlayer(byte id, String name) {
		Client.log("Player: "+id+" = "+name);
		
		if(id == LocalPlayer.id){
			Client.log("LocalPlayer is ID: "+id);
		}else{
			Player p = new Player(id,name);
			players.add(p);
		}
	}
}
