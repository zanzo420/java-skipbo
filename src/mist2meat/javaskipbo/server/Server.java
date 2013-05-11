package mist2meat.javaskipbo.server;

import mist2meat.javaskipbo.server.game.Game;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class Server {

	private static ServerConsole console;
	private ServerListener listener;
	public static Game currentGame;
	
	public Server() {
		console = new ServerConsole();
		listener = new ServerListener();
	}
	
	public void start() {
		log("Starting server");
		init();
	}
	
	public void init() {
		listener.setPassive(true);
		PlayerManager.startPinging();
		log("Server initialized");
	}
	
	//Convenience function
	public static void log(String msg) {
		console.log(msg);
	}
}
