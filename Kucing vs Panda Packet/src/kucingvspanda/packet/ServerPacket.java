/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet;

import java.util.ArrayList;
import kucingvspanda.packet.models.RoomInfo;

/**
 *
 * @author Tifani
 */
public class ServerPacket implements Packet {
    private int identifier;
    private Object payload;
    
    public ServerPacket() {
        identifier = Identifier.UNKNOWN;
    }
    
    //----------------------------- LOGIN PACKET
    // Login Success Packet (getRoomList)
    public void buildLoginSuccessPacket(ArrayList<RoomInfo> roomInfoList) {
        identifier = Identifier.LOGIN_SUCCESS;
        payload = roomInfoList;
    }
    
    public ArrayList<RoomInfo> getRoomList() {
        return (ArrayList<RoomInfo>) payload;
    }
    
    // Login Failed Packet
    public void buildLoginFailedPacket() {
        identifier = Identifier.LOGIN_FAILED;
    }  
    
    
    //----------------------------- ADD ROOM PACKET
    // Add Room Success Packet (getNewRoomInfo)
    public void buildAddRoomSuccessPacket(RoomInfo newRoomInfo) {
        identifier = Identifier.ADD_ROOM_SUCCESS;
        payload = newRoomInfo;
    }
    
    public RoomInfo getNewRoomInfo() {
        return (RoomInfo) payload;
    }
    
    // Add Room Failed Packet
    public void buildAddRoomFailedPacket() {
        identifier = Identifier.ADD_ROOM_FAILED;
    }
    
    
    //----------------------------- PLAY PACKET
    // Play Success Packet (getActiveRoomName, getPlayerList, getSpectatorList)
    public void buildPlaySuccessPacket(String roomName, ArrayList<String> players, ArrayList<String> spectators) {
        identifier = Identifier.PLAY_SUCCESS;
        ArrayList<Object> info = new ArrayList<>();
        info.add(roomName);
        info.add(players);
        info.add(spectators);
        payload = info;
    }
    
    public String getActiveRoomName() {
        return (String) ((ArrayList<Object>) payload).get(Identifier.ROOM_NAME);
    }
    
    public ArrayList<String> getPlayerList() {
        return (ArrayList<String>) ((ArrayList<Object>) payload).get(Identifier.PLAYER_LIST);
    }
    
    public ArrayList<String> getSpectatorList() {
        return (ArrayList<String>) ((ArrayList<Object>) payload).get(Identifier.SPECTATOR_LIST);
    }
    
    // New Player Packet (getNewPlayerName)
    public void buildNewPlayerPacket(String player) {
        identifier = Identifier.NEW_PLAYER;
        payload = player;
    }
    
    public String getNewPlayerName() {
        return (String) payload;
    }
    
    // Update Player Count Packet (getUpdatedRoomName)
    public void buildAddPlayerCountPacket(String roomName) {
        identifier = Identifier.ADD_PLAYER_COUNT;
        payload = roomName;
    }
    
    public String getUpdatedRoomName() {
        return (String) payload;
    }
    
    // Play Failed Packet
    public void buildPlayFailedPacket() {
        identifier = Identifier.PLAY_FAILED;
    }
    
    
    //----------------------------- SPECTATOR PACKET
    // Spectator Success Packet (getActiveRoomName, getPlayerList, getSpectatorList, getBoard)
    public void buildSpectatorSuccessPacket(String roomName, ArrayList<String> players, ArrayList<String> spectators, String[][] board) {
        identifier = Identifier.SPECTATOR_SUCCESS;
        ArrayList<Object> info = new ArrayList<>();
        info.add(roomName);
        info.add(players);
        info.add(spectators);
        info.add(board);
        payload = info;
    }
    
    public String[][] getBoard() {
        return (String[][]) ((ArrayList<Object>) payload).get(Identifier.BOARD);
    }
    
    // New Spectator Packet (getNewSpectatorName)
    public void buildNewSpectatorPacket(String player) {
        identifier = Identifier.NEW_SPECTATOR;
        payload = player;
    }
    
    public String getNewSpectatorName() {
        return (String) payload;
    }
    
    
    //----------------------------- BEGIN GAME PACKET
    // Begin Game Packet
    public void buildStartGameSuccessPacket(String roomName) {
        identifier = Identifier.START_GAME_SUCCESS;
        payload = roomName;
    }
    
    // Kapan kapan failed
    
    
    //----------------------------- ADD PAWN PACKET
    // Pawn Placed Packet (getPlayer, getX, getY, getNextPlayer)
    public void buildAddPawnSuccessPacket (String player, int x, int y, String nextPlayer) {
        identifier = Identifier.PAWN_PLACED;
        ArrayList<String> info = new ArrayList<>();
        info.add(player);
        info.add(String.valueOf(x));
        info.add(String.valueOf(y));
        info.add(nextPlayer);
        payload = info;
    }
    
    public String getPlayer() {
        return (String) ((ArrayList<String>) payload).get(Identifier.PLAYER);
    }
    
    public String getNextPlayer() {
        return (String) ((ArrayList<String>) payload).get(Identifier.NEXT_PLAYER);
    }
    
    public int getX() {
        return Integer.parseInt( ((ArrayList<String>)payload).get(Identifier.X) );
    }
    
    public int getY() {
        return Integer.parseInt( ((ArrayList<String>)payload).get(Identifier.Y) );
    }
    
    //----------------------------- LEAVE GAME PACKET
    // Leave Game Success Packet (getRoomList)
    public void buildLeaveGameSuccessPacket(ArrayList<RoomInfo> roomInfoList) {
        identifier = Identifier.LEAVE_GAME_SUCCESS;
        payload = roomInfoList;
    }
    
    // Leave Player Packet (getPlayerName)
    public void buildLeavePlayerPacket(String player) {
        identifier = Identifier.PLAYER_LEAVE;
        payload = player;
    }
    
    // Decrement Player Count Packet (getUpdatedRoomName)
    public void buildDecPlayerCountPacket(String roomName) {
        identifier = Identifier.DEC_PLAYER_COUNT;
        payload = roomName;
    }
    
    //----------------------------- END GAME PACKET
    // End Game Packet (getWinType, getX, getY)
    public void buildWinPacket(String winType, int x, int y) {
        identifier = Identifier.ADD_PAWN;
        ArrayList<String> info = new ArrayList<>();
        info.add(winType);
        info.add(String.valueOf(x));
        info.add(String.valueOf(y));
        payload = info;
    }
    
    public String getWinType() {
        return (String) ((ArrayList<String>) payload).get(Identifier.WIN_TYPE);
    }
    
    // Board Full Packet
    public void buildBoardFullPacket() {
        identifier = Identifier.BOARD_FULL;
    }
    
    //----------------------------- HIGHSCORE PACKET
    // Highscore Packet (getHighscore)
    public void buildHighScorePacket (ArrayList< ArrayList<String> > highscore) {
        identifier = Identifier.HIGHSCORE;
        payload = highscore;
    }
    
    public ArrayList< ArrayList<String> > getHighscore() {
        return (ArrayList< ArrayList<String> >) payload;
    }
    
    //----------------------------- CHAT PACKET
    // Chat Packet (getHighscore)
    public void buildChatPacket(String message) {
        identifier = Identifier.MESSAGE;
        payload = message;
    } 

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public Object getMessage() {
        return (String) payload;
    }
    
}
