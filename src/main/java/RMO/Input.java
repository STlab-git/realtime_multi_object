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
		
		final Logger logger = LoggerFactory.getLogger(Input.class);
		
		Configurations configs = new Configurations();
		try{
			
		    XMLConfiguration config = configs.xml("config.xml"); 
		    
		    List<HierarchicalConfiguration<ImmutableNode>> connection_list = config.configurationsAt("connections");
		    logger.info("total balls are " + connection_list.size());
		    
		    for(HierarchicalConfiguration<ImmutableNode> connection : connection_list) {
		    	
		    	int _start = connection.getInt("start") - 1;
		    	int _end = connection.getInt("end") - 1;
		    	double _dis = connection.getDouble("dis");
		    	
		    	if(_start<0 || _all_nodes-1<_start) {
		    		logger.error("invalid start node");
		    	}
		    	if(_end<0 || _all_nodes-1<_end) {
		    		logger.error("invalid end node");
		    	}
		    	
		    	Branch b = new Branch(_start, _end, _dis);
		    	_branches.add(b);
		    	
		    }
		    
	    	logger.info("read branch information");

		    
		}
		catch (ConfigurationException cex) {
		
			logger.error("Fail to read config file");
	    
		}
	}
	
	public get_

}
