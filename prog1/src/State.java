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
	
	//return if the current agent position has dirt or not
	public boolean isDirty() {
		return (state[agentPosition.GetX()][agentPosition.GetY()] == 1)? true: false;
	}
	
	//generate unique id from state to minimize
	//lookup cost in algorithms
	@Override
	public String toString() {
		String id = "";
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				id += Integer.toString(state[x][y]);
			}
		}
		
		id += Integer.toString(this.agentPosition.GetX());
		id += Integer.toString(this.agentPosition.GetY());
		id += this.agentPosition.GetDirection();
		
		if(this.parent == null) {
			id += "init";
		}else {
			id += "notinit";
		}

		return id;
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
		
		String moves[] = {"TURN_RIGHT", "TURN_LEFT"};
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
			State toAdd = State.ComputeSuccessor(parent, moves[i]);
			
			if(toAdd == null)
				System.out.println("To add is null, move is: " + moves[i]);
			states.add(toAdd);
			//states.add(State.ComputeSuccessor(parent, moves[i]));
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
	
	//tester
	public static void main(String[] args) {
		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,2), new Coord(1,3), new Coord(3,0), new Coord(2,1), new Coord(4,4)};
		Coord obstacles[] = {new Coord(0,1), new Coord(2,2), new Coord(2,3), new Coord(2,4), new Coord(4,2)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,0, 'N'));
		
		State ss = new State(init, init.state, init.agentPosition, "TURN_ON");
		
		State aa = State.ComputeSuccessor(ss, "TURN_RIGHT");
		
		System.out.println("Agent orientation: " + aa.agentPosition.GetDirection());
		System.out.println("Agent position: " + aa.agentPosition.GetX() + ", " + aa.agentPosition.GetY());
		
		State ab = State.ComputeSuccessor(aa, "GO");
		
		System.out.println("Agent orientation: " + ab.agentPosition.GetDirection());
		System.out.println("Agent position: " + ab.agentPosition.GetX() + ", " + ab.agentPosition.GetY());
		
		State ac = State.ComputeSuccessor(ab, "GO");
		
		System.out.println("Agent orientation: " + ac.agentPosition.GetDirection());
		System.out.println("Agent position: " + ac.agentPosition.GetX() + ", " + ac.agentPosition.GetY());
		
		State ad = State.ComputeSuccessor(ac, "GO");
		
		System.out.println("Agent orientation: " + ad.agentPosition.GetDirection());
		System.out.println("Agent position: " + ad.agentPosition.GetX() + ", " + ad.agentPosition.GetY());
		
		//State testStates[] = {};
		
		ArrayList<State> succs = State.ComputeAllSuccessors(ad);
		
		for(State s: succs) {
			System.out.println("Created State from move: " + s.transition);
		}
	}
}
