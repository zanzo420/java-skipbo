package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class BeginGamePacket extends SendablePacket {

	public BeginGamePacket(DatagramSocket sock) throws IOException {
		super(sock);
		writeByte(PacketType.GAME_BEGIN);
		writeByte(Main.getGamemode());
	}

}
