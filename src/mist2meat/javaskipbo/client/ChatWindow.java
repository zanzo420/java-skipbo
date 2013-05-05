package mist2meat.javaskipbo.client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.network.client.PlayerChatPacket;

public class ChatWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2229324101943750872L;
	private JTextArea box;

	public ChatWindow(){
		setSize(300,360);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Chat");
		setVisible(true);
		setLayout(null);
		setResizable(false);
		
		JScrollPane pane = new JScrollPane();
		pane.setBounds(2,5,290,290);
		
		box = new JTextArea();
		box.setEditable(false);
		box.setLineWrap(true);
    	box.setWrapStyleWord(true);
		
		pane.getViewport().add(box);
		add(pane);
		
		final JTextField text = new JTextField();
		text.setBounds(2,300,290,22);
		add(text);
		
		text.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					sendMessage(text.getText());
					text.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		validate();
	}
	
	private void sendMessage(String message) {
		if(message.trim().length() > 0){
			try {
				PlayerChatPacket pack = new PlayerChatPacket(ClientListener.socket,Main.client.getServerAddress(),3625);
				pack.setMessage(message);
				pack.send();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addLine(String text) {
		box.setText(box.getText()+text+"\n");
		box.setCaretPosition(box.getText().length());
	}
}
