import java.util.ArrayList;

public class DFS{
	public ArrayList<State> oldStates;
	public ArrayList<State> frontiers;
	
	private int count;
	
	//Constructor
	public DFS(State init) {
		oldStates = new ArrayList<State>();
		frontiers = new ArrayList<State>();
		count = 0;
		frontiers.add(init);
	}
    
	public State dfs(){	
		while(frontiers.size() > 0) {
			State s = getNextState(frontiers);
			
			System.out.println("Count: " + count);
			
			if(s == null) {/*do something*/}
			
			if(State.isSuccessorGoalState(s)) {
				return s;
			}
			
			oldStates.add(s);
			frontiers.remove(0);
			count++;
			
			ArrayList<State> expandedStates = State.ComputeAllSuccessors(s);
			
			for(int i = 0; i < expandedStates.size(); i++) {
				if(!oldStates.contains(expandedStates.get(i)) && expandedStates.get(i) != null) {
					frontiers.add(expandedStates.get(i));
				}
			}
		}
		
		// Should never run
		return null;
	}
	
	private State getNextState(ArrayList<State> f) {
		for(int i = 0; i < f.size(); i++) {
			if(f.get(i) != null) {
				/*System.out.println(f.get(i));
				System.out.println("New state!");*/
				return f.get(i);
			}
		}
		return null;
	}
	
	//Tester
	public static void main(String[] args){
		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,1), /*new Coord(2,1), new Coord(1,2)*/};
		Coord obstacles[] = {new Coord(0,3)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,1, 'N'), false);
		
		DFS myDFS = new DFS(init);
		State goalState = myDFS.dfs();
	}
}