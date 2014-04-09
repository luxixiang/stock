package com.moscue.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Aaron on 14-3-29.
 */
@Entity
public class Stock implements Serializable{
    private static final long serialVersionUID = 5647379479945790282L;
    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String name;//股票名称
    private String code;//股票代码
    private double openPrice;//今日开盘价（元）
    private double closePrice;//昨日收盘价（元）
    private double currentPrice;//当前价格（元）
    private double maxPrice;//今日最高价（元）
    private double minPrice;//今日最低价（元）
    private double buy1;//买一（元）
    private double sell1;//卖一（元）
    private int tradingVolume;//成交量（股）
    private double tradingAmount;//成交金额（元）
    private int buy1Declare;//买一申报量（股）
    private double buy1Price;//买一出价（元）
    private int buy2Declare;//买二申报量（股）
    private double buy2Price;//买二出价（元）
    private int buy3Declare;//买三申报量（股）
    private double buy3Price;//买三出价（元）
    private int buy4Declare;//买四申报量（股）
    private double buy4Price;//买四出价（元）
    private int buy5Declare;//买五申报量（股）
    private double buy5Price;//买五出价（元）
    private int sell1Declare;//卖一申报量（股）
    private double sell1Price;//卖一报价（元）
    private int sell2Declare;//卖二申报量（股）
    private double sell2Price;//卖二报价（元）
    private int sell3Declare;//卖三申报量（股）
    private double sell3Price;//卖三报价（元）
    private int sell4Declare;//卖四申报量（股）
    private double sell4Price;//卖四报价（元）
    private int sell5Declare;//卖五申报量（股）
    private double sell5Price;//卖五报价（元）
    private String date;//2013-12-18 日期
    private String time;//13:56:49,00 时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getBuy1() {
        return buy1;
    }

    public void setBuy1(double buy1) {
        this.buy1 = buy1;
    }

    public double getSell1() {
        return sell1;
    }

    public void setSell1(double sell1) {
        this.sell1 = sell1;
    }

    public int getTradingVolume() {
        return tradingVolume;
    }

    public void setTradingVolume(int tradingVolume) {
        this.tradingVolume = tradingVolume;
    }

    public double getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(double tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public int getBuy1Declare() {
        return buy1Declare;
    }

    public void setBuy1Declare(int buy1Declare) {
        this.buy1Declare = buy1Declare;
    }

    public double getBuy1Price() {
        return buy1Price;
    }

    public void setBuy1Price(double buy1Price) {
        this.buy1Price = buy1Price;
    }

    public int getBuy2Declare() {
        return buy2Declare;
    }

    public void setBuy2Declare(int buy2Declare) {
        this.buy2Declare = buy2Declare;
    }

    public double getBuy2Price() {
        return buy2Price;
    }

    public void setBuy2Price(double buy2Price) {
        this.buy2Price = buy2Price;
    }

    public int getBuy3Declare() {
        return buy3Declare;
    }

    public void setBuy3Declare(int buy3Declare) {
        this.buy3Declare = buy3Declare;
    }

    public double getBuy3Price() {
        return buy3Price;
    }

    public void setBuy3Price(double buy3Price) {
        this.buy3Price = buy3Price;
    }

    public int getBuy4Declare() {
        return buy4Declare;
    }

    public void setBuy4Declare(int buy4Declare) {
        this.buy4Declare = buy4Declare;
    }

    public double getBuy4Price() {
        return buy4Price;
    }

    public void setBuy4Price(double buy4Price) {
        this.buy4Price = buy4Price;
    }

    public int getBuy5Declare() {
        return buy5Declare;
    }

    public void setBuy5Declare(int buy5Declare) {
        this.buy5Declare = buy5Declare;
    }

    public double getBuy5Price() {
        return buy5Price;
    }

    public void setBuy5Price(double buy5Price) {
        this.buy5Price = buy5Price;
    }

    public int getSell1Declare() {
        return sell1Declare;
    }

    public void setSell1Declare(int sell1Declare) {
        this.sell1Declare = sell1Declare;
    }

    public double getSell1Price() {
        return sell1Price;
    }

    public void setSell1Price(double sell1Price) {
        this.sell1Price = sell1Price;
    }

    public int getSell2Declare() {
        return sell2Declare;
    }

    public void setSell2Declare(int sell2Declare) {
        this.sell2Declare = sell2Declare;
    }

    public double getSell2Price() {
        return sell2Price;
    }

    public void setSell2Price(double sell2Price) {
        this.sell2Price = sell2Price;
    }

    public int getSell3Declare() {
        return sell3Declare;
    }

    public void setSell3Declare(int sell3Declare) {
        this.sell3Declare = sell3Declare;
    }

    public double getSell3Price() {
        return sell3Price;
    }

    public void setSell3Price(double sell3Price) {
        this.sell3Price = sell3Price;
    }

    public int getSell4Declare() {
        return sell4Declare;
    }

    public void setSell4Declare(int sell4Declare) {
        this.sell4Declare = sell4Declare;
    }

    public double getSell4Price() {
        return sell4Price;
    }

    public void setSell4Price(double sell4Price) {
        this.sell4Price = sell4Price;
    }

    public int getSell5Declare() {
        return sell5Declare;
    }

    public void setSell5Declare(int sell5Declare) {
        this.sell5Declare = sell5Declare;
    }

    public double getSell5Price() {
        return sell5Price;
    }

    public void setSell5Price(double sell5Price) {
        this.sell5Price = sell5Price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
