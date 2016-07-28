/**
 * InternationalDemo.java created at 2016年6月24日 上午10:59:51
 */
package com.mnuocom.javapenetrateinto.international;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author saxon
 * <p>国际化主要涉及到两个方面:<br>一个是Locale类,这个类可以改变或者获取语言<br>另一个是ResourceBunlde</p>
 * 
 */
public class InternationalDemo {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Locale locale = new Locale("en","US");
		Locale locale = new Locale("zh_CN");
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("com.mnuocom.javapenetrateinto.international.International", locale);
		String str = resourceBundle.getString("welcome");
		String fomatterStr = resourceBundle.getString("welcome.all");
		str = MessageFormat.format(fomatterStr, "Tom");
		
		System.out.println(str);

	}

}
