package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.network.SendablePacket;

public class Player {
	
	private int id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	public Player(int i, String nam, InetAddress host, int prt){
		id = i;
		name = nam;
		ip = host;
		port = prt;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void sendPacket(SendablePacket pack) throws IOException {
		pack.setIp(ip);
		pack.setPort(port);
		pack.send();
	}
}
