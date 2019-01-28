import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class UCS implements Algorithm{
	public Hashtable<String, State> visited;
	public Hashtable<String, State> inFrontier;
    public PriorityQueue<Entry> frontier;
    public int count;
    
    public UCS(State init) {
    	frontier = new PriorityQueue<Entry>();
    	visited = new Hashtable<String, State>();
		count = 0;
		frontier.add(new Entry(0,init));
    }

    @Override
	public String[] search() {
		// TODO Auto-generated method stub
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
    
	public State ucs() {
		while(frontier.size() > 0) {
    		Entry pair = frontier.poll();
    		
    		State currentState = pair.getValue();
    		int childCost = pair.key + 1;
    		
    		boolean[] success = State.isSuccessorGoalState(currentState);
			
			if(success[0] && !success[1]) {
				//empty the frontier to find another goal state
				//empty the old states list
				frontier.clear();
				visited.clear();
			}
			else if(success[1]) {
				System.out.println("Number of looked at states: " + count);
				System.out.println("Number of dirt left in the environment: " + currentState.numOfDirt);
				return currentState;
			}
			
			visited.put(currentState.toString(), currentState);
			count++;
			
			ArrayList<State> expandedStates = State.ComputeAllSuccessors(currentState);
			
			for(State es: expandedStates) {
				if(es != null && !visited.containsKey(es.toString())) {
					frontier.add(new Entry(childCost,es));
				}
			}
		}
		
		// Should never run
		return null;
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
  		int w = 7, h = 7;
  		Coord dirt[] = {new Coord(0,4), new Coord(2,2), new Coord(4,2)};
  		Coord obstacles[] = {new Coord(0,3)};
  		
  		State init = new State(w, h, dirt, obstacles, new Coord(1,2, 'N'));
  		
  		Algorithm myDFS = new UCS(init);
  		String[] path = myDFS.search();
  		
  		System.out.print("Path: ");
  		
  		for(String s: path) {
  			System.out.print(s + " ");
  		}
  		
  		System.out.println("\nSize of path:" + path.length);
  	}
}