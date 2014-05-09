package com.moscue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moscue.entity.Balance;
import com.moscue.entity.CommonResponse;
import com.moscue.entity.SecureHolder;
import com.moscue.entity.Stock;
import com.moscue.entity.StockHolder;
import com.moscue.fetcher.FetcherConfig;
import com.moscue.fetcher.PageFetcher;
import com.moscue.parser.StockDataParser;
import com.moscue.service.StockService;
import com.moscue.utils.CharsetUtils;
import com.moscue.service.TradeService;

@Controller
@RequestMapping(value = "/stock")
public class StockController {

	@Autowired
	private StockService stockService;
	
	@Autowired
	private TradeService tradeService;

	@RequestMapping("/SearchView")
	public ModelAndView balance(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		List<Stock> stocks = new ArrayList<Stock>();
		Balance  balance = tradeService.getBalanceByHolderId(stockHolder.getId());
		return new ModelAndView("stock/searchView", "stocks", stocks);
	}
	
	@RequestMapping("/Search")
	public ModelAndView Search(Map<String, Object> model,@RequestParam(value="code", required=false) String code){
		List<Stock> stocks = new ArrayList<Stock>();
		if (StringUtils.isBlank(code)) {
			code = "sh601006";//测试用
		}
		Stock stock = stockService.getStockInfo(code);
		if (stock != null) {
			stocks.add(stock);
		}
		stocks.addAll(stockService.getHistStock(code));
		return new ModelAndView("stock/searchView", "stocks", stocks);
	}
	
	@RequestMapping("/Detail")
	@ResponseBody
	public CommonResponse Detail(Map<String, Object> model){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}
	
	@RequestMapping("/Info")
	@ResponseBody
	public List<Stock> getStockInfo(@RequestParam("stocks") String stocks) {
		List<Stock> list = new ArrayList<Stock>();
		FetcherConfig config = new FetcherConfig();
        PageFetcher fetcher = new PageFetcher(config);
        //sh600133,sh601005,sh601006
        com.moscue.fetcher.PageFetchResult result = fetcher.get("http://hq.sinajs.cn/list="+stocks);
        String ret = CharsetUtils.getString(result.getContentBytes(), "GBK");
        String[] arr = ret.split(";");
        if (ret.length() > 0) {
        	int i = 0;
        	for(String data : arr) {
        		i++;
        		if (i == arr.length) {
        			break;
        		}
        		Stock stock = StockDataParser.parser(data);
        		list.add(stock);
        		stockService.save(stock);
        	}
        }
        return list;
	}
}
