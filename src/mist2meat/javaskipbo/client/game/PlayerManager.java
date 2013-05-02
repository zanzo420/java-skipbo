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
	
	public static Player getPlayerByID(byte id) {
		for(Player p : players){
			if(id == p.id){
				return p;
			}
		}
		return null;
	}

	public static void resetTurn() {
		for(Player p : players){
			p.setTurn(false);
		}
	}
}
