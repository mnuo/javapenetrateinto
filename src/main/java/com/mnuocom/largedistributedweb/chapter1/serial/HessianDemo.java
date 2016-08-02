/**
 * HessianDemo.java created at 2016年8月2日 上午11:14:56
 */
package com.mnuocom.largedistributedweb.chapter1.serial;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * @author saxon
 */
public class HessianDemo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		//Hessian的序列化输出
		HessianOutput ho = new HessianOutput(bos);
		ho.writeObject(new Persion("李四"));
		byte[] data = bos.toByteArray();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		//Hessian反序列化读取对象
		HessianInput hi = new HessianInput(bis);
		Persion lizi = (Persion) hi.readObject();
		
		System.out.println(lizi.getName());
	}

}
