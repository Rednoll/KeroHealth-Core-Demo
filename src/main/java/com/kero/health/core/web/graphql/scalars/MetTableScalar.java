package com.kero.health.core.web.graphql.scalars;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kero.health.core.domain.sport.additionals.MetTable;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class MetTableScalar extends GraphQLScalarType {
	
	public MetTableScalar(@Autowired ObjectMapper mapper) {
		super("MetTable", "MetTable", new Coercing<MetTable, String>() {

			@Override
			public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
				
				MetTable table = (MetTable) dataFetcherResult;
				
				try {
					
					return mapper.writeValueAsString(table.getMets());
				}
				catch(JsonProcessingException e) {
				
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			public MetTable parseValue(Object input) throws CoercingParseValueException {
				
				String rawData = (String) input;
						
				TreeMap<Integer, Integer> mets = null;
				
				try {
					
					mets = mapper.readValue(rawData, mapper.getTypeFactory().constructMapLikeType(TreeMap.class, Integer.class, Integer.class));
				}
				catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}
			
				return new MetTable(mets);
			}

			@Override
			public MetTable parseLiteral(Object input) throws CoercingParseLiteralException {
				
				String rawData = (String) input;
				
				TreeMap<Integer, Integer> mets = null;
				
				try {
					
					mets = mapper.readValue(rawData, mapper.getTypeFactory().constructMapLikeType(TreeMap.class, Integer.class, Integer.class));
				}
				catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}
			
				return new MetTable(mets);
			}
			
		});
	}

}
