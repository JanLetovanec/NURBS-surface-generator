package jl2119.nurbs;

import jl2119.Vector.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NurbSurfaceGenerator {
    private Map2D<Long, Long, Double> points;
    private Map<Long, Double> xKnots;
    private Map<Long, Double> zKnots;
    private long degree;
    private long gridSize;

    public NurbSurfaceGenerator(Map2D<Long, Long, Vector> pointGrid, long degree, long gridSize) {
        this.gridSize = gridSize;
        this.degree = degree;
        points = new Map2D<>();
        xKnots = new HashMap<>();
        zKnots = new HashMap<>();
        for(long i = 1; i <= gridSize; i++) {
            for(long j = 1; j <= gridSize; j++) {
                Vector point = pointGrid.getValues(i, j);
                xKnots.put(i, point.getX());
                zKnots.put(j, point.getZ());
                points.putValue(i, j, point.getY());
            }
        }

        // Add degree + 1 knots
        Vector point = pointGrid.getValues(gridSize, gridSize);
        for(long i = 1; i <= degree + 1; i++) {
            xKnots.put(gridSize + i, point.getX() + i);
            zKnots.put(gridSize + i, point.getZ() + i);
        }
    }

    public double Apply(double u, double v) {
        double total = 0;
        for(long i = 1; i <= gridSize; i++) {
            for(long j = 1; j <= gridSize; j++) {
                RationalBasisFunction rbf = new RationalBasisFunction(i, j, degree, xKnots, zKnots);
                total += rbf.Apply(u, v) * points.getValues(i, j);
            }
        }
        return total;
    }
}
