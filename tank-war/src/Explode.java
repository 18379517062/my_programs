
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//±¨’®¿‡
public class Explode extends Fighter {
	
	Circle explodeBody;
	double[] r = {2,5,9,13,4,1};
	int step = 0;
	
	public Explode(double x, double y) {
		circleX = x;
		circleY = y;

		explodeBody = new Circle(circleX,circleY,1,Color.ORANGE);
		this.getChildren().add(explodeBody);

	}
	
	public void move() {
		
		if(step == 6) {
			step = 0;
			this.live = false;
			this.setVisible(false);
		}
		
		explodeBody.setRadius(r[step]);
		step ++;
	}
	
}
