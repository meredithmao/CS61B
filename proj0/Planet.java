import java.lang.Math;
public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double G = 6.67e-11; /**gravitational constant*/

	public Planet(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass =  p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance (Planet given) {
		double ydistance = ((- given.yyPos + yyPos) * (- given.yyPos + yyPos));
		double xdistance = ((- given.xxPos + xxPos) * (- given.xxPos + xxPos)) ;
		double distance = Math.sqrt(xdistance + ydistance);
		return distance; 
	}
	public double calcForceExertedBy (Planet given) {
		double force = ( G * given.mass * mass ) / (calcDistance(given) * calcDistance(given));
		return force;
	}
	public double calcForceExertedByX (Planet given) {
		double xForce = calcForceExertedBy(given) * (given.xxPos - xxPos)/calcDistance(given) ;
		return xForce;
	}
	public double calcForceExertedByY (Planet given) {
		double yForce = calcForceExertedBy(given) * (given.yyPos - yyPos)/calcDistance(given);
		return yForce;
	}
	public double calcNetForceExertedByX (Planet a[]) {
		double xNetForce = 0;
		for (int i = 0; i < a.length ; i += 1) {
			if (this.equals(a[i]) == false) {
				xNetForce = calcForceExertedByX(a[i]) + xNetForce;
			}
		}
		return xNetForce;
	}
	public double calcNetForceExertedByY (Planet a[]) {
		double yNetForce = 0;
		for (int i = 0; i < a.length ; i += 1) {
			if (this.equals(a[i]) == false) {
				yNetForce = calcForceExertedByY(a[i]) + yNetForce ;
			}
		}
		
		return yNetForce;
	}
	public void update (double dt, double fX, double fY) {
		double accelerationx = fX / this.mass;
		double accelerationy = fY / this.mass;
		xxVel = this.xxVel + (dt*accelerationx);
		yyVel = this.yyVel + (dt*accelerationy);
		xxPos = xxPos + (dt*xxVel);
		yyPos = yyPos + (dt*yyVel);
	}
	public void draw () {
		String planetpics = "images/"+this.imgFileName;
		StdDraw.picture(this.xxPos, this.yyPos, planetpics);
	}
}