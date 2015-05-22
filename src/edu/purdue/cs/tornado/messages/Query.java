package edu.purdue.cs.tornado.messages;


public class Query {
	private  String  queryType;
	private  Integer queryId;
	private  Double  focalXCoord;
	private  Double  focalYCoord;
	private  Integer  k;
	private  String  queryText;
	private  Long    timeStamp;
	private  Double  queryXMin;
	private  Double  queryYMin;
	private  Double  queryXMax;
	private  Double  queryYMax;
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Integer getQueryId() {
		return queryId;
	}
	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}
	
	public Double getFocalXCoord() {
		return focalXCoord;
	}
	public void setFocalXCoord(Double focalXCoord) {
		this.focalXCoord = focalXCoord;
	}
	public Double getFocalYCoord() {
		return focalYCoord;
	}
	public void setFocalYCoord(Double focalYCoord) {
		this.focalYCoord = focalYCoord;
	}
	public Integer getK() {
		return k;
	}
	public void setK(Integer k) {
		this.k = k;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Double getQueryXMin() {
		return queryXMin;
	}
	public void setQueryXMin(Double queryXMin) {
		this.queryXMin = queryXMin;
	}
	public Double getQueryYMin() {
		return queryYMin;
	}
	public void setQueryYMin(Double queryYMin) {
		this.queryYMin = queryYMin;
	}
	public Double getQueryXMax() {
		return queryXMax;
	}
	public void setQueryXMax(Double queryXMax) {
		this.queryXMax = queryXMax;
	}
	public Double getQueryYMax() {
		return queryYMax;
	}
	public void setQueryYMax(Double queryYMax) {
		this.queryYMax = queryYMax;
	}
	
}
