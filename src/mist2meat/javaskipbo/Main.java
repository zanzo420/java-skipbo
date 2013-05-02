package mist2meat.javaskipbo;

import java.io.File;

import mist2meat.javaskipbo.client.Client;
import mist2meat.javaskipbo.client.popups.HostJoinPopup;
import mist2meat.javaskipbo.client.popups.JoinServerPopup;
import mist2meat.javaskipbo.client.popups.SelectGamemodePopup;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.game.PlayerManager;

import org.newdawn.slick.SlickException;

public class Main {
	
	public static byte gamemode;
	private static boolean useServer;
	
	public static Server server;
	public static Client client;
	
	public static int scrw,scrh;
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());
		
		scrw = 800;
		scrh = 600;
		
		client = new Client();
		client.getListener().setPassive(false);
		new HostJoinPopup();
	}
	
	public static void useServer(boolean use) {
		useServer = use;
		if(useServer){
			new SelectGamemodePopup();
			client.getListener().setPassive(true);
		}else{
			new JoinServerPopup();
		}
	}
	
	public static boolean isHosting() {
		return useServer;
	}
	
	public static void setGamemode(byte gm) {
		gamemode = gm;
		
		try {
			startGame();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static byte getGamemode() {
		return gamemode;
	}
	
	private static void startGame() throws SlickException {
		client.start();
		
		if(useServer){
			server = new Server();
			server.start();
			
			PlayerManager.maxplayers = (gamemode == GameMode.GAME_1VS1 ? 2 : 4);
		}
	}
}
