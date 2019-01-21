public class State {
	//width and height of the rectangle
	public int width, height;
	
	//Inside the rectangle: 
	//0 = nothing
	//1 = dirt
	//2 = obstacle
	public int state[][];
	
	//position of the agent,[0] = x and  [1] = y
	public Coord agentPosition;
	
	State(int w, int h, Coord dirt[], Coord obsticales[]){
		this.width = w;
		this.height = h;
		
		this.state = new int[w][h];
		
		for(Coord c : dirt) {
			state[c.GetX()][c.GetY()] = 1;
		}
		
		for(Coord c : obsticales) {
			state[c.GetX()][c.GetY()] = 2;
		}
	}
	
	/* TODO
	 * Implement methods to compute the legal moves in a state and the successor state
	 * resulting of executing an action.
	 */
	public String[] ComputeMoves() {
		String[] ret = {""};
		return ret;
	}
	
	//return if the current agent position has dirt or not
	public boolean isDirty() {
		return (state[agentPosition.GetX()][agentPosition.GetY()] == 1)? true: false;
	}
	
	/* TODO
	 * Implement the goal test, i.e., a method telling you whether a certain state fulfils all goal
	 * conditions.
	 */
}
