package com.moscue.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
/**
 * 持仓
 * @author Aaron
 *
 */
@Entity
public class Trade {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	private int buyOrSell;//买卖标记1买0卖
	private Integer count;
	private Double price;
	private Double currentPrice;
	private Double amount;//总金额
	private String tradeCode;//交易代码
	private String agencyCode;//委托代码
	private Date tradeTime;//交易时间
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "holder_id",referencedColumnName="id", unique = true)
	private StockHolder stockHolder;
	public StockHolder getStockHolder() {
		return stockHolder;
	}
	public void setStockHolder(StockHolder stockHolder) {
		this.stockHolder = stockHolder;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getBuyOrSell() {
		return buyOrSell;
	}
	public void setBuyOrSell(int buyOrSell) {
		this.buyOrSell = buyOrSell;
	}
	
}
