package cn.wow.common.service;

import java.util.List;
import java.util.Map;
import cn.wow.common.domain.Scale;

public interface ScaleService {
	
    public Scale selectOne(Long id);

    public int save(String userName, Scale scale);

    public int update(String userName, Scale scale);

    public int deleteByPrimaryKey(String userName, Scale scale);

    public List<Scale> selectAllList(Map<String, Object> map);

}
