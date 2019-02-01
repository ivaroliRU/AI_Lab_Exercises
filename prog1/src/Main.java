import java.io.IOException;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){
		Algorithm algo = null;
		
		if(args.length > 0) {
			switch(args[0]) {
			case "bfs":
				System.out.println("Running BFS");
				algo = new BFS();
				break;
			case "dfs":
				System.out.println("Running DFS");
				algo = new DFS();
				break;
			case "ucs":
				System.out.println("Running UCS");
				algo = new UCS();
				break;
			case "astar":
				System.out.println("Running A*");
				algo = new AStar();
				break;
			default:
				System.out.println("Running BFS");
				algo = new BFS();
				break;
			}
		}
		else {
			System.out.println("Running BFS");
			algo = new BFS();//default algo is BFS
		}
		
		try{
			// TODO: put in your agent here
			Agent agent = new RealAgent(algo);

			int port=4001;
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
