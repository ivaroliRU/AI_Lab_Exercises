import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RealAgent implements Agent
{
	Algorithm algo;
	String path[];
	int index = -1;
	
	public RealAgent(Algorithm algo) {
		this.algo = algo;
	}
	

    public void init(Collection<String> percepts) {
		Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
		Coord agentCoord = new Coord(0,0,'N');
		
		int w = 0,h = 0;
		ArrayList<Coord> dirt = new ArrayList<Coord>();
		ArrayList<Coord> obstacles = new ArrayList<Coord>();
		
		//taking in percept and constructing environment
		for (String percept:percepts) {			
			Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
			
			if (perceptNameMatcher.matches()) {
				String perceptName = perceptNameMatcher.group(1);
				
				if (perceptName.equals("HOME")) {
					Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					
					if (m.matches()) {
						agentCoord.x = Integer.parseInt(m.group(1)) - 1;
						agentCoord.y = Integer.parseInt(m.group(2)) - 1;
					}
					
				} else if(perceptName.equals("AT")){
					Matcher m1 = Pattern.compile("\\(\\s*AT\\s+DIRT\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					
					if (m1.matches()) {
						dirt.add(new Coord(Integer.parseInt(m1.group(1)) - 1,Integer.parseInt(m1.group(2)) - 1));
					}
					
					Matcher m2 = Pattern.compile("\\(\\s*AT\\s+OBSTACLE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					
					if (m2.matches()) {
						obstacles.add(new Coord(Integer.parseInt(m2.group(1)) - 1 ,Integer.parseInt(m2.group(2)) - 1));
					}
				} else if(perceptName.equals("ORIENTATION")) {
					Matcher m = Pattern.compile("\\(\\s*ORIENTATION\\s+([A-Z]+)\\s*\\)").matcher(percept);
					
					if (m.matches()) {
						agentCoord.dir = m.group(1).charAt(0);
					}
				} else if(perceptName.equals("SIZE")) {
					Matcher m = Pattern.compile("\\(\\s*SIZE\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
					if (m.matches()) {
						w = Integer.parseInt(m.group(1));
						h = Integer.parseInt(m.group(2));
					}
				}
			} else {
				System.err.println("strange percept that does not match pattern: " + percept);
			}
		}
		
		Coord dirtArray[] = new Coord[dirt.size()];
		dirtArray = dirt.toArray(dirtArray);
		
		Coord obsArray[] = new Coord[obstacles.size()];
		obsArray = obstacles.toArray(obsArray);
		
		State init = new State(w, h, dirtArray, obsArray, agentCoord);
		
		Instant starts = Instant.now();
		path = algo.search(init);
		Instant ends = Instant.now();
		System.out.println("Time: " + Duration.between(starts, ends));
		System.out.println("Max frontier: " + algo.getSpaceUsage());
		System.out.println("Number of states looked at: " + algo.getCount());
		
		System.out.print("Path: ");
  		
  		for(String s: path) {
  			System.out.print(s + " ");
  		}
  		
  		System.out.println("\nSize of path: " + path.length);
    }

    public String nextAction(Collection<String> percepts) {
		index += 1;
		return path[index];
	}
}
