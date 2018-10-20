package cn.wow.support.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.wow.common.domain.Account;
import cn.wow.common.domain.App;
import cn.wow.common.domain.Combo;
import cn.wow.common.domain.Record;
import cn.wow.common.domain.Scale;
import cn.wow.common.service.AppService;
import cn.wow.common.service.ComboService;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.service.RecordService;
import cn.wow.common.service.ScaleService;
import cn.wow.common.utils.AjaxVO;
import cn.wow.common.utils.Contants;
import cn.wow.common.utils.ImportExcelUtil;
import cn.wow.common.utils.ToolUtils;
import cn.wow.common.utils.operationlog.OperationType;
import cn.wow.common.utils.operationlog.ServiceType;
import cn.wow.common.utils.pagination.PageMap;

@Controller
@RequestMapping(value = "app")
public class AppController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(AppController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private ScaleService scaleService;

	@Value("${app.url}")
	protected String appUrl;

	@Autowired
	private OperationLogService operationLogService;

	// 查询的条件，用于导出
	private Map<String, Object> queryMap = new PageMap(false);

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model, String name, String startEffectiveDate,
			String endEffectiveDate, String startExpireDate, String endExpireDate, String startCreateTime,
			String endCreateTime, String startUpdateTime, String endUpdateTime, String sort, String order, String isCut,
			String valid) {

		Map<String, Object> map = new PageMap(request);
		map.put("isDelete", "0");

		if (StringUtils.isBlank(sort)) {
			sort = "update_time";
		}
		if (StringUtils.isBlank(order)) {
			order = "desc";
		}

		String orderSql = sort + " " + order + ", name asc";
		map.put("custom_order_sql", orderSql);

		queryMap.clear();
		queryMap.put("custom_order_sql", orderSql);
		queryMap.put("isDelete", "0");

		if (StringUtils.isNotBlank(name)) {
			map.put("qname", name);
			queryMap.put("qname", name);
			model.addAttribute("name", name);
		}

		if (StringUtils.isNotBlank(startEffectiveDate)) {
			map.put("startEffectiveDate", startEffectiveDate);
			queryMap.put("startEffectiveDate", startEffectiveDate);
			model.addAttribute("startEffectiveDate", startEffectiveDate);
		}

		if (StringUtils.isNotBlank(endEffectiveDate)) {
			map.put("endEffectiveDate", endEffectiveDate);
			queryMap.put("endEffectiveDate", endEffectiveDate);
			model.addAttribute("endEffectiveDate", endEffectiveDate);
		}

		if (StringUtils.isNotBlank(startExpireDate)) {
			map.put("startExpireDate", startExpireDate);
			queryMap.put("startExpireDate", startExpireDate);
			model.addAttribute("startExpireDate", startExpireDate);
		}

		if (StringUtils.isNotBlank(endExpireDate)) {
			map.put("endExpireDate", endExpireDate);
			queryMap.put("endExpireDate", endExpireDate);
			model.addAttribute("endExpireDate", endExpireDate);
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

		if (StringUtils.isNotBlank(startUpdateTime)) {
			map.put("startUpdateTime", startUpdateTime + " 00:00:00");
			queryMap.put("startUpdateTime", startUpdateTime + " 00:00:00");
			model.addAttribute("startUpdateTime", startUpdateTime);
		}
		if (StringUtils.isNotBlank(endUpdateTime)) {
			map.put("endUpdateTime", endUpdateTime + " 23:59:59");
			queryMap.put("endUpdateTime", endUpdateTime + " 23:59:59");
			model.addAttribute("endUpdateTime", endUpdateTime);
		}
		if (StringUtils.isNotBlank(isCut)) {
			map.put("isCut", isCut);
			queryMap.put("isCut", isCut);
			model.addAttribute("isCut", isCut);
		}
		if (StringUtils.isNotBlank(valid)) {
			map.put("valid", valid);
			queryMap.put("valid", valid);
			model.addAttribute("valid", valid);
		}

		List<App> dataList = appService.selectAllList(map);
		model.addAttribute("dataList", dataList);

		model.addAttribute("sort", sort);
		model.addAttribute("order", order);

		return "app/app_list";
	}

	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Model model, String id) {
		if (StringUtils.isNotBlank(id)) {
			App app = appService.selectOne(Long.parseLong(id));
			model.addAttribute("facadeBean", app);
		}

		return "app/app_detail";
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxVO save(HttpServletRequest request, Model model, String id, String name, String remark,
			@RequestParam(value = "valid", required = false) int valid) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("编辑成功");

		App app = null;
		Date date = new Date();

		try {
			if (StringUtils.isNotBlank(id)) {
				app = appService.selectOne(Long.parseLong(id));

				if (app != null) {
					Map<String, Object> rMap = new HashMap<String, Object>();
					rMap.put("name", name);
					rMap.put("isDelete", 0);
					rMap.put("id", id);
					List<App> appList = appService.selectAllList(rMap);

					if (appList != null && appList.size() > 0) {
						vo.setMsg("app名称已经存在");
						vo.setSuccess(false);
						return vo;
					} else {
						app.setName(name);
						app.setRemark(remark);
						app.setUpdateTime(date);
						app.setValid(valid);

						appService.update(getCurrentUserName(), app);
					}
				}
			} else {
				Map<String, Object> rMap = new HashMap<String, Object>();
				rMap.put("name", name);
				rMap.put("isDelete", 0);
				List<App> appList = appService.selectAllList(rMap);

				if (appList != null && appList.size() > 0) {
					vo.setMsg("app名称已经存在");
					vo.setSuccess(false);
					return vo;
				} else {
					app = new App();
					app.setName(name);
					app.setRemark(remark);
					app.setCreateTime(date);
					app.setUpdateTime(date);
					app.setIsDelete(0);
					app.setIsCut(0);
					app.setValid(valid);
					appService.addApp(getCurrentUserName(), app);
					vo.setMsg("添加成功");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			logger.error("签名保存失败", ex);
			vo.setMsg("保存失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}
		return vo;
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public AjaxVO delete(HttpServletRequest request, String id) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("删除成功");

		try {
			App app = appService.selectOne(Long.parseLong(id));

			if (app != null) {
				appService.deleteByPrimaryKey(getCurrentUserName(), app);
			} else {
				vo.setMsg("删除失败，记录不存在");
				vo.setSuccess(false);
				return vo;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("签名删除失败", ex);

			vo.setMsg("删除失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}

		return vo;
	}

	/**
	 * 续费
	 */
	@RequestMapping(value = "/renewDetail")
	public String renewDetail(HttpServletRequest request, Model model, Long appId, int type) {
		model.addAttribute("appId", appId);

		// 套餐信息
		Map<String, Object> comboMap = new PageMap(false);
		comboMap.put("custom_order_sql", "name asc");
		comboMap.put("isDelete", "0");
		List<Combo> comboList = comboService.selectAllList(comboMap);

		// 分成信息
		Map<String, Object> scaleMap = new PageMap(false);
		scaleMap.put("custom_order_sql", "val asc");
		scaleMap.put("isDelete", "0");
		List<Scale> scaleList = scaleService.selectAllList(scaleMap);

		model.addAttribute("comboList", comboList);
		model.addAttribute("scaleList", scaleList);
		model.addAttribute("type", type);
		model.addAttribute("appId", appId);
		return "app/app_renew";
	}

	@ResponseBody
	@RequestMapping(value = "/renew")
	public AjaxVO renew(HttpServletRequest request, Model model, Long appId, String expireDate, String comboId,
			String scaleId, String remark, String cutoffDate, int type) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("续费成功");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = new Date();

			double priceVal = 0d;
			if (StringUtils.isNotBlank(comboId)) {
				Combo combo = comboService.selectOne(Long.parseLong(comboId));
				if (combo != null) {
					priceVal = combo.getPrice();
				}
			}

			int scaleVal = 0;
			if (StringUtils.isNotBlank(scaleId)) {
				Scale scale = scaleService.selectOne(Long.parseLong(scaleId));
				if (scale != null) {
					scaleVal = scale.getVal();
				}
			}

			BigDecimal bg = new BigDecimal((priceVal * scaleVal * 0.01));
			double f1 = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			double f2 = priceVal - f1;

			Record record = new Record();
			App app = appService.selectOne(appId);

			record.setAppId(appId);
			record.setCreateTime(date);
			record.setUpdateTime(date);
			if (StringUtils.isNotBlank(expireDate)) {
				record.setExpireDate(sdf.parse(expireDate));
			}
			if (StringUtils.isNoneBlank(cutoffDate)) {
				record.setCutoffDate(sdf.parse(cutoffDate));
			}
			record.setScaleId(Long.parseLong(scaleId));
			record.setComboId(Long.parseLong(comboId));
			record.setType(type);
			record.setPrice(priceVal);
			record.setExtract1(f1);
			record.setExtract2(f2);

			app.setUpdateTime(date);
			app.setExpireDate(record.getExpireDate());
			app.setIsCut(1);

			appService.renewApp(getCurrentUserName(), app, record);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("操作失败", ex);

			vo.setMsg("删除失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}

		return vo;
	}

	/**
	 * 新增列表
	 */
	@RequestMapping(value = "/newList")
	public String newList(HttpServletRequest request, Model model, String name, String startCreateTime,
			String endCreateTime, String sort, String order) {

		Map<String, Object> map = new PageMap(request);
		map.put("isCut", 0);
		map.put("isDelete", "0");

		if (StringUtils.isBlank(sort)) {
			sort = "create_time";
		}
		if (StringUtils.isBlank(order)) {
			order = "desc";
		}

		String orderSql = sort + " " + order + ", name asc";
		map.put("custom_order_sql", orderSql);

		if (StringUtils.isNotBlank(name)) {
			map.put("qname", name);
			model.addAttribute("name", name);
		}

		if (StringUtils.isNotBlank(startCreateTime)) {
			map.put("startCreateTime", startCreateTime + " 00:00:00");
			model.addAttribute("startCreateTime", startCreateTime);
		}
		if (StringUtils.isNotBlank(endCreateTime)) {
			map.put("endCreateTime", endCreateTime + " 23:59:59");
			model.addAttribute("endCreateTime", endCreateTime);
		}

		List<App> dataList = appService.selectAllList(map);
		model.addAttribute("dataList", dataList);

		model.addAttribute("sort", sort);
		model.addAttribute("order", order);

		return "app/app_new_list";
	}

	/**
	 * 导出
	 */
	@RequestMapping(value = "/exportApp")
	public void exportApp(HttpServletRequest request, HttpServletResponse response) {
		Account currentAccount = (Account) request.getSession().getAttribute(Contants.CURRENT_ACCOUNT);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		String filename = "App清单-" + sdf2.format(new Date());

		try {
			// 设置头
			ImportExcelUtil.setResponseHeader(response, filename + ".xlsx");

			Workbook wb = new SXSSFWorkbook(100); // 保持100条在内存中，其它保存到磁盘中
			// 工作簿
			Sheet sh = wb.createSheet("App清单");
			sh.setColumnWidth(0, (short) 6000);
			sh.setColumnWidth(1, (short) 4000);
			sh.setColumnWidth(2, (short) 6000);
			sh.setColumnWidth(3, (short) 6000);
			sh.setColumnWidth(4, (short) 3000);
			sh.setColumnWidth(5, (short) 10000);

			Map<String, CellStyle> styles = ImportExcelUtil.createStyles(wb);

			String[] titles = { "APP名称", "过期日期", "创建时间", "更新时间", "是否结算", "备注" };
			int r = 0;

			Row titleRow = sh.createRow(0);
			titleRow.setHeight((short) 450);
			for (int k = 0; k < titles.length; k++) {
				Cell cell = titleRow.createCell(k);
				cell.setCellStyle(styles.get("header"));
				cell.setCellValue(titles[k]);
			}

			++r;

			List<App> dataList = appService.selectAllList(queryMap);
			for (int j = 0; j < dataList.size(); j++) {// 添加数据
				Row contentRow = sh.createRow(r);
				contentRow.setHeight((short) 400);
				App app = dataList.get(j);

				Cell cell1 = contentRow.createCell(0);
				cell1.setCellStyle(styles.get("cell"));
				cell1.setCellValue(app.getName());

				Cell cell2 = contentRow.createCell(1);
				cell2.setCellStyle(styles.get("cell"));
				if (app.getExpireDate() != null) {
					cell2.setCellValue(sdf1.format(app.getExpireDate()));
				}

				Cell cell3 = contentRow.createCell(2);
				cell3.setCellStyle(styles.get("cell"));
				if (app.getCreateTime() != null) {
					cell3.setCellValue(sdf.format(app.getCreateTime()));
				}

				Cell cell4 = contentRow.createCell(3);
				cell4.setCellStyle(styles.get("cell"));
				if (app.getUpdateTime() != null) {
					cell4.setCellValue(sdf.format(app.getUpdateTime()));
				}

				Cell cell5 = contentRow.createCell(4);
				cell5.setCellStyle(styles.get("cell"));
				if (app.getIsCut() == 0) {
					cell5.setCellValue("否");
				} else {
					cell5.setCellValue("是");
				}

				Cell cell6 = contentRow.createCell(5);
				cell6.setCellStyle(styles.get("cell"));
				if (StringUtils.isNotBlank(app.getRemark())) {
					cell6.setCellValue(app.getRemark());
				}

				r++;
			}

			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();

			String logDetail = "导出APP清单";
			operationLogService.save(currentAccount.getUserName(), OperationType.EXPORT, ServiceType.APP, logDetail);

		} catch (Exception e) {
			logger.error("App清单导出失败");

			e.printStackTrace();
		}
	}

}