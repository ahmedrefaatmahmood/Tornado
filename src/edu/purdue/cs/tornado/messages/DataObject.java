package edu.purdue.cs.tornado.messages;


public class DataObject {
	private Integer objectId;
	private Double objectXCoord;
	private Double objectYCoord;
	private String objectText;
	private Long timeStamp;
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public Double getObjectXCoord() {
		return objectXCoord;
	}
	public void setObjectXCoord(Double objectXCoord) {
		this.objectXCoord = objectXCoord;
	}
	public Double getObjectYCoord() {
		return objectYCoord;
	}
	public void setObjectYCoord(Double objectYCoord) {
		this.objectYCoord = objectYCoord;
	}
	public String getObjectText() {
		return objectText;
	}
	public void setObjectText(String objectText) {
		this.objectText = objectText;
	}
	public Long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	} 
}
