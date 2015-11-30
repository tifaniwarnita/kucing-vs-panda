/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda;

import java.util.ArrayList;

/**
 *
 * @author Tifani
 */
public class Packet {
    private int identifier;
    private ArrayList<Object> message = new ArrayList<>();
    
    public Packet() {
        identifier = 0;
    }
    
    /*  Server mengirim informasi apakah status permainan
        sudah selesai (WIN) atau belum (KEEP_PLAYING) 
        sekaligus block terakhir yang diisi player */
    public Packet(int _identifier, int playingStatus, Block block) {
        identifier = _identifier;
        message.add((Integer) playingStatus);
        message.add(block);
    }
    
    /*  Client mengirim informasi mengenai aksi yang sedang
        dilakukan oleh client. Misalnya, register, new board, dsb. */
    public Packet(int _identifier, int action) {
        identifier = _identifier;
        message.add((Integer) action);
    }
    
    /*  Client mengirim informasi mengenai block mana yang dipilih
        oleh player untuk diisi */
    public Packet(int _identifier, Block block) {
        identifier = _identifier;
        message.add(block);
    }
    
}
