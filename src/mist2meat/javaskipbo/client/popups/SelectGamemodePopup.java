package mist2meat.javaskipbo.client.popups;

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
		setTitle("Select Game");
		
		setLocationRelativeTo(null);
		setSize(200+6, 264+28);
		setLayout(null);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JButton button1 = new JButton();
		button1.setSize(200, 66);
		button1.setLocation(0, 0);
		button1.setText("2 Players");
		
		final JFrame frame = this;
		
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.setGamemode(GameMode.GAME_1VS1);
			}
		});
		
		add(button1);
		
		JButton button2 = new JButton();
		button2.setSize(200, 66);
		button2.setLocation(0, 66);
		button2.setText("3 Players");
		
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.setGamemode(GameMode.GAME_1VS1VS1);
			}
		});
		
		add(button2);
		
		JButton button3 = new JButton();
		button3.setSize(200, 66);
		button3.setLocation(0, 132);
		button3.setText("4 Players");
		
		button3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.setGamemode(GameMode.GAME_1VS1VS1VS1);
			}
		});
		
		add(button3);
		
		JButton button4 = new JButton();
		button4.setSize(200, 66);
		button4.setLocation(0, 198);
		button4.setText("4 Players - 2 Player COOP");
		
		button4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				Main.setGamemode(GameMode.GAME_2VS2);
			}
		});
		
		add(button4);
		
		setVisible(true);
	}
}
