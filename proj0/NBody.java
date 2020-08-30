public class NBody {
  public static double readRadius(String fileName) {
    In in = new In(fileName);

    int numPlanet = in.readInt();
    double radius = in.readDouble();

    return radius;
  }

  public static Body[] readBodies(String fileName) {
    In in = new In(fileName);

    int numPlanet = in.readInt();
    double radius = in.readDouble();
    Body[] bodies = new Body[numPlanet];

    for (int index = 0; index < numPlanet; index += 1) {
      double xxPos = in.readDouble();
      double yyPos = in.readDouble();
      double xxVel = in.readDouble();
      double yyVel = in.readDouble();
      double mass = in.readDouble();
      String imgFileName = in.readString();

      bodies[index] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
    }
    return bodies;
  }

  public static void main(String[] args) {
    double T = Double.parseDouble(args[0]);
    double dt = Double.parseDouble(args[1]);
    String fileName = args[2];

    double radius = readRadius(fileName);
    Body[] bodies = readBodies(fileName);

    String imageToDraw = "images/starfield.jpg";
    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-radius, radius);

    StdDraw.clear();
    StdDraw.picture(0, 0, imageToDraw);
    for (int index = 0; index < bodies.length; index += 1) {
      bodies[index].draw();
    }
    StdDraw.show();
    StdDraw.pause(10);

    int time = 0;

    while (time <= T) {
      double[] xForces = new double[bodies.length];
      double[] yForces = new double[bodies.length];

      for (int index = 0; index < bodies.length; index += 1) {
        xForces[index] = bodies[index].calcNetForceExertedByX(bodies);
        yForces[index] = bodies[index].calcNetForceExertedByY(bodies);
      }

      for (int index = 0; index < bodies.length; index += 1) {
        bodies[index].update(dt, xForces[index], yForces[index]);
      }

      StdDraw.picture(0, 0, imageToDraw);

      for (int index = 0; index < bodies.length; index += 1) {
        bodies[index].draw();
      }

      StdDraw.show();
      StdDraw.pause(10);

      time += dt;
    }

    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", radius);
    for (int i = 0; i < bodies.length; i++) {
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
    }
  }
}
