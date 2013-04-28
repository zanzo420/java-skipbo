package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.util.ArrayList;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.network.client.ReadyToPlayPacket;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameWindow extends BasicGame {
	
	Image board;
	ArrayList<Image> boards = new ArrayList<Image>();
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
		
		boards.add(new Image("gfx/boards/waiting.png"));
		boards.add(new Image("gfx/boards/1v1.png"));
		
		board = boards.get(0);
		
		try {
			new ReadyToPlayPacket(ClientListener.socket,Main.client.getServerAddress(),3625).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepareGame() {
		int boardid = 0;
		
		switch(Main.getGamemode()){
			case GameMode.GAME_1VS1:
				boardid = 1;
				break;
			default:
				boardid = 0;
				break;
		}
		
		board = boards.get(boardid);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
	}

}
