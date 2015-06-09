/**
 * 
 */
package com.p.controller.converters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.p.model.PropietarioPartido;

@Component
/**
 * @author David
 *
 */
public class PropietarioPartidoConverter extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6133000761104633611L;
	
	public PropietarioPartidoConverter(){
		SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null, null, null));
        module.addSerializer(PropietarioPartido.class, new PropietarioPartidoSerializer());
        module.addDeserializer(PropietarioPartido.class, new PropietarioPartidoDeserializer());
        // Add more here ...
        registerModule(module);
	}
	
	public class PropietarioPartidoSerializer extends StdSerializer<PropietarioPartido> {

	    /**
		 * 
		 */
		private static final long serialVersionUID = -659216852285608186L;

		public PropietarioPartidoSerializer() {
	        super(PropietarioPartido.class);
	    }

	    @Override
	    public void serialize(PropietarioPartido prop, JsonGenerator json,
	            SerializerProvider provider) throws IOException,
	            JsonGenerationException {
	        json.writeString(prop.getId().toString());
	    }

	}
	
	public class PropietarioPartidoDeserializer extends StdDeserializer<PropietarioPartido> {

	    /**
		 * 
		 */
		private static final long serialVersionUID = -2939000607632910917L;

		public PropietarioPartidoDeserializer() {
	        super(PropietarioPartido.class);
	    }

	    @Override
	    public PropietarioPartido deserialize(JsonParser json, DeserializationContext context)
	            throws IOException, JsonProcessingException {
	    	Integer id = json.getIntValue();
	    	PropietarioPartido prop = new PropietarioPartido();
	    	prop.setId(id);
	        return prop;
	    }

	}
	
	

}
