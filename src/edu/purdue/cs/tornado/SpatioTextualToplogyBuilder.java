package edu.purdue.cs.tornado;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.topology.BoltDeclarer;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import edu.purdue.cs.tornado.evaluator.SpatioTextualBolt;
import edu.purdue.cs.tornado.helper.SpatioTextualConstants;
import edu.purdue.cs.tornado.index.DynamicSpatialIndexBolt;
import edu.purdue.cs.tornado.index.StaticSpatialIndexBolt;
/**
 * This class is an extension to the storm topology builder and it allows adding spatio- textual query processing abilities 
 * @author Ahmed Mahmood
 *
 */
public class SpatioTextualToplogyBuilder extends TopologyBuilder {
	private Map<String, IRichBolt> _spatioTexualIndexes = new HashMap<String, IRichBolt>();
	private Map<String, IRichBolt> _spatioTexualEvaluators = new HashMap<String, IRichBolt>();
	/**
	 * This method adds a static spatio-textual index processor to the tornado
	 */
	public SpatioTextualBoltDeclarer addStaticSpatioTextualProcessor(String id,
			Number parallelism_hint) {
		
		String indexId = id + SpatioTextualConstants.IndexIDExtension;
		String indexToBoltStreamId_Query = id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Query;
		String indexToBoltStreamId_Data = id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Data;
		String indexToBoltStreamId_Control = id
				+ SpatioTextualConstants.Index_Bolt_STreamIDExtension_Control;
		
		String boltToIndexStreamId_Query = id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Query;
		String boltToIndexStreamId_Data = id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Data;
		String boltToIndexStreamId_Control = id
				+ SpatioTextualConstants.Bolt_Index_STreamIDExtension_Control;
		
		String boltToBoltStreamId_Query = id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Query;
		String boltToBoltStreamId_Data = id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Data;
		String boltToBoltStreamId_Control = id
				+ SpatioTextualConstants.Bolt_Bolt_STreamIDExtension_Control;
		
		
		
		StaticSpatialIndexBolt staticSpatialIndexBolt = new StaticSpatialIndexBolt(id);
		SpatioTextualBolt spatioTextualBolt = new SpatioTextualBolt(id);
		_spatioTexualIndexes.put(id, staticSpatialIndexBolt);
		_spatioTexualEvaluators.put(id, spatioTextualBolt);
		
		
		BoltDeclarer indexDeclarer = this.setBolt(indexId,
				staticSpatialIndexBolt, parallelism_hint).directGrouping(id, boltToIndexStreamId_Query)
				.directGrouping(id, boltToIndexStreamId_Data).directGrouping(id, boltToIndexStreamId_Control);
				
				
				
				
				
				
		
		SpatioTextualIndexGetter spatioTextualIndexGetter = new SpatioTextualIndexGetter(indexDeclarer);
		this.setBolt(id, spatioTextualBolt, parallelism_hint).directGrouping(
				indexId, indexToBoltStreamId_Data).directGrouping(indexId, indexToBoltStreamId_Query).directGrouping(indexId, indexToBoltStreamId_Control)
				.directGrouping(id, boltToBoltStreamId_Query).directGrouping(id, boltToBoltStreamId_Data).directGrouping(id, boltToBoltStreamId_Control);
				
		
		return spatioTextualIndexGetter;
	}
	/**are you 
	 * This method adds a dynamic spatio-textual index processor to the tornado
	 */
	public SpatioTextualBoltDeclarer addDynamicSpatioTextualProcessor(String id,
			Number parallelism_hint) {
		
		return null;
	}
	protected class SpatioTextualIndexGetter implements SpatioTextualBoltDeclarer {
		private BoltDeclarer _boltDeclarer;
		public SpatioTextualIndexGetter(BoltDeclarer boltDeclarer){
			this._boltDeclarer =boltDeclarer;
		}
		/**
		 * Adding a persistent data input
		 * this adds to the configuration of the bolt that this source is persistent
		 */
		public SpatioTextualBoltDeclarer addPersistentSpatioTextualInput(String componentId,String streamId){
			this._boltDeclarer=this._boltDeclarer.fieldsGrouping(componentId, streamId,new Fields(SpatioTextualConstants.objectIdField));
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Data_Source+"_"+componentId+"_"+streamId, SpatioTextualConstants.Persistent);//this configurat
			return this;
		}
		public SpatioTextualBoltDeclarer addPersistentSpatioTextualInput(String componentId){
			this._boltDeclarer=this._boltDeclarer.fieldsGrouping(componentId,new Fields(SpatioTextualConstants.objectIdField));
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Data_Source+"_"+componentId, SpatioTextualConstants.Persistent);
			return this;		
		}
		public SpatioTextualBoltDeclarer addVolatileSpatioTextualInput(String componentId,String streamId){
			this._boltDeclarer=this._boltDeclarer.shuffleGrouping(componentId, streamId);
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Data_Source+"_"+componentId+"_"+streamId, SpatioTextualConstants.Volatile);
			return this;
			
		}
		public SpatioTextualBoltDeclarer addVolatileSpatioTextualInput(String componentId){
			this._boltDeclarer=this._boltDeclarer.shuffleGrouping(componentId);
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Data_Source+"_"+componentId, SpatioTextualConstants.Volatile);
			return this;
		}
		public SpatioTextualBoltDeclarer addPersisentQuerySource(String componentId,String streamId){
			this._boltDeclarer=this._boltDeclarer.fieldsGrouping(componentId, streamId,new Fields(SpatioTextualConstants.queryIdField));
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Query_Source+"_"+componentId+"_"+streamId, SpatioTextualConstants.Persistent);
			return this;
		}
		public SpatioTextualBoltDeclarer addPersisentQuerySource(String componentId){
			this._boltDeclarer=this._boltDeclarer.fieldsGrouping(componentId,new Fields(SpatioTextualConstants.queryIdField));
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Query_Source+"_"+componentId, SpatioTextualConstants.Persistent);
			return this;
		}
		public SpatioTextualBoltDeclarer addSnapShotQuerySource(String componentId,String streamId){
			this._boltDeclarer=this._boltDeclarer.shuffleGrouping(componentId, streamId);
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Query_Source+"_"+componentId+"_"+streamId, SpatioTextualConstants.Volatile);
			return this;
		}
		public SpatioTextualBoltDeclarer addSnapShotQuerySource(String componentId){
			this._boltDeclarer=this._boltDeclarer.shuffleGrouping(componentId);
			this._boltDeclarer.addConfiguration(SpatioTextualConstants.Query_Source+"_"+componentId, SpatioTextualConstants.Volatile);
			return this;
		}
	}

}
