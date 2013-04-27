package mist2meat.javaskipbo.server;

import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerConsole extends JFrame {

	private static final long serialVersionUID = 8384864273218960624L;
	private static JTextArea logbox;

	public ServerConsole() {
		setTitle("Server Log");

		setLocationRelativeTo(null);
		setSize(400, 500); // TODO: perfect later
		setResizable(false);
    	
    	JScrollPane pane = new JScrollPane();
    	pane.setBounds(20, 20, 450, 320);
    	
    	JTextArea area = new JTextArea();
    	area.setLineWrap(true);
    	area.setWrapStyleWord(true);
    	area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    	area.setEditable(false);
    	
    	logbox = area;
    	
    	pane.getViewport().add(area);
    	add(pane);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
		
		setLocation(0,getLocation().y);
	}

	public void log(String msg) {
		logbox.setText(logbox.getText()+"["+(new Date())+"] "+msg+System.lineSeparator());
		logbox.setCaretPosition(logbox.getText().length());
	}
}
