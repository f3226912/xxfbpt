package com.xxfbpt.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.UUID;

public class FileTools {
	static Long sorting = 0L;
	private static final Logger logger = Logger.getLogger(FileTools.class);

	public FileTools() {
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param String
	 *            删除路径
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deleteFile(String delpath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						logger.info("删除文件成功");
					} else if (delfile.isDirectory()) {
						deleteFile(delpath + "\\" + filelist[i]);
					}
				}
				file.delete();

			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile()   Exception:" + e.getMessage());
		}
		return true;
	}
	
	/**
	 * 删除某个文件夹下的所有文件夹和文件,不删除本文件夹
	 * 
	 * @param String
	 *            删除路径
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean deleteFile2(String delpath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "\\" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
						System.out.println("删除文件成功");
					} else if (delfile.isDirectory()) {
						deleteFile(delpath + "\\" + filelist[i]);
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 读取某个文件夹下的所有文件夹和文件
	 * 
	 * @param String
	 *            读取路径
	 * @param Long
	 *            pid 父id
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return boolean
	 */
	public static boolean readFile(String filepath)
			throws FileNotFoundException, IOException {
		try {
			String str = "";
			File file = new File(filepath);
			if (!file.isDirectory()) {
				// 根据文件名取到编号
				str = file.getName();
			} else if (file.isDirectory()) {
				String[] fileName = file.getName().split("_");
				String name = fileName[0];
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "/" + filelist[i]);
					if (!readfile.isDirectory()) {
						// System.out.println("path=" + readfile.getPath());
						// System.out.println("absolutepath="
						// + readfile.getAbsolutePath());
						// str = readfile.getName();
						// File f = new File(readfile.getAbsolutePath());

					} else if (readfile.isDirectory()) {
						readFile(filepath + "/" + filelist[i]);
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 根据文件名写文件
	 * 
	 * @param msg
	 * @param fileName
	 */
	public static void writeFile(String msg, String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			// Print formatted representations of objects to a text-output
			// stream.
			PrintWriter out = new PrintWriter(fw);
			out.println(msg);
			// fw.write("\r\n");

			out.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("Write file error!");
			e.printStackTrace();
		}
	}

	/**
	 * 根据文件名写文件
	 * 
	 * @param msgBuffer
	 * @param repx
	 * @param fileName
	 */
	public static void writeFile(StringBuffer msgBuffer, String repx,
			String fileName, String code) {
		try {
			// OutputStreamWriter fos = new OutputStreamWriter(
			//
			// new FileOutputStream(new File("c:\\2.csv")), "UTF-16LE");
			if ("".equals(code) || null == code) {
				code = "UTF-8";
			}
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, code);

			// Convenience class for writing character files.
			// boolean:true,append方式
			// FileWriter fw = new FileWriter(fileName, true);
			// Print formatted representations of objects to a text-output
			// stream.
			// PrintWriter out = new PrintWriter(fw);
			String msg = msgBuffer.toString();
			if (!"".equals(repx) || null != repx) {
				String[] stringArrayStrings = msg.split(repx);
				for (String s : stringArrayStrings) {
					osw.write(0xFEFF);   
					osw.write(s);
					osw.write("\r\n");
				}

			} else {
				osw.write(0xFEFF);   
				osw.write(msg);
				
			}
			osw.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("Write file error!");
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param delpath
	 * @param fileName
	 * @param suffer
	 * @return
	 */
	public static boolean deleteFileByFileName(String fileName)
			throws FileNotFoundException {
		File file = new File(fileName);
		if (!file.isDirectory()) {
			file.delete();
		}
		return true;
	}

	/**
 * 
 */
	public static void readFile() {
		String record = null;
		int reccount = 0;
		try {
			// Convenience class for reading character files.
			FileReader fr = new FileReader("test.js");
			// Read text from a character-input stream,
			// buffering characters so as to provide for the efficient reading
			// of characters, arrays, and lines.
			BufferedReader br = new BufferedReader(fr);
			record = new String();
			while ((record = br.readLine()) != null) {
				reccount++;
				System.out.println(reccount + ": " + record);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Read file error!");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			//readFile("F:/work/document/深圳市建筑装饰工程消耗量标准_1");
			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString().replace("-", ""));
			FileTools.deleteFileByFileName("E:\\fileupload\\8eaf9f9589834cdd9cef639b6fc9d286.zip");
			// deleteFile("D:/file");
		} catch (Exception ex) {
		}
		System.out.println("导入完毕");
	}
	/**
	 * 创建文件夹
	 * @param path
	 */
	public static void makeDirectory(String path){
		File file =new File(path); 
		if (!file.exists()) {
			file.mkdir();
		}
	}
}
