package RMO;

public class Ball {

	Ball(){
		_x = 0.0;
		_y = 0.0;
	}
	
	Ball(double x, double y){
		_x = x;
		_y = y;
	}
	
	public double getX() {
		return _x;
	}

	public double getY() {
		return _y;
	}

	
	private double _x;
	private double _y;
}
