package RMO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

import java.util.List;
import java.util.ArrayList;


public class Input {
	
	Input(){
		
		list = new ArrayList<Ball>();
		
		final Logger logger = LoggerFactory.getLogger(Input.class);
		
		Configurations configs = new Configurations();
		try{
			
		    XMLConfiguration config = configs.xml("config.xml"); 
		    
		    List<HierarchicalConfiguration<ImmutableNode>> config_list = config.configurationsAt("location");
		    logger.info("total balls are " + config_list.size());
		    
		    double _x;
		    double _y;
		    for(HierarchicalConfiguration<ImmutableNode> c : config_list) {
		    	
		    	_x = c.getDouble("x");
		    	_y = c.getDouble("y");
		    	
		    	if(_x<0 || _x>630.0) {
		    		logger.error("invalid x");
		    	}
		    	if(_y<0 || _y>470.0) {
		    		logger.error("invalid y");
		    	}
		    	
		    	Ball b = new Ball(_x, _y);
		    	list.add(b);
		    	
		    }
		    
		} catch (ConfigurationException cex) {
		
			logger.error("Fail to read config file");
	    
		}
	}
	
	public static List<Ball> getConfig() {
		return list;
	}
	
	private static List<Ball> list;
	
}
