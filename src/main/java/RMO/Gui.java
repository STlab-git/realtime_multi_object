package RMO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Deque;
import java.util.ArrayDeque;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;



public class Gui extends Application {

	public static void run(String args[]) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception{
		
		tk = new Timekeeper();
		
		click = false;
		
		status_box = new VBox();
		status_box.setAlignment(Pos.CENTER);
		
		scene = new Scene(status_box, 640, 500);
		width = 640;
		height = 480;
		
		root = new Group();
		
		status = new Label();
		status.setText("click!");
		status.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
		status.setMinWidth(640);
		status.setAlignment(Pos.BOTTOM_CENTER);
		
		logger.info("get config");

		obs = new ArrayList<Ball>();
		obs = Input.getConfig();

		ego = new Ball(90, 50, 10);
		ego.setSpeed(-3);
		
		tree = new Tree(640, 480);
		tree.setObject(ego);
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {
			
			Rectangle rect = new Rectangle(640, 480, Color.GREENYELLOW);
			root.getChildren().add(rect);
			
			Circle cir0 = new Circle(ego.getX(), ego.getY(), ego.getR(), Color.RED);
			root.getChildren().add(cir0);
			
			Ball tmp;
			for(Iterator<Ball> it=obs.iterator(); it.hasNext();) {
				
				tmp = it.next();
				tmp.setR(20);
				tree.setObject(tmp);
				Circle cir = new Circle(tmp.getX(), tmp.getY(), tmp.getR(), Color.AQUA);
				root.getChildren().add(cir);
				
			}
		
			status_box.getChildren().add(root);
			status_box.getChildren().add(status);
			status_box.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			
			
			EventHandler<MouseEvent> firstclick = new EventHandler<MouseEvent>() { 
				@Override 
				public void handle(MouseEvent e) {
					
					if(click) {return;}
					
					status.setText("on process!");
					status.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
					logger.info("start to move");
					
					tk.start();
				} 
			};   
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, firstclick);
			
			
			EventHandler<MouseEvent> secondclick = new EventHandler<MouseEvent>() { 
				@Override 
				public void handle(MouseEvent e) {
					if(click) {
						logger.info("process end");
						Platform.exit();
					}
					click = true;
				} 
			};   
			scene.addEventFilter(MouseEvent.MOUSE_PRESSED, secondclick);
			
			primaryStage.setTitle("Multiple Objects");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(Exception e) {
			logger.error("fail to initialize");
		}
		
	}
	
	private class Timekeeper extends AnimationTimer {

		Timekeeper(){
			stime = 0;
			foreno = -1;
		}
		
		@Override
		public void handle(long now) {
			
			if(stime == 0) {
				stime = now;
			}
			Node target;
			target = root.getChildren().get(1);
			ego.refresh(width, height);
			target.setTranslateX(ego.getTX());
	        target.setTranslateY(ego.getTY());
	        tree.moveObject(ego);

			target = root.getChildren().get(2);
			obs.get(0).refresh(width, height);
			target.setTranslateX(obs.get(0).getTX());
	        target.setTranslateY(obs.get(0).getTY());
	        tree.moveObject(obs.get(0));
	        
	        target = root.getChildren().get(3);
			obs.get(1).refresh(width, height);
			target.setTranslateX(obs.get(1).getTX());
	        target.setTranslateY(obs.get(1).getTY());
			tree.moveObject(obs.get(1));
	        
			tree.findCollision(0, new ArrayDeque<Ball>());
			
		}
		
		
		private long stime;
		private long foreno;
		
	}

	
	
	private final Logger logger = LoggerFactory.getLogger(Gui.class);
	
	private Group root;
	private Scene scene;
	private VBox status_box;
	private Label status;
	
	private double width;
	private double height;
	
	private Timekeeper tk;
	
	private Ball ego;
	private List<Ball> obs;
	
	private boolean click;
	
	private Tree tree;
	
}
