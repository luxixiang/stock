package com.moscue.fetcher;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;

/**
 * 
 * @author CHEN
 */
public class PageFetcherFactory extends BasePoolableObjectFactory<PageFetcher> implements PoolableObjectFactory<PageFetcher> {
	@Override
	public PageFetcher makeObject() throws Exception {
		PageFetcher fetcher = new PageFetcher();
		return fetcher;
	}
}