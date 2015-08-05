package cs;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 *  爆炸的类
 * @author Super pan
 * @time2014-12-22上午12:00:20
 */

public class Explode implements gameConfig{
	private int x,y;
	private int i=0;
	public Player player=gameFrame.player;
	public boolean live=true;
	
	public Explode(int x1,int y1, Player p){
		x=x1;
		y=y1;
		if(p.cry){
			new Thread(){
				public void run(){
					voice();
				}
			}.start();
		}
	}
	
	public void draw(Graphics g){
		if(i>=pics.length){
			i=0;
			live=false;
		}
		
		g.drawImage(pics[i].getImage(),px+(x-player.x)-pics[i].getIconWidth()/2,py+(y-player.y)-pics[i].getIconHeight()/2,null);
		i++;
		try{
			Thread.sleep(20);
		}catch(Exception e){}
	}
	
	public void voice(){
		try {
			InputStream in = new FileInputStream("mis\\中枪.wav");
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start (as);
			Thread.sleep(2000);
			AudioPlayer.player.stop(as);
		} catch (Exception e1) {}
	}
}
