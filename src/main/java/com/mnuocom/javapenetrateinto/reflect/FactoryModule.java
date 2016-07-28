/**
 * FactoryModule.java created at 2016年6月23日 上午11:13:22
 */
package com.mnuocom.javapenetrateinto.reflect;

/**
 * @author saxon
 * 工厂模式
 */
public class FactoryModule {
	public static void main(String[] args) throws Exception {
		Animal cat = Factory.getInstance(Cat.class);
		cat.whoAmI();
		Animal dog = Factory.getInstance(Dog.class);
		dog.whoAmI();
	}

}

interface Animal{
	void whoAmI();
}
class Factory{
	static Animal getInstance(Class<? extends Animal> clazz) throws Exception{
		return clazz.newInstance();
	}
}
class Cat implements Animal{
	@Override
	public void whoAmI() {
		System.out.println("I'm a cat,my name is ZhangSan");
	}
}
class Dog implements Animal{
	@Override
	public void whoAmI() {
		System.out.println("I'm a Dog,my name is LiSi");
	}
}

