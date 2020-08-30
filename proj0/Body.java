import java.lang.*;

public class Body {

  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;

  public Body(double xP, double yP, double xV, double yV, double m, String img) {
    xxPos  = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }

  public Body(Body b) {
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }

  public double calcDistance(Body b) {
    return Math.sqrt((xxPos - b.xxPos) * (xxPos - b.xxPos) + (yyPos - b.yyPos) * (yyPos - b.yyPos));
  }

  public double calcForceExertedBy(Body b) {
    if (this.equals(b)) {
      return 0.0;
    }
    return (6.67e-11 * mass * b.mass) / Math.pow(this.calcDistance(b), 2);
  }

  public double calcForceExertedByX(Body b) {
    return (this.calcForceExertedBy(b) * (b.xxPos - xxPos)) / this.calcDistance(b);
  }

  public double calcForceExertedByY(Body b) {
    return (this.calcForceExertedBy(b) * (b.yyPos - yyPos)) / this.calcDistance(b);
  }

  public double calcNetForceExertedByX(Body[] bs)  {
    double netForce = 0;
    for (int index = 0; index < bs.length; index += 1) {
      if (this.equals(bs[index])) {
        continue;
      }
      netForce += this.calcForceExertedByX(bs[index]);
    }
    return netForce;
  }

  public double calcNetForceExertedByY(Body[] bs)  {
    double netForce = 0;
    for (int index = 0; index < bs.length; index += 1) {
      if (this.equals(bs[index])) {
        continue;
      }
      netForce += this.calcForceExertedByY(bs[index]);
    }
    return netForce;
  }

  public void update(double dt, double fX, double fY) {
    double xAccel = fX / mass;
    double yAccel = fY / mass;
    double newXVel = xxVel + dt * xAccel;
    double newYVel = yyVel + dt * yAccel;
    double newXPos = xxPos + dt * newXVel;
    double newYPos = yyPos + dt * newYVel;
    xxVel = newXVel;
    yyVel = newYVel;
    xxPos = newXPos;
    yyPos = newYPos;
  }

  public void draw() {
    StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
  }
}
