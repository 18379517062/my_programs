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
	//���ڵ�ļ���
	public AnchorPane warField = new AnchorPane();
	private Scene gameScene;
	private boolean running;
	
	//ʤ��ͼƬ
	private ImageView victory = new ImageView("tankPNG/ʤ��.jpg");
	
	//ʧ��ͼƬ
	private ImageView lost = new ImageView("tankPNG/ս��.jpg");
	
	//���ؼ���ͼƬ
	private ImageView returnStart = new ImageView("tankPNG/����.png");
	
	//�˳�����ͼƬ
	private ImageView leave = new ImageView("tankPNG/�˳�.png");
	

	
	//ʤ��������
	public Media winmedia = new Media(getClass().getResource("music/ʤ��.mp3").toExternalForm());	
	public MediaPlayer winmediaplayer = new MediaPlayer(winmedia);

	//ʧ�ܵ�����
	public Media defeatmedia = new Media(getClass().getResource("music/ʧ��.mp3").toExternalForm());	
	public MediaPlayer defeatmediaplayer = new MediaPlayer(defeatmedia);
	
	
	//���캯��
	public TankWar(int friendNumber ,int enemyNumber, TankWarClient twc ,Stage primaryStage) {
		this.FRIENDNUMBER = friendNumber;
		this.ENEMYNUMBER = enemyNumber;
		this.twc = twc;
		this.stage = primaryStage;
		buildWorld();
		this.gameScene = new Scene(warField, 1200, 800);
		thread.setDaemon(true);
	}
	
	//�����߳�
	MyTask myTask = new MyTask();
	Thread thread = new Thread(myTask);
	
	//��������
	private void buildWorld() {
		cleanWorld();
		
		createFriends();
		createEnemy();
		createWalls();
	}
		
	//�������
	private void cleanWorld() {
		
		badTanks.clear();
		goodTanks.clear();
		missiles.clear();
		explodes.clear();
		walls.clear();
		warField.getChildren().clear();
		
	}
	
	//���¿�ʼ��Ϸ
	public void restartGame() {
		buildWorld();
		//û�������
		thread.start();
	}
	
	//�����ѷ�̹��
	private void createFriends() {
		myTank = new Tank(MYBORN_X, MYBORN_Y, true, this);
		goodTanks.add(myTank);
	}
	
	//����ǽ
		private void createWalls() {
			//��һ��ʯǽ
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
				
			//�ڶ���ľͷǽ
				for(int i = 0; i<8;i++) {
					for(int j = 0;j<23;j++) {
						walls.add(new Wall((200+j*25),(288+i*25),false,this));
					}
				}
				
				for(int i =0;i<3;i++)
					for(int j=0;j<5;j++)
						walls.add(new Wall((775+j*25),(350+i*25),false,this));
				
				walls.add(new Wall(500,400,true,this));
				
			//������ʯǽ		
			
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
		
	//����з�̹��
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
				//Q��-���¿�ʼ���˳�
				if(event.getCode() == KeyCode.Q) {
					running = false;
					cleanWorld();
					
					stage.setScene(twc.GUIScene);
				}
				
				//���ESC �����˳���Ϸ
				
				if(event.getCode() == KeyCode.TAB) {
		
						System.exit(0);
				}
				
				//W��-��ͣ
				if(event.getCode() == KeyCode.W) {
					thread.suspend();
				}
				//E��-����
				if(event.getCode() == KeyCode.E) {
					thread.resume();
				}
				//P��-����
				if(event.getCode() == KeyCode.P) {
					gamemediaplayer2.pauseMusic();
				}
				//O��-��������
				if(event.getCode() == KeyCode.O) {
					gamemediaplayer2.playMusic();
				}
				
				myTank.keyPressed(event);  
			}
			
		});
		
		//����̹��
		gameScene.setOnKeyReleased(e-> {
				myTank.keyReleased(e);			
		});
		
		
		return gameScene;
	}
	
	//�ж���Ϸ����,ˢ�½�����̲߳���
	private class MyTask extends Task {
		private int countDown = 0;
		@Override
		protected Object call() throws Exception {
			// TODO Auto-generated method stub
			//��Ϸû�н���
			while(myTank.isLive() && running && badTanks.size()!=0) {
				//�߳�
				Platform.runLater(new Runnable() {
					
	                 @Override 
	                 public void run() {
	                	 showFighters();
	                	 //	Q���˳�
	                	if(!running) {
	            				//��Ϸ����ֹͣ���ţ�����ʧ������
	                			//gameplayer.stopMusic();
	            				//defeatmediaplayer.play();		
	            				//	gamemediaplayer.dispose();
	                			cleanWorld();	
	            		} 
	                	//ʤ��
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
	            		//ʧ��
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
	
	//��������������
	private void showFighters() {
		//������
		warField.getChildren().clear();
		
		//����ս���ı���ͼƬ
		warField.getChildren().add(new ImageView(new Image("tankPNG/ս��ҳ��.png")));

		
		//ǽ��״̬�ж�
		for(int i = 0; i< walls.size(); i++) {
			Wall wall = walls.get(i);
			if(wall.isLive()) {
				warField.getChildren().add(wall);
			}else {
				walls.remove(i--);
			}
		}
		
		//�з�̹��״̬�ж�
		for(int i = 0; i< badTanks.size(); i++) {
			Tank tank = badTanks.get(i);
			//�з�̹���������Ƴ�
			if(tank.isLive()) {
				tank.move();
				warField.getChildren().add(tank);
			} else {
				
				
				badTanks.remove(i--);
			}
		}
		//�ӵ�״̬�ж�
		for(int i = 0; i< missiles.size(); i++) {
			Missile missile = missiles.get(i);
			//�ӵ��������Ƴ�
			if(missile.isLive()) {
				missile.move();
				warField.getChildren().add(missile);
			}
			else {
				missiles.remove(i--);
			}
			
		}
		//��ը״̬�ж�
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
		//�ҵ�̹��״̬�ж�
		if(myTank.isLive()) {
			myTank.move();
			warField.getChildren().add(myTank);
		} else {
			return;
		}
	}
	
}
