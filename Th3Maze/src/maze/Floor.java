package maze;

public class Floor {
	
	private int floorId;//usually is which level the floor is
	private int [] startPosition = new int [2];
	private int sideX;//width of the maze
	private int sideY;//height of the maze
	
	private GenerateFloor generateFloor;
	
	/*
	 * The 2d array follow a cartesian map using only the first quadrants (x positive and y positive).
	 * [0][0] = (0,0) | [8][0] = (8,0) | [4][3] = (4,3)
	 */
	Tile [][] tiles;//[x][y] 
	
	//when the floor is created so is the maze
	public Floor(int id){
		this.floorId = id;
		this.generateFloor = new GenerateFloor(id);
		
	}
	
	public void createMaze(){
		//generate the size of the maze first
		System.out.println("Generate the maze size");
		this.generateFloor.generateMazeSize();
		this.sideX = this.generateFloor.getSideX();
		this.sideY = this.generateFloor.getSideY();
		this.tiles = new Tile[sideX][sideY];
		System.out.println("The map size is " + sideX + " par " + sideY);
		
		//create a single method that regroups everything
		this.generateFloor.createMaze(tiles);
		this.showMaze();
		
	}
	
	public void showMaze() {
		for(int i = (sideX - 1); i > -1; i--){//we start we the top left corner to print since the tiles were place using cartesian plan with bottom right corner being (0,0)
			for(int j = 0; j < sideY; j++){
				if(this.tiles[i][j] != null) {
					if(this.tiles[i][j].isStartTile) {
						System.out.print("s");
					} else if(this.tiles[i][j].isEndTile) {
						System.out.print("e");
					} else {
						System.out.print(" ");
					}
					
					if(j == sideY - 1) System.out.print("\n");
				}
			}
		}
	}

	//Getters and setters
	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public int[] getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int[] startPosition) {
		this.startPosition = startPosition;
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

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}
	
	
}
