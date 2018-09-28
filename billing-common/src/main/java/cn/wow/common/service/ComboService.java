package cn.wow.common.service;

import java.util.List;
import java.util.Map;
import cn.wow.common.domain.Combo;

public interface ComboService {
    public Combo selectOne(Long id);

    public int save(String userName, Combo combo);

    public int update(String userName, Combo combo);

    public int deleteByPrimaryKey(String userName, Combo combo);

    public List<Combo> selectAllList(Map<String, Object> map);

}
