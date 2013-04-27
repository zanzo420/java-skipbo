package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerListener {

	public static DatagramSocket socket;
	private boolean passive;
	private Thread passiveThread;
	private ServerPacketHandler handler;
	
	public ServerListener() {
		handler = new ServerPacketHandler();
		try {
			socket = new DatagramSocket(3625);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void setPassive(boolean pass) {
		passive = pass;
		if(passive){
			Server.log("Starting passive listener");
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
			passiveThread.setName("Server Passive Listener");
			passiveThread.start();
		}else{
			Server.log("Stopping passive listener");
		}
	}
	
	public DatagramPacket listen() throws IOException {
		byte[] buffer = new byte[128];
		DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
		socket.receive(packet);
		
		return packet;
	}
}
