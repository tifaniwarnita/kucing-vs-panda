/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucing.vs.panda.packet;

import java.util.ArrayList;

/**
 *
 * @author Tifani
 */
public class ClientPacket implements Packet{
    
    public int identifier;
    public ArrayList<String> payload = new ArrayList<>();;
    
    public ClientPacket() {
        identifier = Identifier.UNKNOWN;
    }
    
    // Login Packet
    public void buildLoginPacket(String nickname) {
        identifier = Identifier.LOGIN;
        payload.add(nickname);
    }
    
    public String getNickname() {
        return payload.get(Identifier.NICKNAME);
    }
    
    // Add Room Packet
    public void buildAddRoomPacket (String roomName) {
        identifier = Identifier.ADD_ROOM;
        payload.add(roomName);
    }
    
    public String getRoomName() {
        return payload.get(Identifier.ROOM_NAME);
    }
    
    // Play Packet
    public void buildPlayPacket (String roomName, String player) {
        identifier = Identifier.PLAY;
        payload.add(roomName);
        payload.add(player);
    }
    
    public String getPlayer() {
        return payload.get(Identifier.PLAYER);
    }
    
    // Spectator Packet
    public void buildSpectatorPacket (String roomName, String player) {
        identifier = Identifier.WATCH;
        payload.add(roomName);
        payload.add(player);
    }
    
    // Start Game Packet
    public void buildStartGamePacket (String roomName) {
        identifier = Identifier.START_GAME;
        payload.add(roomName);
    }
    
    // Add Pawn Packet
    public void buildAddPawnPacket (String roomName, String player, int x, int y) {
        identifier = Identifier.ADD_PAWN;
        payload.add(roomName);
        payload.add(player);
        payload.add(String.valueOf(x));
        payload.add(String.valueOf(y));
    }
    
    public int getX() {
        return Integer.parseInt(payload.get(Identifier.X));
    }
    
    public int getY() {
        return Integer.parseInt(payload.get(Identifier.Y));
    }

    // Exit Game Packet
    public void buildExitGamePacket (String roomName, String player) {
        identifier = Identifier.EXIT_GAME;
        payload.add(roomName);
        payload.add(player);
    }
    
    // View Highscore Packet
    public void buildViewHighscorePacket() {
        identifier = Identifier.VIEW_HIGHSCORE;
    }
    
    // Chat Packet
    public void buildChatPacket(String roomName, String player, String message) {
        identifier = Identifier.CHAT;
        payload.add(roomName);
        payload.add(player);
        payload.add(message);
    }
    
    public String getChatMessage() {
        return payload.get(Identifier.MESSAGE);
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