/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycs;

import Bullet.Bullet;
import Bullet.BulletManager;
import config.GlobalVars;
import static java.lang.Math.sqrt;
import java.util.List;

/**
 * connect map, frontpanel, bulletpanel, Playsers
 *
 * @author Newnius
 */
public class Connect {

    protected PlayerPanel playerPanel;
    protected Map map;
    protected BulletPanel bulletPanel;
    BulletManager bulletManager;
    int mapWidth;
    int mapHeight;

    public Connect(Map tmpMap, PlayerPanel tmpFrontPanel, BulletPanel tmpBulletPanel, BulletManager tmpBulletManager, int width, int height) {
        map = tmpMap;
        playerPanel = tmpFrontPanel;
        bulletPanel = tmpBulletPanel;
        bulletManager = tmpBulletManager;
        mapWidth = width;
        mapHeight = height;
    }

    //return areas that tmpName can see now
    public boolean[][] sight(int x, int y) {
        return map.sight(x, y);
    }

    public boolean moveto(Player player, int tmpX, int tmpY) {
        //System.out.println("move to "+tmpX+" "+tmpY);
        if (map.canMoveTo(player, tmpX, tmpY) && playerPanel.canMoveTo(player, tmpX, tmpY)) {
            return playerPanel.moveto(player, tmpX, tmpY);
        } else {
            return false;
        }
    }

    //return players that tmpName can see now
    public List<Player> players(String tmpName) {
        return playerPanel.players(tmpName);
    }

    public boolean bulletMoveTo(Bullet bullet, int tmpX, int tmpY) {
        if (bullet == null) {
            return false;
        }
        return bulletPanel.bulletMoveTo(bullet, tmpX, tmpY);
    }

    //return bullets that tmpName can see now
    public List<Bullet> bullets(String tmpName) {
        if (tmpName == null) {
            return null;
        }
        return bulletPanel.bullets(tmpName);
    }

    public boolean fire(Bullet bullet) {
        if (bullet == null) {
            return false;
        }
        bulletManager.createBullet(bullet);
        return bulletPanel.fire(bullet);
    }

    public boolean canBulletFly(Bullet bullet) {
        //judge weather players is hit
        if (!playerPanel.canBulletFly(bullet)) {
            return false;
        }
        //judge weather bullet hit the boundary
        return map.canBulletFly(bullet);
    }

    public boolean destroyBullet(List<Bullet> delBullet) {

        if (delBullet == null || delBullet.size() == 0) {
            return true;
        }
        bulletManager.destroyBullet(delBullet);
        //System.out.println("destroyBullet in Connect.java");
        return bulletPanel.destroyBullet(delBullet);

    }

    public boolean killed(Player playser) {
        playerPanel.killed(playser);
        return true;
    }

    public boolean canMoveTo(Player player, int x, int y) {
        //judge weather players is hit
        if (!playerPanel.canMoveTo(player, x, y)) {
            return false;
        }
        //judge weather bullet hit the boundary
        return map.canMoveTo(player, x, y);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    boolean canBulletReach(int destX, int destY, Bullet bullet) {
        while ((destX - bullet.getLocationX())*(destX - bullet.getLocationX())+ (destY - bullet.getLocationY())*(destY - bullet.getLocationY())>GlobalVars.BULLET_SPEED*GlobalVars.BULLET_SPEED) {
            if (!canBulletFly(bullet)) {
                return false;
            } else {
                double len = sqrt(bullet.getDirectionX() * bullet.getDirectionX() + bullet.getDirectionY() * bullet.getDirectionY());
                bullet.setLocationX(bullet.getLocationX()+(int) (GlobalVars.BULLET_SPEED * bullet.getDirectionX() / len));
                bullet.setLocationY(bullet.getLocationY()+(int) (GlobalVars.BULLET_SPEED * bullet.getDirectionY() / len));
            }
        }
        return true;
    }

}
