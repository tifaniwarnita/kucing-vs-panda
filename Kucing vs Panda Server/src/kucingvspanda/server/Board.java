/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kucingvspanda.server;
import kucingvspanda.packet.models.WinInfo;

/**
 *
 * @author ASUS X202E
 */
public class Board {
   private String[][] board = new String[20][20];
   int emptySquares = 400;
   
   public String getPlayerAtCoordinate(int x, int y) {
       return getBoard()[x][y];
   }
   
   public void setPlayerAtCoordinate(int x, int y, String name) {
        getBoard()[x][y] = name;
        emptySquares--;
   }
   
   public WinInfo checkWin(int x, int y) {
       WinInfo win;
       if ((win = checkHorizontal(x,y)) == null) {
           if ((win = checkVertical(x,y)) == null) {
               if ((win = checkDiagonalLeft(x,y)) == null) {
                   if ((win = checkDiagonalRight(x,y)) == null) return null;
               }
           }
       }
       return win;
   }
   
   private WinInfo checkHorizontal(int x, int y) {
       int counter = 0;
       int start = (x>4) ? (x-4) : 0;
       int end = (x<16) ? (start+8) : 19;
       int i = start;
       int firstx = start;
       while (i<=end && counter<5) { 
            if (getBoard()[i][y]!=null) {
                if (getBoard()[i][y].equals(getBoard()[x][y])) {
                    if (counter==0) firstx = i;
                    counter++;
                }
                else counter=0;
            }
           else counter = 0;
           i++;
       }
       if (counter==5) return new WinInfo(firstx,y,"Horizontal");
       else return null;
   }
   private WinInfo checkVertical(int x, int y) {
       int counter = 0;
       int start = (y>4) ? (y-4) : 0;
       int end = (y<16) ? (start+8) : 19;
       int i = start;
       int firsty = start;
       while (i<=end && counter<5) {
            if (getBoard()[x][i]!=null) {
                if (getBoard()[x][i].equals(getBoard()[x][y])) {
                    if (counter==0) firsty = i;
                    counter++;
                }
                else counter=0;
            }
            else counter = 0;
            i++;
       }
       if (counter==5) return new WinInfo(x,firsty,"Vertical");
       else return null;
   }
   private WinInfo checkDiagonalLeft(int x, int y) {
       int counter = 0;
       int xstart = x;
       int ystart = y;
       while (xstart>0 && xstart>(x-4) && ystart>0 && ystart>(y-4)) {
           xstart--;
           ystart--;
       }
       int xt = xstart;
       int yt = ystart;
       int firstx = xstart;
       int firsty = ystart;
       while ((xt<=19 && xt<=(xstart+8) && yt<=19 && yt<=(ystart+8)) && counter<5) {
            if (getBoard()[xt][yt]!=null) {
               if (getBoard()[xt][yt].equals(getBoard()[x][y])) {
                   if (counter==0) {
                       firstx = xt;
                       firsty = yt;
                   }
                   counter++;
               }
                else counter = 0;
            }
            else counter = 0;
            
            xt++; yt++;
       }
       if (counter==5) return new WinInfo(firstx,firsty,"Diagonal Left");
       else return null;
   }
   
   private WinInfo checkDiagonalRight(int x, int y) {
       int counter = 0;
       int xstart = x;
       int ystart = y;
       while (xstart<19 && xstart<(x+4) && ystart>0 && ystart>(y-4)) {
           xstart++;
           ystart--;
       }
       int xt = xstart;
       int yt = ystart;
       int firstx = xstart;
       int firsty = ystart;
       while (xt>=0 && xt>=(xstart-8) && yt<=19 && yt<=(ystart+8) && counter<5) {
           if (getBoard()[xt][yt]!=null) {
               if (getBoard()[xt][yt].equals(getBoard()[x][y])) {
                   if (counter==0) {
                       firstx = xt;
                       firsty = yt;
                   }
                   counter++;
               }
                else counter = 0;
            }
            else counter = 0;
            xt--; yt++;
       }
       if (counter==5) return new WinInfo(firstx,firsty,"Diagonal Right");
       else return null;
   }
   
   public boolean boardFull() {
       return (emptySquares==0);
   }

    /**
     * @return the board
     */
    public String[][] getBoard() {
        return board;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(String[][] board) {
        this.board = board;
    }
   
}
