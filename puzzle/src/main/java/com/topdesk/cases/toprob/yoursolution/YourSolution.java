package com.topdesk.cases.toprob.yoursolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.topdesk.cases.toprob.Coordinate;
import com.topdesk.cases.toprob.Grid;
import com.topdesk.cases.toprob.Instruction;
import com.topdesk.cases.toprob.Solution;

public class YourSolution implements Solution {

	private Coordinate actualPositionOfRob;
	private List<Coordinate> routeCoordinates;
	private List<Coordinate> neighboursCoordinates;
	private List<Instruction> instructions;
	private Grid grid;

	public YourSolution() {
	}
	
	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}
		
	public List<Instruction> getInstructions() {
		return instructions;
	}

	@Override
	public List<Instruction> solve(Grid grid, int time) {
	
		if (0 > time) {
			throw new IllegalArgumentException("time must be zero or positive value");
		}
		init();
		int ellapsedSec = goFromRoomToKitchen(grid, time);
		List<Instruction> instructions = parseRouteCoordinatesToInstructions(grid);
		init();
		goFromKitchenToRoom(grid, ellapsedSec);
		instructions.addAll(parseRouteCoordinatesToInstructions(grid));

		return instructions;
	}

	private void init() {
		routeCoordinates = new ArrayList<>();
		neighboursCoordinates = new ArrayList<>();
	}

	private int goFromRoomToKitchen(Grid grid, int time) {

		actualPositionOfRob = grid.getRoom();
		routeCoordinates.add(actualPositionOfRob);

		while (!isKitchenCell(actualPositionOfRob, grid)) {
			storeNeighboursCellsOfBob(grid);
			removeHoleAndExistedRouteCellFromNeighbourCells(grid);
			Coordinate theClosest = getTheClosestCellFromNeighbourCellsToTargetCell(grid.getKitchen());
			if (!isBugOneSecLater(theClosest, grid, time)) {
				routeCoordinates.add(theClosest);
				actualPositionOfRob = theClosest;
			} else {
				routeCoordinates.add(actualPositionOfRob);
			}
			time++;
		}
		return time + 5;
	}

	private void goFromKitchenToRoom(Grid grid, int time) {

		actualPositionOfRob = grid.getKitchen();
		routeCoordinates.add(actualPositionOfRob);

		while (!isRoomCell(actualPositionOfRob, grid)) {

			storeNeighboursCellsOfBob(grid);
			removeHoleAndExistedRouteCellFromNeighbourCells(grid);
			Coordinate theClosest = getTheClosestCellFromNeighbourCellsToTargetCell(grid.getRoom());
			if (!isBugOneSecLater(theClosest, grid, time)) {
				routeCoordinates.add(theClosest);
				actualPositionOfRob = theClosest;
			} else {
				routeCoordinates.add(actualPositionOfRob);
			}
			time++;
		}

	}

	private void storeNeighboursCellsOfBob(Grid grid) {

		neighboursCoordinates.clear();

		int x = actualPositionOfRob.getX();
		int y = actualPositionOfRob.getY();

		if (y - 1 >= 0 && y < grid.getHeight() && x >= 0 && x < grid.getWidth()) {
			neighboursCoordinates.add(new Coordinate(x, y - 1));
		}
		if (grid.getWidth() > x + 1 && x >= 0 && y >= 0 && y < grid.getHeight()) {
			neighboursCoordinates.add(new Coordinate(x + 1, y));
		}
		if (grid.getHeight() > y + 1 && y >= 0 && x >= 0 && x < grid.getWidth()) {
			neighboursCoordinates.add(new Coordinate(x, y + 1));
		}
		if (x - 1 >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight()) {
			neighboursCoordinates.add(new Coordinate(x - 1, y));
		}

	}

	private void removeHoleAndExistedRouteCellFromNeighbourCells(Grid grid) {

		for (int i = neighboursCoordinates.size() - 1; i >= 0; i--) {
			if (isHoleCell(neighboursCoordinates.get(i), grid)
					|| isExistedRouteCell(neighboursCoordinates.get(i), grid)) {
				neighboursCoordinates.remove(neighboursCoordinates.get(i));
			}
		}
	}

	private Coordinate getTheClosestCellFromNeighbourCellsToTargetCell(Coordinate targetCell)
			throws RobCannotBeSteppedException {

		if (neighboursCoordinates.isEmpty()) {
	
			instructions = parseRouteCoordinatesToInstructions(this.grid);
			throw new RobCannotBeSteppedException("Rob elakadt a(z) " + actualPositionOfRob + " cellában.");
			
		} else if (neighboursCoordinates.size() == 1) {
			return neighboursCoordinates.get(0);
		} else if (neighboursCoordinates.contains(targetCell)) {
			return targetCell;
		}

		Coordinate theClosest = neighboursCoordinates.get(0);
		int minDistance = Math.abs(targetCell.getX() - neighboursCoordinates.get(0).getX())
				+ Math.abs(targetCell.getY() - neighboursCoordinates.get(0).getY());

		for (Coordinate coord : neighboursCoordinates) {

			int distance = Math.abs(targetCell.getX() - coord.getX()) + Math.abs(targetCell.getY() - coord.getY());

			if (minDistance > distance) {
				minDistance = distance;
				theClosest = coord;
			}

		}

		return theClosest;
	}

	private List<Instruction> parseRouteCoordinatesToInstructions(Grid grid) {

		List<Instruction> instructions = new ArrayList<>();

		for (int i = 0; i < routeCoordinates.size() - 1; i++) {

			if (Instruction.EAST.execute(routeCoordinates.get(i)).equals(routeCoordinates.get(i + 1))) {

				instructions.add(Instruction.EAST);
			} else if (Instruction.SOUTH.execute(routeCoordinates.get(i)).equals(routeCoordinates.get(i + 1))) {

				instructions.add(Instruction.SOUTH);
			} else if (Instruction.WEST.execute(routeCoordinates.get(i)).equals(routeCoordinates.get(i + 1))) {

				instructions.add(Instruction.WEST);
			} else if (Instruction.NORTH.execute(routeCoordinates.get(i)).equals(routeCoordinates.get(i + 1))) {

				instructions.add(Instruction.NORTH);
			} else if (Instruction.PAUSE.execute(routeCoordinates.get(i)).equals(routeCoordinates.get(i + 1))) {

				instructions.add(Instruction.PAUSE);
			}
		}

		if (isKitchenCell(routeCoordinates.get(routeCoordinates.size() - 1), grid)) {
			instructions.addAll(Arrays.asList(Instruction.PAUSE, Instruction.PAUSE, Instruction.PAUSE,
					Instruction.PAUSE, Instruction.PAUSE));
		}

		return instructions;
	}

	private boolean isKitchenCell(Coordinate coord, Grid grid) {
		return coord.equals(grid.getKitchen());
	}

	private boolean isRoomCell(Coordinate coord, Grid grid) {
		return coord.equals(grid.getRoom());
	}

	private boolean isHoleCell(Coordinate coord, Grid grid) {
		return grid.getHoles().contains(coord);
	}

	private boolean isExistedRouteCell(Coordinate coord, Grid grid) {
		return routeCoordinates.contains(coord);
	}

	private boolean isBugOneSecLater(Coordinate coord, Grid grid, int time) {
		return coord.equals(grid.getBug(time + 1));
	}

}
