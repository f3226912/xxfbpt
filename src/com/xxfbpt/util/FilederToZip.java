package com.xxfbpt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件夹压缩
 * @author Administrator
 *
 */
public class FilederToZip {
	
	/*
    * inputFileName 输入一个文件夹
    * zipFileName 输出一个压缩文件夹
    */
    public static void zip(String inputFileName, String zipFileName) throws Exception {
        zip(zipFileName, new File(inputFileName));
    }

    private static void zip(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        out.close();
    }

    private static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
	          zip(out, fl[i], base + fl[i].getName());
	       }
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           while ( (b = in.read()) != -1) {
              out.write(b);
           }
           out.setEncoding("gbk");
           out.closeEntry();
           in.close();
        }
    }
    
    
    public static InputStream zip2(String inputFileName, String zipFileName) throws Exception {
        System.out.println(zipFileName);
        return zip2(zipFileName, new File(inputFileName));
    }

    private static InputStream zip2(String zipFileName, File inputFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        return zip2(out, inputFile, "");
        //out.close();
    }
    
    private static InputStream zip2(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
	           zip(out, fl[i], base + fl[i].getName());
	       }
           return null;
        }else {
           out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           return in;
        }
    }

}
