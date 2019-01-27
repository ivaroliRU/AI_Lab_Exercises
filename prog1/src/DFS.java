import java.util.ArrayList;

public class DFS implements Algorithm{
	public ArrayList<State> oldStates;
	public ArrayList<State> frontier;
	
	private int count;
	
	//Constructor
	public DFS(State init) {
		oldStates = new ArrayList<State>();
		frontier = new ArrayList<State>();
		count = 0;
		frontier.add(init);
	}
    
	@Override
	public String[] search() {
		// TODO Auto-generated method stub
		State next = dfs();
		ArrayList<String> path = new ArrayList<String>();
		
		path.add("TURN_OFF");
		
		while(next.transition != null) {
			path.add(next.transition);
			next = next.parent;
		}
		
		String pathArray[] = new String[path.size()];
		
		for(int i = path.size()-1; i >= 0; i--) {
			int j = (path.size() - 1) - i;
			pathArray[j] = path.get(i);
		}
		
		return pathArray;
	}
	
	public State dfs(){	
		while(frontier.size() > 0) {
			State s = getNextState(frontier);
			boolean[] success = State.isSuccessorGoalState(s);
			
			if(success[0] && !success[1]) {
				//empty the frontier and add this state back in
				//empty the old states list
				frontier.clear();
				oldStates.clear();
			}
			else if(success[1]) {
				return s;
			}
			
			oldStates.add(s);
			frontier.remove(s);
			count++;
			
			ArrayList<State> expandedStates = State.ComputeAllSuccessors(s);
			
			for(int i = 0; i < expandedStates.size(); i++) {
				if(!oldStates.contains(expandedStates.get(i)) && expandedStates.get(i) != null) {
					frontier.add(expandedStates.get(i));
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
		Coord dirt[] = {new Coord(0,4), new Coord(2,2), new Coord(4,2)};
		Coord obstacles[] = {new Coord(0,3)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,1, 'N'));
		
		DFS myDFS = new DFS(init);
		String[] path = myDFS.search();
		
		System.out.print("Path: ");
		
		for(String s: path) {
			System.out.print(s + " ");
		}
	}
}