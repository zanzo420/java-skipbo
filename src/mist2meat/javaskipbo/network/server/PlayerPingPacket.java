package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class PlayerPingPacket extends SendablePacket {
	public PlayerPingPacket(DatagramSocket sock, byte id) throws IOException {
		super(sock);
		writeByte(PacketType.PLAYER_PING);
		writeByte(id);
	}

}
