/**
 * Business.java created at Jul 26, 2016 10:12:50 PM
 */
package com.mnuocom.distributedjava.distributedjava1.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author saxon
 * 服务器端对外提供的接口
 */
public interface Business extends Remote {
	public String echo(String message) throws RemoteException;
}
