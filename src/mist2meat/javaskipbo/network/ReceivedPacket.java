package mist2meat.javaskipbo.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

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
	
	public int readByte() throws IOException {
		return dis.readByte();
	}
	
	public String readString() throws IOException {
		String out = "";
		int len = dis.readByte();
		for(int i = 0;i < len; i++){
			out += (char)dis.readByte();
		}
		return out;
	}
	
	public void clean() throws IOException {
		dis.close();
		bais.close();
	}
}
