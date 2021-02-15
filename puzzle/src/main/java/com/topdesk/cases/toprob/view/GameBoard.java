package com.topdesk.cases.toprob.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import com.topdesk.cases.toprob.Coordinate;
import com.topdesk.cases.toprob.controller.CellClickListener;
import com.topdesk.cases.toprob.controller.ChooseBoardListener;
import com.topdesk.cases.toprob.controller.DeleteBoardListener;
import com.topdesk.cases.toprob.controller.EditBoardListener;
import com.topdesk.cases.toprob.controller.StepListener;
import com.topdesk.cases.toprob.utils.GridStore;



public class GameBoard {

	private JFrame frame;
	private JButton cells[];
	private Font font;
	private Timer timer;
	private JMenu boardsMenu;
	private JMenu optionsMenu;
	private JMenuItem startStop;
	private JMenuItem save;
	private JMenuItem step;
	private JMenuItem edit;
	private JMenuItem delete;
	private List<JMenuItem> boards;
	private JMenuItem newBoard;
	private JMenuBar menuBar;
	private String boardName;
	private ImageIcon rob;
	private ImageIcon bug;
	private InputDataBoard inputBoard;
	private int widthOfBoard;
	private int heightOfBoard;
	private StepListener stepListener;
	
	
	public GameBoard() {
		font = new Font("Book Antiqua", Font.BOLD, 30);
		rob = new ImageIcon("./img/rob.jpg");
		bug = new ImageIcon("./img/bug.jpg");
	}
	
	public void createBoard(int width, int height) {
		
		widthOfBoard = width;
		heightOfBoard = height;
		boards = new ArrayList<>();
		GridStore.loadTables();
		GridStore.NAMES_OF_TABLES.forEach(n -> boards.add(new JMenuItem(n)));
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(width * 100, height * 100);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(height, width));
		cells = new JButton[width * height];
		int index = 0;
		for (JButton c : cells) {
			c = new JButton();
			c.setBackground(Color.WHITE);
			cells[index++] = c;
			frame.add(c);
		}
		boardsMenu = new JMenu("Táblák");
		optionsMenu = new JMenu("Opciók");
		startStop = new JMenuItem("Start/Stop");
		save = new JMenuItem("Mentés");
		save.setEnabled(false);
		step = new JMenuItem("Lépés");
		stepListener = new StepListener(this);
		step.addActionListener(stepListener);
		edit = new JMenuItem("Módosítás");
		edit.addActionListener(new EditBoardListener(this));
		delete = new JMenuItem("Törlés");
		delete.addActionListener(new DeleteBoardListener(this));
		newBoard = new JMenuItem("Új tábla létrehozása");
		menuBar = new JMenuBar();
		boards.forEach(b -> boardsMenu.add(b));
		boardsMenu.addSeparator();
		boardsMenu.add(newBoard);
		optionsMenu.add(startStop);
		optionsMenu.add(step);
		optionsMenu.add(edit);
		optionsMenu.add(save);
		optionsMenu.add(delete);
		menuBar.add(boardsMenu);
		menuBar.add(optionsMenu);
		frame.setJMenuBar(menuBar);
		addListenerForMenu();
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Timer getTimer() {
		return timer;
	}

	public JMenuItem getStartStop() {
		return startStop;
	}

	public JMenuItem getSave() {
		return save;
	}
		
	public JMenuItem getEdit() {
		return edit;
	}
	
	public JMenuItem getStep() {
		return step;
	}
	
	public JMenuItem getDelete() {
		return delete;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}
	
	public JButton[] getCells() {
		return cells;
	}

	public void setTitle(String boardName, Coordinate actualPosition, int sec) {
		frame.setTitle(boardName + " - Rob a(z) " + actualPosition.toString().substring(0, actualPosition.toString().length() - 1) + ", " + sec  +"] cellában van.");
	}
	
	public int getWidthOfBoard() {
		return widthOfBoard;
	}

	public int getHeightOfBoard() {
		return heightOfBoard;
	}

	public void setCellValue(List<String> gridAsList) {
		
		int index = 0;
		for (String value : gridAsList) {
			
			if( !".".equals(value)) {
				
			cells[index].setFont(font);
			
			if("r".equals(value)) {
				cells[index].setIcon(rob);
			}
			else if("A".equals(value)) {
				cells[index].setForeground(Color.RED);
				cells[index].setIcon(bug);
			}
			else if("k".equals(value)) {
				cells[index].setForeground(Color.BLUE);
				cells[index].setText(value);
			}
			else if(!"o".equals(value)) {
				cells[index].setForeground(Color.RED);
				cells[index].setText(value);
			}
			else {
				cells[index].setText(value);
			}
		}	
			index++;	
	}	
}
	
public void addListenerForMenu() {

	boards.forEach(i -> i.addActionListener(new ChooseBoardListener(this, i.getText())));
	newBoard.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (inputBoard == null) {
				inputBoard = new InputDataBoard(frame);
			} else {
				inputBoard.frame.setVisible(true);
			}

		}
	});
}
	
	public void addStartStopListener() {
		startStop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(timer.isRunning()) {
					timer.stop();
					step.setEnabled(true);
					edit.setEnabled(true);
					delete.setEnabled(true);
				}
				else {
					timer.start();
					step.setEnabled(false);
					edit.setEnabled(false);
					delete.setEnabled(false);
				}
				
			}
		});
	}
	
	public void addStepListener() {
		if(timer != null) {
		timer.stop();
		}
		timer = new Timer(1000, stepListener);
	}
	
	public void addCellClickListener() {
		for (JButton cell : cells) {
			cell.addActionListener(new CellClickListener(cell));
		}
	}
	
	
	public void clearImageIcon(int index, List<String> gridAsList) {
		
		cells[index].setIcon(null);
		
		if("r".equals(gridAsList.get(index)) || "k".equals(gridAsList.get(index))){
			cells[index].setForeground(Color.BLUE);
		}
				
		if( !".".equals(gridAsList.get(index))) {
			cells[index].setText(gridAsList.get(index));
			}
	}
	
	public void showBug(int index, List<String> gridAsList) {
		
		cells[index].setText("");
		cells[index].setIcon(bug);
		
	}
	
	public void showRob(int index, List<String> gridAsList) {
		
		cells[index].setText("");
		cells[index].setIcon(rob);
		
	}
}
