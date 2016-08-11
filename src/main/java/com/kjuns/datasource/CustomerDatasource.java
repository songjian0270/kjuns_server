package com.kjuns.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.kjuns.util.DataSourceKeyHolder;

public class CustomerDatasource extends AbstractRoutingDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger("CustomerDatasource");

	@Override
	protected Object determineCurrentLookupKey() {
		logger.info("DataSourceKeyHolder.getDataSourceKey():"+DataSourceKeyHolder.getDataSourceKey());
		return DataSourceKeyHolder.getDataSourceKey();
	}
	
}
