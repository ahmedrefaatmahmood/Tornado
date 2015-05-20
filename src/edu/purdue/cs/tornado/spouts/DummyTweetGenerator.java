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

/**
 * This is a sample of a transient spatio-textual object generator
 * 
 * @author Ahmed Mahmood
 *
 */
public class DummyTweetGenerator extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private RandomGenerator randomGenerator;
	int i = 0;

	public void ack(Object msgId) {
		System.out.println("OK:" + msgId);
	}

	public void close() {
	}

	public void fail(Object msgId) {
		System.out.println("FAIL:" + msgId);
	}

	@Override
	public void nextTuple() {

		int id = randomGenerator
				.nextInt(SpatioTextualConstants.numMovingObjects);
		Double xCoord = randomGenerator.nextDouble(0,
				SpatioTextualConstants.xMaxRange);
		Double yCoord = randomGenerator.nextDouble(0,
				SpatioTextualConstants.yMaxRange);
		String textContent = "";
		for (int i = 0; i < SpatioTextualConstants.objectTextualContentLength; i++)
			textContent = SampleTextualContent.TextArr[randomGenerator
					.nextInt(SampleTextualContent.TextArr.length - 1)] + " ";

		Date date = new Date();

		this.collector.emit(new Values(id, xCoord, yCoord, textContent, date
				.getTime()));
		try {
			Thread.sleep(SpatioTextualConstants.dataGeneratorDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		randomGenerator = new RandomGenerator(
				SpatioTextualConstants.generatorSeed);

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(SpatioTextualConstants.objectIdField,
				SpatioTextualConstants.objectXCoordField,
				SpatioTextualConstants.objectYCoordField,
				SpatioTextualConstants.objectTextField,
				SpatioTextualConstants.timeStamp));
	}

}
