/**
 * ReflectDemo.java created at 2016年6月23日 上午9:29:23
 */
package com.mnuocom.javapenetrateinto.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author saxon
 * <h4>反射:</h4>
 * <br>不通过new关键字创建对象,而是通过Class类对象创建
 * 1,获取Class对象的三种方法:<br>
 * 		1 对象.getClass();<br>
 * 		2 类名.class<br>
 * 		3 Class.forClass("全类名");<br>
 * 2,反射创建对象:<br>
 * 		clazz.newInstance();//调用无参构造方法<br>
 * 
 * 3,构造方法调用:<br>
 * 		Constructor<?>[] constructor= clazz.getConstructors();<br>
 * 		constructor[i].newInstance(Object... obj);<br>
 * 4,普通方法调用:<br>
 * 		Method setMetcod = clazz.getMethod("setName", String.class);<br>
		Method getMethod = clazz.getMethod("getName");<br>
		
		setMetcod.invoke(obj1, "李四");<br>
		Object name = getMethod.invoke(obj1);<br>
 * 5,设置field属性值:
 * 		Field field = clazz.getDeclaredField("name");<br>
		field.setAccessible(true);//私有对象设置允许访问<br>
		field.set(obj2, "王五");<br>
		System.out.println(field.get(obj2));<br>
 * 
 */
public class ReflectDemo {

	public static void main(String[] args) throws Exception {
		Class<?> clazz = Class.forName("com.mnuocom.javapenetrateinto.reflect.Person");
		System.out.println(clazz.getName());//获取全类名
		System.out.println(clazz.getSimpleName());//获取不包含包路径的类名
		
		Constructor<?>[] constructor= clazz.getConstructors();
		Object obj1 = null;
		Object obj2 = null;
		for (int i = 0; i < constructor.length; i++) {
			if(constructor[i].getParameterCount() == 1){
				obj1 = constructor[i].newInstance("张三");
			}else {
				obj2 = constructor[i].newInstance();
			}
		}
		
		System.out.println(constructor.length);
		System.out.println(obj1.toString());
		System.out.println(obj2.toString());
		
		Method setMetcod = clazz.getMethod("setName", String.class);
		Method getMethod = clazz.getMethod("getName");
		
		setMetcod.invoke(obj1, "李四");
		Object name = getMethod.invoke(obj1);
		
		System.out.println(name);
		
		Field field = clazz.getDeclaredField("name");
		field.setAccessible(true);
		field.set(obj2, "王五");
		System.out.println(field.get(obj2));
	}

}
class Person{
	private String name;

	public Person() {
	}
	public Person(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return " name: " + name;
	}
}
