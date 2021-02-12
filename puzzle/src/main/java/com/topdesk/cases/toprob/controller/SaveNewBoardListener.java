package com.topdesk.cases.toprob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.topdesk.cases.toprob.app.Puzzle;
import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;

public class SaveNewBoardListener implements ActionListener {

	private GameBoard board;

	public SaveNewBoardListener(GameBoard board) {
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!isValidInputCellsValue()) {
			JOptionPane.showMessageDialog(board.getFrame(),
					"HIBA: Pontosan egy \'r\' (szoba) és egy \'k\' (konyha) mező szükséges az új táblában.");
			return;
		}
		if (!isValidRouteForBug()) {
			JOptionPane.showMessageDialog(board.getFrame(),
					"HIBA: A bogárnak legalább két mező megadása szükséges, esetleg nem folyamatos a bogár útja az új táblában.");
			return;
		}

		List<String> newGridList = createNewGridList();
		String fileName = board.getFrame().getTitle();

		if (GridStore.NAMES_OF_TABLES.contains(fileName)) {
			fileName = JOptionPane.showInputDialog("Létező fájl, felülírod?", board.getFrame().getTitle());
		}

		if (fileName != null && new ValidateBoardDataListener(null).isValidName(fileName)) {
			GridStore.saveNewGridList(newGridList, fileName);
			board.getFrame().setVisible(false);
			Puzzle.launch(fileName);
		} else if (fileName != null && !new ValidateBoardDataListener(null).isValidName(fileName)) {
			JOptionPane.showMessageDialog(board.getFrame(),
					"HIBA: Az új tábla neve legalább 3 karakterből állhat és csak betűt tartalmazhat.");
		}

	}

	private boolean isValidInputCellsValue() {

		int kitchen_pcs = 0;
		int room_pcs = 0;

		for (JButton cell : board.getCells()) {

			if ("k".equals(cell.getText())) {
				kitchen_pcs++;
			}
			if ("r".equals(cell.getText())) {
				room_pcs++;
			}
		}

		return kitchen_pcs == 1 && room_pcs == 1;
	}

	private boolean isValidRouteForBug() {

		List<Integer> route = new ArrayList<>();

		for (JButton cell : board.getCells()) {

			String value = cell.getText();

			if (!value.isBlank() && !value.isEmpty() && !"o".equals(value) && !"r".equals(value)
					&& !"k".equals(value)) {
				route.add((int) value.charAt(0));
			}

		}

		Collections.sort(route);

		if (route.size() > 1 && route.get(0) == 65) {

			for (int i = 0; i < route.size() - 1; i++) {
				if (route.get(i + 1) - route.get(i) != 1) {
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}

	private List<String> createNewGridList() {

		List<String> gridList = new ArrayList<>();
		StringBuilder rowBuilder = new StringBuilder();

		for (int i = 0; i < board.getHeightOfBoard(); i++) {
			for (int j = 0; j < board.getWidthOfBoard(); j++) {

				String cellTextValue = board.getCells()[i * board.getWidthOfBoard() + j].getText();

				if (cellTextValue.isBlank() || cellTextValue.isEmpty()) {
					rowBuilder.append(".");
				} else {
					rowBuilder.append(cellTextValue);
				}

			}

			gridList.add(rowBuilder.toString());
			rowBuilder.delete(0, board.getWidthOfBoard());

		}

		return gridList;
	}

}
