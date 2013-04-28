package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.popups.EnterNamePopup;
import mist2meat.javaskipbo.client.popups.JoinServerPopup;
import mist2meat.javaskipbo.network.client.JoinServerPacket;
import mist2meat.javaskipbo.network.client.PingServerPacket;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Client {

	private ClientListener listener;
	private InetAddress serveripaddr;
	public String name;
	private GameWindow game;

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
			joinServer();
		}
	}
	
	protected void joinServer() {
		joinServer(serveripaddr);
	}
	
	private void joinServer(String ip) throws UnknownHostException {
		serveripaddr = InetAddress.getByName(ip);
		joinServer(serveripaddr);
	}
	
	private void joinServer(InetAddress ip) {
		Client.log("Joining server: "+ip);
		try {
			JoinServerPacket p = new JoinServerPacket(ClientListener.socket, getServerAddress(), 3625);
			p.setName(name);
			
			p.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void queryServer(String ip) throws UnknownHostException {
		try {
			serveripaddr = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			Client.log("Invalid IP!");
			new JoinServerPopup(ip);
			return;
		}
		
		try {
			new PingServerPacket(ClientListener.socket, serveripaddr, 3625).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ClientListener.socket.setSoTimeout(1000);
			getListener().listen();
			ClientListener.socket.setSoTimeout(0);
			getListener().setPassive(true);
			new EnterNamePopup();
		} catch (SocketTimeoutException e) {
			Client.log("Timed out!");
			new JoinServerPopup(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void beginGame() {
		try {
			startGame();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	
	private void startGame() throws SlickException {
		game = new GameWindow("Java Skip-Bo");
		final AppGameContainer app = new AppGameContainer(game);

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
	
	public ClientListener getListener() {
		return listener;
	}

	public InetAddress getServerAddress() {
		return serveripaddr;
	}

	public void setServerAddress(InetAddress ip) {
		serveripaddr = ip;
	}

	public void prepareGame() {
		game.prepareGame();
	}
}
