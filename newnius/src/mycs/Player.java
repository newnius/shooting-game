/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;

/**
 *
 * @author Newnius
 */
public abstract class Player {

    protected final String name;
    protected int hitpoint;
    protected int speed;
    protected final int team;
    protected Connect connect;
    protected int X;
    protected int Y;
    private long lastFiredTime = System.currentTimeMillis()-GlobalVars.BULLET_WAIT;
    protected int bulletLeft=30;

    public Player(String tmpName, int tmpTeam, int tmpX, int tmpY, Connect tmpConnect) {
        team = tmpTeam;
        name = tmpName;
        X = tmpX;
        Y = tmpY;
        connect = tmpConnect;
        
    }

    public abstract void attacked(Bullet bullet);

    public final boolean fire(int tmpDirectionX, int tmpDirectionY) {
        if (bulletLeft>0 && System.currentTimeMillis() - lastFiredTime > GlobalVars.BULLET_WAIT) {
            connect.fire(new Bullet(this, tmpDirectionX - X, tmpDirectionY - Y, X, Y));
            lastFiredTime = System.currentTimeMillis();
            bulletLeft--;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public int getHitpoint() {
        return hitpoint;
    }

    public int getSpeed() {
        return speed;
    }

    public int getTeam() {
        return team;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

}
