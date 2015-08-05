/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycs;

import Bullet.BulletManager;
import config.GlobalVars;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

/**
 *
 * @author Newnius choose number of AIs choose the size of map
 */
public class MyCS extends JFrame {

    /**
     * @param args the command line arguments
     */
    Map map = null;
    PlayerPanel playerPanel = null;
    BulletPanel bulletPanel = null;
    BulletManager bulletManager;

    public static void main(String[] args) {
        JFrame Window = new MyCS();
        Window.setVisible(true);
    }

    public MyCS() {
        this.setLayout(null);
        int sizeX = 20;
        int sizeY = 10;
        setTitle("hello world, this is our CS");
        setBounds(0, 0, sizeX * GlobalVars.BLOCK_SIZE, sizeY * GlobalVars.BLOCK_SIZE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //first of all, show a pane to let user choose the size of map, the AIs

        //you have to edit the following codes to make sure that j is would not be covered by map
        //and change the layout to make map fill the window
        //take care: it should be handled when the size of window is changed
        map = new Map(sizeX, sizeY);
        playerPanel = new PlayerPanel(sizeX, sizeY);
        bulletPanel = new BulletPanel(sizeX, sizeY);
        bulletManager = new BulletManager();

        Connect connect = new Connect(map, playerPanel, bulletPanel, bulletManager,sizeX * GlobalVars.BLOCK_SIZE,sizeY * GlobalVars.BLOCK_SIZE);

        String[] names = {"newnius", "vivian", "superPan", "player1", "player2", "player3", "player4"};
        List<Player> players = new ArrayList<>();
        int namesCnt = (int) (random() * names.length);

        int locationX;
        int locationY;
//
        do {
            locationX = (int) (random() * sizeX * GlobalVars.BLOCK_SIZE);
            locationY = (int) (random() * sizeY * GlobalVars.BLOCK_SIZE);
        } while (!map.canMoveTo(null, locationX, locationY));
        players.add(new Human(names[namesCnt++ % names.length], (int) (random() * 4), locationX, locationY, connect));
        //
        for (int i = 0; i <0; i++) {
            do {
                locationX = (int) (random() * sizeX * GlobalVars.BLOCK_SIZE);
                locationY = (int) (random() * sizeY * GlobalVars.BLOCK_SIZE);
            } while (!map.canMoveTo(null, locationX, locationY));

            players.add(new AI(names[(namesCnt++) % names.length], (int) (random() * 4), locationX, locationY, connect));
        }

        playerPanel.init(players);
        bulletManager.init(connect);
        new Thread(bulletManager).start();
        add(bulletPanel);
        add(playerPanel);
        add(map);

        new Thread(bulletManager).start();
        //we have to add listener here as jpanel can not get focus
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (playerPanel != null) {
                    playerPanel.keyPressed(e);
                }
            }
        }
        );
    }

}
