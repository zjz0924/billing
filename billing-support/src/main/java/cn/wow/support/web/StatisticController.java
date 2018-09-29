package cn.wow.support.web;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.wow.common.domain.Account;
import cn.wow.common.domain.App;
import cn.wow.common.domain.Record;
import cn.wow.common.service.AppService;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.service.RecordService;
import cn.wow.common.service.StatisticsService;
import cn.wow.common.utils.Contants;
import cn.wow.common.utils.DateUtils;
import cn.wow.common.utils.ImportExcelUtil;
import cn.wow.common.utils.JsonUtil;
import cn.wow.common.utils.operationlog.OperationType;
import cn.wow.common.utils.operationlog.ServiceType;
import cn.wow.common.utils.pagination.PageMap;
import cn.wow.common.vo.PriceItem;

@Controller
@RequestMapping(value = "statistic")
public class StatisticController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(StatisticController.class);

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private OperationLogService operationLogService;
	
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private AppService appService;

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

	
	/**
	 * 收入记录
	 */
	@RequestMapping(value = "/income")
	public String income(HttpServletRequest httpServletRequest, Model model, String name, String startExpireDate,
			String endExpireDate, String startCreateTime, String endCreateTime, String startUpdateTime,
			String endUpdateTime, String startCutoffDate, String endCutoffDate, String type, String appId) {

		Map<String, Object> map = new PageMap(httpServletRequest);
		map.put("custom_order_sql", "cutoff_date desc");
		queryMap.put("custom_order_sql", "cutoff_date desc");

		map.put("isDelete", "0");
		queryMap.put("isDelete", "0");
		
		if (StringUtils.isNotBlank(appId)) {
			map.put("appId", appId);
			model.addAttribute("appId", appId);
			queryMap.put("appId", appId);
		}
		
		if (StringUtils.isNotBlank(name)) {
			map.put("qname", name);
			model.addAttribute("name", name);
			queryMap.put("qname", name);
		}

		if (StringUtils.isNotBlank(startCutoffDate)) {
			map.put("startCutoffDate", startCutoffDate);
			model.addAttribute("startCutoffDate", startCutoffDate);
			queryMap.put("startCutoffDate", startCutoffDate);
		}

		if (StringUtils.isNotBlank(endCutoffDate)) {
			map.put("endCutoffDate", endCutoffDate);
			model.addAttribute("endCutoffDate", endCutoffDate);
			queryMap.put("endCutoffDate", endCutoffDate);
		}

		if (StringUtils.isNotBlank(startExpireDate)) {
			map.put("startExpireDate", startExpireDate);
			model.addAttribute("startExpireDate", startExpireDate);
			queryMap.put("startExpireDate", startExpireDate);
		}

		if (StringUtils.isNotBlank(endExpireDate)) {
			map.put("endExpireDate", endExpireDate);
			model.addAttribute("endExpireDate", endExpireDate);
			queryMap.put("endExpireDate", endExpireDate);
		}

		if (StringUtils.isNotBlank(startCreateTime)) {
			map.put("startCreateTime", startCreateTime + " 00:00:00");
			model.addAttribute("startCreateTime", startCreateTime);
			queryMap.put("startCreateTime", startCreateTime + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			map.put("endCreateTime", endCreateTime + " 23:59:59");
			model.addAttribute("endCreateTime", endCreateTime);
			queryMap.put("endCreateTime", endCreateTime + " 23:59:59");
		}
		if (StringUtils.isNotBlank(startUpdateTime)) {
			map.put("startUpdateTime", startUpdateTime + " 00:00:00");
			model.addAttribute("startUpdateTime", startUpdateTime);
			queryMap.put("startUpdateTime", startUpdateTime + " 00:00:00");
		}
		if (StringUtils.isNotBlank(endUpdateTime)) {
			map.put("endUpdateTime", endUpdateTime + " 23:59:59");
			model.addAttribute("endUpdateTime", endUpdateTime);
			queryMap.put("endUpdateTime", endUpdateTime + " 00:00:00");
		}
		if (StringUtils.isNotBlank(type)) {
			map.put("type", type);
			model.addAttribute("type", type);
			queryMap.put("type", type);
		}

		// 金额统计
		PriceItem priceItem = statisticsService.statisticsPrice(map);

		List<Record> dataList = recordService.selectAllList(map);

		model.addAttribute("dataList", dataList);
		model.addAttribute("priceItem", priceItem);

		return "statistic/income";
	}
	
	
	/**
	 * 到期app
	 */
	@RequestMapping(value = "/expire")
	public String Expire(HttpServletRequest request, Model model, String type) {

		Map<String, Object> map = new PageMap(request);
		map.put("custom_order_sql", "expire_date asc");
		map.put("isDelete", "0");

		String endExpireDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (StringUtils.isBlank(type)) {
			type = "1";
		}

		// 5天后
		if ("1".equals(type)) {

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +5);
			endExpireDate = sdf.format(cal.getTime());

		} else if ("2".equals(type)) { // 3天后

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +3);
			endExpireDate = sdf.format(cal.getTime());

		} else { // 1天后

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, +1);
			endExpireDate = sdf.format(cal.getTime());
		}

		map.put("endExpireDate", endExpireDate);

		List<App> dataList = appService.selectAllList(map);
		model.addAttribute("dataList", dataList);
		model.addAttribute("resUrl", resUrl);
		model.addAttribute("type", type);
		return "statistic/expire_list";
	}
	
	
	/**
	 * 收入记录导出
	 */
	@RequestMapping(value = "/incomeExport")
	public void incomeExport(HttpServletRequest request, HttpServletResponse response) {
		Account currentAccount = (Account) request.getSession().getAttribute(Contants.CURRENT_ACCOUNT);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

		String filename = "收入记录清单-" + sdf2.format(new Date());

		try {
			// 设置头
			ImportExcelUtil.setResponseHeader(response, filename + ".xlsx");

			Workbook wb = new SXSSFWorkbook(100); // 保持100条在内存中，其它保存到磁盘中
			// 工作簿
			Sheet sh = wb.createSheet("清单");
			sh.setColumnWidth(0, (short) 5000);
			sh.setColumnWidth(1, (short) 4000);
			sh.setColumnWidth(2, (short) 4000);
			sh.setColumnWidth(3, (short) 3000);
			sh.setColumnWidth(4, (short) 3000);
			sh.setColumnWidth(5, (short) 3000);
			sh.setColumnWidth(6, (short) 3000);
			sh.setColumnWidth(7, (short) 3000);
			sh.setColumnWidth(8, (short) 6000);
			sh.setColumnWidth(9, (short) 6000);

			Map<String, CellStyle> styles = ImportExcelUtil.createStyles(wb);

			String[] titles = { "App名称", "结算日期",  "过期时间", "总金额", "比例(%)", "我的/元", "其他/元", "类型", "创建时间", "备注" };
			int r = 0;

			Row titleRow = sh.createRow(0);
			titleRow.setHeight((short) 450);
			for (int k = 0; k < titles.length; k++) {
				Cell cell = titleRow.createCell(k);
				cell.setCellStyle(styles.get("header"));
				cell.setCellValue(titles[k]);
			}

			++r;

			// 金额统计
			PriceItem priceItem = statisticsService.statisticsPrice(queryMap);
			List<Record> dataList = recordService.selectAllList(queryMap);

			for (int j = 0; j < dataList.size(); j++) {// 添加数据
				Row contentRow = sh.createRow(r);
				contentRow.setHeight((short) 400);
				Record record = dataList.get(j);

				Cell cell0 = contentRow.createCell(0);
				cell0.setCellStyle(styles.get("cell"));
				if(record.getApp() != null) {
					cell0.setCellValue(record.getApp().getName());
				}
				
				Cell cell1 = contentRow.createCell(1);
				cell1.setCellStyle(styles.get("cell"));
				cell1.setCellValue(sdf1.format(record.getCutoffDate()));

				Cell cell3 = contentRow.createCell(2);
				cell3.setCellStyle(styles.get("cell"));
				cell3.setCellValue(sdf1.format(record.getExpireDate()));

				Cell cell4 = contentRow.createCell(3);
				cell4.setCellStyle(styles.get("cell"));
				if(record.getCombo() != null) {
					cell4.setCellValue(record.getCombo().getPrice());
				}

				Cell cell5 = contentRow.createCell(4);
				cell5.setCellStyle(styles.get("cell"));
				if(record.getScale() != null) {
					cell5.setCellValue(record.getScale().getVal());
				}

				Cell cell6 = contentRow.createCell(5);
				cell6.setCellStyle(styles.get("cell"));
				cell6.setCellValue(record.getExtract1());

				Cell cell7 = contentRow.createCell(6);
				cell7.setCellStyle(styles.get("cell"));
				cell7.setCellValue(record.getExtract2());
				
				Cell cell8 = contentRow.createCell(7);
				cell8.setCellStyle(styles.get("cell"));
				if(record.getType() == 1) {
					cell8.setCellValue("新增");
				}else {
					cell8.setCellValue("续费");
				}

				Cell cell9 = contentRow.createCell(8);
				cell9.setCellStyle(styles.get("cell"));
				cell9.setCellValue(sdf.format(record.getCreateTime()));

				Cell cell10 = contentRow.createCell(9);
				cell10.setCellStyle(styles.get("cell"));
				cell10.setCellValue(record.getRemark());

				r++;
			}

			Row contentRow1 = sh.createRow(++r);
			contentRow1.setHeight((short) 400);

			Cell cell1 = contentRow1.createCell(0);
			cell1.setCellStyle(styles.get("cell"));
			cell1.setCellValue("总金额/元");

			Cell cell2 = contentRow1.createCell(1);
			cell2.setCellStyle(styles.get("cell"));
			cell2.setCellValue((priceItem.getTotal1() + priceItem.getTotal2()));
		
			Row contentRow2 = sh.createRow(++r);
			contentRow2.setHeight((short) 400);
			
			Cell cell3 = contentRow2.createCell(0);
			cell3.setCellStyle(styles.get("cell"));
			cell3.setCellValue("我的/元");
			
			Cell cell4 = contentRow2.createCell(1);
			cell4.setCellStyle(styles.get("cell"));
			cell4.setCellValue(priceItem.getTotal1());

			Row contentRow3 = sh.createRow(++r);
			contentRow3.setHeight((short) 400);

			Cell cell5 = contentRow3.createCell(0);
			cell5.setCellStyle(styles.get("cell"));
			cell5.setCellValue("其他/元");

			Cell cell6 = contentRow3.createCell(1);
			cell6.setCellStyle(styles.get("cell"));
			cell6.setCellValue(priceItem.getTotal2());

			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();

			String logDetail = "导出记录清单";
			operationLogService.save(currentAccount.getUserName(), OperationType.EXPORT, ServiceType.RECORD, logDetail);

		} catch (Exception e) {
			logger.error("清单导出失败");

			e.printStackTrace();
		}
	}
}
