import java.util.ArrayList;
import java.util.Iterator;
/*
import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.jmx.snmp.tasks.ThreadService;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
*/
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class Missile extends Fighter {
	
	ImageView iView = new ImageView();
	//子弹的出口离坦克的距离
	private double MODIFYDISTANCE = 12;  
	//子弹类，14*14像素大小



	//爆炸效果的音乐
	public  Media explodemedia = new Media(getClass().getResource("music/爆炸音效.mp3").toExternalForm());	
	public  MediaPlayer explodemediaplayer = new MediaPlayer(explodemedia);

	public Missile (double d, double e, Tank.Direction direction, boolean good, TankWar tc){
		circleX = d;
		circleY = e;
		this.XSPEED = 15;
		this.YSPEED = 15;
		this.good = good;
		this.tc = tc;

		Image image;
		if(good) {
			image = new Image("missilePNG//goodMissile_"+direction.toString()+".png");
			
		} else {
			image = new Image("missilePNG//badMissile_"+direction.toString()+".png");
		}
		iView.setImage(image);
			iView.setX(circleX - MODIFYDISTANCE);
			iView.setY(circleY - MODIFYDISTANCE);
			this.direction = direction;
			this.getChildren().add(iView);
		tc.missiles.add(this);
	}
	//击中墙
	public boolean hitWall(Wall wall) {
		if(wall.good == true) {
			if(this.iView.intersects(wall.circleX-12.5, wall.circleY- 12.5, wall.getWidth(), wall.getHeight())) {
				return true;
			}
		}
		else {
			if(this.iView.intersects(wall.circleX-25, wall.circleY- 25, wall.getWidth(), wall.getHeight())) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean hitWalls(ArrayList<Wall> w) {
		for(Wall wall:w) {
			if (hitWall(wall)) {
				//木头墙
				if(wall.good != true) {
					wall.setLive(false);
				}
				return true;
			}
		}
		return false;
	}
	
	//击中坦克
	public boolean hitTank(Tank tank) {
		if(tank.iView.intersects(this.circleX - 7, this.circleY-7,14,14)
				&& (this.good)!= tank.good) {

			return true;
		}
		
		return false;
	}
	
	
	public boolean hitTanks(ArrayList<Tank> t) {
		for(Tank tank:t) {
			if (hitTank(tank)) {
		
				tank.setLive(false);
				return true;
			}
		}
		return false;
	}
	//子弹移动的事件
	public void move() {
		//击中坦克，创建爆炸	
		if(hitTanks(tc.goodTanks) || hitTanks(tc.badTanks)) {
			this.live = false;
			//播放爆炸音效
			explodemediaplayer.play();
			explodemediaplayer.seek(Duration.ZERO);
			Explode explode = new Explode(this.circleX, this.circleY);
			tc.explodes.add(explode);
		}
		
		//击中墙体，
		if(hitWalls(tc.walls)) {
			this.live = false;
			Explode explode = new Explode(this.circleX, this.circleY);
			tc.explodes.add(explode);
		}
		
		//碰到边界
		if(circleX <= 15 || circleX >= 1185
				|| circleY <= 15 || circleY >= 785) {
			this.live = false;
		} 
		
		switch(direction) {
				case U: {
					circleY = circleY - YSPEED;
					break;
				}
				
				case D: {
					circleY = circleY + YSPEED;
					break;
				}
				
				case L: {
					circleX = circleX - XSPEED;
					break;
				}
				
				case R: {
					circleX = circleX + XSPEED;
					break;
				}
				
				case UL: {
					circleX = circleX - XSPEED;
					circleY = circleY - YSPEED;
					break;
				}
				
				case UR: {
					circleX = circleX + XSPEED;					
					circleY = circleY - YSPEED;
					break;
				}
				
				case DL: {
					circleX = circleX - XSPEED;
					circleY = circleY + YSPEED;
					break;
				}
				
				case DR: {
					circleX = circleX + XSPEED;
					circleY = circleY + YSPEED;
					break;
				}
		
		}
		iView.setX(circleX-MODIFYDISTANCE);
		iView.setY(circleY-MODIFYDISTANCE);
	}
	
}
	