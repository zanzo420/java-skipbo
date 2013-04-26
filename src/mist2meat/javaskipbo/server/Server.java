package mist2meat.javaskipbo.server;

public class Server {

	private static ServerConsole console;
	
	public Server() {
		console = new ServerConsole();
	}
	
	public void init() {
		//TODO: init server
		log("Server initialized");
	}
	
	//Convenience function
	public static void log(String msg) {
		console.log(msg);
	}
}
