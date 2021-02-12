package com.topdesk.cases.toprob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import com.topdesk.cases.toprob.view.GameBoard;
import com.topdesk.cases.toprob.view.InputDataBoard;

public class ValidateBoardDataListener implements ActionListener {

	private static final List<Character> FORBIDDEN_CHARS = Arrays.asList(' ', '\\', '/', '*', ',', '.', '!', '\'', '{',
			'}', '\"', '$', ';', '(', ')', '%', '=', '+', '[', ']', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'&', '@', '&');
	private InputDataBoard inputBoard;
	private String boardName;
	private int boardWidth;
	private int boardHeight;

	public ValidateBoardDataListener(InputDataBoard inputBoard) {
		this.inputBoard = inputBoard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (isGivenInputValue(inputBoard.boardName.getText(), inputBoard.boardWidth.getText(),
				inputBoard.boardHeight.getText())) {

			JOptionPane.showMessageDialog(inputBoard.frame, "HIBA: Mind a három adat megadása szükséges.");
			return;
		}

		if (!isValidName(inputBoard.boardName.getText())) {

			JOptionPane.showMessageDialog(inputBoard.frame,
					"HIBA: Az új tábla neve legalább 3 karakterből állhat és csak betűt tartalmazhat.");
			return;
		}

		if (!isValidBoardSize(inputBoard.boardWidth.getText()) || !isValidBoardSize(inputBoard.boardHeight.getText())) {

			JOptionPane.showMessageDialog(inputBoard.frame,
					"HIBA: A tábla mérete csak egész szám lehet és 2 <= szélesség, magasság <= 10.");
			return;
		}

		boardName = inputBoard.boardName.getText();
		boardWidth = Integer.parseInt(inputBoard.boardWidth.getText());
		boardHeight = Integer.parseInt(inputBoard.boardHeight.getText());
		inputBoard.deleteFrames();
		createNewBoard();
	}

	private boolean isGivenInputValue(String name, String width, String height) {

		return name.isEmpty() || width.isEmpty() || height.isEmpty();
	}

	public boolean isValidName(String name) {

		if (3 > name.length()) {
			return false;
		}

		for (int i = 0; i < name.length(); i++) {
			if (FORBIDDEN_CHARS.contains(name.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	private boolean isValidBoardSize(String widthOrHeight) {

		try {

			int value = Integer.parseInt(widthOrHeight);

			if (2 > value || 10 < value) {
				return false;
			}

		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	private void createNewBoard() {

		GameBoard board = new GameBoard();
		board.createBoard(boardWidth, boardHeight);
		board.getFrame().setTitle(boardName);
		board.getSave().setEnabled(true);
		board.getStartStop().setEnabled(false);
		board.getStep().setEnabled(false);
		board.addCellClickListener();
		board.getSave().addActionListener(new SaveNewBoardListener(board));
	}

}
