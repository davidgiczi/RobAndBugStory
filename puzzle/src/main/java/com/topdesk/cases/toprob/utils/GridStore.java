package com.topdesk.cases.toprob.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.topdesk.cases.toprob.Grid;
import com.topdesk.cases.toprob.helper.GridFactory;

public final class GridStore {

	public static Grid GRID;
	public static List<String> GRID_AS_LIST;
	public static List<String> NAMES_OF_TABLES;
	private static String[] InputGrid;

	static {
		NAMES_OF_TABLES = new ArrayList<>();
		NAMES_OF_TABLES.add("Basic Grid");
		NAMES_OF_TABLES.add("Small Grid");
		NAMES_OF_TABLES.add("Small Grid Bug Everywhere");
		NAMES_OF_TABLES.add("Big Grid");
		NAMES_OF_TABLES.add("Big Grid Bug Everywhere");
		NAMES_OF_TABLES.add("Annoying Bug Grid");
	}

	private GridStore() {
	}

	public static void loadTables() {

		File dir = new File("./grids");

		if (dir.isDirectory()) {

			File[] store = dir.listFiles();

			for (File file : store) {

				String fileName = file.getName().substring(0, file.getName().length() - 4);

				if (!NAMES_OF_TABLES.contains(fileName)) {
					NAMES_OF_TABLES.add(fileName);
				}
			}
		}
	}

	private static final Grid basicGrid = GridFactory.create(".....", ".r.o.", ".B.k.", ".A.o.", ".....");
	private static final Grid smallGrid = GridFactory.create(".rBA.", "...o.", ".ook.", "..o..");
	private static final Grid smallGrid_bug_everywhere = GridFactory.create("NrBAM", "ECDoL", "FookK", "GHoIJ");
	private static final Grid bigGrid = GridFactory.create(".rBA......", "...o......", "......oko.", "..o.......",
			"..........", "..........", "..........", "..........", "..........", "..........");
	private static final Grid bigGrid_bug_everywhere = GridFactory.create(".rBA......", "...oW.X.Y.", "...U.ookN.",
			".Do....J..", ".C......Z.", ".E...I....", ".F........", ".V.GH...K.", ".......LM.", "...TSRQPO.");

	private static final Grid annoyingBugGrid = GridFactory.create("rBDFHJLNk", ".ACEGIKMO");

	public static void getGrid(String gridName) {

		if (gridName.equals(NAMES_OF_TABLES.get(0))) {
			GRID = basicGrid;
		} else if (gridName.equals(NAMES_OF_TABLES.get(1))) {
			GRID = smallGrid;
		} else if (gridName.equals(NAMES_OF_TABLES.get(2))) {
			GRID = smallGrid_bug_everywhere;
		} else if (gridName.equals(NAMES_OF_TABLES.get(3))) {
			GRID = bigGrid;
		} else if (gridName.equals(NAMES_OF_TABLES.get(4))) {
			GRID = bigGrid_bug_everywhere;
		} else if (gridName.equals(NAMES_OF_TABLES.get(5))) {
			GRID = annoyingBugGrid;
		} else {

			InputGrid = getSavedGridData(gridName);
			GRID = GridFactory.create(InputGrid);

		}

		createGridList(gridName);
	}

	private static String[] getSavedGridData(String gridName) {

		List<String> gridData = new ArrayList<>();
		File file = new File("./grids/" + gridName + ".txt");
		try (BufferedReader bfr = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")));) {

			String row;

			while ((row = bfr.readLine()) != null) {
				gridData.add(row);
			}

		} catch (IOException e) {
			System.out.println("The file cannot be loaded: " + file.getName());
		}

		return gridData.toArray(new String[gridData.size()]);
	}

	private static void createGridList(String gridName) {

		if (gridName.equals(NAMES_OF_TABLES.get(0))) {

			GRID_AS_LIST = Arrays.asList(".", ".", ".", ".", ".", ".", "r", ".", "o", ".", ".", "B", ".", "k", ".", ".",
					"A", ".", "o", ".", ".", ".", ".", ".", ".");
		} else if (gridName.equals(NAMES_OF_TABLES.get(1))) {
			GRID_AS_LIST = Arrays.asList(".", "r", "B", "A", ".", ".", ".", ".", "o", ".", ".", "o", "o", "k", ".", ".",
					".", "o", ".", ".");
		} else if (gridName.equals(NAMES_OF_TABLES.get(2))) {
			GRID_AS_LIST = Arrays.asList("N", "r", "B", "A", "M", "E", "C", "D", "o", "L", "F", "o", "o", "k", "K", "G",
					"H", "o", "I", "J");
		} else if (gridName.equals(NAMES_OF_TABLES.get(3))) {
			GRID_AS_LIST = Arrays.asList(".", "r", "B", "A", ".", ".", ".", ".", ".", ".", ".", ".", ".", "o", ".", ".",
					".", ".", ".", ".", ".", ".", ".", ".", ".", ".", "o", "k", "o", ".", ".", ".", "o", ".", ".", ".",
					".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
					".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
					".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
					".", ".", ".", ".");
		} else if (gridName.equals(NAMES_OF_TABLES.get(4))) {
			GRID_AS_LIST = Arrays.asList(".", "r", "B", "A", ".", ".", ".", ".", ".", ".", ".", ".", ".", "o", "W", ".",
					"X", ".", "Y", ".", ".", ".", ".", "U", ".", "o", "o", "k", "N", ".", ".", "D", "o", ".", ".", ".",
					".", "J", ".", ".", ".", "C", ".", ".", ".", ".", ".", ".", "Z", ".", ".", "E", ".", ".", ".", "I",
					".", ".", ".", ".", ".", "F", ".", ".", ".", ".", ".", ".", ".", ".", ".", "V", ".", "G", "H", ".",
					".", ".", "K", ".", ".", ".", ".", ".", ".", ".", ".", "L", "M", ".", ".", ".", ".", "T", "S", "R",
					"Q", "P", "O", ".");
		} else if (gridName.equals(NAMES_OF_TABLES.get(5))) {
			GRID_AS_LIST = Arrays.asList("r", "B", "D", "F", "H", "J", "L", "N", "k", ".", "A", "C", "E", "G", "I", "K",
					"M", "O");
		} else {
			GRID_AS_LIST = createSavedGridList(gridName);
		}

	}

	private static List<String> createSavedGridList(String gridName) {
		List<String> gridList = new ArrayList<>();
		for (String row : InputGrid) {
			for (int i = 0; i < row.length(); i++) {
				gridList.add(String.valueOf(row.charAt(i)));
			}

		}
		return gridList;
	}

	public static void saveNewGridList(List<String> gridList, String boardName) {

		File file = new File("./grids/" + boardName + ".txt");

		try (BufferedWriter bfr = new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8")));) {

			file.createNewFile();

			for (String row : gridList) {
				bfr.write(row);
				bfr.newLine();
			}

		} catch (IOException e) {
			System.out.println("The file cannot be created: " + file.getName());
		}

	}

	public static boolean deleteBoard(String boardName) {

		if (NAMES_OF_TABLES.contains(boardName) && NAMES_OF_TABLES.indexOf(boardName) > 5) {

			NAMES_OF_TABLES.remove(boardName);

			return new File("./grids/" + boardName + ".txt").delete();
		}

		return false;
	}

}
