package mist2meat.javaskipbo.server.game;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mist2meat.javaskipbo.network.SendablePacket;

public class Player {
	
	private byte id;
	private String name;
	
	private InetAddress ip;
	private int port;
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	private Map<Integer, ArrayList<Card>> freedecks = new HashMap<Integer, ArrayList<Card>>();
	
	public Player(byte i, String nam, InetAddress host, int prt){
		id = i;
		name = nam;
		ip = host;
		port = prt;
		
		
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
	
	public void addCardtoDeck(Card card){
		deck.add(card);
	}
}
