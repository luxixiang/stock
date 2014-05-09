package com.moscue.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.moscue.entity.Position;
import com.moscue.entity.SecureHolder;
import com.moscue.entity.Stock;
import com.moscue.entity.StockHolder;
import com.moscue.entity.StockOrder;
import com.moscue.entity.Trade;
import com.moscue.exec.OrderExector;
import com.moscue.service.StockService;
import com.moscue.service.TradeService;
import com.moscue.utils.MvcUtils;

@Controller
@RequestMapping(value = "/trade")
public class TradeController {

	@Autowired
	private StockService stockService;

	@Autowired
	private TradeService tradeService;

	@Autowired
	private OrderExector orderExector;

	@RequestMapping("/SellList")
	public ModelAndView sellView(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<Trade> list = tradeService.getAllTrade(stockHolder, 1);
		ModelAndView mav = new ModelAndView("stock/sellList", "trades", list);
		return mav;
	}

	// 卖出视图
	@RequestMapping("/SellView")
	public ModelAndView sellView(Map<String, Object> model,
			@RequestParam(value = "code", required = false) String code) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Stock stock = null;
		if (!StringUtils.isBlank(code)) {
			stock = stockService.getStockInfo(code);
		}
		ModelAndView mav = new ModelAndView("stock/sellView", "stock", stock);
		Position position = tradeService.getPositionByHolderIdAndCode(
				stockHolder.getId(), code);
		mav.addObject("position", position);
		return mav;
	}

	@RequestMapping("/Sell")
	public ModelAndView sell(
			RedirectAttributes redirect,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "priceType", required = true) Integer priceType,
			@RequestParam(value = "agenceType", required = true) Integer agenceType,
			@RequestParam(value = "agenceType", required = true) Integer tradeType,
			@RequestParam(value = "count", required = true) Integer count) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Stock stock = stockService.getStockInfo(code);
		StockOrder order = new StockOrder();
		order.setCode(code);
		order.setName(stock.getName());
		order.setAddTime(new Date());
		order.setAgencyCode(String.valueOf(System.currentTimeMillis()));
		order.setAgencyType(agenceType);
		order.setAgencyTime(new Date());
		order.setCount(count);
		order.setBuyOrSell(2);
		order.setHolderId(stockHolder.getId());
		order.setStatus(1);// 1表示正常订单
		if (priceType == 1) {
			order.setAgencyPrice(stock.getSell1Price());
		}

		if (priceType == 2) {
			order.setAgencyPrice(stock.getSell2Price());
		}

		if (priceType == 3) {
			order.setAgencyPrice(stock.getSell3Price());
		}
		tradeService.saveOrder(order);
		if (tradeType == 1) {
			order.setAgencyPrice(stock.getSell1Price());
			orderExector.exector(order, stock);
		}
		redirect.addFlashAttribute("message", "操作成功");
		return new ModelAndView("redirect:/trade/OrderList");
	}

	@RequestMapping("/Buy")
	public ModelAndView buy(
			RedirectAttributes redirect,
			HttpServletRequest request,
			@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "priceType", required = true) Integer priceType,
			@RequestParam(value = "agenceType", required = true) Integer agenceType,
			@RequestParam(value = "agenceType", required = true) Integer tradeType,
			@RequestParam(value = "count", required = true) Integer count) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Stock stock = stockService.getStockInfo(code);
		StockOrder order = new StockOrder();
		order.setCode(code);
		order.setName(stock.getName());
		order.setAddTime(new Date());
		order.setAgencyCode(String.valueOf(System.currentTimeMillis()));
		order.setAgencyType(agenceType);
		order.setAgencyTime(new Date());
		order.setAddTime(new Date());
		order.setCount(count);
		order.setBuyOrSell(1);
		order.setHolderId(stockHolder.getId());
		order.setStatus(1);// 1表示正常订单，2表示已处理订单
		// 判断以什么价格
		if (priceType == 1) {
			order.setAgencyPrice(stock.getBuy1Price());
		} else if (priceType == 2) {
			order.setAgencyPrice(stock.getBuy2Price());
		} else if (priceType == 3) {
			order.setAgencyPrice(stock.getBuy3Price());
		}
		tradeService.saveOrder(order);
		// 如果为即时交易订单，马上处理交易
		if (tradeType == 1) {
			order.setAgencyPrice(stock.getBuy1Price());
			orderExector.exector(order, stock);
		}
		
		//redirect.addFlashAttribute("message","成功");
		//redirect.addFlashAttribute("nextUrl","http://baidu.com");
		//return MvcUtils.createMessageModelAndView("成功", "/stock/SearchView", request);
		return new ModelAndView("redirect:/stock/SearchView");
	}

	// 买入视图
	@RequestMapping("/BuyView")
	public ModelAndView buyView(Map<String, Object> model,
			@RequestParam(value = "code", required = false) String code) {
		Stock stock = null;
		if (!StringUtils.isBlank(code)) {
			stock = stockService.getStockInfo(code);
		}
		return new ModelAndView("stock/buyView", "stock", stock);
	}

	// 订单详情
	@RequestMapping("/OrderDetail")
	public ModelAndView cancelView(
			Map<String, Object> model,
			@RequestParam(value = "agencyCode", required = true) String agencyCode) {
		StockOrder stockOrder = tradeService.getOrderByAgencyCode(agencyCode);
		return new ModelAndView("stock/orderDetail", "stockOrder", stockOrder);
	}

	@RequestMapping("/OrderList")
	public ModelAndView orderList(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<StockOrder> list = tradeService.getOrderByStatusAndHolderId(1,
				stockHolder.getId());//
		return new ModelAndView("stock/orderList", "stockOrders", list);
	}

	@RequestMapping("/CancelOrder")
	public ModelAndView cancel(
			RedirectAttributes redirect,
			@RequestParam(value = "agencyCode", required = true) String agencyCode) {
		StockOrder stockOrder = tradeService.getOrderByAgencyCode(agencyCode);
		stockOrder.setStatus(3);// 订单取消状态
		tradeService.saveStockOrder(stockOrder);
		//redirect.addFlashAttribute("message", "取消成功");
		return new ModelAndView("redirect:/trade/OrderList");
	}

	// 取消订单
	@RequestMapping("/CancellDetail")
	public ModelAndView cancelDetail(
			Map<String, Object> model,
			@RequestParam(value = "agencyCode", required = true) String agencyCode) {
		StockOrder stockOrder = tradeService.getOrderByAgencyCode(agencyCode);
		return new ModelAndView("stock/cancelDetail", "stockOrders", stockOrder);
	}

	// 成交
	@RequestMapping("/TransactionList")
	public ModelAndView transactionView(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<Trade> trades = tradeService.getTradesByHolderId(stockHolder
				.getId());
		return new ModelAndView("stock/transactionList", "trades", trades);
	}

	// 持仓查询
	@RequestMapping("/PositionList")
	public ModelAndView holdView(Map<String, Object> model) {
		StockHolder stockHolder = (SecureHolder) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<Position> positions = tradeService
				.getPositionByHolderId(stockHolder.getId());
		return new ModelAndView("stock/positionList", "positions", positions);
	}
}
