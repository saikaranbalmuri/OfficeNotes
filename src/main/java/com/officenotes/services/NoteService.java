package com.officenotes.services;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.officenotes.model.NoteRequest;

public interface NoteService<T> {
	
	public T service(NoteRequest request) throws JsonGenerationException, JsonMappingException, IOException;


}
