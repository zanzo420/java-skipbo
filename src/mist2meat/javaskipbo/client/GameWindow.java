package mist2meat.javaskipbo.client;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameWindow extends BasicGame {
	
	Image board;
	boolean gameRunning = false;
	
	public GameWindow(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		board.draw(0,0,800,600);
		if(gameRunning) {
			//draw stuff
		}
	}

	@Override
	public void init(GameContainer game) throws SlickException {
		game.setAlwaysRender(true);
		game.setTargetFrameRate(60);
		
		board = new Image("gfx/boards/waiting.png");
	}
	
	public void prepareGame() {
//		try {
//			String mode;
//			
//			switch(Main.getGamemode()){
//				case GameMode.GAME_1VS1:
//					mode = "1v1";
//					break;
//				default:
//					mode= "waiting";
//					break;
//			}
//			
//			board.destroy();
//			board = new Image("gfx/boards/"+mode+".png");
//		} catch (SlickException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
	}

}
