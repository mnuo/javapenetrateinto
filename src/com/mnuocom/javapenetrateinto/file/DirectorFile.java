/**
 * create at 2016年6月27日 上午10:36:23
 */
package com.mnuocom.javapenetrateinto.file;

import java.io.File;

/**
 * @author mnuo
 * <p>获取目录下的所有文件，主要根据File 的一些方法：<br>
 * 		isDirectory(),exists();delete();getParentFile();
 * </p>
 */
public class DirectorFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("/home/monuo");
		print(file);
	}
	
	public static void print(File file){
		if(file == null)
			return;
		
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(int i = 0; i < files.length; i ++){
				print(files[i]);
			}
		}
		System.out.println(file);
	}
}
