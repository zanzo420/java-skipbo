package mist2meat.javaskipbo.server;

public class Server {

	private static ServerConsole console;
	private ServerListener listener;
	
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
		log("Server initialized");
	}
	
	//Convenience function
	public static void log(String msg) {
		console.log(msg);
	}
}
