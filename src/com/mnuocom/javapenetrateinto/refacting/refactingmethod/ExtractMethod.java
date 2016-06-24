/**
 * ExtractMethod.java created at 2016年6月16日 下午3:19:46
 */
package com.mnuocom.javapenetrateinto.refacting.refactingmethod;

import java.util.Enumeration;
import java.util.Vector;

/**
 * @author saxon
 */
public class ExtractMethod {
	private Vector<Order> orders = new Vector<>();
	
	private String name;
	
	void printOwing(double initAmount){
		Enumeration<Order> order = orders.elements();
		double outstanding = initAmount * 1.2;
		System.out.println("********************************");
		System.out.println("******** Customer Owers ********");
		System.out.println("********************************");
		
		while (order.hasMoreElements()) {
			Order each = order.nextElement();
			outstanding += each.getAmount();
		}
		
		//print details
		System.out.println("name: " + name);
		System.out.println("amount: " + outstanding);
	}
	
	void printOwingNew(double initAmount){
		Enumeration<Order> order = orders.elements();
		double outstanding = initAmount * 1.2;
		//
		
		while (order.hasMoreElements()) {
			Order each = order.nextElement();
			outstanding += each.getAmount();
		}
		
		//print details
		System.out.println("name: " + name);
		System.out.println("amount: " + outstanding);
	}
	void pintBannar(){
		System.out.println("********************************");
		System.out.println("******** Customer Owers ********");
		System.out.println("********************************");
	}
	
	
}
class Order{
	private String name;
	private double amount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}