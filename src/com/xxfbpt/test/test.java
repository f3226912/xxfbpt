package com.xxfbpt.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

import com.xxfbpt.ssh.dao.impl.DefaultDaoImpl;
import com.xxfbpt.util.DESCorder;

public class test {

	@Test
	public void testHaveEffectiveData() {
		try {
			// byte[] data = null;
			// // 读取图片字节数组
			// try {
			// InputStream in = new FileInputStream("c:\\blzt.gif");
			// data = new byte[in.available()];
			// in.read(data);
			// in.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// // 对字节数组Base64编码
			// BASE64Encoder encoder = new BASE64Encoder();
			// String imgStr = encoder.encode(data);
			// imgStr = imgStr.replaceAll(" ", "").replaceAll("\n",
			// "").replaceAll("\r", "");
			// imgStr = DESCorder.encryptModeToString(imgStr,
			// SysConst.ASCDESC_KEY);
			// // System.out.println(imgStr.length());
			// //StringUtil.Base64ToImage(imgStr, "c:\\test.jpg");
			// String srcs =
			// "<request><body><hphm>粤B88558</hphm><hpzl>01</hpzl><wfxw1>违法2</wfxw1><wfxw2>违法2</wfxw2><wfxw3>违法3</wfxw3><wfdd>东门</wfdd><wfsj>2014/10/21</wfsj><lrr>f3226912</lrr><lrrxm>小庄</lrrxm><lrrlxdh>13590197307</lrrlxdh><lrly>01</lrly><jbtp1>"
			// + imgStr +
			// "</jbtp1><jbtp2></jbtp2><jbtp3></jbtp3></body></request>";
			// //String srcs = "";
			// //微博举报内容写入报文
			// //String srcs =
			// "<request><body><wbyhm>wb01</wbyhm><wbnr>测试</wbnr><wbzhdz>http://www.baidu.com</wbzhdz><wbjblrsj>2014-11-27 00:56:05</wbjblrsj></body></request>";
			// srcs = DESCorder.encryptModeToString(srcs,
			// "94D863D9BE7FB032E6A19430CC892610");
			// //String xml = imgStr+"#"+imgStr2+"#"+imgStr;
			// //String xml =
			// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><pictures><picture>"+imgStr+"</picture></pictures></request>";
			// for (int i = 0; i < 1; i++) {
			// Client client = new Client(new URL(
			// "http://100.100.3.157:8089/xxfbpt/services/xxfbptservice?wsdl"));
			// Object[] results = client.invoke("xxptSchuding",
			// new Object[] { "WX02", "WX02@168", "1003", srcs });
			// System.out.println(results[0]);
			// }
			// Jqum0FygzHriAT8EhyXZF5/vZXCoqXLOVjuq2jESOKjsyNjhiYMNnVB2lcgoMhMHsG5E6upr31xdSDn75vx6l8uUyxxIffBfnHzEdHqlSQc=
			System.out
					.println(DESCorder
							.decryptMode(
									"Jqum0FygzHriAT8EhyXZF5/vZXCoqXLOVjuq2jESOKireivvmUK2trFXA1MArJ9TamReKdJtYfjzIHn9+mMW28uUyxxIffBfz9jxlbC6dOk=",
									"ZG20YD14WFJB2013XXLRJK16", "utf-8"));
			System.out
					.println(DESCorder
							.encryptModeToString(
									"<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><wfdd>东门</wfdd></request>",
									"ZG20YD14WFJB2013XXLRJK16"));
			// File file = new File("C:\\printzj.jpg");
			// test t = new test();
			// t.editBusByXSZZP(file);
			// t.zip("E:\\fileupload\\wfjb");//你要压缩的文件夹84011110

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int editBusByXSZZP(File file) throws Exception {
		Connection con = null;
		Statement stc = null;
		ResultSet rs = null;
		int count = 0;
		if (file != null) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection(
						"jdbc:oracle:thin:@100.100.3.157:1521:orcl", "xxpt",
						"xxpt!402");
				stc = con.createStatement();
				con.setAutoCommit(false);// 取消自动提交
				OutputStream os = null;
				// 插入一个空对象empty_blob()
				int c = stc
						.executeUpdate("update xxpt_wfjb_photo  set JBTP2_OUT = empty_blob() where xh='3'");
				// int
				// c=stc.executeUpdate("insert into xxpt_wfjb_photo(xh,jbxh, JBTP2_OUT) values('3', '2', empty_blob())");
				// 锁定数据行进行更新，注意"for update"语句
				rs = stc
						.executeQuery("select JBTP2_OUT from xxpt_wfjb_photo where xh = '3' for update");
				if (rs.next()) {
					// 得到java.sql.Blob对象后强制转换为oracle.sql.BLOB
					oracle.sql.BLOB blob = (oracle.sql.BLOB) rs
							.getBlob("JBTP2_OUT");
					// 通过getBinaryOutputStream()方法获得向数据库中插入图片的"管道"
					os = blob.getBinaryOutputStream();
					InputStream is = new FileInputStream(file);
					// 依次读取流字节,并输出到已定义好的数据库字段中
					int i = 0;
					while ((i = is.read()) != -1) {
						os.write(i);
					}
					count++;
				}
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (stc != null) {
					stc.close();
				}
				if (con != null) {
					con.commit();
					con.setAutoCommit(true);
					con.close();
				}
			}
		}
		return count;
	}

	/*
	 * inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
	 */
	public void zip(String inputFileName) throws Exception {
		String zipFileName = "c:\\test.zip"; // 打包后文件名字
		System.out.println(zipFileName);
		zip(zipFileName, new File(inputFileName));
	}

	private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zip(out, inputFile, "");
		System.out.println("zip done");
		out.close();
	}

	private void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			System.out.println(base);
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	@Test
	public void HibernateTest() {
		DefaultDaoImpl dao = new DefaultDaoImpl();
		try {
			List userList = dao.findSQLByPage("select * from scott.emp ",
					0, 10);
			System.out.println(userList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
