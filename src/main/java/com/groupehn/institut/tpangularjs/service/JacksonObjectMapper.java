package com.groupehn.institut.tpangularjs.service;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * {@link ObjectMapper} pour Jackson sp�cifiant qu�il faut utiliser des
 * dates ISO-8601.
 */
public class JacksonObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -5801889011828582921L;

	public JacksonObjectMapper() {
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
	}

}
