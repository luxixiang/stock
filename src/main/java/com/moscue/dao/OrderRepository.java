package com.moscue.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moscue.entity.StockOrder;

public interface OrderRepository extends CrudRepository<StockOrder, Long> {
	List<StockOrder> findByCode(String code);
	
	List<StockOrder> findByStatusAndHolderId(int status, long holderId);

	StockOrder findOrderByAgencyCode(String agencyCode);

	List<StockOrder> findStockOrderByHolderIdAndStatus(long holderId,
			int status);

	List<StockOrder> findStockOrderByCodeAndStatus(String code, int status);
}
