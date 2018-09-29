package cn.wow.common.dao;

import java.util.List;

public interface AppDao extends SqlDao{

	public List<String> getAppNames();
}