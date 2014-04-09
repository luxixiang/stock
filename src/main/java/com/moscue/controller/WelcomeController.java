package com.moscue.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moscue.entity.Stock;
import com.moscue.fetcher.FetcherConfig;
import com.moscue.fetcher.PageFetcher;
import com.moscue.parser.StockDataParser;
import com.moscue.service.StockService;
import com.moscue.utils.CharsetUtils;

@Controller
@RequestMapping(value = "/")
public class WelcomeController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";
	
	@Autowired
	private StockService stockService;

	@RequestMapping
	public String welcome(Map<String, Object> model) {
		return "redirect:/holder/LoginView";
	}
	
	@RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", 
    		required=false, 
    		defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting_info";
    }
	
	@RequestMapping("/info")
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
