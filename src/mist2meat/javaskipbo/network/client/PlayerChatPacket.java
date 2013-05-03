package mist2meat.javaskipbo.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.client.game.LocalPlayer;
import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class PlayerChatPacket extends SendablePacket {

	public PlayerChatPacket(DatagramSocket sock, InetAddress to, int toport) throws IOException {
		super(sock, to, toport);
		writeByte(PacketType.PLAYER_CHAT);
	}

	public void setMessage(String message) throws IOException {
		writeByte(LocalPlayer.id);
		writeString(message);
	}
}
