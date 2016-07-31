/**
 * CopyOnWriteArrayListDemo.java created at Jul 30, 2016 10:35:42 PM
 */
package com.mnuocom.distributedjava.distributedjava4;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author saxon
 */
public class CopyOnWriteArrayListDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> cl = new CopyOnWriteArrayList<>(); 
		cl.add("a");
		cl.add("b");
		cl.add("a");
		cl.add(null);
		System.out.println(cl);
		cl.remove("a");
		System.out.println(cl);
	}

}
