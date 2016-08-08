/**
 * MagicNumberDemo.java created at 2016年8月8日 下午1:14:21
 */
package com.mnuocom.largedistributedweb.chapter3;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author saxon
 */
public class MagicNumberDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		System.out.println(getType("C:\\Users\\Administrator\\Desktop/0014100a9e7720160406130553890894.jpg"));
		System.out.println(getType("C:\\Users\\Administrator\\Desktop/data.xml"));
		System.out.println(getType("D:\\appserver\\eclipse\\eclipse/eclipse.exe"));
	}
	/**
	 * 读取文件头
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	private static String getFileHeader(String filePath) throws Exception{
		//在这里需要注意的是,每个文件的魔数的长度都不相同,因此需要使用startwith
		byte[] b = new byte[28];
		
		InputStream inputStream = new FileInputStream(filePath);
		
		inputStream.read(b, 0, 28);
		inputStream.close();
		return bytes2hex(b);
	}
	/**
	 * 判断文件类型
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static FileType getType(String filePath) throws Exception{
		String fileHead = getFileHeader(filePath);
		if(fileHead == null || fileHead.length() == 0){
			return null;
		}
		fileHead = fileHead.toUpperCase();
		FileType[] fileTypes = FileType.values();
		for(FileType type : fileTypes){
			if(fileHead.startsWith(type.getValue())){
				return type;
			}
		}
		return null;
	}
	
	public static String bytes2hex(byte[] bs){
		StringBuffer sBuffer = new StringBuffer(bs.length);
		String stemp;
		for(int i = 0; i < bs.length; i ++){
			stemp = Integer.toHexString(0xFF & bs[i]);
			if(stemp.length() < 2){
				sBuffer.append(0);
			}
			sBuffer.append(stemp.toUpperCase());
		}
		return sBuffer.toString();
	}
}
enum FileType{
	JPEG("FFD8FF"),
	PNG("89504E47"),
	GIF("47494638"),
	TIFF("49492A00"),
	BMP("424D"),
	DWG("41433130"),
	PSD("38425053"),
	XML("3C3F786D6C"),
	HTML("68746D6C3E"),
	PDF("255044462D312E"),
	ZIP("504B0304"),
	RAR("52617221"),
	WAV("57415645"),
	AVI("41564920");
	
	private String value="";
	
	private FileType(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
