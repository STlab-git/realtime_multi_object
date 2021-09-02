package RMO;

public class Ball {

	Ball(){
		_x = 0.0;
		_y = 0.0;
		_tx = 0.0;
		_ty = 0.0;
		_spdx = 0.0;
		_spdy = 0.0;
		_rad = 5.0;
		_hit = true;
		_area = -1;
	}
	
	Ball(double x, double y){
		_x = x;
		_y = y;
		_tx = 0.0;
		_ty = 0.0;
		_spdx = 0.0;
		_spdy = 0.0;
		_rad = 5.0;
		_hit = true;
		_area = -1;
	}
	
	Ball(double x, double y, double r){
		_x = x;
		_y = y;
		_tx = 0.0;
		_ty = 0.0;
		_spdx = 0.0;
		_spdy = 0.0;
		_rad = r;
		_hit = true;
		_area = -1;
	}
	
	public double getX() {
		return _x+_tx;
	}

	public double getY() {
		return _y+_ty;
	}
	
	public double getR() {
		return _rad;
	}
	
	public double getTX() {
		return _tx;
	}
	
	public double getTY() {
		return _ty;
	}
	
	public void setR(double r) {
		_rad = r;
	}
	
	public void setSpeed(double spd) {
		_spdx = spd;
		_spdy = 0;
	}
	
	public int getArea() {
		return _area;
	}
	
	public void setArea(int area) {
		_area = area;
	}
	
	public void unsetHit() {
		_hit = false;
	}
	
	public void refresh(double w, double h) {
		
		double tx = _tx + _spdx;
		double ty = _ty + _spdy;
		
		if(_x+tx-_rad<=0 || w<=_x+tx+_rad) {
			_spdx = -_spdx;
		} else {
			_tx = tx;
			
		}
		
		if(_y+ty-_rad<=0 || h<=_y+ty+_rad) {
			_spdy = -_spdy;
		} else {
			_ty = ty;
		}
		
	}
	
	public void reflect() {
		_spdx = -_spdx;
		_spdy = -_spdy;
	}
	

	
	private double _x;
	private double _y;
	
	private double _rad;
	
	private double _spdx;
	private double _spdy;
	
	private double _tx;
	private double _ty;
	
	private boolean _hit;
	
	private int _area;
	
}
