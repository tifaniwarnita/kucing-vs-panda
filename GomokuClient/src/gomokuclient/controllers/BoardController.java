/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuclient.controllers;

import gomokuclient.models.*;
import gomokuclient.views.GameFrame;

/**
 *
 * @author ASUS X202E
 */
public class BoardController {
    private GameFrame frame;
    private BoardModel model = new BoardModel(1);
    
    public BoardController(GameFrame frame) {
        this.frame = frame;
    }
    
    public void test() {
        frame.initBoard(model.getTableModel());
    }
    
}
