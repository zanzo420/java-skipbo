package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.server.BeginGamePacket;
import mist2meat.javaskipbo.network.server.PongClientPacket;
import mist2meat.javaskipbo.network.server.ServerLoginResponsePacket;
import mist2meat.javaskipbo.server.game.Game;
import mist2meat.javaskipbo.server.game.Player;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class ServerEvents {
	
	public static int numresponses = 0;
	public static void playerLogin(String name, InetAddress ip, int port) throws IOException {
		Server.log("Logging in player: "+name+" from "+ip+":"+port);
		
		byte response = PlayerManager.newPlayer(name, ip, port);
		
		ServerLoginResponsePacket resppack = new ServerLoginResponsePacket(ServerListener.socket,ip,port);
		resppack.setResponse(response);
		if(response == ServerLoginResponse.LOGIN_SUCCESS){
			resppack.setID(PlayerManager.getPlayerByName(name).getID());
		}
		resppack.send();
	}
	
	public static void playerReady(byte id) {
		Player ply = PlayerManager.players.get(id);
		Server.log(ply.getName()+" is ready!");
		numresponses++;
		if(numresponses >= PlayerManager.maxplayers){
			Server.log("Game can start now!");
			ServerEvents.beginGame();
		}
	}
	
	public static void ping(InetAddress addr, int port) throws IOException {
		new PongClientPacket(ServerListener.socket,addr,port).send();
	}
	
	public static void beginGame() {
		try {
			BeginGamePacket pack = new BeginGamePacket(ServerListener.socket);
			for(Player pl : PlayerManager.players){
				pl.sendPacket(pack);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Server.currentGame = new Game();
	}
}
