package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.util.ArrayList;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout_1v1;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.network.client.ReadyToPlayPacket;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameWindow extends BasicGame {
	
	private Image board;
	
	public static Layout curLayout;
	public static Image cardSlotImage;
	
	private ArrayList<Image> boards = new ArrayList<Image>();
	
	public static ArrayList<Image> cards = new ArrayList<Image>();
	
	boolean gameRunning = false;
	
	public GameWindow(String title) {
		super(title);
	}
	
	@Override
	public void init(GameContainer game) throws SlickException {
		game.setAlwaysRender(true);
		game.setTargetFrameRate(60);
		
		boards.add(new Image("gfx/boards/waiting.png"));
		boards.add(new Image("gfx/boards/1v1.png"));
		
		cardSlotImage = new Image("gfx/cards/slot.png");
		
		for(int i = 0; i <= 13; i++){
			Client.log("loaded card "+i);
			cards.add(new Image("gfx/cards/"+i+".png"));
		}
		
		board = boards.get(0);
		
		try {
			new ReadyToPlayPacket(ClientListener.socket,Main.client.getServerAddress(),3625).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		board.draw(0,0,container.getWidth(),container.getHeight());
		if(gameRunning) {
			curLayout.drawCardSlots();
			AnimatedCard.drawAnimations();
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if(gameRunning) {
			if(curLayout.changed(container.getWidth(),container.getHeight())){
				curLayout.update(container.getWidth(),container.getHeight());
			}
			AnimatedCard.updateAnimations();
		}
	}

	public void prepareGame() {
		int boardid;
		
		switch(Main.getGamemode()){
			case GameMode.GAME_1VS1:
				boardid = 1;
				curLayout = new Layout_1v1();
				break;
			default:
				boardid = 0;
				break;
		}
		
		curLayout.build();
		board = boards.get(boardid);
		
		gameRunning = true;
	}
}
