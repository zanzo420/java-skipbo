package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.server.BeginGamePacket;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerEvents;
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
		} else {
			Player ply = new Player(players.size(), name, ip, port);
			players.add(ply);
			Server.log("Login succeeded!");
			Server.log("Playercount: "+players.size()+"/"+maxplayers);
			
			if(players.size() >= maxplayers){
				//TODO: game should start - inform clients - build game - deal cards - first players' turn
				try {
					BeginGamePacket pack = new BeginGamePacket(ServerListener.socket);
					for(Player pl : players){
						pl.sendPacket(pack);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServerEvents.beginGame();
			}
			
			return ServerLoginResponse.LOGIN_SUCCESS;
		}
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
