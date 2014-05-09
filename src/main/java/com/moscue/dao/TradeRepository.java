package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Trade;

public interface TradeRepository extends CrudRepository<Trade, Long> {
	List<Trade> findByCode(String code);

	List<Trade> findTradeByHolderId(long id);
}
