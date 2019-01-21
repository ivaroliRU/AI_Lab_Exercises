//coordination
//holds values for x and y
public class Coord {
	//x and y positon
	int x,y;
	//direction, N A S W
	char dir;
	
	Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int GetX() {
		return x;
	}
	
	public int GetY() {
		return y;
	}
	
	public char GetDirection() {
		return dir;
	}
	
	public Coord GetCoordFwd() {
		switch(dir) {
		case 'N':
			return 
		}
		
		return null;
	}
}
