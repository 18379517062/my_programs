import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class music {
	private  Media media ;//= new Media(getClass().getResource("music/��ϷBGM.mp3").toExternalForm());	
	private  MediaPlayer mediaplayer;// = new MediaPlayer(gamemedia);
	private  double volume;

	
	public music(MediaPlayer mediaplayer) {
		this.mediaplayer = mediaplayer;
	}
	//��������
	public  void pauseMusic() {
		mediaplayer.pause();
	}
	//��ͣ����
	public  void playMusic() {
		mediaplayer.play();
	}
	//����ֹͣ
	public  void stopMusic() {
		mediaplayer.stop();
	}
	//��������
	public void setVolume(double volume) {
		this.volume = volume;
	}
}
	

	

