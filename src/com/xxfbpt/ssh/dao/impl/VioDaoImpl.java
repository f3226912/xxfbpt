package com.xxfbpt.ssh.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xxfbpt.ssh.dao.IVioDao;

public class VioDaoImpl extends HibernateDaoSupport implements IVioDao {

	@SuppressWarnings("unchecked")
	public List findSQL(final String sql) throws Exception{
		List list = this.getHibernateTemplate().executeFind(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						List result = session.createSQLQuery(sql).list();
						return result;
					}
				});
		return list;
	}

}
