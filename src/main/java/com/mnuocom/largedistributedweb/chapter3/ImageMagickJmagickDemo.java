/**
 * ImageMagickJmagickDemo.java created at 2016年8月8日 下午2:08:09
 */
package com.mnuocom.largedistributedweb.chapter3;

import magick.ImageInfo;
import magick.MagickImage;

/**
 * @author saxon
 */
public class ImageMagickJmagickDemo {
	static{
		System.out.println(System.getProperty("java.library.path"));
        System.setProperty("jmagick.systemclassloader","no");
    }
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		resetsize("C:\\Users\\Administrator\\Desktop/0014100a9e7720160406130553890894.jpg"
				,"C:\\Users\\Administrator\\Desktop/dddd.jpg");

	}
	public static void resetsize(String picFrom, String picTo) throws Exception{
		ImageInfo imageInfo = new ImageInfo(picFrom);
		
		MagickImage image = new MagickImage(imageInfo);
		
		MagickImage scaled = image.scaleImage(120, 97);
		scaled.setFileName(picTo);
		scaled.writeImage(new ImageInfo());
	}
}
