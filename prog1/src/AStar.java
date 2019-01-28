import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class AStar  implements Algorithm {
	public Hashtable<String, State> visited;
	public Hashtable<String, State> inFrontier;
    public PriorityQueue<Entry> frontier;
    public int count;
    
    public AStar(State init) {
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
					//Take note that the only diffirence between UCS and A* is this
					//we take in account the heuristical value AND the cost into the priority queue
					frontier.add(new Entry(childCost + calculateHeuristic(es),es));
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
	
	//an admissable heuristic is the distance from a goal position in the board
	//Resulting in an underestimated evaluation making it admissable
	int calculateHeuristic(State s) {
		Coord pos = s.agentPosition;
		
		if(s.numOfDirt == 0) {
			int dis1 = pos.GetX()*pos.GetX() + pos.GetY()*pos.GetY();
			int dis2 = s.agentInitPos.GetX()*s.agentInitPos.GetX() + s.agentInitPos.GetY()*s.agentInitPos.GetY();
			
			return Math.abs(dis1 - dis2);
		}
		else {
			int cheapest = Integer.MAX_VALUE;
			int dis1 = pos.GetX()*pos.GetX() + pos.GetY()*pos.GetY();
			
			for(int x = 0; x < s.width; x++) {
				for(int y = 0; y < s.height; y++) {
					if(s.state[x][y] == 1) {
						int dist2 = x*x + y*y;
						
						if(dist2 < cheapest)
							cheapest = dist2;
					}
				}
			}
			
			return Math.abs(dis1 - cheapest);
		}
	}

}
