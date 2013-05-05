package mist2meat.javaskipbo.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendablePacket {

	DatagramPacket packet;
	DatagramSocket socket;
	
	ByteArrayOutputStream baos;
	DataOutputStream dos;

	int port;
	InetAddress ip;
	
	public SendablePacket(DatagramSocket sock, InetAddress to, int toport) {
		socket = sock;
		
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
		
		ip = to;
		port = toport;
	}
	
	public SendablePacket(DatagramSocket sock) {
		socket = sock;
		
		baos = new ByteArrayOutputStream();
		dos = new DataOutputStream(baos);
	}
	
	public DatagramPacket buildPacket() {
		byte[] data = baos.toByteArray();
		
		packet = new DatagramPacket(data,data.length);
		
		packet.setPort(port);
		packet.setAddress(ip);
		
		return packet;
	}
	
	public void writeByte(byte b) throws IOException {
		dos.writeByte(b);
	}
	
	public void writeString(String str) throws IOException {
		dos.writeUTF(str);
	}
	
	public void clean() throws IOException {
		dos.close();
		baos.close();
	}
	
	public void send() throws IOException {
		clean();
		socket.send(buildPacket());
	}

	public void setIp(InetAddress host) {
		ip = host;
	}
	
	public void setPort(int prt) {
		port = prt;
	}
}
