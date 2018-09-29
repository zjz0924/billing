package cn.wow.support.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.wow.common.domain.Account;
import cn.wow.common.domain.Combo;
import cn.wow.common.domain.Record;
import cn.wow.common.domain.Scale;
import cn.wow.common.service.ComboService;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.service.RecordService;
import cn.wow.common.service.ScaleService;
import cn.wow.common.service.StatisticsService;
import cn.wow.common.utils.AjaxVO;
import cn.wow.common.utils.Contants;
import cn.wow.common.utils.ImportExcelUtil;
import cn.wow.common.utils.operationlog.OperationType;
import cn.wow.common.utils.operationlog.ServiceType;
import cn.wow.common.utils.pagination.PageMap;
import cn.wow.common.vo.PriceItem;

@Controller
@RequestMapping(value = "record")
public class RecordController extends AbstractController {

	private static Logger logger = LoggerFactory.getLogger(RecordController.class);

	@Autowired
	private RecordService recordService;

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private OperationLogService operationLogService;

	@Autowired
	private ComboService comboService;

	@Autowired
	private ScaleService scaleService;

	// 查询的条件，用于导出
	private Map<String, Object> queryMap = new PageMap(false);

	@RequestMapping(value = "/list")
	public String list(HttpServletRequest httpServletRequest, Model model, String name, String startExpireDate,
			String endExpireDate, String startCreateTime, String endCreateTime, String startUpdateTime,
			String endUpdateTime, String startCutoffDate, String endCutoffDate, String type, String appId) {

		Map<String, Object> map = new PageMap(false);
		map.put("custom_order_sql", "cutoff_date desc");
		queryMap.put("custom_order_sql", "cutoff_date desc");

		if (StringUtils.isNotBlank(appId)) {
			map.put("appId", appId);
			model.addAttribute("appId", appId);
			queryMap.put("appId", appId);
		}
		
		if (StringUtils.isNotBlank(name)) {
			map.put("qname", name);
			model.addAttribute("name", name);
			queryMap.put("name", name);
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
			queryMap.put("endUpdateTime", endUpdateTime + " 23:59:59");
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

		return "record/record_list";
	}

	@RequestMapping(value = "/detail")
	public String detail(HttpServletRequest request, Model model, String id) {
		if (StringUtils.isNotBlank(id)) {
			Record record = recordService.selectOne(Long.parseLong(id));
			model.addAttribute("facadeBean", record);
		}

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
		model.addAttribute("mode", request.getParameter("mode"));
		return "record/record_detail";
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public AjaxVO save(HttpServletRequest request, Model model, String id, String expireDate,
			String comboId, String scaleId, String remark, String cutoffDate) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("编辑成功");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Record record = null;

		try {
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
			Date date = new Date();

			if (StringUtils.isNotBlank(id)) {
				record = recordService.selectOne(Long.parseLong(id));
				if (StringUtils.isNoneBlank(expireDate)) {
					record.setExpireDate(sdf.parse(expireDate));
				}
				if (StringUtils.isNotBlank(scaleId)) {
					record.setScaleId(Long.parseLong(scaleId));
				}
				if (StringUtils.isNotBlank(comboId)) {
					record.setComboId(Long.parseLong(comboId));
				}
				record.setPrice(priceVal);
				record.setExtract1(f1);
				record.setExtract2(f2);

				if (StringUtils.isNoneBlank(cutoffDate)) {
					record.setCutoffDate(sdf.parse(cutoffDate));
				}

				record.setUpdateTime(date);
				record.setRemark(remark);
				recordService.update(getCurrentUserName(), record);
			} else {
				record = new Record();

				if (StringUtils.isNoneBlank(expireDate)) {
					record.setExpireDate(sdf.parse(expireDate));
				}
				if (StringUtils.isNotBlank(scaleId)) {
					record.setScaleId(Long.parseLong(scaleId));
				}
				if (StringUtils.isNotBlank(comboId)) {
					record.setComboId(Long.parseLong(comboId));
				}
				record.setPrice(priceVal);
				record.setExtract1(f1);
				record.setExtract2(f2);
				record.setCreateTime(date);
				record.setUpdateTime(date);

				if (StringUtils.isNoneBlank(cutoffDate)) {
					record.setCutoffDate(sdf.parse(cutoffDate));
				}

				record.setRemark(remark);
				recordService.save(getCurrentUserName(), record);

				vo.setMsg("添加成功");
			}
		} catch (Exception ex) {
			ex.printStackTrace();

			logger.error("保存失败", ex);
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
			Record record = recordService.selectOne(Long.parseLong(id));

			if (record != null) {
				recordService.deleteByPrimaryKey(getCurrentUserName(), record);
			} else {
				vo.setMsg("删除失败，记录不存在");
				vo.setSuccess(false);
				return vo;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("记录删除失败", ex);

			vo.setMsg("删除失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}

		return vo;
	}

	/**
	 * 结算
	 */
	@ResponseBody
	@RequestMapping(value = "/cutOff")
	public AjaxVO cutOff(HttpServletRequest request, @RequestParam(value = "ids[]") Long[] ids) {
		AjaxVO vo = new AjaxVO();
		vo.setMsg("结算成功");

		try {
			recordService.batchCutOff(getCurrentUserName(), ids);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("结算失败", ex);

			vo.setMsg("结算失败，系统异常");
			vo.setSuccess(false);
			return vo;
		}

		return vo;
	}

}