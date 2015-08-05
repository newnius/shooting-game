package cs;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 
 * @author Super pan
 * @time2014-12-14上午10:47:23
 */

public class NPC extends Player{
	public int speed=2;
	public double x_speed;
	public double y_speed;
	public boolean stop=false;
	
	
	public NPC(){}
	
	public NPC(int x1,int y1,boolean b){
		x=x1; y=y1;
		good=b;
	}
	
	public void run(){
		    new Thread(new Runnable(){
				 public void run(){
					 while(!stop){
						 if(live==0){
				    			explode();
				    			stop=true;
				    		}
						 if(check_inrect()&&gameFrame.player.found){
							 boolean hit=new MissileB(x, y, gameFrame.player.x, gameFrame.player.y, NPC.this).hit_wall();
							 if(!hit){
			    				fire();
							 }
							 interval--;
				    	 }
						 try{
			    				Thread.sleep(20);
			    		   }catch(Exception e){}
					 }
				 }
			 }).start();
		    
		    int count=100;
	    	while(!stop){
	    		if(gameFrame.player.found){
	    			automove();    
	    			
	    		}else{
		    			while((count--)==0){
		    		    Random ran=new Random();
			    		dir=ran.nextInt(10);
			    		if(dir>=9) dir=3;                                             //增大向右的概率
			    			count=100;
		    			}
		    			move(speed);
		    			try{
		    				Thread.sleep(20);
		    			}catch(Exception e){}
	    	   	}
		   }
    }
	 
	 public void move(int speed){
		 super.move(speed);
	 }
	 
	 
	 public void automove(){
		 List<Location> paths=new PathFinder().findPath(getLocation(), gameFrame.player.getLocation(), GetMap.map2);
		 int index=paths.size()-1;

		 if(near_player()){
			 round_move();
			 try{
				 Thread.sleep(20);
			 }catch(Exception e){}
			 
			 return ;
		 }
		 
		 if(index<=20&&index>=0){
			 Location loc=null;
			 for(int i=0; index>=0&&i<3; i++,index--){                                                                                                 // ; index>=0&&automove; index--          
					loc = paths.get(index);
					this.setLocationX(loc.getJ());
					this.setLocationY(loc.getI());
			}
		 }else{
				 move(speed);
			}
		
			 
	 }
	 
	 public void setLocationX(int nextX){
			int xPixel = nextX * gameConfig.elesize+ gameConfig.elesize;
			if(xPixel >= x){
				dir=3;
				while(x < xPixel){
					if(R++>=12) R=0;
					x += 3;
					try{
						Thread.sleep(10);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				dir=2;
				while(x > xPixel){
					if(L++>=12) L=0;
					x -= 3;
					try{
						Thread.sleep(10);
					}catch(Exception e){
						e.printStackTrace();
					}
			   }
		  }
	  }
	 
	 
	 public void setLocationY(int nextY){
			int yPixel = nextY * gameConfig.elesize +gameConfig.elesize;
			if(yPixel >= y){
				dir=1;
				while(y < yPixel){
					if(D++>=12) D=0;
					y +=3;
					try{
						Thread.sleep(10);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}else{
				dir=4;
				while(y > yPixel){
					if(U++>=12) U=0;
					y -= 3;
					try{     
						
						Thread.sleep(10);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
	 }

	 public boolean near_player(){
		 return ((x-gameFrame.player.x)*(x-gameFrame.player.x)+(y-gameFrame.player.y)*(y-gameFrame.player.y))<=200*200;
	 }

	 
	 /**计算x_speed和y_speed*/
		public void calcSpeed(){
			Player p=gameFrame.player;
			
			float dx=Math.abs(x-p.x);
			float dy=Math.abs(y-p.y);
			float dz=(float)Math.sqrt(dx*dx+dy*dy);
			if(dz!=0){
				x_speed=speed*dx/dz;
				y_speed=speed*dy/dz;
			}
		}
		
		/**计算运动方向*/
		public void calcDirection(){
			Player p=gameFrame.player;
			
			if(y<p.y&&x==p.x)              dir=1;             
			else if(x>p.x&&y==p.y)       dir=2;       
			else if(x<p.x&&y==p.y)       dir=3;               
			else if(y>p.y&&x==p.x)       dir=4;                   
			else if(x>p.x&&y<p.y)         dir=5;                   
			else if(x<p.x&&y<p.y)         dir=6;                
			else if(x>p.x&&y>p.y)         dir=7;                    
			else if(x<p.x&&y>p.y)         dir=8;                
		}
	 
	 /**绕着敌人走*/
	 public void round_move(){
		 int x1=x, y1=y;             //保留上一次的x 、y ,下面同理
		 switch(dir){
		    case 1:                                 //向下
		    	if(D++>=12) D=0;
			    y+=speed;break;
		    case 2:                                  //向左
		    	if(L++>=12) L=0;
			    x-=speed;break;
		    case 3:                                  //向右
		    	if(R++>=12) R=0;
		    	x+=speed;break;
		    case 4:                                  //向上
		    	if(U++>=12) U=0;
	    		y-=speed;break;
		    case 5:                                  //向左下
		    	if(LD++>=12) LD=0;
	    		x-=x_speed;
		    	y+=y_speed;break;
		    case 6:                                   //向右下
		    	if(RD++>=12) RD=0;
			    x+=x_speed;
			    y+=y_speed;break;
		    case 7:                                   //向左上
		    	if(LU++>=12) LU=0;
			    x-=x_speed;
			    y-=y_speed;break;
		    case 8:                                   //向右上
		    	if(RU++>=12) RU=0;
		    	x+=x_speed;
			    y-=y_speed;break;
		}
		 
		 if(hit()){                    //碰撞处理，回到上一步
				x=x1; y=y1;
				turn();
			}
			if(dir!=0)  dir1=dir;
	 }
	 
	 public void fire(){
		 
		 if(interval<0){
			 gameFrame.missile.add(new Missile(x,y,gameFrame.player.x,gameFrame.player.y,false,npc_missile,this));
			 interval=npc_missile_interval;                                     //重置子弹间隔
		 }
	 }
	 
	 public boolean hit(){
		 if(hitWalls()||hitNPCs()||hitPlayer())
			 return true;
		 return false;
	 }
	 
	 public boolean hitNPCs(){
		 for(int i=0;i<gameFrame.npc.size();i++)
			 if(!this.equals(gameFrame.npc.get(i))&&this.getRect().intersects(gameFrame.npc.get(i).getRect()))
			      return true;
		 return false;
	 }
	 
	 public boolean hitPlayer(){
		 if(this.getRect().intersects(gameFrame.player.getRect()))
			 return true;
		 return false;
	 }
	 
	 public void draw(Graphics g,int mx,int my){
		int nx=mx+elesize;
		int ny=my+elesize;
		super.draw(g,mx,my,nx,ny);
	 }
	 
	 public boolean check_inrect(){
		 if(gameFrame.player.getRect2().contains(new Point(x,y)))
			 return true;
		 return false;
	 }

	 public void setXY(int x1,int y1){
		 x=x1; y=y1;
	 }
	 
}


