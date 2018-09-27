package cn.wow.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.wow.common.dao.AccountDao;
import cn.wow.common.domain.Account;
import cn.wow.common.service.AccountService;
import cn.wow.common.utils.pagination.PageHelperExt;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountDao accountDao;

	public Account selectOne(Long id) {
		return accountDao.selectOne(id);
	}

	public int save(String userName, Account account) {
		return accountDao.insert(account);
	}

	public int update(String userName, Account account) {
		return accountDao.update(account);
	}

	public int deleteByPrimaryKey(String userName, Account account) {
		return accountDao.deleteByPrimaryKey(account.getId());
	}

	public Account selectByAccountName(String userName) {
		return accountDao.selectByAccountName(userName);
	}

	public List<Account> selectAllList(Map<String, Object> map) {
		PageHelperExt.startPage(map);
		return accountDao.selectAllList(map);
	}

	public void batchAdd(List<Account> list) {
		accountDao.batchAdd(list);
	}

	public void batchUpdate(List<Account> list) {
		accountDao.batchUpdate(list);
	}
}
