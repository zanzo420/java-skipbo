package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.server.game.Player;

public class PlayersTurnPacket extends SendablePacket {

	public PlayersTurnPacket(DatagramSocket sock) throws IOException {
		super(sock);
		writeByte(PacketType.PLAYERS_TURN);
	}
	
	public void setPlayer(Player ply) throws IOException{
		writeByte(ply.getID());
	}

}
