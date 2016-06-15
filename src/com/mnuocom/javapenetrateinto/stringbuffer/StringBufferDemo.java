/**
 * StringBufferDemo.java created at Jun 15, 2016 11:02:14 PM
 */
package com.mnuocom.javapenetrateinto.stringbuffer;

/**
 * @author saxon
 * 
 * String,StringBuffer,StringBuilder的区别
 * 0) String,StringBuffer,StringBuilder都是CharSequence接口的子类
 * 1) String 的内容一旦生成是不可以改变的,StringBuffer和StringBuilder是可以改变的
 * 2) StringBuffer是JDK1.0提供,而StringBuilder是JDK1.5之后才提供
 * 3) StringBuffer是线程安全的,性能不高,而StringBuilder是非线程安全的,性能更高
 */
public class StringBufferDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("Hello world!");
		System.out.println(sb.reverse());
		System.out.println(sb.reverse().delete(6, 11));
		System.out.println(sb.insert(6, "Ameria"));
	}

}
