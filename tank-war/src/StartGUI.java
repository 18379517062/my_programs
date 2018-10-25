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
	
	//��ʼ����ı���ͼƬ
	 ImageView background = new ImageView(new Image("tankPNG/��ʼ����.jpg"));
	
	//��ʼ����ı�������	
	public  Media startmedia = new Media(getClass().getResource("music/��ʼ��Ч.mp3").toExternalForm());
	public  MediaPlayer startmediaplayer = new MediaPlayer(startmedia);
	

	//��Ϸ����ı�������
	public  Media gamemedia = new Media(getClass().getResource("music/��ϷBGM.mp3").toExternalForm());	
	public  MediaPlayer gamemplayer = new MediaPlayer(gamemedia);

	
	


	//��ʼ��Ϸ��ť
	public Button startbutton= new Button();
	
	//�˳���Ϸ��ť
	public Button endbutton= new Button();
	
	 //��ʼ���
	private static AnchorPane startpane = new AnchorPane();

	//��ʼ�ĳ���
	private Scene guiScene = new Scene( returnPane(startpane),1200,800);
	 
	//����ʼ�������
	public AnchorPane returnPane(AnchorPane pane) {

		startbutton.setText("��ʼ��Ϸ");
		endbutton.setText("�˳���Ϸ");
		startbutton.setStyle("-fx-background-color:Yellow;");
		endbutton.setStyle("-fx-background-color:RED;");
		//��Ϸ����
		Text Help = new Text(920,500,"��Ϸ����:\n��ʼ��ť/Space��-��ʼ��Ϸ\nSpace��-���\n"
    			+ "S��-��������\nQ��-���س�ʼ����\nW��-��ͣ��Ϸ\nE��-������Ϸ\nP��-����\nO��-������Ч\nTAB-���˳�");
    	Help.setFont(Font.font("Courier",FontWeight.BOLD,FontPosture.ITALIC,20));
    	Help.setFill(Color.RED);
    	
    	//��ʼ��Ϸ��ťλ�� 
		pane.setLeftAnchor(startbutton,450.0);
		pane.setRightAnchor(startbutton,650.0);
		pane.setTopAnchor(startbutton,600.0);
		pane.setBottomAnchor(startbutton,130.0);
		//�˳���Ϸ��ťλ��
		pane.setLeftAnchor(endbutton,650.0);
		pane.setRightAnchor(endbutton,450.0);
		pane.setTopAnchor(endbutton,600.0);
		pane.setBottomAnchor(endbutton,130.0);
	
		
		pane.getChildren().addAll(background,Help,startbutton,endbutton);
		return pane;
	}
	
	//��ʼ����	
	public StartGUI(TankWarClient twc,Stage stage) {
		this.stage = stage;
		music gamemediaplayer = new music(gamemplayer);
		//���ÿ�ʼ�������ֵ�����
		startmediaplayer.setVolume(0.5);
	    startmediaplayer.seek(Duration.ZERO);	
		//������Ϸ�������ֵ������������ŵĿ�ʼʱ��
		gamemediaplayer.setVolume(0.5);
	    gamemplayer.seek(Duration.ZERO);
	    //���ſ�ʼ��������
	    startmediaplayer.play();
		//�����ť����ʼ��Ϸq
		startbutton.setOnMousePressed(e -> {
			        //��ʼ��Ϸ�󣬹رտ�ʼ������
					startmediaplayer.stop();
					
					//������Ϸ�ı�������
					
					gamemediaplayer.playMusic();
					TankWar tWar = new TankWar(1, 5, twc, stage);
					Scene gameScene = tWar.getScene(gamemediaplayer);
					stage.setScene(gameScene);	
					
		});
		
		//����˳���ť���˳���Ϸ
		endbutton.setOnMousePressed(e -> {
			System.exit(0);
			
		});
		//���ո������ʼ��Ϸ
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
		
		//���ESC �����˳���Ϸ
		endbutton.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.TAB) 
				System.exit(0);
		});
	}
	
	public Scene getScene() {
		return guiScene;
	}
	
}	
