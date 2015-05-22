package edu.purdue.cs.tornado.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import edu.purdue.cs.tornado.messages.Control;

public class ControlSerializer extends  com.esotericsoftware.kryo.Serializer<Control> {

	@Override
	public Control read(Kryo kryo, Input input, Class<Control> controlClass ) {
		Control control = new Control();
		control.setControlMessageType(input.readString());
		return control;
	}

	@Override
	public void write(Kryo kryo, Output output, Control control) {
		output.writeString(control.getControlMessageType());
		
	}

	

}
