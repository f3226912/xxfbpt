package com.xxfbpt.ssh.service.wfjb;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.xxfbpt.ssh.hbm.XxptInterfaceInfo;
import com.xxfbpt.ssh.hbm.XxptInterfaceUserinfo;
import com.xxfbpt.ssh.hbm.XxptUsers;

public interface IXxptUserService {
	
	/**
	 * 查询用户信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<XxptUsers> getXxptUser(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取部门资源信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptResorceInfo(HttpServletRequest request) throws Exception;
	
	/**
	 * 修改密码
	 * @param usercode
	 * @param password
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Integer updatePwssword(String usercode, String password, HttpServletRequest request) throws Exception;
	
	/**
	 * 获取用户权限
	 * @param usercode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPower(String usercode) throws Exception ;
	
	/**
	 * 授信单位分页查询
	 * @param request
	 * @param currentpage
	 * @param cztype
	 * @return
	 * @throws Exception
	 */
	public List<XxptInterfaceUserinfo> getInterfaceUserList(HttpServletRequest request, int currentpage, String cztype) throws Exception;
	
	/**
	 * 查询接口信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<XxptInterfaceInfo> getInterfaceInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 查询授信单位集合
	 * @param request
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public List<XxptInterfaceUserinfo> getInterfaceUserInfo(HttpServletRequest request, String userid) throws Exception;
	
	/**
	 * 查询授信单位
	 * @param request
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public XxptInterfaceUserinfo getInterfaceUserInfoByuserId(HttpServletRequest request, String userid) throws Exception;
	
	/**
	 * 编辑授信单位
	 * @param request
	 * @param interfaceUserinfo
	 * @param jkids
	 * @throws Exception
	 */
	public void editInterfaceUserinfo(HttpServletRequest request, XxptInterfaceUserinfo interfaceUserinfo, String jkids) throws Exception;
	
	/**
	 * 查询权限list
	 * @param request
	 * @param sysMdlCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSysMdlList(HttpServletRequest request, String sysMdlCode) throws Exception;
	
	/**
	 * 查询平台用户信息
	 * @param request
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public XxptUsers getXxptUserInfo(HttpServletRequest request, String userid) throws Exception;
	
	/**
	 * 查找平台部门信息
	 * @param request
	 * @param deptid
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getXxptDeptList(HttpServletRequest request, String deptid) throws Exception;
	
	/**
	 * 平台用户分页查询
	 * @param request
	 * @param currentpage
	 * @param cztype
	 * @return
	 * @throws Exception
	 */
	public List<XxptUsers> getPtUserList(HttpServletRequest request, int currentpage, String cztype) throws Exception;
	
	/**
	 * 编辑平台用户信息
	 * @param request
	 * @param interfaceUserinfo
	 * @param jkids
	 * @throws Exception
	 */
	public void editPtUserinfo(HttpServletRequest request, XxptUsers user, String jkids) throws Exception;
	
	/**
	 *  部门分页查询
	 * @param request
	 * @param currentpage
	 * @param cztype
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request, int currentpage, String cztype) throws Exception;
	
	/**
	 * 资源查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getXxptResourceInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 部门资源信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptResourceList(HttpServletRequest request) throws Exception;
	
	/**
	 * 授权部门资源
	 * @param request
	 * @throws Exception
	 */
	public void editDeptResource(HttpServletRequest request) throws Exception;
	
	

}
