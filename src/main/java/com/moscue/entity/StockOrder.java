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
 * 订单
 * @author Aaron
 *
 */
@Entity
public class StockOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	private Integer count;
	private Date agencyTime;
	private int buyOrSell;
	private String agencyCode;//委托编号
	private Double agencyPrice;
	private Double tradePrice;
	private Integer tradeCount;
	private int agencyType;
	private Date addTime;
	private int status;
	//@ManyToOne(cascade = CascadeType.ALL, optional = false)//持久化层有缺陷
    //@JoinColumn(name = "holder_id",referencedColumnName="id", unique = true)
	private long holderId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getAgencyTime() {
		return agencyTime;
	}
	public void setAgencyTime(Date agencyTime) {
		this.agencyTime = agencyTime;
	}
	public Integer getBuyOrSell() {
		return buyOrSell;
	}
	public void setBuyOrSell(Integer buyOrSell) {
		this.buyOrSell = buyOrSell;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public Double getAgencyPrice() {
		return agencyPrice;
	}
	public void setAgencyPrice(Double agencyPrice) {
		this.agencyPrice = agencyPrice;
	}
	public Double getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(Double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public int getAgencyType() {
		return agencyType;
	}
	public void setAgencyType(int agencyType) {
		this.agencyType = agencyType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getHolderId() {
		return holderId;
	}
	public void setHolderId(long holderId) {
		this.holderId = holderId;
	}
	public Integer getTradeCount() {
		return tradeCount;
	}
	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}
}
