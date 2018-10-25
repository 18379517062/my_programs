import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class StartGUI {
	private Stage stage;
	
	//开始界面的背景图片
	 ImageView background = new ImageView(new Image("tankPNG/开始界面.jpg"));
	
	//开始界面的背景音乐	
	public  Media startmedia = new Media(getClass().getResource("music/开始音效.mp3").toExternalForm());
	public  MediaPlayer startmediaplayer = new MediaPlayer(startmedia);
	

	//游戏界面的背景音乐
	public  Media gamemedia = new Media(getClass().getResource("music/游戏BGM.mp3").toExternalForm());	
	public  MediaPlayer gamemplayer = new MediaPlayer(gamemedia);

	
	


	//开始游戏按钮
	public Button startbutton= new Button();
	
	//退出游戏按钮
	public Button endbutton= new Button();
	
	 //开始面板
	private static AnchorPane startpane = new AnchorPane();

	//开始的场景
	private Scene guiScene = new Scene( returnPane(startpane),1200,800);
	 
	//给开始面板加组件
	public AnchorPane returnPane(AnchorPane pane) {

		startbutton.setText("开始游戏");
		endbutton.setText("退出游戏");
		startbutton.setStyle("-fx-background-color:Yellow;");
		endbutton.setStyle("-fx-background-color:RED;");
		//游戏帮助
		Text Help = new Text(920,500,"游戏帮助:\n开始按钮/Space键-开始游戏\nSpace键-射击\n"
    			+ "S键-超级火力\nQ键-返回初始界面\nW键-暂停游戏\nE键-继续游戏\nP键-静音\nO键-开启音效\nTAB-键退出");
    	Help.setFont(Font.font("Courier",FontWeight.BOLD,FontPosture.ITALIC,20));
    	Help.setFill(Color.RED);
    	
    	//开始游戏按钮位置 
		pane.setLeftAnchor(startbutton,450.0);
		pane.setRightAnchor(startbutton,650.0);
		pane.setTopAnchor(startbutton,600.0);
		pane.setBottomAnchor(startbutton,130.0);
		//退出游戏按钮位置
		pane.setLeftAnchor(endbutton,650.0);
		pane.setRightAnchor(endbutton,450.0);
		pane.setTopAnchor(endbutton,600.0);
		pane.setBottomAnchor(endbutton,130.0);
	
		
		pane.getChildren().addAll(background,Help,startbutton,endbutton);
		return pane;
	}
	
	//开始界面	
	public StartGUI(TankWarClient twc,Stage stage) {
		this.stage = stage;
		music gamemediaplayer = new music(gamemplayer);
		//设置开始背景音乐的音量
		startmediaplayer.setVolume(0.5);
	    startmediaplayer.seek(Duration.ZERO);	
		//设置游戏背景音乐的音量，及播放的开始时间
		gamemediaplayer.setVolume(0.5);
	    gamemplayer.seek(Duration.ZERO);
	    //播放开始背景音乐
	    startmediaplayer.play();
		//点击按钮，开始游戏q
		startbutton.setOnMousePressed(e -> {
			        //开始游戏后，关闭开始的音乐
					startmediaplayer.stop();
					
					//播放游戏的背景音乐
					
					gamemediaplayer.playMusic();
					TankWar tWar = new TankWar(1, 5, twc, stage);
					Scene gameScene = tWar.getScene(gamemediaplayer);
					stage.setScene(gameScene);	
					
		});
		
		//点击退出按钮，退出游戏
		endbutton.setOnMousePressed(e -> {
			System.exit(0);
			
		});
		//按空格键，开始游戏
		startbutton.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.SPACE) {
				startmediaplayer.stop();
				//gamemediaplayer.play();
				gamemediaplayer.playMusic();
				TankWar tWar = new TankWar(1, 5, twc, stage);
				Scene gameScene = tWar.getScene(gamemediaplayer);
				stage.setScene(gameScene);
			}
		});
		
		//点击ESC 键，退出游戏
		endbutton.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.TAB) 
				System.exit(0);
		});
	}
	
	public Scene getScene() {
		return guiScene;
	}
	
}	
