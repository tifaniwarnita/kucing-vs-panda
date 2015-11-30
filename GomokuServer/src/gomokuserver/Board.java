/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomokuserver;

/**
 *
 * @author ASUS X202E
 */
public class Board {
   private String[][] board = new String[20][20];
    
   public String getPlayerAtCoordinate(int x, int y) {
       return board[x][y];
   }
   
   public void setPlayerAtCoordinate(int x, int y, String name) {
       board[x][y] = name;
   }
   
   public boolean checkWin(int x, int y, String name) {
       setPlayerAtCoordinate(x,y,name);
       if (!checkHorizontal(x,y)) {
           if (!checkVertical(x,y)) {
               if (!checkDiagonal(x,y)) {
                   return false;
               }
           }
       }
       return true;
   }
   
   private boolean checkHorizontal(int x, int y) {
       int counter = 0;
       int start = (y>4) ? (y-4) : 0;
       int end = (y<16) ? (start+8) : 19;
       int i = start;
       while (i<end || counter<5) {
           if (board[i][y].equals(board[x][y])) counter++;
           else counter = 0;
           i++;
       }
       if (counter==5) return true;
       else return false;
   }
   private boolean checkVertical(int x, int y) {
       int counter = 0;
       int start = (x>4) ? (x-4) : 0;
       int end = (x<16) ? (start+8) : 19;
       int i = start;
       while (i<end || counter<5) {
           if (board[x][i].equals(board[x][y])) counter++;
           else counter = 0;
           i++;
       }
       if (counter==5) return true;
       else return false;
   }
   private boolean checkDiagonal(int x, int y) {
       int counter = 0;
       int xstart = x;
       int ystart = y;
       while (xstart>0 && xstart>(x-4) && ystart>0 && ystart>(x-4)) {
           xstart--;
           ystart--;
       }
       int xt = xstart;
       int yt = ystart;
       while ((xt<19 && xt<(xstart+8) && yt<19 && yt<(ystart+8)) || counter<5) {
           if (board[xt][yt].equals(board[x][y])) counter++;
           else counter = 0;
           xt++; yt++;
       }
       if (counter==5) return true;
       else return false;
   }
   
}
