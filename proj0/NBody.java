public class NBody {
	public static double readRadius(String file) {
		In in = new In(file);
		int firstval = in.readInt();
		double secondval = in.readDouble();
		return secondval;
	}
	public static Planet[] readPlanets (String file) {
		In in = new In(file);
		int numofplanets = in.readInt();
		double radius = in.readDouble();
		Planet[] n = new Planet[numofplanets];
		for(int i = 0; i < numofplanets; i += 1) {
				double xxPos = in.readDouble();
				double yyPos = in.readDouble();
				double xxVel = in.readDouble();
				double yyVel =in.readDouble();
				double mass = in.readDouble();
				String imgFileName = in.readString();
				Planet one = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
			n[i] = one;
		}
		return n;
	}
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		Planet[] planets = readPlanets(filename);
		double universeradius = readRadius(filename);



		StdDraw.enableDoubleBuffering();
		double [] xForces = new double [planets.length];
		double [] yForces = new double [planets.length];

		for (double time = 0; time < T ; time = time + dt) {


			StdDraw.setScale(-universeradius, universeradius);
			String image = "images/starfield.jpg";
			StdDraw.picture(0,0,image);

			for(int i = 0; i < planets.length; i+=1) {
					xForces[i] = planets[i].calcNetForceExertedByX(planets);
					yForces[i] = planets[i].calcNetForceExertedByY(planets);
				
				for (int j = 0; j < planets.length; j+=1) {
					planets[j].update(dt, xForces[j], yForces[j]);
					planets[j].draw(); 
				}
			}
			StdDraw.show();
			StdDraw.pause(10);

		}
			StdOut.printf("%d\n", planets.length);
			StdOut.printf("%.2e\n", universeradius);
			for (int i = 0; i < planets.length; i++) {
		    	StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}