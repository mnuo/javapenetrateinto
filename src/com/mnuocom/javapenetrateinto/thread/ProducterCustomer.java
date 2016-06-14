/**
 * ProducterCustomer.java created at Jun 14, 2016 11:27:57 PM
 */
package com.mnuocom.javapenetrateinto.thread;

/**
 * 这是模仿生产者和消费者模型:
 * 	用于了解线程间的通信
 * @author saxon
 */
public class ProducterCustomer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Info info = new Info();
		Thread product = new Thread(new Producter(info));
		Thread customer = new Thread(new Customer(info));
		product.start();
		customer.start();
	}

}
class Info{
	private String title;
	private String content;
	
	private boolean flag = true;
	//false 表示仓库为红灯,说明里面有数据,不应该生产,应该取走
	//true 表示仓库为绿灯,说明里面没有数据,应该生产,不应该取走
	public synchronized void setInfo(String title, String content){
		if(flag == false){//表示仓库中有数据,不应该生产,等待取走数据
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.title = title;
		this.content = content;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//生产完成之后,仓库有数据,应该把绿灯变成红灯,并且唤醒取数据的线程
		flag = false;
		super.notify();
	}
	public synchronized void getInfo(){
		if(flag == true){//表示仓库中没有数据,不应该取东西,应该等待生成完数据
			try {
				super.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//如果是红灯,说明仓库中有数据,进行取数据操作
		System.out.println(this.title + "---->" + this.content);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//数据取完成,将红灯变成绿灯,并唤醒生产线程进行生产
		flag = true;
		super.notify();
	}
}

class Producter implements Runnable{
	private Info info;
	public Producter(Info info) {
		this.info = info;
	}
	@Override
	public void run() {
		for(int i = 0; i < 50; i ++){
			if(i % 2 == 0){
				info.setInfo("张三", "张三是帅哥");
			}else{
				info.setInfo("李四", "李四是美女");
			}
		}
	}
}
class Customer implements Runnable{
	private Info info;
	public Customer(Info info) {
		this.info = info;
	}
	@Override
	public void run() {
		for(int i = 0; i < 50; i ++){
			info.getInfo();
		}
	}
}

