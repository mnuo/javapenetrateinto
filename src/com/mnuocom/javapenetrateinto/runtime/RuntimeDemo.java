/**
 * RuntimeDemo.java created at Jun 16, 2016 12:02:55 AM
 */
package com.mnuocom.javapenetrateinto.runtime;

/**
 * @author saxon
 * 图解:
 * 	Permanent Space : 永久空间
 * 	总共的堆内存空间:
 * 		Old Space:旧生代
 * 		新对象空间:
 * 			Eden:伊甸园
 * 			From Space: 源空间
 * 			To Space: 目标空间
 * java中内存划分主要有两个组成部分:
 * 	堆内存:保存的实例化对象内容,在每个jvm进程之中,对象的堆内存空间都会由垃圾收集器自动管理回收
 *  非堆内存(Eden, From Space, To Space):用于产生新的对象:
 *  	所有方法的全局方法区
 *  	所有的static的全局数据区
 *  	永生代:负责存放反射对象的操作空间
 *  如果想要调整内存大小,主要调整堆内存的空间,设置虚拟机内存参数:
 *  -Xmx128M 最大内存空间:通常是物理内存的1/4,不超过1G
 * 	-Xms 初始内存空间:荣昌是物理内存的1/64
 *  -Xmn 年轻代内存空间
 *  
 *  l 内存回收:
 *  	创建对象---判断Eden空间是否充足--true-->申请内存保存对象
 *  						--->false --> 生成MinoGC/MajorGC回收
 *  
 *  ---是否充足---false--->将Eden区保存到存活区---判断旧生代是否充足--false
 *  
 *  ==>FullGC回收--->判断存活区和旧生代是否充足--false-->outmemoryERROR
 *  2 简单描述流程:
 *  	新的对象保存在Eden中,之后此对象保存到年轻代中,而后进行垃圾回收(从gc)将保存在旧生代中(主GC)
 *  	如果再有新对象,从年轻代开始回收,在找到旧生代,最后没有空间了,进行fullgc回收
 *  3 什么是GC:
 *  	GC是垃圾回收,对于GC的操作可以通过Runtime类中的gc()方法进行手动释放回收,或者利用系统自动释放,GC的回收流程是新的对象保存到Eden空间中,之后
 *  	此对象保存到年轻代中,而后进行垃圾回收(MinoGC)将对象保存到旧生代中(主Gc)
 *  	如果再有新对象,从年轻代开始回收,在找到旧生代,最后没有空间了,进行fullgc回收
 */
public class RuntimeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime runtime = Runtime.getRuntime();
		
		System.out.println("1,Max Momery: " + runtime.maxMemory());//最大可用内存
		System.out.println("1,Total Momery: " + runtime.totalMemory());//总共可用内存
		System.out.println("1,Free Momery: " + runtime.freeMemory());//空闲可用内存
		
		String str = "";
		for(int i=0; i < 5000; i ++){
			str += "添加垃圾";
		}
		
		System.out.println("2,Max Momery: " + runtime.maxMemory());
		System.out.println("2,Total Momery: " + runtime.totalMemory());
		System.out.println("2,Free Momery: " + runtime.freeMemory());
		
		runtime.gc();
		
		System.out.println("3,Max Momery: " + runtime.maxMemory());
		System.out.println("3,Total Momery: " + runtime.totalMemory());
		System.out.println("3,Free Momery: " + runtime.freeMemory());
	}

}
