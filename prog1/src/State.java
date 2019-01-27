import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class State {
	//width and height of the rectangle
	public int width, height;
	
	//Inside the rectangle: 
	//0 = nothing
	//1 = dirt
	//2 = obstacle
	public int state[][];
	
	//position of the agent,[0] = x and  [1] = y
	public Coord agentPosition;
	public Coord agentInitPos;
	
	//possible moves of the agent
	public String possibleMoves[];
		
	//the parent and transition state
	//will be both null if this is the init state
	public State parent;
	public String transition;
	
	public int numOfDirt;
	
	State(int w, int h, Coord dirt[], Coord obstacles[], Coord agent){
		this.width = w;
		this.height = h;
		
		this.state = new int[w][h];
		
		this.agentPosition = agent;
		this.agentInitPos = agent;
		
		for(Coord c : dirt) {
			state[c.GetX()][c.GetY()] = 1;
			numOfDirt++;
		}
		
		for(Coord c : obstacles) {
			state[c.GetX()][c.GetY()] = 2;
		}
		
		this.possibleMoves = computeMoves();
		this.parent = null;
		this.transition = null;
	}
	
	public State(State parent, int state[][], Coord agent, String transition){
		this.state = state;
		this.width = parent.width;
		this.height = parent.height;
		this.agentPosition = agent;
		this.possibleMoves = computeMoves();
		this.agentInitPos = parent.agentInitPos;
		this.parent = parent;
		this.numOfDirt = parent.numOfDirt;
		this.transition = transition;
	}
	
	//method to compute the legal moves
	private String[] computeMoves() {
		if(parent == null) {
			String ret[] = {"TURN_ON"};
			return ret;
		}
		
		if(isDirty()) {
			String ret[] = {"SUCK"};
			return ret;
		}
		
		String moves[] = {"TURN_OFF", "TURN_RIGHT", "TURN_LEFT"};
		List<String> ret = new ArrayList<String>(Arrays.asList(moves));
		
		Coord goCoord = Coord.GetCoordFwd(agentPosition);
				
		//try
		if((goCoord.GetX() >= 0 && goCoord.GetX() < width
			&& goCoord.GetY() >= 0 && goCoord.GetY() < height)
			&& state[goCoord.GetX()][goCoord.GetY()] != 2) {
			ret.add("GO");
		}
		//catch
		//print("ILLEGAL MOVE!");
		
		String moveArray[] = new String[ret.size()];
		moveArray = ret.toArray(moveArray);

		return moveArray;
	}
	
	//return if the current agent position has dirt or not
	public boolean isDirty() {
		return (state[agentPosition.GetX()][agentPosition.GetY()] == 1)? true: false;
	}
	
	//method to compute the successor state
	public static State ComputeSuccessor(State parent, String move) {
		switch(move) {
		case "TURN_ON":
			return new State(parent, parent.state, parent.agentPosition, "TURN_ON");
		case "SUCK":
			int newState[][] = parent.state;
			
			newState[parent.agentPosition.GetX()][parent.agentPosition.GetY()] = 0;
			State returnState = new State(parent, newState, parent.agentPosition, "SUCK");
			returnState.numOfDirt = parent.numOfDirt - 1;
			
			return returnState;
		case "GO":
			Coord goAgentPosition = Coord.GetCoordFwd(parent.agentPosition);
			return new State(parent, parent.state, goAgentPosition, "GO");
		case "TURN_LEFT":
			Coord leftAgentPosition = Coord.TurnLeft(parent.agentPosition);
			return new State(parent, parent.state, leftAgentPosition, "TURN_LEFT");
		case "TURN_RIGHT":
			Coord rightAgentPosition = Coord.TurnRight(parent.agentPosition);
			return new State(parent, parent.state, rightAgentPosition, "TURN_RIGHT");
		}
		
		//Should never run
		return null;
	}
	
	public static ArrayList<State> ComputeAllSuccessors(State parent){
		ArrayList<State> states = new ArrayList<State>();
		String[] moves = parent.computeMoves();
		
		for(int i = 0; i < moves.length; i++) {
			states.add(State.ComputeSuccessor(parent, moves[i]));
		}
		
		return states;
	}
	
	public static boolean[] isSuccessorGoalState(State child) {
		boolean ret[] = {false, false};
		
		//Only way to change?? the state is to remove dirt so this is a goal
		if(child.parent != null && child.numOfDirt != child.parent.numOfDirt) {
			ret[0] = true;
			return ret;
		}
		else {
			//Hægt að gera contains eða optimize-a??
			//Checks if there is any dirt left in the grid
			for(int x = 0; x < child.width; x++) {
				for(int y = 0; y < child.height; y++) {
					if(child.state[x][y] == 1) {
						return ret;
					}
				}
			}
			//Since there is no dirt left in the grid
			//agent only needs to return to his initial position
			if(child.agentPosition.GetX() == child.agentInitPos.GetX() &&
			   child.agentPosition.GetY() == child.agentInitPos.GetY() && 
			   child.agentPosition.GetDirection() == child.agentInitPos.GetDirection() && 
			   child.numOfDirt == 0) {
				ret[0] = true;
				ret[1] = true;
				return ret;
			}
		}
		// What is the state when this runs??
		return ret;
	}
	
	//Tester
	public static void main(String[] args){
		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,2), new Coord(2,1), new Coord(1,2)};
		Coord obstacles[] = {new Coord(0,3)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,1, 'N'));
		
		for(int i = 0; i < init.possibleMoves.length; i++) {
			System.out.print(init.possibleMoves[i]);
		}
		
		System.out.print("\n");
		
		State successor = State.ComputeSuccessor(init, init.possibleMoves[0]);
		
		for(int i = 0; i < successor.possibleMoves.length; i++) {
			System.out.print(successor.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor)[0]) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
		
		State successor1 = State.ComputeSuccessor(successor, successor.possibleMoves[3]);
		
		for(int i = 0; i < successor1.possibleMoves.length; i++) {
			System.out.print(successor1.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor1)[0]) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
		
		State successor2 = State.ComputeSuccessor(successor1, successor1.possibleMoves[3]);
		
		for(int i = 0; i < successor2.possibleMoves.length; i++) {
			System.out.print(successor2.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor2)[0]) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
	}
}
