package com.mnuocom.javapenetrateinto.mathoperation;

import java.math.BigDecimal;

public class BigDecimalDemo {
	public static void main(String[] args){
		System.out.println(BigDecimalUtil.round(-15.50, 0));
		System.out.println(BigDecimalUtil.round(-15.5106, 2));
	}

}
class BigDecimalUtil{
	public static double round(double target, int scale){
		return new BigDecimal(target).divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
