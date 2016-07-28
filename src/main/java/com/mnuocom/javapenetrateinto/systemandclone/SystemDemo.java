/**
 * SystemDemo.java created at Jun 17, 2016 10:24:05 PM
 */
package com.mnuocom.javapenetrateinto.systemandclone;

/**
 * @author saxon
 * 
 * System类中,关键有两个方法:
 * 	system.out.println();
 * 	System.currentTimeMillis();
 *  System.copyArray(...);
 *  System.gc();等价于Runtine.getRuntime().gc();
 *  
 * system.gc()是垃圾回收器:
 * finalize:是object类的properted 方法,protected只能在同一个包下或者子类中使用
 * 			他是对象在被垃圾回收器回收之前调用,并且这个方法对对象的回收没有影响
 *  finally:是try catch代码块的统一出口,不管有没有异常抛出,都会回收
 *  final:final定义的类不能被继承,定义的方法不能被重写,定义的属性是常量,常量是不可以修改的
 */
public class SystemDemo {
	public static void main(String[] args) {
		Person person= new Person();
		person = null;
		System.gc();
	}
}
class Person{
	@Override
	protected void finalize() throws Throwable {
		
		super.finalize();
		System.out.println("我真的还想再活五百年");
		new Exception("真的");
	}
}
