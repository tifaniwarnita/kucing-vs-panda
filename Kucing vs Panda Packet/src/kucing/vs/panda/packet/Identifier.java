/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucing.vs.panda.packet;

/**
 *
 * @author Tifani
 */
public class Identifier {
    // From client
    public static final int LOGIN = 1; //nickname
    public static final int ADD_ROOM = 2; //roomname
    public static final int PLAY = 3; //roomname + player
    public static final int WATCH = 4; //roomname + player
    
    public static final int START_GAME = 5; //roomname
    public static final int ADD_PAWN = 6; //player + x + y + roomname
    public static final int EXIT_GAME = 7; //roomname + player

    
    public static final int END_GAME = 8; //roomname + 
    
    public static final int CHAT = 9;
    public static final int ROOM_INFO = 2;
}
