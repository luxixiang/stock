package com.moscue.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.moscue.service.StockHolderService;
import com.moscue.service.StockService;
import com.moscue.service.TradeService;

@Controller
@RequestMapping(value = "/holder")
public class StockHolderController {

	@Autowired
	private StockService stockService;
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private StockHolderService stockHolderService;

	@RequestMapping("/BalanceView")
	public ModelAndView balance(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder.getContext()
	    .getAuthentication()
	    .getPrincipal();
		Balance balance = stockHolderService.getBalanceByHolderId(stockHolder.getId());
		ModelAndView mv = new ModelAndView("stock/holderBalance", "balance", balance);
		mv.addObject("stockHolder", stockHolder);
		return mv;
	}
	
	@RequestMapping("/BalanceOper")
	public ModelAndView balanceAdd(@RequestParam("money") Double money, @RequestParam("OperType") Integer type){
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Balance balance = stockHolderService.getBalanceByHolderId(stockHolder.getId());
		ModelAndView mv = new ModelAndView("stock/holderBalance");
		//账户不存在时先创建
		if (balance == null) {
			balance = new Balance();
			balance.setAddTime(new Date());
			balance.setHolderId(stockHolder.getId());
			balance.setRemain(0.0);
			stockHolderService.saveBalance(balance);
		}
		//充值与提取
		if (type == 1) {
			balance.setRemain(balance.getRemain() + money);
		} else if(type == 2) {
			Double temp = balance.getRemain() - money;
			stockHolderService.saveBalance(balance);
			if (temp >= 0) {
				balance.setRemain(temp);
			} else {
				mv.addObject("errmsg", "余额不足");
			}
		}
		mv.addObject("balance", balance);
		mv.addObject("stockHolder", stockHolder);
		return mv;
	}
	
	@RequestMapping("/LoginView")
	public String loginView(Map<String, Object> model) {
		return "login";
	}
	
	@RequestMapping(value="/Login", method = RequestMethod.POST)
	public ModelAndView login(RedirectAttributes redirect, @RequestParam("username") String username, @RequestParam("password") String password) {		
		//redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
		return new ModelAndView("redirect:/stock/SearchView", "message.id", null);
	}
	
	@RequestMapping("/Logout")
	public String logout(Map<String, Object> model) {
		return "/holder/loginView";
	}
	
}
