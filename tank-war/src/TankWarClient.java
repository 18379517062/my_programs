import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TankWarClient extends Application{
	public Scene gameScene;
	public Scene GUIScene;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		StartGUI fg = new StartGUI(this, primaryStage);
		
		GUIScene = fg.getScene();
		primaryStage.setHeight(800);
		primaryStage.setWidth(1200);
		//设置不能窗口改变大小
		primaryStage.setResizable(false);
		primaryStage.setTitle("坦克突击");
		primaryStage.setScene(GUIScene);
		primaryStage.show();
	}
	
}
