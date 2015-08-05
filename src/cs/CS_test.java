package cs;
/**
 * 测试主类
 * @author Super pan
 * @time 2014-11-29  下午5:14:44
 */

public class CS_test implements gameConfig{
	public static void main(String[] args){
		GetMap.ReadMapFile(path);
		new gameFrame().init();
	}
}
