![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.0.jpg)

JVM负责装载class文件并执行,因此,首先要掌握的是JDK如何将Java代码编译为class文件,入股装载class文件及如何执行class,将源码编译为class文件的实现取决于各个JVM实现或各种源码编译器;calss文件通常有类加载器(ClassLoader)来完成加载;calss的执行在Sun JDK中有解释执行和编译为机器码执行两种方式,其中编译为机器码又分为client和server两种模式.Sun JDK为了提升class的执行效率,对于解释执行和编译为机器码执行都设置了很多的优化策略
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.0.1.jpg)

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

执行: javac -g Foo.java

执行: javap -c -s -l -verbose Foo
        
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
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.1.2.1.jpg)

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
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.1.2.2.jpg)

例子:

    ClassLoader clazzLoader = ClassLoaderDemo.class.getClassLoader();
    System.out.println(clazzLoader);//sun.misc.Launcher$AppClassLoader@4e0e2f2a 是System ClassLoader
    System.out.println(clazzLoader.getParent());//sun.misc.Launcher$ExtClassLoader@2a139a55 是Extension ClassLoader
    System.out.println(clazzLoader.getParent().getParent());//null 是boostrap ClassLoader 这个是c++写的,所以无法获取

+ Boostrap ClassLoader
  SUN JDK 采用C++实现了此类,此类并非ClassLoader的子类,在代码中没有办法拿到这个对象,Sun JDK启动时会初始化此ClassLoader,并有ClassLoader完成$JAVA_HOME中jre/lib/rt.jar里所有class文件的加载,jar中包含了JAVA规范定义的所有接口及实现
+ Extension ClassLoader
  JVM用此ClassLoader 来加载扩展功能的一些jar包,例如 Sun JDK中目录下有dns工具jar包等,在Sun JDK中ClassLoader对应的类名为ExtClassLoader
+ System ClassLoader
  JVM用此ClassLoader 来记载启动参数中指定的Classppath中的jar包及目录,在sunJdk中ClassLoader对应的类名AppClassLoader
+ User-Defined ClassLoader
  这个是Java开发人员寂静城ClassLoader抽象类自行实现的ClassLoader,基于自定义的ClassLoader可用于加载非Classpath中的jar及目录,还可以再加载之前对class文件做一些动作,录入解密等

ClassLoader抽象类提供了几个关键的方法:

+ loadClass 此方法负责加载指定名字的类,ClassLoader的实现方法为先从已经记载的类中寻找,如没有,则继续从parentClassLoader中寻找;如果仍然没找到此方法 则从system ClassLoader 中寻找,最后在调用findClass方法来寻找;如果要改变类的加载顺序,则可覆盖此方法;如果加载顺序相同,则可通过覆盖findClass来做特殊的处理,例如解密,固定路径寻找等,当通过整个过程没有获取Class对象时,则抛出ClassNotFindExce,如果类需要resolve,则调用resolveClass进行链接
+ findLoadedClass 此方法负责从当前ClassLoader实例对象的缓存中寻找已加载的类,调用的为native的方法
+ findClass 此方法直接抛出ClassNotFoundException,因此要通过覆盖loadClass或此方法来以自定义的方法加载相应的类
+ findSystemClass 负责从System ClassLoader中寻找,如未找到,则继续从Boostrap ClassLoader中寻找
+ difineClass 负责将二进制的字节码转换为Class对象,这个方法对于自定义加载类而言非常重要
+ resolveClass 此方法负责完成Class对象的链接,如果链接过,则会直接返回

异常:

+ ClassNotFoundException 
+ NoClassDefFoundError 造成此异常的主要原因是加载的类中引用到的另外的类不存在
+ LinkageError  该异常在自定义ClassLoader的情况更加容易出现,主要原因是此类已经在ClassLoader加载过了,重复的加载会照成改异常,因此要注意避免在并发的情况下出现这个的问题.
+ ClassCastException 该异常很多原因,合理使用泛型可相对减少此异常的触发,这些原因中还有是两个A对象由不同CllassLoader加载的情况,这是如果将其中某个A对象造型成两外一个A对象,也会爆出ClassCastException

###### 1.3 类的执行机制

1) 字节码解释执行

在完成将class文件信息集加载到JVM并产生Class对象后,就可执行Class对象的静态方法或实例化对象进行调用了,在源码一阶段将源码编译为JVM字节码,JVM字节码是一种中间代码的方式,要有JVM在运行期对其进行过解释并执行,这种方式称为字节码解释执行方式.JVM采用了四个指令:

+ invlkestatic 对应的是调用static方法
+ invokevirtual 对应的是调用对象实例的方法
+ invokeinterface 对应的是调用接口的方法
+ invokespecial 对应的是调用private方法和编译源码后生成的<init>方法,此方法为对象实例化时的初始化方法

线程在创建后都会产生程序计数器(pc) (或称为PC registers和栈);PC存放了下一条要执行的指令在方法内的偏移量;占中存放了栈帧(StackFrame),每个方法每次调用都会产生栈帧.栈帧的主要分为局部变量区和操作数栈两部分,局部变量区用于存放方法中的局部变量和参数,操作数栈中用于存放方法执行过程中产生的中间结果,栈帧中还会有一些杂用的空间,例如指向方法已解析的常量池的应用,其他一些VM内部实现需要的数据等

2) 编译执行

解释执行的效率较低,为提升代码的执行性能,Sun JDK提供将字节码编译为机器码的支持,编译在运行时进行,通常称为JIT编译器,Sun JDK在执行过程中对执行频率高的代码进行编译,对执行不频繁的代码则继续采用解释的方式,因此Sun JDK又称为Hotspot VM

3)反射执行

反射执行是JAVA的亮点之一,基于反射可动态调用某对象实例中的对应方法,访问查看对象的属性等,无需再编写代码时就确定要创建的对象


#### 2 JVM内存管理
##### 1) 内存空间
![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.2.3-neicun%20jiegou.jpg)

+ 方法区 
> 方法去存放了要加载的类的信息(名称,修饰符等),类中的静态变量,类中定义为final类型的常量,类中的Field信息,类中的方法信息,当开发人员呢在程序中通过Class对象的getName,isInterface等方法来获取信息时,这些数据都来源方法区域,方法区域也是全局共享的,在一定条件下她也会被GC,当方法区域还要使用的内存超过其允许的大小时,会抛出OoutOfMemory的错误信息   
在Sun JDK中这块区域对应Permanet Generation又称为持久代,默认最小值16M,最大值64M;通过-XX:PermSize及-XX:MaxPermSize指定最小和最大值
+ 堆
> 堆用于存储对象实例及数组值,可以认为你Java中所有通过new创建的对象的内存都在此分配,Heap中对象所占用的内存又GC进行回收,32位操作系统上最大为2G,64位不限:-Xms为JVM启动时申请的最小Heap内存,默认物理内存的1/64,但小于1G,-Xmx 是最大Heap 默认物理内存的1/4小于1G,默认当空余堆内存小于40%时,JJVM会增大Heap到-Xmx指定的大小,可通过-XX:MinHeapFreeRatio=来指定这个比例;当空余堆>70% JVM会减小到-Xms 可通过-XX:MaxHeapFreeRatio=指定这个比例,通常 -Xms -Xmx设成一样

堆采用分代管理:
  + 新生代(new generation)
  包括EdenSpace和两块大小相同的Survivor space;-Xmn指定新生代大小;-XX:SurvivorRate来调整Eden 和From的比例

  + 旧生代(Old Generation 或 Tenuring Generation)
-XX:PretenureSizeThreshold=1024 来代表当对象超过多大时就不在新生代你分配 = -Xmx - -Xmn

+ 本地方法栈:
用于支持native方法的执行,存储了每个native方法调用的状态,在Sun JDK的实现中本地方法栈和JVM方法栈是同一个

+ PC寄存器和JVM方法栈
每个线程俊辉创建PC寄存器和JVM方法栈,PC寄存器占用的可能为CPU寄存器或操作系统内存,JVM方法栈占用的为操作系统内存,JVM方法栈为线程私有,在内存分配上非常高效,当方法运行完毕时,其对应的栈帧所占用的内存也会自动释放.当JVM方法栈空间不足时,会抛出StackOverflowError 通过-Xss来指定其大小

##### 2) 内存分配
见GC

##### 3) 内存回收

+ 1.引用计数收集器
+ 2.跟踪收集器
  - 复制(coping)
  ![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.2.3-coping.jpg)
  - 标记-清除(Mark-Sweep)
    ![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.2.3-biaojiqingchu.jpg)
  - 标记-压缩(mark-compact)
    ![](http://7xsqwa.com1.z0.glb.clouddn.com/mnuo-distributedjava-3.2.3-markcompact.jpg)

##### 4) 可用GC
+ 新生代可用Gc
  - 串行GC(Serial GC)
    当扫描存活对象时,Minor GC所做的动作为将存货的对象复制目前作为To Space 当再次进行MinorGc 则转换为From space,通常存活的对象在Minor GC后并不是直接进入旧生代,只有经历过几次Minor GC仍然存活的对象,才放入旧生代中
  - 并行回收GC(Paralled Scavenge)

  - 并行GC (ParNew)

+ 旧生代和持久代可用的GC
  Full GC
