package cs;
import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 * 子弹的类
 * @author Super pan
 * @time 2014-12-2上午0:06:30     
 */

public class Missile extends Thread implements gameConfig{
	public int x,y;                                //子弹的坐标
	public int tx,ty;                             //鼠标点击处的等效坐标
	private int dir; 
	private int speed=30;
	private float x_speed;
	private float y_speed;
	private Player shooter;
	private Player player=gameFrame.player;
	public boolean good=false;              //好或坏子弹
	public boolean live=true;                  //是否存活
	public boolean stop=false;               //线程是否关闭
	
	public Missile(int x1,int y1,int d,boolean b,int s,Player p){
		x=x1; y=y1;
		dir=d;
		speed=s;
		x_speed=y_speed=speed;
		good=b;	
		shooter=p;
		this.start();
		new Thread(){
			public void run(){
				voice();
			}
		}.start();
	}
	
	public Missile(int x1,int y1,int x2,int y2,boolean b,int s,Player p){
		x=x1;y=y1;
		tx=x2;ty=y2;
		good=b;
		speed=s;
		shooter=p;
		calcSpeed();
		calcDirection();
		this.start();
		new Thread(){
			public void run(){
				voice();
			}
		}.start();
		
	}
	
	public void run(){
		while(!stop){
			move();
			if(hit()){
				live=false;
				stop=true;
			}
			try{
				Thread.sleep(20);
			}catch(Exception e){}
		}
	}
	
	public void move(){
		switch(dir){
		    case 1:                                 //向下
			    y+=speed;break;
		    case 2:                                  //向左
			    x-=speed;break;
		    case 3:                                  //向右
		    	x+=speed;break;
		    case 4:                                  //向上
	    		y-=speed;break;
		    case 5:                                  //向左下
	    		x-=x_speed;
		    	y+=y_speed;break;
		    case 6:                                   //向右下
			    x+=x_speed;
			    y+=y_speed;break;
		    case 7:                                   //向左上
			    x-=x_speed;
			    y-=y_speed;break;
		    case 8:                                   //向右上
		    	x+=x_speed;
			    y-=y_speed;break;
		}
		if(Math.abs(x-shooter.x)>400||Math.abs(y-shooter.y)>400){
			live=false;
		    stop=true;
		}
	}
	
	public void draw(Graphics g){
		if(shooter.good)
			g.drawImage(missileY.getImage(),px+(x-player.x),py+(y-player.y),null);
		else
			g.drawImage(missileB.getImage(),px+(x-player.x),py+(y-player.y),null);
	}
	
	public void voice(){
		try {
			InputStream in = new FileInputStream("mis\\枪声.wav");
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start (as);
			Thread.sleep(2000);
			AudioPlayer.player.stop(as);
		} catch (Exception e1) {}
	}
	
	/**计算x_speed和y_speed*/
	public void calcSpeed(){
		float dx=Math.abs(x-tx);
		float dy=Math.abs(y-ty);
		float dz=(float)Math.sqrt(dx*dx+dy*dy);
		if(dz!=0){
			x_speed=speed*dx/dz;
			y_speed=speed*dy/dz;
		}
	}
	
	/**计算运动方向*/
	public void calcDirection(){
		if(y<ty&&x==tx)              dir=1;             
		else if(x>tx&&y==ty)       dir=2;       
		else if(x<tx&&y==ty)       dir=3;               
		else if(y>ty&&x==tx)       dir=4;                   
		else if(x>tx&&y<ty)         dir=5;                   
		else if(x<tx&&y<ty)         dir=6;                
		else if(x>tx&&y>ty)         dir=7;                    
		else if(x<tx&&y>ty)         dir=8;                
	}
	
	public boolean hit(){
		if(good!=gameFrame.player.good&&this.getRect().intersects(gameFrame.player.getRect1())){
			gameFrame.player.hitByMissile();
			return true;
		}
		for(int i=0;i<gameFrame.npc.size();i++)
			if((good!=gameFrame.npc.get(i).good&&this.getRect().intersects(gameFrame.npc.get(i).getRect1()))){
				gameFrame.npc.get(i).hitByMissile();
				return true;
		}
		for(int i=0;i<gameFrame.wall.size();i++)
			if((this.getRect().intersects(gameFrame.wall.get(i).getRect1())))
				return true;
		
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x-2,y-2,4,4);
	}
}
