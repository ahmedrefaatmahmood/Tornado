package edu.purdue.cs.tornado.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import edu.purdue.cs.tornado.helper.SpatioTextualConstants;

/**
 * 
 * This class is for build a static global index of streamed data
 * 
 * @author Ahmed Mahmood
 *
 */
public class StaticSpatialIndexBolt extends BaseRichBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * here we are assuming point objects every point can go only to a single
	 * query node
	 */
	private String id;
	//**************** Evaluator bolts attributes **********************
	private Integer numberOfEvaluatorTasks;
	private List<Integer> evaluatorBoltTasks; //this keeps track of the evaluator bolts ids 
	private Map<String, DataSourceInformation> sourcesInformations; //this keeps track of the type of every input source 
 
	
	//******************Grid  parameters ********************************
	private Double xrange;
	private Double yrange;
	private Double xStep;
	private Double yStep;
	private Integer xCellsNum;
	private Integer yCellsNum;
	
	//*******************Storm specific attributes *********************
	private Map stormConf;  //configuration
	private TopologyContext context;  //storm context
	private OutputCollector collector; 
	private List<Integer> mapQueryToPartitions(double  xmin, double  ymin, double  xmax,
			double  ymax) {

		ArrayList<Integer> partitions = new ArrayList<Integer>();
		int xMinCell = (int) (xmin / xStep);
		int yMinCell = (int) (ymin / yStep);
		int xMaxCell = (int) (xmax / xStep);
		int yMaxCell = (int) (ymax / yStep);
		for (Integer xCell = xMinCell; xCell <= xMaxCell; xCell++)
			for (Integer yCell = yMinCell; yCell <= yMaxCell; yCell++) {
				Integer partitionNum = xCell * yCellsNum + yCell;
				if (partitionNum >= evaluatorBoltTasks.size())
					System.out.println("error in query " + xmin + " , " + ymin
							+ " , " + xmax + " , " + ymax + "  index is "
							+ partitionNum + " while partitions "
							+ evaluatorBoltTasks.size());
				else {
					// System.out.println("Query "+xmin+" , "+ymin+" , "+xmax+" , "+ymax+" is mapped to xcell:"+xCell+"ycell:"+yCell+" index is "+
					// partitionNum+" to partitions "+_targets.get(partitionNum));
					partitions.add(evaluatorBoltTasks.get(partitionNum));
				}

			}

		return partitions;
	}
	private int mapDataPointToPartition(Double x, Double y) {
		Integer xCell = (int) (x / xStep);
		Integer yCell = (int) (y / yStep);
		Integer partitionNum = xCell * yCellsNum + yCell;
		if (partitionNum >= evaluatorBoltTasks.size()) {
			System.out.println("error in data " + x + " , " + y + "  index is "
					+ partitionNum + " while partitions "
					+ evaluatorBoltTasks.size());
			return 0;
		} else {
			 System.out.println("Point "+x+" , "+y+" is mapped to xcell:"+xCell+"ycell:"+yCell+" index is "+
			 partitionNum+" to partitions "+evaluatorBoltTasks.get(partitionNum));
			return evaluatorBoltTasks.get(partitionNum);
		}
	}
	/**
	 * Construcor
	 * @param id
	 */
	public StaticSpatialIndexBolt(String id) {
		this.id = id;
		this.xrange = SpatioTextualConstants.xMaxRange;
		this.yrange = SpatioTextualConstants.yMaxRange;
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {

		this.context = context;
		this.collector = collector;
		this.stormConf = stormConf;
		this.evaluatorBoltTasks = context.getComponentTasks(id); 
		numberOfEvaluatorTasks = this.evaluatorBoltTasks.size();
		yCellsNum = xCellsNum = (int)Math.sqrt(numberOfEvaluatorTasks);
		xStep = xrange/xCellsNum;
		yStep = yrange/yCellsNum;
		System.out.println("***************************Printing polt configuration*******************");
		Iterator it = stormConf.entrySet().iterator();
		sourcesInformations = new HashMap<String, DataSourceInformation>();
		String sourceId = "";
		String sourceType = "";
		Boolean persistent = true;
		
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			
			String key = ""+(String)pair.getKey();
			
			
			if(key.startsWith(SpatioTextualConstants.Data_Source)){
				sourceType = SpatioTextualConstants.Data_Source;
				sourceId = key.substring(SpatioTextualConstants.Data_Source.length()+1);
			}else if(key.startsWith(SpatioTextualConstants.Query_Source)){
				sourceType = SpatioTextualConstants.Query_Source;
				sourceId = key.substring(SpatioTextualConstants.Query_Source.length()+1);
			}else
				continue;
			String value = ""+(String)pair.getValue();
			if(value.startsWith(SpatioTextualConstants.Volatile))
				persistent = false;
			
			DataSourceInformation dataSourcesInformation = new DataSourceInformation(sourceId, sourceType, persistent);
			sourcesInformations.put(sourceId, dataSourcesInformation);
			System.out.println(key + " = " + value);
			
		}

	}

	@Override
	public void execute(Tuple input) {
		//get tuple source information
		String source = input.getSourceComponent();
		String streamId = input.getSourceStreamId();
		if(!SpatioTextualConstants.Default.equals(streamId))
			source = source+"_"+streamId;
		Boolean persistent =sourcesInformations.get(source).getPersistent();
		String sourceType = sourcesInformations.get(source).getDataSourceType();
		
		if(SpatioTextualConstants.Query_Source.equals(sourceType)){
			handleQuery(input, source, persistent);
		}
		else if(SpatioTextualConstants.Data_Source.equals(sourceType)){
			handleData(input,source,persistent);
		}
		
		
		
	}
	private void handleQuery(Tuple input,String source,Boolean persistent){
		//identify the evaluator bolt to submit your tuple to 

		//update the last evaluator bolt information
	}
	private void handleData(Tuple input,String source,Boolean persistent){
		//identify the evaluator bolt to submit your tuple to 
		//if persistent update the last evaluator bolt information and emit to the previous bolt information 
		//to invalidate the previous tuple, 
		//the evaluator bolt should detect this and remove the previous tuple and the send to the proper bolt 
		Integer objectId = input.getIntegerByField(SpatioTextualConstants.objectIdField) ;
		Double objectXCoord = input.getDoubleByField(SpatioTextualConstants.objectXCoordField);
		Double objectYCoord = input.getDoubleByField(SpatioTextualConstants.objectYCoordField);
		String objectText = input.getStringByField(SpatioTextualConstants.objectTextField);
		Long timeStamp = input.getLongByField(SpatioTextualConstants.timeStamp);
		
		Integer evalauatorTask = mapDataPointToPartition(objectXCoord, objectYCoord);
		if(persistent){
			if(sourcesInformations.get(source)==null)
				sourcesInformations.put(source, new DataSourceInformation());
			Integer previousEvalauatorTask = sourcesInformations.get(source).getLastBoltTasKInformation().get(objectId);
			if(previousEvalauatorTask!=null&&previousEvalauatorTask!=evalauatorTask){
				sourcesInformations.get(source).getLastBoltTasKInformation().put(objectId, evalauatorTask);
				collector.emitDirect(previousEvalauatorTask, SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data, new  Values( id, objectXCoord, objectYCoord, objectText, timeStamp) );
			}
			else if(previousEvalauatorTask!=null)
				collector.emitDirect(previousEvalauatorTask, SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data, new  Values( id, objectXCoord, objectYCoord, objectText, timeStamp) );
			else
				collector.emitDirect(evalauatorTask, SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data, new  Values( id, objectXCoord, objectYCoord, objectText, timeStamp) );
		}
		else{
			collector.emitDirect(evalauatorTask, SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data, new  Values( id, objectXCoord, objectYCoord, objectText, timeStamp) );
		}
		
	}
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declareStream(id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Query,
				new Fields(SpatioTextualConstants.queryTypeField,
						SpatioTextualConstants.queryIdField,
						SpatioTextualConstants.focalXCoordField,
						SpatioTextualConstants.focalYCoordField,
						SpatioTextualConstants.kField,
						SpatioTextualConstants.queryTextField,
						SpatioTextualConstants.timeStamp,
						SpatioTextualConstants.queryXMinField,
						SpatioTextualConstants.queryYMinField,
						SpatioTextualConstants.queryXMaxField,
						SpatioTextualConstants.queryYMaxField));
		declarer.declareStream(id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data,
				new Fields(SpatioTextualConstants.objectIdField,
						SpatioTextualConstants.objectXCoordField,
						SpatioTextualConstants.objectYCoordField,
						SpatioTextualConstants.objectTextField,
						SpatioTextualConstants.timeStamp));
		
		declarer.declareStream(id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Control,
				new Fields(SpatioTextualConstants.control));

	}

	
	
}
