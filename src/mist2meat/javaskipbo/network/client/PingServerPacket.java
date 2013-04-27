package mist2meat.javaskipbo.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class PingServerPacket extends SendablePacket {

	public PingServerPacket(DatagramSocket sock, InetAddress to, int toport) throws IOException {
		super(sock, to, toport);
		writeByte(PacketType.PING);
	}

}
