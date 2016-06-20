/**
 * BinaryTreeDemo.java created at Jun 20, 2016 11:25:50 PM
 */
package com.mnuocom.javapenetrateinto.arraycomparate;

import java.util.Arrays;

/**
 * @author saxon
 * 通过comparable实现二叉树
 */
public class BinaryTreeDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryTree bt = new BinaryTree();
		bt.add(new Person("张三", 22));
		bt.add(new Person("李四", 12));
		bt.add(new Person("王五", 32));
		bt.add(new Person("赵六", 21));
		bt.add(new Person("前妻", 20));
		Object[] objects = bt.toArray();
		System.out.println(Arrays.toString(objects));
	}

}
class BinaryTree {
	class Node{
		private Comparable<Object> data;
		
		public Node(Comparable<Object> data){
			this.data = data;
		}
		private Node left;
		private Node right;
		
		public void addNode(Node newNode){
			if(this.data.compareTo(newNode.data) > 0){
				if(this.left == null){
					this.left = newNode;
				}else{
					this.left.addNode(newNode);
				}
			}else{
				if(this.right == null){
					this.right = newNode;
				}else{
					this.right.addNode(newNode);
				}
			}
		}
		
		public void toArrayNode(){
			if(this.left != null){
				this.left.toArrayNode();
			}
			BinaryTree.this.result[BinaryTree.this.index ++] = this.data;
			if(this.right != null){
				this.right.toArrayNode();
			}
		}
	}
	private Node root;//根节点
	private int count = 0;//node节点数
	private int index = 0;//
	private Object[] result;
	
	public void add(Comparable<Object> obj){
		if(this.root == null){
			this.root = new Node(obj);
		}else{
			this.root.addNode(new Node(obj));
		}
		count ++;
	}
	
	public Object[] toArray(){
		if(this.count > 0){
			this.index = 0;
			this.result = new Object[this.count];
			this.root.toArrayNode();
			return this.result;
		}else{
			return null;
		}
	}
}