package com.csc205.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Cube3D {

    private static final Logger logger = Logger.getLogger(Cube3D.class.getName());

    private Point3D origin;
    private double sideLength;
    private List<Point3D> vertices;

    /**
     * Constructs a Cube3D with a given origin and side length.
     * The origin is the bottom-left-front corner of the cube.
     */
    private Cube3D(Point3D origin, double sideLength) {
        if (origin == null || sideLength <= 0) {
            logger.log(Level.SEVERE, "Invalid cube parameters: origin={0}, sideLength={1}", new Object[]{origin, sideLength});
            throw new IllegalArgumentException("Origin must not be null and side length must be positive.");
        }
        this.origin = origin;
        this.sideLength = sideLength;
        this.vertices = calculateVertices();
        logger.log(Level.INFO, "Cube3D created with origin {0} and side length {1}", new Object[]{origin, sideLength});
    }

    /**
     * Static factory method to create a Cube3D instance.
     * Promotes clarity and flexibility in object creation.
     */
    public static Cube3D of(Point3D origin, double sideLength) {
        return new Cube3D(origin, sideLength);
    }

    /**
     * Calculates the 8 vertices of the cube based on origin and side length.
     * Vertices are ordered for consistent edge construction.
     */
    private List<Point3D> calculateVertices() {
        List<Point3D> v = new ArrayList<>();
        double x = origin.getX();
        double y = origin.getY();
        double z = origin.getZ();
        double s = sideLength;

        v.add(Point3D.of(x, y, z));         // 0: origin
        v.add(Point3D.of(x + s, y, z));     // 1
        v.add(Point3D.of(x + s, y + s, z)); // 2
        v.add(Point3D.of(x, y + s, z));     // 3
        v.add(Point3D.of(x, y, z + s));     // 4
        v.add(Point3D.of(x + s, y, z + s)); // 5
        v.add(Point3D.of(x + s, y + s, z + s)); // 6
        v.add(Point3D.of(x, y + s, z + s)); // 7

        logger.log(Level.INFO, "Cube vertices calculated.");
        return v;
    }

    /**
     * Translates the cube by the given deltas in each direction.
     * Updates all vertices accordingly.
     */
    public void translate(double dx, double dy, double dz) {
        logger.log(Level.INFO, "Translating cube by ({0}, {1}, {2})", new Object[]{dx, dy, dz});
        origin.translate(dx, dy, dz);
        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).translate(dx, dy, dz);
        }
    }

    /**
     * Rotates the cube around the Z-axis by a given angle in degrees.
     * Applies rotation to all vertices.
     */
    public void rotateAroundZ(double angleDegrees) {
        logger.log(Level.INFO, "Rotating cube around Z-axis by {0} degrees", angleDegrees);
        for (Point3D vertex : vertices) {
            vertex.rotateAroundZ(angleDegrees);
        }
    }

    /**
     * Calculates the perimeter of the cube.
     * A cube has 12 edges of equal length.
     */
    public double perimeter() {
        double perimeter = 12 * sideLength;
        logger.log(Level.INFO, "Cube perimeter calculated: {0}", perimeter);
        return perimeter;
    }

    /**
     * Calculates the volume of the cube.
     * Volume = sideLength³
     */
    public double volume() {
        double volume = Math.pow(sideLength, 3);
        logger.log(Level.INFO, "Cube volume calculated: {0}", volume);
        return volume;
    }

    /**
     * Returns a list of Line3D objects representing the cube's edges.
     * Useful for rendering or collision detection.
     */
    public List<Line3D> getEdges() {
        List<Line3D> edges = new ArrayList<>();
        int[][] edgeIndices = {
            {0,1},{1,2},{2,3},{3,0}, // bottom face
            {4,5},{5,6},{6,7},{7,4}, // top face
            {0,4},{1,5},{2,6},{3,7}  // vertical edges
        };
        for (int[] pair : edgeIndices) {
            edges.add(Line3D.of(vertices.get(pair[0]), vertices.get(pair[1])));
        }
        logger.log(Level.INFO, "Cube edges constructed.");
        return edges;
    }

    /**
     * Returns a string representation of the cube.
     * Useful for debugging and logging.
     */
    @Override
    public String toString() {
        return String.format("Cube3D(origin=%s, sideLength=%.2f)", origin, sideLength);
    }

    // Getters
    public Point3D getOrigin() { return origin; }
    public double getSideLength() { return sideLength; }
    public List<Point3D> getVertices() { return new ArrayList<>(vertices); }
}
