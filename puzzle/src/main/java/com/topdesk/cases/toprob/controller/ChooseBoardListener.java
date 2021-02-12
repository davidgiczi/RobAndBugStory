package com.topdesk.cases.toprob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;

public class ChooseBoardListener implements ActionListener {

	private GameBoard board;
	private String boardName;

	public ChooseBoardListener(GameBoard board, String boardName) {
		this.board = board;
		this.boardName = boardName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		board.getFrame().setVisible(false);
		GridStore.getGrid(boardName);
		board.createBoard(GridStore.GRID.getWidth(), GridStore.GRID.getHeight());
		board.setTitle(boardName, GridStore.GRID.getRoom(), 0);
		board.setCellValue(GridStore.GRID_AS_LIST);
		board.setBoardName(boardName);
		board.addStepListener();
		board.addStartStopListener();
	}

}
