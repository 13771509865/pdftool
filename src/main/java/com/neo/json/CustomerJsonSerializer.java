package com.neo.json;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.annotation.PostConstruct;

public class CustomerJsonSerializer {

	static final String DYNC_INCLUDE = "DYNC_INCLUDE";
	static final String DYNC_FILTER = "DYNC_FILTER";
	ObjectMapper mapper = new ObjectMapper();
	
	public CustomerJsonSerializer() {
		this.mapper =  getObjectMapper();
	}

	@JsonFilter(DYNC_FILTER)
	interface DynamicFilter {
	}

	@JsonFilter(DYNC_INCLUDE)
	interface DynamicInclude {
	}

	@PostConstruct
	public ObjectMapper getObjectMapper() {
		SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL).registerModule(simpleModule);
    }

	/**
	 * @param clazz
	 *            target type
	 * @param include
	 *            include fields
	 * @param filter
	 *            filter fields
	 */
	public void filter(Class<?> clazz, String include, String filter) {
		if (clazz == null)
			return;
		if (include != null && include.length() > 0) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(
					DYNC_INCLUDE, SimpleBeanPropertyFilter
							.filterOutAllExcept(include.split(","))));
			mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true); 
			mapper.addMixIn(clazz, DynamicInclude.class);
		} else if (filter != null && filter.length() > 0) {
			mapper.setFilterProvider(new SimpleFilterProvider().addFilter(
					DYNC_FILTER, SimpleBeanPropertyFilter
							.serializeAllExcept(filter.split(","))));
			mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true); 
			mapper.addMixIn(clazz, DynamicFilter.class);
		}
	}

	public String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
}
