package cn.wow.common.service.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.wow.common.utils.pagination.PageHelperExt;
import cn.wow.common.dao.ComboDao;
import cn.wow.common.domain.Combo;
import cn.wow.common.service.ComboService;

@Service
@Transactional
public class ComboServiceImpl implements ComboService{

    private static Logger logger = LoggerFactory.getLogger(ComboServiceImpl.class);

    @Autowired
    private ComboDao comboDao;

    public Combo selectOne(Long id){
    	return comboDao.selectOne(id);
    }

    public int save(String userName, Combo combo){
    	return comboDao.insert(combo);
    }

    public int update(String userName, Combo combo){
    	return comboDao.update(combo);
    }

    public int deleteByPrimaryKey(String userName, Combo combo){
    	return comboDao.deleteByPrimaryKey(combo.getId());
    }

    public List<Combo> selectAllList(Map<String, Object> map){
    	PageHelperExt.startPage(map);
    	return comboDao.selectAllList(map);
    }

}
