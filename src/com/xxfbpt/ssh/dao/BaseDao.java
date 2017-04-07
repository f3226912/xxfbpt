package com.xxfbpt.ssh.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	List<T> findHQLByPage(final String hql, final int offset, final int pageSize) throws Exception;
	

	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param map		参数Map数组对象
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	List<T> findHQLByPage(final String hql, final Map<String, Object> map,
			final int offset, final int pageSize) throws Exception;


	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param values	参数Object数组对象
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	List<T> findHQLByPage(final String hql, final Object[] values, final int offset,
			final int pageSize) throws Exception;
	
	/**
	 *查询所有存储器数据
	 * 
	 * @param hql	HQL语句
	 * @return List
	 */
	List<T> getRepositories(String hql) throws Exception;
	
	/**
	 * 分页查询
	 * 
	 * @param sql		SQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	List findSQLByPage(final String sql, final int offset, final int pageSize) throws Exception;
	
	/**
	 * 分页查询
	 * 
	 * @param sql		SQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @param clasz		Class对象
	 * @return
	 */
	List findSQLByPage(final String sql, final int offset, final int pageSize,final Class clasz) throws Exception;
	
	/**
	 * 普通SQL查询
	 * 
	 * @param sql		SQL语句
	 * @param clasz		Class对象
	 * @return
	 */
	List findSQL(final String sql,final Class clasz) throws Exception;
	
	/**
	 * 普通SQL查询
	 * 
	 * @param sql		SQL语句
	 * @return
	 */
	List findSQL(final String sql) throws Exception;
	

	/**
	 * 插入实体
	 * 
	 * @param t		实体类对象
	 * @return
	 */
	T addRepository(T t) throws Exception;
	
	/**
	 * 添加实体相应日志
	 * @param log_obj 主表日志对象
	 * @param obj 主表对象
	 * @param list 日志对象中不映射的字段
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	T addRepositoryLog(T log_obj, T obj, List list) throws Exception;

	/**
	 * 更新实体
	 * 
	 * @param t		实体类对象
	 * @return
	 */
	T updateRepository(T t) throws Exception;
	
	/**
	 * 根据主键获取存储器对象
	 * 
	 * @param pk	主键
	 * @return
	 */
	T getRepository(String id, final Class clasz) throws Exception;

	/**
	 * 获取存储器列表对象数
	 * 
	 * @param hql	HQL语句
	 * @return
	 */
	int getRepositoryByHQLListSize(String hql) throws Exception;
	
	/**
	 * 获取存储器列表对象数
	 * 
	 * @param sqls	SQL语句
	 * @return
	 */
	int getRepositoryBySQLListSize(String sql) throws Exception;


	/**
	 * 删除存储器对象
	 * 
	 * @param t		实体类对象
	 */
	void deleteRepository(T t) throws Exception;

	/**
	 * 批量删除存储器对象
	 * 
	 * @param tCollection	Collection集合对象
	 */
	void deleteRepositoryList(Collection<T> tCollection) throws Exception;
	
	/**
	 * 普通sql方式更新存储器
	 * 
	 * @param sql
	 * 
	 */
	void updateRepositoryBySql(final String sql) throws Exception;
	
}
