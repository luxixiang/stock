package com.moscue.utils;


public class CodeConvertor {
	public static String convertBuyOrSell(Integer code){
		if (code == null) {
			return "";
		}
		if (code == 1) {
			return "买";
		}
		if (code == 2) {
			return "卖";
		}
		return "";
	}
	
	public static String convertOrderStatus(Integer code){
		if (code == null) {
			return "";
		}
		if (code == 1) {
			return "已下单";
		}
		if (code == 2) {
			return "交易完成";
		}
		if (code == 3) {
			return "已取消";
		}
		return "";
	}
	
	public static String convertTradeStatus(Integer status) {
		if (status == null) {
			return "";
		}
		if (status == 1) {
			return "成功";
		}
		if (status == 2) {
			return "失败";
		}
		return "";
	}
}
