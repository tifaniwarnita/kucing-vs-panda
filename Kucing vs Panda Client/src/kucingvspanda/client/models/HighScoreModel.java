/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.client.models;

import javax.swing.table.DefaultTableModel;
import kucingvspanda.packet.models.HighScoreInfo;

/**
 *
 * @author ASUS X202E
 */
public class HighScoreModel {
    private DefaultTableModel highScoreModel = new DefaultTableModel(new Object[]{"Ranking", "Player", "Score"},0);
    
    public DefaultTableModel getTableModel() {
        return highScoreModel;
    }
    
    public void initModel(java.util.List<HighScoreInfo> highScoreList) {
        highScoreModel.setRowCount(0);
        for (int i=0;i<highScoreList.size();i++) {
            HighScoreInfo hs = highScoreList.get(i);
            Object[] o = new Object[3];
            o[0] = i+1;
            o[1] = hs.getPlayerName();
            o[2] = hs.getScore();
            highScoreModel.addRow(o);
        }
    }
    
}
