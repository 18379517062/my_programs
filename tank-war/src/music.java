import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class music {
	private  Media media ;//= new Media(getClass().getResource("music/”Œœ∑BGM.mp3").toExternalForm());	
	private  MediaPlayer mediaplayer;// = new MediaPlayer(gamemedia);
	private  double volume;

	
	public music(MediaPlayer mediaplayer) {
		this.mediaplayer = mediaplayer;
	}
	//≤•∑≈“Ù¿÷
	public  void pauseMusic() {
		mediaplayer.pause();
	}
	//‘›Õ£“Ù¿÷
	public  void playMusic() {
		mediaplayer.play();
	}
	//“Ù¿÷Õ£÷π
	public  void stopMusic() {
		mediaplayer.stop();
	}
	//…Ë÷√“Ù¡ø
	public void setVolume(double volume) {
		this.volume = volume;
	}
}
	

	

