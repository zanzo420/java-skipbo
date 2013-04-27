package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.DatagramPacket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.ReceivedPacket;

public class ClientPacketHandler {

	public ClientPacketHandler() {
		
	}
	
	public void parse(DatagramPacket packet) throws IOException {
		ReceivedPacket pack = new ReceivedPacket(packet);
		int type = pack.readByte();
		switch(type){
			case PacketType.PONG:
				ClientEvents.serverPong(pack.getPacket().getAddress());
				break;
			case PacketType.SERVER_MESSAGE:
				ClientEvents.serverMessage(pack.readString());
				break;
			case PacketType.SERVER_LOGIN_RESPONSE:
				ClientEvents.serverLoginResponse(pack.readByte());
				break;
			default:
				Client.log("Unknown packet type: "+type);
				break;
		}
	}
}
