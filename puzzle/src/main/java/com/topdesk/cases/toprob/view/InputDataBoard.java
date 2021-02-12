package com.topdesk.cases.toprob.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.topdesk.cases.toprob.controller.ValidateBoardDataListener;

public class InputDataBoard {

	private JButton ok;
	public JFrame frame;
	public JTextField boardName;
	public JTextField boardWidth;
	public JTextField boardHeight;
	public List<JFrame> frameStore;

	public InputDataBoard(JFrame frame) {
		this.frame = new JFrame("Adatbevitel");
		frameStore = new ArrayList<>();
		frameStore.add(frame);
		frameStore.add(this.frame);
		boardName = new JTextField(30);
		boardWidth = new JTextField(30);
		boardHeight = new JTextField(30);
		ok = new JButton("Ok");
		createBoard();
	}

	private void createBoard() {

		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(500, 250);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(4, 2));
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.add(new JLabel("Add meg az új tábla nevét: "));
		panel1.add(boardName);
		frame.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.WHITE);
		panel2.add(new JLabel("Add meg az új tábla szélességét: "));
		panel2.add(boardWidth);
		frame.add(panel2);
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.WHITE);
		panel3.add(new JLabel("Add meg az új tábla magasságát: "));
		panel3.add(boardHeight);
		frame.add(panel3);
		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.WHITE);
		ok.addActionListener(new ValidateBoardDataListener(this));
		panel4.add(ok);
		frame.add(panel4);
		frame.setVisible(true);
	}

	public void deleteFrames() {
		frameStore.forEach(f -> f.setVisible(false));
		frameStore.forEach(f -> f = null);
	}
}
