package utility;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Methods extends ListenerAdapter {
	public static double a;
	public static double b;
	public static double c;
	public static boolean perfect;
	public static boolean posintpos;
	public static boolean negintpos;
	public static boolean posdblpos;
	public static boolean negdblpos;
	public static boolean isInt;
	public static String returnRoot, rootposString, rootnegString;
	public static int rootposIntFinal, rootnegIntFinal;
	public static double rootpos, rootneg, rootposDblFinal, rootnegDblFinal;

	
	public Methods() {
		a = 0;
		b = 0;
		c = 0;
		perfect = false;
		posintpos = false;
		negintpos = false;
		posdblpos = false;
		negdblpos = false;

	}
	public Methods(double x, double y, double z) {
		a = x;
		b = y;
		c = z;
		perfect = false;
		posintpos = false;
		negintpos = false;
		posdblpos = false;
		negdblpos = false;
		isInt = false;
	}

	/*Below are all the methods relating to quadratic formula.*/
	public void Factor() {
		System.out.println("");
		PerfectRootCalculations();
		if (perfect) {
			System.out.println(returnRoot);
		} else {
			RealRootCalculations();
			if (rootposString.equals("NaN") && rootnegString.equals("NaN")) {
				System.out.println("No real roots.");
			} else {
				IsRootPos();
				if (isInt) {
					FinalIntOutput();
				} else {
					FinalDblOutput();
				}
			}

		}
	}
	public static void PerfectRootCalculations() {
			for (int y = -100000; y <= 100000; y++) {
				if (((y * y) == c) && (y * 2 == b) && (a == 1)) {
					returnRoot = ("This is a perfect sqaure root.\nFactor: " + y + "\nMultiplicty: 2");
					perfect = true;
				} else if ((((y * y) == c) && (y * 2 == b) && (a == 1))) {
					returnRoot = ("\nThis is a perfect sqaure root.\nFactor: " + y + "\nMultiplicity: 2");
					perfect = true;
	
				} else {
				}
			}
		}
	public static void RealRootCalculations() {
			rootpos = ((b + (Math.sqrt((b * b) - (4 * a * c))))) / (2 * a);
			rootneg = ((b - (Math.sqrt((b * b) - (4 * a * c))))) / (2 * a);
	
			rootposString = "" + rootpos;
			if (rootposString.equals("NaN")) {
	
			} else if ((rootposString.substring(rootposString.indexOf("."), rootposString.length()).equals(".0"))) {
				rootposIntFinal = (int) rootpos;
			} else {
				rootposDblFinal = rootpos;
			}
	
			rootnegString = "" + rootneg;
			if (rootnegString.equals("NaN")) {
	
			} else if ((rootnegString.substring(rootnegString.indexOf("."), rootnegString.length()).equals(".0"))) {
				rootnegIntFinal = (int) rootneg;
			} else {
				rootnegDblFinal = rootneg;
			}
	
		}
	public static void IsRootPos() {
			if ((rootposString.substring(rootposString.indexOf("."), rootposString.length()).equals(".0"))) {
				rootposIntFinal = (int) rootpos;
				if (rootposIntFinal < 0) {
					posintpos = true;
					isInt = true;
				} else {
					posintpos = false;
					isInt = true;
				}
			} else {
				rootposDblFinal = rootpos;
				if (rootposDblFinal < 0) {
					posdblpos = true;
				} else {
					posdblpos = false;
				}
			}
	
			if ((rootnegString.substring(rootnegString.indexOf("."), rootnegString.length()).equals(".0"))) {
				rootnegIntFinal = (int) rootneg;
				if (rootnegIntFinal < 0) {
					negintpos = true;
					isInt = true;
				} else {
					negintpos = false;
					isInt = true;
				}
	
			} else {
				rootnegDblFinal = rootneg;
				if (rootnegDblFinal < 0) {
					negdblpos = true;
				} else {
					negdblpos = false;
				}
			}
		}
	public static void FinalIntOutput() {
			if (posintpos && negintpos) {
				System.out.println("This is factorable.\nFactors: (x - " + Math.abs(rootposIntFinal) + ") and (x - "
						+ Math.abs(rootnegIntFinal) + ")");
			} else if (!posintpos && !negintpos) {
				System.out.println("This is factorable.\nFactors: (x + " + Math.abs(rootposIntFinal) + ") and (x + "
						+ Math.abs(rootnegIntFinal) + ")");
			} else if (!posintpos && negintpos) {
				System.out.println("This is factorable.\nFactors: (x + " + Math.abs(rootposIntFinal) + ") and (x - "
						+ Math.abs(rootnegIntFinal) + ")");
			} else if (posintpos && !negintpos) {
				System.out.println("This is factorable.\nFactors: (x - " + Math.abs(rootposIntFinal) + ") and (x + "
						+ Math.abs(rootnegIntFinal) + ")");
			}
	
		}
	public static void FinalDblOutput() {
			if (posdblpos && negdblpos) {
				System.out.println("This is factorable.\nFactors: (x - " + Math.abs(rootposDblFinal) + ") and (x - "
						+ Math.abs(rootnegDblFinal) + ")");
			} else if (!posdblpos && !negdblpos) {
				System.out.println("This is factorable.\nFactors: (x + " + Math.abs(rootposDblFinal) + ") and (x + "
						+ Math.abs(rootnegDblFinal) + ")");
			} else if (!posdblpos && negdblpos) {
				System.out.println("This is factorable.\nFactors: (x + " + Math.abs(rootposDblFinal) + ") and (x - "
						+ Math.abs(rootnegDblFinal) + ")");
			} else if (posdblpos && !negdblpos) {
				System.out.println("This is factorable.\nFactors: (x - " + Math.abs(rootposDblFinal) + ") and (x + "
						+ Math.abs(rootnegDblFinal) + ")");
			}
		}
	public void Polynomial() {
		if (b > 0 && c > 0) {
			System.out.println("x²" + " + " + b + "x" + " + " + c);
		} else if (b > 0 && c < 0) {
			System.out.println("x²" + " + " + b + "x" + " - " + Math.abs(c));
		} else if (b < 0 && c > 0) {
			System.out.println("x²" + " - " + Math.abs(b) + "x" + " + " + c);
		} else if (b < 0 && c < 0) {
			System.out.println("x²" + " - " + Math.abs(b) + "x" + " - " + Math.abs(c));
		}
	}

}
