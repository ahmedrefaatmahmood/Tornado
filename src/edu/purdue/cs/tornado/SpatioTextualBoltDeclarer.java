package edu.purdue.cs.tornado;


public interface SpatioTextualBoltDeclarer{
	public SpatioTextualBoltDeclarer addPersistentSpatioTextualInput(String componentId,String streamID);
	public SpatioTextualBoltDeclarer addPersistentSpatioTextualInput(String componentId);
	public SpatioTextualBoltDeclarer addVolatileSpatioTextualInput(String componentId,String streamID);
	public SpatioTextualBoltDeclarer addVolatileSpatioTextualInput(String componentId);
	public SpatioTextualBoltDeclarer addPersisentQuerySource(String componentId,String streamID);
	public SpatioTextualBoltDeclarer addPersisentQuerySource(String componentId);
	public SpatioTextualBoltDeclarer addSnapShotQuerySource(String componentId,String streamID);
	public SpatioTextualBoltDeclarer addSnapShotQuerySource(String componentId);
}
