/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.packet;

import java.util.ArrayList;
import kucingvspanda.packet.model.RoomInfo;
import kucingvspanda.packet.model.RoomModel;

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
    
    // Login Success Packet
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
    
    public String getErrorMessage() {
        return (String) payload;
    }
    
    // Add Room Success Packet
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
    
    // Play Success Packet
    public void buildPlaySuccessPacket(String roomName, ArrayList<String> players, ArrayList<String> spectators) {
        identifier = Identifier.PLAY_SUCCESS;
        ArrayList<String> info = new ArrayList<>();
        info.add(roomName);
        ArrayList< ArrayList<String> > listInfo = new ArrayList<>();
        listInfo.add(info);
        listInfo.add(players);
        listInfo.add(spectators);
        payload = listInfo;
    }
    
    public String getActiveRoomName() {
        return (String) ((ArrayList< ArrayList<String> >) payload).get(0).get(Identifier.ROOM_NAME);
    }
    
    public ArrayList<String> getPlayerList() {
        return (ArrayList<String>) ((ArrayList< ArrayList<String> >) payload).get(Identifier.PLAYER_LIST);
    }
    
    public ArrayList<String> getSpectatorList() {
        return (ArrayList<String>) ((ArrayList< ArrayList<String> >) payload).get(Identifier.SPECTATOR_LIST);
    }
    
    // New Player Packet
    public void buildNewPlayerPacket(String player) {
        identifier = Identifier.NEW_PLAYER;
        payload = player;
    }
    
    public String getNewPlayerName() {
        return (String) ((ArrayList<String>)payload).get(Identifier.PLAYER);
    }
    
    // Update Player Count Packet
    public void buildUpdatePlayerCountPacket(String roomName) {
        identifier = Identifier.UPDATE_PLAYER_COUNT;
        payload = roomName;
    }
    
    public String getUpdatedRoomName() {
        return (String) payload;
    }
    
    // Play Failed Packet
    public void buildPlayFailedPacket(String errorMessage) {
        identifier = Identifier.PLAY_FAILED;
        payload = errorMessage;
    }
    
    // New Spectator Packet
    public void buildNewSpectatorPacket(String roomName, String player) {
        identifier = Identifier.NEW_SPECTATOR;
        ArrayList<String> info = new ArrayList<>();
        info.add(roomName);
        info.add(player);
        payload = info;
    }
    
    public String getNewSpectatorName() {
        return (String) ((ArrayList<String>)payload).get(Identifier.PLAYER);
    }
    
    
    // Begin Game Packet
    public void buildBeginGamePacket(String roomName) {
        identifier = Identifier.BEGIN_GAME;
        payload = roomName;
    }
    
    // Pawn Placed Packet
    public void buildAddPawnPacket (String roomName, String player, int x, int y) {
        identifier = Identifier.ADD_PAWN;
        ArrayList<String> info = new ArrayList<>();
        info.add(roomName);
        info.add(player);
        info.add(String.valueOf(x));
        info.add(String.valueOf(y));
        payload = info;
    }
    
    public int getX() {
        return Integer.parseInt( ((ArrayList<String>)payload).get(Identifier.X) );
    }
    
    public int getY() {
        return Integer.parseInt( ((ArrayList<String>)payload).get(Identifier.Y) );
    }
    
    // Player Leave Packet
    public void buildPlayerLeavePacket (String roomName, String player) {
        identifier = Identifier.PLAYER_LEAVE;
        ArrayList<String> info = new ArrayList<>();
        info.add(roomName);
        info.add(player);
        payload = info;
    }
    
    // End Game Packet
    public void buildEndGamePacket (String roomName, String winner) {
        identifier = Identifier.END_GAME;
        ArrayList<String> info = new ArrayList<>();
        info.add(roomName);
        info.add(winner);
        payload = info;
    }
    
    public String getWinner() {
        return (String) payload;
    }
    
    // Highscore Packet
    public void buildHighScorePacket (ArrayList< ArrayList<String> > highscore) {
        identifier = Identifier.HIGHSCORE;
        payload = highscore;
    }
    
    public ArrayList< ArrayList<String> > getHighscore() {
        return (ArrayList< ArrayList<String> >) payload;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public Object getMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
