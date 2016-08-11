package com.kjuns.util;

import java.util.Collection;
import java.util.List;

public class CollectionUtils {
	
	@SuppressWarnings("rawtypes")
	public static final Collection NULL_COLLECTION = new NullCollection();

	@SuppressWarnings("unchecked")
	public static final <T> Collection<T> nullCollection() {
		return (List<T>) NULL_COLLECTION;
	}
	
}