package com.moscue.exec;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moscue.entity.Balance;
import com.moscue.entity.Stock;
import com.moscue.entity.StockOrder;
import com.moscue.entity.Trade;
import com.moscue.service.StockHolderService;
import com.moscue.service.TradeService;

@Component
public class OrderExector {

	@Autowired
	private TradeService tradeSerivce;
	
	@Autowired
	private StockHolderService stockHolderService;

	/**
	 * 执行交易的方法
	 * @param order
	 * @param stock
	 * @param tradeType
	 */
	public boolean exector(StockOrder order, Stock stock) {
		Balance balance = tradeSerivce
				.getBalanceByHolderId(order.getHolderId());
		if (balance == null) {
			System.out.println("交易失败：获取不到账户信息");
			return false;
		}
		
		if ((order.getBuyOrSell() == 1 && stock.getBuy1Price() >= order.getAgencyPrice())||(order.getBuyOrSell() == 2 && stock.getSell1Price() <= order.getAgencyPrice())) {
			order.setStatus(2);
			order.setTradeCount(order.getCount());
			Trade trade = new Trade();
			if (order.getAgencyType() == 1) {
				order.setTradePrice(stock.getBuy1Price());
				trade.setCurrentPrice(stock.getBuy1Price());
				trade.setCount(order.getTradeCount());
			} else {
				order.setTradePrice(stock.getSell1Price());
				trade.setCurrentPrice(stock.getSell1Price());
				trade.setCount(-order.getTradeCount());
			}
			trade.setPrice(order.getTradePrice());
			trade.setAgencyCode(order.getAgencyCode());
			trade.setAmount(order.getTradePrice() * order.getTradeCount());
			trade.setBuyOrSell(order.getBuyOrSell());
			trade.setCode(order.getCode());
			trade.setCurrentPrice(stock.getBuy1Price());
			
			trade.setHolderId(order.getHolderId());
			trade.setName(stock.getName());
			trade.setStatus(1);
			trade.setTradeTime(new Date());
			trade.setTradeCode(order.getHolderId() + "-"
					+ System.currentTimeMillis());
			saveTradeAndBalance(trade, balance);
			tradeSerivce.saveOrder(order);
			return true;
		}
		return false;
	}
	/**
	 * 保存交易和账户信息
	 * @param trade
	 * @param balance
	 */
	private void saveTradeAndBalance(Trade trade, Balance balance){
		
		double profitAndLoss = tradeSerivce.savePosition(trade);// 保存仓位信息
		trade.setProfitAndLoss(profitAndLoss);
		tradeSerivce.saveTrade(trade);
		balance.setRemain(balance.getRemain() + profitAndLoss);
		stockHolderService.saveBalance(balance);
	}

}
