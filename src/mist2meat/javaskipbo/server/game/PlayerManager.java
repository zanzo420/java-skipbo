package mist2meat.javaskipbo.server.game;

import java.net.InetAddress;
import java.util.ArrayList;

import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerEvents;


public class PlayerManager {

	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int maxplayers = 0;
	
	public static byte newPlayer(String name, InetAddress ip, int port) {
		if(isNameTaken(name)){
			Server.log("Login failed: name is in use");
			return ServerLoginResponse.LOGIN_NAME_TAKEN;
		} else if (players.size() >= maxplayers) {
			return ServerLoginResponse.LOGIN_SERVER_FULL;
		} else {
			Player ply = new Player((byte)players.size(), name, ip, port);
			players.add(ply);
			Server.log("Login succeeded!");
			Server.log("Playercount: "+players.size()+"/"+maxplayers);
			
			Server.log(""+ServerEvents.numresponses+"/"+maxplayers);
			if(players.size() >= maxplayers){
				ServerEvents.beginGame();
			}
			
			return ServerLoginResponse.LOGIN_SUCCESS;
		}
	}
	
	public static int getNumPlayers(){
		return players.size();
	}
	
	public static Player getPlayerByName(String name) {
		for(Player ply : players) {
			if(ply.getName().equals(name)){
				return ply;
			}
		}
		return null;
	}
	
	private static boolean isNameTaken(String name) {
		for(Player ply : players) {
			if(ply.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
}
