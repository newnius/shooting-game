package cs;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import cs.MapConfig;
/**
 * 地图编辑器
 * @author Super pan
 * @time 2014/11/29    当天完工
 */

@SuppressWarnings("serial")
public class MapEditor extends JFrame implements MapConfig{
	//存放数字，0表示没有图片，其他数字分别代表相应图片，map1是脚下素材，map2是地面素材，map数组存到文件
	private int[][] map1=new int[m][n];
	private int[][] map2=new int[m][n];
	//存放图片，null表示没图片，icons数组不存到文件，只是在编辑地图时调用
	private ImageIcon[][] icons1=new ImageIcon[m][n];
	private ImageIcon[][] icons2=new ImageIcon[m][n];
	
	private JComboBox<Object> box1=null;
	private JComboBox<Object> box2=new JComboBox<Object>(icons);
	private JPanel panel=null;
	
	public volatile boolean b=false;

	//public static void main(String[] args) {
		 //setDefaultLookAndFeelDecorated(true);
		 //new MapEditor().init();
	//}

	public void init() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLayout(null);
		setVisible(true);
		setTitle("地图编辑器");
		setLocationRelativeTo(null);
		
		//初始化map数组和icons数组，生成地板和地图边缘的围墙，减少自定义地图时麻烦
		int num1=Integer.parseInt(icon1.toString().substring(4, 6));      //比如icons1名为"pic\\11.png"，取出数字11
		int num2=Integer.parseInt(icon5.toString().substring(4, 6));
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++){
				map1[i][j]=num1;
				icons1[i][j]=icon1;
				if((i==4||i==55)&&(j>=4&&j<=55)||(j==4||j==55)&&(i>=4&&i<=55)){
					map2[i][j]=num2;
					icons2[i][j]=icon5;
				}
			}
		
		panel = new JPanel(){                           //重写JPanel的paint方法
			public void paint(Graphics g){
				super.paint(g);
				for(int i=0;i<m;i++)
					for(int j=0;j<n;j++){
						if(icons1[i][j]!=null)
							g.drawImage(icons1[i][j].getImage(),j*eleWidth,i*eleHeight,eleWidth,eleHeight,null);
						if(icons2[i][j]!=null)
							g.drawImage(icons2[i][j].getImage(),j*eleWidth,i*eleHeight,eleWidth,eleHeight,null);
					}
			}
		};
		panel.setPreferredSize(new Dimension(mapWidth,mapHeight));
		JScrollPane jsp=new JScrollPane(panel);
		jsp.setBounds(10, 10, 725, 541);          
		add(jsp);
		
		JButton button = new JButton("保存");
		button.setFont(new Font("微软雅黑", Font.BOLD, 16));
		button.setBounds(773, 507, 93, 42);
		button.addActionListener(new ActionListener() {                    //监听单击按钮事件，实现保存map文件
			public void actionPerformed(ActionEvent e) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					JFileChooser fc = new JFileChooser();
					fc.setDialogType(JFileChooser.SAVE_DIALOG);
					fc.setCurrentDirectory(new File("map\\"));//初始化目录
					fc.setFileFilter(new FileNameExtensionFilter(".map", "map"));//添加文本文件过滤器,你可以添加更多
					fc.showSaveDialog(MapEditor.this);
					String filepath=fc.getSelectedFile().toString()+".map";
					
					DataOutputStream dos=new DataOutputStream(new FileOutputStream(filepath));
					dos.writeInt(m);
					dos.writeInt(n);
					for(int i=0;i<m;i++)
						for(int j=0;j<n;j++){
							dos.writeInt(map1[i][j]);
							dos.writeInt(map2[i][j]);
						}
					dos.flush();
					dos.close();
				} catch (Exception e1) {}
			}
		});
		add(button);
		
		Integer[] temp={1,2};
		box1= new JComboBox<Object>(temp);
		box1.setFont(new Font("微软雅黑", Font.BOLD, 16));
		box1.setBounds(773, 23, 90, 32);
		add(box1);
		
		box2.setBounds(773, 118, 90, 32);
		add(box2);
		
		panel.addMouseListener(new MouseAdapter(){                       //给panel添加鼠标监听事件
			public void mouseClicked(MouseEvent e){
				int i=e.getY()/eleHeight;
				int j=e.getX()/eleWidth;
				
				ImageIcon icon=(ImageIcon)box2.getSelectedItem();
				int choice=(Integer)box1.getSelectedItem();
				int num=Integer.parseInt(icon.toString().substring(4,6));
				
				if(choice==1){
					map1[i][j]=num;
					icons1[i][j]=icon;
					if(num==10){                     //10是一个透明图片，当选中10时相当于撤销原来的错误图片
						map1[i][j]=0;
						icons1[i][j]=null;
					}
				}else if(choice==2){
					map2[i][j]=num;
					icons2[i][j]=icon;
					if(num==10){
						map2[i][j]=0;
						icons2[i][j]=null;
					}
				}
				panel.repaint();
			}
		});
	}
}
