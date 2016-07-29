![]()

JVM负责装载class文件并执行,因此,首先要掌握的是JDK如何将Java代码编译为class文件,入股装载class文件及如何执行class,将源码编译为class文件的实现取决于各个JVM实现或各种源码编译器;calss文件通常有类加载器(ClassLoader)来完成加载;calss的执行在Sun JDK中有解释执行和编译为机器码执行两种方式,其中编译为机器码又分为client和server两种模式.Sun JDK为了提升class的执行效率,对于解释执行和编译为机器码执行都设置了很多的优化策略
![]()

##### 1 JAVA 代码的执行机制	
###### 1.1 JAVA 源码编译机制
+ 1.分析和输入到符号表(Parse and Enter)
	+ Parse过程所做的为此法和语法分析。此法分析(com.sun.tools.javac.parser.Scanner)要完成的是将代码字符串转变为token序列(例如Token.EQ(name:=));语法分析(com.sun.tools.javac.parser.Parser)要完成的是根据语法有token序列生成抽象语法树
	+ Enter(com.sun.toools.javac.comp.Enter)过程为将符号输入到符号表,通常包括确定类的超类型,和接口,根据需要添加默认构造器,将类中出现的符号输入到符号表中

+ 2.注解处理(Annotation Processing)
	该步主要用于处理用户自定义的annotation,可能带来的好处是基于annotation来生成附加的代码或进行一些特殊的检查,从而节省一些公用的代码的编写
		public class User{
			private @Getter String username; 
	}

+ 3.语义分析和生成class文件(Analyse and Generate)
	Analyse步骤基于抽象语法树进行一系列的语义分析,包括将语法树中的名字,表达式等元素与变量方法,类型等联系到一起;检查变量使用前是否已生命;推导泛型方法的类型参数;检查类型匹配;进行常量折叠;检查所有语句都可到达;检查所有checked exception都没捕获或者抛出;检查变量的确定性赋值;检查变量的确定性不重复赋值;接触语法糖(if(false){})形式无用改代码;将泛型Java转为普通Jjava;将含有语法糖的语法树改为含有简单语言结构的语法树,例如foreach,自动装箱/拆箱等

在完成了语义分析后,开始生成class文件(com.sun.tools.javac.jvm.Gen),生成的步骤为:首先将实例成员初始化器收集到构造器中,将静态成员初始化器手机为<lient>();接着将抽象语法树生成字节码,采用的方法为后序遍历语法树,并进行最后的少量代码装换;最后生成class文件

class文件中的信息:
+ 结构信息
> 包括class文件格式版本号及各部分的数量与大小的信息
+ 元数据
> 可以认为元数据对应的就是java源码中声明与常量的信息,主要有类/继承的超类/实现的接口的声明信息,域(field)与方法声明信息和常量池
+ 方法信息
> 简单来说,可以认为方法信息对应的就是Java源码中"语句"与"表达式"对应的信息,主要有字节二码,异常处理器表,求值栈与局部变量区大小,求值栈的类型记录,调式用符号信息

例子:
	public class Foo{
		private static final int MAX_COUNT=1000;
		private static int  count = 0;
		public int bar() throws Exception{
			if(++ count >= MAX_COUNT){

			count =0;
			throw new Exception("count overflow");
			
			}
			return count;
		}
	}

	javac -g Foo.java

	javap -c -s -l -verbose Foo
//基本信息
Classfile /home/test/Foo.class
  Last modified Jul 28, 2016; size 607 bytes
  MD5 checksum fb1c0160910e86806ee18214e020195e
  Compiled from "Foo.java"
//类的声明
public class Foo
	//class 文件格式版本号,major version 52表示jdk 8, 51表示jdk7...
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
  //常量池 存放了所有的Field名称,方法名,方法签名,类型名,代码及calss文件中的常量值
Constant pool:
   #1 = Methodref          #7.#27         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#28         // Foo.count:I
   #3 = Class              #29            // Foo
   #4 = Class              #30            // java/lang/Exception
   #5 = String             #31            // count overflow
   #6 = Methodref          #4.#32         // java/lang/Exception."<init>":(Ljava                                                    /lang/String;)V
   #7 = Class              #33            // java/lang/Object
   #8 = Utf8               MAX_COUNT
   #9 = Utf8               I
  #10 = Utf8               ConstantValue
  #11 = Integer            1000
  #12 = Utf8               count
  #13 = Utf8               <init>
  #14 = Utf8               ()V
  #15 = Utf8               Code
  #16 = Utf8               LineNumberTable
  #17 = Utf8               LocalVariableTable
  #18 = Utf8               this
  #19 = Utf8               LFoo;
  #20 = Utf8               bar
  #21 = Utf8               ()I
  #22 = Utf8               StackMapTable
  #23 = Utf8               Exceptions
  #24 = Utf8               <clinit>
  #25 = Utf8               SourceFile
  #26 = Utf8               Foo.java
  #27 = NameAndType        #13:#14        // "<init>":()V
  #28 = NameAndType        #12:#9         // count:I
  #29 = Utf8               Foo
  #30 = Utf8               java/lang/Exception
  #31 = Utf8               count overflow
  #32 = NameAndType        #13:#34        // "<init>":(Ljava/lang/String;)V
  #33 = Utf8               java/lang/Object
  #34 = Utf8               (Ljava/lang/String;)V
{
	//将符号输入到符号表时生成的默认构造器方法
  public Foo();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>                                                    ":()V
         4: return
      LineNumberTable:
        line 1: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0       5     0  this   LFoo;

//bar 方法的元数据信息
  public int bar() throws java.lang.Exception;
    descriptor: ()I
    flags: ACC_PUBLIC

    Code:
      stack=3, locals=1, args_size=1
      //方法对用的字节码
         0: getstatic     #2                  // Field count:I
         3: iconst_1
         4: iadd
         5: dup
         6: putstatic     #2                  // Field count:I
         9: sipush        1000
        12: if_icmplt     29
        15: iconst_0
        16: putstatic     #2                  // Field count:I
        19: new           #4                  // class java/lang/Exception
        22: dup
        23: ldc           #5                  // String count overflow
        25: invokespecial #6                  // Method java/lang/Exception."<in                                                    it>":(Ljava/lang/String;)V
        28: athrow
        29: getstatic     #2                  // Field count:I
        32: ireturn

        //对应字节码的源码行号信息,可在编译的时候通过-g:none去掉行号信息,行号信息对于查找问题而言至关重要,因此最好还是保留
      LineNumberTable:
        line 5: 0
        line 7: 15
        line 8: 19
        line 10: 29
        //局部变量信息,如生成的class文件中无局部变量信息,则无法知道局部变量的名称,并且局部变量信息是和方法绑定的,接口是没有方法体的,所有ASM之类的在获取接口方法时是拿不到方法中参数的信息的
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      33     0  this   LFoo;
       //记录有分支的情况(对应代码表中 if ..., for,while等)
      StackMapTable: number_of_entries = 1
        frame_type = 29 /* same */
        //异常处理
    Exceptions:
      throws java.lang.Exception
  static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: iconst_0
         1: putstatic     #2                  // Field count:I
         4: return
      LineNumberTable:
        line 3: 0
}
SourceFile: "Foo.java"

###### 1.2 类加载机制
类加载机制是指.class文件加载到JVM,并形成Class对象的机制,之后应用就可对Class对象进行实例化并调用,类加载机制可在运行时动态加载外部的类,远程网络下载过来的class文件等,类加载过程可划分为:装载,链接和初始化;装载和链接过程完成后,即将二进制的字节码转换成Class对象;初始化过程不是类加载类时必须触发,但最迟必须在初次主动使用对象前执行,其所作的动作为给静态变量赋值,调用<clinit>()等
+ 1 装载
> 装载过程负责找到二进制字节码并加载至JVM中,JVM通过类的全限定名(com.bluedavy.HelloWorld)及类加载器(ClassLoaderA实例)完成类的加载,同样也采用以上两个元素来标识一个被加载了的类: 类的全限定名+CllassLoader实例ID

+ 2 连接(Link)
> 链接过程负责对二进制字节码的格式进行校验,初始化装载类中的静态变量及解析类中调用的接口,类;二进制字节码的格式校验遵循Java Class File Format规范,如果格式不符合,则抛出VerifyError;检验过程中如果碰到要引用到其他的接口和类,也会进行加载;如果加载过程失败,则会抛出NoClassDefFoundError.在完成校验后,JVM初始化类中的静态变量,并将其赋为默认值。最后对类中的属性,方法进行验证,以确保其要调用的属性,方法存在,以及具备相应的权限(public,private),如果这个阶段失败可能会造成NoSuchMethodError,NoSuchFieldError

+ 3 初始化(Initialize)
> 初始化过程即执行类中的静态初始化代码,噶偶早起代码及静态属性的初始化,在以下四种情况下,初始化过程会被触发执行:
  - 1) 调用了new
  - 2) 反射调用了类中的方法;
  - 3) 子类调用了初始化;
  - 4) JVM启动过程中指定的初始化类

JVM 的类加载通过ClassLoader及其子类来完成,分为:Boostrap ClassLoader,Extension ClassLoader,System ClassLoader及 User-Defined ClassLoader
![]()

      ClassLoader clazzLoader = ClassLoaderDemo.class.getClassLoader();
      System.out.println(clazzLoader);//sun.misc.Launcher$AppClassLoader@4e0e2f2a 是System ClassLoader
      System.out.println(clazzLoader.getParent());//sun.misc.Launcher$ExtClassLoader@2a139a55 是Extension ClassLoader
      System.out.println(clazzLoader.getParent().getParent());//null 是boostrap ClassLoader 这个是c++写的,所以无法获取

+ Boostrap ClassLoader
+ Extension ClassLoader
+ System ClassLoader
+ User-Defined ClassLoader