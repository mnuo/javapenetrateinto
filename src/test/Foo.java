/**
 * Foo.java created at 2016年7月28日 下午4:08:59
 */

/**
 * @author saxon
 */
public class Foo {
	public class Foo{
		private static final int MAX_COUNT=1000;
		private static int  count = 0;
		public int bar() throws Exception{
			if(++ count >= MAX_COUNT){

			count =0;
			throw new Exception("count overflow");
			return count;
			}
		}
	}
}
