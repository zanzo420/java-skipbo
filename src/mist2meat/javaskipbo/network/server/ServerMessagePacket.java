package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class ServerMessagePacket extends SendablePacket {

	public ServerMessagePacket(DatagramSocket sock, InetAddress to, int toport) throws IOException {
		super(sock, to, toport);
		writeByte(PacketType.SERVER_MESSAGE);
	}
	
	public void setMessage(String msg) throws IOException {
		writeString(msg);
	}
	
}
