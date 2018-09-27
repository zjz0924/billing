package cn.wow.support.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.wow.common.domain.Account;
import cn.wow.common.service.AccountService;
import cn.wow.common.service.StatisticsService;
import cn.wow.common.utils.pagination.PageMap;
import cn.wow.common.vo.PriceItem;

/**
 * 首页控制器
 * 
 * @author zhenjunzhuo
 */
@Controller
@RequestMapping(value = "")
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	// 查询的条件，用于导出
	private Map<String, Object> queryMap = new PageMap(false);
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, Model model) {
		return "index/index";
	}
	
	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, Model model) {

		// 金额统计
		List<PriceItem> priceDataList = statisticsService.statisticsPrice();
		model.addAttribute("priceDataList", priceDataList);
		
		return "index/home";
	}
	
	@RequestMapping(value = "/accountlist")
	public String list(HttpServletRequest request, Model model, String userName, String nickName,
			String mobile, String startCreateTime, String endCreateTime, String lock) {

		queryMap.clear();
		Map<String, Object> map = new PageMap(request);
		map.put("custom_order_sql", "username asc");
		queryMap.put("custom_order_sql", "username asc");
		
		if (StringUtils.isNotBlank(userName)) {
			map.put("qUserName", userName);
			queryMap.put("qUserName", userName);
			model.addAttribute("userName", userName);
		}
		if (StringUtils.isNotBlank(nickName)) {
			map.put("nickName", nickName);
			queryMap.put("nickName", nickName);
			model.addAttribute("nickName", nickName);
		}
		if (StringUtils.isNotBlank(mobile)) {
			map.put("mobile", mobile);
			queryMap.put("mobile", mobile);
		}
		if (StringUtils.isNotBlank(startCreateTime)) {
			map.put("startCreateTime", startCreateTime + " 00:00:00");
			queryMap.put("startCreateTime", startCreateTime + " 00:00:00");
			model.addAttribute("startCreateTime", startCreateTime);
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			map.put("endCreateTime", endCreateTime + " 23:59:59");
			queryMap.put("endCreateTime", endCreateTime + " 23:59:59");
			model.addAttribute("endCreateTime", endCreateTime);
		}

		List<Account> dataList = accountService.selectAllList(map);
		model.addAttribute("dataList", dataList);
		
		return "sys/account/account_list";
	}
	
}
