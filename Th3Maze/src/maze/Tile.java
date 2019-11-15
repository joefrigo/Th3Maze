package maze;

import java.util.ArrayList;

public class Tile {

	int [] position = new int[3];
	
	boolean wasVisited;
	boolean isStartTile;
	boolean isEndTile;
	boolean isDeadendTile;
	boolean isPathTile;
	boolean isNotAvailable;
	
	ArrayList<Integer> availableDirections = null;
	int direction;
	int tileOrientation;
	int tileType;
	String imgPath;
	
	public Tile(int x, int y, int z, boolean isStartTile, boolean isEndTile, boolean isDeadendTile, boolean isPathTile){
		this.position[0] = x;
		this.position[1] = y;
		this.position[2] = z;
		
		this.isStartTile = isStartTile;
		this.isEndTile = isEndTile;
		this.isDeadendTile = isDeadendTile;
		this.isPathTile = isPathTile;
	}
	
	public boolean equals(Tile tile){
		boolean isEqual = true;
		for(int i = 0; i < this.position.length; i++) {
			if(this.position[i] != tile.position[i]) {
				isEqual = false;
				break;
			}
		}
		return isEqual;
	}
	
}


