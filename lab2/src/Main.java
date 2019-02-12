import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.Domain;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.NotEqualConstraint;
import aima.core.search.csp.SolutionStrategy;
import aima.core.search.csp.Variable;

public class Main {

	private static CSP setupCSP() {
		CSP csp = null;
//		In five houses, each with a different color, live five persons of different nationality,
//		each of whom prefers a different brand of cigarettes, a different drink, and a different pet.
//		The five houses are arranged in a row (no house has more than 2 neighbors).   
//		# The Englishman lives in the red house.
//		# The Spaniard owns the dog.
//		# Coffee is drunk in the green house.
//		# The Ukrainian drinks tea.
//		# The green house is immediately to the right of the ivory house.
//		# The Old Gold smoker owns snails.
//		# Kools are smoked in the yellow house.
//		# Milk is drunk in the middle house.
//		# The Norwegian lives in the first house.
//		# The man who smokes Chesterfields lives in the house next to the man with the fox.
//		# Kools are smoked in the house next to the house where the horse is kept.
//		# The Lucky Strike smoker drinks orange juice.
//		# The Japanese smokes Parliaments.
//		# The Norwegian lives next to the blue house.
//
//		Now, who drinks water? Who owns the zebra?
				
		String[] colors = {"Red", "Green", "Ivory", "Yellow", "Blue"};
		String[] nations = {"Englishman", "Spaniard", "Norwegian", "Ukrainian", "Japanese"};
		String[] cigarettes = {"Old Gold", "Kools", "Chesterfields", "Lucky Strike", "Parliaments"};
		String[] drink = {"Water", "Orange juice", "Tea", "Coffee", "Milk"};
		String[] pet = {"Zebra", "Dog", "Fox", "Snails", "Horse"};
		
		// TODO create variables, e.g.,
		// Variable var1 = new Variable("name of the variable 1");
		// Variable var2 = new Variable("name of the variable 2");
		
		Variable ColorVarRed = new Variable("Red");
		Variable ColorVarGreen = new Variable("Green");
		Variable ColorVarIvory = new Variable("Ivory");
		Variable ColorVarYellow = new Variable("Yellow");
		Variable ColorVarBlue = new Variable("Blue");
		
		Variable NationsVarEng = new Variable("Englis");
		Variable NationsVarSpan = new Variable("Spanish");
		Variable NationsVarNo = new Variable("Norsk");
		Variable NationsVarUkr = new Variable("Ukranian");
		Variable NationsVarJap = new Variable("Japanese");
		
		Variable CigVarOG = new Variable("Old Gold");
		Variable CigVarKools = new Variable("Kools");
		Variable CigVarChest = new Variable("Chesterfields");
		Variable CigVarLS = new Variable("Lucky Strike");
		Variable CigVarPar = new Variable("Parliaments");
		
		Variable DrinkVarW = new Variable("Water");
		Variable DrinkVarOJ = new Variable("Orange Juice");
		Variable DrinkVarTea = new Variable("Tea");
		Variable DrinkVarCo = new Variable("Coffee");
		Variable DrinkVarMi = new Variable("Milk");
		
		Variable petVarZebra = new Variable("Zebra");
		Variable petVarDog = new Variable("Dog");
		Variable petVarFox = new Variable("Fox");
		Variable petVarSnail = new Variable("Snail");
		Variable petVarHorse = new Variable("Horse");
		
		
		List<Variable> variables = new ArrayList<Variable>();
		// TODO add all your variables to this list, e.g.,
		variables.add(ColorVarRed);
		variables.add(ColorVarGreen);
		variables.add(ColorVarIvory);
		variables.add(ColorVarBlue);
		variables.add(ColorVarYellow);
		
		variables.add(NationsVarEng);
		variables.add(NationsVarJap);
		variables.add(NationsVarNo);
		variables.add(NationsVarSpan);
		variables.add(NationsVarUkr);
		
		variables.add(CigVarOG);
		variables.add(CigVarKools);
		variables.add(CigVarChest);
		variables.add(CigVarLS);
		variables.add(CigVarPar);
		
		variables.add(DrinkVarW);
		variables.add(DrinkVarOJ);
		variables.add(DrinkVarTea);
		variables.add(DrinkVarCo);
		variables.add(DrinkVarMi);
		
		variables.add(petVarZebra);
		variables.add(petVarDog);
		variables.add(petVarFox);
		variables.add(petVarSnail);
		variables.add(petVarHorse);
		
		
		csp = new CSP(variables);

		// TODO set domains of variables, e.g.,
		// Domain d1 = new Domain(new String[]{"foo", "bar"});
		// csp.setDomain(var1, d1);
		// Domain d2 = new Domain(new Integer[]{1, 2});
		// csp.setDomain(var2, d2);
		
		//**COLORS**
		Domain RedDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(ColorVarRed, RedDomain);
		
		Domain GreenDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(ColorVarGreen, GreenDomain);
		
		Domain IvoryDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(ColorVarIvory, IvoryDomain);
		
		Domain YellowDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(ColorVarYellow, YellowDomain);
		
		Domain BlueDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(ColorVarBlue, BlueDomain);
		
		//**NATIONS**
		Domain EnglishDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(NationsVarEng, EnglishDomain);
		
		Domain SpanishDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(NationsVarSpan, SpanishDomain);
		
		Domain NorskDomain = new Domain(new Integer[]{1});
		csp.setDomain(NationsVarNo, NorskDomain);
		
		Domain UkrainianDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(NationsVarUkr, UkrainianDomain);
		
		Domain JapaneseDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(NationsVarJap, JapaneseDomain);
		
		//**CIGARETTES**
		Domain OGDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(CigVarOG, OGDomain);
		
		Domain KoolsDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(CigVarKools, KoolsDomain);
		
		Domain ChestDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(CigVarChest, ChestDomain);
		
		Domain LSDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(CigVarLS, LSDomain);
		
		Domain ParDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(CigVarPar, ParDomain);
		
		//**DRINKS**
		Domain WaterDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(DrinkVarW, WaterDomain);
		
		Domain OJDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(DrinkVarOJ, OJDomain);
		
		Domain TeaDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(DrinkVarTea, TeaDomain);
		
		Domain CoffeeDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(DrinkVarCo, CoffeeDomain);
		
		Domain MilkDomain = new Domain(new Integer[]{3});
		csp.setDomain(DrinkVarMi, MilkDomain);
		
		//**PETS**
		Domain ZebraDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(petVarZebra, ZebraDomain);
		
		Domain DogDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(petVarDog, DogDomain);
		
		Domain FoxDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(petVarFox, FoxDomain);
		
		Domain SnailDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(petVarSnail, SnailDomain);
		
		Domain HorseDomain = new Domain(new Integer[]{1, 2, 3, 4, 5});
		csp.setDomain(petVarHorse, HorseDomain);
		
		// TODO add constraints, e.g.,
		// csp.addConstraint(new NotEqualConstraint(var1, var2)); // meaning var1 != var2
		// csp.addConstraint(new EqualConstraint(var1, var2)); // meaning var1 == var2
		// csp.addConstraint(new SuccessorConstraint(var1, var2)); // meaning var1 == var2 + 1
		// csp.addConstraint(new DifferByOneConstraint(var1, var2)); // meaning var1 == var2 + 1 or var1 == var2 - 1 
		
		// **NOTHING CAN BE THE SAME AS ANOTHER THING CONSTRAINTS**
		// This is getting very tedious btw...
		csp.addConstraint(new NotEqualConstraint(ColorVarBlue, ColorVarGreen));
		csp.addConstraint(new NotEqualConstraint(ColorVarBlue, ColorVarRed));
		csp.addConstraint(new NotEqualConstraint(ColorVarBlue, ColorVarIvory));
		csp.addConstraint(new NotEqualConstraint(ColorVarBlue, ColorVarYellow));
		csp.addConstraint(new NotEqualConstraint(ColorVarRed, ColorVarGreen));
		csp.addConstraint(new NotEqualConstraint(ColorVarRed, ColorVarYellow));
		csp.addConstraint(new NotEqualConstraint(ColorVarRed, ColorVarIvory));
		csp.addConstraint(new NotEqualConstraint(ColorVarGreen, ColorVarYellow));
		csp.addConstraint(new NotEqualConstraint(ColorVarGreen, ColorVarIvory));
		csp.addConstraint(new NotEqualConstraint(ColorVarYellow, ColorVarIvory));

		csp.addConstraint(new NotEqualConstraint(NationsVarEng, NationsVarSpan));
		csp.addConstraint(new NotEqualConstraint(NationsVarEng, NationsVarNo));
		csp.addConstraint(new NotEqualConstraint(NationsVarEng, NationsVarUkr));
		csp.addConstraint(new NotEqualConstraint(NationsVarEng, NationsVarJap));
		csp.addConstraint(new NotEqualConstraint(NationsVarSpan, NationsVarNo));
		csp.addConstraint(new NotEqualConstraint(NationsVarSpan, NationsVarUkr));
		csp.addConstraint(new NotEqualConstraint(NationsVarSpan, NationsVarJap));
		csp.addConstraint(new NotEqualConstraint(NationsVarNo, NationsVarUkr));
		csp.addConstraint(new NotEqualConstraint(NationsVarNo, NationsVarJap));
		csp.addConstraint(new NotEqualConstraint(NationsVarUkr, NationsVarJap));

		csp.addConstraint(new NotEqualConstraint(CigVarOG, CigVarKools));
		csp.addConstraint(new NotEqualConstraint(CigVarOG, CigVarChest));
		csp.addConstraint(new NotEqualConstraint(CigVarOG, CigVarLS));
		csp.addConstraint(new NotEqualConstraint(CigVarOG, CigVarPar));
		csp.addConstraint(new NotEqualConstraint(CigVarKools, CigVarChest));
		csp.addConstraint(new NotEqualConstraint(CigVarKools, CigVarLS));
		csp.addConstraint(new NotEqualConstraint(CigVarKools, CigVarPar));
		csp.addConstraint(new NotEqualConstraint(CigVarChest, CigVarLS));
		csp.addConstraint(new NotEqualConstraint(CigVarChest, CigVarPar));
		csp.addConstraint(new NotEqualConstraint(CigVarLS, CigVarPar));

		csp.addConstraint(new NotEqualConstraint(DrinkVarW, DrinkVarOJ));
		csp.addConstraint(new NotEqualConstraint(DrinkVarW, DrinkVarTea));
		csp.addConstraint(new NotEqualConstraint(DrinkVarW, DrinkVarCo));
		csp.addConstraint(new NotEqualConstraint(DrinkVarW, DrinkVarMi));
		csp.addConstraint(new NotEqualConstraint(DrinkVarOJ, DrinkVarTea));
		csp.addConstraint(new NotEqualConstraint(DrinkVarOJ, DrinkVarCo));
		csp.addConstraint(new NotEqualConstraint(DrinkVarOJ, DrinkVarMi));
		csp.addConstraint(new NotEqualConstraint(DrinkVarTea, DrinkVarCo));
		csp.addConstraint(new NotEqualConstraint(DrinkVarTea, DrinkVarMi));
		csp.addConstraint(new NotEqualConstraint(DrinkVarCo, DrinkVarMi));

		csp.addConstraint(new NotEqualConstraint(petVarZebra, petVarDog));
		csp.addConstraint(new NotEqualConstraint(petVarZebra, petVarFox));
		csp.addConstraint(new NotEqualConstraint(petVarZebra, petVarSnail));
		csp.addConstraint(new NotEqualConstraint(petVarZebra, petVarHorse));
		csp.addConstraint(new NotEqualConstraint(petVarDog, petVarFox));
		csp.addConstraint(new NotEqualConstraint(petVarDog, petVarSnail));
		csp.addConstraint(new NotEqualConstraint(petVarDog, petVarHorse));
		csp.addConstraint(new NotEqualConstraint(petVarFox, petVarSnail));
		csp.addConstraint(new NotEqualConstraint(petVarFox, petVarHorse));
		csp.addConstraint(new NotEqualConstraint(petVarSnail, petVarHorse));
		
		//The Actuall fact constraints
		csp.addConstraint(new EqualConstraint(ColorVarRed, NationsVarEng));
		csp.addConstraint(new EqualConstraint(petVarDog, NationsVarSpan));
		csp.addConstraint(new EqualConstraint(DrinkVarCo, ColorVarGreen));
		csp.addConstraint(new EqualConstraint(DrinkVarTea, NationsVarUkr));
		csp.addConstraint(new SuccessorConstraint(ColorVarGreen, ColorVarIvory));
		csp.addConstraint(new EqualConstraint(CigVarOG, petVarSnail));
		csp.addConstraint(new EqualConstraint(CigVarKools, ColorVarYellow));
		csp.addConstraint(new DifferByOneConstraint(petVarFox, CigVarChest));
		csp.addConstraint(new DifferByOneConstraint(CigVarKools, petVarHorse));
		csp.addConstraint(new EqualConstraint(CigVarLS, DrinkVarOJ));
		csp.addConstraint(new EqualConstraint(NationsVarJap, CigVarPar));
		csp.addConstraint(new DifferByOneConstraint(NationsVarNo, ColorVarBlue));
		
		return csp;
	}

	private static void printSolution(Assignment solution) {
		// TODO print out useful answer
		// You can use the following to get the value assigned to a variable:
		// Object value = solution.getAssignment(var); 
		// For debugging it might be useful to print the complete assignment and check whether
		// it makes sense.
		System.out.println("solution:" + solution);
	}
	
	/**
	 * runs the CSP backtracking solver with the given parameters and print out some statistics
	 * @param description
	 * @param enableMRV
	 * @param enableDeg
	 * @param enableAC3
	 * @param enableLCV
	 */
	private static void findSolution(String description, boolean enableMRV, boolean enableDeg, boolean enableAC3, boolean enableLCV) {
		CSP csp = setupCSP();

		System.out.println("======================");
		System.out.println("running " + description);
		
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		SolutionStrategy solver = new ImprovedBacktrackingStrategy(enableMRV, enableDeg, enableAC3, enableLCV);
		final int nbAssignments[] = {0};
		solver.addCSPStateListener(new CSPStateListener() {
			@Override
			public void stateChanged(Assignment arg0, CSP arg1) {
				nbAssignments[0]++;
			}
			@Override
			public void stateChanged(CSP arg0) {}
		});
		Assignment solution = solver.solve(csp);
		endTime = System.currentTimeMillis();
		System.out.println("runtime " + (endTime-startTime)/1000.0 + "s" + ", number of assignments (visited states):" + nbAssignments[0]);
		printSolution(solution);
	}

	/**
	 * main procedure
	 */
	public static void main(String[] args) throws Exception {
		// run solver with different parameters
		findSolution("backtracking + AC3 + most constrained variable + least constraining value", true, true, true, true);
		findSolution("backtracking + AC3 + most constrained variable", true, true, true, false);
		findSolution("backtracking + AC3", false, false, true, false);
		findSolution("backtracking + forward checking + most constrained variable + least constraining value", true, true, false, true);
		findSolution("backtracking + forward checking + most constrained variable", true, true, false, false);
		findSolution("backtracking + forward checking", false, false, false, false);
	}

}
