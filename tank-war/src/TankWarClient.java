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
		//���ò��ܴ��ڸı��С
		primaryStage.setResizable(false);
		primaryStage.setTitle("̹��ͻ��");
		primaryStage.setScene(GUIScene);
		primaryStage.show();
	}
	
}
