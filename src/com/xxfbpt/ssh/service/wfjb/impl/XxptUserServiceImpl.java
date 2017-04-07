package com.xxfbpt.ssh.service.wfjb.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.xxfbpt.global.SysConst;
import com.xxfbpt.ssh.dao.DefaultDao;
import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfoLog;
import com.xxfbpt.ssh.hbm.XxptUsers;
import com.xxfbpt.ssh.service.wfjb.IXxptUserService;
import com.xxfbpt.util.StringUtil;

public class XxptUserServiceImpl implements IXxptUserService {
	private DefaultDao defaultDao;
	
	@SuppressWarnings("unchecked")
	public List<XxptUsers> getXxptUser(HttpServletRequest request) throws Exception{
		List<XxptUsers> userList = null;
		String hql = "from XxptUsers t where 1 = 1 ";
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		if(!StringUtil.isNull(userid)){
			hql += " and t.userid = '"+userid+"'";
		}
		if(!StringUtil.isNull(username)){
			hql += " and t.username = '"+username+"'";
		}
		hql += " and t.zt = '1'";
		List list = this.defaultDao.getRepositories(hql);
		userList = (List<XxptUsers>)list;
		return userList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptResorceInfo(HttpServletRequest request) throws Exception{
		//String deptid = request.getParameter("deptid")==null?"":request.getParameter("deptid");
		XxptUsers users = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
		String deptid = users.getDeptid();
		String sql = "select a.zy_id, a.zy_name, a.zy_tabname, a.zy_zj, a.zy_type, a.zt " +
				    "from xxpt_resource_info a, xxpt_privilege b " +
				    "where a.zy_id = b.zy_id and b.deptid = '"+deptid+"'";
		List list = this.defaultDao.findSQL(sql);
		return list;
	}
	
	public Integer updatePwssword(String usercode, String password, HttpServletRequest request) throws Exception{
		int result = -1;
		if(!StringUtil.isNull(usercode) && !StringUtil.isNull(password)){
			String updatesql = "update xxpt_users set userpwd='"+password+"' where userid='"+usercode+"'";
			this.defaultDao.updateRepositoryBySql(updatesql);
			result = 1;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List getPower(String usercode) throws Exception {
		List powerList = null;
		String sql = "select sys_mdl_id, sys_mdl_code, sys_mdl_name, sys_mdl_url, is_flag, "
			+"sys_top_id, order_by  from xxpt_sys_mdl "
			+"where sys_mdl_code in (select p.model_code   from xxpt_user_mdl_info p  "
			+"where p.user_id = '"+usercode+"' and is_valiable = 0) and is_flag = 0";
		powerList = this.defaultDao.findSQL(sql);
		if(powerList == null){
			powerList = new ArrayList();
		}
		return powerList;
	}
	
	@SuppressWarnings("unchecked")
	public List<XxptInterfaceUserinfo> getInterfaceUserList(HttpServletRequest request, int currentpage, String cztype) throws Exception{
		List list = null;
		Map map = new HashMap();
		int count = 0;
		int pagesize = SysConst.PAGESIZE;
		int offset = (currentpage - 1)*pagesize;
		String uri = request.getRequestURI();
		StringBuffer hqlBuffer = new StringBuffer(" from XxptInterfaceUserinfo t where 1=1 ");
		StringBuffer countBuffer = new StringBuffer("select count(t) from XxptInterfaceUserinfo t where 1=1 ");
		String username = request.getParameter("username");
		if(!StringUtil.isNull(username)){
			hqlBuffer.append(" and t.username like '%"+username+"%'");
			countBuffer.append(" and t.username like '%"+username+"%'");
			request.setAttribute("username", username);
		}
		count = this.defaultDao.getRepositoryByHQLListSize(countBuffer.toString());
		list = this.defaultDao.findHQLByPage(hqlBuffer.toString(), offset, pagesize);
		map.put("uri", uri);
		map.put("pagesize", pagesize);
		map.put("rscount", count);
		map.put("currentpage", currentpage);
		request.setAttribute("rscount", count);
		request.setAttribute("map", map);
		request.setAttribute("interfaceUserList", (List<XxptInterfaceUserinfo>)list);
		return (List<XxptInterfaceUserinfo>)list;
	}
	
	@SuppressWarnings("unchecked")
	public List<XxptInterfaceInfo> getInterfaceInfoList(HttpServletRequest request) throws Exception{
		List<XxptInterfaceInfo> interfaceList = null;
		StringBuffer hqlBuffer = new StringBuffer(" from XxptInterfaceInfo t where 1 = 1");
		List list = this.defaultDao.getRepositories(hqlBuffer.toString());
		interfaceList = (List<XxptInterfaceInfo>)list;
		return interfaceList;
	}
	
	@SuppressWarnings("unchecked")
	public List<XxptInterfaceUserinfo> getInterfaceUserInfo(HttpServletRequest request, String userid) throws Exception{
		List<XxptInterfaceUserinfo> interfaceUserinfoList = null;
		String hql = " from XxptInterfaceUserinfo t where 1=1";
		if(StringUtil.isNull(userid)){
			userid = request.getParameter("userid");
		}
		if(!StringUtil.isNull(userid)){
			hql += " and t.userid = '"+userid+"'";
		}
		List list = this.defaultDao.getRepositories(hql);
		interfaceUserinfoList = (List<XxptInterfaceUserinfo>)list;
		return interfaceUserinfoList;
	}
	
	public void editInterfaceUserinfo(HttpServletRequest request, XxptInterfaceUserinfo interfaceUserinfo, String jkids) throws Exception{
		if(interfaceUserinfo != null){
			String sql = "delete from xxpt_interface_privilege where userid = '"+interfaceUserinfo.getUserid()+"'";
			String cztype = request.getParameter("cztype");
			if("修改".equals(cztype)){
				this.defaultDao.updateRepository(interfaceUserinfo);
				XxptInterfaceUserinfoLog log = new XxptInterfaceUserinfoLog();
				XxptUsers sessionUser = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
				log.setCzr(sessionUser.getUserid());
				log.setCzrxm(sessionUser.getUsername());
				log.setCzrbm(sessionUser.getDeptid());
				String ip  = request.getRemoteAddr()==null?"":request.getRemoteAddr();
				log.setCzip(ip);
				log.setCzsj(new Date());
				log.setCznr("U");
				this.defaultDao.addRepositoryLog(log, interfaceUserinfo, null);
			}else{
				this.defaultDao.addRepository(interfaceUserinfo);
				XxptInterfaceUserinfoLog log = new XxptInterfaceUserinfoLog();
				XxptUsers sessionUser = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
				log.setCzr(sessionUser.getUserid());
				log.setCzrxm(sessionUser.getUsername());
				log.setCzrbm(sessionUser.getDeptid());
				String ip  = request.getRemoteAddr()==null?"":request.getRemoteAddr();
				log.setCzip(ip);
				log.setCzsj(new Date());
				log.setCznr("I");
				this.defaultDao.addRepositoryLog(log, interfaceUserinfo, null);
			}
			this.defaultDao.updateRepositoryBySql(sql);
			if(!StringUtil.isNull(jkids)){
				String[] jks = jkids.split(",");
				for(int i = 0; i < jks.length; i++){
					sql = "insert into xxpt_interface_privilege(jkid, userid) values('"+jks[i]+"', '"+interfaceUserinfo.getUserid()+"')";
					this.defaultDao.updateRepositoryBySql(sql);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public XxptInterfaceUserinfo getInterfaceUserInfoByuserId(HttpServletRequest request, String userid) throws Exception{
		String hql = " from XxptInterfaceUserinfo t where 1=1";
		if(StringUtil.isNull(userid)){
			userid = request.getParameter("userid");
		}
		if(!StringUtil.isNull(userid)){
			hql += " and t.userid = '"+userid+"'";
			List list = this.defaultDao.getRepositories(hql);
			if(list != null && list.size() > 0){
				//查询授信单位授权接口信息
				String sql = "select jkid from xxpt_interface_privilege where userid = '"+userid+"'";
				List privateList = this.defaultDao.findSQL(sql);
				if(privateList != null && privateList.size() > 0){
					String jkids = "";
					for(int i = 0; i < privateList.size(); i++){
						jkids = jkids+privateList.get(i)+",";
					}
					request.setAttribute("jkids", jkids.substring(0, jkids.length()-1));
				}
				return (XxptInterfaceUserinfo)list.get(0);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getSysMdlList(HttpServletRequest request, String sysMdlCode) throws Exception{
		List menuList = null;
		String sql = "select sys_mdl_id,sys_mdl_name,sys_mdl_code,sys_top_id from xxpt_sys_mdl where is_flag=0 start with sys_top_id=0   connect by prior sys_mdl_id=sys_top_id ";
		if(!StringUtil.isNull(sysMdlCode)){
			sql += " and sys_mdl_code = '"+sysMdlCode+"'";
		}
		menuList = this.defaultDao.findSQL(sql);
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	public XxptUsers getXxptUserInfo(HttpServletRequest request, String userid) throws Exception{
		XxptUsers user = null;
		String hql = " from XxptUsers t where 1 =1 ";
		if(StringUtil.isNull(userid)){
			userid = request.getParameter("userid");
		}
		if(!StringUtil.isNull(userid)){
			hql += " and t.userid = '"+userid+"'";
			List list = this.defaultDao.getRepositories(hql);
			if(list != null && list.size() > 0){
				//查询菜单权限
				String sql = "select model_code from xxpt_user_mdl_info where user_id = '"+userid+"'";
				List privateList = this.defaultDao.findSQL(sql);
				if(privateList != null && privateList.size() > 0){
					String jkids = "";
					for(int i = 0; i < privateList.size(); i++){
						jkids = jkids+privateList.get(i)+",";
					}
					request.setAttribute("jkids", jkids.substring(0, jkids.length()-1));
				}
				user = (XxptUsers)list.get(0);
			}
			return user;
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List getXxptDeptList(HttpServletRequest request, String deptid) throws Exception{
		List deptList = null;
		String sql = "select deptid, deptname from xxpt_department where 1=1";
		if(!StringUtil.isNull(deptid)){
			sql += " and deptid='"+deptid+"'";
		}
		deptList = this.defaultDao.findSQL(sql);
		return deptList;
	}
	
	@SuppressWarnings("unchecked")
	public List<XxptUsers> getPtUserList(HttpServletRequest request, int currentpage, String cztype) throws Exception{
		List list = null;
		List<XxptUsers> userList = null;
		Map map = new HashMap();
		int count = 0;
		int pagesize = SysConst.PAGESIZE;
		int offset = (currentpage - 1)*pagesize;
		String uri = request.getRequestURI();
		StringBuffer hqlBuffer = new StringBuffer(" from XxptUsers t where 1=1 ");
		StringBuffer countBuffer = new StringBuffer("select count(t) from XxptUsers t where 1=1 ");
		String username = request.getParameter("username");
		if(!StringUtil.isNull(username)){
			hqlBuffer.append(" and t.username like '%"+username+"%'");
			countBuffer.append(" and t.username like '%"+username+"%'");
			request.setAttribute("username", username);
		}
		count = this.defaultDao.getRepositoryByHQLListSize(countBuffer.toString());
		if(count > 0){
			list = this.defaultDao.findHQLByPage(hqlBuffer.toString(), offset, pagesize);
			userList = (List<XxptUsers>)list;
			Map<String, String> deptMap = new HashMap<String, String>();
			String sql = "select deptid, deptname from xxpt_department where 1=1";
			List deptList = this.defaultDao.findSQL(sql);
			if(deptList != null && deptList.size() > 0){
				for(int i = 0; i < deptList.size(); i++){
					Object[] objs = (Object[])deptList.get(i);
					deptMap.put(objs[0]+"", objs[1]+"");
				}
			}
			for(XxptUsers user:userList){
				String deptidstr = deptMap.get(user.getDeptid());
				if(deptidstr != null){
					user.setDeptid(deptidstr);
				}
			}
		}
		map.put("uri", uri);
		map.put("pagesize", pagesize);
		map.put("rscount", count);
		map.put("currentpage", currentpage);
		request.setAttribute("rscount", count);
		request.setAttribute("map", map);
		request.setAttribute("ptUserList", userList);
		return (List<XxptUsers>)list;
	}
	
	public void editPtUserinfo(HttpServletRequest request, XxptUsers user, String jkids) throws Exception{
		if(user != null){
			String sql = "delete from xxpt_user_mdl_info where user_id = '"+user.getUserid()+"'";
			String cztype = request.getParameter("cztype");
			XxptUsers sessionUser = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
			user.setLrr(sessionUser.getUserid());
			user.setLrrxm(sessionUser.getUsername());
			user.setLrrbm(sessionUser.getDeptid());
			String ip  = request.getRemoteAddr()==null?"":request.getRemoteAddr();
			user.setLrip(ip);
			user.setLrsj(new Date());
			if("修改".equals(cztype)){
				this.defaultDao.updateRepository(user);
			}else{
				this.defaultDao.addRepository(user);
			}
			this.defaultDao.updateRepositoryBySql(sql);
			if(!StringUtil.isNull(jkids)){
				String[] jks = jkids.split(",");
				for(int i = 0; i < jks.length; i++){
					sql = "insert into xxpt_user_mdl_info(oid, user_id, model_code, is_valiable, re_mark, create_tm) values(SEQ_XXPT_USER_OID.nextval, '"+user.getUserid()+"', '"+jks[i]+"', '0', null, sysdate)";
					this.defaultDao.updateRepositoryBySql(sql);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request, int currentpage, String cztype) throws Exception{
		List deptList = null;
		Map map = new HashMap();
		int count = 0;
		int pagesize = SysConst.PAGESIZE;
		int offset = (currentpage - 1)*pagesize;
		String uri = request.getRequestURI();
		StringBuffer hqlBuffer = new StringBuffer("select deptid, deptname, deptaddr, deptzrr, deptlxdh, point_x, pingt_y, bz from xxpt_department t where 1=1 ");
		StringBuffer countBuffer = new StringBuffer("select count(1) from xxpt_department t where 1=1 ");
		String deptId = request.getParameter("deptId");
		String deptName = request.getParameter("deptName");
		if(!StringUtil.isNull(deptId)){
			hqlBuffer.append(" and t.deptid = '"+deptId+"'");
			countBuffer.append(" and t.deptid = '"+deptId+"'");
			request.setAttribute("deptId", deptId);
		}
		if(!StringUtil.isNull(deptName)){
			hqlBuffer.append(" and t.deptname like '%"+deptName+"%'");
			countBuffer.append(" and t.deptname like '%"+deptName+"%'");
			request.setAttribute("deptName", deptName);
		}
		count = this.defaultDao.getRepositoryBySQLListSize(countBuffer.toString());
		if(count > 0){
			deptList = this.defaultDao.findSQLByPage(hqlBuffer.toString(), offset, pagesize);
		}
		map.put("uri", uri);
		map.put("pagesize", pagesize);
		map.put("rscount", count);
		map.put("currentpage", currentpage);
		request.setAttribute("rscount", count);
		request.setAttribute("map", map);
		request.setAttribute("deptList", deptList);
		return deptList;
	}
	
	@SuppressWarnings("unchecked")
	public List getXxptResourceInfoList(HttpServletRequest request) throws Exception{
		List resorceList = null;
		String sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info where 1=1 order by zy_id asc";
		resorceList = this.defaultDao.findSQL(sql);
		return resorceList;
	}
	
	@SuppressWarnings("unchecked")
	public List getDeptResourceList(HttpServletRequest request) throws Exception{
		List deptSourceList = null;
		String deptid = request.getParameter("deptid");
		if(!StringUtil.isNull(deptid)){
			String sql = "select xh, deptid, zy_id from xxpt_privilege t where t.deptid = '"+deptid+"'";
			deptSourceList = this.defaultDao.findSQL(sql);
		}
		return deptSourceList;
	}
	
	public void editDeptResource(HttpServletRequest request) throws Exception{
		String deptid = request.getParameter("deptid");
		String jkids = request.getParameter("jkids");
		if(!StringUtil.isNull(deptid)){
			String sql = "delete from xxpt_privilege where deptid = '"+deptid+"'";
			this.defaultDao.updateRepositoryBySql(sql);
			if(!StringUtil.isNull(jkids)){
				String[] jks = jkids.split(",");
				String ip  = request.getRemoteAddr()==null?"":request.getRemoteAddr();
				XxptUsers user = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
				for(int i = 0; i < jks.length; i++){
					sql = "insert into xxpt_privilege(xh, deptid, zy_id, lrr, lrrxm, lrrbm, lrrbmkj, lrsj, lrip, lrmac) values(SEQ_PRIVILEGE_XH.nextval, '"+deptid+"', '"+jks[i]+"', '"+user.getUserid()+"', '"+user.getUsername()+"', '"+user.getLrrbm()+"', '"+(user.getLrrbmkj()==null?"":user.getLrrbmkj())+"', sysdate, '"+ip+"', null)";
					this.defaultDao.updateRepositoryBySql(sql);
				}
			}
		}
	}

	public DefaultDao getDefaultDao() {
		return defaultDao;
	}

	public void setDefaultDao(DefaultDao defaultDao) {
		this.defaultDao = defaultDao;
	}
}
