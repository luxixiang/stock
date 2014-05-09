package com.moscue.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moscue.dao.BalanceRepository;
import com.moscue.dao.OrderRepository;
import com.moscue.dao.PositionRepository;
import com.moscue.dao.TradeRepository;
import com.moscue.entity.Balance;
import com.moscue.entity.Position;
import com.moscue.entity.StockHolder;
import com.moscue.entity.StockOrder;
import com.moscue.entity.Trade;

/**
 * 交易与查询
 * 
 * @author Aaron
 * 
 */
@Component
public class TradeService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	private BalanceRepository balanceRepository;

	@Autowired
	private PositionRepository positionRepository;

	public Position getPositionByHolderIdAndCode(long holderId, String code) {
		return positionRepository.findByHolderIdAndCode(holderId, code);
	}

	public List<Position> getPositionByHolderId(long holderId) {
		return positionRepository.findByHolderId(holderId);
	}

	/**
	 * 计算盈亏、保存仓位信息
	 * 
	 * @param trade
	 * @return 返回本次交易获利或损失
	 */
	public Double savePosition(Trade trade) {
		Double profitAndLoss = 0.0;
		Position position = positionRepository.findByHolderIdAndCode(
				trade.getHolderId(), trade.getCode());
		// 新建仓的股票
		if (position == null) {
			position = new Position();
			position.setAddTime(trade.getTradeTime());
			position.setCode(trade.getCode());
			position.setName(trade.getName());
			position.setCostPrice(trade.getPrice());
			position.setCurrentPrice(trade.getPrice());
			position.setCount(trade.getCount());
			position.setHolderId(trade.getHolderId());
			position.setMarketValue(trade.getCurrentPrice() * trade.getCount());
			position.setProfitAndLoss(0.0);
			position.setProfitAndLossRate(0.0);
		}

		double oldAmount = 0.0;
		double newAmount = trade.getAmount();
		double avgPrice = 0.0;
		int newCount = 0;
		// 买入
		if (trade.getBuyOrSell() == 1) {
			newCount = position.getCount() + trade.getCount();
			//旧股票成本
			oldAmount = position.getCostPrice() * position.getCount();
			//平均成本价 = 旧股票成本 + 新股票总额)/持有股票总数
			avgPrice = (oldAmount + newAmount) / newCount;
			// 买入时扣除所有支出
			profitAndLoss = -trade.getAmount();
			position.setProfitAndLoss(0.0);
		}
		// 卖出获益=交易数量*交易价格-持仓平均成本价格*交易数量
		if (trade.getBuyOrSell() == 2) {
			newCount = position.getCount() - trade.getCount();
			avgPrice = position.getCostPrice();
			//交易获利=交易额-交易成本
			double diffVal = trade.getPrice() * trade.getCount()
					-Math.abs(trade.getCount()) * position.getCostPrice();
			
			position.setProfitAndLoss(diffVal);
			profitAndLoss = trade.getCount() * position.getCostPrice();
		}
		
		position.setMarketValue(trade.getCurrentPrice() * Math.abs(newCount));
		position.setCostPrice(avgPrice);
		position.setCount(newCount);
		positionRepository.save(position);
		return profitAndLoss;
	}

	public List<Trade> getTradesByCode(String code) {
		return tradeRepository.findByCode(code);
	}

	public void saveOrder(StockOrder order) {
		orderRepository.save(order);
	}

	public void saveTrade(Trade trade) {
		tradeRepository.save(trade);
	}

	public List<Trade> getAllTrade(StockHolder holder, int tag) {
		List<Trade> ret = new ArrayList<Trade>();
		Iterable<Trade> it = tradeRepository.findAll();
		if (it != null) {
			ret.addAll((Collection<? extends Trade>) it);
		}
		return ret;
	}

	public List<StockOrder> getOrders() {
		return (List<StockOrder>) orderRepository.findAll();
	}

	public List<StockOrder> getOrderByStatusAndHolderId(int status,
			long holderId) {
		return orderRepository.findByStatusAndHolderId(status, holderId);
	}

	public List<StockOrder> getOrderByCode(String code) {
		return orderRepository.findByCode(code);
	}

	public List<StockOrder> getStockOrderByHolderIdAndStatus(long holderId,
			int status) {
		return orderRepository.findStockOrderByHolderIdAndStatus(holderId,
				status);
	}

	public List<StockOrder> getStockOrderByCodeAndStatus(String code, int status) {
		return orderRepository.findStockOrderByCodeAndStatus(code, status);
	}

	public Balance getBalanceByHolderId(long holderId) {
		return balanceRepository.getBalanceByHolderId(holderId);
	}

	public void saveBalance(Balance balance) {
		balanceRepository.save(balance);
	}

	public StockOrder getOrderByAgencyCode(String agencyCode) {
		return orderRepository.findOrderByAgencyCode(agencyCode);
	}

	public void saveStockOrder(StockOrder stockOrder) {
		orderRepository.save(stockOrder);
	}

	public List<Trade> getTradesByHolderId(long id) {
		return tradeRepository.findTradeByHolderId(id);
	}
}
