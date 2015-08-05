/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bullet;

import config.GlobalVars;
import static java.lang.Math.sqrt;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import mycs.Connect;

/**
 *
 * @author Newnius
 */
public class BulletManager implements Runnable {

    private List<Bullet> bullets;
    private Connect connect;

    public BulletManager() {
        bullets = new ArrayList<>();
    }

    public boolean init(Connect tmpConnect) {
        connect = tmpConnect;
        return true;
    }

    public boolean createBullet(Bullet bullet) {
        synchronized (bullets) {
            bullets.add(bullet);
            return true;
        }
    }

    public boolean destroyBullet(List<Bullet> delBullet) {
        //System.out.println(delBullet.size());
        //System.out.println(bullets.size());
        synchronized (bullets) {
            bullets.removeAll(delBullet);
            
            
        }
        //System.out.println(bullets.size());
        return true;
    }

    @Override
    public void run() {
        List<Bullet> delBullet = new ArrayList<>();
        while (0 < 1) {
            try {
                delBullet.clear();
                sleep(GlobalVars.ONE_SECOND);
                synchronized (bullets) {
                    for (Bullet bullet : bullets) {
                        if (moveBullet(bullet)) {
                        } else {
                            delBullet.add(bullet);
                            //break;
                        }
                    }
                    connect.destroyBullet(delBullet);
                }

            } catch (InterruptedException ex) {
                System.out.println("wrong at line 70 in BulletManaget.java");
            }
        }
    }

    private boolean moveBullet(Bullet bullet) {
        int X = bullet.getLocationX();
        int Y = bullet.getLocationY();
        int DX = bullet.getDirectionX();
        int DY = bullet.getDirectionY();
        int speed = bullet.getSpeed();
        if (speed < 1) {
            return false;
        }
        double len = sqrt(DX * DX + DY * DY);
        X += (int) (speed * DX / len);
        Y += (int) (speed * DY / len);
        if (connect.canBulletFly(bullet)) {
            bullet.setLocationX(X);
            bullet.setLocationY(Y);
            return connect.bulletMoveTo(bullet, X, Y);
        } else {
            return false;
        }

    }

}
