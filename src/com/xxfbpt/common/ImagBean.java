package com.xxfbpt.common;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author lzj
 * 
 *         该方法能保证主存中的i值是准确的 （每次运行完后 i的最终值是0） 能保证最大开启IO资源数不会大于 IOsize+1
 *         (偶尔出现正常阻塞线线程比被notify唤醒的线程抢先获得线程对象锁)
 */
public class ImagBean {
	private volatile int i = 0;
	final int IOsize = 3;

	public void writeImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		synchronized (this) {
			if (i >= IOsize) {
				try {
					// System.out.println("xh:"+request.getParameter("xh")+"==========wait=========="+i);
					wait();
					// System.out.println("xh:"+request.getParameter("xh")+"==========continue=========="+i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			i++;
			// System.out.println("xh:"+request.getParameter("xh")+"======exist I/O stream===="+i);
		}
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			//BufferedImage bufferedImage = null;
			ServletOutputStream sos = null;
			InputStream inputStream = null;
		try {
			// 模拟取数据
			Thread.sleep(500);
			// 输出文件
			// FileInputStream fis=new FileInputStream(
			// request.getSession().getServletContext().getRealPath("/pic1.JPG"));
			
			String keyid = request.getParameter("keyid") == null ? "" : request.getParameter("keyid").trim();
			String phType= request.getParameter("phType") == null ?"" : request.getParameter("phType").trim();
			if (!keyid.equals("")) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@100.100.21.33:1521:tpdb1", "jdcglk","glkjdc123");
				if (conn != null) {
					String sql = "select photo from  v_xxpt_photos where cid='" + keyid + "' and photo_type ='"+phType+"' order by photo_type asc";
					System.out.println(sql);
					st = conn.createStatement();
					rs = st.executeQuery(sql);
					response.setContentType("image/jpeg");
					if (rs.next()) {
						Blob blobv = rs.getBlob(1);
//						in = blob.getBinaryStream();
//						bufferedImage = ImageIO.read(in);
						if(blobv != null){
							int length = (int) blobv.length();
							byte[] byte_array = blobv.getBytes(1, length);
							response.setContentType("image/jpeg");
							sos = response.getOutputStream();
							for (int i = 0; i < byte_array.length; i++) {
								sos.write(byte_array[i]);
							}
							byte_array = null;
						}else{
							String filepath = "/"+request.getRealPath("/")+"/images/cp.gif";
							inputStream = new FileInputStream(new File(filepath));
							byte[] b = new byte[1024];
							int len = -1;
							sos = response.getOutputStream();
							while((len = inputStream.read(b, 0, 1024)) != -1){
								sos.write(b, 0, len);
							}
						}
					}
				}
			}
			response.flushBuffer();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sos!=null){
				sos.flush();
				sos.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
			if (null != rs) {
				rs.close();
			}
			if (null != st) {
				st.close();
			}
			if (null != conn) {
				conn.close();
			}
			/**
			 * 放于此位置的原因： 不管是否一直等待，等睡眠时间一过，就将线程唤起；防止线程一直阻塞等待页面出现白屏。
			 */
			synchronized (this) {
				i--;
				notify();
				// System.out.println("xh:"+request.getParameter("xh")+"==========notify=========="+i);
			}
		}

	}
}
