import java.util.Collection;

public class VacuumCleaningAgent implements Agent
{
	//available actions
	String[] actions = { "TURN_ON", "TURN_OFF", "TURN_RIGHT", "TURN_LEFT", "GO", "SUCK" };
	//robot oriantation (reletive to the robot)
	char dir = 'N';
	//current stage in the process
	int stage = 0;
	//distance from right and bottom walls
	int fromRight = 0, fromBottom = 0;
	boolean movingColumns = false;
	
    public String nextAction(Collection<String> percepts) {
    	boolean isBump = false;
    	
    	for(String percept:percepts) {
			if(percept.contains("DIRT") && stage != 0) {
				return actions[5];
			}
			else if(percept.contains("BUMP")) {
				isBump = true;
			}
		}
    	
    	//return depends on the stage
    	switch(stage) {
    	case 0:
    		stage = 1;
    		return actions[0];
    	case 1:
    		return findCorner(isBump); //find the right most wall
    	case 2:
    		return findCorner(isBump); // finally find the corner
    	case 3:
    		return coverRect(isBump);
    	case 4:
    		return coverRect(isBump);
    	case 5:
    		return goHome();
    	default:
    		return "TURN_OFF";
    	}
	}
    
    //find the bottom right corner
    private String findCorner(boolean isBump) {
    	System.out.println("Finding corner!");
    	
		if(isBump) {
			stage++;
		}
    	
    	if((stage == 1 && dir == 'N') || (stage == 2 && dir == 'E')) {//if we are facing north then turn
    		//now we are facing east
    		return turnRight();
    	}
    	else if(stage == 1 && dir == 'E') {
    		return actions[4];
    	}
    	else {
    		return actions[4];
    	}
    }
    
    //cover rect
    //stage == 3 -> go north and turn left on a wall
    //stage == 4 -> go south and turn right on a wall
    private String coverRect(boolean isBump) {
    	System.out.println("Covering Rect");
    	if(isBump && !movingColumns) {
    		System.out.println("we have a bump");
    		if(stage == 3) {
    			stage++;
    		}else {
    			stage--;
    		}
    		movingColumns = true;
    	}
    	
    	if(stage == 3) {
    		if(dir != 'N' && !movingColumns) {
    			System.out.println("1.1");
    			return turnRight();
    		}
    		else if(movingColumns && dir != 'W') {
    			System.out.println("2.1");
    			return turnLeft();
    		}
    		else if (movingColumns && dir == 'W' && !isBump) {
    			System.out.println("3.1");
    			movingColumns = false;
    			return actions[4];
    		}
    		else if(movingColumns && dir == 'W' && isBump){
    			stage = 5;
    		}
    	}
    	else if(stage == 4) {
    		if(dir != 'S'  && !movingColumns) {
    			System.out.println("1.2");
    			return turnLeft();
    		}
    		else if(movingColumns && dir != 'W') {
    			System.out.println("2.2");
    			return turnRight();
    		}
    		else if (movingColumns && dir == 'W' && !isBump) {
    			System.out.println("3.2");
    			movingColumns = false;
    			return actions[4];
    		}
    		else if(movingColumns && dir == 'W' && isBump) {
    			stage = 5;
    		}
    	}    	
    	return actions[4];
    }
    
    private String goHome() {
    	System.out.println("GoHome");
    	return actions[1];
    }
    
    //turn left and change direction
    private String turnLeft() {
    	if(dir == 'N') {
    		dir = 'W';
    	}
    	else if (dir == 'W') {
    		dir = 'S';
    	}
    	else if(dir == 'S') {
    		dir = 'E';
    	}
    	else {
    		dir = 'N';
    	}
    	
    	return actions[3];
    }
    
    //turn right and change direction
    private String turnRight() {
    	if(dir == 'N') {
    		dir = 'E';
    	}
    	else if (dir == 'E') {
    		dir = 'S';
    	}
    	else if(dir == 'S') {
    		dir = 'W';
    	}
    	else {
    		dir = 'N';
    	}
    	
    	return actions[2];
    }
}
