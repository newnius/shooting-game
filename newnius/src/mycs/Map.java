/*
 * panel of map
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;
import java.awt.GridLayout;
import static java.lang.Math.random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Newnius
 *
 *
 *
 * paint a map tmpWidth * tmpHeight road or wall by random numbet of wall should
 * be no less than the total amount, and they should not block the players the
 * walls should not be seperated everywhere, that is to say, they should form
 * some blocks. player can only see what he can see now, so do not show all the
 * map you can set some blocks invisible or set absolute location of map.
 * whatever, as you like^-^
 */
public class Map extends JPanel {

    private int width;
    private int height;
    boolean mapDB[][];

    public Map(int tmpSizeX, int tmpSizeY) {
        width = tmpSizeX * GlobalVars.BLOCK_SIZE;
        height = tmpSizeY * GlobalVars.BLOCK_SIZE;
        setLayout(new GridLayout(tmpSizeY, tmpSizeX));
        setSize(width, height);
        mapDB = new boolean[tmpSizeX][tmpSizeY];
        for (int i = 0; i < tmpSizeY; i++) {
            for (int j = 0; j < tmpSizeX; j++) {
                JLabel jlabel = new JLabel();
                jlabel.setSize(GlobalVars.BLOCK_SIZE, GlobalVars.BLOCK_SIZE);
                if (random() < 0.2) {
                    mapDB[j][i] = false;
                    jlabel.setIcon(new ImageIcon("imgs/wall.gif"));
                } else {
                    mapDB[j][i] = true;
                    jlabel.setIcon(new ImageIcon("imgs/grass.gif"));
                }
                add(jlabel);
            }
        }
    }

    //return areas that tmpName can see now
    public boolean[][] sight(int x, int y) {
        return mapDB;
    }

    public boolean canBulletFly(Bullet bullet) {

        int x = bullet.getLocationX() + GlobalVars.BLOCK_SIZE / 4;
        int y = bullet.getLocationY() + GlobalVars.BLOCK_SIZE / 4;
        //judge weather bullet hit the boundary
        if (x < 0 || y < 0 || x > width || y > height) {
            return false;
        }

        //judge weather bullet hit the wall
        int indexX = x / GlobalVars.BLOCK_SIZE;
        int indexY = y / GlobalVars.BLOCK_SIZE;
        if (indexX >= 0 && indexX < width / GlobalVars.BLOCK_SIZE && indexY >= 0 && indexY < height / GlobalVars.BLOCK_SIZE) {
            return mapDB[indexX][indexY];
        } else {
            return false;
        }

    }

    public boolean canMoveTo(Player player, int x, int y) {

        //int x = player.getX() + GlobalVars.BLOCK_SIZE / 4;
        //int y = player.getY() + GlobalVars.BLOCK_SIZE / 4;
        //judge weather bullet hit the boundary
        if (x < 0 || y < 0 || x + GlobalVars.PLAYER_SIZE >= width || y + GlobalVars.PLAYER_SIZE >= height) {
            return false;
        }

        //judge weather bullet hit the wall
        int indexX = x / GlobalVars.BLOCK_SIZE;
        int indexY = y / GlobalVars.BLOCK_SIZE;
        //north-west
        //判断顺序不能改变，会在右边界和下边界处异常，原因是数组越界
        if (in(indexX * GlobalVars.BLOCK_SIZE, indexY * GlobalVars.BLOCK_SIZE, x, y) && !mapDB[indexX][indexY]) {
            return false;
        }
        //north-east
        if (in((indexX+1) * GlobalVars.BLOCK_SIZE, indexY * GlobalVars.BLOCK_SIZE, x, y) && !mapDB[indexX+1][indexY]) {
            return false;
        }
        //south-west
        if (in(indexX * GlobalVars.BLOCK_SIZE, (indexY+1) * GlobalVars.BLOCK_SIZE, x, y) && !mapDB[indexX][indexY+1]) {
            return false;
        }
        //south-east
        if (in((indexX+1) * GlobalVars.BLOCK_SIZE, (indexY+1) * GlobalVars.BLOCK_SIZE, x, y) && !mapDB[indexX+1][indexY+1]) {
            return false;
        }

        return true;
    }

    public boolean in(int x1, int y1, int x2, int y2) {
        //System.out.println(x1+" "+y1+" "+ x2+" "+y2);
        //north-west
        if (x2 > x1 && x2 < x1 + GlobalVars.BLOCK_SIZE && y2 > y1 && y2 < y1 + GlobalVars.BLOCK_SIZE) {
            return true;
        }

        //north-east
        if (x2 + GlobalVars.PLAYER_SIZE > x1 && x2 + GlobalVars.PLAYER_SIZE < x1 + GlobalVars.BLOCK_SIZE && y2 >y1 && y2 < y1 + GlobalVars.BLOCK_SIZE) {
            return true;
        }

        //south-west
        if (x2 > x1 && x2 < x1 + GlobalVars.BLOCK_SIZE && y2 + GlobalVars.PLAYER_SIZE > y1 && y2 + GlobalVars.PLAYER_SIZE < y1 + GlobalVars.BLOCK_SIZE) {
            return true;
        }

        //south-east
        if (x2 + GlobalVars.PLAYER_SIZE > x1 && x2 + GlobalVars.PLAYER_SIZE < x1 + GlobalVars.BLOCK_SIZE && y2 + GlobalVars.PLAYER_SIZE > y1 && y2 + GlobalVars.PLAYER_SIZE < y1 + GlobalVars.BLOCK_SIZE) {
            return true;
        }
//System.out.println("map return false");
        return false;
    }

}
