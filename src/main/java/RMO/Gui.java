package RMO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;


public class Gui extends Application {

	public static void run(String args[]) {
		launch(args);
	}
	
	@Override
	public void init() throws Exception{
		
		status_box = new VBox();
		status_box.setAlignment(Pos.CENTER);
		
		scene = new Scene(status_box, 640, 500);
		
		root = new Group();
		
		status = new Label();
		status.setText("click!");
		status.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		status.setMinWidth(640);
		status.setAlignment(Pos.CENTER);
		
		logger.info("get config");

		List<Ball> c = new ArrayList<Ball>();
		c = Input.getConfig();

		Ball tmp;
		for(Iterator<Ball> it=c.iterator(); it.hasNext();) {
			
			tmp = it.next();
			Circle cir = new Circle(tmp.getX(), tmp.getY(), _rad, Color.RED);
			root.getChildren().add(cir);

		}
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		try {
			
			initDraw();

			status_box.getChildren().add(root);
			status_box.getChildren().add(status);
			
			status_box.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			
			primaryStage.setTitle("Multiple Objects");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}catch(Exception e) {
			logger.error("fail to initialize");
		}
		
	}
	
	public void initDraw() {
		
		Circle cir = new Circle(620.0, 470.0, _rad, Color.RED);
		root.getChildren().add(cir);
		
	}
	
	private void firstMouseClick(MouseEvent e) {
		status.setText("on process!");
		logger.info("start to move");
	}
	
	
	private final Logger logger = LoggerFactory.getLogger(Gui.class);
	
	private Group root;
	private Scene scene;
	private VBox status_box;
	private Label status;
	
	private double _rad = 10.0;
	
}
