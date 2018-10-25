import javafx.scene.Group;

public abstract class Fighter extends Group{
	protected double circleX;
	protected double circleY;
	public TankWar tc;
	enum Direction {U, D, L, R, UR, UL, DR, DL, STOP};
	public Direction direction = Direction.STOP;
	public Direction tubeDir = Direction.D;
	public boolean UP = false,DOWN = false, LEFT = false, RIGHT = false;
	
	public int XSPEED;
	public int YSPEED;
	public boolean good;
	boolean live = true;
	
	public boolean isLive() {
		return live;
		
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	///public void move() {}
			
	
	public double getX() {
		return circleX;
	}
	
	public double getY() {
		return circleY;
	}
	
}
