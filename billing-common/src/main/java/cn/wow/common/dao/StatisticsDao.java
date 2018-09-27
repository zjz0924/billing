package cn.wow.common.dao;

import java.util.List;
import java.util.Map;

public interface StatisticsDao {

	public List<Map<String, Double>> statisticsPrice(Object params);
	
	// 每月详情统计
	public List<Map<String, Object>> monthStatistic(Object params);
}