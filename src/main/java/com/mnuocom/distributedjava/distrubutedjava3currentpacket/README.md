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





