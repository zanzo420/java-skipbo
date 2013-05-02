package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mist2meat.javaskipbo.enums.CardOperation;
import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.network.server.CardOperationPacket;
import mist2meat.javaskipbo.server.Server;
import mist2meat.javaskipbo.server.ServerListener;

public class Player {
	
	private byte id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	private Map<Integer,Card> hand = new HashMap<Integer,Card>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Map<Integer, ArrayList<Card>> freedecks = new HashMap<Integer, ArrayList<Card>>();
	
	public Player(byte i, String nam, InetAddress host, int prt){
		id = i;
		name = nam;
		ip = host;
		port = prt;
		
		for(int i2 = 0; i2 <= 4; i2++){
			hand.put(i2, null);
		}
		
		for(int i3 = 1; i3 <= 4; i3++){
			freedecks.put(i3, new ArrayList<Card>());
		}
	}
	
	public byte getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public void sendPacket(SendablePacket pack) throws IOException {
		pack.setIp(ip);
		pack.setPort(port);
		pack.send();
	}
	
	public ArrayList<Card> getFreeDeck(int id){
		return freedecks.get(id);
	}
	
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	public void addCardtoDeck(Card card, boolean hidden){
		deck.add(card);
		
		try {
			CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
			pack.setOperation(CardOperation.DRAW_FROM_DECK);
			pack.writeByte(id);
			pack.writeByte((byte)0);
			pack.writeByte(hidden ? 0 : card.getNum());
			
			PlayerManager.broadcastPacket(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCardToHand(Card card) {
		int slot = 0;
		for(Map.Entry<Integer,Card> handcard : hand.entrySet()){
			if(handcard.getValue() == null){
				slot = handcard.getKey();
				break;
			}
		}
		hand.put(slot, card);
		
		try {
			CardOperationPacket pack = new CardOperationPacket(ServerListener.socket);
			pack.setOperation(CardOperation.DRAW_TO_HAND);
			pack.writeByte((byte)slot);
			pack.writeByte(card.getNum());
			
			sendPacket(pack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Integer,Card> getHand() {
		return hand;
	}

	public int getHandCardNum() {
		int num = 0;
		for(Map.Entry<Integer,Card> handcard : hand.entrySet()){
			if(handcard.getValue() != null){
				num++;
			}
		}
		return num;
	}
	
	public void fillHand() {
		ArrayList<Card> deck = Server.currentGame.deck;
		int handcards = getHandCardNum();
		if(handcards < 5){
			int missing = (5-handcards);
			for(int i = 1; i <= missing; i++){
				Card card = deck.get(deck.size()-1);
				addCardToHand(card);
				deck.remove(card);
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Server.log("Gave "+getName()+" "+missing+" hand cards");
		}
	}
}
