package edu.purdue.cs.tornado.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import edu.purdue.cs.tornado.messages.DataObject;

public class DataObjectSerializer extends  com.esotericsoftware.kryo.Serializer<DataObject>{



	@Override
	public DataObject read(Kryo kryo, Input input, Class<DataObject> dataObjectClass) {
		DataObject dataObject = new DataObject();
		
		dataObject.setObjectId(input.readInt());
		dataObject.setObjectXCoord(input.readDouble());
		dataObject.setObjectYCoord(input.readDouble());
		dataObject.setObjectText(input.readString());
		dataObject.setTimeStamp(input.readLong());
		return dataObject;
	}

	@Override
	public void write(Kryo kryo, Output output, DataObject dataObject) {
		output.writeInt(dataObject.getObjectId());
		output.writeDouble(dataObject.getObjectXCoord());
		output.writeDouble(dataObject.getObjectYCoord());
		output.writeString(dataObject.getObjectText());
		output.writeLong(dataObject.getTimeStamp());
		
	}

}
