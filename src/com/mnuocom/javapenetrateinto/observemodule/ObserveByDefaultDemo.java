/**
 * ObserveByDefaultDemo.java created at 2016年6月24日 上午11:14:47
 */
package com.mnuocom.javapenetrateinto.observemodule;

import java.util.Observable;
import java.util.Observer;

/**
 * @author saxon
 * <p><h3>观察者模式:</h3><br>
 * 当被观察者对象发生改变,观察者会收到相应的信息,进行处理
 * </p>
 * 
 */
public class ObserveByDefaultDemo {
	public static void main(String[] args) throws Exception {
		HomePriceBeenObserved obserable = new HomePriceBeenObserved();
		obserable.addObserver(new Pepeole("张三"));
		obserable.addObserver(new Pepeole("李四"));
		obserable.addObserver(new Pepeole("王五"));
		
		obserable.setPrice(100000);
		Thread.sleep(1000);
		obserable.setPrice(200000);
		Thread.sleep(1000);
		obserable.setPrice(300000);
	}
}

class HomePriceBeenObserved extends Observable{
	private int price;
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
		if(this.price > 1000){
			System.out.println("房价涨到了: " + this.price);
			this.setChanged();
			this.notifyObservers(this.price);
		}
	}
}
class Pepeole implements Observer{
	private String name;
	
	public Pepeole(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(this.name + " 观察到" + o.getClass().getSimpleName() + " 的 " + arg.toString() + " 改变了! 该行动了");
	}
}

