package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class PongClientPacket extends SendablePacket {

	public PongClientPacket(DatagramSocket sock, InetAddress to, int toport) throws IOException {
		super(sock, to, toport);
		writeByte(PacketType.PONG);
		// TODO: send more info like player count
	}

}
