package cs;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;



import sun.audio.*;

import java.util.List;
/**
 * 游戏的主窗体
 * @author Super pan
 * @time 2014-11-29下午4:35:28
 */

@SuppressWarnings("serial")
public class gameFrame extends JFrame implements gameConfig{
	private JPanel mainPanel;
	private boolean gameover=false;
	public gameWatcher watcher=new gameWatcher();
	public Image offScreenImage=null;	                                     //双缓冲的虚拟图片
	public Image offScreenImage1=null;	     
	public static Player player=new Player();
	public static List<Wall> wall=new ArrayList<Wall>();
	public static List<NPC> npc=new ArrayList<NPC>();
	public static List<Missile> missile=new ArrayList<Missile>();
	public static List<Explode> explode=new ArrayList<Explode>();
	public static List<Message> messages=new ArrayList<Message>();
	
	public void init(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                    //绘制主窗体
		setBounds(100, 100,frameX, frameY);
		setLayout(null);
		setVisible(true);
		setTitle("CS");
		setResizable(false);
		setLocationRelativeTo(null);
		
		mainPanel=new JPanel(){                                                                   //重写主面板的paint方法
			public void paint(Graphics g){
				super.paint(g);
				
				for(int i=player.getI()-7;i<=player.getI()+7;i++){                  //绘制脚下和地表素材    
					for(int j=player.getJ()-8;j<=player.getJ()+8;j++){
						if(i>=0&&j>=0&&i<GetMap.map1.length&&j<GetMap.map1[0].length){
							ImageIcon icon = GetPic.getPic(GetMap.map1[i][j]);
							g.drawImage(icon.getImage(), (px-elesize/2)+(j-player.getJ())*elesize-player.tx%elesize, (py-elesize/2)+((i-player.getI())*elesize)-player.ty%elesize, elesize, elesize, null);
							ImageIcon icon2 = GetPic.getPic(GetMap.map2[i][j]);
							g.drawImage(icon2.getImage(), (px-elesize/2)+(j-player.getJ())*elesize-player.tx%elesize, (py-elesize/2)+((i-player.getI())*elesize)-player.ty%elesize, elesize, elesize, null);
						}
					}
				}
				g.drawImage(shadow.getImage(), 0, 0, 930, 660, null);     //绘制阴影背景
				
				if(player.live!=0)
					player.draw(g,mx,my,nx,ny);                            //绘制玩家
				
				for(int i=0;i<missile.size();i++){            //绘制子弹
					Missile mis=missile.get(i);
					if(!mis.live)  missile.remove(i);
					else mis.draw(g);
				}
				
				for(int i=0;i<explode.size();i++){                     //绘制爆炸
					Explode exp=explode.get(i);
					if(!exp.live)   explode.remove(i);
					else exp.draw(g);
				}
				
				for(int i=0;i<npc.size();i++){                        //绘制机器人
					NPC n=npc.get(i);
					if(n.live==0) npc.remove(i);
					else{
						if(player.getRect2().intersects(n.getRect())){
							int mx=(px-elesize/2)+(n.x-player.x);
							int my=(py-elesize/2)+(n.y-player.y);
							n.draw(g,mx,my);
						}
					}
				}
				
				for(int i=0;i<messages.size();i++){                                //绘制消息
					Message msg=messages.get(i);
					if(msg.live==false) messages.remove(i);
					else{
						msg.draw(g);
					}
				}
				
				if(player.live<=0||npc.size()==0){                                //绘制游戏结束
					watcher.draw(g);
				}
				
			}
		};
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.black);
		mainPanel.setBounds(0,0,panelX, panelY);
		mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR ));
		add(mainPanel);
		
	    addKeyListener(new KeyAdapter(){                         //主窗体添加按键监听器
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_F2){
					Restart();
					
				}else if(e.getKeyCode()==KeyEvent.VK_F4){ 
					 setDefaultLookAndFeelDecorated(true);
					 new MapEditor().init();
					 
				}else if(e.getKeyCode()==KeyEvent.VK_F6){
					try{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					}catch(Exception e1){}
					
					JFileChooser fc = new JFileChooser();
					fc.setDialogType(JFileChooser.OPEN_DIALOG);
					fc.setCurrentDirectory(new File("map\\"));//初始化目录
					fc.setFileFilter(new FileNameExtensionFilter(".map", "map"));//添加文本文件过滤器,你可以添加更多
					fc.showOpenDialog(gameFrame.this);
					String filepath=fc.getSelectedFile().toString();
					
					Restart();
					GetMap.ReadMapFile(filepath);
					
					
				}else{
					player.KeyPressed(e);
				}
					
			}
			public void keyReleased(KeyEvent e){
				player.KeyReleased(e);
			}
		});
		
		mainPanel.addMouseListener(new MouseAdapter(){                //主面板添加鼠标监听器
			public void mouseClicked(MouseEvent e){
				player.mouseClicked(e);
			}
		});
		
		new Thread(new paintThread()).start();                                     //主面板添加线程实现面板刷新
		player.start();                                                                              //添加线程实现监听到按键事件后玩家移动
		
		NPC n1=new NPC(1375,1375,false);                                        //添加机器人
//		NPC n2=new NPC(1675,1875,false);
		n1.start();
//		n2.start();
		npc.add(n1);
//		npc.add(n2);
		
		while(true){
			try {
				InputStream in = new FileInputStream("mis\\背景.wav");
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start (as);
				Thread.sleep(92000);
				AudioPlayer.player.stop(as);
			} catch (Exception e1) {}
		}
		
	}
	


	
	/**游戏的观察者*/
	public class gameWatcher{
		
		public void draw(Graphics g){
			g.setColor(Color.yellow);
			g.setFont(new Font("微软雅黑",Font.BOLD,26));
			if(player.live<=0){
				g.drawString("Y O U    L O S E ！！！ ",px-110, py-80);
				g.setColor(Color.pink);
				g.drawString("G A M E    O V E R ！！！", px-135, py);
				
				for(NPC n : npc){
					n.stop=true;
				}
			}else{
				g.drawString("Y O U    W I N ！！！", px-100, py-80);
				
				for(NPC n : npc){
			    	n.cry=false;
			    	n.live=0;
			    }
			    for(Missile m : missile){
			    	m.live=false;
			    }
			}
			g.setFont(new Font("微软雅黑",Font.BOLD,16));
			g.setColor(Color.yellow);
			g.drawString("press F2 to restart game", px-90, py+100);
			
		    
		}
	}
	
	
	/**刷新主面板的线程*/
	public class paintThread implements Runnable{
		public void run(){
			while(!gameover){
				mainPanel.repaint();
				try{
					Thread.sleep(20);
				}catch(Exception e){}
			}
		}
	}
	
	/**游戏重新开始*/
	public void Restart(){
		player=new Player();
		player.start();
	    
		for(NPC n : npc){
			n.cry=false;
	    	n.live=0;
	    }
	    for(Missile m : missile){
	    	m.live=false;
	    }
		
	    NPC n1=new NPC(1375,1375,false);                                        //添加机器人
//		NPC n2=new NPC(1675,1875,false);
		n1.start();
//		n2.start();
		npc.add(n1);
//		npc.add(n2);
		gameFrame.player.found=false;
	    
	}
}
