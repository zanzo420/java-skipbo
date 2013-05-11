package mist2meat.javaskipbo.enums;

public class PacketType {
	
	public static final byte PING = 0;
	public static final byte PONG = 1;
	
	public static final byte JOIN_SERVER = 2;
	public static final byte SERVER_MESSAGE = 3;
	public static final byte SERVER_LOGIN_RESPONSE = 4;
	
	public static final byte READY_TO_PLAY = 5;
	public static final byte GAME_BEGIN = 6;
	public static final byte GAME_END = 7;
	
	public static final byte PLAYERS_TURN = 8;
	
	public static final byte CARD_OPERATION = 9;
	public static final byte MOVE_CARD = 10;
	
	public static final byte PLAYER_CHAT = 11;
	public static final byte PLAYER_PING = 12;
	
}
