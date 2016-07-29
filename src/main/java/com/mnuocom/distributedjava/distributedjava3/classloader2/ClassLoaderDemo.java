/**
 * ClassLoaderDemo.java created at 2016年7月29日 下午1:24:21
 */
package com.mnuocom.distributedjava.distributedjava3.classloader2;

/**
 * @author saxon
 */
public class ClassLoaderDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassLoader clazzLoader = ClassLoaderDemo.class.getClassLoader();
		System.out.println(clazzLoader);//sun.misc.Launcher$AppClassLoader@4e0e2f2a 是System ClassLoader
		System.out.println(clazzLoader.getParent());//sun.misc.Launcher$ExtClassLoader@2a139a55 是Extension ClassLoader
		System.out.println(clazzLoader.getParent().getParent());//null 是boostrap ClassLoader 这个是c++写的,所以无法获取
	}

}
