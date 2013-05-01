package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.DatagramPacket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.ReceivedPacket;
import mist2meat.javaskipbo.client.game.PlayerManager;

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
				ClientEvents.serverLoginResponse(pack.readByte(),pack.readByte());
				break;
			case PacketType.GAME_BEGIN:
				byte gamemode = pack.readByte();
				
				byte playercount = pack.readByte();
				for(int i = 0; i < playercount; i++){
					PlayerManager.newPlayer(pack.readByte(),pack.readString());
				}
				
				ClientEvents.beginGame(gamemode);
				break;
			case PacketType.CARD_OPERATION:
				ClientEvents.cardOperation(pack);
				break;
			case PacketType.PLAYERS_TURN:
				ClientEvents.playersTurn(pack.readByte());
				break;
			default:
				Client.log("Unknown packet type: "+type);
				break;
		}
	}
}
