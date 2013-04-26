package mist2meat.javaskipbo.server;

import mist2meat.javaskipbo.server.game.Player;

public class Game {
private int numPlayers = 0;
	
	private Player allPlayers[];
	
	public void createGame(int numply){
		
		//TODO: Create connected players with name
		
		
		numPlayers = numply;
		
		allPlayers = new Player[numply];
		for(int i = 0; i < numPlayers; i++){
			allPlayers[i] = new Player(i);
		}
	}
	
	public void update(){
		for(Player ply : allPlayers){
			//log("Asking "+ply.getName()+" for action");
			int action = ply.getAction(); //TODO: get int to get action?
			processAction(ply,action);
		}
	}
	
	public void processAction(Player ply, int action){
		switch(action){
		case -1:
				//log("Error (Case -1)");
			break;
		}
	}
}
