package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mist2meat.javaskipbo.network.SendablePacket;
import mist2meat.javaskipbo.server.game.content.Card;

public class Player {
	
	private int id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Map<Integer, ArrayList<Card>> freedecks = new HashMap<Integer, ArrayList<Card>>();
	
	public Player(int i, String nam, InetAddress host, int prt){
		id = i;
		name = nam;
		ip = host;
		port = prt;
		
		
	}
	
	public int getID(){
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
	
	public void addCardtoDeck(byte num){
		deck.add(new Card(num));
	}
}
