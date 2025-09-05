package com.csc205.project1;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Point3D {

    private static final Logger logger = Logger.getLogger(Point3D.class.getName());

    private double x;
    private double y;
    private double z;

    /**
     * Constructs a new Point3D with the given coordinates.
     * This constructor is used internally; prefer using the static factory method `of()`.
     */
    private Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        logger.log(Level.INFO, "Point3D created at ({0}, {1}, {2})", new Object[]{x, y, z});
    }

    /**
     * Static factory method to create a new Point3D instance.
     * This approach allows flexibility for future enhancements like caching or validation.
     */
    public static Point3D of(double x, double y, double z) {
        return new Point3D(x, y, z);
    }

    /**
     * Calculates the Euclidean distance between this point and another.
     * Logs the result at INFO level.
     */
    public double distanceTo(Point3D other) {
        if (other == null) {
            logger.log(Level.SEVERE, "Cannot calculate distance to a null point.");
            throw new IllegalArgumentException("Other point must not be null.");
        }
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        logger.log(Level.INFO, "Distance calculated: {0}", distance);
        return distance;
    }

    /**
     * Rotates the point around the Z-axis by a given angle in degrees.
     * This method modifies the X and Y coordinates while keeping Z constant.
     */
    public void rotateAroundZ(double angleDegrees) {
        double angleRadians = Math.toRadians(angleDegrees);
        double newX = x * Math.cos(angleRadians) - y * Math.sin(angleRadians);
        double newY = x * Math.sin(angleRadians) + y * Math.cos(angleRadians);
        logger.log(Level.INFO, "Rotating around Z-axis by {0} degrees", angleDegrees);
        this.x = newX;
        this.y = newY;
    }

    /**
     * Translates the point by the given deltas in each direction.
     * This method is useful for moving the point in space.
     */
    public void translate(double dx, double dy, double dz) {
        logger.log(Level.INFO, "Translating point by ({0}, {1}, {2})", new Object[]{dx, dy, dz});
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    /**
     * Returns a string representation of the point.
     * Useful for debugging and logging.
     */
    @Override
    public String toString() {
        return String.format("Point3D(x=%.2f, y=%.2f, z=%.2f)", x, y, z);
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getZ() { return z; }
}
