package com.xxfbpt.ssh.dao;

import java.util.List;

public interface IVioDao {
	
	/**
	 * 普通SQL查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List findSQL(final String sql) throws Exception;
	
	

}
