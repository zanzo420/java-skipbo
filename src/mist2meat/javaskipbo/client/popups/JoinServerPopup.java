package mist2meat.javaskipbo.client.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import mist2meat.javaskipbo.Main;

public class JoinServerPopup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7785251142342191970L;

	public JoinServerPopup(String sip) {
		setTitle("Enter server IP");
		
		setLocationRelativeTo(null);
		setSize(215, 225); //TODO: perfect later
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		final JTextField ip = new JTextField();
		ip.setText(sip);
		ip.setSize(200, 20);
		ip.setLocation(5,5);
		
		add(ip);
		
		JButton button1 = new JButton();
		button1.setSize(80, 50);
		button1.setLocation(0, 60);
		button1.setText("JOIN");
		
		final JFrame frame = this;
		
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.client.queryServer(ip.getText());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				frame.dispose();
			}

		});
		
		add(button1);
		
		setVisible(true);
	}
	
	public JoinServerPopup() {
		new JoinServerPopup("127.0.0.1");
	}
}

