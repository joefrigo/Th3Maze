package maze;

public class Tower {
	
	static int lastId = -1;
	int towerId;//in the case of multiple towers
	Floor [] floors;//how many floors does the tower have
	int floorOn;//what floor are we currently on
	
	public Tower(int nbOfFloor){
		floors = new Floor [nbOfFloor];
		towerId = ++lastId;
	}
}
