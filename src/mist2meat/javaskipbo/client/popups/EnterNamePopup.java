package mist2meat.javaskipbo.client.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import mist2meat.javaskipbo.Main;

public class EnterNamePopup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7785251142342191970L;

	public EnterNamePopup() {
		setTitle("Enter name");
		
		setLocationRelativeTo(null);
		setSize(215, 225); //TODO: perfect later
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		final JTextField name = new JTextField();
		name.setText("Enter name");
		name.setSize(200, 20);
		name.setLocation(5,5);
		
		add(name);
		
		JButton button1 = new JButton();
		button1.setSize(80, 50);
		button1.setLocation(0, 60);
		button1.setText("JOIN");
		
		final JFrame frame = this;
		
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.client.setName(name.getText());
				frame.dispose();
			}

		});
		
		add(button1);
		
		setVisible(true);
	}
}

