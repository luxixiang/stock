package com.moscue.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moscue.entity.Balance;
import com.moscue.entity.CommonResponse;
import com.moscue.entity.Stock;
import com.moscue.service.StockService;

@Controller
@RequestMapping(value = "/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@RequestMapping("/SearchView")
	public ModelAndView balance(Map<String, Object> model) {
		Balance balance = new Balance();
		return new ModelAndView("stock/searchView", "balance", balance);
	}
	
	@RequestMapping("/Search")
	@ResponseBody
	public CommonResponse Search(Map<String, Object> model){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}
	
	@RequestMapping("/Detail")
	@ResponseBody
	public CommonResponse Detail(Map<String, Object> model){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}
}
