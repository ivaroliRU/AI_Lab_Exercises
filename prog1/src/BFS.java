import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

	public Hashtable<String, State> visited;
    public Queue<State> frontier;//take not this is a queue not a stack

    private int count;
    private int maxFrontier = 0;
    
    public BFS() {
    	frontier = new LinkedList<>();
    	visited = new Hashtable<String, State>();
		count = 0;
    }

    @Override
	public String[] search(State init) {
		// TODO Auto-generated method stub
    	frontier.add(init);
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
    
    @Override
	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}
    
    @Override
	public int getSpaceUsage() {
		// TODO Auto-generated method stub
		return maxFrontier;
	}
    
    public State bfs() {
    	State s = null;
    	
    	//while the frontier/agenda is not empty.....
    	while(frontier.size() > 0) {
    		if(frontier.size() > maxFrontier)
    			maxFrontier = frontier.size();
    		//look at the next state in the frontier
    		s = frontier.remove();
    		count++;
    		
    		//add the successor states to the frontier/agenda
    		ArrayList<State> successors = State.ComputeAllSuccessors(s);
    		
    		for(State successor: successors) {
    			if(!visited.containsKey(successor.toString())) {
    				frontier.add(successor);
    			}
    		}
    		
    		//if s is goal then stop and do a dance
    		boolean[] success = State.isSuccessorGoalState(s);
    		if(success[0] && !success[1]) {
    			System.out.println("Found a goal");
    			//if this is a goal state (and not the final goal state) then
    			//remove everything from visited and go through the algorithm again
    			visited.clear();
    			
    		}
    		else if (success[1]){
    			System.out.println("Found final");
    			return s;
    		}
    		
    		//mark s as visited
    		visited.put(s.toString(), s);
    	}
    	
    	return s;
	}

    //Tester
  	public static void main(String[] args){
  		int w = 5, h = 5;
		//Coord dirt[] = {new Coord(0,2), new Coord(1,3), new Coord(3,0), new Coord(2,1), new Coord(4,4)};
  		Coord dirt[] = {};
		Coord obstacles[] = {new Coord(0,1), new Coord(2,2), new Coord(2,3), new Coord(2,4), new Coord(4,2)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,0, 'N'));
		boolean success[] = State.isSuccessorGoalState(init);
		
		if(success[1]) {
			System.out.println("Typpi");
		}
		
  		/*Algorithm myDFS = new BFS();
  		String[] path = myDFS.search(init);
  		
  		System.out.print("Path: ");
  		
  		for(String s: path) {
  			System.out.print(s + " ");
  		}
  		
  		System.out.println("\nSize of path:" + path.length);*/
  	}
}