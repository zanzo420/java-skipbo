package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientListener {

	public static DatagramSocket socket;
	private boolean passive;
	private Thread passiveThread;
	private ClientPacketHandler handler;
	
	public ClientListener() {
		handler = new ClientPacketHandler();
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setPassive(boolean pass) {
		passive = pass;
		if(passive){
			Client.log("Starting passive listener");
			passiveThread = new Thread(new Runnable() {
				public void run() {				
					while(passive) {
						try {
							handler.parse(listen());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			passiveThread.setName("Client Passive Listener");
			passiveThread.start();
		}else{
			Client.log("Stopping passive listener");
			passiveThread.interrupt();
		}
	}
	
	public DatagramPacket listen() throws IOException {
		byte[] buffer = new byte[128];
		DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
		socket.receive(packet);
		
		return packet;
	}
}
