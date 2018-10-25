import java.util.ArrayList;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TankWar {
	private static final int MYBORN_X = 500;
	private static final int MYBORN_Y = 725;
	private static final int BORN_X = 100;
	private static final int BORN_Y = 50;
	private static final int WALLNUMBER = 20;
	private static int ENEMYNUMBER;
	private static int FRIENDNUMBER;
	
	public ArrayList<Tank> badTanks = new ArrayList<Tank>();
	public ArrayList<Tank> goodTanks = new ArrayList<Tank>();
	public ArrayList<Missile> missiles = new ArrayList<Missile>();
	public ArrayList<Explode> explodes = new ArrayList<Explode>();
	public ArrayList<Wall> walls = new ArrayList<Wall>();
	
	private TankWarClient twc;
	private Stage stage;
	private Tank myTank;
	//面板节点的集合
	public AnchorPane warField = new AnchorPane();
	private Scene gameScene;
	private boolean running;
	
	//胜利图片
	private ImageView victory = new ImageView("tankPNG/胜利.jpg");
	
	//失败图片
	private ImageView lost = new ImageView("tankPNG/战败.jpg");
	
	//返回键的图片
	private ImageView returnStart = new ImageView("tankPNG/返回.png");
	
	//退出键的图片
	private ImageView leave = new ImageView("tankPNG/退出.png");
	

	
	//胜利的音乐
	public Media winmedia = new Media(getClass().getResource("music/胜利.mp3").toExternalForm());	
	public MediaPlayer winmediaplayer = new MediaPlayer(winmedia);

	//失败的音乐
	public Media defeatmedia = new Media(getClass().getResource("music/失败.mp3").toExternalForm());	
	public MediaPlayer defeatmediaplayer = new MediaPlayer(defeatmedia);
	
	
	//构造函数
	public TankWar(int friendNumber ,int enemyNumber, TankWarClient twc ,Stage primaryStage) {
		this.FRIENDNUMBER = friendNumber;
		this.ENEMYNUMBER = enemyNumber;
		this.twc = twc;
		this.stage = primaryStage;
		buildWorld();
		this.gameScene = new Scene(warField, 1200, 800);
		thread.setDaemon(true);
	}
	
	//建立线程
	MyTask myTask = new MyTask();
	Thread thread = new Thread(myTask);
	
	//建立界面
	private void buildWorld() {
		cleanWorld();
		
		createFriends();
		createEnemy();
		createWalls();
	}
		
	//清理界面
	private void cleanWorld() {
		
		badTanks.clear();
		goodTanks.clear();
		missiles.clear();
		explodes.clear();
		walls.clear();
		warField.getChildren().clear();
		
	}
	
	//重新开始游戏
	public void restartGame() {
		buildWorld();
		//没多大作用
		thread.start();
	}
	
	//创建友方坦克
	private void createFriends() {
		myTank = new Tank(MYBORN_X, MYBORN_Y, true, this);
		goodTanks.add(myTank);
	}
	
	//创造墙
		private void createWalls() {
			//第一层石墙
				walls.add(new Wall(200, 200, true, this));
				walls.add(new Wall(200, 250, true, this));		
				walls.add(new Wall(250, 200, true, this));
				walls.add(new Wall(250, 250, true, this));
				walls.add(new Wall(300, 200, true, this));
				walls.add(new Wall(300, 250, true, this));
				
				walls.add(new Wall(650, 200, true, this));
				walls.add(new Wall(650, 250, true, this));			
				walls.add(new Wall(700, 200, true, this));
				walls.add(new Wall(700, 250, true, this));
				walls.add(new Wall(750, 200, true, this));
				walls.add(new Wall(750, 250, true, this));
				
			//第二层木头墙
				for(int i = 0; i<8;i++) {
					for(int j = 0;j<23;j++) {
						walls.add(new Wall((200+j*25),(288+i*25),false,this));
					}
				}
				
				for(int i =0;i<3;i++)
					for(int j=0;j<5;j++)
						walls.add(new Wall((775+j*25),(350+i*25),false,this));
				
				walls.add(new Wall(500,400,true,this));
				
			//第三层石墙		
			
				walls.add(new Wall(200, 500, true, this));
				walls.add(new Wall(200, 550, true, this));		
				walls.add(new Wall(250, 500, true, this));
				walls.add(new Wall(250, 550, true, this));
				walls.add(new Wall(300, 500, true, this));
				walls.add(new Wall(300, 550, true, this));
				
				walls.add(new Wall(650, 500, true, this));
				walls.add(new Wall(650, 550, true, this));			
				walls.add(new Wall(700, 500, true, this));
				walls.add(new Wall(700, 550, true, this));
				walls.add(new Wall(750, 500, true, this));
				walls.add(new Wall(750, 550, true, this));
											
		}
		
	//创造敌方坦克
	private void createEnemy() {
		
		for(int i = 0; i < ENEMYNUMBER ; i++) {
			badTanks.add(new Tank((i+1)*200, BORN_Y, false, this));
		}
	}
	
	public Scene getScene(music gamemediaplayer2) {
			
		running = true;
		thread.start();
				
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {	
				//Q键-重新开始，退出
				if(event.getCode() == KeyCode.Q) {
					running = false;
					cleanWorld();
					
					stage.setScene(twc.GUIScene);
				}
				
				//点击ESC 键，退出游戏
				
				if(event.getCode() == KeyCode.TAB) {
		
						System.exit(0);
				}
				
				//W键-暂停
				if(event.getCode() == KeyCode.W) {
					thread.suspend();
				}
				//E键-继续
				if(event.getCode() == KeyCode.E) {
					thread.resume();
				}
				//P键-静音
				if(event.getCode() == KeyCode.P) {
					gamemediaplayer2.pauseMusic();
				}
				//O键-继续播放
				if(event.getCode() == KeyCode.O) {
					gamemediaplayer2.playMusic();
				}
				
				myTank.keyPressed(event);  
			}
			
		});
		
		//控制坦克
		gameScene.setOnKeyReleased(e-> {
				myTank.keyReleased(e);			
		});
		
		
		return gameScene;
	}
	
	//判断游戏进程,刷新界面的线程部分
	private class MyTask extends Task {
		private int countDown = 0;
		@Override
		protected Object call() throws Exception {
			// TODO Auto-generated method stub
			//游戏没有结束
			while(myTank.isLive() && running && badTanks.size()!=0) {
				//线程
				Platform.runLater(new Runnable() {
					
	                 @Override 
	                 public void run() {
	                	 showFighters();
	                	 //	Q键退出
	                	if(!running) {
	            				//游戏音乐停止播放，播放失败音乐
	                			//gameplayer.stopMusic();
	            				//defeatmediaplayer.play();		
	            				//	gamemediaplayer.dispose();
	                			cleanWorld();	
	            		} 
	                	//胜利
	            		if(myTank.live && badTanks.size() == 0) {
	                	winmediaplayer.play();
	                	warField.getChildren().clear();
	               
	                	warField.setLeftAnchor(returnStart, 450.0);
	                	warField.setRightAnchor(returnStart, 650.0);
	                	warField.setTopAnchor(returnStart, 600.0);
	                	warField.setBottomAnchor(returnStart, 150.0);
	                	
	                	warField.setLeftAnchor(leave, 650.0);
	                	warField.setRightAnchor(leave, 450.0);
	                	warField.setTopAnchor(leave, 600.0);
	                	warField.setBottomAnchor(leave, 150.0);
	                	
	                	warField.getChildren().addAll(victory,returnStart,leave);
	                	
	                	returnStart.setOnMouseClicked(e->{
	                		stage.setScene(twc.GUIScene);
	                	});
	                	
	                	leave.setOnMouseClicked(e->{
	                		System.exit(0);
	                	});
	                }
	            		//失败
	            		if(!myTank.live && badTanks.size()>=0){  
	    				defeatmediaplayer.play();
	    				warField.getChildren().clear();
	    			
	    				warField.setLeftAnchor(returnStart, 450.0);
	                	warField.setRightAnchor(returnStart, 650.0);
	                	warField.setTopAnchor(returnStart, 600.0);
	                	warField.setBottomAnchor(returnStart, 150.0);
	                	
	                	warField.setLeftAnchor(leave, 650.0);
	                	warField.setRightAnchor(leave, 450.0);
	                	warField.setTopAnchor(leave, 600.0);
	                	warField.setBottomAnchor(leave, 150.0);
	                	
	                	warField.getChildren().addAll(lost,returnStart,leave);
	                	
	                	returnStart.setOnMouseClicked(e->{
	                		stage.setScene(twc.GUIScene);
	                	});
	                	
	                	leave.setOnMouseClicked(e->{
	                		System.exit(0);
	                	});
	    			//	gamemediaplayer.stop();
	    			//	gamemediaplayer.dispose();
	    				
	    			//	cleanWorld();
	    				
	    			}
	                 }
	             });
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}
	}
	
	//画出场景的物体
	private void showFighters() {
		//先清屏
		warField.getChildren().clear();
		
		//加入战斗的背景图片
		warField.getChildren().add(new ImageView(new Image("tankPNG/战斗页面.png")));

		
		//墙的状态判定
		for(int i = 0; i< walls.size(); i++) {
			Wall wall = walls.get(i);
			if(wall.isLive()) {
				warField.getChildren().add(wall);
			}else {
				walls.remove(i--);
			}
		}
		
		//敌方坦克状态判定
		for(int i = 0; i< badTanks.size(); i++) {
			Tank tank = badTanks.get(i);
			//敌方坦克死亡则移除
			if(tank.isLive()) {
				tank.move();
				warField.getChildren().add(tank);
			} else {
				
				
				badTanks.remove(i--);
			}
		}
		//子弹状态判定
		for(int i = 0; i< missiles.size(); i++) {
			Missile missile = missiles.get(i);
			//子弹死亡则移除
			if(missile.isLive()) {
				missile.move();
				warField.getChildren().add(missile);
			}
			else {
				missiles.remove(i--);
			}
			
		}
		//爆炸状态判定
		for(int i = 0; i< explodes.size(); i++) {
			Explode explode = explodes.get(i);
			if(explode.isLive()) {
				explode.move();
				warField.getChildren().add(explode);
			}
			else {
				explodes.remove(i--);
			}
			
		}
		//我的坦克状态判定
		if(myTank.isLive()) {
			myTank.move();
			warField.getChildren().add(myTank);
		} else {
			return;
		}
	}
	
}
