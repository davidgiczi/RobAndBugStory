package com.topdesk.cases.toprob.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;

public class EditBoardListener implements ActionListener {

	private GameBoard board;

	public EditBoardListener(GameBoard board) {
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (6 > GridStore.NAMES_OF_TABLES.indexOf(board.getBoardName())) {
			JOptionPane.showMessageDialog(board.getFrame(), "A tábla nem módosítható.");
			return;
		}

		board.getStartStop().setEnabled(false);
		board.getSave().setEnabled(true);
		board.addCellClickListener();
		board.getSave().addActionListener(new SaveNewBoardListener(board));
		board.getEdit().setEnabled(false);
		board.getStep().setEnabled(false);
		clearImageIcons();
		String[] titleComponents = board.getFrame().getTitle().split("\\ ");
		board.getFrame().setTitle(titleComponents[0].trim());
	}

	private void clearImageIcons() {

		boolean isRob = false;
		for (JButton cell : board.getCells()) {

			if (cell.getText().equals("r")) {
				isRob = true;
			}

			if (cell.getIcon() != null) {
				cell.setIcon(null);
				if (!isRob) {
					cell.setFont(new Font("Book Antiqua", Font.BOLD, 30));
					cell.setForeground(Color.BLUE);
					cell.setText("r");
					isRob = true;
				}
			}
		}
	}

}
