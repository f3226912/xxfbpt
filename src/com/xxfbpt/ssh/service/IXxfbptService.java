package com.xxfbpt.ssh.service;

import java.sql.Blob;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;


/**
 * 信息平台业务逻辑层
 * @author wy
 * @date 2014-10-09
 *
 */
public interface IXxfbptService {
	
	/**
	 * 验证用户信息
	 * @param userid
	 * @param userpwd
	 * @return 成功返回用户密钥， 否则返回-1
	 * @throws Exception
	 */
	public XxptInterfaceUserinfo getUserInfo(String userid, String userpwd) throws Exception;
	
	/**
	 * 查询用户权限
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getUserinterface(String userid) throws Exception;
	
	/**
	 * 查询接口信息
	 * @param jkid
	 * @return
	 * @throws Exception
	 */
	public XxptInterfaceInfo getInterfaceInfo(String jkid) throws Exception;
	
	/**
	 * 业务调度
	 * @param jkid
	 * @param srcs
	 * @return
	 * @throws Exception
	 */
	public String getXxptProc(String jkid, String srcs, String picStr) throws Exception;
		
	/**
	 * 获取资源明细
	 * @param request
	 * @param zyid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResourcemx(HttpServletRequest request, String zyid, int currentPage, String cztype) throws Exception;
	
	/**
	 * 获取资源详情
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Object[] getResourceInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 查找图片
	 * @return
	 * @throws Exception
	 */
	public Blob getImgBlob(HttpServletRequest request, String colname, String keyid, String zyid) throws Exception;
	
	/**
	 * 审批资源信息
	 * @param request
	 * @return 0-资源信息不存在  1-审批成功 2-
	 * @throws Exception
	 */
	public String shenPiResource(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取资源图片
	 * @param request
	 * @param keyid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResourceImg(HttpServletRequest request, String keyid, String zyid, String colname) throws Exception;
	
	/**
	 * 添加对象
	 * @param obj
	 * @throws Exception
	 */
	public void addObject(Object obj) throws Exception;
	
	/**
	 * 数据字典对应翻译
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getFyzdList(HttpServletRequest request) throws Exception;
	
	/**
	 * 查询平台数据字典
	 * @param request
	 * @param dmlb
	 * @param xtmc
	 * @param dmz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getXxptSjzd(HttpServletRequest request, String dmlb, String xtmc, String dmz) throws Exception;
	
	/**
	 * 车管所的字典
	 * @param request
	 * @param dmlb
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCgsSysSjzd(HttpServletRequest request, String dmlb) throws Exception ;
	/**
	 * 转指挥中心审批
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String zhuanZhzx(HttpServletRequest request) throws Exception;
	
	/**
	 * 修改信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String updateInfo(HttpServletRequest request) throws Exception;
	/**
	 * 替换图片字节码
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String replaceBase(String jkid,String basestr) throws Exception;
	
}
