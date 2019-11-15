package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateFloor {
	
	//these valued are used for the position array in the tile structure
	private final int X = 0;
	private final int Y = 0;
	
	private int x;
	private int y;
	private int z;
	private int minSide;//minimum value the side of a maze can be
	private int maxSide;//maximum value the side of a maze can be
	private int sideX;//side x of the maze
	private int sideY;//side y of the maze
	
	private Tile startTile;
	private Tile endTile;
	
	static Utils utils = new Utils();
	
	
	public GenerateFloor(int z){
		System.out.println("=====Setting the max and min size of the floor=====");
		minSide = Config.MIN_SIDE_MAZE;
		maxSide = Config.MAX_SIDE_MAZE;
		this.z = z;
		System.out.println("The floor is on the level " + this.z);
		System.out.println("=====End of the setting=====");
	}
	
	//first step in generating the maze is to generate the size of it
	public void generateMazeSize(){
		System.out.println("=====Generating the size of the floor=====");
		this.sideX = utils.generateRandomInt(Config.MIN_SIDE_MAZE, Config.MAX_SIDE_MAZE);
		this.sideY = utils.generateRandomInt(Config.MIN_SIDE_MAZE, Config.MAX_SIDE_MAZE);
		System.out.println();
	}
	/**
	 * Generate a starting tile that isn't on the edge of the maze
	 * @param sideX side x of the maze (width)
	 * @param sideY side y of the maze (height)
	 */
	public void generateStartTile(){
		System.out.println("=====Generating the start tile=====");
		this.x = utils.generateRandomInt(1, this.sideX - 2);// sideX - 2 gives us the left edge of the maze
		this.y = utils.generateRandomInt(1, this.sideY - 2);
		System.out.println("Start Tile info :");
		System.out.println("The x value is : " + x);
		System.out.println("The y value is : " + y);
		
		//create a new tile that has those coordinate
		System.out.println("Creating the startTile object");
		startTile = new Tile(x, y, z, true, false, false, false);
		System.out.println("=====End of the Generation of the start tile=====");
	}
	
	public int [] getStartingCoordinate() {
		int [] startingCoordinate = {this.startTile.position[0], this.startTile.position[1]};
		return startingCoordinate;
	}
	
	void generateEndTile(){
		System.out.println("=====Generating the end tile=====");
		this.x = utils.generateRandomInt(1, this.sideX - 1);
		this.y = utils.generateRandomInt(1, this.sideY - 1);
		System.out.println("End Tile info :");
		System.out.println("The x value is : " + x);
		System.out.println("The y value is : " + y);
		
		//create the endTile
		System.out.println("Creating the endTile object");
		endTile = new Tile(x, y, z, false, true, false, false);
		System.out.println("=====End of the Generation of the end tile=====");
	}
	
	void generatePathtoEnd(Tile [][] floor){
		System.out.println("=====Generating the path from the start tile to the end tile=====");
		int minTilePlaces = this.sideX + (this.sideY - 1);//minimum tile that would have to be place for the path to the end
		int maxTilePlaces = (int)(this.sideX * this.sideY * 0.75);//maximum tile that can be place to complete the path to the end
		int nbOfTilePlace = 0;
		boolean isPathCompleted = false;
		ArrayList<Tile> pathToEnd = new ArrayList<Tile>();
		
		int currX = startTile.position[X];//current position and we start with the start tile
		int currY = startTile.position[Y];
		
		int nextX = -1;//variable used to fetch the next tile in the tiles
		int nextY = -1;
		
		Tile testTile = startTile;//tile that is tested to know if its valid
		testTile.availableDirections = getPossibleDirection(testTile, pathToEnd);
		int index = -1;
		
		while(isPathCompleted){
			boolean switchingTile = false;
			if(testTile.availableDirections.size() > 1){
				index = utils.generateRandomInt(testTile.availableDirections.size() -1);
			} else if (testTile.availableDirections.size() == 1){
				index = 0;
			} else {
				testTile = pathToEnd.get(pathToEnd.size()-1);//return to the last tile that was added to the path
				nbOfTilePlace--;//reduce the number of added tile
				switchingTile = true;//to not make check since we just change tile reselect an index
			}
			if(!switchingTile){
				switch(testTile.availableDirections.get(index)){
					case Direction.NORTH:
						nextY = currY + 1;
						nextX = currX;
						testTile.direction = Direction.NORTH;
						break;
					case Direction.EAST:
						nextX = currX + 1;
						nextY = currY;
						testTile.direction = Direction.EAST;
						break;
					case Direction.SOUTH:
						nextY = currY - 1;
						nextX = currX;
						testTile.direction = Direction.SOUTH;
						break;
					case Direction.WEST:
						nextX = currX - 1;
						nextY = currY;
						testTile.direction = Direction.WEST;
						break;
				}
				testTile.availableDirections.remove(index);
				
				if((nbOfTilePlace < minTilePlaces) && (floor[nextX][nextY].equals(endTile))) {
					pathToEnd.add(testTile);
					isPathCompleted = true;
				} else if(nbOfTilePlace >= maxTilePlaces) {
					testTile = pathToEnd.get(pathToEnd.size()-1);
					nbOfTilePlace--;
				} else {//we add the tile to the path since we havent reach the end and still have tile to place
					pathToEnd.add(testTile);
					nbOfTilePlace++;
					testTile = new Tile(nextX, nextY, z, false, false, false, true);
					testTile.availableDirections = getPossibleDirection(testTile, pathToEnd);
				}
			}
			
		}//at the end of the loop we will have a path from the start tile to the end tile
		for(int i = 0; i < pathToEnd.size(); i++) {
			int x = pathToEnd.get(i).position[X];
			int y = pathToEnd.get(i).position[Y];
			floor[x][y] = pathToEnd.get(i);
		}
	}
	
	public void createMaze(Tile [][] floor){
		generateStartTile();
		generateEndTile();
		generatePathtoEnd(floor);
	}
	
	/**
	 * Will find available direction a tile could be place from a selected tile.
	 * First we check if the tile is on the edge and we add possible direction
	 * Second we check if a tile exist in all available direction 
	 * @param tile tile base on which we will find available space around
	 * @return return a list of integer representing which direction a tile can be added
	 */
	ArrayList<Integer> getPossibleDirection(Tile tile, ArrayList<Tile> tilePlaced) {
		
		int nbOfDirection = 0;
		boolean westAlreadyRemove = true;//this is used in two ways, first if we had a value we set this to false meaning it might get removed, second to avoid looking in a direction where a tile already exist
		boolean eastAlreadyRemove = true;
		boolean southAlreadyRemove = true;
		boolean northAlreadyRemove = true;
		
		ArrayList<Integer> possibleDirection = new ArrayList<Integer>();
		//first check if were on the edge and we add every direction possible
		if(tile.position[X] > 0) {
			possibleDirection.add(Direction.WEST);
			nbOfDirection++;
			westAlreadyRemove = false;
		} if(tile.position[X] < sideX-1) {
			possibleDirection.add(Direction.EAST);
			nbOfDirection++;
			eastAlreadyRemove = false;
		} if(tile.position[Y] > 0) {
			possibleDirection.add(Direction.SOUTH);
			nbOfDirection++;
			southAlreadyRemove = false;
		} if(tile.position[Y] < sideY-1) {
			possibleDirection.add(Direction.NORTH);
			nbOfDirection++;
			northAlreadyRemove = false;
		}
		
		//second we check if any tile that we have already place are found around the coordinates
		int count = 0;//if we reach 4 this mean no tile can be place and we dont have to check for other tiles either
		
		for(int i = 0; i < tilePlaced.size();i++){
			if(count < nbOfDirection){
				Tile testingTile = tilePlaced.get(i);
				//check if the west direction is available but also if we have already remove that direction since only one direction exist 
				if(!westAlreadyRemove && ((testingTile.position[X]+1) == tile.position[X])) {
					//remove the direction since a tile already exit
					removeDirection(possibleDirection, Direction.WEST);
					westAlreadyRemove = true;
					count++;
				} 
				//check if the east direction is available
				else if(!eastAlreadyRemove && ((testingTile.position[X]-1) == tile.position[X])) {
					removeDirection(possibleDirection, Direction.EAST);
					eastAlreadyRemove = true;
					count++;
				}
				//check if south direction really is available
				if(!southAlreadyRemove && ((testingTile.position[Y]+1) == tile.position[Y])){
					removeDirection(possibleDirection, Direction.SOUTH);
					southAlreadyRemove = true;
					count++;
				} 
				//check if the north direction is available
				else if(!northAlreadyRemove && ((testingTile.position[Y]-1) == tile.position[Y])) {
					removeDirection(possibleDirection, Direction.NORTH);
					northAlreadyRemove = true;
					count++;
				}
			}
			
			
		}
		//return the available direction in which we can place a tile
		return possibleDirection;
	}
	/**
	 * Removes the direction if it present in the list of directions
	 * @param directions list containing all possible direction the next tile could be place
	 * @param direction the direction to be removed from the list
	 * @return the list of directions
	 */
	private List<Integer> removeDirection(List<Integer> directions, int direction){
		for(int i = 0; i < directions.size(); i++){
			if(directions.get(i) == direction) {
				directions.remove(i);
				break;
			}
		}
		return directions;
	}
	
	//getters and setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getMinSide() {
		return minSide;
	}

	public void setMinSide(int minSide) {
		this.minSide = minSide;
	}

	public int getMaxSide() {
		return maxSide;
	}

	public void setMaxSide(int maxSide) {
		this.maxSide = maxSide;
	}

	public int getSideX() {
		return sideX;
	}

	public void setSideX(int sideX) {
		this.sideX = sideX;
	}

	public int getSideY() {
		return sideY;
	}

	public void setSideY(int sideY) {
		this.sideY = sideY;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public Tile getEndTile() {
		return endTile;
	}

	public void setEndTile(Tile endTile) {
		this.endTile = endTile;
	}	
}
