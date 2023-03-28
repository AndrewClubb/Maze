package mazeUI;

import mazePD.*;
import droidPD.*;

public class MazeStart {

	public static void main(String[] args) {
		int levels = 3;
		Maze maze = new Maze(10, levels, Maze.MazeMode.NORMAL);
		Droid droid = new Droid("R2D2");
		
		for(int i = 0; i < levels; i++) {
			System.out.println("\nLevel: " + (i + 1));
			for(String string : maze.toStringLevel(i))
				System.out.println(string);
		}
		
		droid.solveMaze(maze);
		droid.outputPath();
	}
}