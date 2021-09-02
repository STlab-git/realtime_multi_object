package RMO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Math;
import java.lang.Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.lang.NullPointerException;


public class Tree extends Exception {
	
	Tree(double w, double h){

		_depth = 1;
		try {
			if(_depth*2>MAXBIT) {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("too deep trees");
			System.exit(0);
		}
		_wpix = w/(Math.pow(2, _depth));
		_hpix = h/(Math.pow(2, _depth));
		
		int len = (power(4, _depth+1)-1)/3;
		_tree = new ArrayList<Node>(len);	
		for(int i=0; i<len; i+=1) {
			Node tmp = new Node(i);
			_tree.add(tmp);
		}

	}
	
	public void findCollision(int cur_area, Deque<Ball> stack_ball) {
		
		//FIXME: when the balls are overlapped in the initial phase
		// they will vibrate.
		checkNode(cur_area, stack_ball);

		int next;
		boolean hasNext = false;
		boolean initPush = true;
		for(int i=0; i<4; i+=1) {
			next = cur_area*4 + 1 + i;
			hasNext = (next <= ((power(4, _depth+1)-1)/3) - 1);
			if(hasNext) {
				if(initPush) {
					for(Ball b: _tree.get(cur_area).getBall()) {
						stack_ball.push(b);
					}
					initPush = false;
				}
				findCollision(next, stack_ball);
			}
		}
		
		if(!initPush) {
			for(int i=0; i<_tree.get(cur_area).getBall().size(); i+=1) {
				stack_ball.pop();
			}
		}

		
	}
	
	public void moveObject(Ball b) {
		
		int cur_area = b.getArea();
		calculateArea(b);

		if(b.getArea() != cur_area) {
			_tree.get(cur_area).removeBall(b);
			_tree.get(b.getArea()).setBall(b);
		}
		
	}
	
	public void setObject(Ball b) {
		
		calculateArea(b);
		_tree.get(b.getArea()).setBall(b);
		
	}
	
	private void checkNode(int cur_area, Deque<Ball> stack_ball) {
		
		List<Ball> cur_list = _tree.get(cur_area).getBall();
		
		if(!cur_list.isEmpty()) {
			
			Ball b1,b2;
			for(int i=0; i<cur_list.size(); i+=1) {
				
				b1 = cur_list.get(i);
				
				for(int j=i+1; j<cur_list.size(); j+=1) {
					b2 = cur_list.get(j);
					checkCollision(b1, b2);
				}
				
				for(Ball idx: stack_ball) {
					checkCollision(b1, idx);
				}
				
			}
			
		}
		
	}
	
	private void checkCollision(Ball b1, Ball b2) {
		
		double len = Math.sqrt((b1.getX()-b2.getX())*(b1.getX()-b2.getX())+ 
				(b1.getY()-b2.getY())*(b1.getY()-b2.getY()));
		
		if(len <= b1.getR()+b2.getR()) {
			b1.reflect();
			b2.reflect();
		}
		
	}
		
	private void calculateArea(Ball result) {
		// GUI xy-coordinate
		// (xs,ys) (xl,ys)
		// (xs,yl) (xl,yl)
		// xs = getWNode(x-r)
		// xl = getWNode(x+r)
		// ys = getHNode(y-r)
		// yl = getHNode(y+r)

		double x = result.getX();
		double y = result.getY();
		double r = result.getR();
		
		int upperL = XbitShift(getWNode(x-r)) | YbitShift(getHNode(y-r));
		int lowerR = XbitShift(getWNode(x+r)) | YbitShift(getHNode(y+r));

		int tmp;
		int count = 0;
		for(int i=0; i<MAXBIT; i+=2) {
			tmp = ((upperL^lowerR) & (3*power(4,i))) >> 2*i;
			if(tmp!=0) {
				count = 2*(i+1);
			}
		}
		
		try {
			if(count/2>_depth) {
				throw new Exception();
			}
		} catch(Exception e) {
			logger.error("invalid bit shift");
			System.exit(0);
		}
		
		int area = (lowerR >> count);
		result.setArea((power(4, _depth-count/2)-1) / 3 + area);
		
	}
	
	private int getWNode(double x) {
		
		try {
			if(x<0) {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("ball is not in the defined W area");
			System.exit(0);
		}
		
		return (int)Math.floor(x/_wpix);
		
	}

	private int getHNode(double x) {

		try {
			if(x<0) {
				throw new Exception();
			}
		} catch (Exception e) {
			logger.error("ball is not in the defined H area");
			System.exit(0);
		}

		return (int)Math.floor(x/_hpix);

	}

	private int XbitShift(int x) {
		
		int shift;
		// 0000 0000 0000 0000 aaaa bbbb cccc dddd
		// 0000 0000 aaaa bbbb 0000 0000 cccc dddd		
		shift = (x | (x << 8)) & 0x00ff00ff;

		// 0000 0000 aaaa bbbb 0000 0000 cccc dddd	
		// 0000 aaaa 0000 bbbb 0000 cccc 0000 dddd
		shift = (shift | (shift << 4)) & 0x0f0f0f0f;
		
		// 0000 aaaa 0000 bbbb 0000 cccc 0000 dddd
		// 00aa 00aa 00bb 00bb 00cc 00cc 00dd 00dd
		shift = (shift | (shift << 2)) & 0x33333333;
		
		// 00aa 00aa 00bb 00bb 00cc 00cc 00dd 00dd
		// 0a0a 0a0a 0b0b 0b0b 0c0c 0c0c 0d0d 0d0d
		shift = (shift | (shift << 1)) & 0x55555555;
		
		return shift;
		
	}

	private int YbitShift(int x) {

		int shift = XbitShift(x);
		shift = shift << 1;
		
		return shift;
		
	}
	
	private static int power(int a, int b) {
		int result = 1;
		for(int i=0; i<b; i+=1) {
			result *= a;
		}
		return result;
	}
	
	/**
	private class Register {
		
		Register(){
			_b = new Ball();
			_area = 0;
			_depth = 0;
		}
		
		Register(Ball b){
			_b = b;
			_area = 0;
			_depth = 0;
		}
		
		Register(Ball b, int area){
			_b = b;
			_area = area;
			_depth = 0;
		}
				
		public int getArea() {
			return _area;
		}
		
		public Ball getBall() {
			return _b;
		}
		
		public void replaceArea(int area) {
			_area = area;
		}
		
		public void setDepth(int d) {
			_depth = d;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(!(obj instanceof Register)) {
				return false;
			} else if(!(obj instanceof Ball)) {
				return false;
			}
			
			Register otherreg = (Register) obj;
			Ball otherball = (Ball) obj;
			if(this.getBall() == otherreg.getBall()) {
				return true;
			} else if(this.getBall() == otherball)  {
				return true;
			} else {
				return false;
			}
			
		}
		
		
		private Ball _b;
		private int _area;
		private int _depth;
		
	}
	
	private class CompareRegister implements Comparator<Register>{
		@Override
		public int compare(Register r1, Register r2) {
			if(r1.getArea() > r2.getArea()) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	**/
	
	private class Node {
		
		Node(int a){
			_values = new ArrayList<Ball>();
		}
		
		public void setBall(Ball b) {
			_values.add(b);
		}
		
		public void removeBall(Ball b) {
			
			try {
				if(_values.contains(b)) {
					_values.remove(b);
				} else {
					throw new NullPointerException();
				}
			} catch(NullPointerException e) {
				logger.error("the ball is not in the node");
				System.exit(0);
			}
		}
		
		public List<Ball> getBall(){
			return _values;
		}
		
		private List<Ball> _values;
		
	}
		
	
	private final Logger logger = LoggerFactory.getLogger(Tree.class);
	
	private List<Node> _tree;
	
	private int _depth;
	private double _wpix;
	private double _hpix;
	
	private final int MAXBIT = 32;
	
	
}
