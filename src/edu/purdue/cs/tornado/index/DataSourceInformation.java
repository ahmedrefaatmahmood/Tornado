package edu.purdue.cs.tornado.index;

import java.util.HashMap;
import java.util.Map;

/**
 * This class keeps track of the data source information
 * 
 * @author Ahmed Mahmood
 *
 */
public class DataSourceInformation {
	private String dataSourceId; // component id of the data source
	private String dataSourceType; // Query, data source
	private Boolean persistent; // Query, data source
	private Map<Integer, Integer> lastBoltTasKInformation;

	public DataSourceInformation() {
		lastBoltTasKInformation = new HashMap<Integer, Integer>();
	}

	public DataSourceInformation(String dataSourceId,
			 String dataSourceType,
			Boolean persistent) {
		this.dataSourceId = dataSourceId;
		this.dataSourceType = dataSourceType;
		this.persistent = persistent;
		lastBoltTasKInformation = new HashMap<Integer, Integer>();
	}

	// **************** Getters and setters *******************
	public void setEvaluatorBoltTaskID(Integer oid, Integer boltTaskId) {
		lastBoltTasKInformation.put(oid, boltTaskId);
	}

	public Integer getEvaluatorBoltTaskID(Integer oid) {
		if (lastBoltTasKInformation.containsKey(oid))
			return lastBoltTasKInformation.get(oid);
		else
			return null;
	}

	public Boolean getPersistent() {
		return persistent;
	}

	public void setPersistent(Boolean persistent) {
		this.persistent = persistent;
	}

	public String getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}


	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public Map<Integer, Integer> getLastBoltTasKInformation() {
		
		return lastBoltTasKInformation;
	}

	public void setLastBoltTasKInformation(
			Map<Integer, Integer> lastBoltTasKInformation) {
		this.lastBoltTasKInformation = lastBoltTasKInformation;
	}

}
