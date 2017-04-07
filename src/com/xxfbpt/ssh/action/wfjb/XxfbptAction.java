package com.xxfbpt.ssh.action.wfjb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.xxfbpt.global.SysConst;
import com.xxfbpt.ssh.action.BaseAction;
import com.xxfbpt.ssh.service.IXxfbptService;
import com.xxfbpt.ssh.service.wfjb.IXxptUserService;
import com.xxfbpt.util.FileTools;
import com.xxfbpt.util.FilederToZip;
import com.xxfbpt.util.JsonUtil;
import com.xxfbpt.util.PropertiesHelper;

public class XxfbptAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4577976511806849199L;
	private static final Logger log = Logger.getLogger(XxfbptAction.class);
	private IXxfbptService xxfbptServiceimpl;
	private IXxptUserService xxptUserService;
	private String chineseName;
	private String filename;		//下载文件名
	private String inputStream; //下载文件流
	private String zipName;
	private int currentpage = 1;
	
	
	/**
	 * 获取资源列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getResourcemx() throws Exception{
		log.info("XxfbptAction method getResourcemx.....");
		List resourceList = this.xxptUserService.getDeptResorceInfo(request);
		request.setAttribute("resourceList", resourceList);
		this.xxfbptServiceimpl.getResourcemx(request, null, currentpage, "select");
		String type = request.getParameter("type");
		String zt = request.getParameter("zt");
		request.setAttribute("type", type);
		request.setAttribute("zt", zt);
		return "resourMx";
	}
	
	/**
	 * 获取资源详情
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getResourceDetail() throws Exception{
		log.info("XxfbptAction method getResourceDetail.....");
		String resouceType = request.getParameter("resouceType");
		String cztype = request.getParameter("cztype");
		this.xxfbptServiceimpl.getResourceInfo(request);
		String zyid = request.getParameter("zyid")==null?"":request.getParameter("zyid");
		List shjg = this.xxfbptServiceimpl.getXxptSjzd(request, "SHZT", zyid, null);
		List tbyy = this.xxfbptServiceimpl.getXxptSjzd(request, "TBYY", zyid, "");
		List phoTypes= this.xxfbptServiceimpl.getCgsSysSjzd(request,"PHOTO_TYPE");
		request.setAttribute("phoTypes",phoTypes);
		request.setAttribute("resouceType", resouceType);
		request.setAttribute("cztype", cztype);
		request.setAttribute("shjg", shjg);
		request.setAttribute("tbyy", tbyy);
		return "resourceDetail";
	}
	
	/**
	 * 资源审批
	 * @return
	 * @throws Exception
	 */
	public String shenPiResource() throws Exception{
		log.info("XxfbptAction method shenPiResource.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String result = this.xxfbptServiceimpl.shenPiResource(request);
			out.println(result);
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 转指挥中心审批
	 * @return
	 * @throws Exception
	 */
	public String zhuanZhzx() throws Exception{
		log.info("XxfbptAction method shenPiResource.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String result = this.xxfbptServiceimpl.zhuanZhzx(request);
			out.println(result);
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 转指挥中心审批
	 * @return
	 * @throws Exception
	 */
	public String updateInfo() throws Exception{
		log.info("XxfbptAction method shenPiResource.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String result = this.xxfbptServiceimpl.updateInfo(request);
			out.println(result);
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	// 读取BLOB照片
	@SuppressWarnings("deprecation")
	public String getPic(){
		ServletOutputStream sos = null;
		InputStream inputStream = null;
		try{
			// 从session中取得Blob照片
			String colname = request.getParameter("colname");
			String keyid = request.getParameter("keyid");
			String zyid = request.getParameter("zyid");
			Blob blobv = this.xxfbptServiceimpl.getImgBlob(request, colname, keyid, zyid);
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
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(sos != null){
				try{
					sos.close();
				}
				catch (Exception e) {
					
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 违法举报导出
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String exportWfjbExcel() throws Exception{
		PrintWriter out = null;
		out = response.getWriter();
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		List list = this.xxfbptServiceimpl.getResourcemx(request, null, currentpage, "export");
		String zyname = request.getAttribute("zyname")+"";
		this.setChineseName(zyname+".xls");
		HSSFWorkbook wb=new HSSFWorkbook();
		
		HSSFCellStyle style1=wb.createCellStyle();
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font=wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font);
		
		HSSFCellStyle style2=wb.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);
		
		HSSFSheet sheet=wb.createSheet(zyname);
		sheet.setDefaultColumnWidth(20);
		List titleList = (List)request.getAttribute("title");
		String path = this.getClass().getResource("/").getPath().substring(1);
		String filepath = path+SysConst.XTFILEPATH;
		String uploadpath = new String(PropertiesHelper.readValue(filepath,"uploadpath").getBytes("iso-8859-1"), "utf-8");
		UUID uuidd = UUID.randomUUID();
		String uuid = uuidd.toString().replace("-", "");
		FileTools.makeDirectory(uploadpath+"/"+uuid);
		if(titleList != null && titleList.size() > 0){
			int size = 0;
			for(int i = 0; i < titleList.size(); i++){
				Object[] objs = (Object[])titleList.get(i);
				if("1".equals(objs[5])){
					continue;
				}
				size = size+1;
			}
			String[] title = new String[size];
			size = 0;
			for(int i = 0; i < titleList.size(); i++){
				Object[] objs = (Object[])titleList.get(i);
				if("1".equals(objs[5])){
					continue;
				}
				title[size] = objs[2]+"";
				size = size+1;
			}
			HSSFRow row=sheet.createRow(0);
			row.setHeight((short)380);
			int n=0;
			for(String str:title){
				HSSFCell cell=row.createCell(n);
				cell.setCellStyle(style1);
				cell.setCellValue(str);
				n++;
			}
			for(int i=0;i<list.size();i++){
				Object[] bean=(Object[])list.get(i);
				HSSFRow h=sheet.createRow(i+1);
				h.setHeight((short)350);
				int col = 0;
				for(int j = 0; j < bean.length; j++){
					Object[] objs = (Object[])titleList.get(j);
					if("1".equals(objs[5])){
						continue;
					}
					createCell(col,bean[j]==null?"":bean[j]+"",h,style2);
					col = col + 1;
				}
			}
			//ByteArrayOutputStream baos=new ByteArrayOutputStream();
			FileOutputStream fout = new FileOutputStream(uploadpath+"/"+uuid+"/"+zyname+".xls");
			wb.write(fout);
			//ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
		}
		//导图片到服务器端
		if(list != null && list.size() > 0){
			String keyids = "";
			for(int i = 0; i < list.size(); i++){
				Object[] bean=(Object[])list.get(i);
				keyids += "'"+bean[0]+"', ";
			}
			keyids = keyids.substring(0, keyids.length() - 2);
			String zyid = request.getParameter("zyid")==null?"":request.getParameter("zyid");
			List blobs = this.xxfbptServiceimpl.getResourceImg(request, keyids, zyid, "");
			if(blobs != null && blobs.size() > 0){
				InputStream inpuStream = null;
				FileOutputStream outStream = null;
				Blob blob = null;
				for(int i = 0;i < blobs.size(); i++){
					Object[] temp = (Object[])blobs.get(i);
					for(int j = 1; j < temp.length; j++){
						blob = (Blob)temp[j];
						if(blob != null){
							inpuStream = blob.getBinaryStream();
							outStream = new FileOutputStream(uploadpath+"/"+uuid+"/"+temp[0]+"_"+j+".jpg");
							byte[] buf = new byte[1024];
							int len = 0;
							while((len = inpuStream.read(buf)) != -1){
								outStream.write(buf, 0, len);
							}
							inpuStream.close();
							outStream.close();
						}
					}
				}
			}
			//打成压缩包
			zipName = uploadpath+"/"+uuid;
			FilederToZip.zip(zipName, zipName+".zip");
			out.print(zipName);
		}else{
			out.print("0");
		}
		//下载到本地
		return null;
	}
	
	/**
	 * 数据字典对应翻译
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getZdfy() throws Exception{
		log.info("XxfbptAction method getZdfy.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			List list = this.xxfbptServiceimpl.getFyzdList(request);
			String jsonString = JsonUtil.list2json(list);
			out.println(jsonString);
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 根据资源获取对应状态字典
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String getZyztzd() throws Exception{
		log.info("XxfbptAction method getZyztzd.....");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setContentType("text/xml; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String zyid = request.getParameter("zyid");
			List list = this.xxfbptServiceimpl.getXxptSjzd(request, "SHZT", zyid, "");
			String jsonString = JsonUtil.list2json(list);
			out.println(jsonString);
		} catch (Exception e) {
			String exceptionstr = "异常信息:";
			e.printStackTrace();
			log.error(e);
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			String estr = e.getMessage();
			if(estr != null){
				estr = estr.replaceAll("\r", "");
				estr = estr.replaceAll("\n", "");
				estr = estr.replaceAll("\t", "");
				estr = estr.replaceAll("\f", "");
				estr = estr.replaceAll("\b", "");
				exceptionstr += estr + "</br>文件名:"
						+ stackTraceElement.getFileName() + "</br>行数:"
						+ stackTraceElement.getLineNumber() + "</br>方法名:"
						+ stackTraceElement.getMethodName();
			}else{
				exceptionstr += " 获取连接异常";
			}
			out.println(exceptionstr);
		}
		return null;
	}
	
	/**
	 * 下载
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String downLoad() throws Exception{
		return "SUCCESS";
		
	}
	
	public InputStream getInputStream() throws Exception {
		InputStream inputStream = null;
		File downfile = new File(zipName+".zip");
		inputStream = new FileInputStream(downfile);
		setFilename(downfile.getName());
		FileTools.deleteFile(zipName);
		return inputStream;
		
	}
	
	public void setInputStream(String inputStream) {
		this.inputStream = inputStream;
	}
	
	private void createCell(int i,String param,HSSFRow row,HSSFCellStyle style) {
		HSSFCell cell=row.createCell(i);
		cell.setCellStyle(style);
		cell.setCellValue(param);
	}
	
	public void setChineseName(String chineseName) throws Exception {
		chineseName=new String(chineseName.getBytes("GBK"),"ISO-8859-1");
		this.chineseName = chineseName;
	}
	
	public IXxfbptService getXxfbptServiceimpl() {
		return xxfbptServiceimpl;
	}
	public void setXxfbptServiceimpl(IXxfbptService xxfbptServiceimpl) {
		this.xxfbptServiceimpl = xxfbptServiceimpl;
	}

	public IXxptUserService getXxptUserService() {
		return xxptUserService;
	}

	public void setXxptUserService(IXxptUserService xxptUserService) {
		this.xxptUserService = xxptUserService;
	}

	public String getChineseName() {
		return chineseName;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getZipName() {
		return zipName;
	}

	public void setZipName(String zipName) {
		this.zipName = zipName;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}
	

}
