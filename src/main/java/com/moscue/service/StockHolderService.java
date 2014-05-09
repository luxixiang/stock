package com.moscue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moscue.dao.BalanceRepository;
import com.moscue.dao.StockHolderRepository;
import com.moscue.entity.Balance;
import com.moscue.entity.StockHolder;

@Component
public class StockHolderService {
	@Autowired
	StockHolderRepository stockHolderRepository;
	
	@Autowired
	BalanceRepository balanceRepository;
	
	public StockHolder findByCode(String code){
		return stockHolderRepository.findByCode(code);
	}
	
	public StockHolder saveHolder(StockHolder holder){
		return stockHolderRepository.save(holder);
	}
	
	public Balance getBalanceByHolderId(long holderId){
		return balanceRepository.getBalanceByHolderId(holderId);
	}
	
	public void saveBalance(Balance balance){
		balanceRepository.save(balance);
	}
}
