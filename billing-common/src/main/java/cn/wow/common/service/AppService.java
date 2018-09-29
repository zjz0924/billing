package cn.wow.common.service;

import java.util.List;
import java.util.Map;

import cn.wow.common.domain.App;
import cn.wow.common.domain.Record;

public interface AppService {
    public App selectOne(Long id);

    public int save(String userName, App sign);

    public int update(String userName, App sign);

    public int deleteByPrimaryKey(String userName, App sign);

    public List<App> selectAllList(Map<String, Object> map);

    public void addApp(String userName, App app);
    
    /**
     * 续费
     */
    public void renewApp(String userName, App app, Record record);
    
    
    public List<String> getAppNames();
}
