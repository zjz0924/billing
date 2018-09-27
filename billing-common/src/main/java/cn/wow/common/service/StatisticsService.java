package cn.wow.common.service;

import java.util.List;
import java.util.Map;

import cn.wow.common.vo.PriceItem;

public interface StatisticsService {

	// 金额统计
	public List<PriceItem> statisticsPrice();
	
	// 金额统计
	public PriceItem statisticsPrice(Map<String, Object> queryMap);
	
	// 每月详情统计
	public List<Map<String, Object>> monthStatistic(Map<String, Object> queryMap);
}
