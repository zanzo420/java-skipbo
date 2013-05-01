package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.DatagramPacket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.ReceivedPacket;

public class ServerPacketHandler {

	public ServerPacketHandler() {
		
	}
	
	public void parse(DatagramPacket packet) throws IOException {
		ReceivedPacket pack = new ReceivedPacket(packet);
		
		int type = pack.readByte();
		switch(type){
			case PacketType.JOIN_SERVER:
				String name = pack.readString();
				ServerEvents.playerLogin(name, pack.getPacket().getAddress(), pack.getPacket().getPort());
				break;
			case PacketType.PING:
				ServerEvents.ping(pack.getPacket().getAddress(),pack.getPacket().getPort());
				break;
			case PacketType.READY_TO_PLAY:
				ServerEvents.playerReady(pack.readByte());
				break;
			case PacketType.MOVE_CARD:
				byte fromwho = pack.readByte();
				byte fromdeckid = pack.readByte();
				byte cardnum = pack.readByte();
				byte towho = pack.readByte();
				byte todeckid = pack.readByte();
				
				ServerEvents.playerMoveCard(fromwho, fromdeckid, cardnum, towho, todeckid);
				break;
			default:
				Server.log("Unknown packet type: "+type);
				break;
		}
	}
}
