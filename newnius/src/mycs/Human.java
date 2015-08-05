/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import static java.lang.Math.sqrt;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Newnius
 */
public class Human extends Player {

    public Human(String tmpName, int tmpTeam, int tmpX, int tmpY, Connect tmpConnect) {
        super(tmpName, tmpTeam, tmpX, tmpY, tmpConnect);
        hitpoint = 5;
        speed = 5;
        connect = tmpConnect;
        test();
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        fire(x, y);
        //System.out.println(x+" "+y);
        //connect.moveto(this,x,y);
    }

    @Override
    public void attacked(Bullet bullet) {
        hitpoint -= bullet.getSpeed();
        //System.out.println(hitpoint);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int ver = 0;
        int hor = 0;
        if (key == KeyEvent.VK_W) {
            ver--;
        } else if (key == KeyEvent.VK_A) {
            hor--;
        } else if (key == KeyEvent.VK_S) {
            ver++;
        } else if (key == KeyEvent.VK_D) {
            hor++;
        } else {
            return;
        }
        double len = sqrt(hor * hor + ver * ver);
        int x = (int) (speed * hor / len);
        int y = (int) (speed * ver / len);
        //System.out.println(X+x+" "+(Y+y));
        if (connect.moveto(this, X + x, Y + y)) {
            X += x;
            Y += y;
        }

    }

    public void test() {
        int destX = 14;
        int destY = 4;
        boolean sight[][] = connect.sight(X, Y);
        //boolean cross[]=new boolean[100];
        Queue<Point> cross = new LinkedList<>();
        Point vis[][] = new Point[20][10];
        cross.add(new Point(X / GlobalVars.BLOCK_SIZE, Y / GlobalVars.BLOCK_SIZE));
        //Point startPoint=new Point(X/GlobalVars.BLOCK_SIZE, Y/GlobalVars.BLOCK_SIZE);
        System.out.println(new Point(X / GlobalVars.BLOCK_SIZE, Y / GlobalVars.BLOCK_SIZE));
        int x;
        int y;
        Point point;
        while ((point = cross.poll()) != null) {
            x = point.x;
            y = point.y;
            if (x == destX && y == destY) {
                break;
            }
            vis[x][y]=new Point(x,y);
            //System.out.println("--> (" + x + " " + y + ")");
            //north
            if (y > 0 && sight[x][y - 1] && vis[x][y - 1] == null) {
                cross.offer(new Point(x, y - 1));
                vis[x][y - 1] = new Point(x, y);
            }

            //west
            if (x > 0 && sight[x - 1][y] && vis[x - 1][y] == null) {
                cross.offer(new Point(x - 1, y));
                vis[x - 1][y] = new Point(x, y);
            }

            //south
            if (y < 10 - 1 && sight[x][y + 1] && vis[x][y + 1] == null) {
                cross.offer(new Point(x, y + 1));
                vis[x][y + 1] = new Point(x, y);
            }

            //east
            if (x < 20 - 1 && sight[x + 1][y] && vis[x + 1][y] == null) {
                cross.offer(new Point(x + 1, y));
                vis[x + 1][y] = new Point(x, y);
            }

        }
        if (vis[destX][destY] != null) {
            while (vis[destX][destY] != null) {
                System.out.println("--> (" + destX + " " + destY + ")");
                if(destX==vis[destX][destY].x&&destY == vis[destX][destY].y)break;
                destX = vis[destX][destY].x;
                destY = vis[destX][destY].y;
            }
        }
    }

}
