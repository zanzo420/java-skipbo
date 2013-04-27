package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.popups.EnterNamePopup;
import mist2meat.javaskipbo.network.client.JoinServerPacket;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Client {

	private ClientListener listener;
	private InetAddress serveripaddr;
	public String name;

	public Client() {
		listener = new ClientListener();
		listener.setPassive(true);
	}

	public void start() throws SlickException {
		new EnterNamePopup();
	}
	
	public void setName(String nam) {
		name = nam;
		if(Main.isHosting()){
			try {
				joinServer("127.0.0.1");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}else{
			
		}
	}
	
	private void joinServer(String ip) throws UnknownHostException {
		serveripaddr = InetAddress.getByName(ip);
		Client.log("Joining server: "+ip);
		try {
			JoinServerPacket p = new JoinServerPacket(ClientListener.socket, getServerAddress(), 3625);
			p.setName(name);
			p.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void queryServer(String ip) {
		// TODO: query and then join
	}
	
	public void beginGame() { // called when login was successful (when hosting)
		// TODO: wait for more players
		try {
			startGame();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void startGame() throws SlickException {
		final AppGameContainer app = new AppGameContainer(new GameWindow("Java Skip-Bo"));

		app.setDisplayMode(800, 600, false);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					app.start();
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.setName("Client");
		t.start();
	}

	public static void log(String msg) {
		System.out.println(msg);
	}

	public InetAddress getServerAddress() {
		return serveripaddr;
	}
}
