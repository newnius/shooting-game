package cs;

import java.awt.Color;
import java.awt.Graphics;

public class BloodBar {
	private Player player;
	
	public BloodBar(Player player) {
		super();
		this.player=player;
	}
	
	public void draw(Graphics g ,int x, int y){
		int width=gameConfig.elesize/4*3;
		g.setColor(Color.BLACK);
		g.drawRect(x+8 , y-gameConfig.elesize/4, width, 5);
		if(player instanceof NPC){
			g.setColor(Color.blue);                                                	       
		}else{
			if(player.live>3)         g.setColor(Color.green);                                                	// 3/5黄血了，1/5红血了
			else if(player.live>1)       g.setColor(Color.green);
			else g.setColor(Color.RED);
		}
		g.fillRect(x+9 , y-gameConfig.elesize/4, width/5*player.live , 5);
	}

}
