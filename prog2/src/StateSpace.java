import java.util.ArrayList;
import java.util.List;

//A tree structure representing the state space
public class StateSpace {
	public boolean isGoal(Node node) {
		int distances[] = getEdgeDistance(node.value);
		
		if(distances[0] >= node.value.board[0].length - 1) {
			return true;
		}
		return false;
	}
	
	public Node[] computeMoves(Node node) {
		//possible moves
		List<Node> moves = new ArrayList<Node>();
		//current player?
		char player = (node.value.turn)?(char)1:(char)2;
		
		for(int x = 0; x < node.value.board.length; x++) {
			for(int y = 0; y < node.value.board[0].length; y++) {
				if(node.value.board[x][y] == player) {
					moves.addAll(computeSinglePawnMoves(x, y, player, node));
				}
			}
		}
		return moves.toArray(new Node[0]);
	}
	
	public int[] computeNumPawns(Node node) {
		int num[] = new int[2];
		
		for(int x = 0; x < node.value.board.length; x++) {
			for(int y = 0; y < node.value.board[0].length; y++) {
				if(node.value.board[x][y] == (char)1) {
					num[0]++;
				}
				else if(node.value.board[x][y] == (char)2){
					num[1]++;
				}
			}
		}
		return num;
	}
	
	/*
	 * Distance to edge = E
	 * Number of possible moves = M
	 * 
	 * Version 1: E_max - E_min
	 * Version 2: E_max*M_max - E_min*M_min
	 * Version 3: M_max^E_max - M_min^E_min
	*/
	public int evaluation(Node node) {
		int distances[] = getEdgeDistance(node.value);
		int numPlayers[] = computeNumPawns(node);
		
		return distances[0]-distances[1]*2;
	}
	
	//compute the last move
	public Node computeLastMove(Node n, int lmove[], char player, boolean turn) {
		char newboard[][] = cloneArray(n.value.board);
		
		newboard[lmove[0]-1][lmove[1]-1] = 0;
		newboard[lmove[2]-1][lmove[3]-1] = player;
		return new Node(null, new State(newboard, turn), "");
	}
	
	//returns the farthest pawns distance to an edge
	//Max's distance = int[0], Min's = int[1]
	private int[] getEdgeDistance(State state) {
		int farthestMax = 0, farthestMin = 0;
		
		for(int x = 0; x < state.board.length; x++) {
			for(int y = 0; y < state.board[0].length; y++) {
				if(state.board[x][y] == 1 && y > farthestMax){
					farthestMax = y;
				}
				
				if(state.board[x][y] == 2 && (state.board[0].length-1) - y > farthestMin){
					farthestMin = (state.board[0].length-1) - y;
				}
			}
		}
		
		return new int[] {farthestMax, farthestMin};
	}
	
	//computes the moves for a single pawn
	private List<Node> computeSinglePawnMoves(int x, int y, char player, Node node){
		List<Node> moves = new ArrayList<Node>();
		
		if(player == 1 && y + 1 < node.value.board[0].length) {
			if(node.value.board[x][y+1] == 0) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x][y+1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + (x+1) + " " + ((y+1)+1) + ")"));
			}
			
			if(x < node.value.board.length - 1 && node.value.board[x+1][y+1] == 2) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x+1][y+1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + ((x+1)+1) + " " + ((y+1)+1) + ")"));
			}
			
			if(x > 0 && node.value.board[x-1][y+1] == 2) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x-1][y+1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + ((x-1)+1) + " " + ((y+1)+1) + ")"));
				
			}
		}
		else if(player == 2 && y - 1 >= 0) {
			if(node.value.board[x][y-1] == 0) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x][y-1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + ((x)+1) + " " + ((y-1)+1) + ")"));
			}
			
			if(x < node.value.board.length - 1 && node.value.board[x+1][y-1] == 1) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x+1][y-1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + ((x+1)+1) + " " + ((y-1)+1) + ")"));
			}
			
			if(x > 0 && node.value.board[x-1][y-1] == 1) {
				char newboard[][] = cloneArray(node.value.board);
				newboard[x-1][y-1] = player;
				newboard[x][y] = 0;
				moves.add(new Node(node, new State(newboard, !node.value.turn),
						"(move " + (x+1) + " " + (y+1) + " " + ((x-1)+1) + " " + ((y-1)+1) + ")"));
				
			}
		}
		
		return moves;
	}
	
	public static char[][] cloneArray(char[][] src) {
		char[][] target = new char[src.length][src[0].length];
	    for (int i = 0; i < src.length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    return target;
	}
	
	//Tester
	public static void main(String[] args) {
		State s = new State(5, 5, true);
		Node init = new Node(null, s, "");
		StateSpace space = new StateSpace();
		
		System.out.println("Initial state:");
		System.out.print(s.toString());
		
		System.out.println("\n\nChilde states:");
		Node children[] = space.computeMoves(init);
		
		for(int i = 0; i < children.length; i++) {
			System.out.println("Child " + (i+1) + ":");
			System.out.print(children[i].value.toString());
			System.out.println("");
		}
	}
}
