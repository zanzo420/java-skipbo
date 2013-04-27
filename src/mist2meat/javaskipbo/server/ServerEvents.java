package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.server.PongClientPacket;
import mist2meat.javaskipbo.network.server.ServerLoginResponsePacket;
import mist2meat.javaskipbo.network.server.ServerMessagePacket;

public class ServerEvents {

	public static void playerLogin(String name, InetAddress ip, int port) throws IOException {
		Server.log("Logging in player: "+name+" from "+ip+":"+port);
		
		ServerMessagePacket msgpack = new ServerMessagePacket(ServerListener.socket,ip,port);
		msgpack.setMessage("Login successful!");
		msgpack.send();
		
		ServerLoginResponsePacket resppack = new ServerLoginResponsePacket(ServerListener.socket,ip,port);
		resppack.setResponse(ServerLoginResponse.LOGIN_SUCCESS);
		resppack.send();
	}
	
	public static void ping(InetAddress addr, int port) throws IOException {
		new PongClientPacket(ServerListener.socket,addr,port).send();
	}
}
