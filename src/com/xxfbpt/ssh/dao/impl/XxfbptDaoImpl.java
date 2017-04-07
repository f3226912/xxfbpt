package com.xxfbpt.ssh.dao.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import sun.misc.BASE64Encoder;

import com.xxfbpt.ssh.dao.IXxfbptDao;
import com.xxfbpt.util.StringUtil;
import com.xxfbpt.webservice.ParseXML;

public class XxfbptDaoImpl extends HibernateDaoSupport implements IXxfbptDao{

	/**
	 * 加密
	 */
	@SuppressWarnings("deprecation")
	public String getEncrypt(String value,String p_key) throws Exception {
		String encryptValue="";
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
		Session session = sf.getCurrentSession();				//获取session	getCurrentSession方法获取session后 spring自动管理事务,不须手动关闭
		try {
			conn = session.connection();
			proc = conn.prepareCall("{? = call PkG_ENCRYPT_DECRYPT.ENCRYPT_3KEY_MODE(?,?)}");
			proc.registerOutParameter(1, OracleTypes.CLOB);	//定义返回游标类型
			proc.setString(2,value);							//传入参数值
			proc.setString(3,p_key);
			proc.execute();
			encryptValue= proc.getString(1);
		} catch (Exception re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (proc != null) {
				proc.close();
				proc = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
   		return encryptValue;
	}
	
	/**
	 * 解密
	 */
	@SuppressWarnings("deprecation")
	public String getDecrypt(String value,String p_key) throws Exception {
		String encryptValue="";
		Connection conn = null;
		CallableStatement proc = null;
		ResultSet rs = null;
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
		Session session = sf.getCurrentSession();				//获取session	getCurrentSession方法获取session后 spring自动管理事务,不须手动关闭
		try {
			conn = session.connection();
			proc = conn.prepareCall("{? = call PkG_ENCRYPT_DECRYPT.DECRYPT_3KEY_MODE(?,?)}");
			proc.registerOutParameter(1, OracleTypes.CLOB);	//定义返回游标类型
			proc.setString(2, value);							//传入参数值
			proc.setString(3, p_key);
			proc.execute();
			encryptValue= proc.getString(1);
		} catch (Exception re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (proc != null) {
				proc.close();
				proc = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
   		return encryptValue;
	}
	
	@SuppressWarnings("deprecation")
	public String getXxptProc(String jkid, String srcs, String picStr) throws Exception{
		String result = "";
		Connection conn = null;
		CallableStatement proc = null;
		Statement stc = null;
		ResultSet rs = null;
		SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
		Session session = sf.getCurrentSession();				//获取session	getCurrentSession方法获取session后 spring自动管理事务,不须手动关闭
		String jbxh = "";
//		byte[] data = null;
//		// 读取图片字节数组
//		try {
//			InputStream in = new FileInputStream("c:\\printtx.jpg");
//			data = new byte[in.available()];
//			in.read(data);
//			in.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// 对字节数组Base64编码
//		BASE64Encoder encoder = new BASE64Encoder();
//		String imgStr = encoder.encode(data);
//		srcs = imgStr;
//		System.out.println(srcs.length());
		try {
//			conn = session.connection();
//			if("WFJB0001".equals(jkid)){   //违法举报
//				//生成举报序号
//				stc = conn.createStatement();
//				rs = stc.executeQuery("select to_char(sysdate, 'yyyyMMdd')||lpad(SEQ_WFJBPHOTO_XH.Nextval, 5, '0') from dual");
//				while(rs.next()){
//					jbxh = rs.getString(1);
//				}
//				if(!StringUtil.isNull(picStr)){
//					uploadPic(jkid, jbxh, picStr);
//				}
//			}
//			if(conn != null){
//				conn.close();
//			}
			conn = session.connection();
			proc = conn.prepareCall("{? = call pkg_xxpt_glxt.fuc_xxpt_main(?,?)}");
			proc.registerOutParameter(1, OracleTypes.CLOB);
			proc.setString(2, jkid);							//传入参数值
			proc.setString(3, srcs);
			proc.execute();
//			oracle.sql.CLOB clob = (oracle.sql.CLOB)proc.getClob(1);
//			BufferedReader in = new BufferedReader(clob.getCharacterStream());
//			int c ;
//			while((c = in.read()) != -1){
//				result.append(in.readLine());
//			}
			result = proc.getString(1);
		} catch (Exception re) {
			re.printStackTrace();
			throw re;
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if(stc != null){
				stc.close();
				stc = null;
			}
			if (proc != null) {
				proc.close();
				proc = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
   		return result;
	}
	
	/**
	 * 上传图片
	 * @param jkic
	 * @param picStr
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String uploadPic(String jkid, String jbxh, String picStr) throws Exception{
		List list = ParseXML.dom4jParsePicList(picStr);
		if(list != null && list.size() > 0){
			SessionFactory sf = this.getHibernateTemplate().getSessionFactory();
			Session session = sf.getCurrentSession();	
			for(int j = 0; j < list.size(); j++){
				if(!StringUtil.isNull(list.get(j)+"")){
					Connection con = null;
					Statement stc = null;
					ResultSet rs = null;
					oracle.sql.BLOB blob = null;
					OutputStream os = null;
					byte[] bytes = StringUtil.base4ToByte(list.get(j).toString());
					try {
						con = session.connection();
						stc = con.createStatement();
						con.setAutoCommit(false);//取消自动提交
						// 插入一个空对象empty_blob()   
						//int c=stc.executeUpdate("update xxpt_wfjb_photo  set JBTP"+(j+1)+"_OUT = empty_blob() where jbxh='"+jbxh+"'");
						if(j == 0){
							int c=stc.executeUpdate("insert into xxpt_wfjb_photo(xh,jbxh, JBTP"+(j+1)+"_OUT, syn_type, scsj, syn_flag) values(SEQ_WFJBPHOTO_XH.nextval, '"+jbxh+"', empty_blob(), '0', sysdate, 'UW')");
						}else{
							int c=stc.executeUpdate("update xxpt_wfjb_photo  set JBTP"+(j+1)+"_OUT = empty_blob() where jbxh='"+jbxh+"'");
						}
						//锁定数据行进行更新，注意"for update"语句   
						rs = stc.executeQuery("select JBTP"+(j+1)+"_OUT from xxpt_wfjb_photo where jbxh = '"+jbxh+"' for update");
						if(rs.next()){
							// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB  
							blob = (oracle.sql.BLOB)rs.getBlob("JBTP"+(j+1)+"_OUT");
							// 通过getBinaryOutputStream()方法获得向数据库中插入图片的"管道"
							os = blob.getBinaryOutputStream();
							InputStream is = new ByteArrayInputStream(bytes);
							//依次读取流字节,并输出到已定义好的数据库字段中
							int i = 0;
							while((i = is.read()) != -1){
								os.write(i);
							}
						}
						if(os!=null){
							os.flush();
							os.close();
						}	
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}finally{
						if(rs != null){
							rs.close();
						}
						if(stc != null){
							stc.close();
						}
						if(con != null){
							con.commit();
							con.setAutoCommit(true);
							con.close();
						}
						if(session != null){
							session.flush();
							session.clear();
						}
					}
				}
			}
		}
		return null;
	}

}
