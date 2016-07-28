/**
 * BigIntegerDemo.java created at Jun 19, 2016 9:27:40 PM
 */
package com.mnuocom.javapenetrateinto.mathoperation;

import java.math.BigInteger;

/**
 * @author saxon
 */
public class BigIntegerDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigInteger bi = new BigInteger("321123131225555234234234");
		BigInteger bi1 = new BigInteger("4234234234234");
		System.out.println("加法: " + bi.add(bi1));
		System.out.println("减法: " + bi.subtract(bi1));
		System.out.println("乘法: " + bi.multiply(bi1));
		System.out.println("除法: " + bi.divide(bi1));
		System.out.println("余法: " + bi.divideAndRemainder(bi1)[1]);
	}

}
