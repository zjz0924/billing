package cn.wow.support.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wow.common.domain.Account;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.service.StatisticsService;
import cn.wow.common.utils.Contants;
import cn.wow.common.utils.DateUtils;
import cn.wow.common.utils.JsonUtil;
import cn.wow.common.utils.pagination.PageMap;

@Controller
@RequestMapping(value = "statistic")
public class StatisticController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(StatisticController.class);

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private OperationLogService operationLogService;

	// 查询的条件，用于导出
	private Map<String, Object> queryMap = new PageMap(false);

	/**
	 * 每月详情统计
	 */
	@RequestMapping(value = "/monthStatistic")
	public String monthStatistic(HttpServletRequest request, Model model, String month) {

		Map<String, Object> queryMap = new PageMap(false);
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
		
		Date date = null;
		try {
			if (StringUtils.isBlank(month)) {
				date = new Date();
			} else {
				date = sdf1.parse(month + "-01");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		queryMap.put("startCutoffDate", DateUtils.getMonthStart(date));
		queryMap.put("endCutoffDate", DateUtils.getMonthEnd(date));

		List<Map<String, Object>> dataList = statisticsService.monthStatistic(queryMap);
		model.addAttribute("dataList", dataList);
		
		// 时间列表
		List<String> dateList = new ArrayList<String>();
		// 值列表1
		List<Double> valueList1 = new ArrayList<Double>();
		// 值列表2
		List<Double> valueList2 = new ArrayList<Double>();
		
		for (Map<String, Object> map : dataList) {
			Date dateVal = (Date) map.get("datelist");
			dateList.add(sdf.format(dateVal));
			valueList1.add((Double) map.get("total1"));
			valueList2.add((Double) map.get("total2"));
		}
		
		model.addAttribute("month", date);
		model.addAttribute("monthTitle", sdf1.format(date));
		model.addAttribute("date", JsonUtil.toJson(dateList));
		model.addAttribute("val1", JsonUtil.toJson(valueList1));
		model.addAttribute("val2", JsonUtil.toJson(valueList2));
		return "statistic/month_statistic";
	}

}
