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
				algo = new BFS();
				break;
			case "dfs":
				algo = new DFS();
				break;
			case "ucs":
				algo = new UCS();
				break;
			case "astar":
				algo = new AStar();
				break;
			default:
				algo = new BFS();
				break;
			}
		}
		else {
			algo = new BFS();//default algo is BFS
		}
		
		try{
			// TODO: put in your agent here
			Agent agent = new RealAgent(algo);

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
