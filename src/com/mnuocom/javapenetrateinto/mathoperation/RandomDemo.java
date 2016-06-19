/**
 * RandomDemo.java created at Jun 19, 2016 8:59:32 PM
 */
package com.mnuocom.javapenetrateinto.mathoperation;

import java.util.Random;

/**
 * @author saxon
 */
public class RandomDemo {

	public static void main(String[] args) {
	int[] arr = new int[7];	
	handar(arr);
	print(arr);
	}
	public static int[] handar(int[] target){
		int i = 0;
		Random random = new Random();
		while(i < 7){
			int temp = random.nextInt(37);
			if(isRepeat(target, temp))
				target[i++] = temp;
		}
		return target;
	}
	public static boolean isRepeat(int[] target, int temp){
		for(int j = 0 ; j < target.length; j ++){
			if(target[j] == temp)
				return false;
		}
		return true;
	}
	public static void print(int[] arr){
		for(int i = 0; i < arr.length; i ++){
			System.out.println(arr[i] + ",");
		}
		
	}
}
