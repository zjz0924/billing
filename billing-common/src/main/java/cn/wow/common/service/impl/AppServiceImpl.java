package cn.wow.common.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wow.common.dao.AppDao;
import cn.wow.common.domain.App;
import cn.wow.common.domain.Record;
import cn.wow.common.service.AppService;
import cn.wow.common.service.OperationLogService;
import cn.wow.common.service.RecordService;
import cn.wow.common.utils.JsonUtil;
import cn.wow.common.utils.operationlog.OperationType;
import cn.wow.common.utils.operationlog.ServiceType;
import cn.wow.common.utils.operationlog.annotation.OperationLogIgnore;
import cn.wow.common.utils.pagination.PageHelperExt;

@Service
@Transactional
public class AppServiceImpl implements AppService {

	private static Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	@Autowired
	private AppDao appDao;

	@Autowired
	private RecordService recordService;

	@Autowired
	private OperationLogService operationLogService;

	@Value("${app.url}")
	protected String appUrl;

	public App selectOne(Long id) {
		return appDao.selectOne(id);
	}

	public int save(String userName, App sign) {
		return appDao.insert(sign);
	}

	public int update(String userName, App sign) {
		return appDao.update(sign);
	}

	public int deleteByPrimaryKey(String userName, App sign) {
		return appDao.deleteByPrimaryKey(sign.getId());
	}

	public List<App> selectAllList(Map<String, Object> map) {
		PageHelperExt.startPage(map);
		return appDao.selectAllList(map);
	}

	@OperationLogIgnore
	public void addApp(String userName, App app) {
		// 添加日志
		addLog(userName, app);

		this.save(userName, app);
	}

	public void renewApp(String userName, App app, Record record) {
		this.update(userName, app);
		recordService.save(userName, record);
	}

	public List<String> getAppNames() {
		return appDao.getAppNames();
	}

	/**
	 * 添加日志
	 */
	void addLog(String userName, App app) {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("ENTITY", JsonUtil.toJson(app));
		map.put("OLDENTITY", null);
		map.put("ENTITYTYPE", "cn.wow.common.domain.App");
		map.put("OPERATION", "新建");
		String logDetail = JsonUtil.toJson(map);

		operationLogService.save(userName, OperationType.CREATE, ServiceType.APP, logDetail);
	}

}
