package cs;


import java.awt.Color;
import java.awt.Graphics;


public class Message implements gameConfig{
	private int x;
	private int y;
	private String message="message";                      	//消息内容
	private int step=0;
	private Color color=Color.yellow;                               	//设置颜色
	public boolean live=true;
	private Player player=gameFrame.player;
	
	public Message(String message, int x, int y){
		this.message=message;
		this.x=x;
		this.y=y;
	}
	
	public void draw(Graphics g){
		if(step>30) {
			live=false;
			return;
		}
		Color c=g.getColor();
		g.setColor(color);
		g.drawString(message, px+(x-player.x),py+(y-player.y)-50-step);
		g.setColor(c);
		step++;
		
	}
}
