package cn.wow.common.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wow.common.dao.RecordDao;
import cn.wow.common.domain.Record;
import cn.wow.common.service.RecordService;
import cn.wow.common.utils.pagination.PageHelperExt;

@Service
@Transactional
public class RecordServiceImpl implements RecordService{

    private static Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Autowired
    private RecordDao recordDao;

    public Record selectOne(Long id){
    	return recordDao.selectOne(id);
    }

    public int save(String userName, Record record){
    	return recordDao.insert(record);
    }

    public int update(String userName, Record record){
    	return recordDao.update(record);
    }

    public int deleteByPrimaryKey(String userName, Record record){
    	return recordDao.deleteByPrimaryKey(record.getId());
    }

    public List<Record> selectAllList(Map<String, Object> map){
    	PageHelperExt.startPage(map);
    	return recordDao.selectAllList(map);
    }
    
    public void batchCutOff(String userName, Long[] ids) {
    	if (ids != null && ids.length > 0) {
    		Date date = new Date();
			for (Long id : ids) {
				Record record = recordDao.selectOne(id);
				record.setUpdateTime(date);
				
				recordDao.update(record);
			}
    	}
    }

}
