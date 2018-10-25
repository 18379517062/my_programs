
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class Tank extends Fighter {
	
	private static Random random = new Random();
	private double oldX, oldY;
	public Line tube;
	public ArrayList<Image> images = new ArrayList<Image>();
	public ImageView iView = new ImageView();
	//̹�˴�С50���أ��߽����25
	private static final int TUBELENGTH = 25;
	private int step = random.nextInt(12) + 3;
	//�з�̹������
	private int BADFIREPOWER = 7;   

	private int MODIFYDISTANCE = 25;
	
	//�ӵ����������
		public  Media shootmedia = new Media(getClass().getResource("music/�����Ч.mp3").toExternalForm());	
	    public  MediaPlayer shootmediaplayer = new MediaPlayer(shootmedia);
    
    
	
	public Tank(int x, int y, boolean good, TankWar tc) {
		circleX = x;
		circleY = y;
		this.XSPEED = 5;
		this.YSPEED = 5;
		this.good = good;
		
		if(good) {
			for(int i = 0; i<8;i++) {
				images.add(new Image("tankPNG//goodTank_"+Integer.toString(i)+".png"));
			}
		} else {
			for(int i = 0; i<8;i++) {
				images.add(new Image("tankPNG//badTank_"+Integer.toString(i)+".png"));
			}
		}
		
		tube = new Line(circleX, circleY, circleX, circleY+TUBELENGTH);
		iView.setImage(images.get(1));
		this.getChildren().add(iView);
		
		//ͼƬ�ŵĵط���Ŀ��ľ���
		iView.setX(circleX);
		iView.setY(circleY);	
		this.tc = tc;
		tc.warField.getChildren().add(this);
	}

	//���ӵ�
	public void fire() {
		
		Missile bullet = new Missile(circleX, circleY, tubeDir, good,tc);
		
	}
	
	//��������������淢�ӵ�
	private void superFire() {
		Direction[] directions = Direction.values();
		for(int i = 0; i<8 ;i++) {
			Missile bullet = new Missile(tube.getEndX(), tube.getEndY(), directions[i], good,tc);
		}
	}

	//�������������������ĸ�����
	public void keyPressed(KeyEvent event) {

		KeyCode code = event.getCode();
		
		if(code == KeyCode.UP) {
			UP = true;
		} 
		if(code == KeyCode.DOWN) {
			DOWN = true;
		} 
		if(code == KeyCode.LEFT) {
			LEFT = true;
		} 
		if(code == KeyCode.RIGHT) {
			RIGHT = true;
		}
		
		getDirection();
	
	}

	//�ɼ��¼���
	public void keyReleased(KeyEvent event) {
		KeyCode code = event.getCode();
		if(code == KeyCode.SPACE) {
		shootmediaplayer.play();
		shootmediaplayer.seek(Duration.ZERO);
			//��һ��������ӵ�
			fire();
			//shootmediaplayer.stop();
		} else if(code == KeyCode.S) {
			//��˸�������ӵ�
			shootmediaplayer.play();
			shootmediaplayer.seek(Duration.ZERO);
			superFire();
		}
		//���������¼�����ֹһֱ��һ�������ƶ�
		if(code == KeyCode.UP) {
			UP = false;
		}
		if(code == KeyCode.DOWN) {
			DOWN = false;
		}
		if(code == KeyCode.LEFT) {
			LEFT = false;
		}
		if(code == KeyCode.RIGHT) {
			RIGHT = false;
		}
		
		getDirection();	
	}
	
	//�˸�����
	private void getDirection() {
		if(UP && !DOWN && !LEFT && !RIGHT) {
			direction = Direction.U;
		}
		else if(UP && !DOWN && LEFT && !RIGHT) {
			direction = Direction.UL;
		}
		else if(UP && !DOWN && !LEFT && RIGHT) {
			direction = Direction.UR;
		}
		else if(!UP && DOWN && !LEFT && !RIGHT) {
			direction = Direction.D;
		}
		else if(!UP && DOWN && LEFT && !RIGHT) {
			direction = Direction.DL;
		}
		else if(!UP && DOWN && !LEFT && RIGHT) {
			direction = Direction. DR;
		}
		else if(!UP && !DOWN && LEFT && !RIGHT) {
			direction = Direction.L;
		}
		else if(!UP && !DOWN && !LEFT && RIGHT) direction = Direction.R;
		else direction = Direction.STOP;
	}
	
	//�ƶ�
	public void tubeMove() {
		tube.setStartX(circleX);
		tube.setStartY(circleY);
		if(direction != Direction.STOP) {
			tubeDir = direction;
		}
		switch(tubeDir) {
		case U: {
			tube.setEndX(circleX);
			tube.setEndY(circleY - TUBELENGTH);
			break;
		}
		
		case D: {
			tube.setEndX(circleX);
			tube.setEndY(circleY + TUBELENGTH);
			break;
		}
		
		case L: {
			tube.setEndX(circleX - TUBELENGTH);
			tube.setEndY(circleY);
			break;
		}
		
		case R: {
			tube.setEndX(circleX + TUBELENGTH);
			tube.setEndY(circleY);
			break;
		}
		
		case UL: {
			tube.setEndX(circleX - TUBELENGTH/1.4);
			tube.setEndY(circleY - TUBELENGTH/1.4);
			break;
		}
		
		case UR: {
			tube.setEndX(circleX + TUBELENGTH/1.4);
			tube.setEndY(circleY - TUBELENGTH/1.4);
			break;
		}
		
		case DL: {
			tube.setEndX(circleX - TUBELENGTH/1.4);
			tube.setEndY(circleY + TUBELENGTH/1.4);
			break;
		}
		
		case DR: {
			tube.setEndX(circleX + TUBELENGTH/1.4);
			tube.setEndY(circleY + TUBELENGTH/1.4);
			break;
		}
		}
	}
	
	public void move() {
		//�з�̹�˵�����ƶ��ķ���
		if(!good) {
			Direction[] directions = Direction.values();
			if(step == 0) {
				step = random.nextInt(12) + 3;
				int n = random.nextInt(directions.length);
				direction = directions[n];
			}
			step --;
			if(random.nextInt(40)<BADFIREPOWER ) {
				fire();
			}
		}
		
		tubeMove();
		
		switch(direction) {
			case U: {
				circleY = circleY - YSPEED;
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(1));
				break;
			}
			
			case D: {
				circleY = circleY + YSPEED;
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(5));
				break;
			}
			
			case L: {
				circleX = circleX - XSPEED;
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setImage(images.get(7));
				break;
			}
			
			case R: {
				circleX = circleX + XSPEED;
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setImage(images.get(3));
				break;
			}
			
			case UL: {
				circleX = circleX - XSPEED;
				circleY = circleY - YSPEED;
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(0));
				break;
			}
			
			case UR: {
				circleX = circleX + XSPEED;
				circleY = circleY - YSPEED;
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(2));
				break;
			}
			
			case DL: {
				circleX = circleX - XSPEED;		
				circleY = circleY + YSPEED;			
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(6));
				break;
			}
			
			case DR: {
				circleX = circleX + XSPEED;		
				circleY = circleY + YSPEED;			
				iView.setX(circleX - MODIFYDISTANCE);
				iView.setY(circleY - MODIFYDISTANCE);
				iView.setImage(images.get(4));
				break;
			}
			
			default: break;
		
		}
		
		//̹�˱߽���
		if(circleX < 25) circleX = 25;
		if(circleX > 1175) circleX = 1175;
		if(circleY < 25) circleY = 25;
		if(circleY > 740) circleY = 740;
		
		if(collidesWithTanks(tc.goodTanks) || collidesWithWalls(tc.walls) ||
				collidesWithTanks(tc.badTanks)) {
			direction = Direction.STOP;
			stay();
		}
		
		oldX = this.circleX;
		oldY = this.circleY;
		
	}

	//��ǰ���ᷢ����ײʱ�������ԭ��
	private void stay() {
		circleX = oldX;
		circleY = oldY;
	}
	
	//̹����̹�˽Ӵ����
	private boolean collidesWithTank(Tank tank) {
		if(this.iView.intersects(tank.circleX-MODIFYDISTANCE, tank.circleY-MODIFYDISTANCE, 50, 50) && this!=tank) {
			return true;
		}
		return false;
	}
	
	//̹����ײ���
	private boolean collidesWithTanks(ArrayList<Tank> tanks) {
		for(Tank tank: tanks) {
			if(collidesWithTank(tank)) {
				return true;
			}
		}
		return false;
	}
	
	//ǽ��Ӵ����
	public boolean collidesWithWalls(ArrayList<Wall> walls) {
		for(Wall wall: walls) {
			if(this.iView.intersects(wall.circleX-wall.getWidth()/2, wall.circleY-wall.getHeight()/2,
					wall.getWidth(), wall.getHeight())) {
				return true;
			}
		}
		return false;
	}
}
