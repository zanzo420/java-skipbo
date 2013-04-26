package mist2meat.javaskipbo.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import mist2meat.javaskipbo.Main;
import mist2meat.javaskipbo.enums.GameMode;

public class SelectGamemodePopup extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8696467569367374741L;

	public SelectGamemodePopup() {
		setTitle("Select Game Type");
		
		setLocationRelativeTo(null);
		setSize(215, 225); //TODO: perfect later
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton button1 = new JButton();
		button1.setSize(200, 100);
		button1.setLocation(0, 0);
		button1.setText("1 VS 1");
		
		final JFrame frame = this;
		
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Main.setGamemode(GameMode.GAME_1VS1);
			}
		});
		
		add(button1);
		
		JButton button2 = new JButton();
		button2.setSize(200, 100);
		button2.setLocation(0, 100);
		button2.setText("2 VS 2");
		
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Main.setGamemode(GameMode.GAME_2VS2);
			}
		});
		
		add(button2);
		
		setVisible(true);
	}
}
