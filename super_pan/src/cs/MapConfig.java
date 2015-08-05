package cs;
import javax.swing.*;

/**
 * 地图的配置接口，主要是定义并初始化一些变量，
 * 使用此接口的类可以直接使用这些变量
 * @author Super pan
 * @time 2014/11/29    当天完工
 */
public interface MapConfig {
	    /**素材大小 */
		public int eleWidth=50,
				        eleHeight=50;
		
		/** 地图大小*/
		public int mapWidth=3000,
				        mapHeight=3000;
		
		/**地图大小与素材大小之比*/
		public int m=mapWidth/eleWidth,
				        n=mapHeight/eleHeight;
		
		/**地图保存路径*/
		String path="map\\map.map";
		
		/**要用到的图片素材*/
		ImageIcon icon0=new ImageIcon("pic\\10.png"),
		                  icon1=new ImageIcon("pic\\11.png"),
		                  icon2=new ImageIcon("pic\\12.png"),
		                  icon3=new ImageIcon("pic\\13.png"),
		                  icon4=new ImageIcon("pic\\14.png"),
		                  icon5=new ImageIcon("pic\\21.png"),
		                  icon6=new ImageIcon("pic\\22.png"),
		                  icon7=new ImageIcon("pic\\23.png"),
		                  icon8=new ImageIcon("pic\\24.png");
		
		/**图片数组*/
		ImageIcon[] icons={icon1,icon2,icon3,icon4,icon5,icon6,icon7,icon8,icon0 };
}
