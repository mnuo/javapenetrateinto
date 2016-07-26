package com.mnuocom.distributedjava.distributedJava1.mina.udp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

public class MemoryMonitor {
	public static final int PORT = 18567;

	protected static final Dimension PANEL_SIZE = new Dimension(300, 200);

	private JFrame frame;

	private JTabbedPane tabbedPane;

	private ConcurrentHashMap<SocketAddress, ClientPanel> clients;

	public MemoryMonitor() throws IOException {

		NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
		acceptor.setHandler(new MemoryMonitorHandler(this));

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addLast("logger", new LoggingFilter());

		DatagramSessionConfig dcfg = acceptor.getSessionConfig();
		dcfg.setReuseAddress(true);

		frame = new JFrame("Memory monitor");
		tabbedPane = new JTabbedPane();
		tabbedPane.add("Welcome", createWelcomePanel());
		frame.add(tabbedPane, BorderLayout.CENTER);
		clients = new ConcurrentHashMap<SocketAddress, ClientPanel>();
		frame.pack();
		frame.setLocation(300, 300);
		frame.setVisible(true);

		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("UDPServer listening on port " + PORT);
	}

	private JPanel createWelcomePanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(PANEL_SIZE);
		panel.add(new JLabel("Welcome to the Memory Monitor"));
		return panel;
	}

	protected void recvUpdate(SocketAddress clientAddr, long update) {
		ClientPanel clientPanel = clients.get(clientAddr);
		if (clientPanel != null) {
			clientPanel.updateTextField(update);
		} else {
			System.err.println("Received update from unknown client");
		}
	}

	protected void addClient(SocketAddress clientAddr) {
		if (!containsClient(clientAddr)) {
			ClientPanel clientPanel = new ClientPanel(clientAddr.toString());
			tabbedPane.add(clientAddr.toString(), clientPanel);
			clients.put(clientAddr, clientPanel);
		}
	}

	protected boolean containsClient(SocketAddress clientAddr) {
		return clients.contains(clientAddr);
	}

	protected void removeClient(SocketAddress clientAddr) {
		clients.remove(clientAddr);
	}

	public static void main(String[] args) throws IOException {
		new MemoryMonitor();
	}
}

class MemoryMonitorHandler extends IoHandlerAdapter {
	private MemoryMonitor server;

	public MemoryMonitorHandler(MemoryMonitor server) {
		this.server = server;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		session.close(true);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {

		if (message instanceof IoBuffer) {
			IoBuffer buffer = (IoBuffer) message;
			SocketAddress remoteAddress = session.getRemoteAddress();
			server.recvUpdate(remoteAddress, buffer.getLong());
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("Session closed...");
		SocketAddress remoteAddress = session.getRemoteAddress();
		server.removeClient(remoteAddress);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {

		System.out.println("Session created...");

		SocketAddress remoteAddress = session.getRemoteAddress();
		server.addClient(remoteAddress);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		System.out.println("Session idle...");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("Session Opened...");
	}
}

class ClientPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField textField;

	public ClientPanel(String label) {
		super();

		setPreferredSize(MemoryMonitor.PANEL_SIZE);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.CENTER;

		c.gridwidth = GridBagConstraints.REMAINDER;
		add(new JLabel(label), c);

		c.gridwidth = 1;
		add(new JLabel("Memory Used : "));
		textField = new JTextField(10);
		textField.setEditable(false);
		add(textField, c);
	}

	public void updateTextField(final long val) {
		System.out.println("New value for textfield - " + val);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textField.setText(String.valueOf(val));
			}
		});
	}
}