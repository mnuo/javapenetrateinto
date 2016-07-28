/**
 * EchoProtocol.java created at 2016年7月26日 下午3:44:06
 */
package com.mnuocom.distributedjava.distributedjava1.udpnio;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author saxon
 */
public interface EchoProtocol {
	void handleAccept(SelectionKey selectionKey) throws IOException;
	void handleRead(SelectionKey selectionKey) throws IOException;
	void handleWrite(SelectionKey selectionKey) throws IOException;
}
