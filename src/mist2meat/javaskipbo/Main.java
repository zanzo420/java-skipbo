package mist2meat.javaskipbo;

import mist2meat.javaskipbo.client.GameWindow;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.popups.HostJoinPopup;
import mist2meat.javaskipbo.popups.JoinServerPopup;
import mist2meat.javaskipbo.popups.SelectGamemodePopup;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
	
	private static GameMode gamemode;
	private static boolean server;
	
	public static void main(String[] args) {
		new HostJoinPopup();
	}
	
	public static void useServer(boolean use) {
		server = use;
		if(server){
			new SelectGamemodePopup();
		}else{
			new JoinServerPopup();
		}
	}
	
	public static void setGamemode(GameMode gm) {
		gamemode = gm;
		try {
			startGame();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public static GameMode getGamemode() {
		return gamemode;
	}
	
	private static void startGame() throws SlickException {
		if(server){
			//start server
		}
		
		AppGameContainer app = new AppGameContainer(new GameWindow("testipippeli"));
		
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}
