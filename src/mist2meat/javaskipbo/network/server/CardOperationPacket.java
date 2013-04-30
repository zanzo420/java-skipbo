package mist2meat.javaskipbo.network.server;

import java.io.IOException;
import java.net.DatagramSocket;

import mist2meat.javaskipbo.enums.PacketType;
import mist2meat.javaskipbo.network.SendablePacket;

public class CardOperationPacket extends SendablePacket {

	public CardOperationPacket(DatagramSocket sock) throws IOException {
		super(sock);
		writeByte(PacketType.CARD_OPERATION);
	}

	public void setOperation(byte operation) throws IOException {
		writeByte(operation);
	}

}
