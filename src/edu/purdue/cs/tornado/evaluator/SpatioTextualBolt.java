package edu.purdue.cs.tornado.evaluator;

import java.util.Map;

import edu.purdue.cs.tornado.helper.SpatioTextualConstants;
import edu.purdue.cs.tornado.messages.DataObjectList;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
/**
 * This class is for the adaptive indexing of streamed data 
 * @author Ahmed Mahmood
 *
 */
public class SpatioTextualBolt extends BaseRichBolt{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	public SpatioTextualBolt(String id){
		this.id = id;
	}
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		System.out.println("Preparing");
	}

	@Override
	public void execute(Tuple input) {
		DataObjectList dataObjectList=(DataObjectList) input.getValueByField(SpatioTextualConstants.data);
		for (int i=0;i<dataObjectList.getDataObjects().size();i++)
			System.out.println("receieved data item "+dataObjectList.getDataObjects().get(i).getObjectText());
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//These are the streams for interaction with other bolts 
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Query,
				new Fields(SpatioTextualConstants.query));
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Data,
				new Fields(SpatioTextualConstants.data));
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Control,
				new Fields(SpatioTextualConstants.control));
		
		
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Query,
				new Fields(SpatioTextualConstants.query));
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Data,
				new Fields(SpatioTextualConstants.data));
		
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Control,
				new Fields(SpatioTextualConstants.control));
		
		//This is the final output 
		declarer.declareStream(id
				+ SpatioTextualConstants.Bolt_Output_STreamIDExtension,
				new Fields(
						SpatioTextualConstants.data));
		
	}

}
