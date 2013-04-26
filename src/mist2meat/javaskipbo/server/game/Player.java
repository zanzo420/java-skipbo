package mist2meat.javaskipbo.server.game;

public class Player {
	
	private int id;
	private String name;
	
	
	//TODO:Networking
	//private String host;
	//private int port;
	
	public Player(int i){
		id = i;
		name = "Player "+i; //TODO: Select name
	}
	
	public int getID(){
		return id;
	}
	
	public int getAction(){
		//System.out.println(name+": Your turn");
		int action = -1;
		
		while(action == -1){
			//Waiting for packet
			//PacketHandler
		}
		
		return action; //If function returns -1, an error will be shown
	}
	
	public String getName(){
		return name;
	}
}
