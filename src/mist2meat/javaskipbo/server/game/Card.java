package mist2meat.javaskipbo.server.game;

public class Card {
	
	private byte number;
	private byte wildcard;
	
	public Card(byte num){
		number = num;
	}
	
	public Card(byte num, byte wild){
		number = num;
		wildcard = wild;
	}
	
	public byte getNum(){
		if(number == 13 && wildcard > 0){
			return wildcard;
		}else{
			return number;
		}
	}
}
