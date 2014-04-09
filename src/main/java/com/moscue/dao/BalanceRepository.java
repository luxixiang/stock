package com.moscue.dao;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Balance;
import com.moscue.entity.StockHolder;

public interface BalanceRepository extends CrudRepository<Balance, Long> {
	public Balance getBalanceByStockHolder(StockHolder holder);
}
