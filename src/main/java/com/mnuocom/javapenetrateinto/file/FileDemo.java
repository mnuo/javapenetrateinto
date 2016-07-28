/**
 * create at 2016年6月27日 上午10:16:34
 */
package com.mnuocom.javapenetrateinto.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author mnuo
 * <p>
 * 	这个列子，主要实现了文件的拷贝，这个列子我们主要用到了InputStream和OutputStream的实现<br>
 * 	inputStream和OutputStream都是字节流<br>
 * 	Reader和Writer是字符流，磁盘上存储的文件是字节流，是在读取过程中将字节流转换成字符流
 * </p>
 */
public class FileDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		copy("/home/monuo/mylog/linux-1st", "/root/Desktop/hell0 11");
	}
	
	public static void copy(String src, String target){
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream( new File(src));
			File file = new File(target);
			if(!file.getParentFile().exists()){
				file.mkdirs();
			}
			byte[] b = new byte[1024];
			int temp = 0;
			outputStream = new FileOutputStream(file);
			while((temp = inputStream.read(b)) != -1){
				outputStream.write(b, 0, temp);
			}
			outputStream.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("not exists");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			close(inputStream, outputStream);
		}
	}
	public static void close(InputStream inputStream, OutputStream outputStream){
		if(inputStream != null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(outputStream != null){
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
