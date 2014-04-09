package com.moscue.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.moscue.entity.CommonResponse;
import com.moscue.entity.Message;
import com.moscue.entity.Stock;
import com.moscue.fetcher.FetcherConfig;
import com.moscue.fetcher.PageFetcher;
import com.moscue.parser.StockDataParser;
import com.moscue.service.StockService;
import com.moscue.utils.CharsetUtils;

@Controller
@RequestMapping(value = "/trade")
public class TradeController {

	// 卖出视图
	@RequestMapping("/SellView")
	public ModelAndView sellView(Map<String, Object> model) {
		return new ModelAndView("stock/sellView", "messages", null);
	}
	
	@RequestMapping("/Sell")
	@ResponseBody
	public CommonResponse sell(){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}
	
	@RequestMapping("/Buy")
	@ResponseBody
	public CommonResponse buy(){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}

	// 买入视图
	@RequestMapping("/BuyView")
	public ModelAndView buyView(Map<String, Object> model) {
		return new ModelAndView("stock/buyView", "messages", null);
	}

	// 取消订单
	@RequestMapping("/CancellView")
	public ModelAndView cancelView(Map<String, Object> model) {
		return new ModelAndView("stock/cancelView", "messages", null);
	}
	
	@RequestMapping("/Cancel")
	@ResponseBody
	public CommonResponse cancel(){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}

	// 取消订单
	@RequestMapping("/CancellDetail")
	public ModelAndView cancelDetail(Map<String, Object> model) {
		return new ModelAndView("stock/cancelDetail", "messages", null);
	}

	// 成交
	@RequestMapping("/TransactionView")
	public ModelAndView transactionView(Map<String, Object> model) {
		return new ModelAndView("stock/transactionView", "messages", null);
	}

	// 持仓查询
	@RequestMapping("/HoldView")
	public ModelAndView holdView(Map<String, Object> model) {
		return new ModelAndView("stock/holdView", "messages", null);
	}
}
