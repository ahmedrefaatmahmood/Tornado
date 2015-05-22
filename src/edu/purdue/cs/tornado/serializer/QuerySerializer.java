package edu.purdue.cs.tornado.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import edu.purdue.cs.tornado.messages.Query;

public class QuerySerializer extends  com.esotericsoftware.kryo.Serializer<Query> {

	@Override
	public Query read(Kryo kryo, Input input, Class<Query> queryClass) {
		Query query = new Query();
		query.setQueryType(input.readString());
		query.setQueryId(input.readInt());
		query.setFocalXCoord(input.readDouble());
		query.setFocalYCoord(input.readDouble());
		query.setK(input.readInt());
		query.setQueryText(input.readString());
		query.setTimeStamp(input.readLong());
		query.setQueryXMin(input.readDouble());
		query.setQueryYMin(input.readDouble());
		query.setQueryXMax(input.readDouble());
		query.setQueryYMax(input.readDouble());
		return query;
	}

	@Override
	public void write(Kryo kryo, Output output, Query query) {
		output.writeString(query.getQueryType());
		output.writeInt(query.getQueryId());
		output.writeDouble(query.getFocalXCoord());
		output.writeDouble(query.getFocalYCoord());
		output.writeInt(query.getK());
		output.writeString(query.getQueryText());
		output.writeLong(query.getTimeStamp());
		output.writeDouble(query.getQueryXMin());
		output.writeDouble(query.getQueryYMin());
		output.writeDouble(query.getQueryXMax());
		output.writeDouble(query.getQueryYMax());
		
	}

}
