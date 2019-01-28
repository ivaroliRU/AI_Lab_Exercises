import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

	public Hashtable<String, State> visited;
    public Queue<State> frontier;
    public int count;
    
    public BFS(State init) {
    	frontier = new LinkedList<>();
    	visited = new Hashtable<String, State>();
		count = 0;
		frontier.add(init);
    }

    @Override
	public String[] search() {
		// TODO Auto-generated method stub
		State next = bfs();
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
    
    public State bfs() {
    	while(frontier.size() > 0) {
    		State s = frontier.remove();
    		boolean[] success = State.isSuccessorGoalState(s);
			
			if(success[0] && !success[1]) {
				//empty the frontier to find another goal state
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
					frontier.add(es);
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
  		
  		Algorithm myDFS = new BFS(init);
  		String[] path = myDFS.search();
  		
  		System.out.print("Path: ");
  		
  		for(String s: path) {
  			System.out.print(s + " ");
  		}
  		
  		System.out.println("\nSize of path:" + path.length);
  	}
}