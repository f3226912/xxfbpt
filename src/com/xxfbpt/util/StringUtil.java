package com.xxfbpt.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sun.misc.BASE64Decoder;

/**
 * @包名:com.ckjg.util
 * @文件名:StringUtil.java
 * @作者:wy E-mail:wyong@szjst.com.cn
 * @创建日期:2013-7-27
 * @描述:
 * @版本:V 1.0
 */
public class StringUtil {
	
	/**
     * <默认构造函数>
     */
    private StringUtil()
    {
        
    }
    
    /**
     * 验证是否为空
     * @author wangyong
     * @param value 需要验证的字符串
     * @return boolean
     */
    public static boolean isNull(String value)
    {
        if (value == null)
        {
            return true;
        }
        if("".equals(value)){
        	return true;
        }
        if(value.length() == 0 || value.trim().length() == 0){
        	return true;
        }
        return false;
    }
    
    /**
     * MD5加密
     * @param s
     * @return
     */
    public static String encode(String s){

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
    
    public static byte[] base4ToByte(String base64) throws Exception{
		byte[] bytes = null;
		if(null != base64 && !"".equals(base64)){
			try {
				BASE64Decoder decoder = new BASE64Decoder();
				// Base64解码
				bytes = decoder.decodeBuffer(base64);
				for (int i = 0; i < bytes.length; ++i) {
					if (bytes[i] < 0) {// 调整异常数据
						bytes[i] += 256;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		return bytes;
	}
    
    public static boolean Base64ToImage(String imgStr, String imgFilePath) {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null)
			// 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] bytes = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < bytes.length; ++i) {
				if (bytes[i] < 0) {// 调整异常数据
					bytes[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
    public static void main(String[] args) throws SQLException {
		StringUtil util = new StringUtil();
		Connection conn = null;
		CallableStatement proc = null;
		Statement stc = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@100.100.21.33:1521:tpdb1", "vio_interface", "vii501$$");
			proc = conn.prepareCall("select * from v_user");
			rs = proc.executeQuery();
//			while(rs.next()){
//				String usercode = rs.getString("user_code");
//				String password = rs.getString("pwd");
//				String oid = rs.getString("oid");
//				String md5 = util.encode(usercode+password);
//				System.out.println(usercode+","+password+","+md5);
//				stc = conn.createStatement();
//			    stc.executeUpdate("update v_user set pwdmd5 = '"+md5+"' where oid = "+oid);
//			}
			String md5 = util.encode("010643010643");
			stc = conn.createStatement();
		    stc.executeUpdate("update v_user set pwdmd5 = '"+md5+"' where user_code = '010643'");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				rs.close();
			}
			if(proc != null){
				proc.close();
			}
			if(stc != null){
				stc.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}

}
