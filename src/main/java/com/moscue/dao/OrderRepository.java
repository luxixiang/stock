package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.StockOrder;

public interface OrderRepository extends CrudRepository<StockOrder, Long> {
	List<StockOrder> findByCode(String code);
}
