package mist2meat.javaskipbo.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class ReadyToPlayPacket extends SendablePacket {

	public ReadyToPlayPacket(DatagramSocket sock, InetAddress ip, int port) throws IOException {
		super(sock, ip, port);
		writeByte(PacketType.READY_TO_PLAY);
		writeByte(LocalPlayer.id);
	}
}
