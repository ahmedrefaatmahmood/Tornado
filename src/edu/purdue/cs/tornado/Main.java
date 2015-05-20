package edu.purdue.cs.tornado;

import edu.purdue.cs.tornado.spouts.DummyTweetGenerator;
import edu.purdue.cs.tornado.spouts.TextualRangeQueryGenerator;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		SpatioTextualToplogyBuilder builder = new SpatioTextualToplogyBuilder();
		builder.setSpout("TweetsGenerator", new DummyTweetGenerator(), 1);
		builder.setSpout("TextualRangeQueryGenerator", new TextualRangeQueryGenerator(), 1);
		builder.addStaticSpatioTextualProcessor("spatiotextualcomponent1", 4)
				.addPersistentSpatioTextualInput("TweetsGenerator")
				.addSnapShotQuerySource("TextualRangeQueryGenerator");
		
		
		
		Config conf = new Config();
		conf.setDebug(true);
		conf.setNumWorkers(2);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, builder.createTopology());
		Utils.sleep(10000);
		cluster.killTopology("test");
		cluster.shutdown();
	}
}
