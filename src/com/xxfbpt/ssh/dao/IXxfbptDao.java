package com.xxfbpt.ssh.dao;

public interface IXxfbptDao {
	
	/**
	 * 解密
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String getDecrypt(String value,String p_key) throws Exception;
	
	/**
	 * 加密
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String getEncrypt(String value,String p_key) throws Exception ;
	
	/**
	 * 业务调度
	 * @return
	 * @throws Exception
	 */
	public String getXxptProc(String jkid, String srcs, String picStr) throws Exception;

}
