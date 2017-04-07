package com.xxfbpt.common;

import org.apache.log4j.Logger;

import com.xxfbpt.global.SysConst;
import com.xxfbpt.ssh.action.wfjb.XxfbptAction;
import com.xxfbpt.util.FileTools;
import com.xxfbpt.util.PropertiesHelper;

public class TimerTask {
	private static final Logger log = Logger.getLogger(XxfbptAction.class);
	
	public void TimerTask(){
		
		try {
			log.info("TimerTask");
			String path = this.getClass().getResource("/").getPath().substring(1);
			String filepath = path+SysConst.XTFILEPATH;
			String uploadpath = new String(PropertiesHelper.readValue(filepath,"uploadpath").getBytes("iso-8859-1"), "utf-8");
			FileTools.deleteFile2(uploadpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
