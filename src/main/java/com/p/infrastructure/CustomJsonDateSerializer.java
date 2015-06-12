package com.p.infrastructure;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date fecha, JsonGenerator jgen,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		jgen.writeString(format.format(fecha));
	}

}
