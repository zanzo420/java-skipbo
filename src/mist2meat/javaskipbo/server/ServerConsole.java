package mist2meat.javaskipbo.server;

import javax.swing.JFrame;

public class ServerConsole extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8384864273218960624L;

	public ServerConsole() {
		setTitle("Server Log");
		
		setLocationRelativeTo(null);
		setSize(400, 500); //TODO: perfect later
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
