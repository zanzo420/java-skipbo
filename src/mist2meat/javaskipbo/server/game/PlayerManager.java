package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.network.server.ServerMessagePacket;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerListener;


public class PlayerManager {

	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int maxplayers = 0;
	
	public static byte newPlayer(String name, InetAddress ip, int port) {
		if(isNameTaken(name)){
			Server.log("Login failed: name is in use");
			return ServerLoginResponse.LOGIN_NAME_TAKEN;
		} else if (players.size() >= maxplayers) {
			return ServerLoginResponse.LOGIN_SERVER_FULL;
		}else if(name.length() <= 2 || name.length() >= 15) {
			return ServerLoginResponse.LOGIN_INVALID_NAME_LENGTH;
		} else {
			Player ply = new Player((byte)players.size(), name, ip, port);
			players.add(ply);
			Server.log("Login succeeded!");
			Server.log("Playercount: "+players.size()+"/"+maxplayers);
			
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
	
	public static Player getPlayerByID(byte id) {
		for(Player ply : players) {
			if(ply.getID() == id){
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
	
	public static void broadcastPacket(SendablePacket pack) throws IOException {
		for(Player pl : PlayerManager.players){
			pl.sendPacket(pack);
		}
	}
	
	public static void broadcastMessage(String message) throws IOException {
		ServerMessagePacket pack = new ServerMessagePacket(ServerListener.socket);
		pack.setMessage(message);
		broadcastPacket(pack);
	}
}
