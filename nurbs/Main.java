package jl2119.nurbs;

import jl2119.Vector.Vector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<Long, Double> knots = new HashMap<>();
        knots.put(1l, 0.0);
        knots.put(2l, 1.0);
        knots.put(3l, 2.0);
        knots.put(4l, 3.0);
        knots.put(5l, 4.0);
        knots.put(6l, 5.0);
        NFunction n = new NFunction(3, 3, knots);
        double test = n.Apply(2.5);
        test = n.Apply(3.5);

        Map2D<Long, Long, Vector> grid = GetPointGrid();
        Plot plot = new Plot(12, 12, 1, grid, 10);

        System.out.println("Plotting");
        plot.Draw();
    }

    private static Map2D<Long, Long, Vector> GetPointGrid() {
        Map2D<Long, Long, Vector> ptsGrid = new Map2D<>();
        long gridSize = 10;
        // Level out:
        for (long i = 1; i <= gridSize; i++) {
            for (long j = 1; j <= gridSize; j++) {
                Vector v = new Vector(i , 0, j);
                ptsGrid.putValue(i, j, v);
            }
        }
        // Now add free pts
        PutVector(ptsGrid, new Vector(3, 1, 4));
        PutVector(ptsGrid, new Vector(2, -1, 2));
        PutVector(ptsGrid, new Vector(4, 0.5, 2));
        PutVector(ptsGrid, new Vector(7, 3, 6));

        return ptsGrid;
    }

    private static void PutVector(Map2D<Long, Long, Vector> map, Vector v) {
        map.putValue((long)v.getX(), (long)v.getZ(), v);
    }
}
