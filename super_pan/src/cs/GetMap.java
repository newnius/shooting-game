package cs;
import java.io.*;
/**
 * 读取地图编辑器生成的地图文件，生成游戏地图的二维数组
 * @author Super pan
 * @time 2014-11-29下午4:23:18  当天完工  
 */

public class GetMap implements gameConfig{
	public static int[][] map1;
	public static int[][] map2;
	public static int m;
	public static int n;
	/**
	 *  读取地图文件，生成地图的二维数组
	 * @param path   地图文件的路径
	 */
	public static void ReadMapFile(String path){
		try {
			gameFrame.wall.removeAll(gameFrame.wall);
			DataInputStream dis=new DataInputStream(new FileInputStream(path));
			m=dis.readInt();
			n=dis.readInt();
			
			map1=new int[m][n];
			map2=new int[m][n];
			
			for(int i=0;i<m;i++)
				for(int j=0;j<n;j++){
					map1[i][j]=dis.readInt();
					map2[i][j]=dis.readInt();
					if(map2[i][j]!=0)
						gameFrame.wall.add(new Wall(j*elesize+elesize/2,i*elesize+elesize/2));
				}
			
			dis.close();
		} catch (IOException e) {}
	}
}
