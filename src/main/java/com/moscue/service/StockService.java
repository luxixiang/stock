package com.moscue.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moscue.dao.StockMetaRepository;
import com.moscue.dao.StockRepository;
import com.moscue.entity.Stock;
import com.moscue.entity.StockMeta;
import com.moscue.fetcher.FetcherConfig;
import com.moscue.fetcher.PageFetcher;
import com.moscue.parser.StockDataParser;
import com.moscue.utils.CharsetUtils;
/**
 * 获取和更新股票信息
 * @author Aaron
 *
 */
@Component
public class StockService {
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	StockMetaRepository stockMetaRepository;
	
    PageFetcher fetcher = new PageFetcher(new FetcherConfig());
	
	public void save(Stock stock){
		stockRepository.save(stock);
	}
	
	public Map<String, Stock> getStockInfo(List<String> stockIds){
		Map<String, Stock> map = new HashMap<String, Stock>();
		String ids = StringUtils.join(stockIds, ",");
        //sh600133,sh601005,sh601006
        com.moscue.fetcher.PageFetchResult result = fetcher.get("http://hq.sinajs.cn/list="+ids);
        String ret = CharsetUtils.getString(result.getContentBytes(), "GBK");
        String[] arr = ret.split(";");
        if (ret.length() > 0) {
        	int i = 0;
        	for(String data : arr) {
        		i++;
        		if (i == arr.length) {
        			break;
        		}
        		Stock stock = StockDataParser.parser(data);
        		map.put(stock.getCode(), stock);
        	}
        }
		return map;
	}
	
	public Stock getStockInfo(String stockId){
		List<String> stockIds = new ArrayList<String>();
		stockIds.add(stockId);
		Map<String, Stock> map = getStockInfo(stockIds);
		return map.get(stockId);
	}

	public List<Stock> getHistStock(String code) {
		List<Stock> list = stockRepository.findByCode(code);
		return list == null? new ArrayList<Stock>() : list;
	}
	
	public List<StockMeta> getAllStockMeta() {
		return (List<StockMeta>) stockMetaRepository.findAll();
	}
	
}
