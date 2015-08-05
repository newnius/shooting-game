package cs;
import javax.swing.*;
/**
 * 游戏的配置接口，主要是定义并初始化一些变量，
 * 使用此接口的类可以直接使用这些变量
 * @author Super pan
 * @time 2014/11/29    当天完工
 */

public interface gameConfig {
	   /**游戏主窗体的大小*/
		public int frameX = 950,
				         frameY = 700;
		
		/**游戏面板大小*/
		public int panelX = 930,
				         panelY = 660;
		
		/**游戏素材大小*/
		public int elesize = 50;
		
		/**玩家相对游戏主面板的位置的中心点*/
		public static int px=panelX/2,
				                  py=panelY/2;
		
		/**玩家的图片的相对主面板的左上角和右下角的坐标*/
		public static int mx=px-elesize/2,
				                  my=py-elesize/2,
				                  nx=px+elesize/2,
				                  ny=py+elesize/2;
		
		/**地图保存路径*/
		String path="map\\mymap.map";
		
		/**要用到的地面元素和地表元素的图片素材*/
		ImageIcon icon0=new ImageIcon("pic\\15.png"),
		                  icon1=new ImageIcon("pic\\11.png"),
		                  icon2=new ImageIcon("pic\\12.png"),
		                  icon3=new ImageIcon("pic\\13.png"),
		                  icon4=new ImageIcon("pic\\14.png"),
		                  icon5=new ImageIcon("pic\\21.png"),
		                  icon6=new ImageIcon("pic\\22.png"),
		                  icon7=new ImageIcon("pic\\23.png"),
		                  icon8=new ImageIcon("pic\\24.png");
		
		/**要用到的爆炸的图片*/
		ImageIcon pic0=new ImageIcon("pic\\0.gif"),
							pic1=new ImageIcon("pic\\1.gif"),
							pic2=new ImageIcon("pic\\2.gif"),
							pic3=new ImageIcon("pic\\3.gif"),
							pic4=new ImageIcon("pic\\4.gif"),
							pic5=new ImageIcon("pic\\5.gif"),
							pic6=new ImageIcon("pic\\6.gif"),
							pic7=new ImageIcon("pic\\7.gif"),
							pic8=new ImageIcon("pic\\8.gif"),
							pic9=new ImageIcon("pic\\9.gif"),
				            pic10=new ImageIcon("pic\\10.gif");
		
		/**爆炸图片数组*/
		ImageIcon[] pics={pic0,pic2,pic4,pic6,pic8,pic10,pic10,pic6,pic6,pic5,pic4,pic3,pic3,pic2,pic2,pic1,pic1 };
		
		/**子弹图片*/
		ImageIcon missileY=new ImageIcon("pic\\MissileY.png");
		ImageIcon missileB=new ImageIcon("pic\\MissileB.png");
		
		/**角色行走图*/
		ImageIcon walk=new ImageIcon("pic\\Player.png");
		
		/**镜头阴影图*/
		ImageIcon shadow=new ImageIcon("pic\\Shadow.png");
		
		/**GameOver*/
		ImageIcon over=new ImageIcon("pic\\gameover.png");
		
		/**玩家子弹射击时间间隔*/
		int missile_interval=20;
		
		/**npc子弹射击时间间隔*/
		int npc_missile_interval=30;
		
		/**玩家子弹射击速度*/
		int player_missile=20;
		
		/**npc子弹射击速度*/
		int npc_missile=20;
}
