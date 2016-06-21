/**
 * RegexDemo.java created at Jun 21, 2016 11:21:48 PM
 */
package com.mnuocom.javapenetrateinto.regex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author saxon
 * 关于正则表达式主要用于验证操作,大部分通过string类的方法可以实现:
 * 	String.replaceAll("\\d+");
 *  String.matches("\\d+");
 *  String.split("\\d+");
 * 正则表达式基本语法:
 * 	单字符:
 * 	\n,\t,\\,\\.
 *  范围,没有两次说明是单字符
 *  [a-zA-Z]
 *  简化字符:
 *  \d \D \w \W \s
 *  量词:
 *  ? 0|1次
 *  + 1或n次
 *  * 0或n次
 *  {n} n次
 *  {n,m} n-m次
 *  ()作为一个整体出现
 *  逻辑
 *  xy
 *  x|y
 */
public class RegexDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> list = new HashSet<String>();
    	Pattern p = Pattern.compile("<[img,IMG][^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");//<img[^<>]*src=[\'\"]([0-9A-Za-z.\\/]*)[\'\"].(.*?)>");
    	Matcher m = p.matcher("dfasdfasdfasdfasdf<img src=\"5\"/>asdfasdfasasdf<img src=\"2\"/>asdfaasdf<img src=\"3\"/>assdfasdf");
    	while(m.find()){
    		list.add(m.group(1));
    	}
    	System.out.println(Arrays.toString(list.toArray()));

	}

}
