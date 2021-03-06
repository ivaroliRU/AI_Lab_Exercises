import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class UCS  implements Algorithm {
	public Hashtable<String, State> visited;
	public Hashtable<String, State> inFrontier;
    public PriorityQueue<Entry> frontier;

    private int count;
    private int maxFrontier = 0;
    
    public UCS() {
    	frontier = new PriorityQueue<Entry>();
    	visited = new Hashtable<String, State>();
    }

    @Override
	public String[] search(State init) {
		// TODO Auto-generated method stub
    	frontier.add(new Entry(0,init));
		State next = ucs();
		
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
    
	public State ucs() {
		State s = null;
    	
    	//while the frontier/agenda is not empty.....
    	while(frontier.size() > 0) {
    		if(frontier.size() > maxFrontier)
    			maxFrontier = frontier.size();
    		//look at the next state in the frontier
    		Entry e = frontier.poll();
    		s = e.value;
    		int cost = e.key;
    		count++;
    		
    		//add the successor states to the frontier/agenda
    		ArrayList<State> successors = State.ComputeAllSuccessors(s);
    		
    		for(State successor: successors) {
    			if(!visited.containsKey(successor.toString())) {
    				Entry succ = new Entry(cost+1,successor);// << this is making it a UCS
    				
    				frontier.add(succ);
    			}
    		}
    		
    		//if s is goal then stop and do a dance
    		boolean[] success = State.isSuccessorGoalState(s);
    		if(success[0] && !success[1]) {
    			//if this is a goal state (and not the final goal state) then
    			//remove everything from visited and go through the algorithm again
    			visited.clear();
    			
    		}
    		else if (success[1]){
    			return s;
    		}
    		
    		//mark s as visited
    		visited.put(s.toString(), s);
    	}
    	
    	return s;
	}
	
	//A class that holds a cost and state pair to be able to enter into a priority queue
	public class Entry implements Comparable<Entry> {
	    private int key;
	    private State value;

	    public Entry(int key, State value) {
	        this.key = key;
	        this.value = value;
	    }

	    public int getKey() {
	    	return key;
	    }
	    
	    public State getValue() {
	    	return value;
	    }

	    @Override
	    public int compareTo(Entry other) {
	        return this.getKey() - other.getKey();
	    }
	}

	//Tester
  	public static void main(String[] args){
  		int w = 5, h = 5;
		Coord dirt[] = {new Coord(0,2), new Coord(1,3), new Coord(3,0), new Coord(2,1), new Coord(4,4)};
		Coord obstacles[] = {new Coord(0,1), new Coord(2,2), new Coord(2,3), new Coord(2,4), new Coord(4,2)};
		
		State init = new State(w, h, dirt, obstacles, new Coord(0,0, 'N'));
  		
  		Algorithm myDFS = new UCS();
  		String[] path = myDFS.search(init);
  		
  		System.out.print("Path: ");
  		
  		for(String s: path) {
  			System.out.print(s + " ");
  		}
  		
  		System.out.println("\nSize of path:" + path.length);
  	}
}
