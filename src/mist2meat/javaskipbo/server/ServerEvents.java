package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.network.server.PongClientPacket;
import mist2meat.javaskipbo.network.server.ServerLoginResponsePacket;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class ServerEvents {

	public static void playerLogin(String name, InetAddress ip, int port) throws IOException {
		Server.log("Logging in player: "+name+" from "+ip+":"+port);
		
		byte response = PlayerManager.newPlayer(name, ip, port);
		
		ServerLoginResponsePacket resppack = new ServerLoginResponsePacket(ServerListener.socket,ip,port);
		resppack.setResponse(response);
		resppack.send();
	}
	
	public static void ping(InetAddress addr, int port) throws IOException {
		new PongClientPacket(ServerListener.socket,addr,port).send();
	}
}
