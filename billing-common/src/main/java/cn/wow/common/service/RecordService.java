package cn.wow.common.service;

import java.util.List;
import java.util.Map;
import cn.wow.common.domain.Record;

public interface RecordService {
    public Record selectOne(Long id);

    public int save(String userName, Record record);

    public int update(String userName, Record record);

    public int deleteByPrimaryKey(String userName, Record record);

    public List<Record> selectAllList(Map<String, Object> map);
    
    public void batchCutOff(String userName, Long[] ids);

}
