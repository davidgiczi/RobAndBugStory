package com.topdesk.cases.toprob.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class CellClickListener implements ActionListener {

	private JButton cell;
	private static final char[] LETTERS = { 'r', 'k', 'o', ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private Font font = new Font("Book Antiqua", Font.BOLD, 30);
	private int index = 0;

	public CellClickListener(JButton cell) {
		this.cell = cell;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LETTERS[index] == 'r' || LETTERS[index] == 'k') {
			cell.setForeground(Color.BLUE);
		} else if (LETTERS[index] == 'o') {
			cell.setForeground(Color.BLACK);
		} else {
			cell.setForeground(Color.RED);
		}
		cell.setFont(font);
		cell.setText(String.valueOf(LETTERS[index]));
		index++;
		if (index == LETTERS.length) {
			index = 0;
		}
	}

}
