package mist2meat.javaskipbo.client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class ChatWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2229324101943750872L;
	
	private JTextArea box;
	private JScrollPane pane;
	private JTextField text;
	private JButton send;
	private JLabel head;

	public ChatWindow(){
		setSize(300,430);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		this.getContentPane().setLayout(null);
		
		head = new JLabel("You're playing with: ");
		head.setBounds(0,0,300,25);
		this.getContentPane().add(head);
		
		box = new JTextArea(16,14);
		box.setEditable(false);
		pane = new JScrollPane(box);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setBounds(0,25,300,275);
		this.getContentPane().add(pane);
		
		text = new JTextField();
		text.setBounds(0,300,300,50);
		this.getContentPane().add(text);
		
		send = new JButton("Send");
		send.setBounds(0,350,300,50);
		this.getContentPane().add(send);
	}
	
	public void unlockChat(boolean b){
		if(!b){
			head.setText("Waiting");
			text.setEditable(false);
		} else {
			head.setText("Playing with: ");
			text.setEditable(true);
		}
	}
}
