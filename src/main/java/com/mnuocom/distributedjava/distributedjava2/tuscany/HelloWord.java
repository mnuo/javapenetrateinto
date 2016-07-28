/**
 * HelloWord.java created at 2016年7月28日 上午9:46:49
 */
package com.mnuocom.distributedjava.distributedjava2.tuscany;

import org.oasisopen.sca.annotation.Remotable;

/**
 * @author saxon
 */
@Remotable
public interface HelloWord {
	public String sayHello(String name);
}
