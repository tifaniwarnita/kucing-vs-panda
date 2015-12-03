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
    public static final int CHAT = 9; //roomname + player + message
    
    // FROM SERVER
    public static final int LOGIN_SUCCESS = 10; //list<roominfo>
    public static final int LOGIN_FAILED = 11; //message
    public static final int ADD_ROOM_SUCCESS = 12; //roominfo
    public static final int ADD_ROOM_FAILED = 13; //message
    public static final int ROOM_MODEL = 14; //room model
    public static final int NEW_PLAYER = 15; //roomname + playername
    public static final int NEW_SPECTATOR = 16; //roomname + playername
    public static final int PLAY_SUCCESS = 17; //roomname + player -> plus room model packet u selain c
    public static final int PLAY_FAILED = 18; //message
    public static final int BEGIN_GAME = 19; //roomname
    public static final int PAWN_PLACED = 20; //roomname + player + x + y
    public static final int PLAYER_LEAVE = 21; //roomname + player
    public static final int END_GAME = 21; //roomname + winner
    public static final int HIGHSCORE = 22; //highscore list
    
    
    
    public static final int ROOM_INFO = 2;
    
    // Array list index
    public static final int NICKNAME = 0;
    public static final int ROOM_NAME = 0;
    public static final int PLAYER = 0;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int MESSAGE = 1;
    
    
    
    
    public static final int WINNER = 1;
}
