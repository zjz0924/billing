package cn.wow.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wow.common.dao.StatisticsDao;
import cn.wow.common.service.StatisticsService;
import cn.wow.common.utils.DateUtils;
import cn.wow.common.utils.pagination.PageMap;
import cn.wow.common.vo.PriceItem;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private StatisticsDao statisticsDao;
	
	// 金额统计
	public PriceItem statisticsPrice(Map<String, Object> queryMap) {
		List<Map<String, Double>> dataList = statisticsDao.statisticsPrice(queryMap);
		PriceItem item = new PriceItem();
		
		if (dataList != null && dataList.size() > 0) {
			for (Map<String, Double> map : dataList) {
				double total1 = (double) map.get("total1");
				double total2 = (double) map.get("total2");

				item.setTotal1(total1);
				item.setTotal2(total2);
			}
		}
		return item;
	}

	// 金额统计
	public List<PriceItem> statisticsPrice() {
		List<PriceItem> dataList = new ArrayList<PriceItem>();

		Map<String, Object> queryMap = new PageMap(false);
		queryMap.put("isDelete", 0);
		List<Map<String, Double>> total = statisticsDao.statisticsPrice(queryMap);

		queryMap.put("startCutoffDate", DateUtils.getToday());
		queryMap.put("endCutoffDate", DateUtils.getToday());
		List<Map<String, Double>> today = statisticsDao.statisticsPrice(queryMap);

		queryMap.put("startCutoffDate", DateUtils.getYestoday());
		queryMap.put("endCutoffDate", DateUtils.getYestoday());
		List<Map<String, Double>> yesterday = statisticsDao.statisticsPrice(queryMap);

		queryMap.put("startCutoffDate", DateUtils.getWeekStart());
		queryMap.put("endCutoffDate", DateUtils.getWeekEnd());
		List<Map<String, Double>> week = statisticsDao.statisticsPrice(queryMap);

		queryMap.put("startCutoffDate", DateUtils.getMonthStart());
		queryMap.put("endCutoffDate", DateUtils.getMonthEnd());
		List<Map<String, Double>> month = statisticsDao.statisticsPrice(queryMap);

		this.assembleData(dataList, total, "总数");
		this.assembleData(dataList, today, "今天");
		this.assembleData(dataList, yesterday, "昨天");
		this.assembleData(dataList, week, "本周");
		this.assembleData(dataList, month, "本月");

		return dataList;
	}

	public List<Map<String, Object>> monthStatistic(Map<String, Object> queryMap) {
		return statisticsDao.monthStatistic(queryMap);
	}

	private void assembleData(List<PriceItem> dataList, List<Map<String, Double>> data, String name) {
		PriceItem item = new PriceItem();
		item.setName(name);

		if (data != null && data.size() > 0) {
			for (Map<String, Double> map : data) {
				double total1 = (double) map.get("total1");
				double total2 = (double) map.get("total2");

				item.setTotal1(total1);
				item.setTotal2(total2);
			}
		}
		dataList.add(item);
	}

}
