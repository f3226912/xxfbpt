package com.xxfbpt.ssh.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xxfbpt.ssh.dao.BaseDao;

public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	private static final Log logger = LogFactory
			.getLog(BaseDaoImpl.class);
	
	protected Class<T> clazz;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}
	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findHQLByPage(final String hql, final int offset,
			final int pageSize) throws Exception {
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createQuery(hql).setFirstResult(
								offset).setMaxResults(pageSize).list();
						return result;
					}
				});
		logger.info("执行分页查询操作hql:" + hql);
		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param map		参数Map数组对象
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findHQLByPage(final String hql, final Map<String, Object> map,
			final int offset, final int pageSize) throws Exception {
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						for (String key : map.keySet())
							query.setParameter(key, map.get(key));
						List<T> result = query.setFirstResult(offset)
								.setMaxResults(pageSize).list();
						return result;
					}
				});
		logger.info("执行分页查询操作hql:" + hql);
		return list;

	}


	/**
	 * 分页查询
	 * 
	 * @param hql		HQL语句
	 * @param values	参数Object数组对象
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findHQLByPage(final String hql, final Object[] values,
			final int offset, final int pageSize) throws Exception {
		List<T> list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(hql);
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
						List<T> result = query.setFirstResult(offset)
								.setMaxResults(pageSize).list();

						return result;
					}
				});
		logger.info("执行分页查询操作hql:" + hql);
		return list;
	}
	
	/**
	 *查询所有存储器数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getRepositories(String hql) throws Exception {
		logger.info("执行查询操作hql:" + hql);
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 普通SQL分页查询
	 * 
	 * @param sql		SQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findSQLByPage(final String sql, final int offset, final int pageSize) throws Exception{
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).setFirstResult(
								offset).setMaxResults(pageSize).list();
						return result;
					}
				});
		logger.info("执行查询操作sql:" + sql);
		return list;
	}
	
	
	/**
	 * 普通SQL分页查询
	 * 
	 * @param sql		SQL语句
	 * @param offset	开始页数
	 * @param pageSize	显示数量
	 * @param clasz		Class对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findSQLByPage(final String sql, final int offset, final int pageSize,final Class clasz) throws Exception{
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).addEntity(clasz).setFirstResult(
								offset).setMaxResults(pageSize).list();
						return result;
					}
				});
		logger.info("执行查询操作sql:" + sql);
		return list;
	}

	/**
	 * 普通SQL查询
	 * 
	 * @param sql		SQL语句
	 * @param clasz		Class对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findSQL(final String sql,final Class clasz) throws Exception {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).addEntity(clasz).list();
						return result;
					}
				});
		logger.info("执行查询操作sql:" + sql);
		return list;
	}

	/**
	 * 普通SQL查询
	 * 
	 * @param sql		SQL语句
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findSQL(final String sql) throws Exception {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).list();
						return result;
					}
				});
		logger.info("执行查询操作sql:" + sql);
		return list;
	}


	/**
	 * 插入实体
	 * 
	 * @param t		实体类对象
	 * @return
	 */
	public T addRepository(T t) throws Exception {
		this.getHibernateTemplate().save(t);
		logger.info("执行插入操作T:" + t);
		return t;
	}
	
	/**
	 * 添加实体相应日志
	 * @param t1 主表日志对象-- 对象不能为空
	 * @param t2 主表对象
	 * @param objs 日志对象中不映射的字段
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T addRepositoryLog(T log_obj, T obj, List list) throws Exception{
		if(log_obj == null || obj == null){
			return null;
		}
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			
			//设置可访问(因为字段若为private则会报无权限访问错误)
			fields[i].setAccessible(true);
			
			String name = fields[i].getName();
			if(list != null){
				if(list.contains(name)){
					continue;
				}
			}
			Field field = log_obj.getClass().getDeclaredField(name);
			
			//设置可访问
			field.setAccessible(true);
			
			field.set(log_obj, fields[i].get(obj));
		}
		this.getHibernateTemplate().save(log_obj);
		return log_obj;
	}

	/**
	 * 更新实体
	 * 
	 * @param t		实体类对象
	 * @return
	 */
	public T updateRepository(T t) throws Exception {
		this.getHibernateTemplate().update(t);
		logger.info("执行更新操作T:" + t);
		return t;
	}
	
	/**
	 * 根据主键获取存储器对象
	 * 
	 * @param pk	主键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getRepository(String id, final Class clasz) throws Exception{
		return (T) this.getHibernateTemplate().get(clasz, id);
	}

	/**
	 * 获取存储器列表对象数
	 * 
	 * @param hql	HQL语句
	 * @return	int	返回集合个数
	 */
	public int getRepositoryByHQLListSize(String hql) throws Exception {
		Query q = this.getSession().createQuery(hql);
		logger.info("执行查询记录数hql:" + hql);
		return ((Long) q.uniqueResult()).intValue();
	}
	
	/**
	 * 获取存储器列表对象数
	 * 
	 * @param sql	SQL语句
	 * @return	int	返回集合个数
	 */
	@SuppressWarnings("unchecked")
	public int getRepositoryBySQLListSize(final String sql) throws Exception {
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).list();
						return result;
					}
				});
		if(null != list && list.size() > 0){
			int i = Integer.parseInt(list.get(0).toString());
			return i;
		}
		logger.info("执行查询记录数sql:" + sql);
		return 0;
	}


	/**
	 * 删除存储器对象
	 * 
	 * @param t
	 */
	public void deleteRepository(T t) throws Exception {
		this.getHibernateTemplate().delete(t);
		logger.info("执行删除T:" + t);
	}

	/**
	 * 批量删除存储器对象
	 * 
	 * @param customerCollection
	 */
	public void deleteRepositoryList(Collection<T> tCollection) throws Exception {
		this.getHibernateTemplate().deleteAll(tCollection);
		logger.info("执行批量删除");
	}

	/**
	 * 普通sql方式更新存储器
	 * 
	 * @param sql
	 * 
	 */
	public void updateRepositoryBySql(final String sql) throws Exception {
		this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int result = session.createSQLQuery(sql).executeUpdate();
						return result;
					}
				});
		logger.info("native sql方式执行更新操作sql:" + sql);
	}
}
