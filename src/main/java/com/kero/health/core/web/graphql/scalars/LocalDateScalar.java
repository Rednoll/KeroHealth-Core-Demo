package com.kero.health.core.web.graphql.scalars;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import graphql.language.IntValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Component
public class LocalDateScalar extends GraphQLScalarType {

	public LocalDateScalar() {
		super("LocalDate", "LocalDate", new Coercing<LocalDate, Long>() {

			@Override
			public Long serialize(Object dataFetcherResult) throws CoercingSerializeException {
				
				LocalDate date = (LocalDate) dataFetcherResult;
				
				return date.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
			}

			@Override
			public LocalDate parseValue(Object input) throws CoercingParseValueException {
				
				IntValue epochSecond = (IntValue) input;
						
				return LocalDateTime.ofEpochSecond(epochSecond.getValue().longValue(), 0, ZoneOffset.UTC).toLocalDate();
			}

			@Override
			public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
				
				IntValue epochSecond = (IntValue) input;
				
				return LocalDateTime.ofEpochSecond(epochSecond.getValue().longValue(), 0, ZoneOffset.UTC).toLocalDate();
			}
			
		});
	}

}
