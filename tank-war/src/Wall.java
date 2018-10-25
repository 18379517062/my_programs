import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Wall extends Fighter{
	private double width = 50;
	private double height = 50;
	//实心墙长度的一般
	public static final double good_length = 25;
	//木头墙长度的一般
	public static final double bad_length = 12.5;
	public ArrayList<Image> images = new ArrayList<Image>();
	public ImageView iView = new ImageView();
	
	public Wall(double x, double y, boolean good, TankWar tc) {
		this.circleX = x;
		this.circleY = y;
		this.good = good;
	
		if(good) {
			images.add(new Image("tankPNG//wall0.gif"));//实心墙
			iView.setImage(images.get(0));
			this.getChildren().add(iView);
			iView.setX(circleX-good_length);
			iView.setY(circleY-good_length);
		} else {
			width = 25;
			height = 25;
			images.add(new Image("tankPNG//wall1.gif"));//木头墙
			iView.setImage(images.get(0));
			this.getChildren().add(iView);
			iView.setX(circleX-bad_length);
			iView.setY(circleY-bad_length);
		}
			
		this.tc = tc;		
		tc.warField.getChildren().add(this);
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
}