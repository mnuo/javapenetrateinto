#### java并发包
##### 1 ConcurrentHashMap
ConcurrentHashMap是线程安全的HashMap
代码操作:
		/**
		 * @author saxon
		 * ConcurrentHashMap key和value均不可为空 ; HashMap key和value均可为空
		 */
		public class CurrentHashMapDemo {
			/**
			 * @param args
			 */
			public static void main(String[] args) {
				ConcurrentHashMap<String, Object> cmap = new ConcurrentHashMap<String, Object>();
				cmap.put("A", "hello world ");
				cmap.put("b", "aa");
				cmap.put("B", "bb");
				///key value 均不可为空
				//cmap.put(null, "2aa");
				//cmap.put("b", null);
				System.out.println(cmap);
				System.out.println("是否包含keyA: " + cmap.containsKey("A"));
				System.out.println("remove key==B");
				cmap.remove("B");
				//遍历
				Set<Entry<String, Object>> set = cmap.entrySet();
				Iterator<Entry<String, Object>> ite = set.iterator();
				while(ite.hasNext()){
					Entry<String, Object> element = ite.next();
					System.out.print(element.getKey() + ": ");
					System.out.println(element.getValue());
				}
				//HashMap
				HashMap<String, Object> mashmap = new HashMap<String, Object>();
				mashmap.put("A", "hello world ");
				mashmap.put("b", null);
				mashmap.put("b", "gg");
				//均可为空
				mashmap.put(null, null);
				System.out.println(mashmap);
			}
		}
与HashMap性能比较:
> 1 无论元素数量为多少,在线程数为10时,ConcurrentHashMap带来的性能提升并不明显;但当线程数越多时,在增加和删除元素带来了一倍左右的性能提升,在查找元素上更是带来了10倍左右的性能提升,并且ConcurrentHashMap性能并没有出现下降的现象

##### 2 CopyOnWriteArrayList
CopyWriteArrayList是一个线程安全,并且在读操作时无锁的ArrayList.
方法:
+ add(E) 
add方法并没有加上synchronized关键字,他通过ReentrantLock来保证县城安全,和ArrayList不同的是每次都会创建一个新的Object数组,次数字的大小为当前数字大小加1,将之前数组中的内容复制到新的数组中,并将新增加的对象放入数组末尾,最后切换将新创建的数组对象复制给全局的数组对象

+ remove(E)
和add一样也是通过ReentrantLock来保证线程安全;remove时创建一个比当前数组小1的数组,遍历新数组,如找到equals或均为null的元素,则将之后的元素全部复制给新的数组对象,并作引用切换,返回true;如果未找到,则将当前的元素,赋值给新的数组对象,最后特殊处理数组中的最后一个元素

+ get(int)
此方法没有加锁

+ iterator()

和ArrayList性能比较
> CopyOnWriteArrayList在增加元素和删除元素 时性能下降比较明显,并且性能会比ArrayList低,但在查找元素这点上随着线程数的增加,性能较ArraList会好很多







