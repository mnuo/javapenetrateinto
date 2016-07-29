/**
 * MinorGCDemo.java created at Jul 29, 2016 10:33:44 PM
 */
package com.mnuocom.distributedjava.distributedjava3.classloader2;

/**
 * @author saxon
 * 
 * 设置VM:-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails
 * 当分配到创建到12个对象时,eden空间不足,执行minor gc 将对象移动到from space 中
 * minor gc should happen
	[GC (Allocation Failure) [DefNew: 12583K->1596K(14784K), 0.0063938 secs] 12583K->1596K(39360K), 0.0065061 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
	minor gc should happen
	Heap
 def new generation   total 14784K, used 14275K [0x04800000, 0x05800000, 0x05800000)
  eden space 13184K,  96% used [0x04800000, 0x05461a40, 0x054e0000)
  from space 1600K,  99% used [0x05670000, 0x057ff2a0, 0x05800000)
  to   space 1600K,   0% used [0x054e0000, 0x054e0000, 0x05670000)
 tenured generation   total 24576K, used 0K [0x05800000, 0x07000000, 0x07000000)
   the space 24576K,   0% used [0x05800000, 0x05800000, 0x05800200, 0x07000000)
 Metaspace       used 95K, capacity 2244K, committed 2368K, reserved 4480K
 
 * 
 * 
 */
public class MinorGCDemo {
	public static void main(String[] args) throws InterruptedException {
		MemoryObject obj = new MemoryObject(1024*1024);
		for(int i = 0; i < 2; i ++){
			happenMinorGC(11);
			Thread.sleep(2000);
		}
	}
	private static void happenMinorGC(int happanMinorGCIndex) throws InterruptedException{
		for(int i =0;i < happanMinorGCIndex; i ++){
			if(i == happanMinorGCIndex - 1){
				Thread.sleep(2000);
				System.out.println("minor gc should happen");
			}
			new MemoryObject(1024 * 1024);
		}
	}
}
class MemoryObject{
	private byte[] bytes;
	public MemoryObject(int objectSize){
		this.setBytes(new byte[objectSize]);
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}