package edu.purdue.cs.tornado.spouts;

import java.util.Date;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import edu.purdue.cs.tornado.helper.RandomGenerator;
import edu.purdue.cs.tornado.helper.SpatioTextualConstants;

public class TextualKNNQueryGenerator extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private RandomGenerator randomGenerator;
	int i ;

	public void ack(Object msgId) {
		System.out.println("OK:" + msgId);
	}

	public void close() {
	}

	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}

	public void nextTuple() {
		// Generate queries at random.
		if (i < SpatioTextualConstants.numQueries) { // i will be the query
			// id.
			i++;
			Double xCoord = randomGenerator.nextDouble(0,SpatioTextualConstants.xMaxRange);
			Double yCoord = randomGenerator.nextDouble(0,SpatioTextualConstants.yMaxRange);

			int k = randomGenerator.nextInt(SpatioTextualConstants.maxK) + 1; 
			String textContent = "";
			for (int i = 0; i < SpatioTextualConstants.queryTextualContentLength; i++)
				textContent = SampleTextualContent.TextArr[randomGenerator
						.nextInt(SampleTextualContent.TextArr.length - 1)]
						+ " ";
			Date date = new Date();
			this.collector.emit(new Values(SpatioTextualConstants.queryTextualKNN,i, xCoord, yCoord, k,textContent,date));

			try {
				if (SpatioTextualConstants.queryGeneratorDelay != 0)
					Thread.sleep(SpatioTextualConstants.queryGeneratorDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		i=0;
		this.collector = collector;
		randomGenerator = new RandomGenerator(SpatioTextualConstants.generatorSeed);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(
				SpatioTextualConstants.queryTypeField,
				SpatioTextualConstants.queryIdField,
				SpatioTextualConstants.focalXCoordField,
				SpatioTextualConstants.focalYCoordField,
				SpatioTextualConstants.kField,
				SpatioTextualConstants.queryTextField,
				SpatioTextualConstants.timeStamp));
	}
}
