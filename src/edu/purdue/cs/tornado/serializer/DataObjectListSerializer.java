package edu.purdue.cs.tornado.serializer;

import java.util.ArrayList;
import java.util.function.IntPredicate;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import edu.purdue.cs.tornado.messages.DataObject;
import edu.purdue.cs.tornado.messages.DataObjectList;

public class DataObjectListSerializer extends com.esotericsoftware.kryo.Serializer<DataObjectList>{

	@Override
	public DataObjectList read(Kryo kryo, Input input, Class<DataObjectList> dataObjectListClass) {
		DataObjectList dataObjectList= new DataObjectList();
		ArrayList<DataObject> dataObjects = new ArrayList<DataObject>();
		int size = input.readInt();
		for (int i =0 ;i< size;i++){
			DataObject dataObject = new DataObject();
			dataObject=kryo.readObject(input, DataObject.class);
			dataObjects.add(dataObject);
		}
			
		dataObjectList.setDataObjects(dataObjects);
		return dataObjectList;
	}

	@Override
	public void write(Kryo kryo, Output output, DataObjectList dataObjectList) {
		int size =0;
		if (dataObjectList.getDataObjects()!=null)
			size =dataObjectList.getDataObjects().size();
		output.writeInt(size);
		for(int i =0;i<size;i++)
			kryo.writeObject(output, dataObjectList.getDataObjects().get(i));
		
	}

}
