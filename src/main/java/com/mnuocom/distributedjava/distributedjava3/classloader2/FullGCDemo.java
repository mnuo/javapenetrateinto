/**
 * FullGCDemo.java created at Jul 29, 2016 11:02:25 PM
 */
package com.mnuocom.distributedjava.distributedjava3.classloader2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saxon
 * 
 * 设置VM:-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails
 * [GC (Allocation Failure) [DefNew: 7323K->572K(9216K), 0.0103217 secs] 7323K->6716K(19456K), 0.0104213 secs] [Times: user=0.00 sys=0.00, real=0.01 secs] 
[Full GC (System.gc()) [Tenured: 6144K->9216K(10240K), 0.0062887 secs] 10961K->10810K(19456K), [Metaspace: 95K->95K(4480K)], 0.0063703 secs] [Times: user=0.00 sys=0.02, real=0.01 secs] 
[Full GC (System.gc()) [Tenured: 9216K->9216K(10240K), 0.0026784 secs] 10810K->10810K(19456K), [Metaspace: 95K->95K(4480K)], 0.0027502 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC (Allocation Failure) [Tenured: 9216K->4666K(10240K), 0.0043285 secs] 17109K->4666K(19456K), [Metaspace: 95K->95K(4480K)], 0.0043973 secs] [Times: user=0.02 sys=0.00, real=0.00 secs] 
Heap
 def new generation   total 9216K, used 4405K [0x04800000, 0x05200000, 0x05200000)
  eden space 8192K,  53% used [0x04800000, 0x04c4d420, 0x05000000)
  from space 1024K,   0% used [0x05100000, 0x05100000, 0x05200000)
  to   space 1024K,   0% used [0x05000000, 0x05000000, 0x05100000)
 tenured generation   total 10240K, used 4666K [0x05200000, 0x05c00000, 0x05c00000)
   the space 10240K,  45% used [0x05200000, 0x0568eba0, 0x0568ec00, 0x05c00000)
 Metaspace       used 95K, capacity 2244K, committed 2368K, reserved 4480K
 */
public class FullGCDemo {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		List<MemoryObject> objects = new ArrayList<MemoryObject>(6);
		for(int i = 0; i < 10; i ++){
			objects.add(new MemoryObject(1024*1024));
		}
		//让上面的对象尽可能转入旧生代中
		System.gc();
		System.gc();
		Thread.sleep(2000);
		objects.clear();
		for(int i = 0; i < 10;i ++){
			objects.add(new MemoryObject(1024*1024));
			if(i % 3 == 0){
				objects.remove(0);
			}
		}
		Thread.sleep(5000);
	}

}
