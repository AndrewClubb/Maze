package droidPD;

import stackPD.*;
import mazePD.*;
import mazePD.Maze.*;

public class Droid implements DroidInterface {
	private String name;
	private LinkedStack<Coordinates> droidPath;
	private Cell[][][] myMaze;
	private Coordinates myCords;
	private int mazeDepth;
	private int mazeDim;
	
	public Droid() {
		droidPath = new LinkedStack<Coordinates>();
	}
	
	public Droid(String name) {
		this();
		this.name = name;
	}
	
	public void solveMaze(Maze maze) {
		boolean isFinished = false;
		
		this.initializeMyMaze(maze);
		
		maze.enterMaze(this);
		
		myCords = maze.getCurrentCoordinates(this);
		droidPath.push(myCords);
		this.setMyCell(myCords, maze.scanCurLoc(this));
		this.visitedCell(myCords);
		
		while(!isFinished) {
			this.scanAdjCells(maze);
			
			if(moveToAdjCells(maze)) {
				myCords = maze.getCurrentCoordinates(this);
				myMaze[myCords.getX()][myCords.getY()][myCords.getZ()].setBeenVisited(true);
				droidPath.push(myCords);
			}
			else {
				droidPath.pop();
				Coordinates tempCords = droidPath.peek();
				
				if(tempCords.getX() == myCords.getX())
				{
					if(tempCords.getY() > myCords.getY()) //Move South
						maze.move(this, Direction.D180);
					else //Move North
						maze.move(this, Direction.D00);
				}
				else
				{
					if(tempCords.getX() > myCords.getX()) //Move East 
						maze.move(this, Direction.D90);
					else //Move West
						maze.move(this, Direction.D270);
				}
				
				myCords = maze.getCurrentCoordinates(this);
			}
			
			if(checkCellContent(myCords, Content.END))
				isFinished = true;
			else if (checkCellContent(myCords, Content.PORTAL_DN)) {
				maze.usePortal(this, Direction.DN);
				myCords = maze.getCurrentCoordinates(this);
				this.setMyCell(myCords, maze.scanCurLoc(this));
				droidPath.push(myCords);
			}
		}
	}
	
	public void outputPath() {
		Coordinates cords = droidPath.pop();
		String output = "";
		while (cords != null) {
			output = cords.toString() + "\n" + output;
			cords = droidPath.pop();
		}
		System.out.println();
		System.out.println("Droid Path:");
		System.out.println(output);
	}
	
	private void setMyCell(Coordinates cords, Content cellType) {
		myMaze[cords.getX()][cords.getY()][cords.getZ()].setCellType(cellType);
	}
	
	private void visitedCell(Coordinates cords) {
		myMaze[cords.getX()][cords.getY()][cords.getZ()].setBeenVisited(true);
	}
	
	private void initializeMyMaze(Maze maze) {
		this.mazeDepth = maze.getMazeDepth();
		this.mazeDim = maze.getMazeDim();
		myMaze = new Cell[mazeDim][mazeDim][mazeDepth];
		
		for(int z = 0; z < mazeDepth; z++)
			for(int y = 0; y < mazeDim; y++)
				for(int x = 0; x < mazeDim; x++)
					myMaze[x][y][z] = new Cell();
	}
	
	private void scanAdjCells(Maze maze) {
		Content[] adjCells = maze.scanAdjLoc(this);
		Coordinates cords = maze.getCurrentCoordinates(this);
		
		//Set north cell
		cords.setY(cords.getY() - 1);
		if(adjCells[0] != Content.NA && !hasBeenVisited(cords))
			this.setMyCell(cords, adjCells[0]);
		cords.setY(cords.getY() + 1);
		
		//Set east cell
		cords.setX(cords.getX() + 1);
		if(adjCells[1] != Content.NA && !hasBeenVisited(cords))
			this.setMyCell(cords, adjCells[1]);
		cords.setX(cords.getX() - 1);
		
		//Set south cell
		cords.setY(cords.getY() + 1);
		if(adjCells[2] != Content.NA && !hasBeenVisited(cords))
			this.setMyCell(cords, adjCells[2]);
		cords.setY(cords.getY() - 1);
		
		//Set west cell
		cords.setX(cords.getX() - 1);
		if(adjCells[3] != Content.NA && !hasBeenVisited(cords))
			this.setMyCell(cords, adjCells[3]);
		cords.setX(cords.getX() + 1);
	}
	
	private boolean hasBeenVisited(Coordinates cords) {
		return myMaze[cords.getX()][cords.getY()][cords.getZ()].getBeenVisited();
	}
	
	private boolean checkCellContent(Coordinates cords, Content cellType) {
		return myMaze[cords.getX()][cords.getY()][cords.getZ()].getCellType() == cellType;
	}
	
	private boolean moveToAdjCells(Maze maze) {
		Coordinates tempCords = new Coordinates(myCords.getX(), myCords.getY(), myCords.getZ());
		Boolean hasMoved = false;
		
		tempCords.setY(tempCords.getY() - 1);
		if(tempCords.getY() >= 0 && !checkCellContent(tempCords, Content.BLOCK) && !hasBeenVisited(tempCords)) {
			maze.move(this, Direction.D00);
			hasMoved = true;
		}
		tempCords.setY(tempCords.getY() + 1);
		
		//Check east
		tempCords.setX(tempCords.getX() + 1);
		if(!hasMoved && tempCords.getX() < mazeDim && !checkCellContent(tempCords, Content.BLOCK) && !hasBeenVisited(tempCords)) {
			maze.move(this, Direction.D90);
			hasMoved = true;
		}
		tempCords.setX(tempCords.getX() - 1);
		
		//Check south
		tempCords.setY(tempCords.getY() + 1);
		if(!hasMoved && tempCords.getY() < mazeDim && !checkCellContent(tempCords, Content.BLOCK) && !hasBeenVisited(tempCords)) {
			maze.move(this, Direction.D180);
			hasMoved = true;
		}
		tempCords.setY(tempCords.getY() - 1);
		
		//Check west
		tempCords.setX(tempCords.getX() - 1);
		if(!hasMoved && tempCords.getX() >= 0 && !checkCellContent(tempCords, Content.BLOCK) && !hasBeenVisited(tempCords)) {
			maze.move(this, Direction.D270);
			hasMoved = true;
		}
		tempCords.setX(tempCords.getX() + 1);
		
		return hasMoved;
	}
	
	private class Cell {
		private Boolean beenVisited;
		private Content cellType;
		
		public Cell() {
			beenVisited = false;
		}

		public Boolean getBeenVisited() {
			return beenVisited;
		}

		public void setBeenVisited(Boolean beenVisited) {

			this.beenVisited = beenVisited;
		}

		public Content getCellType() {
			return cellType;
		}

		public void setCellType(Content cellType) {
			this.cellType = cellType;
		}
	}

	public String getName() {
		return this.name;
	}
}
