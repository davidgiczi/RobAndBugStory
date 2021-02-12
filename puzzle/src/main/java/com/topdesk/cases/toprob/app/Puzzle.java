package com.topdesk.cases.toprob.app;

import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;

public class Puzzle {

	public static void main(String[] args) {

		launch("Basic Grid");

	}

	public static void launch(String gridName) {
		GridStore.getGrid(gridName);
		GameBoard board = new GameBoard();
		board.createBoard(GridStore.GRID.getWidth(), GridStore.GRID.getHeight());
		board.setCellValue(GridStore.GRID_AS_LIST);
		board.setTitle(gridName, GridStore.GRID.getRoom(), 0);
		board.setBoardName(gridName);
		board.addStepListener();
		board.addStartStopListener();
	}

}
