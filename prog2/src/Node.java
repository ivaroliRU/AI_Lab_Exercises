public class Node{
	public Node parent;
	public Node[] children;
	public State value;
	public String move;
	
	public Node(Node parent, State value, String move) {
		this.parent = null;
		this.value = value;
		this.move = move;
	}
}