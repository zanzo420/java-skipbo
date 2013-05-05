package mist2meat.javaskipbo.client;

import java.io.IOException;
import java.util.ArrayList;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.client.drawable.AnimatedCard;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout_1v1;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout_1v1v1;
import mist2meat.javaskipbo.client.drawable.cardlayout.Layout_1v1v1v1;
import mist2meat.javaskipbo.client.game.DragNDropManager;
import mist2meat.javaskipbo.enums.GameMode;
import mist2meat.javaskipbo.network.client.ReadyToPlayPacket;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class GameWindow extends BasicGame {
	
	private Image board;
	
	public static Layout curLayout;
	public static Image cardSlotImage;
	
	private ArrayList<Image> boards = new ArrayList<Image>();
	
	public static ArrayList<Image> cards = new ArrayList<Image>();
	
	public static boolean gameRunning = false;
	
	public static Sound chatsound,turnsound;
	
	public GameWindow(String title) {
		super(title);
	}
	
	@Override
	public void init(GameContainer game) throws SlickException {
		game.setAlwaysRender(true);
		game.setClearEachFrame(false);
		game.setTargetFrameRate(60);
		game.setShowFPS(false);
		
		boards.add(new Image("gfx/boards/waiting.png"));
		boards.add(new Image("gfx/boards/1v1.png"));
		
		cardSlotImage = new Image("gfx/cards/slot.png");
		
		for(int i = 0; i <= 13; i++){
			Client.log("loaded card "+i);
			cards.add(new Image("gfx/cards/"+i+".png"));
		}
		
		board = boards.get(0);
		
		chatsound = new Sound("sfx/chat.wav");
		turnsound = new Sound("sfx/turn.wav");
		
		try {
			new ReadyToPlayPacket(ClientListener.socket,Main.client.getServerAddress(),3625).send();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DragNDropManager.init(game.getInput());
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		graphics.setAntiAlias(true);
		board.draw(0,0,container.getWidth(),container.getHeight());
		if(gameRunning) {
			graphics.scale(curLayout.xscale, curLayout.yscale);
			curLayout.drawCardSlots();
			curLayout.drawPlayerNames(graphics);
			
			AnimatedCard.drawAnimations();
			DragNDropManager.draw();
		}
		graphics.setAntiAlias(false);
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
		
		if(gameRunning){
			return;
		}
		
		int boardid;
		
		switch(Main.getGamemode()){
			case GameMode.GAME_1VS1:
				boardid = 1;
				curLayout = new Layout_1v1();
				break;
			case GameMode.GAME_1VS1VS1:
				boardid = 1;
				curLayout = new Layout_1v1v1();
				break;
			case GameMode.GAME_1VS1VS1VS1:
				boardid = 1;
				curLayout = new Layout_1v1v1v1();
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
