
public class Minimax {	
	static int minimax(Node position, int depth, int alpha, int beta, boolean max, StateSpace space) {
		if(depth == 0 || space.isGoal(position))
			return space.evaluation(position);
		
		if(max) {
			int maxEval = Integer.MIN_VALUE;
			Node children[] = space.computeMoves(position);
			
			for(Node n: children) {
				int eval = minimax(n, depth-1, alpha, beta, !max, space);
				maxEval = Integer.max(eval, maxEval);
				alpha = Integer.max(eval, alpha);
				
				if(beta <= alpha)
					break;
			}
			
			return maxEval;
		}
		else {
			int minEval = Integer.MAX_VALUE;
			Node children[] = space.computeMoves(position);
			
			for(Node n: children) {
				int eval = minimax(n, depth-1, alpha, beta, !max, space);
				minEval = Integer.min(eval, minEval);
				beta = Integer.min(eval, beta);
				
				if(beta <= alpha)
					break;
			}
			
			return minEval;
		}
	}
}
