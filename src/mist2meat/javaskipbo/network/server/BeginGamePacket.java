package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.server.game.Player;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class BeginGamePacket extends SendablePacket {

	public BeginGamePacket(DatagramSocket sock) throws IOException {
		super(sock);
		writeByte(PacketType.GAME_BEGIN);
		writeByte(Main.getGamemode());

		writeByte((byte) PlayerManager.getNumPlayers());
	}

	public void setTargetPlayer(Player ply) throws IOException {
		byte id = ply.getID();

		for (int i = 0; i < PlayerManager.minplayers; i++) {
			Player p = PlayerManager.players.get((id + i) % PlayerManager.minplayers);
			writeByte(p.getID());
			writeString(p.getName());
		}
	}
}
