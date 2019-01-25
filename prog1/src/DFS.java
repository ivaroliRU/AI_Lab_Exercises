import java.util.ArrayList;

public class DFS{
	public ArrayList<State> oldStates = new ArrayList<State>();
	public ArrayList<State> frontier = new ArrayList<State>();
	
	int count;
	
	public DFS(State init) {
		count = 0;
		frontier.add(init);
	}
	
	public ArrayList<String> search(State state) {
		return null;
	}
    
	public State DFS(){	
		while(frontier.size() > 0) {
			State s = getNext(frontier);
			
			System.out.println("Count: " + count);
			
			if(s == null) {
			}
			
			if(State.isSuccessorGoalState(s)) {
				return s;
			}
			
			oldStates.add(s);
			frontier.remove(0);
			count++;
			
			ArrayList<State> expandedStates = State.ComputeAllSuccessors(s);
			
			for(int i = 0; i < expandedStates.size(); i++) {
				if(!oldStates.contains(expandedStates.get(i))) {
					frontier.add(expandedStates.get(i));
				}
			}
		}
		
		return null;
	}
	
	private State getNext(ArrayList<State> f) {
		for(int i = 0; i < f.size(); i++) {
			if(f.get(i) != null) {
				return f.get(i);
			}
		}
		return null;
	}
	
	//Tester
	public static void main(String[] args){
		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,2), new Coord(2,1), new Coord(1,2)};
		Coord obstacles[] = {new Coord(0,3)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,1, 'N'), false);
		
		DFS dfs = new DFS(init);
		State goalState = dfs.DFS();
	}
}