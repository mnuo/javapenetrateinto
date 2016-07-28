/**
 * ArraysDemo.java created at Jun 20, 2016 10:55:11 PM
 */
package com.mnuocom.javapenetrateinto.arraycomparate;

import java.util.Arrays;

/**
 * @author saxon
 * Arrays 主要用到方法:
 * 	Arrays.sort();//排序
 *  Arrays.banarySearch(Object[] arr, object obj);//二分查找法,但是在调用此方法之前需要先sort
 *  Arrays.equals(Object[] arr1, Object[] arr2);//比较,只有当长度,并且对应每个位置的元素值相等时才是true
 *  Arrays.fill();填充
 */
public class ArraysDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr1 = new int[]{30,10,4,22};
		Arrays.sort(arr1);//比较
		System.out.println(Arrays.toString(arr1));
		
		int[] arr2 = new int[]{30,10,4,22,44};
		Arrays.sort(arr2);//比较
		System.out.println(Arrays.binarySearch(arr2, 10));
		System.out.println(Arrays.binarySearch(arr2, 5));
		
		System.out.println(arr1.equals(arr2));
		System.out.println(Arrays.equals(arr1, arr2));
		
		Arrays.fill(arr1, 50);
		System.out.println(Arrays.toString(arr1));
	}

}
