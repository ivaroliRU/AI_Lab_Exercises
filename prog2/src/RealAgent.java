
public class RealAgent implements Agent {
	private static final int MAX_DEPTH = 10;
	
	private String role; // the name of this agent's role (white or black)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	private StateSpace stateSpace;
	private Node lMove;
	
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white");
		this.width = width;
		this.height = height;
		this.stateSpace = new StateSpace();
		this.lMove = new Node(null, new State(width,height, myTurn), "");
    }

    public String nextAction(int[] lastMove) {
    	myTurn = !myTurn;
		if (myTurn) {
			//get the last move from the opponent
			if(lastMove != null)
				lMove = stateSpace.computeLastMove(lMove, lastMove, (char)2, !myTurn);
			
			//expand one level before executing the real minimax
			//I do this because I want to know which branch to go to and
			//this is much cleaner than making minimax return a pair
			Node children[] = stateSpace.computeMoves(lMove);
			Node bestBranch = null;
			int maxEval = Integer.MIN_VALUE;
			
			if(children.length == 0) {
				System.out.println("NO CHILDREN!!!");
			}
			
			for(Node child: children) {
				int eval = Minimax.minimax(child, MAX_DEPTH-1, Integer.MIN_VALUE, Integer.MAX_VALUE, myTurn, stateSpace);
				
				if(eval > maxEval || bestBranch == null) {
					maxEval = eval;
					bestBranch = child;
				}
			}
			
			lMove = bestBranch;
			return bestBranch.move;
		} else {
			return "noop";
		}
	}

	public void cleanup() {
		//do some clean up
	}
}
