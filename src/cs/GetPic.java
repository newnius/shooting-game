package cs;
import javax.swing.ImageIcon;
/**
 * 返回图片序号对应的素材图片
 * @author Super pan
 * @time 2014-11-29下午4:15:59  当天完工
 */

public class GetPic implements gameConfig{
	/**
	 * 返回num对应的素材图片
	 * @param num   图片序号
	 * @return ImageIcon     相应图片文件
	 */
	public static ImageIcon getPic(int num){
			if(num==11){
				return icon1;
			}else if(num==12){
				return icon2;
			}else if(num==13){
				return icon3;
			}else if(num==14){
				return icon4;
			}else if(num==21){
				return icon5;
			}else if(num==22){
				return icon6;
			}else if(num==23){
				return icon7;
			}else if(num==24){
				return icon8;
			}else{
				return icon0;
			}
	  }
}
