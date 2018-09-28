package cn.wow.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wow.common.dao.ScaleDao;
import cn.wow.common.domain.Scale;
import cn.wow.common.service.ScaleService;
import cn.wow.common.utils.pagination.PageHelperExt;

@Service
@Transactional
public class ScaleServiceImpl implements ScaleService{

    private static Logger logger = LoggerFactory.getLogger(ScaleServiceImpl.class);

    @Autowired
    private ScaleDao scaleDao;

    public Scale selectOne(Long id){
    	return scaleDao.selectOne(id);
    }

    public int save(String userName, Scale Scale){
    	return scaleDao.insert(Scale);
    }

    public int update(String userName, Scale Scale){
    	return scaleDao.update(Scale);
    }

    public int deleteByPrimaryKey(String userName, Scale Scale){
    	return scaleDao.deleteByPrimaryKey(Scale.getId());
    }

    public List<Scale> selectAllList(Map<String, Object> map){
    	PageHelperExt.startPage(map);
    	return scaleDao.selectAllList(map);
    }

}
