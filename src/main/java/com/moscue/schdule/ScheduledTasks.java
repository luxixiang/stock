package com.moscue.schdule;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.moscue.entity.Balance;
import com.moscue.entity.Stock;
import com.moscue.entity.StockMeta;
import com.moscue.entity.StockOrder;
import com.moscue.entity.Trade;
import com.moscue.exec.OrderExector;
import com.moscue.service.StockService;
import com.moscue.service.TradeService;

//@Component
public class ScheduledTasks {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private TradeService tradeSerivce;
	
	@Autowired
	private OrderExector exector;
	
	/**
	 * 每隔2分钟执行定时任务采集股票信息
	 */
    @Scheduled(fixedRate = 1000 * 120)
    public void fetchStock(){
    	System.out.println("fetching...");
    	List<StockMeta> list = stockService.getAllStockMeta();
    	List<String> stockIds = new ArrayList<String>();
    	for(StockMeta meta : list) {
    		stockIds.add(meta.getCode());
    	}
    	
    	Map<String, Stock> stockMap = stockService.getStockInfo(stockIds);
    	for (Entry<String, Stock> entity : stockMap.entrySet()) {
    		final Stock stock = entity.getValue();
    		stockService.save(stock);
    		CalculateTradeTask task = new CalculateTradeTask(stockService, tradeSerivce, stock);
    		task.run();
    	}
    }
    
    class CalculateTradeTask implements Runnable{
    	
    	private StockService stockService;
    	
    	private TradeService tradeSerivce;
    	
    	private Stock stock;
    	
    	public CalculateTradeTask(StockService stockService, TradeService tradeSerivce, Stock stock) {
    		this.setStockService(stockService);
    		this.setTradeSerivce(tradeSerivce);
    		this.stock = stock;
    	}
    	
    	/**
    	 * 循环遍历未完成订单，执行交易
    	 */
		@Override
		public void run() {
			List<StockOrder> orders = tradeSerivce.getStockOrderByCodeAndStatus(stock.getCode(), 1);
			for (StockOrder order : orders) {
				exector.exector(order, stock);
			}
		}

		public TradeService getTradeSerivce() {
			return tradeSerivce;
		}

		public void setTradeSerivce(TradeService tradeSerivce) {
			this.tradeSerivce = tradeSerivce;
		}

		public StockService getStockService() {
			return stockService;
		}

		public void setStockService(StockService stockService) {
			this.stockService = stockService;
		}

		public Stock getStock() {
			return stock;
		}

		public void setStock(Stock stock) {
			this.stock = stock;
		}
    	
    }
}
