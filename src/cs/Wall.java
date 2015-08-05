package cs;
import java.awt.*;
/**
 * 障碍物的类
 * @author Super pan
 * @time2014-12-15下午11:48:06
 */
public class Wall implements gameConfig{
	/**x,y为障碍物的中心坐标*/
	public int x,y;
	
	public Wall(int x1,int y1){
		x=x1;y=y1;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x-elesize*3/4-5,y-elesize*3/4-5,elesize/2+10,elesize/2+10);     //这里为什么乘上什么3/4、什么1/2完全是从调试的效果试出来的
   }
	
	public Rectangle getRect1(){                                                      //真正的自身范围
		return new Rectangle(x-elesize/2,y-elesize/2,elesize,elesize);
	}
}
