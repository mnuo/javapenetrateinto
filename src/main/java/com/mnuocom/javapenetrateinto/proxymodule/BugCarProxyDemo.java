/**
 * create at 2016年6月28日 上午10:10:41
 */
package com.mnuocom.javapenetrateinto.proxymodule;

/**
 * @author mnuo
 */
public class BugCarProxyDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		People p1 = new People(20000, "vip", "zhangsan");
		People p2 = new People(5000011, "", "lisi");
		People p3 = new People(50000, "", "wangwu");
		ProxyBuyCar proxy = new ProxyBuyCar();
		
		proxy.setPeople(p1);
		proxy.buyCar();
		proxy.setPeople(p2);
		proxy.buyCar();
		proxy.setPeople(p3);
		proxy.buyCar();
		
	}

}

interface BuyCar{
	public void buyCar();
}

class People implements BuyCar{
	private int cash;
	private String vip;
	private String userName;
	
	public People(int cash, String vip, String userName) {
		this.cash = cash;
		this.vip = vip;
		this.userName = userName;
	}
	@Override
	public void buyCar() {
		System.out.println(this.userName + " is " + this.vip + " buy a car");
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

class ProxyBuyCar implements BuyCar{
	private People people;
	
	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	@Override
	public void buyCar() {
		if(this.people.getVip().equals("vip")){
			people.buyCar();
			return;
		}
		if(this.people.getCash() > 500000){
			System.out.println("buy a new car over!");
		}else{
			System.out.println("no enough money to buy a new car");
		}
	}
}