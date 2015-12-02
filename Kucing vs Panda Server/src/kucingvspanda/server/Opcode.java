/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;

/**
 *
 * @author FiqieUlya
 */
/**************** an interface to define different operation code **************/

public interface Opcode {
    int CLIENT_CONNECTEING = 1;
    int CLIENT_CONNECTED = 2;
    int CLIENT_MESSAGE = 3;
    int CLIENT_BROADCAST = 4;
}