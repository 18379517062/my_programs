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
	//�ӵ��ĳ�����̹�˵ľ���
	private double MODIFYDISTANCE = 12;  
	//�ӵ��࣬14*14���ش�С



	//��ըЧ��������
	public  Media explodemedia = new Media(getClass().getResource("music/��ը��Ч.mp3").toExternalForm());	
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
	//����ǽ
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
				//ľͷǽ
				if(wall.good != true) {
					wall.setLive(false);
				}
				return true;
			}
		}
		return false;
	}
	
	//����̹��
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
	//�ӵ��ƶ����¼�
	public void move() {
		//����̹�ˣ�������ը	
		if(hitTanks(tc.goodTanks) || hitTanks(tc.badTanks)) {
			this.live = false;
			//���ű�ը��Ч
			explodemediaplayer.play();
			explodemediaplayer.seek(Duration.ZERO);
			Explode explode = new Explode(this.circleX, this.circleY);
			tc.explodes.add(explode);
		}
		
		//����ǽ�壬
		if(hitWalls(tc.walls)) {
			this.live = false;
			Explode explode = new Explode(this.circleX, this.circleY);
			tc.explodes.add(explode);
		}
		
		//�����߽�
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
	