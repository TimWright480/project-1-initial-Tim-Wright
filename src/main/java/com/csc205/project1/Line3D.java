package com.csc205.project1;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Line3D {

    private static final Logger logger = Logger.getLogger(Line3D.class.getName());

    private Point3D start;
    private Point3D end;

    /**
     * Constructs a Line3D between two points in 3D space.
     * This constructor is private to encourage use of the factory method.
     */
    private Line3D(Point3D start, Point3D end) {
        if (start == null || end == null) {
            logger.log(Level.SEVERE, "Start or end point cannot be null.");
            throw new IllegalArgumentException("Start and end points must not be null.");
        }
        this.start = start;
        this.end = end;
        logger.log(Level.INFO, "Line3D created from {0} to {1}", new Object[]{start, end});
    }

    /**
     * Static factory method to create a Line3D instance.
     * Promotes clarity and flexibility in object creation.
     */
    public static Line3D of(Point3D start, Point3D end) {
        return new Line3D(start, end);
    }

    /**
     * Calculates the length of the line segment.
     * Uses the distance formula between two 3D points.
     */
    public double length() {
        double len = start.distanceTo(end);
        logger.log(Level.INFO, "Line length calculated: {0}", len);
        return len;
    }

    /**
     * Calculates the shortest distance between this line and another line in 3D space.
     * Uses vector projection and cross product to determine the minimum separation.
     */
    public double shortestDistanceTo(Line3D other) {
        if (other == null) {
            logger.log(Level.SEVERE, "Cannot calculate distance to a null line.");
            throw new IllegalArgumentException("Other line must not be null.");
        }

        // Vector representations
        Point3D p1 = this.start;
        Point3D p2 = this.end;
        Point3D q1 = other.start;
        Point3D q2 = other.end;

        // Direction vectors
        double[] u = {p2.getX() - p1.getX(), p2.getY() - p1.getY(), p2.getZ() - p1.getZ()};
        double[] v = {q2.getX() - q1.getX(), q2.getY() - q1.getY(), q2.getZ() - q1.getZ()};
        double[] w0 = {p1.getX() - q1.getX(), p1.getY() - q1.getY(), p1.getZ() - q1.getZ()};

        // Cross product of direction vectors
        double[] cross = {
            u[1]*v[2] - u[2]*v[1],
            u[2]*v[0] - u[0]*v[2],
            u[0]*v[1] - u[1]*v[0]
        };

        double crossMag = Math.sqrt(cross[0]*cross[0] + cross[1]*cross[1] + cross[2]*cross[2]);

        if (crossMag == 0) {
            // Lines are parallel; compute distance from one point to the other line
            logger.log(Level.WARNING, "Lines are parallel; using point-to-line distance.");
            return pointToLineDistance(q1, q2, p1);
        }

        // Compute shortest distance using scalar triple product
        double numerator = Math.abs(
            w0[0]*cross[0] + w0[1]*cross[1] + w0[2]*cross[2]
        );
        double distance = numerator / crossMag;
        logger.log(Level.INFO, "Shortest distance between lines: {0}", distance);
        return distance;
    }

    /**
     * Helper method to compute distance from a point to a line defined by two points.
     * Useful when lines are parallel.
     */
    private double pointToLineDistance(Point3D a, Point3D b, Point3D p) {
        double[] ab = {b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ()};
        double[] ap = {p.getX() - a.getX(), p.getY() - a.getY(), p.getZ() - a.getZ()};

        double[] cross = {
            ab[1]*ap[2] - ab[2]*ap[1],
            ab[2]*ap[0] - ab[0]*ap[2],
            ab[0]*ap[1] - ab[1]*ap[0]
        };

        double crossMag = Math.sqrt(cross[0]*cross[0] + cross[1]*cross[1] + cross[2]*cross[2]);
        double abMag = Math.sqrt(ab[0]*ab[0] + ab[1]*ab[1] + ab[2]*ab[2]);

        double distance = crossMag / abMag;
        logger.log(Level.INFO, "Point-to-line distance calculated: {0}", distance);
        return distance;
    }

    /**
     * Returns a string representation of the line.
     * Useful for debugging and logging.
     */
    @Override
    public String toString() {
        return String.format("Line3D(start=%s, end=%s)", start, end);
    }

    // Getters
    public Point3D getStart() { return start; }
    public Point3D getEnd() { return end; }
}
