package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.DatagramPacket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.ReceivedPacket;

public class ServerPacketHandler {

	public ServerPacketHandler() {
		
	}
	
	public void parse(DatagramPacket packet) throws IOException {
		Server.log("Parsing received packet");
		ReceivedPacket pack = new ReceivedPacket(packet);
		
		int type = pack.readByte();
		switch(type){
			case PacketType.JOIN_SERVER:
				Server.log("PlayerLoginPacket");
				String name = pack.readString();
				ServerEvents.PlayerLogin(name, pack.getPacket().getAddress(), pack.getPacket().getPort());
				break;
			default:
				Server.log("Unknown packet type: "+type);
				break;
		}
	}
}
