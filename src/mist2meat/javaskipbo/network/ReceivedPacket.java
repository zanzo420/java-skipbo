package mist2meat.javaskipbo.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import mist2meat.javaskipbo.client.ClientListener;

public class ReceivedPacket {

	DatagramPacket packet;
	
	ByteArrayInputStream bais;
	DataInputStream dis;
	
	public ReceivedPacket(DatagramPacket pack) {
		packet = pack;
		
		bais = new ByteArrayInputStream(pack.getData());
		dis = new DataInputStream(bais);
	}
	
	public DatagramPacket getPacket() {
		return packet;
	}
	
	public byte readByte() throws IOException {
		return dis.readByte();
	}
	
	public String readString() throws IOException {
		return dis.readUTF();
	}
	
	public void clean() throws IOException {
		dis.close();
		bais.close();
	}

	public void sendBack() throws IOException {		
		packet.setAddress(packet.getAddress());
		packet.setPort(packet.getPort());
		
		ClientListener.socket.send(packet);
	}
}
