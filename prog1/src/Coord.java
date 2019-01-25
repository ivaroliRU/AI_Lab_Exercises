//coordination
//holds values for x and y
public class Coord {
	//x and y position
	int x,y;
	//direction, N E S W
	char dir;
	
	Coord(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	Coord(int x, int y, char dir){
		this.x = x;
		this.y = y;
		this.dir = dir;
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
	
	public static Coord GetCoordFwd(Coord c) {
		char direction = c.GetDirection();
		
		switch(direction) {
		case 'N':
			return new Coord(c.GetX(), c.GetY()+1, direction);
		case 'W':
			return new Coord(c.GetX()-1, c.GetY(), direction);
		case 'S':
			return new Coord(c.GetX(), c.GetY()-1, direction);
		case 'E':
			return new Coord(c.GetX()+1, c.GetY(), direction);
		}
		//Should never run
		return null;
	}
	
	public static Coord TurnLeft(Coord c) {
		char direction = c.GetDirection();
		
		switch(direction) {
		case 'N':
			return new Coord(c.GetX(), c.GetY(), 'W');
		case 'W':
			return new Coord(c.GetX(), c.GetY(), 'S');
		case 'S':
			return new Coord(c.GetX(), c.GetY(), 'E');
		case 'E':
			return new Coord(c.GetX(), c.GetY(), 'N');
		}
		
		//Should never run
		return null;
	}
	
	public static Coord TurnRight(Coord c) {
		char direction = c.GetDirection();
		
		switch(direction) {
		case 'N':
			return new Coord(c.GetX(), c.GetY(), 'E');
		case 'E':
			return new Coord(c.GetX(), c.GetY(), 'S');
		case 'S':
			return new Coord(c.GetX(), c.GetY(), 'W');
		case 'W':
			return new Coord(c.GetX(), c.GetY(), 'N');
		}
		
		//Should never run
		return null;
	}
}
