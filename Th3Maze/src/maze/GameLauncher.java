package maze;

import java.util.Random;

public class GameLauncher {

	public static void main(String[] args) {
		Random rn = new Random();
		//create a tower and generate a number of floor
		Tower tower = new Tower(rn.nextInt(Config.MAX_NB_FLOOR - Config.MIN_NB_FLOOR + 1) + Config.MIN_NB_FLOOR);
		
		//for each floor generate the maze
		for(int i = 0; i < tower.floors.length;i++){
			Floor f = new Floor(i);
			f.createMaze();
		}
	}

}
