package com.moscue.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * 持仓
 * @author Aaron
 *
 */
@Entity
public class Position {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	private Integer count;
	private Integer countCanBeBought;
	private Double costPrice;
	private Double currentPrice;
	private Double marketValue;
	private Double profitAndLoss;
	private Double profitAndLossRate;
	private Date exitTime;
	private Date addTime;
    private long holderId;
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
	public Integer getCountCanBeBought() {
		return countCanBeBought;
	}
	public void setCountCanBeBought(Integer countCanBeBought) {
		this.countCanBeBought = countCanBeBought;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Double getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(Double marketValue) {
		this.marketValue = marketValue;
	}
	public Double getProfitAndLoss() {
		return profitAndLoss;
	}
	public void setProfitAndLoss(Double profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}
	public Double getProfitAndLossRate() {
		return profitAndLossRate;
	}
	public void setProfitAndLossRate(Double profitAndLossRate) {
		this.profitAndLossRate = profitAndLossRate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getExitTime() {
		return exitTime;
	}
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}
	public long getHolderId() {
		return holderId;
	}
	public void setHolderId(long holderId) {
		this.holderId = holderId;
	}
}
