/*
 * panel of bullets
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Newnius
 */
public class BulletPanel extends JPanel {

    List<Bullet> bullets;
    java.util.Map<Bullet, JLabel> bulletsLabel;

    public BulletPanel(int tmpSizeX, int tmpSizeY) {
        this.setLayout(null);
        bullets = new ArrayList<>();
        bulletsLabel = new java.util.HashMap<>();
        setSize(tmpSizeX * GlobalVars.BLOCK_SIZE, tmpSizeY * GlobalVars.BLOCK_SIZE);
        setOpaque(false);
    }

    public boolean bulletMoveTo(Bullet bullet, int tmpX, int tmpY) {
        try {
            bulletsLabel.get(bullet).setLocation(tmpX, tmpY);
            //System.out.println(""+ bullet +" "+tmpX+" "+tmpY);
            //this.updateUI();
        } catch (Exception e) {
        }
        return true;
    }

    //return bullets that tmpName can see now
    public List<Bullet> bullets(String tmpName) {
        return bullets;
    }

    public boolean fire(Bullet tmpBullet) {
        //System.out.println(tmpBullet.getOwner() + " fired a bullet to (" + tmpBullet.getDirectionX() + "," + tmpBullet.getDirectionY() + ") at " + tmpBullet.getLocationX() + "," + tmpBullet.getLocationY() + ")");
        synchronized (bullets) {
            bullets.add(tmpBullet);
        }
        synchronized (bulletsLabel) {
            JLabel localJLabel = new JLabel();
            bulletsLabel.put(tmpBullet, localJLabel);
            localJLabel.setSize(GlobalVars.BULLET_SIZE, GlobalVars.BULLET_SIZE);
            add(localJLabel);
            localJLabel.setLocation(tmpBullet.getLocationX(), tmpBullet.getLocationY());
            localJLabel.setIcon(new ImageIcon("imgs/bullet.jpg"));
            localJLabel.setVisible(true);
            //this.updateUI();
        }
        return true;
    }

    public boolean destroyBullet(List<Bullet> delBullet) {
        try {
            synchronized (bullets) {
                bullets.removeAll(delBullet);
            }
            synchronized (bulletsLabel) {
                for (Bullet bullet : delBullet) {
                    bulletsLabel.get(bullet).setVisible(false);
                    bulletsLabel.get(bullet).setIcon(null);
                    remove(bulletsLabel.get(bullet));
                    //System.out.println("destroy a bullet");
                    bulletsLabel.remove(bullet);
                }
            }

        } catch (Exception e) {
            System.out.println("destroy bullet wrong");
        }
        return true;
    }

}
