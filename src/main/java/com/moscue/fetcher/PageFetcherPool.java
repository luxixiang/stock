package com.moscue.fetcher;

import org.apache.commons.pool.impl.GenericObjectPool;

public class PageFetcherPool extends GenericObjectPool<PageFetcher> {

	public PageFetcherPool(PageFetcherFactory factory) {
		super(factory);

	}

	@Override
	public synchronized PageFetcher borrowObject() {
		try {
			return super.borrowObject();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public synchronized void returnObject(PageFetcher fetcher) {
		try {
			super.returnObject(fetcher);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}