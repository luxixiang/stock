package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.Stock;

public interface StockRepository extends CrudRepository<Stock, Long> {
	List<Stock> findByCode(String code);
}
