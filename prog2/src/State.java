//Class representing the state in the state space
public class State {
	//2D byte array to represent the board
	//0 = empty, 1 = max pawn and 2 is min pawn
	//Max's init pawn pos is (0,0) -> (W-1,1)
	//Min's init pawn pos is (0,H-1) -> (W-1,H-2)
	char board[][];
	//if its max's turn
	boolean turn;
	//parent State
	State parent;
	
	//initial state constructor
	public State(int W, int H, boolean t) {
		this.board = new char[W][H];
		this.turn = t;
		this.parent = null;
		
		//inserting Max's pawns into the board
		for(int x = 0; x < W; x++) {
			for(int y = 0; y < 2; y++) {
				board[x][y] = 1;
			}
		}
		
		//inserting Min's pawns into the board
		for(int x = 0; x < W; x++) {
			for(int y = H-1; y > H-3; y--) {
				board[x][y] = 2;
			}
		}
	}
	
	//child state constructor
	public State(char state[][], boolean t) {
		this.board = state;
		this.turn = t;
	}
	
	public String toString() {
		String ret = "";
		
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board[0].length; y++) {
				ret += ((int)board[x][y]) + " ";
			}
			ret += "\n";
		}
		return ret;
	}
	
	//Tester
	public static void main(String[] args) {
		State s = new State(5, 5, true);
		
		System.out.print(s.toString());
	}
}
