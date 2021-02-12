package com.topdesk.cases.toprob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import com.topdesk.cases.toprob.app.Puzzle;
import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;

public class DeleteBoardListener implements ActionListener {

	private GameBoard board;

	public DeleteBoardListener(GameBoard board) {
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (JOptionPane.showConfirmDialog(null, "Biztosan törölni akarod a táblát?", "Figyelmeztetés",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

			if (GridStore.deleteBoard(board.getBoardName())) {

				board.getFrame().setVisible(false);
				Puzzle.launch("Basic Grid");
				JOptionPane.showMessageDialog(board.getFrame(), "\"" + board.getBoardName() + "\" tábla törölve.");

			} else {
				JOptionPane.showMessageDialog(board.getFrame(), "A tábla nem törölhető.");
			}

		}
	}
}