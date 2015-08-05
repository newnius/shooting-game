package cs;
import java.awt.*;
import java.awt.event.*;

/**
 * 玩家的类
 * @author Super pan
 * @time 2014-11-29下午4:44:41
 */

public class Player extends Thread implements gameConfig{
	/**玩家相对map的x,y坐标*/
	public int x=375,y=375;
	/**玩家移动的偏移量! 这个十分必要！不然出现严重Bug!人物移动就会非常卡顿，原因自己领会*/
	public int tx=500,ty=500;
    public final int speed=10;
    public int dir=0;                      //0-8分别表示 0停、1向下、2向左、3向右、4向上、5向左下、6向右下、7向左上、8向右上
    public int dir1=1;                    //上一次移动的方向
    public boolean bD,bL,bR,bU;          //分别代表 下、左、右、上
    public int D,L,R,U,LD,RD,LU,RU;
    public int interval=missile_interval;       //子弹射击时间间隔
    public int live=5;
    public boolean good=true;
    public boolean stop=false;
    public boolean found=false;
    private BloodBar bloodBar=new BloodBar(this);
    private boolean hit=false;
    public boolean cry=true;

    
    public void run(){
    	while(!stop){
    		if(live==0){
    			explode();
    			stop=true;
    		}
    		move(speed);
    		interval--;
        	try{
        		Thread.sleep(20);
        	}catch(Exception e){}
    	}
    }
    
	public void move(int speed){
		int x1=x, y1=y;             //保留上一次的x 、y ,下面同理
		int x2=tx, y2=ty;
		switch(dir){
		case 1:                                   //下
			if(D++>=12) D=0;
			y+=speed;
		    ty+=speed;
			break;
		case 2:                                   //左
			if(L++>=12) L=0;
			x-=speed;
			tx-=speed;
			break;
		case 3:                                  //右
			if(R++>=12) R=0;
			x+=speed;         
			tx+=speed;         
			break;
		case 4:                                  //上
			if(U++>=12) U=0;
			y-=speed;
			ty-=speed;
			break;
		case 5:                                  //左下
			if(LD++>=12) LD=0;
		    x-=(int)(speed/1.4);                       //斜着走，速度修正
			y+=(int)(speed/1.4);
			tx-=(int)(speed/1.4);                      
		    ty+=(int)(speed/1.4);
			break;
		case 6:                                  //右下
			if(RD++>=12) RD=0;
			x+=(int)(speed/1.4);
			y+=(int)(speed/1.4);
			tx+=(int)(speed/1.4);
			ty+=(int)(speed/1.4);
			break;
		case 7:                                 //左上
			if(LU++>=12) LU=0;
			x-=(int)(speed/1.4);		
			y-=(int)(speed/1.4);
			tx-=(int)(speed/1.4);		
			ty-=(int)(speed/1.4);
			break;
		case 8:                                //右上
			if(RU++>=12) RU=0;
			x+=(int)(speed/1.4);
			y-=(int)(speed/1.4);
			tx+=(int)(speed/1.4);
			ty-=(int)(speed/1.4);
			break; 
		case 0:                                //停止
			break;
	   }
		if(hit()){                    //碰撞处理，回到上一步
			x=x1; y=y1;
			tx=x2;ty=y2;
			if(!this.equals(gameFrame.player)){
				turn();
			}
		}
		
		
		if(dir!=0)  dir1=dir;
	}
	
	/**碰撞判断*/
	public boolean hit(){               //泛指一切碰撞，由于NPC继承了Player，而两者处理碰撞不一样，所以这个处理单独写一个函数
		if(hitWalls()||hitNPCs())
			return true;
		return false;
	}
	
	/**碰到障碍物转方向*/
	public void turn(){
		 switch(dir){
		 	case 1:
		 		dir=4;
		 		break;
		 	case 2:
		 		dir=3;
		 	    break;
		 	case 3:
		 		dir=2;
		 		break;
		 	case 4:
		 		dir=4;
		 		break;
		 	case 5:
		 		dir=8;
		 		break;
		 	case 6:
		 		dir=7;
		 		break;
		 	case 7:
		 		dir=6;
		 		break;
		 	case 8:
		 		dir=5;
		 		break;
		 }
	 }
	
	/**判断运动时是否撞到地表素材 */
	public boolean hitWalls(){
		for(int i=0;i<gameFrame.wall.size();i++){
			if(this.getRect().intersects(gameFrame.wall.get(i).getRect()))
				return true;
		}
	    return false;
	}
	
	/**判断运动时是否撞到机器人*/
	public boolean hitNPCs(){
		for(int i=0;i<gameFrame.npc.size();i++){
			if(this.getRect().intersects(gameFrame.npc.get(i).getRect()))
				return true;
		}
	    return false;
	}
	
	/**判断运动时是否撞到子弹*/
	/*public boolean hitMissile(){
		for(int i=0;i<gameFrame.missile.size();i++)
			if(this.getRect().intersects(gameFrame.missile.get(i).getRect()))
				return true;
	    return false;
	}*/
	
	public void draw(Graphics g,int mx,int my,int nx,int ny){
		if(dir==0){
			if(dir1==1)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,0,100,100,null);
			if(dir1==2)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100,100,100*2,null);
			if(dir1==3)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*2,100,100*3,null);
			if(dir1==4)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*3,100,100*4,null);
			if(dir1==5)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*4,100,100*5,null);
			if(dir1==6)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*5,100,100*6,null);
			if(dir1==7)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*6,100,100*7,null);
			if(dir1==8)   g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*7,100,100*8,null);
		}else if(dir==1){
			if(D<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,0,100,100,null);
			else if(D<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,0,100*2,100,null);
			else if(D<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,0,100*3,100,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,0,100*4,100,null);
		}else if(dir==2){
			if(L<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100,100,100*2,null);
			else if(L<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100,100*2,100*2,null);
			else if(L<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100,100*3,100*2,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100,100*4,100*2,null);
		}else if(dir==3){
			if(R<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*2,100,100*3,null);
			else if(R<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*2,100*2,100*3,null);
			else if(R<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*2,100*3,100*3,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*2,100*4,100*3,null);
		}else if(dir==4){
			if(U<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*3,100,100*4,null);
			else if(U<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*3,100*2,100*4,null);
			else if(U<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*3,100*3,100*4,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*3,100*4,100*4,null);
		}else if(dir==5){
			if(LD<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*4,100,100*5,null);
			else if(LD<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*4,100*2,100*5,null);
			else if(LD<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*4,100*3,100*5,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*4,100*4,100*5,null);
		}else if(dir==6){
			if(RD<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*5,100,100*6,null);
			else if(RD<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*5,100*2,100*6,null);
			else if(RD<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*5,100*3,100*6,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*5,100*4,100*6,null);
		}else if(dir==7){
			if(LU<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*6,100,100*7,null);
			else if(LU<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*6,100*2,100*7,null);
			else if(LU<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*6,100*3,100*7,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*6,100*4,100*7,null);
		}else if(dir==8){
			if(RU<3)          g.drawImage(walk.getImage(),mx,my,nx,ny,0,100*7,100,100*8,null);
			else if(RU<6) g.drawImage(walk.getImage(),mx,my,nx,ny,100,100*7,100*2,100*8,null);
			else if(RU<9) g.drawImage(walk.getImage(),mx,my,nx,ny,100*2,100*7,100*3,100*8,null);
			else                g.drawImage(walk.getImage(),mx,my,nx,ny,100*3,100*7,100*4,100*8,null);
		}
		
		
		if(live>0){
			bloodBar.draw(g ,mx ,my);
		}
		
		if(hit){
			gameFrame.messages.add(new Message("-20", x, y));
			hit=false;
		}
		
	}
	
	public void KeyPressed(KeyEvent e){
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_D)          bR=true;
		if(key==KeyEvent.VK_A)          bL=true;
		if(key==KeyEvent.VK_W)         bU=true;
		if(key==KeyEvent.VK_S)      	  bD=true;
		if(key==KeyEvent.VK_J){
			fire();
			found=true;
		}
		LocateDirection();
	}
	
	public void KeyReleased(KeyEvent e){
		int key=e.getKeyCode();
		
		if(key==KeyEvent.VK_D)          bR=false;
		if(key==KeyEvent.VK_A)          bL=false;
		if(key==KeyEvent.VK_W)         bU=false;
		if(key==KeyEvent.VK_S)      	  bD=false;
		LocateDirection();
	}
	
	public void LocateDirection(){
		if(bL && !bU && !bD && !bR) dir=2;
		else if(!bL && bU && !bD && !bR) dir=4;
		else if(!bL && !bU && bD && !bR) dir=1;
		else if(!bL && !bU && !bD && bR) dir=3;
		else if(bL && bU && !bD && !bR) dir=7;
		else if(bL && !bU && bD && !bR) dir=5;
		else if(!bL && bU && !bD && bR) dir=8;
		else if(!bL && !bU && bD && bR) dir=6;
		else if(!bL && !bU && !bD && !bR) dir=0;
	}
	
	public void mouseClicked(MouseEvent e){
		//changeDir(e.getX(),e.getY());
		int mx=x+(e.getX()-px);
		int my=y+(e.getY()-py);
	    if(interval<0){
	    	gameFrame.missile.add(new Missile(x,y,mx,my,true,20,this));
	    	interval=missile_interval;
	    	found=true;
	    }
	}
	
	public void fire(){
		 if(interval<0){
		    	gameFrame.missile.add(new Missile(x,y,dir1,true,player_missile,this));
		    	interval=missile_interval;
		    }
	}
	
	public void explode(){
		gameFrame.explode.add(new Explode(x,y, this));
	}
	
	public void changeDir(int ex,int ey){
		if(px==ex&&py<ey)          dir=1;
		else if(py==ey&&px>ex)   dir=2;
		else if(py==ey&&px<ex)   dir=3;
		else if(px==ex&&py>ey)   dir=4;
		else if(px>ex&&py<ey)     dir=5;
		else if(px<ex&&py<ey)     dir=6;
		else if(px>ex&&py>ey)     dir=7;
		else if(px<ex&&py>ey)     dir=8;
	}
	
	 public Rectangle getRect(){                             //为实现更好的碰撞效果的自身的范围
			return new Rectangle(x-elesize*3/4-5,y-elesize*3/4-5,elesize/2+10,elesize/2+10);     //这里为什么乘上什么3/4、什么1/2完全是从调试的效果试出来的
	}
	 
	public Rectangle getRect1(){                            //自身真正的范围
		return new Rectangle(x-elesize/2,y-elesize/2,elesize,elesize);
	}
	
	public Rectangle getRect2(){                             //视野范围
		int rx=(getJ()-7)*50;
		int ry=(getI()-6)*50;
		return new Rectangle(rx,ry,19*50,17*50);          //15、13
	}

	/**返回玩家在map数组的行，比如返回map[i][j]的 i */
	public int getI(){
		return (y-elesize/2)/elesize;
	}
	
	/**返回玩家在map数组的列，比如返回map[i][j]的 j */
	public int getJ(){
		return (x-elesize/2)/elesize;
	}
	
	public Location getLocation(){
		return new Location(getI(), getJ());
	}
	
	public void hitByMissile(){
		if(live>0){
			live--;
			hit=true;
		}
	}
}
