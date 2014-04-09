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
@RequestMapping(value = "/holder")
public class StockHolderController {

	@Autowired
	private StockService stockService;

	@RequestMapping("/BalanceView")
	public ModelAndView balance(Map<String, Object> model) {
		Balance balance = new Balance();
		return new ModelAndView("stock/holderBalance", "balance", balance);
	}
	
	@RequestMapping("/BalanceAdd")
	@ResponseBody
	public CommonResponse balanceAdd(){
		CommonResponse res = new CommonResponse(1,"成功");
		return res;
	}
	
	@RequestMapping("/LoginView")
	public String loginView(Map<String, Object> model) {
		return "login";
	}
	
	@RequestMapping(value="/Login", method = RequestMethod.POST)
	public ModelAndView login(RedirectAttributes redirect) {
		redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
		return new ModelAndView("redirect:/stock/SearchView", "message.id", null);
	}
	
	@RequestMapping("/Logout")
	public String logout(Map<String, Object> model) {
		return "/holder/loginView";
	}
	
}
