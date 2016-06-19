/**
 * MathDemo.java created at Jun 19, 2016 8:51:24 PM
 */
package com.mnuocom.javapenetrateinto.mathoperation;

/**
 * @author saxon
 */
public class MathDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(MathUtil.round(10.12124,2));
	}

}
class MathUtil{
	/**
	 * round()方法 是四舍五入
	 * pow(double a,double b)是a的b次方
	 * 此方法是保留指定小数点并四舍五入
	 * @param target
	 * @param scale
	 * @return
	 */
	public static double round(double target, int scale){
		return Math.round(target * Math.pow(10.0, scale))/Math.pow(10, scale);
	}
}
