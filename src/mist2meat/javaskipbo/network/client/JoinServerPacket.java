package mist2meat.javaskipbo.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class JoinServerPacket extends SendablePacket {

	public JoinServerPacket(DatagramSocket sock, InetAddress ip, int port) throws IOException {
		super(sock, ip, port);
		writeByte(PacketType.JOIN_SERVER);
	}
	
	public void setName(String name) throws IOException{
		writeString(name);
	}
}
