/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bullet;

import config.GlobalVars;
import java.awt.Point;
import mycs.Player;

/**
 *
 * @author Newnius
 */
public class Bullet implements Cloneable{
    private int speed;
    private int directionX;
    private int directionY;
    private int locationX;
    private int locationY;
    private  Player owner;
    
    public Bullet(Player player, int tmpDirectionX, int tmpDirectionY,int tmpLocationX,int tmpLocationY) {
        speed = GlobalVars.BULLET_SPEED;
        directionX = tmpDirectionX;
        directionY = tmpDirectionY;
        locationX = tmpLocationX;
        locationY=tmpLocationY;
        owner=player;
    }

    
    public Bullet clone(Bullet bullet){
        return new Bullet(bullet.getOwner(),bullet.getDirectionX(),bullet.getDirectionY(),bullet.getLocationX(),bullet.getLocationY());
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public Player getOwner() {
        return owner;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
    
    public void setOwner(Player player) {
        this.owner=player;
    }
    
    public Point fly(){
        
        
        
        return null;
    }
    
    
    
}
