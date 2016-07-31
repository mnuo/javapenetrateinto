/**
 * CPUUSDemo.java created at Jul 31, 2016 9:42:18 PM
 */
package com.mnuocom.distributedjava.distributedjava5;

import java.util.ArrayList;

/**
 * @author saxon
 * 
 */
public class CPUUSDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CPUUSDemo demo = new CPUUSDemo();
		demo.runTest();
	}
	private void runTest() throws Exception{
		int count = Runtime.getRuntime().availableProcessors();
		for(int i = 0; i < count; i ++){
			new Thread(new ConsumeCPUTask()).start();
		}
		for(int i = 0; i < 200; i ++ ){
			new Thread(new NotConsumeCPUTask()).start();
		}
	}
	class ConsumeCPUTask implements Runnable{
		@Override
		public void run() {
			String str = "asdjfla;sfjl;asjf;laskfjasldfjlaskj#flasdjlfasjdlkfajslfjaksdjfalsjdflkasjlfkajsdlkfjalsdkjflkajskldfjaklsdjflaksjldfjasdklfjkas";
			float i = 0.002f;
			float j = 232.12232f;
			while(true){
				j = i * j;
				str.indexOf("#");
				ArrayList<String> list = new ArrayList<String>();
				for (int k =0; k < 1000;) {
					list.add(str + String.valueOf(k));
				}
				list.contains("iii");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class NotConsumeCPUTask implements Runnable{
		@Override
		public void run() {
			while(true){
				try {
					Thread.sleep(100000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
