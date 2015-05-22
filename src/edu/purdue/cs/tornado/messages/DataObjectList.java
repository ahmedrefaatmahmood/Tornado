package edu.purdue.cs.tornado.messages;

import java.util.ArrayList;

public class DataObjectList {
	private ArrayList<DataObject> dataObjects = new ArrayList<DataObject>();
	public void  addDataObject (DataObject dataObject){
		dataObjects.add(dataObject);
	}
	public ArrayList<DataObject> getDataObjects() {
		return dataObjects;
	}

	public void setDataObjects(ArrayList<DataObject> dataObjects) {
		this.dataObjects = dataObjects;
	}
}
