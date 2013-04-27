package mist2meat.javaskipbo.client.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import mist2meat.javaskipbo.Main;

public class HostJoinPopup extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4518610456510535660L;

	public HostJoinPopup() {
		setTitle("Host or Join");
		
		setLocationRelativeTo(null);
		setSize(215, 225); //TODO: perfect later
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton button1 = new JButton();
		button1.setSize(200, 100);
		button1.setLocation(0, 0);
		button1.setText("JOIN");
		
		final JFrame frame = this;
		
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.useServer(false);
			}

		});
		
		add(button1);
		
		JButton button2 = new JButton();
		button2.setSize(200, 100);
		button2.setLocation(0, 100);
		button2.setText("HOST");
		
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.useServer(true);
			}
		});
		
		add(button2);
		
		setVisible(true);
	}
}
