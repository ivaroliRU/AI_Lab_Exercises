import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

    public ArrayList<State> oldStates;
    public Queue<State> frontier;
    public int count;
    
    public BFS(State init) {
    	frontier = new LinkedList<>();
    	oldStates = new ArrayList<State>();
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
				oldStates.clear();
			}
			else if(success[1]) {
				System.out.println("Number of looked at states: " + count);
				System.out.println("Number of dirt left in the environment: " + s.numOfDirt);
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
  	}
}