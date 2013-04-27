package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class ServerLoginResponsePacket extends SendablePacket {
	
	public ServerLoginResponsePacket(DatagramSocket sock, InetAddress to, int toport) throws IOException {
		super(sock, to, toport);
		writeByte(PacketType.SERVER_LOGIN_RESPONSE);
	}
	
	public void setResponse(byte msg) throws IOException {
		writeByte(msg);
	}
}
