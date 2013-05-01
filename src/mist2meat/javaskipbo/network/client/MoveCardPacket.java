package mist2meat.javaskipbo.network.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

import mist2meat.javaskipbo.client.drawable.CardSlot;
import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class MoveCardPacket extends SendablePacket {

	public MoveCardPacket(DatagramSocket socket, InetAddress ip, int port) throws IOException {
		super(socket,ip,port);
		writeByte(PacketType.MOVE_CARD);
	}
	
	public void setFromSlot(CardSlot slot) throws IOException{
		writeByte(slot.getOwner());
		writeByte(slot.getDeckID());
		writeByte(slot.getCard().getNum());
	}
	
	public void setToSlot(CardSlot slot) throws IOException{
		writeByte(slot.getOwner());
		writeByte(slot.getDeckID());
	}
}
