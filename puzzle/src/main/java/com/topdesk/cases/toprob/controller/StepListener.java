package com.topdesk.cases.toprob.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import com.topdesk.cases.toprob.Coordinate;
import com.topdesk.cases.toprob.Instruction;
import com.topdesk.cases.toprob.utils.GridStore;
import com.topdesk.cases.toprob.view.GameBoard;
import com.topdesk.cases.toprob.yoursolution.RobCannotBeSteppedException;
import com.topdesk.cases.toprob.yoursolution.YourSolution;

public class StepListener implements ActionListener {

	private int sec;
	private GameBoard board;
	private List<Instruction> instructions;
	private Coordinate actualPositionOfBug;
	private Coordinate actualPositionOfRob;
	private String stuckingMessage;
	private boolean isStucking;

	public StepListener(GameBoard board) {
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (sec == 0) {
			init();
		}
		stepBug();
		stepRob();
		board.setTitle(board.getBoardName(), actualPositionOfRob, ++sec);
		if (instructions.isEmpty() || sec == instructions.size()) {
			board.getTimer().stop();
			if (isStucking) {
				JOptionPane.showMessageDialog(board.getFrame(), stuckingMessage);
				board.getStartStop().setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(board.getFrame(), "Rob " + sec + " sec alatt visszatért a szobájába.");
			}
			sec = 0;
		}

	}

	public void stepBug() {

		int actualIndexOfBug = actualPositionOfBug.getY() * GridStore.GRID.getWidth() + actualPositionOfBug.getX();
		board.clearImageIcon(actualIndexOfBug, GridStore.GRID_AS_LIST);
		int nextIndexOfBug = getNextStepIndexOfBug();
		board.showBug(nextIndexOfBug, GridStore.GRID_AS_LIST);
	}

	public void stepRob() {

		int actualIndexOfRob = actualPositionOfRob.getY() * GridStore.GRID.getWidth() + actualPositionOfRob.getX();
		if (!actualPositionOfRob.equals(actualPositionOfBug)) {
			board.clearImageIcon(actualIndexOfRob, GridStore.GRID_AS_LIST);
		}
		int nextIndexOfRob = getNextStepIndexOfBob();
		board.showRob(nextIndexOfRob, GridStore.GRID_AS_LIST);
	}

	public void init() {

		GridStore.getGrid(board.getBoardName());
		YourSolution mySolution = new YourSolution();
		mySolution.setGrid(GridStore.GRID);
		try {
			instructions = mySolution.solve(GridStore.GRID, 0);
			
		} catch (RobCannotBeSteppedException e) {
			instructions = mySolution.getInstructions();
			stuckingMessage = e.getMessage();
			isStucking = true;
		}
		actualPositionOfRob = GridStore.GRID.getRoom();
		actualPositionOfBug = GridStore.GRID.getBug(sec);
	}

	private int getNextStepIndexOfBug() {
		Coordinate nextBugPosition = GridStore.GRID.getBug(sec + 1);
		actualPositionOfBug = nextBugPosition;
		return nextBugPosition.getY() * GridStore.GRID.getWidth() + nextBugPosition.getX();
	}

	private int getNextStepIndexOfBob() {

		Coordinate nextRobPosition = actualPositionOfRob;

		if (!instructions.isEmpty()) {

			if (instructions.get(sec) == Instruction.EAST) {
				nextRobPosition = Instruction.EAST.execute(nextRobPosition);
			} else if (instructions.get(sec) == Instruction.SOUTH) {
				nextRobPosition = Instruction.SOUTH.execute(nextRobPosition);
			} else if (instructions.get(sec) == Instruction.WEST) {
				nextRobPosition = Instruction.WEST.execute(nextRobPosition);
			} else if (instructions.get(sec) == Instruction.NORTH) {
				nextRobPosition = Instruction.NORTH.execute(nextRobPosition);
			} else if (instructions.get(sec) == Instruction.PAUSE) {
				nextRobPosition = Instruction.PAUSE.execute(nextRobPosition);
			}

			actualPositionOfRob = nextRobPosition;
		}

		return nextRobPosition.getY() * GridStore.GRID.getWidth() + nextRobPosition.getX();
	}

}
