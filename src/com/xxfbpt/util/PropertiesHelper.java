package com.xxfbpt.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @包名:com.ckjg.util
 * @文件名:PropertiesHelper.java
 * @作者:wy E-mail:wyong@szjst.com.cn
 * @创建日期:2014-2-19
 * @描述:
 * @版本:V 1.0
 */
public class PropertiesHelper {
	private static final Logger logger = Logger.getLogger(PropertiesHelper.class);
	private long lastModify = 0L;//资源文件最后修改时间
	private Properties prop = new Properties();
	private static PropertiesHelper instance = new PropertiesHelper();
	
	public static void main(String[] args) {
    	//getAddress();
    	PropertiesHelper pro = new PropertiesHelper();
    	try {
//			String str = pro.getValue("db.AdminloginId");
//			System.out.println("str:"+str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		String path = System.getProperty("user.dir");//项目路径
//        String filepath= path +"\\src\\emailSender.properties";
        String path = PropertiesHelper.class.getResource("/").getPath();//classe下路径
        String filepath= "D:\\Tomcat6.0\\webapps\\ckjg\\WEB-INF\\classes\\xtpagram.properties";
        System.out.println(path+" 1=====");
        System.out.println(filepath+" 2=====");
        //filepath = PathUtil.getWebInfPath()+"src/emailSender.properties";
        //System.out.println("filepath:"+filepath);
//        PropertiesHelper.writeProperties(filepath,"email.user","wangyong");
//        PropertiesHelper.writeProperties(filepath,"email.password","wangyong");
//        PropertiesHelper.writeProperties(filepath,"email.address","wangyong@mail.zxd.com");
//        PropertiesHelper.writeProperties(filepath,"email.validate","true");
//        PropertiesHelper.writeProperties(filepath,"email.subject","This is a Test Mail!");
//        PropertiesHelper.writeProperties(filepath,"email.serverHost","192.168.18.98");
//        PropertiesHelper.writeProperties(filepath,"email.serverPort","25");
        
       //输出更改后的信息。
        System.out.println(PropertiesHelper.readValue(filepath,"xt.title"));
	}
    /**
     * 私有构造函数
     */
    private PropertiesHelper(){
    	
    }
    
    /**
     * 返回单例对象应用
     * @return
     */
    public static PropertiesHelper getInstance(){
    	return instance;
    }
    
    
    /**
     * 根据key获取value
     * 在工程中获取相对路径的方法：
     * this.getClass().getResource("工程路劲").getPath();
     * 
     * 
     * @param filepath
     * @param key
     * @return 读取到的值
     */
    public static String readValue(String filepath, String key){
    	Properties props = new Properties();
    	FileInputStream fileinputstream = null;
    	try {
			fileinputstream = new FileInputStream(filepath);
			props.load(fileinputstream);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				fileinputstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    /**
     * 读取properties的全部信息
     * @param filePath 文件的路径
     */
    @SuppressWarnings("unchecked")
	public static void readProperties(String filePath)
    {
        Properties props = new Properties();
        InputStream in=null;
        try{
            in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            Enumeration en = props.propertyNames();
            while(en.hasMoreElements())
            {
                String key = (String)en.nextElement();
                String property = props.getProperty(key);
                logger.info(key + " : " + property);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
	         try 
	         {
	             in.close();
	         } catch (IOException e)
	         {
	            e.printStackTrace();
	            logger.error("PropertiesHelper.readProperties() error", e);
	         }
        }
    }
    
    /**
     * 写入properties信息
     * @param filepath 要写入文件的路径
     * @param paramterName 要改变的值的key
     * @param paramterValue 要改变的内容
     */
    public static void writeProperties(String filepath, String paramterName, String paramterValue){
    	Properties props = new Properties();
    	OutputStream fos = null;
    	InputStream fis = null;
    	try {
    		 //输入流和输出流要分开处理， 放一起会造成写入时覆盖以前的属性   
			fis = new FileInputStream(filepath);
			 //先载入已经有的属性文件  //从输入流中读取属性列表（键和元素对）
			props.load(fis);
			 //追加新的属性   
			props.setProperty(paramterName, paramterValue);
			fis.close();
			//写入属性   
			fos = new FileOutputStream(filepath);
			//以适合使用load方法加载到Properties表中的格式，将此Properties表中的属性列表（键和元素对）写入输出流
			
			 //加载额外的内容
			String otherContent="########\n#append\n########";
	        props.store(fos,otherContent);//存入
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PropertiesHelper.writeProperties() error", e);
		}finally{
			try {
				fis.close();
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

}
