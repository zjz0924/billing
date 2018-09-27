package cn.wow.common.dao;

import java.util.List;
import java.util.Map;

import cn.wow.common.domain.Account;

public interface AccountDao extends SqlDao{
    int deleteByPrimaryKey(Long id);
    
    Account selectByAccountName(String userName);
    
    public void batchAdd(List<Account> list);
	
	public void batchUpdate(List<Account> list);
	
}