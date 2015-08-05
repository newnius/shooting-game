/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;
import java.awt.Point;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;
import static java.lang.Thread.sleep;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Newnius
 */
public class AI extends Player implements Runnable {

    State state = State.WANDER;
    Stack<Point> ways = new Stack<>();

    public AI(String tmpName, int tmpTeam, int tmpX, int tmpY, Connect tmpConnect) {
        super(tmpName, tmpTeam, tmpX, tmpY, tmpConnect);
        hitpoint = 100;
        speed = 5;
        connect = tmpConnect;
    }

    @Override
    public void run() {
        boolean haveMoved;
        boolean haveFired;
        List<Player> players;
        List<Bullet> bullets;

        try {

            while (hitpoint > 0) {
                haveMoved = false;//have moved this second
                haveFired = false;//have shot this second

                /**
                 * get necessary information. eg. get players you can see now.
                 * it is an ArrayList<Player>
                 * get bullets you can see now. it is an ArrayList<Bullet>
                 * get map you can see now. it is a two-dimensional boolean
                 * array.true means you can walk pass it, false no.
                 *
                 */
                //boolean[][] sight = connect.sight(X, Y);
                players = connect.players(name);
                bullets = connect.bullets(name);

                /**
                 * judge whether a bullet is flying to me.
                 *
                 *
                 */
                synchronized (bullets) {
                    for (Bullet bullet : bullets) {
                        if (canBulletReachMe(bullet)) {
                            state = State.DEFENCE;
                            int ver = -bullet.getLocationX() * speed;
                            int hor = bullet.getLocationY() * speed;
                            if (moveto(hor + Y, ver + X)) {
                                haveMoved = true;
                            } else if (moveto(-hor + X, -ver + Y)) {
                                haveMoved = true;
                            }
                            break;
                        }
                    }
                }

                if (state == State.WANDER) {
                    //System.out.println(name+" is wandering");
                    if (!ways.empty()) {
                        Point pt = ways.peek();
                        //System.out.println(pt.getX()+","+pt.getY());
                        if (moveto((int) pt.getX(), (int) pt.getY())) {
                            haveMoved = true;
                            if (pt.getX() == X && pt.getY() == Y) {
                                ways.pop();
                            }
                        } else {
                            ways.pop();
                        }
                    } else {
                        int localX = (int) (random() * 300 - 150) + X;
                        int localY = (int) (random() * 200 - 100) + Y;
                        ways.push(new Point(localX, localY));
                        //System.out.println(localX+","+localY);
                    }

                    if (random() < 0.2) {
                        fire((int) (random() * connect.getMapWidth()), (int) (random() * connect.getMapHeight()));
                    }
                }

                synchronized (players) {
                    for (Player player : players) {
                        if(player==this)continue;
                        if (player.getTeam() != this.team) {
                            state = State.ATTACK;
                            System.out.println("enemy found at (" + player.getX() + "," + player.getY() + ")");
                            //if bullet can reach, fire. no, move
                            if (canBulletReach(player.getX(), player.getY(), new Bullet(null,player.getX()-X,player.getY()-Y,X,Y))) {
                                fire(player.getX(), player.getY());
                            }else{
                                
                            }

                        } else {
                            System.out.println("parter found at (" + player.getX() + "," + player.getY() + ")");
                        }
                    }
                }

                if (!haveMoved && !haveFired) {
                }

                sleep(GlobalVars.ONE_SECOND);
            }
        } catch (Exception e) {
            System.out.println("wrong at AI line 122");
            e.printStackTrace();
        }

    }

    private boolean canBulletReachMe(Bullet bullet) {
        return canBulletReach(X,Y,bullet);

        //return false;
    }

    @Override
    public void attacked(Bullet bullet) {
        hitpoint -= bullet.getSpeed();
        if (hitpoint <= 0) {
            connect.killed(this);
        }
    }

    //return weather this point can be reached
    public boolean moveto(int hor, int ver) {

        int x, y;
        ver -= Y;
        hor -= X;
        if (ver == 0 && hor == 0) {
            return true;
        }
        double len = sqrt(hor * hor + ver * ver);
        if (len <= speed) {
            x = hor;
            y = ver;
        } else {
            x = (int) (speed * hor / len);
            y = (int) (speed * ver / len);
        }

        if (X + x < 0 || Y + y < 0) {
            return false;
        }

        if (X + x > 800 || Y + y > 600) {
            return false;
        }
        if (connect.moveto(this, X + x, Y + y)) {
            X += x;
            Y += y;
            return true;
        }
        return false;
    }

    private boolean canBulletReach(int destX, int destY, Bullet tmpBullet) {
        Bullet bullet=tmpBullet.clone(tmpBullet);
        bullet.setOwner(null);
        return connect.canBulletReach(destX, destY, bullet);
    }
}

enum State {

    WANDER, ATTACK, DEFENCE, WITHDRAW
}
