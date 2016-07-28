/**
 * TuscanyServer.java created at 2016年7月28日 上午11:12:27
 */
package com.mnuocom.distributedjava.distributedjava2.tuscany;

import org.apache.tuscany.sca.host.embedded.SCADomain;

/**
 * @author saxon
 */
public class TuscanyServer {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		SCADomain.newInstance("com/mnuocom/distributedjava/distributedjava2/tuscany/publicservice.composite");
		System.out.println("Server Started");
		while (true) {
			Thread.sleep(10000);
		}

	}

}
