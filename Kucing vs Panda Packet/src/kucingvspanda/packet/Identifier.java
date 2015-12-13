/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet;

/**
 *
 * @author Tifani
 */
public class Identifier {
    public static final int UNKNOWN = 0;
    
    // FROM CLIENT
    public static final int LOGIN = 1; //nickname
    public static final int ADD_ROOM = 2; //roomname
    public static final int PLAY = 3; //roomname + player
    public static final int WATCH = 4; //roomname + player
    public static final int START_GAME = 5; //roomname
    public static final int ADD_PAWN = 6; //roomname + player + x + y
    public static final int LEAVE_GAME = 7; //roomname + player
    public static final int VIEW_HIGHSCORE = 8; //empty
    public static final int CHAT = 9; //message
    public static final int LOGOUT = 10; //empty
    
    // FROM SERVER
    public static final int LOGIN_SUCCESS = 11; //list<roominfo>
    public static final int LOGIN_FAILED = 12;
    public static final int ADD_ROOM_SUCCESS = 13; //roominfo
    public static final int ADD_ROOM_FAILED = 14;
    public static final int PLAY_SUCCESS = 15; //roomname + players + spectators
    public static final int NEW_PLAYER = 16; //playername
    public static final int ADD_PLAYER_COUNT = 17; //roomname 
    public static final int PLAY_FAILED = 18; //message
    public static final int SPECTATOR_SUCCESS = 19; //players + spectators + board (matrix string)
    public static final int NEW_SPECTATOR = 20; //playername
    public static final int START_GAME_SUCCESS = 21; //roomname
    public static final int PAWN_PLACED = 22; //roomname + player + x + y
    public static final int LEAVE_GAME_SUCCESS = 23; //list<roominfo>
    public static final int PLAYER_LEAVE = 24; //playername
    public static final int DEC_PLAYER_COUNT = 25; //roomname
    public static final int WIN = 26; //x + y + win type
    public static final int BOARD_FULL = 27;
    // kapan kapan kasih tau player leave game saat kurang dari 3
    public static final int HIGHSCORE_LIST = 28; //highscore list
    public static final int CHAT_SUCCESS = 29; //message
    public static final int LOGOUT_SUCCESS = 30; //playername
    
    // Array list index
    public static final int NICKNAME = 0;
    public static final int ROOM_NAME = 0;
    public static final int PLAYER = 0;
    public static final int WIN_TYPE = 0;
    public static final int X = 1;
    public static final int Y = 2;
    public static final int CLIENT_X = 0;
    public static final int CLIENT_Y = 1;
    public static final int NEXT_PLAYER = 3;
    public static final int PLAYER_LIST = 1;
    public static final int SPECTATOR_LIST = 2;
    public static final int BOARD = 3;
    public static final int MESSAGE = 1;
    public static final int CHAT_SENDER = 0;
    public static final int CHAT_CONTENT = 1;
}
