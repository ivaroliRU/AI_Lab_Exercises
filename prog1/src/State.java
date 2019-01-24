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
	
	public boolean isOn;
	
	State(int w, int h, Coord dirt[], Coord obsticales[], Coord agent, boolean on){
		this.width = w;
		this.height = h;
		
		this.state = new int[w][h];
		
		this.agentPosition = agent;
		this.agentInitPos = agent;
		
		for(Coord c : dirt) {
			state[c.GetX()][c.GetY()] = 1;
		}
		
		for(Coord c : obsticales) {
			state[c.GetX()][c.GetY()] = 2;
		}
		
		possibleMoves = computeMoves();
		this.isOn = on;
	}
	
	State(State parent, int state[][], Coord agent, boolean on){
		this.state = state;
		this.width = parent.width;
		this.height = parent.height;
		this.agentPosition = agent;
		this.isOn = on;
		this.possibleMoves = computeMoves();
		this.agentInitPos = parent.agentInitPos;
	}
	
	//method to compute the legal moves
	private String[] computeMoves() {
		if(!isOn) {
			String ret[] = {"TURN_ON"};
			return ret;
		}
		
		String moves[] = {"TURN_OFF", "TURN_RIGHT", "TURN_LEFT"};
		List<String> ret = new ArrayList<String>(Arrays.asList(moves));
		
		Coord goCoord = Coord.GetCoordFwd(agentPosition);
		
		if((goCoord.GetX() >= 0 || goCoord.GetX() < width
			|| goCoord.GetY() >= 0 || goCoord.GetY() < height)
			&& state[goCoord.GetX()][goCoord.GetY()] != 2) {
			ret.add("GO");
		}
		
		if(isDirty()) {
			ret.add("SUCK");
		}
		
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
			return new State(parent, parent.state, parent.agentPosition, true);
		case "SUCK":
			int newState[][] = parent.state;
			newState[parent.agentPosition.GetX()][parent.agentPosition.GetY()] = 0;
			return new State(parent, newState, parent.agentPosition, true);
		case "GO":
			Coord goAgentPosition = Coord.GetCoordFwd(parent.agentPosition);
			return new State(parent, parent.state, goAgentPosition, true);
		case "TURN_LEFT":
			Coord leftAgentPosition = Coord.TurnLeft(parent.agentPosition);
			return new State(parent, parent.state, leftAgentPosition, true);
		case "TURN_RIGHT":
			Coord rightAgentPosition = Coord.TurnRight(parent.agentPosition);
			return new State(parent, parent.state, rightAgentPosition, true);
		}
		
		return null;
	}
	
	public static boolean isSuccessorGoalState(State parent, State child) {
		//only way to change the state is to remove dirt so this is a goal
		if(!parent.state.equals(child.state)) {
			return true;
		}
		else {
			for(int x = 0; x < child.width; x++) {
				for(int y = 0; y < child.height; y++) {
					if(child.state[x][y] == 1) {
						return false;
					}
				}
			}
			if(child.agentPosition.GetX() == child.agentInitPos.GetX() &&
			   child.agentPosition.GetY() == child.agentInitPos.GetY()) {
				return true;
			}
		}
		return false;
	}
	
	//Tester
	public static void main(String[] args){
		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,2), new Coord(2,1), new Coord(1,2)};
		Coord obstacles[] = {new Coord(0,3)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,1, 'N'), false);
		
		for(int i = 0; i < init.possibleMoves.length; i++) {
			System.out.print(init.possibleMoves[i]);
		}
		
		System.out.print("\n");
		
		State successor = State.ComputeSuccessor(init, init.possibleMoves[0]);
		
		for(int i = 0; i < successor.possibleMoves.length; i++) {
			System.out.print(successor.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor, init)) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
		
		State successor1 = State.ComputeSuccessor(successor, successor.possibleMoves[3]);
		
		for(int i = 0; i < successor1.possibleMoves.length; i++) {
			System.out.print(successor1.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor1, successor)) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
		
		State successor2 = State.ComputeSuccessor(successor1, successor1.possibleMoves[3]);
		
		for(int i = 0; i < successor2.possibleMoves.length; i++) {
			System.out.print(successor2.possibleMoves[i] + " ");
		}
		
		if(State.isSuccessorGoalState(successor2, successor1)) {
			System.out.print(" Is succ\n");
		}
		else {
			System.out.print("\n");
		}
	}
}
