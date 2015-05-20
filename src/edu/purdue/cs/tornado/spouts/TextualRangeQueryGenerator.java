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
 * This is a sample of a persisent spatio-textual moving object genereator
 * 
 * @author ahmed
 *
 */
public class TextualRangeQueryGenerator extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector collector;
	private RandomGenerator randomGenerator;
	int i;

	public void nextTuple() {

		if (i < SpatioTextualConstants.numQueries) { // i will be the query id.
			i++;
			Double xMin = randomGenerator.nextDouble(0,
					SpatioTextualConstants.xMaxRange);
			Double yMin = randomGenerator.nextDouble(0,
					SpatioTextualConstants.yMaxRange);

			Double width = randomGenerator.nextDouble(0,
					SpatioTextualConstants.queryMaxWidth);
			Double xMax = xMin + width;
			if (xMax > SpatioTextualConstants.xMaxRange) {
				xMax = SpatioTextualConstants.xMaxRange;
			}

			Double height = randomGenerator.nextDouble(0,
					SpatioTextualConstants.queryMaxHeight);
			Double yMax = yMin + height;
			if (yMax > SpatioTextualConstants.yMaxRange) {
				yMax = SpatioTextualConstants.yMaxRange;
			}
			String textContent = "";
			for (int i = 0; i < SpatioTextualConstants.queryTextualContentLength; i++)
				textContent = SampleTextualContent.TextArr[randomGenerator
						.nextInt(SampleTextualContent.TextArr.length - 1)]
						+ " ";
			Date date = new Date();
			this.collector.emit(new Values(
					SpatioTextualConstants.queryTextualRange, i, xMin, yMin,
					xMax, yMax, textContent, date));

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
		i = 0;
		this.collector = collector;
		randomGenerator = new RandomGenerator(
				SpatioTextualConstants.generatorSeed);
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(SpatioTextualConstants.queryTypeField,
				SpatioTextualConstants.queryIdField,
				SpatioTextualConstants.queryXMinField,
				SpatioTextualConstants.queryYMinField,
				SpatioTextualConstants.queryXMaxField,
				SpatioTextualConstants.queryYMaxField,
				SpatioTextualConstants.queryTextField,
				SpatioTextualConstants.queryTimeStampField));
	}
}
