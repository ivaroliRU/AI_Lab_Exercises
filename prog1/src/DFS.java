import java.util.Stack;
import java.util.ArrayList;
import java.util.Hashtable;

public class DFS implements Algorithm{
	//using hastable to minmize lookup cost
	public Hashtable<String, State> visited;
	public Stack<State> frontier;
	
	private int count;
	
	//Constructor
	public DFS(State init) {
		visited = new Hashtable<String, State>();
		frontier = new Stack<State>();
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
			State s = frontier.pop();
			boolean[] success = State.isSuccessorGoalState(s);
			
			if(success[0] && !success[1]) {
				//empty the frontier and add this state back in
				//empty the old states list
				frontier.clear();
				visited.clear();
			}
			else if(success[1]) {
				System.out.println("Number of looked at states: " + count);
				System.out.println("Number of dirt left in the environment: " + s.numOfDirt);
				return s;
			}
			
			visited.put(s.toString(), s);
			frontier.remove(s);
			count++;
			
			ArrayList<State> expandedStates = State.ComputeAllSuccessors(s);
			
			for(State es: expandedStates) {
				if(es != null && !visited.containsKey(es.toString())) {
					frontier.push(es);
				}
			}
		}
		
		// Should never run
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
		
		System.out.println("\nSize of path:" + path.length);
	}
}