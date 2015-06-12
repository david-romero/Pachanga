package com.p.controller.converters;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.p.model.User;

public class ObjectCodec extends com.fasterxml.jackson.core.ObjectCodec{

	@Override
	public <T> T readValue(JsonParser jp, Class<T> valueType)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T readValue(JsonParser jp, TypeReference<?> valueTypeRef)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T readValue(JsonParser jp, ResolvedType valueType)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Iterator<T> readValues(JsonParser jp, Class<T> valueType)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Iterator<T> readValues(JsonParser jp,
			TypeReference<?> valueTypeRef) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Iterator<T> readValues(JsonParser jp, ResolvedType valueType)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeValue(JsonGenerator jgen, Object value)
			throws IOException, JsonProcessingException {
		if ( value instanceof User ){
			User usr = (User) value;
			jgen.writeStartObject();
			jgen.writeNumberField("id", usr.getId());
			jgen.writeStringField("email", usr.getEmail());
			jgen.writeStringField("firstName", usr.getFirstName());
			jgen.writeStringField("lastName", usr.getLastName());
			jgen.writeEndObject();
		}
	}

	@Override
	public <T extends TreeNode> T readTree(JsonParser jp) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeTree(JsonGenerator jg, TreeNode tree) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TreeNode createObjectNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeNode createArrayNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonParser treeAsTokens(TreeNode n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T treeToValue(TreeNode n, Class<T> valueType)
			throws JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
