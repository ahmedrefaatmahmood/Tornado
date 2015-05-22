package edu.purdue.cs.tornado;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;
import edu.purdue.cs.tornado.messages.Control;
import edu.purdue.cs.tornado.messages.DataObject;
import edu.purdue.cs.tornado.messages.DataObjectList;
import edu.purdue.cs.tornado.messages.Query;
import edu.purdue.cs.tornado.serializer.ControlSerializer;
import edu.purdue.cs.tornado.serializer.DataObjectListSerializer;
import edu.purdue.cs.tornado.serializer.DataObjectSerializer;
import edu.purdue.cs.tornado.serializer.QuerySerializer;
import edu.purdue.cs.tornado.spouts.DummyTweetGenerator;
import edu.purdue.cs.tornado.spouts.TextualRangeQueryGenerator;

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
		
		
		//this needs to be added in configuration
		conf.registerSerialization(Query.class, QuerySerializer.class);
		conf.registerSerialization(DataObject.class, DataObjectSerializer.class);
		conf.registerSerialization(DataObjectList.class, DataObjectListSerializer.class);
		conf.registerSerialization(Control.class, ControlSerializer.class);
		
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("test", conf, builder.createTopology());
		Utils.sleep(10000);
		cluster.killTopology("test");
		cluster.shutdown();
	}
}
