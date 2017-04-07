package com.xxfbpt.ssh.service.impl;


import java.sql.Blob;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xxfbpt.global.SysConst;
import com.xxfbpt.ssh.dao.DefaultDao;
import com.xxfbpt.ssh.dao.IXxfbptDao;
import com.xxfbpt.ssh.dao.IVioDao;
import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;
import com.xxfbpt.ssh.hbm.XxptUsers;
import com.xxfbpt.ssh.service.IXxfbptService;
import com.xxfbpt.util.DateUtil;
import com.xxfbpt.util.StringUtil;


/**
 * 信息平台业务逻辑层
 * @author wy
 * @date 2014-07-23
 *
 */
public class XxfbptServiceImpl implements IXxfbptService{
	
	private DefaultDao defaultDao;
	private IXxfbptDao xxfbptDao;
	private IVioDao vioDao;
	
	@SuppressWarnings("unchecked")
	public XxptInterfaceUserinfo getUserInfo(String userid, String userpwd) throws Exception{
		String hql = " from XxptInterfaceUserinfo t "
				   +" where t.userid = '"+userid+"'";
		if(!StringUtil.isNull(userpwd)){
			hql += " and t.password = '"+userpwd+"'";
		}
		List list = this.defaultDao.getRepositories(hql);
		if(list != null && list.size() > 0){
			XxptInterfaceUserinfo objs = (XxptInterfaceUserinfo)list.get(0);
			return objs;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List getUserinterface(String userid) throws Exception{
		String sql = "select jkid from xxpt_interface_privilege t where t.userid = '"+userid+"'";
		List list = this.defaultDao.findSQL(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public XxptInterfaceInfo getInterfaceInfo(String jkid) throws Exception{
		XxptInterfaceInfo interfaceInfo = null;
		String hql = "from XxptInterfaceInfo t where t.jkid = '"+jkid+"'";
		List list = this.defaultDao.getRepositories(hql);
		if(list != null && list.size() > 0){
			interfaceInfo = (XxptInterfaceInfo)list.get(0);
		}
		return interfaceInfo;
	}
	
	public String getXxptProc(String jkid, String srcs, String picStr) throws Exception{
		String result = this.xxfbptDao.getXxptProc(jkid, srcs, picStr);
		return result;
	}
	
	@SuppressWarnings({ "unchecked"})
	public List getResourcemx(HttpServletRequest request, String zyid, int currentPage, String cztype) throws Exception{
		List list = null;
		List mxList = null;
		Map<String, String> jmMap = new HashMap<String, String>();
		int count = 0;
		Map map = new HashMap();
		if(StringUtil.isNull(zyid)){
			zyid = request.getParameter("zyid")==null?"":request.getParameter("zyid");
			request.setAttribute("zyid", zyid);
		}
		String isshow = request.getParameter("isshow")==null?"":request.getParameter("isshow");
		int pagesize = SysConst.PAGESIZE;
		int offset = (currentPage-1)*pagesize;
		String uri = request.getRequestURI();
		if(!StringUtil.isNull(zyid)){
			String sql = "select mx_id, mx_name, mx_ms, zy_id, zt, is_pic, is_show, " +
					"is_jm, zd_dx, zd_text, zd_value, zd_key_add, zy_order, data_type " +
					"from xxpt_resouce_mx t where t.zy_id = "+zyid+" " +
					"and t.zt = 'T' ";
			if("1".equals(isshow)){
				sql += " and t.is_show = 1  ";
			}else if("2".equals(isshow)){
				sql += " and t.is_show in ('1', '2')  ";
			}else{
				return null;
			}
			sql += " order by zy_order asc";
			list = this.defaultDao.findSQL(sql);
			if(list != null && list.size() > 0){
				//标题list
				request.setAttribute("title", list);
				//明细list
				sql = "select zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in from xxpt_resource_info t where t.zy_id = "+zyid;
				List zyList = this.defaultDao.findSQL(sql);
				String tabname = "";
				String zy_zj = "";
				String cxtj = "";
				if(zyList != null && zyList.size() > 0){
					Object[] objs = (Object[])zyList.get(0);
					tabname = objs[1]+"";
					request.setAttribute("zyname", objs[0]+"");
					zy_zj = objs[2]==null?"":objs[2]+"";
					if(objs[4] != null){
						cxtj = objs[4]+"";
					}
					
				}
				StringBuffer buffer = new StringBuffer("select ");
				StringBuffer countSql = new StringBuffer("select count(1)");
				
				for(int i = 0; i < list.size(); i++){
					Object[] objs = (Object[])list.get(i);
					if(i == 0){
						//是否需要字典翻译
						if(objs[8] == null){
							//不需要翻译
							if("1".equals(objs[7]+"")){
								//加密字段
								buffer.append(" pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
							}else{
								if("DATE".equals(objs[13]+"")){
									buffer.append(" to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
								}else{
									buffer.append(objs[1]);
								}
							}
						}else{
							//字典翻译
							if("1".equals(objs[7]+"")){
								buffer.append(" (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') ");
								if(objs[11] != null){
									buffer.append(objs[11]+"");
								}
								buffer.append(") ");
							}else{
								buffer.append(" (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
								if(objs[11] != null){
									buffer.append(objs[11]+"");
								}
								buffer.append(") ");
							}
						}
					}else{
						//是否需要字典翻译
						if(objs[8] == null){
							//不需要翻译
							if("1".equals(objs[7]+"")){
								//加密字段
								buffer.append(", pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
							}else{
								if("DATE".equals(objs[13]+"")){
									buffer.append(", to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
								}else{
									buffer.append(","+objs[1]);
								}
							}
						}else{
							//字典翻译
							if("1".equals(objs[7]+"")){
								buffer.append(", (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') ");
								if(objs[11] != null){
									buffer.append(objs[11]+"");
								}
								buffer.append(") ");
							}else{
								buffer.append(", (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
								if(objs[11] != null){
									buffer.append(objs[11]+"");
								}
								buffer.append(") ");
							}
						}
					}
				}
				countSql.append(" from "+tabname+" t where 1=1 ");
				buffer.append(" from "+tabname+" t where 1=1 ");
				
				buffer.append(" ");
				String zt = request.getParameter("zt");
				if(!StringUtil.isNull(zt)){
					request.setAttribute("zt", zt);
				}else{
					request.setAttribute("zt", "");
				}
				
				if(!StringUtil.isNull(zt)){
					buffer.append(" and t.zt = '"+zt+"'");
					countSql.append(" and t.zt = '"+zt+"'");
				}
				
				if("export".equals(cztype)){
					//资源主键
					String xhs = request.getParameter("xhs");
					String xhstr = "";
					if(!StringUtil.isNull(xhs)){
						String[] xhobj = xhs.split(",");
						for(int t = 0; t < xhobj.length; t++){
							xhstr += "'"+(xhobj[t]).trim()+"',";
						}
						xhstr = xhstr.substring(0, xhstr.length() - 1);
					}
					if(!StringUtil.isNull(xhstr)){
						buffer.append(" and t."+zy_zj+" in ("+xhstr+")");
						countSql.append(" and t."+zy_zj+" in ("+xhstr+")");
					}
				}
				String jmsql = "select mx_id, mx_name, mx_ms, zy_id, zt, is_pic, is_show, " +
						"is_jm, zd_dx, zd_text, zd_value, zd_key_add, zy_order, data_type " +
						"from xxpt_resouce_mx t where t.zy_id = "+zyid+" " +
						"and t.zt = 'T' ";
				jmsql += " order by zy_order asc";
				List jmlist = this.defaultDao.findSQL(jmsql);
				for(int i = 0; i < jmlist.size(); i++){
					Object[] objs = (Object[])jmlist.get(i);
					jmMap.put(objs[1]+"", objs[7]+"");
				}
				
				if(!StringUtil.isNull(cxtj)){
					//所有条件
					String[] tjs = cxtj.split(",");
					if(tjs != null && tjs.length > 0){
						for(int k = 0; k < tjs.length; k++){
							//获取单个条件
							String tj = tjs[k];
							//获取条件名和条件类型
							String[] nametype = tj.split("-");
							if("varchar".equals((nametype[1]+"").trim().toLowerCase()) || "varchar2".equals((nametype[1]+"").trim().toLowerCase())){
								String val = request.getParameter((nametype[0]+"").trim().toLowerCase());
								if(!StringUtil.isNull(val)){
									if("1".equals(jmMap.get(nametype[0].trim().toUpperCase()))){
										buffer.append(" and pkg_encrypt_decrypt.decrypt_3key_mode(t."+nametype[0]+", '"+SysConst.ASCDESC_KEY+"') like '%"+val+"%'");
										countSql.append(" and pkg_encrypt_decrypt.decrypt_3key_mode(t."+nametype[0]+", '"+SysConst.ASCDESC_KEY+"') like '%"+val+"%'");
									}else{
										buffer.append(" and t."+nametype[0]+" like '%"+val+"%'");
										countSql.append(" and t."+nametype[0]+" like '%"+val+"%'");
									}
									request.setAttribute((nametype[0]+"").trim().toLowerCase(), val);
								}
								
							}else if("date".equals((nametype[1]+"").trim().toLowerCase())){
								if("lrsj".equals((nametype[0]+"").trim().toLowerCase())){
									String s_date = request.getParameter("s_"+(nametype[0]+"").trim().toLowerCase());
									String e_date = request.getParameter("e_"+(nametype[0]+"").trim().toLowerCase());
									if(!StringUtil.isNull(s_date) && !StringUtil.isNull(e_date)){
										buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
										countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
									}else if(!StringUtil.isNull(s_date) && StringUtil.isNull(e_date)){
										buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') ");
										countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') ");
									}else if(StringUtil.isNull(s_date) && !StringUtil.isNull(e_date)){
										buffer.append(" and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
										countSql.append(" and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
									}else{
//										s_date = DateUtil.date2String(DateUtil.getAppointDate(-7));
//										e_date = DateUtil.date2String(new Date());
//										buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1");
//										countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1");
									}
									request.setAttribute("s_"+(nametype[0]+"").trim().toLowerCase(), s_date);
									request.setAttribute("e_"+(nametype[0]+"").trim().toLowerCase(), e_date);
								}else{
									String s_date = request.getParameter("s_"+(nametype[0]+"").trim().toLowerCase());
									String e_date = request.getParameter("e_"+(nametype[0]+"").trim().toLowerCase());
									if(!StringUtil.isNull(s_date) || !StringUtil.isNull(e_date)){
										if(!StringUtil.isNull(s_date) && !StringUtil.isNull(e_date)){
											buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
											countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
										}else if(!StringUtil.isNull(s_date) && StringUtil.isNull(e_date)){
											buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') ");
											countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') ");
										}else if(StringUtil.isNull(s_date) && !StringUtil.isNull(e_date)){
											buffer.append(" and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
											countSql.append(" and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1 ");
										}else{
											s_date = DateUtil.date2String(DateUtil.getAppointDate(-7));
											e_date = DateUtil.date2String(new Date());
											buffer.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1");
											countSql.append(" and t."+nametype[0]+" >= to_date('"+s_date+"', 'yyyy-MM-dd') and t."+nametype[0]+" < to_date('"+e_date+"', 'yyyy-MM-dd')+1");
										}
										request.setAttribute("s_"+(nametype[0]+"").trim().toLowerCase(), s_date);
										request.setAttribute("e_"+(nametype[0]+"").trim().toLowerCase(), e_date);
									}
								}
								
							}else if("number".equals((nametype[1]+"").trim().toLowerCase())){
								String val = request.getParameter(nametype[0]+"");
								buffer.append(" and t."+nametype[0]+" = "+val);
								countSql.append(" and t."+nametype[0]+" = "+val);
								request.setAttribute((nametype[0]+"").trim().toLowerCase(), val);
							}else{
								String val = request.getParameter(nametype[0]+"");
								buffer.append(" and t."+nametype[0]+" like '%"+val+"%'");
								countSql.append(" and t."+nametype[0]+" like '%"+val+"%'");
								request.setAttribute((nametype[0]+"").trim().toLowerCase(), val);
							}
						}
					}
				}
				
				count = this.defaultDao.getRepositoryBySQLListSize(countSql.toString());
				if(count > 10000){
					map.put("uri", uri);
					map.put("pagesize", pagesize);
					map.put("rscount", count);
					map.put("currentpage", currentPage);
					request.setAttribute("rscount", count);
					request.setAttribute("map", map);
					request.setAttribute("mxList", mxList);
					request.setAttribute("exportData", "数据量超过加载最大限制，请筛选！");
					return null;
				}
				buffer.append(" order by "+zy_zj+" asc");
				if("export".equals(cztype)){
					mxList = this.defaultDao.findSQL(buffer.toString());
					request.setAttribute("mxList", mxList);
				}else{
					mxList = this.defaultDao.findSQLByPage(buffer.toString(), offset, pagesize);
					request.setAttribute("mxList", mxList);
				}
			}else{
				request.setAttribute("title", null);
				request.setAttribute("mxList", null);
			}
		}
		map.put("uri", uri);
		map.put("pagesize", pagesize);
		map.put("rscount", count);
		map.put("currentpage", currentPage);
		request.setAttribute("rscount", count);
		request.setAttribute("map", map);
		return mxList;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getResourceInfo(HttpServletRequest request) throws Exception{
		List list = null;
		Object[] mxinfo = null;
		String zyid = request.getParameter("zyid")==null?"":request.getParameter("zyid");
		String keyid = request.getParameter("keyid")==null?"":request.getParameter("keyid");
		String cztype = request.getParameter("cztype")==null?"":request.getParameter("cztype");
		request.setAttribute("zyid", zyid);
		request.setAttribute("keyid", keyid);
		if(!StringUtil.isNull(zyid)){
			String sql = "select mx_id, mx_name, mx_ms, zy_id, zt, is_pic, is_show, " +
					" is_jm, zd_dx, zd_text, zd_value, zd_key_add, zy_order, data_type, " +
					" is_fanyi, fy_show_zd, fy_zdbm, zd_tj, fy_zd, fy_tabname, fy_tj, zd_gxzd, is_update "+
					" from xxpt_resouce_mx t where t.zy_id = "+zyid+" " +
					"and t.zt = 'T'  order by zy_order asc";
			list = this.defaultDao.findSQL(sql);
			if(list != null && list.size() > 0){
				//标题list
				request.setAttribute("title", list);
				//明细list
				sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info t where t.zy_id = "+zyid;
				List zyList = this.defaultDao.findSQL(sql);
				Object[] zys = (Object[])zyList.get(0);
				String tabname = zys[2]+"";
				String zykey = zys[3]+"";
				StringBuffer buffer = new StringBuffer("select ");
				for(int i = 0; i < list.size(); i++){
					Object[] objs = (Object[])list.get(i);
					if(i == 0){
						if("1".equals(objs[5])){
							buffer.append(" cast('"+objs[1]+"' as varchar2(100))");
						}else{
							//是否需要字典翻译
							if(objs[8] == null){
								//不需要翻译
								if("1".equals(objs[7]+"")){
									//加密字段
									buffer.append(" pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
								}else{
									if("DATE".equals(objs[13]+"")){
										buffer.append(" to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
									}else{
										buffer.append(objs[1]);
									}
								}
							}else{
								//字典翻译
								if("1".equals(objs[14]+"")){
//									if("1".equals(objs[7]+"")){
//										buffer.append(" pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') || '--' ||(select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
//										if(objs[11] != null){
//											buffer.append(objs[11]+"");
//										}
//										buffer.append(") ");
//									}else{
//										buffer.append(objs[1]+"||'--'||(select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
//										if(objs[11] != null){
//											buffer.append(objs[11]+"");
//										}
//										buffer.append(") ");
//									}
									if("1".equals(objs[7]+"")){
										//加密字段
										buffer.append(" pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
									}else{
										if("DATE".equals(objs[13]+"")){
											buffer.append(" to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
										}else{
											buffer.append(objs[1]);
										}
									}
								}else{
									if("1".equals(objs[7]+"")){
										buffer.append(" (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') ");
										if(objs[11] != null){
											buffer.append(objs[11]+"");
										}
										buffer.append(") ");
									}else{
										buffer.append(" (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
										if(objs[11] != null){
											buffer.append(objs[11]+"");
										}
										buffer.append(") ");
									}
								}
								
							}
						}
						
					}else{
						if("1".equals(objs[5])){
							buffer.append(", cast('"+objs[1]+"' as varchar2(100))");
						}else{
							//是否需要字典翻译
							if(objs[8] == null){
								//不需要翻译
								if("1".equals(objs[7]+"")){
									//加密字段
									buffer.append(", pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
								}else{
									if("DATE".equals(objs[13]+"")){
										buffer.append(", to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
									}else{
										buffer.append(","+objs[1]);
									}
								}
							}else{
								//字典翻译
								if("1".equals(objs[14]+"")){
//									if("1".equals(objs[7]+"")){
//										buffer.append(",pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"')||'--'|| (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') ");
//										if(objs[11] != null){
//											buffer.append(objs[11]+"");
//										}
//										buffer.append(") ");
//									}else{
//										buffer.append(", "+objs[1]+"||'--'||(select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
//										if(objs[11] != null){
//											buffer.append(objs[11]+"");
//										}
//										buffer.append(") ");
//									}
									if("1".equals(objs[7]+"")){
										//加密字段
										buffer.append(", pkg_encrypt_decrypt.decrypt_3key_mode("+objs[1]+", '"+SysConst.ASCDESC_KEY+"')");
									}else{
										if("DATE".equals(objs[13]+"")){
											buffer.append(", to_char("+objs[1]+", 'yyyy-MM-dd HH24:mi:ss')");
										}else{
											buffer.append(","+objs[1]);
										}
									}
								}else{
									if("1".equals(objs[7]+"")){
										buffer.append(", (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = pkg_encrypt_decrypt.decrypt_3key_mode(t."+objs[1]+", '"+SysConst.ASCDESC_KEY+"') ");
										if(objs[11] != null){
											buffer.append(objs[11]+"");
										}
										buffer.append(") ");
									}else{
										buffer.append(", (select "+objs[9]+" from "+objs[8]+" a where a."+objs[10]+" = t."+objs[1]+" ");
										if(objs[11] != null){
											buffer.append(objs[11]+"");
										}
										buffer.append(") ");
									}
								}
								
							}
						}
						
					}
					
				}
				buffer.append(" from "+tabname+" t where t."+zykey+"='"+keyid+"'");
				List mxList = this.defaultDao.findSQL(buffer.toString());
				mxinfo = (Object[])mxList.get(0);
				request.setAttribute("mx", mxinfo);
				
				
				//查看举报序号对应的图片库
				String sqlPhotos ="select cid,photo_str,photo_type from v_xxpt_photos where cid='"+keyid+"' order by to_number(photo_type) asc";
				List photos = this.defaultDao.findSQL(sqlPhotos);
				request.setAttribute("photos", photos);
				
				
				
				//如果是查看，则查询审批信息
				if(!cztype.equals("shenpi")){
					String shsql = "select pkg_encrypt_decrypt.decrypt_3key_mode(t.shr, '"+SysConst.ASCDESC_KEY+"'), "+
					"pkg_encrypt_decrypt.decrypt_3key_mode(t.shrxm, '"+SysConst.ASCDESC_KEY+"'), "+
					"to_char(t.shsj, 'yyyy-MM-dd HH24:mi:ss'), "+
					"(select deptname from xxpt_department a where a.deptid = pkg_encrypt_decrypt.decrypt_3key_mode(t.shbm, '"+SysConst.ASCDESC_KEY+"')), "+
					"t.ship, "+
					"t.zt, t.tbyy"+
					" from "+tabname+" t where t."+zykey+"='"+keyid+"'";
					List shList = this.defaultDao.findSQL(shsql);
					if(shList != null && shList.size() > 0){
						Object[] shInfo = (Object[])shList.get(0);
						request.setAttribute("shInfo", shInfo);
					}else{
						request.setAttribute("shInfo", null);
					}
				}
				
			}else{
				request.setAttribute("title", null);
				request.setAttribute("mxList", null);
			}
		}
		return mxinfo;
	}
	
	@SuppressWarnings("unchecked")
	public Blob getImgBlob(HttpServletRequest request, String colname, String keyid, String zyid) throws Exception{
		String sql = "select zy_tabname, zy_zj from xxpt_resource_info t where t.zy_id = "+zyid;
		Object[] objs = (Object[])this.defaultDao.findSQL(sql).get(0);
		String tabname = objs[0]+"";
		sql = "select "+colname+" from "+tabname+" a where a."+objs[1]+" = '"+keyid + "'";
		List list = this.defaultDao.findSQL(sql);
		if(list != null && list.size() > 0){
			Blob blob = (Blob)list.get(0);
			return blob;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String shenPiResource(HttpServletRequest request) throws Exception{
		String result = request.getParameter("result");
		String reason = request.getParameter("reason")==null?"":request.getParameter("reason");
		String keyid = request.getParameter("keyid");
		String zyid = request.getParameter("zyid");
		String gxzd= request.getParameter("gxzd");
		String sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info t where t.zy_id = "+zyid;
		List list = this.defaultDao.findSQL(sql);
		if(list != null && list.size() > 0){
			//获取资源明细
			sql = "select mx_id, mx_name, mx_ms, zy_id, data_type, zt, is_pic, is_show, is_jm, is_update from xxpt_resouce_mx t where zy_id = '"+zyid+"' and is_update is not null";
			List list2 = this.defaultDao.findSQL(sql);
			Map<String, String> jmMap = new HashMap<String, String>();
			Map<String, String> typeMap = new HashMap<String, String>();
			for(int i = 0; i < list2.size(); i++){
				Object[] temp = (Object[])list2.get(i);
				jmMap.put(temp[9]+"", temp[8]+"");
				typeMap.put(temp[9]+"", temp[4]+"");
			}
			Object[] sourceObjs = (Object[])list.get(0);
			XxptUsers users = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
			sql = "update "+sourceObjs[2]+" set zt = '"+result+"', tbyy = '"+reason+"', " +
				"shr = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getUserid()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"shrxm = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getUsername()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"shsj = sysdate, " +
				"shbm = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getDeptid()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"ship = '"+getLoginIp(request)+"'";
			if(!StringUtil.isNull(gxzd)){
				String[] zds = gxzd.split("★");
				if(zds != null && zds.length > 0){
					for(int i = 0; i < zds.length; i++){
						String temp = zds[i];
						String[] vals = temp.split("卍");
						if("1".equals(jmMap.get(vals[0]))){
							sql += ", "+vals[0]+" = pkg_encrypt_decrypt.encrypt_3key_mode('"+vals[1]+"', '"+SysConst.ASCDESC_KEY+"')";
						}else{
							if("DATE".equals(typeMap.get(vals[0]))){
								sql += ", "+vals[0]+" = to_date('"+vals[1]+"', 'yyyy-MM-dd HH24:mi:ss')";
							}else{
								sql += ", "+vals[0]+" = "+vals[1];
							}
						}
					}
				}
			}
			sql += " where "+sourceObjs[3]+" = '"+keyid+"'";
			this.defaultDao.updateRepositoryBySql(sql);
			return "1";
		}
		return "0";
	}
	
	@SuppressWarnings("unchecked")
	public List getResourceImg(HttpServletRequest request, String keyid, String zyid, String colname) throws Exception{
		String sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info t where t.zy_id = "+zyid;
		List list = this.defaultDao.findSQL(sql);
		if(list != null && list.size() > 0){
			Object[] zyObjs = (Object[])list.get(0);
			String tabname = zyObjs[2]+"";
			tabname = tabname.toUpperCase();
			sql = "select column_name from user_tab_columns where table_name = '"+tabname+"' and DATA_TYPE = 'BLOB'";
			list  = this.defaultDao.findSQL(sql);
			if(list != null && list.size() > 0){
				sql = "select "+zyObjs[3]+", ";
				for(int i = 0; i < list.size(); i++){
					if(i == 0){
						sql += list.get(i);
					}else{
						sql += " ,"+list.get(i);
					}
				}
				sql += " from "+tabname + " where "+zyObjs[3] + " in ("+keyid+")";
			}
			list = this.defaultDao.findSQL(sql);
			if(list != null && list.size() > 0){
				return list;
			}else{
				return null;
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getFyzdList(HttpServletRequest request) throws Exception{
		List list = null;
		String showzd = request.getParameter("showzd");
		String zdbm = request.getParameter("zdbm");
		String zdtj = request.getParameter("zdtj");
		String fyzd = request.getParameter("fyzd");
		String tabname = request.getParameter("tabname");
		String fytj = request.getParameter("fytj");
		String xh = StringUtil.isNull(request.getParameter("xh")) ? "null":request.getParameter("xh");
//		String sql = "select "+fyzd+" from "+tabname + " where "+fytj+"='"+xh+"'";
//		list = this.defaultDao.findSQL(sql);
//		if(list != null && list.size()>0){
//			String wfxw = list.get(0)+"";
//			String[] xws = wfxw.split(",");
//			if(xws != null && xws.length > 0){
//				String wfxws = "";
//				for(int i = 0; i < xws.length; i++){
//					wfxws = wfxws + "'"+xws[i]+"',";
//				}
//				wfxws = wfxws.substring(0, wfxws.length()-1);
//				sql = "select "+showzd+" from "+zdbm + " where "+ zdtj + " in ("+wfxws+")";
//				list = this.defaultDao.findSQL(sql);
//			}
//			
//		}
		String sql = "select "+showzd+" from "+zdbm + " "+zdtj;
		list = this.defaultDao.findSQL(sql);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List getXxptSjzd(HttpServletRequest request, String dmlb, String xtmc, String dmz) throws Exception{
		List list = null;
		String sql = "select xtmc, dmz, dmsm1, dmsm2, dmsm3, dmlb from xxpt_sjzd where 1=1 ";
		if(!StringUtil.isNull(dmlb)){
			sql += " and dmlb = '"+dmlb+"'";
		}
		if(!StringUtil.isNull(xtmc)){
			sql += " and xtmc = '"+xtmc+"'";
		}
		if(!StringUtil.isNull(dmz)){
			sql += " and dmz = '"+dmz+"'";
		}
		list = this.defaultDao.findSQL(sql);
		return list;
	}
	
	/**
	 * 车管所的字典
	 * @param request
	 * @param dmlb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCgsSysSjzd(HttpServletRequest request, String dmlb) throws Exception {
		List list = null;
		String sql = "select xh,dmz,dmsm from sfrz_sjzd where 1=1 ";
		if(!StringUtil.isNull(dmlb)){
			sql += " and dmlb = '"+dmlb+"'";
		}
		
		list = this.defaultDao.findSQL(sql+" order by dmz asc ");
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	public String zhuanZhzx(HttpServletRequest request) throws Exception{
		String keyid = request.getParameter("keyid");
		String zyid = request.getParameter("zyid");
		String gxzd= request.getParameter("gxzd");
		String sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info t where t.zy_id = "+zyid;
		List list = this.defaultDao.findSQL(sql);
		if(list != null && list.size() > 0){
			//获取资源明细
			sql = "select mx_id, mx_name, mx_ms, zy_id, data_type, zt, is_pic, is_show, is_jm, is_update from xxpt_resouce_mx t where zy_id = '"+zyid+"' and is_update is not null";
			List list2 = this.defaultDao.findSQL(sql);
			Map<String, String> jmMap = new HashMap<String, String>();
			Map<String, String> typeMap = new HashMap<String, String>();
			for(int i = 0; i < list2.size(); i++){
				Object[] temp = (Object[])list2.get(i);
				jmMap.put(temp[9]+"", temp[8]+"");
				typeMap.put(temp[9]+"", temp[4]+"");
			}
			Object[] sourceObjs = (Object[])list.get(0);
			XxptUsers users = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
			sql = "update "+sourceObjs[2]+" set zhzxzt = '0', " +
				"shr = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getUserid()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"shrxm = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getUsername()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"shsj = sysdate, " +
				"shbm = pkg_encrypt_decrypt.encrypt_3key_mode('"+users.getDeptid()+"', '"+SysConst.ASCDESC_KEY+"'), " +
				"ship = '"+getLoginIp(request)+"'";
			if(!StringUtil.isNull(gxzd)){
				String[] zds = gxzd.split("★");
				if(zds != null && zds.length > 0){
					for(int i = 0; i < zds.length; i++){
						String temp = zds[i];
						String[] vals = temp.split("卍");
						if("1".equals(jmMap.get(vals[0]))){
							sql += ", "+vals[0]+" = pkg_encrypt_decrypt.encrypt_3key_mode('"+vals[1]+"', '"+SysConst.ASCDESC_KEY+"')";
						}else{
							if("DATE".equals(typeMap.get(vals[0]))){
								sql += ", "+vals[0]+" = to_date('"+vals[1]+"', 'yyyy-MM-dd HH24:mi:ss')";
							}else{
								sql += ", "+vals[0]+" = "+vals[1];
							}
						}
					}
				}
			}
			sql += " where "+sourceObjs[3]+" = '"+keyid+"'";
			this.defaultDao.updateRepositoryBySql(sql);
			return "1";
		}
		return "0";
	}
	
	public String replaceBase(String jkid,String basestr) throws Exception{
		String retstr = basestr;
		if(null != retstr && !"".equals(retstr)){
			List list = defaultDao.getRepositories("from XxptInterfaceInfo where jkid = '" + jkid + "'");
			if(null != list && list.size() > 0){
				XxptInterfaceInfo xii = (XxptInterfaceInfo) list.get(0);
				if(null != xii && null != xii.getZdm()){
					String zdm = xii.getZdm();
					if(zdm.indexOf(",") > 0){
						String[] zdms = zdm.split(",");
						for(String tempzdm : zdms){
							if(retstr.indexOf("<"+tempzdm+">") > 0){
								Integer zdmi = tempzdm.length();
								String tempstrs = "";
								String tempstre = "";
								tempstrs = retstr.substring(0,retstr.indexOf("<"+tempzdm+">") + zdmi + 2);
								tempstre = retstr.substring(retstr.indexOf("</"+tempzdm+">"));
								retstr = tempstrs + tempstre;
							}
						}
					}else{
						if(retstr.indexOf("<"+zdm+">") > 0){
							Integer zdmi = zdm.length();
							String tempstrs = "";
							String tempstre = "";
							tempstrs = retstr.substring(0,retstr.indexOf("<"+zdm+">") + zdmi + 2);
							tempstre = retstr.substring(retstr.indexOf("</"+zdm+">"));
							retstr = tempstrs + tempstre;
						}
					}
				}
			}
		}
		return retstr;
	}
	
	@SuppressWarnings("unchecked")
	public String updateInfo(HttpServletRequest request) throws Exception{
		String keyid = request.getParameter("keyid");
		String zyid = request.getParameter("zyid");
		String gxzd= request.getParameter("gxzd");
		//根据资源id获取资源信息
		String sql = "select zy_id, zy_name, zy_tabname, zy_zj, zy_cxtj_out, zy_cxtj_in, zy_type, zt, zy_ms from xxpt_resource_info t where t.zy_id = "+zyid;
		List list = this.defaultDao.findSQL(sql);
		if(list != null && list.size() > 0){
			Object[] sourceObjs = (Object[])list.get(0);
			XxptUsers users = (XxptUsers)request.getSession().getAttribute(SysConst.USERBEAN);
			//获取资源明细
			sql = "select mx_id, mx_name, mx_ms, zy_id, data_type, zt, is_pic, is_show, is_jm, is_update from xxpt_resouce_mx t where zy_id = '"+zyid+"' and is_update is not null";
			list = this.defaultDao.findSQL(sql);
			Map<String, String> jmMap = new HashMap<String, String>();
			Map<String, String> typeMap = new HashMap<String, String>();
			for(int i = 0; i < list.size(); i++){
				Object[] temp = (Object[])list.get(i);
				jmMap.put(temp[9]+"", temp[8]+"");
				typeMap.put(temp[9]+"", temp[4]+"");
			}
			sql = "update "+sourceObjs[2]+" set "+sourceObjs[3]+" = "+sourceObjs[3];
			if(!StringUtil.isNull(gxzd)){
				String[] zds = gxzd.split("★");
				if(zds != null && zds.length > 0){
					for(int i = 0; i < zds.length; i++){
						String temp = zds[i];
						String[] vals = temp.split("卍");
						if("1".equals(jmMap.get(vals[0]))){
							sql += ", "+vals[0]+" = pkg_encrypt_decrypt.encrypt_3key_mode('"+vals[1]+"', '"+SysConst.ASCDESC_KEY+"')";
						}else{
							if("DATE".equals(typeMap.get(vals[0]))){
								sql += ", "+vals[0]+" = to_date('"+vals[1]+"', 'yyyy-MM-dd HH24:mi:ss')";
							}else{
								if(vals.length>1){
									sql += ", "+vals[0]+" = '"+vals[1]+"'";
								}else{
									sql += ", "+vals[0]+" = ''";
								}								
							}
						}						
					}
				}
			}
			sql += " where "+sourceObjs[3]+" = '"+keyid+"'";
			this.defaultDao.updateRepositoryBySql(sql);
			return "1";
		}
		return "0";
	}
	
	public void addObject(Object obj) throws Exception{
		this.defaultDao.addRepository(obj);
	}
	
	public String getLoginIp(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public DefaultDao getDefaultDao() {
		return defaultDao;
	}

	public void setDefaultDao(DefaultDao defaultDao) {
		this.defaultDao = defaultDao;
	}
	
	public IXxfbptDao getXxfbptDao() {
		return xxfbptDao;
	}

	public void setXxfbptDao(IXxfbptDao xxfbptDao) {
		this.xxfbptDao = xxfbptDao;
	}

	public IVioDao getVioDao() {
		return vioDao;
	}

	public void setVioDao(IVioDao vioDao) {
		this.vioDao = vioDao;
	}
	
}
