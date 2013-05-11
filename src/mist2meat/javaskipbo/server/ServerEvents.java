package mist2meat.javaskipbo.server;

import java.io.IOException;
import java.net.InetAddress;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.enums.ServerLoginResponse;
import mist2meat.javaskipbo.network.server.CardOperationPacket;
import mist2meat.javaskipbo.network.server.EndGamePacket;
import mist2meat.javaskipbo.network.server.PongClientPacket;
import mist2meat.javaskipbo.network.server.ServerLoginResponsePacket;
import mist2meat.javaskipbo.server.game.Game;
import mist2meat.javaskipbo.server.game.Player;
import mist2meat.javaskipbo.server.game.PlayerManager;

public class ServerEvents {

	public static int numresponses = 0;

	public static void playerLogin(String name, InetAddress ip, int port) throws IOException {
		Server.log("Logging in player: " + name + " from " + ip + ":" + port);

		byte response = PlayerManager.newPlayer(name, ip, port);

		ServerLoginResponsePacket resppack = new ServerLoginResponsePacket(ServerListener.socket, ip, port);
		resppack.setResponse(response);
		if (response == ServerLoginResponse.LOGIN_SUCCESS) {
			resppack.setID(PlayerManager.getPlayerByName(name).getID());
		}
		resppack.send();
	}

	public static void playerReady(byte id) {
		Player ply = PlayerManager.players.get(id);
		Server.log(ply.getName() + " is ready!");

		try {
			PlayerManager.broadcastMessage(ply.getName() + " joined the game!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		numresponses++;
		if (numresponses >= PlayerManager.minplayers) {
			Server.log("Game can start now!");
			ServerEvents.beginGame();
		}
	}

	public static void ping(InetAddress addr, int port) throws IOException {
		new PongClientPacket(ServerListener.socket, addr, port).send();
	}

	public static void beginGame() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				Server.currentGame = new Game();
				Server.currentGame.start();
			}
		});
		t.setDaemon(true);
		t.setName("Current Game");
		t.start();
	}

	public static void playerSay(byte from, String msg) {
		Player pl = PlayerManager.getPlayerByID(from);

		Server.log(pl.getName() + " says \"" + msg + "\"");

		try {
			PlayerManager.broadcastMessage(pl.getName() + ": " + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void endGame() throws IOException {
		EndGamePacket pack = new EndGamePacket(ServerListener.socket);
		pack.writeByte((byte) 0);
		PlayerManager.broadcastPacket(pack);
		
		clearTable();
	}

	public static void endGame(Player winner) throws IOException {
		EndGamePacket pack = new EndGamePacket(ServerListener.socket);
		pack.writeByte((byte) 1);
		pack.setWinner(winner);
		PlayerManager.broadcastPacket(pack);

		for (Player p : PlayerManager.players) {
			PlayerManager.broadcastMessage(p.getName() + " had " + p.getDeck(0).size() + " cards left");
		}

		Server.log(winner.getName() + " won the game!");
		Server.log("Game over!");

		PlayerManager.broadcastMessage("Game will restart in 10 seconds");
		Server.log("Game will restart in 10 seconds");

		Server.currentGame = null;

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				clearTable();

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ServerEvents.beginGame();
			}
		});
		t.start();
	}

	public static void clearTable() {
		for (Player p : PlayerManager.players) {
			p.getHand().clear();

			for (int i = 0; i <= 4; i++) {
				p.getDeck(i).clear();

				try {
					CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
					pack.setOperation(CardOperation.SET_CARD);
					pack.writeByte(p.getID());
					pack.writeByte((byte) i);
					pack.writeByte((byte) 0);
					pack.writeByte((byte) 0);
					PlayerManager.broadcastPacket(pack);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			p.initDecks();
		}

		for (int i2 = 0; i2 < 4; i2++) {
			try {
				CardOperationPacket pack2 = new CardOperationPacket(ServerListener.socket);
				pack2.setOperation(CardOperation.EMPTY_DECK);
				pack2.writeByte((byte) i2);

				PlayerManager.broadcastPacket(pack2);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
