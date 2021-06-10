package RMO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;

public class Main {

public static void main(String arg[]) {
		
		final Logger logger = LoggerFactory.getLogger(Main.class);
		
		logger.info("start to read a config file");
        Input config = new Input();        
        
        Gui gui = new Gui();
        Gui.run(arg);
		
	}
	
}
