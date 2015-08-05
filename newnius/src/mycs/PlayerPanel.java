/*
 * panel of players
 */
package mycs;

import Bullet.Bullet;
import config.GlobalVars;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Newnius
 */
public class PlayerPanel extends JPanel {

    List<Player> players = null;
    java.util.Map<Player, JLabel> playersLabel = null;
    Human human = null;
    int width;
    int height;

    public PlayerPanel(int tmpSizeX, int tmpSizeY) {
        this.setLayout(null);
        width = tmpSizeX * GlobalVars.BLOCK_SIZE;
        height = tmpSizeY * GlobalVars.BLOCK_SIZE;
        setSize(width, height);
        setOpaque(false);
    }

    public boolean init(List<Player> tmpPlayers) {
        playersLabel = new java.util.HashMap<>();
        players = tmpPlayers;
        human = (Human) players.get(0);

        for (int i = 0; i < players.size(); i++) {
            JLabel localJLabel = new JLabel(players.get(i).name);
            localJLabel.setText("");
            playersLabel.put(players.get(i), localJLabel);

            localJLabel.setSize(GlobalVars.BLOCK_SIZE, GlobalVars.BLOCK_SIZE);
            localJLabel.setLocation(players.get(i).getX(), players.get(i).getY() - 8);//减8为修正，原因未知
            localJLabel.setIcon(new ImageIcon("imgs/boy.gif"));
            //localJLabel.setBackground(Color.red);
            localJLabel.setVisible(true);
            add(localJLabel);
        }

        for (int i = 1; i < players.size(); i++) {
            new Thread((AI) players.get(i)).start();
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (human != null) {
                    human.mouseReleased(e);
                }
            }
        }
        );
        return true;
    }

    public boolean moveto(Player player, int tmpX, int tmpY) {
        //if tmpPlayer is human, move the map,else change the position of it
        //System.out.println(tmpName + " moves to (" + tmpX + "," + tmpY + ")");
        //playersLabel.get(player).setText(""+player.getHitpoint());
        playersLabel.get(player).setLocation(tmpX, tmpY - 8);//减8为修正，原因未知

        return true;
    }

    public void keyPressed(KeyEvent e) {
        if (human != null) {
            human.keyPressed(e);
        }
    }

    //return players that tmpName can see now
    public List<Player> players(String tmpName) {
        return new ArrayList<>(players);
    }

    public boolean canBulletFly(Bullet bullet) {
        int x = bullet.getLocationX() + GlobalVars.BULLET_SIZE / 2;
        int y = bullet.getLocationY() + GlobalVars.BULLET_SIZE / 2;
        synchronized (players) {
            for (Player player : players) {
                int X = player.getX() + GlobalVars.PLAYER_SIZE / 2;
                int Y = player.getY() + GlobalVars.PLAYER_SIZE / 2;
                if (bullet.getOwner() != null && !player.equals(bullet.getOwner()) && Math.abs(x - X) < (GlobalVars.BULLET_SIZE / 2 + GlobalVars.PLAYER_SIZE / 2) && Math.abs(y - Y) < (GlobalVars.BULLET_SIZE / 2 + GlobalVars.PLAYER_SIZE / 2)) {

                    player.attacked(bullet);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean killed(Player player) {
        System.out.println(player.getName() + "'s killed");
        synchronized (players) {
            players.remove(player);
            playersLabel.get(player).setVisible(false);
            remove(playersLabel.get(player));
            if (players.size() == 1) {
                System.out.println(players.get(0).getName() + " win!");
            }
        }
        this.updateUI();

        return true;
    }

    public boolean canMoveTo(Player player, int x, int y) {
        synchronized (players) {
            for (Player tmpPlayer : players) {
                if (tmpPlayer == player) {
                    continue;
                }
                /*
                 int X = tmpPlayer.getX() + GlobalVars.PLAYER_SIZE / 2;
                 int Y = tmpPlayer.getY() + GlobalVars.PLAYER_SIZE / 2;
                 if (Math.abs(x - X) < GlobalVars.PLAYER_SIZE && Math.abs(y - Y) < GlobalVars.PLAYER_SIZE) {
                 return false;
                 }
                 */
                if (in(x, y, tmpPlayer.getX(), tmpPlayer.getY())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean in(int x1, int y1, int x2, int y2) {
        //System.out.println("player "+x1+" "+y1+" "+ x2+" "+y2);
        //north-west
        if (x2 >= x1 && x2 <= x1 + GlobalVars.PLAYER_SIZE && y2 >= y1 && y2 <= y1 + GlobalVars.PLAYER_SIZE) {
            return true;
        }

        //north-east
        if (x2 + GlobalVars.PLAYER_SIZE >= x1 && x2 + GlobalVars.PLAYER_SIZE <= x1 + GlobalVars.PLAYER_SIZE && y2 >= y1 && y2 <= y1 + GlobalVars.PLAYER_SIZE) {
            return true;
        }

        //south-west
        if (x2 >= x1 && x2 <= x1 + GlobalVars.PLAYER_SIZE && y2 + GlobalVars.PLAYER_SIZE >= y1 && y2 + GlobalVars.PLAYER_SIZE <= y1 + GlobalVars.PLAYER_SIZE) {
            return true;
        }

        //south-east
        if (x2 + GlobalVars.PLAYER_SIZE >= x1 && x2 + GlobalVars.PLAYER_SIZE <= x1 + GlobalVars.PLAYER_SIZE && y2 + GlobalVars.PLAYER_SIZE >= y1 && y2 + GlobalVars.PLAYER_SIZE <= y1 + GlobalVars.PLAYER_SIZE) {
            return true;
        }
//System.out.println("player return false");
        return false;
    }

}
