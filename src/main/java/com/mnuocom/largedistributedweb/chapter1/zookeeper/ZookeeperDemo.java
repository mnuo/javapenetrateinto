/**
 * ZookeeperDemo.java created at 2016年8月4日 下午3:56:58
 */
package com.mnuocom.largedistributedweb.chapter1.zookeeper;

import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author saxon
 * 例子:
 * 	假设我是一家KTV的老板,我同事拥有5家KTV,我肯定得时刻监视我KTV的情况,这时候我会给每家KTV设置一个视频监控,然后每一家
 * 都连接到我的视频监控里面,那么我就可以在家里看到所有的KTV情况了,如果某一家出现问题,我就能及时发现,并且做出反应
 */
public class ZookeeperDemo {
	//根节点
	public static final String ROOT = "/root-ktv";
	
	public static void main(String[] args) throws Exception {
		//创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher(){
			//监控所有被触发的事件
			@Override
			public void process(WatchedEvent event) {
				System.out.println("状态: " + event.getState() + " : " + 
						event.getType() + "　：　" + event.getWrapper() + 
						" : " + event.getPath());
			}
		});
		//创建一个总的目录ktv,并不控制权限,这里需要用持久节点,不然下面的节点创建容易出错
		zk.create(ROOT, "root-ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		//然后在杭州开一个KTV,PERSISTEMT_SEQUENTIAL类型会自动加上0000000000自增的后缀
		zk.create(ROOT + "/杭州KTV", "杭州ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
		//也可以在北京开一个 EPHEMERAL session过期了就会自动删除
		zk.create(ROOT + "/北京KTV", "北京ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		//同理,我可以在北京开多个,EPHEMERAL_SEQUENTIAL session过期自动删除,也会加数字的后缀
		zk.create(ROOT + "/北京KTV", "北京ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		
		//我们也可以来看看,一共监视了多少家的ktv
		List<String> ktvs = zk.getChildren(ROOT, true);
		
		System.out.println(Arrays.toString(ktvs.toArray()));
		/*for(String node : ktvs){
			//删除节点
			zk.delete(ROOT + "/" + node, -1);
		}
		
		zk.delete(ROOT, -1);
		zk.close();*/
	}
	
}
