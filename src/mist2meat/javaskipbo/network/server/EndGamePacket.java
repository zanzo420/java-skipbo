package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.server.game.Player;

public class EndGamePacket extends SendablePacket {
	
	public EndGamePacket(DatagramSocket sock) throws IOException {
		super(sock);
		writeByte(PacketType.GAME_END);
	}
	
	public void setWinner(Player pl) throws IOException {
		writeByte(pl.getID());
	}

}
